package it.polimi.se2019.view.selector;

import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;
import it.polimi.se2019.model.events.selectorEvents.*;
import it.polimi.se2019.view.components.EffectV;
import it.polimi.se2019.view.components.PlayerV;
import it.polimi.se2019.view.components.PowerUpCardV;
import it.polimi.se2019.view.components.WeaponCardV;
import java.util.ArrayList;
import java.util.List;
/**interface with the method implemented by cli and gui selector
 * @author FedericoMainettiGambera
 * @author LudoLerma */
public interface SelectorV {

     void askGameSetUp(boolean canBot);

    /**
     * @deprecated
     */
    @Deprecated
     void askPlayerSetUp();

     void askFirstSpawnPosition(List<PowerUpCardV> powerUpCards, boolean spawnBot);

     void askTurnAction(int actionNumber, boolean canUsePowerUp, boolean canUseBot);

     void askBotMove(SelectorEventPositions selectorEventPositions);

     void askRunAroundPosition(List<Position> positions);

     void askGrabStuffAction();

     void askGrabStuffMove(List<Position> positions);

     void askGrabStuffGrabWeapon(List<WeaponCardV> toPickUp);

     void askGrabStuffSwitchWeapon(List<WeaponCardV> toPickUp, List<WeaponCardV> toSwitch);

     void askPowerUpToDiscard(List<PowerUpCardV> toDiscard);

     void askWhatReaload(List<WeaponCardV> toReload);

     void askSpawn(List<PowerUpCardV> powerUpCards);

     void askShootOrMove();

    /**
     * @deprecated
     */
    @Deprecated
     void askShootReloadMove();

     void askWhatWep(List<WeaponCardV> loadedCardInHand);

     void askWhatEffect(List<EffectV> possibleEffects);

     void askEffectInputs(EffectInfoType inputType, List<Object> possibleInputs);

     void askReconnectionNickname (ReconnectionEvent reconnectionEvent);

     void askNickname();

     void askPaymentInformation(SelectorEventPaymentInformation selectorEventPaymentInformation);

     void askPowerUpToUse(SelectorEventPowerUpCards powerUpCards);

     void wantToUsePowerUpOrNot();

     void askBotShoot(SelectorEventPlayers selectorEventPlayers);

     void askTargetingScope(List<PowerUpCardV> listOfTargetingScopeV, List<Object> possiblePaymentsV, List<PlayerV> damagedPlayersV);

     void askTagBackGranade(List<PowerUpCardV> listOfTagBackGranade);
}
