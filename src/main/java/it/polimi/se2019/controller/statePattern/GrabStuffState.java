package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventString;
import it.polimi.se2019.controller.WaitForPlayerInput;

import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**this class it's a carrefour for the two actions that the choosing to grab put on your way: move and grab or stay still and grab*/
public class GrabStuffState implements State {

    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(GrabStuffState.class.getName());

    private int actionNumber;

    private Player playerToAsk;

    private Thread inputTimer;

    public GrabStuffState(int actionNumber){
        out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    /**this function asks the player to choose between two actions: to move or to grab
     * @param playerToAsk holds the current playing player*/
    @Override
    public void askForInput(Player playerToAsk) {
        this.playerToAsk = playerToAsk;
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        //ask for input
        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
            SelectorGate.getCorrectSelectorFor(playerToAsk).askGrabStuffAction();
            this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
            this.inputTimer.start();
        } catch (Exception e) {
           logger.log(Level.SEVERE, "EXCEPTION", e);
        }
    }

    /**once received the decision of the user,
     * this function calls the correct next state,
     * @param viewControllerEvent contains the needed information to discern
     * whether to call GrabStuffStateMove or GrabStuffStateGrab*/
    @Override
    public void doAction(ViewControllerEvent viewControllerEvent) {
        this.inputTimer.interrupt();

        String choice= readInput(viewControllerEvent);

        if(choice.equals("move")){
            ViewControllerEventHandlerContext.setNextState(new GrabStuffStateMove(this.actionNumber));
            ViewControllerEventHandlerContext.getState().askForInput(ModelGate.getModel().getCurrentPlayingPlayer());
        }
        else if(choice.equals("grab")){
            ViewControllerEventHandlerContext.setNextState(new GrabStuffStateGrab(this.actionNumber));
            ViewControllerEventHandlerContext.getState().doAction(null);
        }
        else{
            this.askForInput(ModelGate.getModel().getCurrentPlayingPlayer());
        }
    }

    /**@param viewControllerEvent, this function extrapolate the needed info from the event*/
    public String readInput(ViewControllerEvent viewControllerEvent){

        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventString viewControllerEventString = (ViewControllerEventString)viewControllerEvent;
        String choice = viewControllerEventString.getInput();

        out.println("<SERVER> Player's choice: " + choice);

        return choice;

    }
    /**
     * set the player AFK in case they don't send required input in a while
     * */
    @Override
    public void handleAFK() {
        this.playerToAsk.setAFKWithNotify(true);
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        //pass turn
        if(!ViewControllerEventHandlerContext.getState().getClass().toString().contains("FinalScoringState")) {
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.getState().doAction(null);
        }
    }
}
