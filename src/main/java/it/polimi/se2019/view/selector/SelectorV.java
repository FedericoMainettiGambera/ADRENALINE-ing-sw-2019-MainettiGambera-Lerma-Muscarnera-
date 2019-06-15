package it.polimi.se2019.virtualView;

import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;
import it.polimi.se2019.view.components.EffectV;
import it.polimi.se2019.view.components.PowerUpCardV;
import it.polimi.se2019.view.components.WeaponCardV;

import java.util.ArrayList;
import java.util.List;

public interface SelectorV {

    public void askGameSetUp();

    @Deprecated
    public void askPlayerSetUp();

    public void askFirstSpawnPosition(ArrayList<PowerUpCardV> powerUpCards);

    public void askTurnAction(int actionNumber);

    public void askRunAroundPosition(ArrayList<Position> positions);

    public void askGrabStuffAction();

    public void askGrabStuffMove(ArrayList<Position> positions);

    public void askGrabStuffGrabWeapon(ArrayList<WeaponCardV> toPickUp);

    public void askGrabStuffSwitchWeapon(ArrayList<WeaponCardV> toPickUp, ArrayList<WeaponCardV> toSwitch);

    public void askPowerUpToDiscard(ArrayList<PowerUpCardV> toDiscard);

    public void askWhatReaload(ArrayList<WeaponCardV> toReload);

    public void askSpawn(ArrayList<PowerUpCardV> powerUpCards);

    public void askShootOrMove();

    @Deprecated
    public void askShootReloadMove();

    public void askWhatWep(ArrayList<WeaponCardV> loadedCardInHand);

    public void askWhatEffect(ArrayList<EffectV> possibleEffects);

    public void askEffectInputs(EffectInfoType inputType, List<Object> possibleInputs);

    public void askReconnectionNickname (ReconnectionEvent RE);

    public void askNickname();

}
