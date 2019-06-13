package it.polimi.se2019.virtualView;

import it.polimi.se2019.model.Effect;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;

import java.util.ArrayList;
import java.util.List;

public interface Selector {

    public void askGameSetUp();

    public void askPlayerSetUp();

    public void askFirstSpawnPosition(ArrayList<PowerUpCard> powerUpCards);

    public void askTurnAction(int actionNumber);

    public void askRunAroundPosition(ArrayList<Position> positions);

    public void askGrabStuffAction();

    public void askGrabStuffMove(ArrayList<Position> positions);

    public void askGrabStuffGrabWeapon(ArrayList<WeaponCard> toPickUp);

    public void askGrabStuffSwitchWeapon(ArrayList<WeaponCard> toPickUp, ArrayList<WeaponCard> toSwitch);

    public void askPowerUpToDiscard(ArrayList<PowerUpCard> toDiscard);

    public void askWhatReaload(ArrayList<WeaponCard> toReload);

    public void askSpawn(ArrayList<PowerUpCard> powerUpCards);

    public void askShootOrMove();

    public void askShootReloadMove();

    public void askWhatWep(ArrayList<WeaponCard> loadedCardInHand);

    public void askWhatEffect(ArrayList<Effect> possibleEffects);

    public void askEffectInputs(EffectInfoType inputType, List<Object> possibleInputs);

    public void askReconnectionNickname (ReconnectionEvent RE);

    public void askNickname();

}
