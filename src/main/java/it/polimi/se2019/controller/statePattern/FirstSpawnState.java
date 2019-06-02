package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventString;
import it.polimi.se2019.virtualView.WaitForPlayerInput;

import java.util.ArrayList;

public class FirstSpawnState implements State {

    public FirstSpawnState(){
        System.out.println("<SERVER> New state: " + this.getClass());
    }

    private Player playerToAsk;

    @Override
    public void askForInput(Player playerToAsk){
        this.playerToAsk = playerToAsk;
        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        //ask to "playerToAsk" what power up he want to discard to spawn on its correspondent SpawnPointColor
        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
            SelectorGate.getCorrectSelectorFor(playerToAsk).askFirstSpawnPosition((ArrayList)playerToAsk.getPowerUpCardsInHand().getCards());
            Thread t = new Thread(new WaitForPlayerInput(this.playerToAsk));
            t.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doAction(ViewControllerEvent VCE) throws NullPointerException{
        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        this.playerToAsk.menageAFKAndInputs();
        if(playerToAsk.isAFK()){
            //set next State
            System.out.println("<SERVER> " + playerToAsk.getNickname() + " is AFK, he'll pass the turn.");
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.state.doAction(null);
            return;
        }

        ViewControllerEventString VCEPowerUpId = (ViewControllerEventString) VCE;

        //set spawning position
        PowerUpCard cardChosen = ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand().getCard(VCEPowerUpId.getInput());
        Position spawnPosition = null;
        System.out.println("<SERVER> choosen card: " + cardChosen.getID());
        System.out.println("<SERVER> choosen color: " + cardChosen.getColor());

        try {
            spawnPosition = ModelGate.model.getBoard().getSpawnpointOfColor(cardChosen.getColor());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ModelGate.model.getCurrentPlayingPlayer().setPosition(spawnPosition);
        System.out.println("<SERVER> Spawning in the SpawnPoint of color " + cardChosen.getColor() + ", in coordinates X:(" +spawnPosition.getX() + "), Y:(" + spawnPosition.getY() + ").");


        //discard the power up card
        System.out.println("<SERVER> Discarding the choosen power up");
        ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand().moveCardTo(
                ModelGate.model.getPowerUpDiscardPile(),
                VCEPowerUpId.getInput()
        );

        //set next State
        ViewControllerEventHandlerContext.setNextState(new TurnState(1));
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
    }
}
