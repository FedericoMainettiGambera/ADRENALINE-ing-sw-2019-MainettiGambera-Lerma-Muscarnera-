package it.polimi.se2019.virtualView;

import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPaymentInformation;
import it.polimi.se2019.view.components.PlayerV;
import it.polimi.se2019.view.components.PowerUpCardV;

import java.util.ArrayList;
import java.util.List;

public class VirtualViewSelector implements Selector {

    public void setPlayerToAsk(Player p){

    }

    public Player getPlayerToAsk(){
        return null;
    }

    @Override
    public void askGameSetUp(boolean canBot) {

    }

    @Deprecated
    @Override
    public void askPlayerSetUp() {

    }

    @Override
    public void askFirstSpawnPosition(List<PowerUpCard> powerUpCards, boolean spawnBot) {

    }

    @Override
    public void askTurnAction(int actionNumber, boolean canUsePowerUp, boolean canUseBot) {

    }

    @Override
    public void askRunAroundPosition(List<Position> positions) {

    }

    @Override
    public void askBotMove(List<Position> positions) {

    }

    @Override
    public void askGrabStuffAction() {

    }

    @Override
    public void askGrabStuffMove(List<Position> positions) {

    }

    @Override
    public void askGrabStuffGrabWeapon(List<WeaponCard> toPickUp) {

    }

    @Override
    public void askGrabStuffSwitchWeapon(List<WeaponCard> toPickUp, List<WeaponCard> toSwitch) {

    }

    @Override
    public void askPowerUpToDiscard(List<PowerUpCard> toDiscard) {

    }

    @Override
    public void askWhatReaload(List<WeaponCard> toReload) {

    }

    @Override
    public void askSpawn(List<PowerUpCardV> powerUpCards) {

    }

    @Override
    public void askShootOrMove() {

    }

    @Deprecated
    @Override
    public void askShootReloadMove() {

    }

    @Override
    public void askWhatWep(List<WeaponCard> loadedCardInHand) {

    }

    @Override
    public void askWhatEffect(List<Effect> possibleEffects) {

    }

    @Override
    public void askEffectInputs(EffectInfoType inputType, List<Object> possibleInputs) {

    }

    @Override
    public void askReconnectionNickname(ReconnectionEvent reconnectionEvent) {

    }

    @Override
    public void askNickname() {

    }

    @Override
    public void askPaymentInformation(SelectorEventPaymentInformation selectorEventPaymentInformation) {

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


}
