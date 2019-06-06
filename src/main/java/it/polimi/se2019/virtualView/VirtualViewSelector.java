package it.polimi.se2019.virtualView;

import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.EffectInfoType;

import java.util.ArrayList;
import java.util.List;

public class VirtualViewSelector implements Selector {

    public void setPlayerToAsk(Player p){

    }

    @Override
    public void askGameSetUp() {

    }

    @Override
    public void askPlayerSetUp() {

    }

    @Override
    public void askFirstSpawnPosition(ArrayList<PowerUpCard> powerUpCards) {

    }

    @Override
    public void askTurnAction(int actionNumber) {

    }

    @Override
    public void askRunAroundPosition(ArrayList<Position> positions) {

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
    public void askSpawn(ArrayList<PowerUpCard> powerUpCards) {

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
    public void askEffectInputs(List<EffectInfoType> inputs) {

    }

}
