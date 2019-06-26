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

public interface SelectorV {

     void askGameSetUp(boolean canBot);

    /**
     * @deprecated
     */
    @Deprecated
     void askPlayerSetUp();

     void askFirstSpawnPosition(ArrayList<PowerUpCardV> powerUpCards, boolean spawnBot);

     void askTurnAction(int actionNumber, boolean canUsePowerUp, boolean canUseBot);

     void askBotMove(SelectorEventPositions selectorEventPositions);

     void askRunAroundPosition(ArrayList<Position> positions);

     void askGrabStuffAction();

     void askGrabStuffMove(ArrayList<Position> positions);

     void askGrabStuffGrabWeapon(ArrayList<WeaponCardV> toPickUp);

     void askGrabStuffSwitchWeapon(ArrayList<WeaponCardV> toPickUp, ArrayList<WeaponCardV> toSwitch);

     void askPowerUpToDiscard(ArrayList<PowerUpCardV> toDiscard);

     void askWhatReaload(ArrayList<WeaponCardV> toReload);

     void askSpawn(ArrayList<PowerUpCardV> powerUpCards);

     void askShootOrMove();

    /**
     * @deprecated
     */
    @Deprecated
     void askShootReloadMove();

     void askWhatWep(ArrayList<WeaponCardV> loadedCardInHand);

     void askWhatEffect(ArrayList<EffectV> possibleEffects);

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
