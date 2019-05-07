package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.controller.ModelGate;
import it.polimi.se2018.controller.ViewControllerEventHandlerContext;
import it.polimi.se2018.model.GameConstant;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.enumerations.AmmoCubesColor;
import it.polimi.se2018.model.events.ViewControllerEvent;
import it.polimi.se2018.model.events.ViewControllerEventPlayerSetUp;

public class PlayerSetUpState implements State {

    private int numberOfPlayer;
    private int numberOfPlayersSet;

    public PlayerSetUpState(){
        this.numberOfPlayer = ModelGate.model.getPlayerList().getNumberOfPlayers();
        this.numberOfPlayersSet = 0;
    }

    @Override
    public void askForInput(Player playerToAsk){
        //ask to "playerToAsk" inputs
    }

   @Override
    public void doAction(ViewControllerEvent VCE){
        ViewControllerEventPlayerSetUp VCEPlayerSetUp = (ViewControllerEventPlayerSetUp) VCE;

        //set nickname and color
       ModelGate.model.getPlayerList().getCurrentPlayingPlayer().setNickname(VCEPlayerSetUp.getNickname());
       ModelGate.model.getPlayerList().getCurrentPlayingPlayer().setColor(VCEPlayerSetUp.getColor());

       //draw two power up cards
       for(int i = 0; i < 2; i++){
           ModelGate.model.getPowerUpDeck().moveCardTo(
                   ModelGate.model.getPlayerList().getCurrentPlayingPlayer().getPowerUpCardsInHand(),
                   ModelGate.model.getPowerUpDeck().getFirstCard().getID()
           );
       }

       //set starting ammocubes
       for(AmmoCubesColor color: AmmoCubesColor.values() ) {
           ModelGate.model.getPlayerList().getCurrentPlayingPlayer().getPlayerBoard().addAmmoCubes(color, GameConstant.NumberOfStartingAmmos);
       }

       numberOfPlayersSet++;

       if(numberOfPlayersSet < numberOfPlayer - 1) {
           ModelGate.model.getPlayerList().setNextPlayingPlayer();
           ViewControllerEventHandlerContext.state.doAction(null);
       }
       else{
           //set Current Playing player
           ModelGate.model.getPlayerList().setCurrentPlayingPlayer(ModelGate.model.getPlayerList().getStartingPlayer());

           //set next State
           ViewControllerEventHandlerContext.setNextState(new FirstSpawnState());
           ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
       }
    }
}
