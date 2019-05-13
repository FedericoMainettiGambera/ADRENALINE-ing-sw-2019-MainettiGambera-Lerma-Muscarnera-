package it.polimi.se2019.virtualView.Socket;

import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.model.events.SelectorEvent;
import it.polimi.se2019.model.events.SelectorEventPowerUpCards;
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
            oos.writeObject(new SelectorEvent(SelectorEventTypes.askTurnAction));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askRunAroundPosition(ArrayList<Position> positions) {
        ObjectOutputStream oos = this.playerToAsk.getOos();
        try {
            oos.writeObject(new SelectorEvent(SelectorEventTypes.askRunAroundPosition));
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
            oos.writeObject(new SelectorEvent(SelectorEventTypes.askGrabStuffMove));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askGrabStuffGrabWeapon(ArrayList<WeaponCard> toPickUp) {
        ObjectOutputStream oos = this.playerToAsk.getOos();
        try {
            oos.writeObject(new SelectorEvent(SelectorEventTypes.askGrabStuffGrabWeapon));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askGrabStuffSwitchWeapon(ArrayList<WeaponCard> toPickUp, ArrayList<WeaponCard> toSwitch) {
        ObjectOutputStream oos = this.playerToAsk.getOos();
        try {
            oos.writeObject(new SelectorEvent(SelectorEventTypes.askGrabStuffSwitchWeapon));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askPowerUpToDiscard(ArrayList<PowerUpCard> toDiscard) {
        ObjectOutputStream oos = this.playerToAsk.getOos();
        try {
            oos.writeObject(new SelectorEvent(SelectorEventTypes.askPowerUpToDiscard));
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
            oos.writeObject(new SelectorEvent(SelectorEventTypes.askWhatReaload));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askSpawn(ArrayList<PowerUpCard> powerUpCards) {
        ObjectOutputStream oos = this.playerToAsk.getOos();
        try {
            oos.writeObject(new SelectorEvent(SelectorEventTypes.askSpawn));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
