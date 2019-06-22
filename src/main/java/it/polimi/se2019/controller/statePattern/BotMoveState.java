package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.WaitForPlayerInput;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventPosition;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class BotMoveState implements State {
    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(TurnState.class.getName());

    private Player playerToAsk;

    private Thread inputTimer;

    private State nextState;

    private ArrayList<Position> possiblePositions = new ArrayList<>();

    public BotMoveState(State nextState){
        out.println("<SERVER> New state: " + this.getClass());
        this.nextState = nextState;
    }

    @Override
    public void askForInput(Player playerToAsk){
        this.playerToAsk = playerToAsk;
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        //calculate possible positions
        Position botposition = ModelGate.model.getPlayerList().getPlayer("Terminator").getPosition();
        this.possiblePositions = ModelGate.model.getBoard().possiblePositions(botposition,1);
        out.println("<SERVER> Possible positions to move Bot calculated:");
        StringBuilder toPrintln = new StringBuilder();
        for (Position possiblePosition : possiblePositions) {
            toPrintln.append("[").append(possiblePosition.getX()).append("][").append(possiblePosition.getY()).append("]    ");
        }
        out.println("    " + toPrintln);

        //ask for input
        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
            SelectorGate.getCorrectSelectorFor(playerToAsk).askBotMove(possiblePositions);
            this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
            this.inputTimer.start();
        } catch (Exception e) {
            logger.severe("Exception occured  "+e.getClass()+"  "+e.getCause()+ Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        this.inputTimer.interrupt();
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        //parse VCE
        ViewControllerEventPosition VCEPosition = (ViewControllerEventPosition)VCE;
        ModelGate.model.getPlayerList().getPlayer("Terminator").setPosition(VCEPosition.getX(), VCEPosition.getY());
        out.println("<SERVER> Bot's new position is: [" + VCEPosition.getX() + "][" + VCEPosition.getY() + "]");

        //change state in botShootState passing him the next state
        ViewControllerEventHandlerContext.setNextState(new BotShootState(this.nextState));
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
    }

    @Override
    public void handleAFK() {
        this.playerToAsk.setAFKWithNotify(true);
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        //pass turn
        if(!ViewControllerEventHandlerContext.state.getClass().toString().contains("FinalScoringState")) {
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.state.doAction(null);
        }
    }
}
