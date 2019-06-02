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
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventString;
import it.polimi.se2019.virtualView.WaitForPlayerInput;

import java.util.ArrayList;

public class ReloadState implements State{

    private boolean CalledFromShootPeople;

    private Player playerToAsk;

    public ReloadState(boolean CalledFromShootPeople){
        this.CalledFromShootPeople = CalledFromShootPeople;
        System.out.println("<SERVER> New state: " + this.getClass());
    }

    @Override
    public void askForInput(Player playerToAsk){
        this.playerToAsk = playerToAsk;
        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        if ((ModelGate.model.hasFinalFrenzyBegun()  && !CalledFromShootPeople)) {
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.state.doAction(null);
        }
        else if(canReload()){
            System.out.println("<SERVER> The player can reload");
            //ask which weapon to reload
            ArrayList<WeaponCard> toReaload = new ArrayList<>();
            for (WeaponCard wc: playerToAsk.getWeaponCardsInHand().getCards()) {
                if (!wc.isLoaded()&&playerToAsk.canPayAmmoCubes(wc.getReloadCost())){
                    toReaload.add(wc);
                }
            }
            String toPrintln = "";
            for (int i = 0; i < toReaload.size() ; i++) {
                toPrintln += "[" + toReaload.get(i).getID() + "]  ";
            }
            System.out.println("<SERVER> Possible weapons that can be reloaded: " + toPrintln);


            try {
                SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
                SelectorGate.getCorrectSelectorFor(playerToAsk).askWhatReaload(toReaload);
                Thread t = new Thread(new WaitForPlayerInput(this.playerToAsk));
                t.start();
            } catch (Exception e) {
                e.printStackTrace();
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
            if(CalledFromShootPeople){
                ViewControllerEventHandlerContext.setNextState(new ShootPeopleChooseWepState());
                ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
                Thread t = new Thread(new WaitForPlayerInput(this.playerToAsk));
                t.start();
            }
            else {
                ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
                ViewControllerEventHandlerContext.state.doAction(null);
            }
        }

    }

    @Override
    public void doAction(ViewControllerEvent VCE){
        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        this.playerToAsk.menageAFKAndInputs();
        if(playerToAsk.isAFK()){
            //set next State
            System.out.println("<SERVER> " + playerToAsk.getNickname() + " is AFK, he'll pass the turn.");
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.state.doAction(null);
            return;
        }

        ViewControllerEventString VCEString=(ViewControllerEventString)VCE;

        if( ! VCEString.getInput().equals("SKIP")){
            System.out.println("<SERVER> Reloading and paying reload cost for weapon card: " + VCEString.getInput());
            ModelGate.model.getCurrentPlayingPlayer().payAmmoCubes(
                    ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCard(VCEString.getInput()).getReloadCost()
            );
            ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCard(VCEString.getInput()).reload();

            if(CalledFromShootPeople){
                ViewControllerEventHandlerContext.setNextState(new ShootPeopleChooseWepState());
                ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
            }
            else {
                ViewControllerEventHandlerContext.setNextState(new ReloadState(false));
                ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
            }
        }
        else{
            System.out.println("<SERVER> Player decided not to reload.");
            if(CalledFromShootPeople){
                ViewControllerEventHandlerContext.setNextState(new ShootPeopleChooseWepState());
                ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
            }
            else {
                ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
                ViewControllerEventHandlerContext.state.doAction(null);
            }
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
