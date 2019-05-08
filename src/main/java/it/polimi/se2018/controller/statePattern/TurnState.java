package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.controller.ModelGate;
import it.polimi.se2018.controller.ViewControllerEventHandlerContext;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.events.ViewControllerEvent;
import it.polimi.se2018.model.events.ViewControllerEventString;

public class TurnState implements State {

    private int actionNumber;

    public TurnState(int actionNumber){
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        //ask for input
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        String actionChosen = ((ViewControllerEventString)VCE).getInput();
        //set correct next state
        if(actionChosen.equals("run around")){
            ViewControllerEventHandlerContext.setNextState(new RunAroundState(this.actionNumber));
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
        else if(actionChosen.equals("grab stuff")){
            ViewControllerEventHandlerContext.setNextState(new GrabStuffState(this.actionNumber));
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
        else{
            //if(actionChosen.equals("shoot people"))
            ViewControllerEventHandlerContext.setNextState(new ShootPeopleState(this.actionNumber));
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
    }

    /*
    //TODO
    public void grabStuff(ViewControllerEvent VCE){
        if(numberOfActionDone == 1) {
            this.numberOfEventReceived = 1;
        }
        if(this.numberOfEventReceived == 1) {
            ViewControllerEventString VCEString = (ViewControllerEventString) VCE;
            if (VCEString.getInput().equals("grab")) {
                int X = ModelGate.model.getPlayerList().getCurrentPlayingPlayer().getPosition().getX();
                int Y = ModelGate.model.getPlayerList().getCurrentPlayingPlayer().getPosition().getY();
                if (ModelGate.model.getBoard().getBoard()[X][Y].getSquareType() == SquareTypes.spawnPoint) {
                    SpawnPointSquare spawnPointSquare = ((SpawnPointSquare) ModelGate.model.getBoard().getBoard()[X][Y]);
                    //ask what weapon he wants ( spawnPointSquare.getWeaponCards(); )
                } else if (ModelGate.model.getBoard().getBoard()[X][Y].getSquareType() == SquareTypes.normal) {
                    AmmoList ammoList = ((AmmoCard) ((NormalSquare) ModelGate.model.getBoard().getBoard()[X][Y]).getAmmoCards().getFirstCard()).getAmmunitions();
                    ModelGate.model.getPlayerList().getCurrentPlayingPlayer().getPlayerBoard().addAmmoList(ammoList);

                    ((NormalSquare) ModelGate.model.getBoard().getBoard()[X][Y]).getAmmoCards().moveAllCardsTo(
                            ModelGate.model.getAmmoDiscardPile()
                    );
                    if(this.numberOfActionDone == 0) {
                        //ask for second action
                    }
                    else if(this.numberOfActionDone == 1){
                        this.actionChosen = "reload";
                    }
                }
            } else if (VCEString.getInput().equals("move")) {

            }
        }
        else if(this.numberOfEventReceived == 2){
            ViewControllerEventString VCEString = (ViewControllerEventString) VCE;
            int X = ModelGate.model.getPlayerList().getCurrentPlayingPlayer().getPosition().getX();
            int Y = ModelGate.model.getPlayerList().getCurrentPlayingPlayer().getPosition().getY();
            SpawnPointSquare spawnPointSquare = ((SpawnPointSquare) ModelGate.model.getBoard().getBoard()[X][Y]);
            spawnPointSquare.getWeaponCards().moveCardTo(
                    ModelGate.model.getPlayerList().getCurrentPlayingPlayer().getWeaponCardsInHand(),
                    VCEString.getInput()
            );
            if(this.numberOfActionDone == 0) {
                //ask for second action
            }
            else if(this.numberOfActionDone == 1){
                this.actionChosen = "reload";
            }
        }
    }


    public void shootPeople(ViewControllerEvent VCE){

    }

    public void reload(ViewControllerEvent VCE){

    }

    */
}
