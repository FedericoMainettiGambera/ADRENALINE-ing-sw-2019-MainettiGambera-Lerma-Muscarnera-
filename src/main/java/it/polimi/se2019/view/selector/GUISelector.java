package it.polimi.se2019.view.selector;

import it.polimi.se2019.model.Effect;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPaymentInformation;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPlayers;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPositions;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPowerUpCards;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventNickname;
import it.polimi.se2019.networkHandler.Socket.SocketNetworkHandler;
import it.polimi.se2019.view.components.*;
import it.polimi.se2019.view.outputHandler.OutputHandlerGate;
import it.polimi.se2019.virtualView.Selector;


import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class GUISelector implements SelectorV {

    private String networkConnection;
    private int actionNumber;
    private boolean canUsePowerUp;
    private boolean canUseBot;

    public GUISelector(String networkConnection){
        this.networkConnection = networkConnection;
    }

    @Override
    public void askGameSetUp(boolean canBot) {
        //ask map
        //ask FF active or not
        //if(canBot) ask bot active or not
        //ask number of starting skulls
        //send ViewControllerEventGameSetUp with correct informations

        //to do this stuffs you should use a Thread that modify the GUI, but the only way to modify the GUI is from the JavaFx Application Thread
        // so you should public static void runLater(Runnable runnable) !!! read this and try it !!
        //Run the specified Runnable on the JavaFX Application Thread at some unspecified time in the future. This method, which may be called from any thread,
        // will post the Runnable to an event queue and then return immediately to the caller. The Runnables are executed in the order they are posted. A runnable
        // passed into the runLater method will be executed before any Runnable passed into a subsequent call to runLater. If this method is called after the JavaFX
        // runtime has been shutdown, the call will be ignored: the Runnable will not be executed and no exception will be thrown.
        //NOTE: applications should avoid flooding JavaFX with too many pending Runnables. Otherwise, the application may become unresponsive. Applications are
        // encouraged to batch up multiple operations into fewer runLater calls. Additionally, long-running operations should be done on a background thread where
        // possible, freeing up the JavaFX Application Thread for GUI operations.
    }

    @Deprecated
    @Override
    public void askPlayerSetUp() {

    }

    @Override
    public void askFirstSpawnPosition(ArrayList<PowerUpCardV> powerUpCards, boolean spawnBot) {

    }

    @Override
    public void askTurnAction(int actionNumber, boolean canUsePowerUp, boolean canUseBot) {

        this.actionNumber = actionNumber;
        this.canUsePowerUp = canUsePowerUp;
        this.canUseBot = canUseBot;
    }

    @Override
    public void askBotMove(SelectorEventPositions SEPositions) {

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

    private boolean nicknameIsAvailable = true;
    @Override
    public void askNickname() {
        if(nicknameIsAvailable) {
            ViewSelector.sendToServer(new ViewControllerEventNickname(ViewModelGate.getMe()));
            nicknameIsAvailable = false;
        }
        else{
            //TODO
            //  show a pop up that ask for a new nickname
        }
    }

    @Override
    public void askPaymentInformation(SelectorEventPaymentInformation SEPaymentInformormation) {

    }

    @Override
    public void askPowerUpToUse(SelectorEventPowerUpCards powerUpCards) {

    }

    @Override
    public void wantToUsePowerUpOrNot() {

    }

    @Override
    public void askBotShoot(SelectorEventPlayers SEPlayers) {

    }

    @Override
    public void askTargetingScope(List<PowerUpCardV> listOfTargetingScopeV, List<Object> possiblePaymentsV, List<PlayerV> damagedPlayersV) {

    }

    @Override
    public void askTagBackGranade(List<PowerUpCardV> listOfTagBackGranade) {

    }
}
