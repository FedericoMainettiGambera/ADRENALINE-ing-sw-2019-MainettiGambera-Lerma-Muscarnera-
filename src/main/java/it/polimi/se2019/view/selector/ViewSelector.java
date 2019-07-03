package it.polimi.se2019.view.selector;

import it.polimi.se2019.model.Position;

import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPaymentInformation;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPlayers;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPositions;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPowerUpCards;
import it.polimi.se2019.networkHandler.RMIREDO.RmiNetworkHandler;
import it.polimi.se2019.networkHandler.Socket.SocketNetworkHandler;
import it.polimi.se2019.view.components.EffectV;
import it.polimi.se2019.view.components.PlayerV;
import it.polimi.se2019.view.components.PowerUpCardV;
import it.polimi.se2019.view.components.WeaponCardV;
import it.polimi.se2019.view.outputHandler.OutputHandlerGate;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public class ViewSelector implements SelectorV {

    private static String networkConnection;

    private String userInterface;

    private it.polimi.se2019.view.selector.CLISelector CLISelector;

    private it.polimi.se2019.view.selector.GUISelector GUISelector;

    public ViewSelector(String networConnection, String userInterface){
        networkConnection = networConnection;
        this.userInterface = userInterface;
        this.CLISelector = new CLISelector(networkConnection);
        this.GUISelector = new GUISelector(networkConnection);
    }

    public SelectorV getCorrectSelector(){
        if(this.userInterface.equalsIgnoreCase("CLI")){
            return this.CLISelector;
        }
        else{
            return this.GUISelector;
        }
    }

    public static void sendToServer(Object o){
        if(networkConnection.equals("SOCKET")){
            try {
                SocketNetworkHandler.getOos().writeObject(o);
            } catch (IOException e) {
                OutputHandlerGate.getCorrectOutputHandler(OutputHandlerGate.getUserIterface()).cantReachServer();
            }
        }
        else{
            try {
                RmiNetworkHandler.getServer().send(o);
            } catch (RemoteException e) {
                OutputHandlerGate.getCorrectOutputHandler(OutputHandlerGate.getUserIterface()).cantReachServer();
            }
        }
    }

    @Override
    public void askGameSetUp(boolean canBot) {
        this.getCorrectSelector().askGameSetUp(canBot);
    }

    @Deprecated
    @Override
    public void askPlayerSetUp() {
        this.getCorrectSelector().askPlayerSetUp();
    }

    @Override
    public void askFirstSpawnPosition(List<PowerUpCardV> powerUpCards, boolean spawnBot) {
        this.getCorrectSelector().askFirstSpawnPosition(powerUpCards, spawnBot);
    }

    @Override
    public void askTurnAction(int actionNumber, boolean canUsePowerUp, boolean canUseBot) {
        this.getCorrectSelector().askTurnAction(actionNumber, canUsePowerUp, canUseBot);
    }

    @Override
    public void askBotMove(SelectorEventPositions selectorEventPositions) {
        this.getCorrectSelector().askBotMove(selectorEventPositions);
    }

    @Override
    public void askRunAroundPosition(List<Position> positions) {
        this.getCorrectSelector().askRunAroundPosition(positions);
    }

    @Override
    public void askGrabStuffAction() {
        this.getCorrectSelector().askGrabStuffAction();
    }

    @Override
    public void askGrabStuffMove(List<Position> positions) {
        this.getCorrectSelector().askGrabStuffMove(positions);
    }

    @Override
    public void askGrabStuffGrabWeapon(List<WeaponCardV> toPickUp) {
        this.getCorrectSelector().askGrabStuffGrabWeapon(toPickUp);
    }

    @Override
    public void askGrabStuffSwitchWeapon(List<WeaponCardV> toPickUp, List<WeaponCardV> toSwitch) {
        this.getCorrectSelector().askGrabStuffSwitchWeapon(toPickUp, toSwitch);
    }

    @Override
    public void askPowerUpToDiscard(List<PowerUpCardV> toDiscard) {
        this.getCorrectSelector().askPowerUpToDiscard(toDiscard);
    }

    @Override
    public void askWhatReaload(List<WeaponCardV> toReload) {
        this.getCorrectSelector().askWhatReaload(toReload);
    }

    @Override
    public void askSpawn(List<PowerUpCardV> powerUpCards) {
        this.getCorrectSelector().askSpawn(powerUpCards);
    }

    @Override
    public void askShootOrMove(){
        this.getCorrectSelector().askShootOrMove();
    }

    /**@deprecated*/
    @Deprecated
    @Override
    public void askShootReloadMove() {
        this.getCorrectSelector().askShootReloadMove();
    }

    @Override
    public void askWhatWep(List<WeaponCardV> loadedCardInHand) {
        this.getCorrectSelector().askWhatWep(loadedCardInHand);
    }

    @Override
    public void askWhatEffect(List<EffectV> possibleEffects) {
        this.getCorrectSelector().askWhatEffect(possibleEffects);
    }

    @Override
    public void askEffectInputs(EffectInfoType inputType, List<Object> possibleInputs) {
        this.getCorrectSelector().askEffectInputs(inputType, possibleInputs);
    }

    @Override
    public void askReconnectionNickname(ReconnectionEvent reconnectionEvent) {
        this.getCorrectSelector().askReconnectionNickname(reconnectionEvent);
    }

    @Override
    public void askNickname() {
        this.getCorrectSelector().askNickname();
    }

    @Override
    public void askPaymentInformation(SelectorEventPaymentInformation selectorEventPaymentInformation) {
        this.getCorrectSelector().askPaymentInformation(selectorEventPaymentInformation);
    }

    @Override
    public void askPowerUpToUse(SelectorEventPowerUpCards powerUpCards) {
        this.getCorrectSelector().askPowerUpToUse(powerUpCards);
    }

    @Override
    public void wantToUsePowerUpOrNot() {
        this.getCorrectSelector().wantToUsePowerUpOrNot();
    }

    @Override
    public void askBotShoot(SelectorEventPlayers selectorEventPlayers) {this.getCorrectSelector().askBotShoot(selectorEventPlayers);}

    @Override
    public void askTargetingScope(List<PowerUpCardV> listOfTargetingScopeV, List<Object> possiblePaymentsV, List<PlayerV> damagedPlayersV) {
        this.getCorrectSelector().askTargetingScope(listOfTargetingScopeV, possiblePaymentsV, damagedPlayersV);
    }

    @Override
    public void askTagBackGranade(List<PowerUpCardV> listOfTagBackGranade) {
        this.getCorrectSelector().askTagBackGranade(listOfTagBackGranade);
    }


}
