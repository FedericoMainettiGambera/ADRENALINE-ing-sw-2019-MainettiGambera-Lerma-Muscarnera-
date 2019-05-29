package it.polimi.se2019.controller.statePattern;

import com.sun.org.apache.xpath.internal.operations.Mod;
import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventPosition;

import java.util.ArrayList;

public class ShootPeopleState implements State {

    private int actionNumber;

    public ShootPeopleState(int actionNumber){
        System.out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk){
        System.out.println("<SERVER> (" + this.getClass() + ") Asking input to Player \"" + playerToAsk.getNickname() + "\"");
        //final frenzy hasnt begun and no adrenaline action available
        if(!ModelGate.model.hasFinalFrenzyBegun()&&!ModelGate.model.getCurrentPlayingPlayer().hasAdrenalineShootAction()){
             if(canShoot()){
                 ViewControllerEventHandlerContext.setNextState(new ShootPeopleChooseWepState());
                 ViewControllerEventHandlerContext.state.askForInput(playerToAsk);
             }
             else{
                 System.out.println("Player cant shoot");
                 ViewControllerEventHandlerContext.setNextState(new TurnState(this.actionNumber));
                 ViewControllerEventHandlerContext.state.askForInput(playerToAsk);
             }
        }
       //FF aint begun & adrenaline action avaible
        else if(!ModelGate.model.hasFinalFrenzyBegun()&&ModelGate.model.getCurrentPlayingPlayer().hasAdrenalineShootAction()){

            if(ViewControllerEventHandlerContext.networkConnection.equals("SOCKET")){
                SelectorGate.selectorSocket.setPlayerToAsk(playerToAsk);
                SelectorGate.selectorSocket.askRunAroundPosition(ModelGate.model.getBoard().possiblePositions(playerToAsk.getPosition(),1));
            }
            else{

                SelectorGate.selectorRMI.setPlayerToAsk(playerToAsk);
                SelectorGate.selectorRMI.askRunAroundPosition(ModelGate.model.getBoard().possiblePositions(playerToAsk.getPosition(),1));
            }
        }
        //FF began
        else if(ModelGate.model.hasFinalFrenzyBegun()){

            int numberOfMoves=1;

            if(playerToAsk.getBeforeorafterStartingPlayer()<0){
                numberOfMoves=1;
            }
            else if(playerToAsk.getBeforeorafterStartingPlayer()>=0){
                numberOfMoves=2;
            }

            if(ViewControllerEventHandlerContext.networkConnection.equals("SOCKET")){
                SelectorGate.selectorSocket.setPlayerToAsk(playerToAsk);
                SelectorGate.selectorSocket.askRunAroundPosition(ModelGate.model.getBoard().possiblePositions(playerToAsk.getPosition(),numberOfMoves));
            }
            else{
                SelectorGate.selectorRMI.setPlayerToAsk(playerToAsk);
                SelectorGate.selectorRMI.askRunAroundPosition(ModelGate.model.getBoard().possiblePositions(playerToAsk.getPosition(),numberOfMoves));
            }
        }
    }


    @Override
    public void doAction(ViewControllerEvent VCE) {
        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventPosition VCEPosition = (ViewControllerEventPosition)VCE;

        //set new position for the player
        System.out.println("<SERVER> Setting player position to: [" +VCEPosition.getX()+ "][" +VCEPosition.getY() + "]");
        ModelGate.model.getPlayerList().getCurrentPlayingPlayer().setPosition(
                VCEPosition.getX(),
                VCEPosition.getY()
        );

        if(!ModelGate.model.hasFinalFrenzyBegun()&&ModelGate.model.getCurrentPlayingPlayer().hasAdrenalineShootAction()){
            ViewControllerEventHandlerContext.setNextState(new ShootPeopleChooseWepState());
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
        else if(ModelGate.model.hasFinalFrenzyBegun()){
            ViewControllerEventHandlerContext.setNextState(new ReloadState(true));
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }

    }

    public ArrayList<WeaponCard> LoadedWep(){
        ArrayList<WeaponCard> UsableWep=new ArrayList<>();

        for (WeaponCard card:ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCards()) {

          if(card.isLoaded()){
              UsableWep.add(card);
          }

        }
        return UsableWep;
    }

    public boolean canShoot(){

        if(LoadedWep().isEmpty()){
            return false;
        }
        else return true;
    }
}
