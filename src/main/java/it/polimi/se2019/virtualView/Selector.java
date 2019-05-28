package it.polimi.se2019.virtualView;

import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.WeaponCard;

import java.util.ArrayList;

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

    public void askIfReload();

    public void askWhatReaload(ArrayList<WeaponCard> toReload);

    public void askSpawn(ArrayList<PowerUpCard> powerUpCards);

    public void askShootOrMove();

}
