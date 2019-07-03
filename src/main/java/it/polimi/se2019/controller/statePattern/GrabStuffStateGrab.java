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

    /**if it's 1st or 2nd action of the player*/
    private int actionNumber;

    /** constructor:
     * @param actionNumber initialize the action number attribute
     * */
    public GrabStuffStateGrab(int actionNumber){
        out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    /**@param playerToAsk indicates the current playing player
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
     * @param viewControllerEvent is left unread since unneeded
     * */
    @Override
    public void doAction(ViewControllerEvent viewControllerEvent) {
        out.println("<SERVER> " + this.getClass() + ".doAction();");
        State state;


        //the player is in a spawnpoint
        if (ModelGate.getModel().getBoard().getSquare(ModelGate.getModel().getCurrentPlayingPlayer().getPosition()).getSquareType()
                == SquareTypes.spawnPoint) {
            out.println("<SERVER> Player is in a SpawnPointSquare");
            state=new GrabStuffStateGrabWeapon(this.actionNumber);
        }
        //the player is in a normal square
        else//(ModelGate.getModel().getBoard().getSquare(ModelGate.getModel().getCurrentPlayingPlayer().getPosition()).getSquareType()
                //== SquareTypes.normal)
        {

           state=playerInNormalSquare();
        }

        ViewControllerEventHandlerContext.setNextState(state);
       if(state.toString().contains("GrabStuffStateDrawPowerUp")){
           ViewControllerEventHandlerContext.getState().doAction(null);

       }
        else ViewControllerEventHandlerContext.getState().askForInput(ModelGate.getModel().getCurrentPlayingPlayer());

    }



    /**@return the following state to be set
     * this function take cares of the situations that can occur if the player happen to be in a normal square
     * */
    private State playerInNormalSquare(){
        out.println("<SERVER> Player is in a NormalSquare");
        State state;
        if (!((NormalSquare) ModelGate.getModel().getBoard().getMap()[ModelGate.getModel().getCurrentPlayingPlayer().getPosition().getX()][ModelGate.getModel().getCurrentPlayingPlayer().getPosition().getY()]).getAmmoCards().getCards().isEmpty()) {
            AmmoCard ammoCard = grabAmmo();

            //grab a power up and the player's hand is already full
            if (ammoCard.isPowerUp()){
                state=handFull();
            }

            // the player doesn't have to grab a power up
            else{
                if(actionNumber == 1){
                    if (ModelGate.getModel().hasFinalFrenzyBegun() && ModelGate.getModel().getCurrentPlayingPlayer().getBeforeorafterStartingPlayer() >= 0) {
                        //TODO same of all of this ifs
                        state=new ReloadState(false);

                    }
                    else  state=new TurnState(2);
                }
                else{
                    state=new ReloadState(false);
                }
            }
        }

        else{
            state=ifNormalSquareIsEmpty();
        }

        return state;
    }

    /**@return the following state to be set
     * this function take care of the situations that can occur
     * if the normal square in which the player is
     * happen to be empty*/
    private State ifNormalSquareIsEmpty(){

        State state;
        out.println("<SERVER> the square doesn't have an ammo card, the player can't grab.");
        if (actionNumber == 1){
            if(ModelGate.getModel().hasFinalFrenzyBegun() && ModelGate.getModel().getCurrentPlayingPlayer().getBeforeorafterStartingPlayer() >= 0) {
                //TODO same of all of this ifs
                state=new ReloadState(false);
            }
            else  state=new TurnState(2);
        }
        else{
            state=new ReloadState(false);
        }
        return state;
    }
    /** change state if the ammo card contains a power up
     * @return the following state to be set */
    private State handFull(){

        State state;

        if(ModelGate.getModel().getCurrentPlayingPlayer().getPowerUpCardsInHand().getCards().size() >=2){
            state=(new GrabStuffStateDrawAndDiscardPowerUp(this.actionNumber));
        }
        //grab a power up and the player's hand is not full
        else {
            state=(new GrabStuffStateDrawPowerUp(this.actionNumber));
        }

        return state;
    }

    /**make the player draw a ammocard
     * @return ammoCard*/
    private AmmoCard grabAmmo(){
        out.println("<SERVER> the square have an ammo card, the player can grab.");

        AmmoCard ammoCard = (AmmoCard)((NormalSquare)ModelGate.getModel().getBoard().getSquare(
                ModelGate.getModel().getCurrentPlayingPlayer().getPosition())
        ).getAmmoCards().getFirstCard();

        //moving the grabbed ammo card to the discard pile
        ((NormalSquare)ModelGate.getModel().getBoard().getSquare(
                ModelGate.getModel().getCurrentPlayingPlayer().getPosition())
        ).getAmmoCards().moveCardTo(ModelGate.getModel().getAmmoDiscardPile(), ammoCard.getID());

        //grab ammocubes
        out.println("<SERVER> Grabbing ammo cubes");
        ModelGate.getModel().getCurrentPlayingPlayer().addAmmoCubes(ammoCard.getAmmunitions());

        return ammoCard;
    }
    /**
     * set the player AFK in case they don't send required input in a while
     * no input is asked in this state, so we will have to wait the next one to find out!
     * */
    @Override
    public void handleAFK(){

        //no need to handle it here
    }
}
