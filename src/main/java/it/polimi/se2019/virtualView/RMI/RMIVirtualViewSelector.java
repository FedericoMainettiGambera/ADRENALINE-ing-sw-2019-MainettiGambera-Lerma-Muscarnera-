package it.polimi.se2019.virtualView.RMI;

import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.model.events.selectorEvents.*;
import it.polimi.se2019.virtualView.Selector;
import it.polimi.se2019.virtualView.VirtualViewSelector;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class RMIVirtualViewSelector extends VirtualViewSelector implements Selector{


    private Player playerToAsk;

    public void setPlayerToAsk(it.polimi.se2019.model.Player playerToAsk){
        this.playerToAsk = playerToAsk;
    }



    @Override
    public  void askGameSetUp(){
        try {
            SelectorEvent SE = new SelectorEvent(SelectorEventTypes.askGameSetUp);
            System.out.println(playerToAsk.getRmiIdentifier());
            playerToAsk.getRmiInterface().getClient(playerToAsk.getRmiIdentifier()).sendToClient(playerToAsk.getRmiIdentifier(), SE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askPlayerSetUp() {
        try {
            SelectorEvent SE= new SelectorEvent(SelectorEventTypes.askPlayerSetUp);
            playerToAsk.getRmiInterface().getClient(playerToAsk.getRmiIdentifier()).sendToClient(playerToAsk.getRmiIdentifier(),SE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askFirstSpawnPosition(ArrayList<PowerUpCard> powerUpCards) {
        try {
            playerToAsk.getRmiInterface().getClient(playerToAsk.getRmiIdentifier()).sendToClient(playerToAsk.getRmiIdentifier(),new SelectorEventPowerUpCards(SelectorEventTypes.askFirstSpawnPosition, powerUpCards));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askTurnAction(int actionNumber) {
        try {
            playerToAsk.getRmiInterface().getClient(playerToAsk.getRmiIdentifier()).sendToClient(playerToAsk.getRmiIdentifier(),new SelectorEventInt(SelectorEventTypes.askTurnAction, actionNumber));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askRunAroundPosition(ArrayList<Position> positions) {
        try {
          playerToAsk.getRmiInterface().getClient(playerToAsk.getRmiIdentifier()).sendToClient(playerToAsk.getRmiIdentifier(),new SelectorEventPositions(SelectorEventTypes.askRunAroundPosition, positions));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askGrabStuffAction() {
        try {
            playerToAsk.getRmiInterface().getClient(playerToAsk.getRmiIdentifier()).sendToClient(playerToAsk.getRmiIdentifier(),new SelectorEvent(SelectorEventTypes.askGrabStuffAction));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askGrabStuffMove(ArrayList<Position> positions) {
        try {
            playerToAsk.getRmiInterface().getClient(playerToAsk.getRmiIdentifier()).sendToClient(playerToAsk.getRmiIdentifier(),new SelectorEventPositions(SelectorEventTypes.askGrabStuffMove, positions));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askGrabStuffGrabWeapon(ArrayList<WeaponCard> toPickUp) {
        SelectorEventWeaponCards SE = new SelectorEventWeaponCards(SelectorEventTypes.askGrabStuffGrabWeapon, toPickUp);
        try {
           playerToAsk.getRmiInterface().getClient(playerToAsk.getRmiIdentifier()).sendToClient(playerToAsk.getRmiIdentifier(),SE);
            //oos.reset(); //IMPORTANT FOR CACHE PROBLEM
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askGrabStuffSwitchWeapon(ArrayList<WeaponCard> toPickUp, ArrayList<WeaponCard> toSwitch) {
        try {
            playerToAsk.getRmiInterface().getClient(playerToAsk.getRmiIdentifier()).sendToClient(playerToAsk.getRmiIdentifier(),new SelectorEventDoubleWeaponCards(SelectorEventTypes.askGrabStuffSwitchWeapon, toPickUp, toSwitch));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askPowerUpToDiscard(ArrayList<PowerUpCard> toDiscard) {
        try {
            playerToAsk.getRmiInterface().getClient(playerToAsk.getRmiIdentifier()).sendToClient(playerToAsk.getRmiIdentifier(),new SelectorEventPowerUpCards(SelectorEventTypes.askPowerUpToDiscard,toDiscard));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askWhatReaload(ArrayList<WeaponCard> toReload) {

        try {
            playerToAsk.getRmiInterface().getClient(playerToAsk.getRmiIdentifier()).sendToClient(playerToAsk.getRmiIdentifier(),new SelectorEventWeaponCards(SelectorEventTypes.askWhatReaload,toReload));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askSpawn(ArrayList<PowerUpCard> powerUpCards) {
        try {
            playerToAsk.getRmiInterface().getClient(playerToAsk.getRmiIdentifier()).sendToClient(playerToAsk.getRmiIdentifier(), new SelectorEventPowerUpCards(SelectorEventTypes.askSpawn, powerUpCards));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void askShootOrMove(){

        try{
            playerToAsk.getRmiInterface().getClient(playerToAsk.getRmiIdentifier()).sendToClient(playerToAsk.getRmiIdentifier(), new SelectorEvent(SelectorEventTypes.askShootOrMove));

        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void askShootReloadMove(){

        try{
            playerToAsk.getRmiInterface().getClient(playerToAsk.getRmiIdentifier()).sendToClient(playerToAsk.getRmiIdentifier(), new SelectorEvent(SelectorEventTypes.askShootReloadMove));

        }catch(IOException e)
        {
            e.printStackTrace();
        }

    }
}