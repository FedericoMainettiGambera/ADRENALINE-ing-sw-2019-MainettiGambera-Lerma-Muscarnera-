package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.NormalSquare;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.enumerations.SquareTypes;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventBoolean;

public class ReloadState implements State{

    public ReloadState(){
        System.out.println("<SERVER> New state: " + this.getClass());
    }
    @Override
    public void askForInput(Player playerToAsk){

        if (ModelGate.model.hasFinalFrenzyBegun() && ModelGate.model.getCurrentPlayingPlayer().getBeforeorafterStartingPlayer() >= 0) {
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.state.doAction(null);
        }

        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");


        if(canReload()){
            System.out.println("<SERVER> The player can reload");
            //ask if they want to reload
            if(ViewControllerEventHandlerContext.networkConnection.equals("SOCKET")) {
                SelectorGate.selectorSocket.setPlayerToAsk(playerToAsk);
                SelectorGate.selectorSocket.askIfReload();
            }
            else{
                SelectorGate.selectorRMI.setPlayerToAsk(playerToAsk);
                SelectorGate.selectorRMI.askIfReload();
            }
        }
        else{
            System.out.println("<SERVER> The player can't reload");

            System.out.println("<SERVER> Placing Ammo cards on all empty NormalSquares");
            for (int i = 0; i < ModelGate.model.getBoard().getMap().length; i++) {
                for (int j = 0; j < ModelGate.model.getBoard().getMap()[0].length; j++) {
                    if((ModelGate.model.getBoard().getMap()[i][j]!=null)
                            &&   (ModelGate.model.getBoard().getMap()[i][j].getSquareType() == SquareTypes.normal)){
                        if(((NormalSquare)ModelGate.model.getBoard().getMap()[i][j]).getAmmoCards().getCards().size() == 0){
                            ModelGate.model.getAmmoDeck().moveCardTo(
                                    ((NormalSquare)ModelGate.model.getBoard().getMap()[i][j]).getAmmoCards(),
                                    ModelGate.model.getAmmoDeck().getFirstCard().getID()
                            );
                            System.out.println("<SERVER> Added Ammo card to square [" + i + "][" + j + "]");
                        }
                    }
                }
            }

            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.state.doAction(null);
        }

    }

    @Override
    public void doAction(ViewControllerEvent VCE){
        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventBoolean VCEBoolean=(ViewControllerEventBoolean)VCE;

        if(VCEBoolean.getInput()){
            System.out.println("<SERVER> Player decided to reload.");
            ViewControllerEventHandlerContext.setNextState(new ReloadWeaponState());
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
        else{
            System.out.println("<SERVER> Player decided not to reload.");
            ModelGate.model.getPlayerList().setNextPlayingPlayer();
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.state.doAction(null);
        }
    }

    public boolean canReload(){

        for (WeaponCard weaponCard : ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCards()){
            if((ModelGate.model.getCurrentPlayingPlayer().canPayAmmoCubes(weaponCard.getReloadCost())) && (!weaponCard.isLoaded())){
                return true;
            }
        }

        return false;
    }
}
