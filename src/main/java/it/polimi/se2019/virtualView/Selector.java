package it.polimi.se2019.virtualView;

import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPaymentInformation;
import it.polimi.se2019.view.components.PlayerV;
import it.polimi.se2019.view.components.PowerUpCardV;
import java.util.List;

public interface Selector {

     void askGameSetUp(boolean canBot);

    /**@deprecated */
    @Deprecated
     void askPlayerSetUp();

     void askFirstSpawnPosition(List<PowerUpCard> powerUpCards, boolean spawnBot);

     void askTurnAction(int actionNumber, boolean canUsePowerUp, boolean canUseBot);

     void askRunAroundPosition(List<Position> positions);

     void askBotMove(List<Position> positions);

     void askGrabStuffAction();

     void askGrabStuffMove(List<Position> positions);

     void askGrabStuffGrabWeapon(List<WeaponCard> toPickUp);

     void askGrabStuffSwitchWeapon(List<WeaponCard> toPickUp, List<WeaponCard> toSwitch);

     void askPowerUpToDiscard(List<PowerUpCard> toDiscard);

     void askWhatReaload(List<WeaponCard> toReload);

     void askSpawn(List<PowerUpCardV> powerUpCards);

     void askShootOrMove();

     /**@deprecated */
    @Deprecated
     void askShootReloadMove();

     void askWhatWep(List<WeaponCard> loadedCardInHand);

     void askWhatEffect(List<Effect> possibleEffects);

     void askEffectInputs(EffectInfoType inputType, List<Object> possibleInputs);

     void askReconnectionNickname (ReconnectionEvent reconnectionEvent);

     void askNickname();

     void askPaymentInformation(SelectorEventPaymentInformation selectorEventPaymentInformation);

     void askPowerUpToUse(List<PowerUpCardV> powerUpCards);

     void askWantToUsePowerUpOrNot();

     void askBotShoot(List<PlayerV> playerVList);

     void askTargetingScope(List<PowerUpCardV> listOfTargetingScopeV, List<Object> possiblePaymentsV, List<PlayerV> damagedPlayersV);

     void askTagBackGranade(List<PowerUpCardV> listOfTagBackGranadesV);
}
