package it.polimi.se2019.virtualView;

import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPaymentInformation;
import it.polimi.se2019.view.components.PlayerV;
import it.polimi.se2019.view.components.PowerUpCardV;

import java.util.ArrayList;
import java.util.List;

public interface Selector {

    public void askGameSetUp(boolean canBot);

    @Deprecated
    public void askPlayerSetUp();

    public void askFirstSpawnPosition(List<PowerUpCard> powerUpCards, boolean spawnBot);

    public void askTurnAction(int actionNumber, boolean canUsePowerUp, boolean canUseBot);

    public void askRunAroundPosition(List<Position> positions);

    public void askBotMove(List<Position> positions);

    public void askGrabStuffAction();

    public void askGrabStuffMove(List<Position> positions);

    public void askGrabStuffGrabWeapon(List<WeaponCard> toPickUp);

    public void askGrabStuffSwitchWeapon(List<WeaponCard> toPickUp, List<WeaponCard> toSwitch);

    public void askPowerUpToDiscard(List<PowerUpCard> toDiscard);

    public void askWhatReaload(List<WeaponCard> toReload);

    public void askSpawn(List<PowerUpCardV> powerUpCards);

    public void askShootOrMove();

    @Deprecated
    public void askShootReloadMove();

    public void askWhatWep(List<WeaponCard> loadedCardInHand);

    public void askWhatEffect(List<Effect> possibleEffects);

    public void askEffectInputs(EffectInfoType inputType, List<Object> possibleInputs);

    public void askReconnectionNickname (ReconnectionEvent reconnectionEvent);

    public void askNickname();

    public void askPaymentInformation(SelectorEventPaymentInformation selectorEventPaymentInformation);

    public void askPowerUpToUse(List<PowerUpCardV> powerUpCards);

    public void askWantToUsePowerUpOrNot();

    public void askBotShoot(List<PlayerV> playerVList);

    public void askTargetingScope(List<PowerUpCardV> listOfTargetingScopeV, List<Object> possiblePaymentsV, List<PlayerV> damagedPlayersV);

    public void askTagBackGranade(List<PowerUpCardV> listOfTagBackGranadesV);
}
