/**package it.polimi.se2019.virtualView.RMI;

import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.CardinalPoint;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;
import it.polimi.se2019.model.events.selectorEvents.*;
import it.polimi.se2019.view.components.*;
import it.polimi.se2019.virtualView.Selector;
import it.polimi.se2019.virtualView.VirtualViewSelector;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.se2019.model.enumerations.CardinalPoint.east;
import static it.polimi.se2019.model.enumerations.CardinalPoint.south;

public class RMIVirtualViewSelector extends VirtualViewSelector implements Selector{


    private Player playerToAsk;

    public void setPlayerToAsk(it.polimi.se2019.model.Player playerToAsk){
        this.playerToAsk = playerToAsk;
    }

    public Player getPlayerToAsk() {
        return playerToAsk;
    }

    @Override
    public  void askGameSetUp(boolean canBot){
        try {
            SelectorEvent SE = new SelectorEvent(SelectorEventTypes.askGameSetUp);
            playerToAsk.getRmiInterface().getClient(playerToAsk.getRmiIdentifier()).sendToClient(playerToAsk.getRmiIdentifier(), SE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
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
    public void askFirstSpawnPosition(ArrayList<PowerUpCard> powerUpCards, boolean spawnBot) {
        ArrayList<PowerUpCardV> powerUpCardsV= new ArrayList<>();
        for (PowerUpCard c : powerUpCards) {
            powerUpCardsV.add(c.buildPowerUpCardV());
        }
        try {
            playerToAsk.getRmiInterface().getClient(playerToAsk.getRmiIdentifier()).sendToClient(playerToAsk.getRmiIdentifier(),new SelectorEventPowerUpCards(SelectorEventTypes.askFirstSpawnPosition, powerUpCardsV));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askTurnAction(int actionNumber, boolean canUsePowerUp, boolean canUseBot) {
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
    public void askBotMove(ArrayList<Position> positions) {

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
        ArrayList<WeaponCardV> weaponCardsV= new ArrayList<>();
        for (WeaponCard c : toPickUp) {
            weaponCardsV.add(c.buildWeapondCardV());
        }
        SelectorEventWeaponCards SE = new SelectorEventWeaponCards(SelectorEventTypes.askGrabStuffGrabWeapon, weaponCardsV);
        try {
           playerToAsk.getRmiInterface().getClient(playerToAsk.getRmiIdentifier()).sendToClient(playerToAsk.getRmiIdentifier(),SE);
            //oos.reset(); //IMPORTANT FOR CACHE PROBLEM
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askGrabStuffSwitchWeapon(ArrayList<WeaponCard> toPickUp, ArrayList<WeaponCard> toSwitch) {
        ArrayList<WeaponCardV> weaponCardsVtoSwitch= new ArrayList<>();
        for (WeaponCard c : toSwitch) {
            weaponCardsVtoSwitch.add(c.buildWeapondCardV());
        }
        ArrayList<WeaponCardV> weaponCardsVtoPickUp= new ArrayList<>();
        for (WeaponCard c : toPickUp) {
            weaponCardsVtoPickUp.add(c.buildWeapondCardV());
        }
        try {
            playerToAsk.getRmiInterface().getClient(playerToAsk.getRmiIdentifier()).sendToClient(playerToAsk.getRmiIdentifier(),new SelectorEventDoubleWeaponCards(SelectorEventTypes.askGrabStuffSwitchWeapon, weaponCardsVtoPickUp, weaponCardsVtoSwitch));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askPowerUpToDiscard(ArrayList<PowerUpCard> toDiscard) {
        ArrayList<PowerUpCardV> powerUpCardsV= new ArrayList<>();
        for (PowerUpCard c : toDiscard) {
            powerUpCardsV.add(c.buildPowerUpCardV());
        }
        try {
            playerToAsk.getRmiInterface().getClient(playerToAsk.getRmiIdentifier()).sendToClient(playerToAsk.getRmiIdentifier(),new SelectorEventPowerUpCards(SelectorEventTypes.askPowerUpToDiscard,powerUpCardsV));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askWhatReaload(ArrayList<WeaponCard> toReload) {
        ArrayList<WeaponCardV> weaponCardsV= new ArrayList<>();
        for (WeaponCard c : toReload) {
            weaponCardsV.add(c.buildWeapondCardV());
        }
        try {
            playerToAsk.getRmiInterface().getClient(playerToAsk.getRmiIdentifier()).sendToClient(playerToAsk.getRmiIdentifier(),new SelectorEventWeaponCards(SelectorEventTypes.askWhatReaload,weaponCardsV));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askSpawn(ArrayList<PowerUpCardV> powerUpCards) {

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

    @Deprecated
    @Override
    public void askShootReloadMove(){

        try{
            playerToAsk.getRmiInterface().getClient(playerToAsk.getRmiIdentifier()).sendToClient(playerToAsk.getRmiIdentifier(), new SelectorEvent(SelectorEventTypes.askShootReloadMove));

        }catch(IOException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void askWhatWep(ArrayList<WeaponCard> loadedCardInHand) {
        ArrayList<WeaponCardV> weaponCardsV= new ArrayList<>();
        for (WeaponCard c : loadedCardInHand) {
            weaponCardsV.add(c.buildWeapondCardV());
        }
        try{
            playerToAsk.getRmiInterface().getClient(playerToAsk.getRmiIdentifier()).sendToClient(playerToAsk.getRmiIdentifier(), new SelectorEventWeaponCards(SelectorEventTypes.askWhatWep, weaponCardsV));

        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void askWhatEffect(ArrayList<Effect> possibleEffects) {
        ArrayList<EffectV> effectsV = new ArrayList<>();
        for (Effect e : possibleEffects) {
            effectsV.add(e.buildEffectV());
        }
        try{
            playerToAsk.getRmiInterface().getClient(playerToAsk.getRmiIdentifier()).sendToClient(playerToAsk.getRmiIdentifier(), new SelectorEventEffect(SelectorEventTypes.askWhatEffect, effectsV));

        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void askEffectInputs(EffectInfoType inputType, List<Object> possibleInputs) {
        List<Object> possibleInputsV = new ArrayList<>();
        if(possibleInputs.get(0).getClass().toString().contains("Player")){
            for (Object p: possibleInputs) {
                possibleInputsV.add(((Player)p).buildPlayerV());
            }
        }
        else{
            for (Object s: possibleInputs) {
                if(s.getClass().toString().contains("NormalSquare")) {
                    possibleInputsV.add(((NormalSquare)s).buildNormalSquareV((NormalSquare)s));
                }
                else{
                    possibleInputsV.add(((SpawnPointSquare)s).builSpawnPointSquareV((SpawnPointSquare)s));
                }
            }
        }
        try{
            playerToAsk.getRmiInterface().getClient(playerToAsk.getRmiIdentifier()).sendToClient(playerToAsk.getRmiIdentifier(), new SelectorEventEffectInputs(inputType,possibleInputsV));

        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void askReconnectionNickname(ReconnectionEvent RE) {
        //left empty
    }

    @Override
    public void askNickname() {
        //left empty
    }

    @Override
    public void askPaymentInformation(SelectorEventPaymentInformation SEPaymentInformation) {
        try{
            playerToAsk.getRmiInterface().getClient(playerToAsk.getRmiIdentifier()).sendToClient(playerToAsk.getRmiIdentifier(), SEPaymentInformation);

        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void askPowerUpToUse(List<PowerUpCardV> powerUpCards) {

    }

    @Override
    public void askWantToUsePowerUpOrNot() {

    }

    @Override
    public void askBotShoot(List<PlayerV> playerVList) {

    }

    @Override
    public void askTargetingScope(List<PowerUpCardV> listOfTargetingScopeV, List<Object> possiblePaymentsV, List<PlayerV> damagedPlayersV) {

    }

    @Override
    public void askTagBackGranade(List<PowerUpCardV> listOfTagBackGranadesV){

    }
}**/