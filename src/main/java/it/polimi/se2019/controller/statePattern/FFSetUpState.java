package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;

import java.io.PrintWriter;
import java.util.logging.Logger;

public class FFSetUpState implements State {

    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(FFSetUpState.class.getName());


    private int numberOfPlayerTurnsToEndGame;

    public FFSetUpState(){
        out.println("<SERVER> New state: " + this.getClass());
        this.numberOfPlayerTurnsToEndGame = ModelGate.model.getPlayerList().getNumberOfPlayers();
    }

    @Override
    public void askForInput(Player playerToAsk) {
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        out.println("<SERVER> "+ this.getClass() +".doAction();");
        int position=-1;
        int number=0;

        ModelGate.model.triggerFinalFrenzy(true);

        //makes the players boards that has no damages in FinalFrenzy mode
        for(Player player : ModelGate.model.getPlayerList().getPlayers()){
            if(player.getBoard().getDamagesTracker().isEmpty()){
                player.makePlayerBoardFinalFrenzy();

            }

            if(ModelGate.model.getPlayerList().getStartingPlayer().getNickname().equals(player.getNickname())){
                player.setBeforeorafterStartingPlayer(0);
                position=1;
                number++;
            }

           else if(position<0) {
                player.setBeforeorafterStartingPlayer(position);
                position--;
                number++;
            }

           else if(position>0){
                player.setBeforeorafterStartingPlayer(position);
                position++;
                number++;
            }
            if(number==ModelGate.model.getPlayerList().getNumberOfPlayers()){
                player.setLastPlayingPlayer();
            }

        }


        ViewControllerEventHandlerContext.setNextState(new TurnState(1));
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());

    }

    @Override
    public void handleAFK() {
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
    }
}
