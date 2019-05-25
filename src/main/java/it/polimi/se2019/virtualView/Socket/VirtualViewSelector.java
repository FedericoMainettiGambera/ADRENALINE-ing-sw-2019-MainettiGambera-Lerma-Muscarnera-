package it.polimi.se2019.virtualView.Socket;

import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.model.events.selectorEvents.*;
import it.polimi.se2019.virtualView.Selector;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.se2019.model.enumerations.SelectorEventTypes.askGrabStuffGrabWeapon;

public class VirtualViewSelector implements Selector {


    private Player playerToAsk;

    public void setPlayerToAsk(it.polimi.se2019.model.Player playerToAsk){
        this.playerToAsk = playerToAsk;
    }

    @Override
    public  void askGameSetUp() {
        ObjectOutputStream oos = this.playerToAsk.getOos();
        try {
            SelectorEvent SE = new SelectorEvent(SelectorEventTypes.askGameSetUp);
            oos.writeObject(SE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askPlayerSetUp() {
        ObjectOutputStream oos = this.playerToAsk.getOos();
        try {
            oos.writeObject(new SelectorEvent(SelectorEventTypes.askPlayerSetUp));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askFirstSpawnPosition(ArrayList<PowerUpCard> powerUpCards) {
        ObjectOutputStream oos = this.playerToAsk.getOos();
        try {
            oos.writeObject(new SelectorEventPowerUpCards(SelectorEventTypes.askFirstSpawnPosition, powerUpCards));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askTurnAction(int actionNumber) {
        ObjectOutputStream oos = this.playerToAsk.getOos();
        try {
            oos.writeObject(new SelectorEventInt(SelectorEventTypes.askTurnAction, actionNumber));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askRunAroundPosition(ArrayList<Position> positions) {
        ObjectOutputStream oos = this.playerToAsk.getOos();
        try {
            oos.writeObject(new SelectorEventPositions(SelectorEventTypes.askRunAroundPosition, positions));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askGrabStuffAction() {
        ObjectOutputStream oos = this.playerToAsk.getOos();
        try {
            oos.writeObject(new SelectorEvent(SelectorEventTypes.askGrabStuffAction));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askGrabStuffMove(ArrayList<Position> positions) {
        ObjectOutputStream oos = this.playerToAsk.getOos();
        try {
            oos.writeObject(new SelectorEventPositions(SelectorEventTypes.askGrabStuffMove, positions));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askGrabStuffGrabWeapon(ArrayList<WeaponCard> toPickUp) {
        ObjectOutputStream oos = this.playerToAsk.getOos();
        SelectorEventWeaponCards SE = new SelectorEventWeaponCards(SelectorEventTypes.askGrabStuffGrabWeapon, toPickUp);
        try {
            oos.writeObject(SE);
            oos.reset(); //IMPORTANT FOR CACHE PROBLEM
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askGrabStuffSwitchWeapon(ArrayList<WeaponCard> toPickUp, ArrayList<WeaponCard> toSwitch) {
        ObjectOutputStream oos = this.playerToAsk.getOos();
        try {
            oos.writeObject(new SelectorEventDoubleWeaponCards(SelectorEventTypes.askGrabStuffSwitchWeapon, toPickUp, toSwitch));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askPowerUpToDiscard(ArrayList<PowerUpCard> toDiscard) {
        ObjectOutputStream oos = this.playerToAsk.getOos();
        try {
            oos.writeObject(new SelectorEventPowerUpCards(SelectorEventTypes.askPowerUpToDiscard,toDiscard));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askIfReload() {
        ObjectOutputStream oos = this.playerToAsk.getOos();
        try {
            oos.writeObject(new SelectorEvent(SelectorEventTypes.askIfReload));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askWhatReaload(ArrayList<WeaponCard> toReload) {
        ObjectOutputStream oos = this.playerToAsk.getOos();
        try {
            oos.writeObject(new SelectorEventWeaponCards(SelectorEventTypes.askWhatReaload,toReload));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askSpawn(ArrayList<PowerUpCard> powerUpCards) {
        ObjectOutputStream oos = this.playerToAsk.getOos();
        try {
            oos.writeObject(new SelectorEventPowerUpCards(SelectorEventTypes.askSpawn, powerUpCards));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
