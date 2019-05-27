package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;

public class FFSetUpState implements State {


    private int numberOfPlayerTurnsToEndGame;

    public FFSetUpState(){
        System.out.println("<SERVER> New state: " + this.getClass());
        this.numberOfPlayerTurnsToEndGame = ModelGate.model.getPlayerList().getNumberOfPlayers();
    }

    @Override
    public void askForInput(Player playerToAsk) {
        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        System.out.println("<SERVER> "+ this.getClass() +".doAction();");
        int position=-1;
        int number=0;

        ModelGate.model.triggerFinalFrenzy(true);

        //makes the players boards that has no damages in FinalFrenzy mode
        for(Player player : ModelGate.model.getPlayerList().getPlayers()){
            if(player.getBoard().getDamagesTracker().isEmpty()){
                player.makePlayerBoardFinalFrenzy();

            }

            if(position<0) {
                player.setBeforeorafterStartingPlayer(position);
                position--;
                number++;
            }
            if(ModelGate.model.getPlayerList().getStartingPlayer().getNickname().equals(player.getNickname())){
                player.setBeforeorafterStartingPlayer(0);
                position=1;
                number++;
            }
            if(position>0){
                player.setBeforeorafterStartingPlayer(position);
                position++;
                number++;
            }
            if(number==ModelGate.model.getPlayerList().getNumberOfPlayers()){
                player.setLastPlayingPlayer();
            }


        }


        ViewControllerEventHandlerContext.setNextState(new FFTurnState(1));
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());

    }
}
