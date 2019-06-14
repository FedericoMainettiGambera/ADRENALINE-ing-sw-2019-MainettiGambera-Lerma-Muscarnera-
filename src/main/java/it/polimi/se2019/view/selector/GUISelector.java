package it.polimi.se2019.view.selector;

import it.polimi.se2019.model.Effect;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;
import it.polimi.se2019.view.components.EffectV;
import it.polimi.se2019.view.components.PowerUpCardV;
import it.polimi.se2019.view.components.WeaponCardV;
import it.polimi.se2019.virtualView.Selector;
import it.polimi.se2019.virtualView.SelectorV;

import java.util.ArrayList;
import java.util.List;

public class GUISelector implements SelectorV {

    private String networkConnection;

    public GUISelector(String networkConnection){
        this.networkConnection = networkConnection;
    }


    @Override
    public void askGameSetUp() {

    }

    @Deprecated
    @Override
    public void askPlayerSetUp() {

    }

    @Override
    public void askFirstSpawnPosition(ArrayList<PowerUpCardV> powerUpCards) {

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
    public void askGrabStuffGrabWeapon(ArrayList<WeaponCardV> toPickUp) {

    }

    @Override
    public void askGrabStuffSwitchWeapon(ArrayList<WeaponCardV> toPickUp, ArrayList<WeaponCardV> toSwitch) {

    }

    @Override
    public void askPowerUpToDiscard(ArrayList<PowerUpCardV> toDiscard) {

    }

    @Override
    public void askWhatReaload(ArrayList<WeaponCardV> toReload) {

    }

    @Override
    public void askSpawn(ArrayList<PowerUpCardV> powerUpCards) {

    }

    @Override
    public void askShootOrMove() {

    }

    @Deprecated
    @Override
    public void askShootReloadMove() {

    }

    @Override
    public void askWhatWep(ArrayList<WeaponCardV> loadedCardInHand) {

    }

    @Override
    public void askWhatEffect(ArrayList<EffectV> possibleEffects) {

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
}
