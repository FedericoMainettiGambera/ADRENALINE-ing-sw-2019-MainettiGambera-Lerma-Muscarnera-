package it.polimi.se2019.virtualView.RMIREDO;

import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPaymentInformation;
import it.polimi.se2019.view.components.PlayerV;
import it.polimi.se2019.view.components.PowerUpCardV;
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
    public void askGameSetUp(boolean canBot) {

    }

    @Override
    public void askPlayerSetUp() {

    }

    @Override
    public void askFirstSpawnPosition(ArrayList<PowerUpCard> powerUpCards, boolean spawnBot) {

    }

    @Override
    public void askTurnAction(int actionNumber, boolean canUsePowerUp, boolean canUseBot) {

    }

    @Override
    public void askRunAroundPosition(ArrayList<Position> positions) {

    }

    @Override
    public void askBotMove(ArrayList<Position> positions) {

    }

    @Override
    public void askGrabStuffAction() {

    }

    @Override
    public void askGrabStuffMove(ArrayList<Position> positions) {

    }

    @Override
    public void askGrabStuffGrabWeapon(ArrayList<WeaponCard> toPickUp) {

    }

    @Override
    public void askGrabStuffSwitchWeapon(ArrayList<WeaponCard> toPickUp, ArrayList<WeaponCard> toSwitch) {

    }

    @Override
    public void askPowerUpToDiscard(ArrayList<PowerUpCard> toDiscard) {

    }

    @Override
    public void askWhatReaload(ArrayList<WeaponCard> toReload) {

    }

    @Override
    public void askSpawn(ArrayList<PowerUpCardV> powerUpCards) {

    }

    @Override
    public void askShootOrMove() {

    }

    @Override
    public void askShootReloadMove() {

    }

    @Override
    public void askWhatWep(ArrayList<WeaponCard> loadedCardInHand) {

    }

    @Override
    public void askWhatEffect(ArrayList<Effect> possibleEffects) {

    }

    @Override
    public void askEffectInputs(EffectInfoType inputType, List<Object> possibleInputs) {

    }

    @Override
    public void askReconnectionNickname(ReconnectionEvent RE) {

    }

    @Override
    public void askNickname() {

    }

    @Override
    public void askPaymentInformation(SelectorEventPaymentInformation SEPaymentInformation) {

    }

    @Override
    public void askPowerUpToUse(List<PowerUpCardV> powerUpCards) {

    }

    @Override
    public void askWantToUsePowerUpOrNot() {

    }

    @Override
    public void askBotShoot(List<PlayerV> playerVList) {

    }

    @Override
    public void askTargetingScope(List<PowerUpCardV> listOfTargetingScopeV, List<Object> possiblePaymentsV, List<PlayerV> damagedPlayersV) {

    }

    @Override
    public void askTagBackGranade(List<PowerUpCardV> listOfTagBackGranadesV) {

    }

    //implementa selector
}
