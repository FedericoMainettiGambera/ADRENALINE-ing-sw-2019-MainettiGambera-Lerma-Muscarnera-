package it.polimi.se2019.virtualView.Socket;

import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.model.events.selectorEvents.*;
import it.polimi.se2019.virtualView.Selector;
import it.polimi.se2019.virtualView.VirtualViewSelector;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class VirtualViewSelectorSocket extends VirtualViewSelector implements Selector {

    private Player playerToAsk;

    public void setPlayerToAsk(it.polimi.se2019.model.Player playerToAsk){
        this.playerToAsk = playerToAsk;
    }

    @Override
    public  void askGameSetUp() {
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEvent(SelectorEventTypes.askGameSetUp));
    }

    @Override
    public void askPlayerSetUp() {
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEvent(SelectorEventTypes.askPlayerSetUp));
    }

    @Override
    public void askFirstSpawnPosition(ArrayList<PowerUpCard> powerUpCards) {
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventPowerUpCards(SelectorEventTypes.askFirstSpawnPosition, powerUpCards));
    }

    @Override
    public void askTurnAction(int actionNumber) {
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventInt(SelectorEventTypes.askTurnAction, actionNumber));
    }

    @Override
    public void askRunAroundPosition(ArrayList<Position> positions) {
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventPositions(SelectorEventTypes.askRunAroundPosition, positions));
    }

    @Override
    public void askGrabStuffAction() {
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEvent(SelectorEventTypes.askGrabStuffAction));
    }

    @Override
    public void askGrabStuffMove(ArrayList<Position> positions) {
        SocketVirtualView.sendToClient(playerToAsk, (new SelectorEventPositions(SelectorEventTypes.askGrabStuffMove, positions)));
    }

    @Override
    public void askGrabStuffGrabWeapon(ArrayList<WeaponCard> toPickUp) {
        SelectorEventWeaponCards SE = new SelectorEventWeaponCards(SelectorEventTypes.askGrabStuffGrabWeapon, toPickUp);
        SocketVirtualView.sendToClient(playerToAsk, SE);
    }

    @Override
    public void askGrabStuffSwitchWeapon(ArrayList<WeaponCard> toPickUp, ArrayList<WeaponCard> toSwitch) {
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventDoubleWeaponCards(SelectorEventTypes.askGrabStuffSwitchWeapon, toPickUp, toSwitch));
    }

    @Override
    public void askPowerUpToDiscard(ArrayList<PowerUpCard> toDiscard) {
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventPowerUpCards(SelectorEventTypes.askPowerUpToDiscard,toDiscard));
    }

    @Override
    public void askWhatReaload(ArrayList<WeaponCard> toReload) {
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventWeaponCards(SelectorEventTypes.askWhatReaload,toReload));
    }

    @Override
    public void askSpawn(ArrayList<PowerUpCard> powerUpCards) {
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventPowerUpCards(SelectorEventTypes.askSpawn, powerUpCards));
    }

    @Override
    public void askShootOrMove(){
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEvent(SelectorEventTypes.askShootOrMove));
    }

    @Override
    public void askShootReloadMove(){
        ObjectOutputStream oos = this.playerToAsk.getOos();
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEvent(SelectorEventTypes.askShootReloadMove));
    }

    @Override
    public void askWhatWep(ArrayList<WeaponCard> loadedCardInHand) {
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventWeaponCards(SelectorEventTypes.askWhatWep, loadedCardInHand));
    }

    @Override
    public void askWhatEffect(ArrayList<Effect> possibleEffects) {
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventEffect(SelectorEventTypes.askWhatEffect, possibleEffects));
    }

    @Override
    public void askEffectInputs(List<EffectInfoType> effectInputs){
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventEffectInputs(SelectorEventTypes.askEffectInputs, effectInputs));
    }
}
