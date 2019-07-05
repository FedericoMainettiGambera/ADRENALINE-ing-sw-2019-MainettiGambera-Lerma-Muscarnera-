package it.polimi.se2019.virtualView.Socket;

import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;
import it.polimi.se2019.model.events.selectorEvents.*;
import it.polimi.se2019.view.components.*;
import it.polimi.se2019.virtualView.Selector;
import it.polimi.se2019.virtualView.VirtualViewSelector;
import java.util.ArrayList;
import java.util.List;

/**implements all kind of events that may be send to the client implemented for socket connection
 * * @author LudoLerma
 *  * @author FedericoMainettiGambera*/

public class VirtualViewSelectorSocket extends VirtualViewSelector implements Selector {

    private Player playerToAsk;

    /**player to be asked the input*/
    public void setPlayerToAsk(it.polimi.se2019.model.Player playerToAsk){
        this.playerToAsk = playerToAsk;
    }

    /**@return playerToAsk*/
    public Player getPlayerToAsk() {
        return playerToAsk;
    }

    /**@param canBot, if there are less than 5 players, the bot is allowed to be set,
     * this function asks all of the needed information to set up the game*/
    @Override
    public  void askGameSetUp(boolean canBot) {
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventBoolean(SelectorEventTypes.askGameSetUp, canBot));
    }


    /**@param powerUpCards the cards beetwen the player need to choose in order to spawn
     * @param spawnBot contains the information on whether to spawn the bot or not */
    @Override
    public void askFirstSpawnPosition(List<PowerUpCard> powerUpCards, boolean spawnBot) {
         List<PowerUpCardV> powerUpCardsV= new ArrayList<>();
        for (PowerUpCard c : powerUpCards) {
            powerUpCardsV.add(c.buildPowerUpCardV());
        }
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventPowerUpCardsAndBoolean(SelectorEventTypes.askFirstSpawnPosition, powerUpCardsV, spawnBot));
    }

    /**@param actionNumber if it's 1st or 2nd action
     * @param canUseBot if the player is allowed to use the bot
     * @param canUsePowerUp if there is any of the power ups the player can use*/
    @Override
    public void askTurnAction(int actionNumber, boolean canUsePowerUp, boolean canUseBot) {
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventTurnAction(SelectorEventTypes.askTurnAction, actionNumber, canUsePowerUp, canUseBot));
    }

    /**@param positions the list of position the player can move to to be asked to the user
     * */
    @Override
    public void askRunAroundPosition(List<Position> positions) {
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventPositions(SelectorEventTypes.askRunAroundPosition, positions));
    }
    /**@param positions the position the bot can move to be asked to the user*/
    @Override
    public void askBotMove(List<Position> positions) {
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventPositions(SelectorEventTypes.askBotMove, positions));
    }

    /**the player is asked if he wants to move or if he wants to stay still to grab*/
    @Override
    public void askGrabStuffAction() {
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEvent(SelectorEventTypes.askGrabStuffAction));
    }
    /**@param positions where the player can move to in order to grab*/
    @Override
    public void askGrabStuffMove(List<Position> positions) {
        SocketVirtualView.sendToClient(playerToAsk, (new SelectorEventPositions(SelectorEventTypes.askGrabStuffMove, positions)));
    }

    /**@param toPickUp the list of the weapon card the player can grab, it is sent to the client
     * so that they can choose one*/
    @Override
    public void askGrabStuffGrabWeapon(List<WeaponCard> toPickUp) {
        List<WeaponCardV> weaponCardsV= new ArrayList<>();
        for (WeaponCard c : toPickUp) {
            weaponCardsV.add(c.buildWeapondCardV());
        }
        SelectorEventWeaponCards selectorEventWeaponCards = new SelectorEventWeaponCards(SelectorEventTypes.askGrabStuffGrabWeapon, weaponCardsV);
        SocketVirtualView.sendToClient(playerToAsk, selectorEventWeaponCards);
    }

    /**@param toPickUp the weapons that can be picked up
     * @param toSwitch the weapons the player can swith in order to be allowed to pick up one of the previous
     * */
    @Override
    public void askGrabStuffSwitchWeapon(List<WeaponCard> toPickUp, List<WeaponCard> toSwitch) {
        List<WeaponCardV> weaponCardsVtoSwitch= new ArrayList<>();
        for (WeaponCard c : toSwitch) {
            weaponCardsVtoSwitch.add(c.buildWeapondCardV());
        }
        List<WeaponCardV> weaponCardsVtoPickUp= new ArrayList<>();
        for (WeaponCard c : toPickUp) {
            weaponCardsVtoPickUp.add(c.buildWeapondCardV());
        }
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventDoubleWeaponCards(SelectorEventTypes.askGrabStuffSwitchWeapon, weaponCardsVtoPickUp, weaponCardsVtoSwitch));
    }

    /**@param toDiscard a list of powerups card to be discarded by the user*/
    @Override
    public void askPowerUpToDiscard(List<PowerUpCard> toDiscard) {
       List<PowerUpCardV> powerUpCardsV= new ArrayList<>();
        for (PowerUpCard c : toDiscard) {
            powerUpCardsV.add(c.buildPowerUpCardV());
        }
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventPowerUpCards(SelectorEventTypes.askPowerUpToDiscard, powerUpCardsV));
    }

    /**@param toReload a list of weapon card that can be reloaded by the user*/
    @Override
    public void askWhatReaload(List<WeaponCard> toReload) {
        List<WeaponCardV> weaponCardsV= new ArrayList<>();
        for (WeaponCard c : toReload) {
            weaponCardsV.add(c.buildWeapondCardV());
        }
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventWeaponCards(SelectorEventTypes.askWhatReaload,weaponCardsV));
    }

    /**@param powerUpCards the list of power ups the player has to choose beetwen  */
    @Override
    public void askSpawn(List<PowerUpCardV> powerUpCards) {

        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventPowerUpCards(SelectorEventTypes.askSpawn, powerUpCards));
    }

    /**asks the player whether they want to shoot or they want to move
     * */
    @Override
    public void askShootOrMove(){
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEvent(SelectorEventTypes.askShootOrMove));
    }


    /**@param loadedCardInHand the weapon cards the player can use to shoot
     * */
    @Override
    public void askWhatWep(List<WeaponCard> loadedCardInHand) {
        ArrayList<WeaponCardV> weaponCardsV= new ArrayList<>();
        for (WeaponCard c : loadedCardInHand) {
            weaponCardsV.add(c.buildWeapondCardV());
        }
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventWeaponCards(SelectorEventTypes.askWhatWep, weaponCardsV));
    }

    /**asks the player which of the
     * @param possibleEffects to be used
     *                        they want to use
     * */
    @Override
    public void askWhatEffect(List<Effect> possibleEffects) {
        ArrayList<EffectV> effectsV = new ArrayList<>();
        for (Effect e : possibleEffects) {
            effectsV.add(e.buildEffectV());
        }
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventEffect(SelectorEventTypes.askWhatEffect, effectsV));
    }

    /**@param inputType the type of the input
     * @param possibleInputs the possible inputs to be used*/
    @Override
    public void askEffectInputs(EffectInfoType inputType, List<Object> possibleInputs) {
        List<Object> possibleInputsV = new ArrayList<>();
        if(possibleInputs.size()!=0) { //TODO BO STAROBA QUA NON DOVREBBE ESSERCI ; L'HO MESSA PER FARE FORZA BRUTA E SPERARE DI NON AVERE PROBLEMI DI INDEX OUT OF BOUND CHE MI DA CERTE VOLTE
            if(possibleInputs.get(0) != null) { // TODO         STA ROBA PURE NON DOVREBBE ESSERCI; L'HO MESSA PERCHE' ALCUNE VOLTE MI DA PORBLEMI DI NULL POINTER EXCEPTION, COME SE CI FOSSERO DELLE LISTE DI INPUT TUTTE A NULL
                                                // TODO         MA COMUNQUE NON E' ABBASTANZA, PERCHé CERTE VOLTE DEGLI ELEMENTI SONO NULL
                                                // TODO         PROBABILMENTE IL PROBLEMA E' CHE NELLA MAPPA CI SONO DEGLI SQUARE A NULL
                if (possibleInputs.get(0).getClass().toString().contains("Player")) {
                    for (Object p : possibleInputs) {
                        possibleInputsV.add(((Player) p).buildPlayerV());
                    }
                } else {
                    for (Object s : possibleInputs) {
                        if (s.getClass().toString().contains("NormalSquare")) {
                            possibleInputsV.add(((NormalSquare) s).buildNormalSquareV((NormalSquare) s));
                        } else {
                            possibleInputsV.add(((SpawnPointSquare) s).builSpawnPointSquareV((SpawnPointSquare) s));
                        }
                    }
                }

                SocketVirtualView.sendToClient(playerToAsk, new SelectorEventEffectInputs(inputType, possibleInputsV));
            }
        }
    }

    /**ask the nickname the user wants to reconnect with
     * @param reconnectionEvent unused*/
    @Override
    public void askReconnectionNickname(ReconnectionEvent reconnectionEvent) {
        //must be empty...
    }
    /**ask the nickname to the user*/
    @Override
    public void askNickname() {
        //must be empty
    }
    /**@param selectorEventPaymentInformation specific event */
    @Override
    public void askPaymentInformation(SelectorEventPaymentInformation selectorEventPaymentInformation) {
        SocketVirtualView.sendToClient(playerToAsk, selectorEventPaymentInformation);
    }

    /**ask the player which power up he wants to use between the ones contained in
     * @param powerUpCards list of power ups*/
    @Override
    public void askPowerUpToUse(List<PowerUpCardV> powerUpCards) {
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventPowerUpCards(SelectorEventTypes.askPowerUpToUse,powerUpCards));
    }

    /**ask the player if he wants to use a power up or not*/
    @Override
    public void askWantToUsePowerUpOrNot() {
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEvent(SelectorEventTypes.wantToUsePowerUpOrNot));
    }

    /**@param playerVList contains all of the player the bot can shoot
     * the user needs to choose one*/
    @Override
    public void askBotShoot(List<PlayerV> playerVList){
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventPlayers(SelectorEventTypes.askBotShoot, playerVList));
    }

    /**@param listOfTargetingScopeV contains all of the targeting scope the player is holding in his hand and that he can or
     * may want to use*/
    @Override
    public void askTargetingScope(List<PowerUpCardV> listOfTargetingScopeV, List<Object> possiblePaymentsV, List<PlayerV> damagedPlayersV) {
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventTargetingScope(SelectorEventTypes.askTargetingScope, listOfTargetingScopeV, possiblePaymentsV, damagedPlayersV));
    }

    /**@param listOfTagBackGranadesV contains all of the tagback grenade the player is holding in his hand and that he can or
     * may want to use*/
    @Override
    public void askTagBackGranade(List<PowerUpCardV> listOfTagBackGranadesV){
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventPowerUpCards(SelectorEventTypes.askTagBackGranade, listOfTagBackGranadesV));
    }
}
