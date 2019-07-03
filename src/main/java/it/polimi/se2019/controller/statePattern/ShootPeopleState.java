package it.polimi.se2019.controller.statePattern;


import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.OrderedCardList;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventPosition;
import it.polimi.se2019.controller.WaitForPlayerInput;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShootPeopleState implements State {
    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(ShootPeopleState.class.getName());

    private int actionNumber;

    private Player playerToAsk;

    private Thread inputTimer;

    public ShootPeopleState(int actionNumber){
        out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }


    /** @param state is the state to be set*/
    private void changeState(State state, Player player){

        ViewControllerEventHandlerContext.setNextState(state);
        ViewControllerEventHandlerContext.getState().askForInput(player);

    }


    @Override
    public void askForInput(Player playerToAsk){
        this.playerToAsk = playerToAsk;
        out.println("<SERVER> (" + this.getClass() + ") Asking input to Player \"" + playerToAsk.getNickname() + "\"");
        //final frenzy hasn't begun and no adrenaline action available
        if(!ModelGate.getModel().hasFinalFrenzyBegun()&&!ModelGate.getModel().getCurrentPlayingPlayer().hasAdrenalineShootAction()){
             if(canShoot(playerToAsk)){

                changeState(new ShootPeopleChooseWepState(this.actionNumber), playerToAsk);
             }
             else{
                 out.println("<SERVER> Player cant shoot");

                 changeState(new TurnState(this.actionNumber), playerToAsk);

             }
        }
       //FF aint begun & adrenaline action available
        else if(!ModelGate.getModel().hasFinalFrenzyBegun()&&ModelGate.getModel().getCurrentPlayingPlayer().hasAdrenalineShootAction()){

            noFFbutAdrenalineAction();
        }
        //FF began
        else if(ModelGate.getModel().hasFinalFrenzyBegun()){
            int numberOfMoves = 1;
            if (playerToAsk.getBeforeorafterStartingPlayer() >= 0) {
                numberOfMoves = 2;
            }
            List<Position> possiblePositions = this.possiblePositionsToShootFromConsideringReloadableWeapons(numberOfMoves, playerToAsk);
            if(possiblePositions.isEmpty()){
                out.println("<SERVER> there are no possible positions to move where the player can shoot from, considering even reloadable weapons.");
                //asking a new action to take
                changeState(new TurnState(this.actionNumber), playerToAsk);
            }
            else {
                askWhereToMove(possiblePositions);
            }
        }
    }

    /**if the ff hasnt started but the user unblocked adrenline action, depending if he can or can't shoot,
     * the state is changed*/
    private void noFFbutAdrenalineAction(){
        List<Position> possiblePositions = this.possiblePositionsToShootFrom(1, playerToAsk);
        if(possiblePositions.isEmpty()){
            out.println("<SERVER> there are no possible positions to move where the player can shoot from");
            //asking a new turn state action
            changeState(new TurnState(this.actionNumber),playerToAsk);
        }
        else {
            askWhereToMove(possiblePositions);
        }
    }

    /**@param possiblePositions will ask the player if they want to move in one of the possible position calculated*/
    private void askWhereToMove(List<Position> possiblePositions){
        try {
            out.println("<SERVER> the player can move and shoot from another position");
            //asking where to move
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
            SelectorGate.getCorrectSelectorFor(playerToAsk).askRunAroundPosition(possiblePositions);
            this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
            this.inputTimer.start();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "EXCEPTION", e);
        }
    }

    /**@param viewControllerEvent needed to be passed to setNewPositionForPlayer
     * depending on whether the FF has begun already and the player unlocked adrenaline actions or not
     * a following state is set*/
    @Override
    public void doAction(ViewControllerEvent viewControllerEvent) {

        this.inputTimer.interrupt();

        setNewPositionForPlayer(viewControllerEvent);

        if(!ModelGate.getModel().hasFinalFrenzyBegun()&&ModelGate.getModel().getCurrentPlayingPlayer().hasAdrenalineShootAction()){
          changeState(new ShootPeopleChooseWepState(this.actionNumber),ModelGate.getModel().getCurrentPlayingPlayer() );
        }
        else if(ModelGate.getModel().hasFinalFrenzyBegun()){
            changeState(new ReloadState(true, this.actionNumber), ModelGate.getModel().getCurrentPlayingPlayer());
        }

    }

    /**@param viewControllerEvent needed to extrapolate information from,
     * user position is set depending on the input of the event*/
    public void setNewPositionForPlayer(ViewControllerEvent viewControllerEvent){

        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventPosition viewControllerEventPosition = (ViewControllerEventPosition)viewControllerEvent;

        //set new position for the player
        out.println("<SERVER> Setting player position to: [" +viewControllerEventPosition.getX()+ "][" +viewControllerEventPosition.getY() + "]");
        ModelGate.getModel().getPlayerList().getCurrentPlayingPlayer().setPosition(
                viewControllerEventPosition.getX(),
                viewControllerEventPosition.getY()
        );}

        /**if the user isn't responsive or just rudely close the connection
         * we set them AFK */
    @Override
    public void handleAFK() {
        this.playerToAsk.setAFKWithNotify(true);
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        //pass turn
        if(!ViewControllerEventHandlerContext.getState().getClass().toString().contains("FinalScoringState")) {
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.getState().doAction(null);
        }
    }

    /**@param numberOfMoves needed to know where the player can go and shoot
     * @param  player the one who's likely about to shoot
     * @return  a list of position he will be able to shoot from */
    public List<Position> possiblePositionsToShootFrom(int numberOfMoves, Player player){
        //all possible positions where the player can move
        List<Position> possiblePositions = ModelGate.getModel().getBoard().possiblePositions(player.getPosition(), numberOfMoves);

        //filtering all the possible position: keeping only the ones from where the player can shoot
        Iterator<Position> possiblePositionsIterator = possiblePositions.iterator();
        while (possiblePositionsIterator.hasNext()){
            Position currentPossiblePositionElement = possiblePositionsIterator.next();

            out.println("<SERVER> checking if player can shoot from position " + currentPossiblePositionElement.humanString());

            if(!canShootFrom(currentPossiblePositionElement, player)){
                possiblePositionsIterator.remove();
                out.println("         no, he can't!");
            }
            else{
                out.println("         yes, he can!");
            }
        }

        return possiblePositions;
    }

    /**@param numberOfMoves the player can effectuate
     * @param player the one who need to shoot
     * similar to the previous one, but considers also the unloaded weapon that the player can afford to reload
     * @return a list of position */
    private List<Position> possiblePositionsToShootFromConsideringReloadableWeapons(int numberOfMoves, Player player){

        ArrayList<WeaponCard> loadableWeapons = new ArrayList<>();

        //finds all unloaded weapons
        for (WeaponCard wp: player.getWeaponCardsInHand().getCards()) {
            if(!wp.isLoaded()){
                loadableWeapons.add(wp);
            }
        }

        //filters the unloaded weapons with only the ones that are loadable
        loadableWeapons.removeIf(weaponCardElement -> !(new ChooseHowToPayState(player, weaponCardElement.getReloadCost())).canPayInSomeWay());

        //now we have the possible loadable weapons and we load them for a moment
        for (WeaponCard wp: loadableWeapons){
            wp.reloadWithoutNotify();
        }

        //we now search for possiblePosition in the usual way
        List<Position> possiblePositions = possiblePositionsToShootFrom(numberOfMoves, player);

        //we unload the previosly loaded weapons
        for (WeaponCard wp: loadableWeapons){
            wp.unloadWithoutNotify();
        }

        return possiblePositions;
    }

    /**@param pos the position he may want to move to
     * @param  shootingPlayer the one who shoots, needed to know their position
     * @return boolean value that indicates if the player can shoot from that position */
    private boolean canShootFrom(Position pos, Player shootingPlayer){
        //old position of the player saved to restore
        Position oldPosition = shootingPlayer.getPosition();

        //changing the players position to the new we want to calculate
        shootingPlayer.setPositionWithoutNotify(pos);

        //checks if the player can shoot
        boolean canShoot = this.canShoot(shootingPlayer);

        //restore old position
        shootingPlayer.setPositionWithoutNotify(oldPosition);

        return canShoot;
    }

    /** @param player  the likely future shooting player
     * @return boolean value that indicates if  there are any cards the player can shoot with*/
    private boolean canShoot(Player player){
        //passing context to the weapons
        for (WeaponCard wp:player.getWeaponCardsInHand().getCards()) {
            wp.passContext(player, ModelGate.getModel().getPlayerList(), ModelGate.getModel().getBoard());
        }

        //all possible usable cards
        OrderedCardList<WeaponCard> possibleCards = player.getHand().usableWeapons();

        //filtering cards with the only loaded ones
        possibleCards.getCards().removeIf(element -> !element.isLoaded());
        return !possibleCards.getCards().isEmpty();
    }

}
