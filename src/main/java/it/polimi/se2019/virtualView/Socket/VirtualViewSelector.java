package it.polimi.se2019.virtualView.Socket;

import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.model.events.SelectorEvent;
import it.polimi.se2019.virtualView.Selector;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class VirtualViewSelector implements Selector {


    private Player playerToAsk;

    public void setPlayerToAsk(it.polimi.se2019.model.Player playerToAsk){
        this.playerToAsk = playerToAsk;
    }

    @Override
    public  void askGameSetUp() {
        ObjectOutputStream oos = this.playerToAsk.getOos();
        try {
            oos.writeObject(new SelectorEvent(SelectorEventTypes.gameSetUp));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public void askGrabStuffGrabAndDiscardPowerUp(ArrayList<PowerUpCard> toDiscard) {

    }

    @Override
    public void askIfReload() {

    }

    @Override
    public void askWhatReaload(ArrayList<WeaponCard> toReload) {

    }

    @Override
    public void askSpawn(ArrayList<PowerUpCard> powerUpCards) {

    }
}
