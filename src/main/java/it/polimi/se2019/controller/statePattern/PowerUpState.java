package it.polimi.se2019.controller.statePattern;


import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.WaitForPlayerInput;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventTwoString;
import it.polimi.se2019.view.components.PowerUpCardV;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PowerUpState implements State {
    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(TurnState.class.getName());
    private String powerUpToUse;
    private State nextState;
    private Player playerToAsk;

    private Thread inputTimer;

    public PowerUpState( String powerUpToUse, State state){
        out.println("<SERVER> New state: " + this.getClass());
        this.powerUpToUse = powerUpToUse;
        this.nextState=state;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");
        this.playerToAsk = playerToAsk;

        List<PowerUpCard> powerUpCards = findPowerUp();
        List<PowerUpCardV> powerUpCardsV = new ArrayList<>();

        for (PowerUpCard p : powerUpCards) {
            powerUpCardsV.add(p.buildPowerUpCardV());
        }
        //ask for input
        try {
            out.println("<SERVER> possible power ups the user can use: ");
            for (PowerUpCardV p: powerUpCardsV) {
                out.println("         " + p.getName() + " " +p.getColor());
            }
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
            SelectorGate.getCorrectSelectorFor(playerToAsk).askPowerUpToUse(powerUpCardsV);
            this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
            this.inputTimer.start();
        } catch (Exception e) {
            logger.severe("Exception Occurred: "+e.getClass()+" "+e.getCause());
        }
    }

    @Override
    public void doAction(ViewControllerEvent viewControllerEvent) {
        this.inputTimer.interrupt();
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventTwoString viewControllerEventTwoString = (ViewControllerEventTwoString)viewControllerEvent;

        PowerUpCard chosenPowerUp = null;
        for (PowerUpCard p : playerToAsk.getPowerUpCardsInHand().getCards()) {
            if(p.getName().equals(viewControllerEventTwoString.getInput1()) && (p.getColor()+"").equals(viewControllerEventTwoString.getInput2())){
                chosenPowerUp = p;
                break;
            }
        }

        ViewControllerEventHandlerContext.setNextState(new PowerUpAskForInputState(this.nextState, chosenPowerUp));
        ViewControllerEventHandlerContext.state.askForInput(playerToAsk);
    }



    /**
     * set the player AFK in case they don't send required input in a while
     * */
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

    public List<PowerUpCard> findPowerUp(){
        List<PowerUpCard> cards = new ArrayList<>();
        if(this.powerUpToUse.equals("movement")){ //for Teleporter and newton
            for (PowerUpCard pu: playerToAsk.getPowerUpCardsInHand().getCards()) {
                if (pu.getName().equalsIgnoreCase("teleporter") || pu.getName().equalsIgnoreCase("newton")) {
                    pu.getSpecialEffect().passContext(playerToAsk, ModelGate.getModel().getPlayerList(), ModelGate.getModel().getBoard());
                    if (pu.isUsable()) {
                        cards.add(pu);
                    }
                }
            }
            return cards;
        }
        else{ //if(this.powerUpToUse.equals("damage")){ //for all power Up that are not Teleporter or newton
            for (PowerUpCard pu: playerToAsk.getPowerUpCardsInHand().getCards()) {
                if (!pu.getName().equalsIgnoreCase("teleporter") && !pu.getName().equalsIgnoreCase("newton")) {
                    pu.getSpecialEffect().passContext(playerToAsk, ModelGate.getModel().getPlayerList(), ModelGate.getModel().getBoard());
                    if (pu.isUsable()) {
                        cards.add(pu);
                    }
                }
            }
            return cards;
        }
    }
}
