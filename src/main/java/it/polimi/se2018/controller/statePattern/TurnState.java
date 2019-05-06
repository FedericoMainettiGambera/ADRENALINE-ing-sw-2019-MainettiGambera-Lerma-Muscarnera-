package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.controller.ModelGate;
import it.polimi.se2018.model.AmmoCard;
import it.polimi.se2018.model.AmmoList;
import it.polimi.se2018.model.NormalSquare;
import it.polimi.se2018.model.SpawnPointSquare;
import it.polimi.se2018.model.enumerations.SquareTypes;
import it.polimi.se2018.model.events.ViewControllerEvent;
import it.polimi.se2018.model.events.ViewControllerEventPosition;
import it.polimi.se2018.model.events.ViewControllerEventString;

public class TurnState implements State {

    private int numberOfEventReceived;

    private int numberOfActionDone;

    private String actionChosen;

    public TurnState(){
        this.numberOfEventReceived = 0;
        this.numberOfActionDone = 0;
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        if(numberOfEventReceived == 0){
            this.actionChosen = ((ViewControllerEventString)VCE).getInput();
            if(this.actionChosen.equals("run around")){
                //ask the position
            }
            else if(this.actionChosen.equals("grab stuff")){
                //ask if move or grab
            }
            else if(this.actionChosen.equals("shoot people")){
                //ask
            }
            numberOfEventReceived++;
        }
        else if(numberOfEventReceived > 0){
            if(this.actionChosen.equals("run around")){
                this.runAround(VCE);
            }
            else if(this.actionChosen.equals("grab stuff")){
                this.grabStuff(VCE);
            }
            else if(this.actionChosen.equals("shoot people")){
                this.shootPeople(VCE);
            }
            else if(this.actionChosen.equals("reload")){
                this.reload(VCE);
            }
            numberOfEventReceived++;
        }
    }

    public void runAround(ViewControllerEvent VCE){
        if(this.numberOfActionDone == 0){
            ViewControllerEventPosition VCEPosition = (ViewControllerEventPosition)VCE;
            ModelGate.model.getPlayerList().getCurrentPlayingPlayer().setPosition(VCEPosition.getPosition().getX(), VCEPosition.getPosition().getY());
            this.numberOfActionDone = 1;
            //ask For Second Action
        }
        else if(this.numberOfActionDone == 1){
            ViewControllerEventPosition VCEPosition = (ViewControllerEventPosition)VCE;
            ModelGate.model.getPlayerList().getCurrentPlayingPlayer().setPosition(VCEPosition.getPosition().getX(), VCEPosition.getPosition().getY());
            this.numberOfActionDone = 2;
            this.actionChosen = "reload";
            //ask For Reload
        }
    }

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

}
