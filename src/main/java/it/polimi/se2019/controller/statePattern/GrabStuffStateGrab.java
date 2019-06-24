package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.AmmoCard;
import it.polimi.se2019.model.NormalSquare;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.enumerations.SquareTypes;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;

import java.io.PrintWriter;

/**this class implements the GrabStuffStateGrab which makes possible for the users to grab ammo card or weapon card*/
public class GrabStuffStateGrab implements State {

    private static PrintWriter out= new PrintWriter(System.out, true);

    private int actionNumber;

    /** constructor:
     * @param actionNumber needed to know if it's 1st or 2nd action of the player
     * */
    public GrabStuffStateGrab(int actionNumber){
        out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    /**@param playerToAsk incates the current playing player
     * no need for inputs here
     * */
    @Override
    public void askForInput(Player playerToAsk) {
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");
        //empty
    }

    /**this function discern whether the player is in a normal square, so that they will be grabbing a ammo card
     * or in a spawn point square, so that they will be grabbing a weapon card
     * in the first case the player will just grab the ammo card
     * in the second case it will be asked to the player to choose the weapon they want to draw (in case there are more than one)
     * */
    @Override
    public void doAction(ViewControllerEvent viewControllerEvent) {
        out.println("<SERVER> "+ this.getClass() +".doAction();");

        //the player is in a spawnpoint
        if(ModelGate.model.getBoard().getSquare(ModelGate.model.getCurrentPlayingPlayer().getPosition()).getSquareType()
                == SquareTypes.spawnPoint){
            out.println("<SERVER> Player is in a SpawnPointSquare");
            ViewControllerEventHandlerContext.setNextState(new GrabStuffStateGrabWeapon(this.actionNumber));
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
        //the player is in a normal square
        else if(ModelGate.model.getBoard().getSquare(ModelGate.model.getCurrentPlayingPlayer().getPosition()).getSquareType()
                == SquareTypes.normal){
            out.println("<SERVER> Player is in a NormalSquare");
            if(!((NormalSquare)ModelGate.model.getBoard().getMap()[ModelGate.model.getCurrentPlayingPlayer().getPosition().getX()][ModelGate.model.getCurrentPlayingPlayer().getPosition().getY()]).getAmmoCards().getCards().isEmpty()){
                out.println("<SERVER> the square have an ammo card, the player can grab.");
                AmmoCard ammoCard = (AmmoCard)((NormalSquare)ModelGate.model.getBoard().getSquare(
                        ModelGate.model.getCurrentPlayingPlayer().getPosition())
                ).getAmmoCards().getFirstCard();

                //moving the grabbed ammo card to the discard pile
                ((NormalSquare)ModelGate.model.getBoard().getSquare(
                        ModelGate.model.getCurrentPlayingPlayer().getPosition())
                ).getAmmoCards().moveCardTo(ModelGate.model.getAmmoDiscardPile(), ammoCard.getID());

                //grab ammocubes
                out.println("<SERVER> Grabbing ammo cubes");
                ModelGate.model.getCurrentPlayingPlayer().addAmmoCubes(ammoCard.getAmmunitions());

                //set next state:

                //grab a power up and the player's hand is already full
                if((ammoCard.isPowerUp())&&(ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand().getCards().size() >=2)){
                    ViewControllerEventHandlerContext.setNextState(new GrabStuffStateDrawAndDiscardPowerUp(this.actionNumber));
                    ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
                }
                //grab a power up and the player's hand is not full
                else if(ammoCard.isPowerUp()){
                    ViewControllerEventHandlerContext.setNextState(new GrabStuffStateDrawPowerUp(this.actionNumber));
                    ViewControllerEventHandlerContext.state.doAction(null);
                }
                // the player doesn't have to grab a power up
                else{
                    if(actionNumber==1){

                        if (ModelGate.model.hasFinalFrenzyBegun() && ModelGate.model.getCurrentPlayingPlayer().getBeforeorafterStartingPlayer() >= 0) {

                            ViewControllerEventHandlerContext.setNextState(new ReloadState(false));//TODO same of all of this ifs

                        }
                        else ViewControllerEventHandlerContext.setNextState(new TurnState(2));
                    }
                    else if(actionNumber==2){
                        ViewControllerEventHandlerContext.setNextState(new ReloadState(false));

                    }
                    ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
                }
            }
            else{
                out.println("<SERVER> the square doesn't have an ammo card, the player can't grab.");
                if(actionNumber==1){

                    if (ModelGate.model.hasFinalFrenzyBegun() && ModelGate.model.getCurrentPlayingPlayer().getBeforeorafterStartingPlayer() >= 0) {

                        ViewControllerEventHandlerContext.setNextState(new ReloadState(false));//TODO same of all of this ifs

                    }
                    else ViewControllerEventHandlerContext.setNextState(new TurnState(2));
                }
                else if(actionNumber==2){
                    ViewControllerEventHandlerContext.setNextState(new ReloadState(false));

                }
                ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
            }
        }
    }

    /**
     * set the player AFK in case they don't send required input in a while
     * no input is asked in this state, so we will have to wait the next one to find out!
     * */
    @Override
    public void handleAFK() {
    }
}
