package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.AmmoCard;
import it.polimi.se2019.model.NormalSquare;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.enumerations.SquareTypes;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;

public class GrabStuffStateGrab implements State {

    private int actionNumber;

    public GrabStuffStateGrab(int actionNumber){
        System.out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");
        //empty
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        //the player is in a spawnpoint
        if(ModelGate.model.getBoard().getSquare(ModelGate.model.getCurrentPlayingPlayer().getPosition()).getSquareType()
                == SquareTypes.spawnPoint){
            System.out.println("<SERVER> Player is in a SpawnPointSquare");
            ViewControllerEventHandlerContext.setNextState(new GrabStuffStateGrabWeapon(this.actionNumber));
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
        //the player is in a normal square
        else if(ModelGate.model.getBoard().getSquare(ModelGate.model.getCurrentPlayingPlayer().getPosition()).getSquareType()
                == SquareTypes.normal){
            System.out.println("<SERVER> Player is in a NormalSquare");
            AmmoCard ammoCard = (AmmoCard)((NormalSquare)ModelGate.model.getBoard().getSquare(
                    ModelGate.model.getCurrentPlayingPlayer().getPosition())
            ).getAmmoCards().getFirstCard();

            //moving the grabbed ammo card to the discard pile
            ((NormalSquare)ModelGate.model.getBoard().getSquare(
                    ModelGate.model.getCurrentPlayingPlayer().getPosition())
            ).getAmmoCards().moveCardTo(ModelGate.model.getAmmoDiscardPile(), ammoCard.getID());

            //grab ammocubes
            System.out.println("<SERVER> Grabbing ammo cubes");
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

                       ViewControllerEventHandlerContext.setNextState(new ReloadState());//TODO same of all of this ifs

                    }
                        else ViewControllerEventHandlerContext.setNextState(new TurnState(2));
                }
                else if(actionNumber==2){
                    ViewControllerEventHandlerContext.setNextState(new ReloadState());

                }
                ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
            }
        }
    }
}
