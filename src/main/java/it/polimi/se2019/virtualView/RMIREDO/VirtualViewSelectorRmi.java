package it.polimi.se2019.virtualView.RMIREDO;

import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;
import it.polimi.se2019.model.events.selectorEvents.*;
import it.polimi.se2019.view.components.EffectV;
import it.polimi.se2019.view.components.PlayerV;
import it.polimi.se2019.view.components.PowerUpCardV;
import it.polimi.se2019.view.components.WeaponCardV;
import it.polimi.se2019.virtualView.Selector;
import it.polimi.se2019.virtualView.VirtualViewSelector;

import java.util.ArrayList;
import java.util.List;

public class VirtualViewSelectorRmi extends VirtualViewSelector implements Selector{

    private Player playerToAsk;

    public void setPlayerToAsk(it.polimi.se2019.model.Player playerToAsk){
        this.playerToAsk = playerToAsk;
    }

    public Player getPlayerToAsk() {
        return playerToAsk;
    }

    //guarda Socket per completare tutte queste @Override
    //      deve usare metodi di rmivirtualview sendtoclient

    @Override
    public  void askGameSetUp(boolean canBot) {
        RmiVirtualView.sendToClient(playerToAsk, new SelectorEventBoolean(SelectorEventTypes.askGameSetUp, canBot));
    }

    @Deprecated
    @Override
    public void askPlayerSetUp() {
        RmiVirtualView.sendToClient(playerToAsk, new SelectorEvent(SelectorEventTypes.askPlayerSetUp));
    }

    @Override
    public void askFirstSpawnPosition(List<PowerUpCard> powerUpCards, boolean spawnBot) {
        ArrayList<PowerUpCardV> powerUpCardsV= new ArrayList<>();
        for (PowerUpCard c : powerUpCards) {
            powerUpCardsV.add(c.buildPowerUpCardV());
        }
        RmiVirtualView.sendToClient(playerToAsk, new SelectorEventPowerUpCardsAndBoolean(SelectorEventTypes.askFirstSpawnPosition, powerUpCardsV, spawnBot));
    }

    @Override
    public void askTurnAction(int actionNumber, boolean canUsePowerUp, boolean canUseBot) {
        RmiVirtualView.sendToClient(playerToAsk, new SelectorEventTurnAction(SelectorEventTypes.askTurnAction, actionNumber, canUsePowerUp, canUseBot));
    }

    @Override
    public void askRunAroundPosition(List<Position> positions) {
        RmiVirtualView.sendToClient(playerToAsk, new SelectorEventPositions(SelectorEventTypes.askRunAroundPosition, positions));
    }

    @Override
    public void askBotMove(List<Position> positions) {
        RmiVirtualView.sendToClient(playerToAsk, new SelectorEventPositions(SelectorEventTypes.askBotMove, positions));
    }

    @Override
    public void askGrabStuffAction() {
        RmiVirtualView.sendToClient(playerToAsk, new SelectorEvent(SelectorEventTypes.askGrabStuffAction));
    }

    @Override
    public void askGrabStuffMove(List<Position> positions) {
        RmiVirtualView.sendToClient(playerToAsk, (new SelectorEventPositions(SelectorEventTypes.askGrabStuffMove, positions)));
    }

    @Override
    public void askGrabStuffGrabWeapon(List<WeaponCard> toPickUp) {
        ArrayList<WeaponCardV> weaponCardsV= new ArrayList<>();
        for (WeaponCard c : toPickUp) {
            weaponCardsV.add(c.buildWeapondCardV());
        }
        SelectorEventWeaponCards selectorEventWeaponCards = new SelectorEventWeaponCards(SelectorEventTypes.askGrabStuffGrabWeapon, weaponCardsV);
        RmiVirtualView.sendToClient(playerToAsk, selectorEventWeaponCards);
    }

    @Override
    public void askGrabStuffSwitchWeapon(List<WeaponCard> toPickUp, List<WeaponCard> toSwitch) {
        ArrayList<WeaponCardV> weaponCardsVtoSwitch= new ArrayList<>();
        for (WeaponCard c : toSwitch) {
            weaponCardsVtoSwitch.add(c.buildWeapondCardV());
        }
        ArrayList<WeaponCardV> weaponCardsVtoPickUp= new ArrayList<>();
        for (WeaponCard c : toPickUp) {
            weaponCardsVtoPickUp.add(c.buildWeapondCardV());
        }
        RmiVirtualView.sendToClient(playerToAsk, new SelectorEventDoubleWeaponCards(SelectorEventTypes.askGrabStuffSwitchWeapon, weaponCardsVtoPickUp, weaponCardsVtoSwitch));
    }

    @Override
    public void askPowerUpToDiscard(List<PowerUpCard> toDiscard) {
        ArrayList<PowerUpCardV> powerUpCardsV= new ArrayList<>();
        for (PowerUpCard c : toDiscard) {
            powerUpCardsV.add(c.buildPowerUpCardV());
        }
        RmiVirtualView.sendToClient(playerToAsk, new SelectorEventPowerUpCards(SelectorEventTypes.askPowerUpToDiscard, powerUpCardsV));
    }

    @Override
    public void askWhatReaload(List<WeaponCard> toReload) {
         List<WeaponCardV> weaponCardsV= new ArrayList<>();
        for (WeaponCard c : toReload) {
            weaponCardsV.add(c.buildWeapondCardV());
        }
        RmiVirtualView.sendToClient(playerToAsk, new SelectorEventWeaponCards(SelectorEventTypes.askWhatReaload,weaponCardsV));
    }

    @Override
    public void askSpawn(List<PowerUpCardV> powerUpCards) {
        RmiVirtualView.sendToClient(playerToAsk, new SelectorEventPowerUpCards(SelectorEventTypes.askSpawn, powerUpCards));
    }

    @Override
    public void askShootOrMove(){
        RmiVirtualView.sendToClient(playerToAsk, new SelectorEvent(SelectorEventTypes.askShootOrMove));
    }

    /** @deprecated */
    @Deprecated
    @Override
    public void askShootReloadMove(){
        RmiVirtualView.sendToClient(playerToAsk, new SelectorEvent(SelectorEventTypes.askShootReloadMove));
    }

    @Override
    public void askWhatWep(List<WeaponCard> loadedCardInHand) {
        List<WeaponCardV> weaponCardsV= new ArrayList<>();
        for (WeaponCard c : loadedCardInHand) {
            weaponCardsV.add(c.buildWeapondCardV());
        }
        RmiVirtualView.sendToClient(playerToAsk, new SelectorEventWeaponCards(SelectorEventTypes.askWhatWep, weaponCardsV));
    }

    @Override
    public void askWhatEffect(List<Effect> possibleEffects) {
        List<EffectV> effectsV = new ArrayList<>();
        for (Effect e : possibleEffects) {
            effectsV.add(e.buildEffectV());
        }
        RmiVirtualView.sendToClient(playerToAsk, new SelectorEventEffect(SelectorEventTypes.askWhatEffect, effectsV));
    }

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

                RmiVirtualView.sendToClient(playerToAsk, new SelectorEventEffectInputs(inputType, possibleInputsV));
            }
        }
    }

    @Override
    public void askReconnectionNickname(ReconnectionEvent RE) {
        //must be empty...
    }

    @Override
    public void askNickname() {
        //must be empty
    }

    @Override
    public void askPaymentInformation(SelectorEventPaymentInformation selectorEventPaymentInformation) {
        RmiVirtualView.sendToClient(playerToAsk, selectorEventPaymentInformation);
    }

    @Override
    public void askPowerUpToUse(List<PowerUpCardV> powerUpCards) {
        RmiVirtualView.sendToClient(playerToAsk, new SelectorEventPowerUpCards(SelectorEventTypes.askPowerUpToUse,(ArrayList<PowerUpCardV>)powerUpCards));
    }

    @Override
    public void askWantToUsePowerUpOrNot() {
        RmiVirtualView.sendToClient(playerToAsk, new SelectorEvent(SelectorEventTypes.wantToUsePowerUpOrNot));
    }

    @Override
    public void askBotShoot(List<PlayerV> playerVList){
        RmiVirtualView.sendToClient(playerToAsk, new SelectorEventPlayers(SelectorEventTypes.askBotShoot, playerVList));
    }

    @Override
    public void askTargetingScope(List<PowerUpCardV> listOfTargetingScopeV, List<Object> possiblePaymentsV, List<PlayerV> damagedPlayersV) {
        RmiVirtualView.sendToClient(playerToAsk, new SelectorEventTargetingScope(SelectorEventTypes.askTargetingScope, listOfTargetingScopeV, possiblePaymentsV, damagedPlayersV));
    }

    @Override
    public void askTagBackGranade(List<PowerUpCardV> listOfTagBackGranadesV){
        RmiVirtualView.sendToClient(playerToAsk, new SelectorEventPowerUpCards(SelectorEventTypes.askTagBackGranade, (ArrayList<PowerUpCardV>)listOfTagBackGranadesV));
    }
}
