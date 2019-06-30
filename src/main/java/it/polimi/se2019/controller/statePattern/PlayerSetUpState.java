package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.GameConstant;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.PlayersList;
import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.model.enumerations.PlayersColors;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventPlayerSetUp;
import it.polimi.se2019.controller.WaitForPlayerInput;

/**DEPRECATED*/
@Deprecated
public class PlayerSetUpState implements State {

    private int numberOfPlayer;
    private int numberOfPlayersSet;

    private Player playerToAsk;

    private Thread inputTimer;

    public PlayerSetUpState(){

        System.out.println("<SERVER> New state: " + this.getClass());
        this.numberOfPlayer = ModelGate.model.getPlayerList().getNumberOfPlayers();
        this.numberOfPlayersSet = 0;
    }

    @Override
    public void askForInput(Player playerToAsk){
        this.playerToAsk = playerToAsk;
        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        System.out.println("<SERVER> Adding Observers to the Player weapons and power ups");
        playerToAsk.getWeaponCardsInHand().addObserver(ModelGate.model.getSocketVirtualView());
        playerToAsk.getWeaponCardsInHand().addObserver(ModelGate.model.getRMIVirtualView());
        playerToAsk.getPowerUpCardsInHand().addObserver(ModelGate.model.getSocketVirtualView());
        playerToAsk.getWeaponCardsInHand().addObserver(ModelGate.model.getRMIVirtualView());

        //ask to "playerToAsk" inputs
        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
            SelectorGate.getCorrectSelectorFor(playerToAsk).askPlayerSetUp();
            this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
            this.inputTimer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   @Override
    public void doAction(ViewControllerEvent VCE){

       this.inputTimer.interrupt();
       System.out.println("<SERVER> player has answered before the timer ended.");

       System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventPlayerSetUp VCEPlayerSetUp = (ViewControllerEventPlayerSetUp) VCE;

        //set nickname and color
        System.out.println("<SERVER> Setting Nickname: " + VCEPlayerSetUp.getNickname());
        ModelGate.model.getPlayerList().getCurrentPlayingPlayer().setNickname(VCEPlayerSetUp.getNickname());

        System.out.println("<SERVER> Setting Color: " + VCEPlayerSetUp.getColor());
        ModelGate.model.getPlayerList().getCurrentPlayingPlayer().setColor(VCEPlayerSetUp.getColor());

        //draw two power up cards
        System.out.println("<SERVER> draw two power up cards.");
        for(int i = 0; i < 2; i++){
            PlayersList pl = ModelGate.model.getPlayerList();
            Player p = ModelGate.model.getCurrentPlayingPlayer();
            ModelGate.model.getPowerUpDeck().moveCardTo(
                    ModelGate.model.getPlayerList().getCurrentPlayingPlayer().getPowerUpCardsInHand(),
                    ModelGate.model.getPowerUpDeck().getFirstCard().getID()
            );
        }

        //set starting ammocubes
        System.out.println("<SERVER> setting starting ammo cubes");
        for(AmmoCubesColor color: AmmoCubesColor.values() ) {
            ModelGate.model.getPlayerList().getCurrentPlayingPlayer().getPlayerBoard().addAmmoCubes(color, GameConstant.NUMBER_OF_STARTING_AMMOS);
        }

        numberOfPlayersSet++;
        System.out.println("<SERVER> number of player ready to play: " + numberOfPlayersSet + " of " + numberOfPlayer);

        if(numberOfPlayersSet < numberOfPlayer && !ModelGate.model.getPlayerList().isSomeoneAFK()) {
            ModelGate.model.getPlayerList().setNextPlayingPlayer();
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
        else{
            //set Current Playing player
            ModelGate.model.getPlayerList().setCurrentPlayingPlayer(ModelGate.model.getPlayerList().getStartingPlayer());
            System.out.println("<SERVER> All players are ready (AFK players will have default nicknames). Game begins with " + ModelGate.model.getCurrentPlayingPlayer().getNickname());

            //set next State
            ViewControllerEventHandlerContext.setNextState(new FirstSpawnState());
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
    }

    @Override
    public void handleAFK() {
        this.playerToAsk.setAFKWithNotify(true);
        System.out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        System.out.println("<SERVER> setting Username and color to default");
        ModelGate.model.getPlayerList().getCurrentPlayingPlayer().setNickname("defaultUser" + ModelGate.model.getPlayerList().getCurrentPlayingPlayer().getNickname());
        //TODO default is purple but still need to make the thing were nobody has the same color
        ModelGate.model.getPlayerList().getCurrentPlayingPlayer().setColor(PlayersColors.purple);
        //pass turn
        ModelGate.model.getPlayerList().setNextPlayingPlayer();
        if(!ViewControllerEventHandlerContext.state.getClass().toString().contains("FinalScoringState")) {
            ViewControllerEventHandlerContext.setNextState(new TurnState(1));
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
    }
}
