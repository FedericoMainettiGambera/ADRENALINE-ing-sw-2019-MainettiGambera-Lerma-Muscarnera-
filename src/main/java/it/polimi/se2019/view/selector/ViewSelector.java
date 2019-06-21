package it.polimi.se2019.view.selector;

import it.polimi.se2019.model.Effect;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPaymentInformation;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPowerUpCards;
import it.polimi.se2019.view.components.EffectV;
import it.polimi.se2019.view.components.PowerUpCardV;
import it.polimi.se2019.view.components.WeaponCardV;
import it.polimi.se2019.virtualView.Selector;
import it.polimi.se2019.virtualView.SelectorV;

import java.util.ArrayList;
import java.util.List;

public class ViewSelector implements SelectorV {

    private String networkConnection;

    private String userInterface;

    private it.polimi.se2019.view.selector.CLISelector CLISelector;

    private it.polimi.se2019.view.selector.GUISelector GUISelector;

    public ViewSelector(String networConnection, String userInterface){
        this.networkConnection = networConnection;
        this.userInterface = userInterface;
        this.CLISelector = new CLISelector(networkConnection);
        this.GUISelector = new GUISelector(networkConnection);
    }

    public SelectorV getCorrectSelector(){
        if(this.userInterface.equalsIgnoreCase("cLI")){
            return this.CLISelector;
        }
        else{
            return this.GUISelector;
        }
    }

    @Override
    public void askGameSetUp() {
        this.getCorrectSelector().askGameSetUp();
    }

    @Deprecated
    @Override
    public void askPlayerSetUp() {
        this.getCorrectSelector().askPlayerSetUp();
    }

    @Override
    public void askFirstSpawnPosition(ArrayList<PowerUpCardV> powerUpCards) {
        this.getCorrectSelector().askFirstSpawnPosition(powerUpCards);
    }

    @Override
    public void askTurnAction(int actionNumber, boolean canUsePowerUp) {
       this.getCorrectSelector().askTurnAction(actionNumber, canUsePowerUp);
    }

    @Override
    public void askRunAroundPosition(ArrayList<Position> positions) {
        this.getCorrectSelector().askRunAroundPosition(positions);
    }

    @Override
    public void askGrabStuffAction() {
        this.getCorrectSelector().askGrabStuffAction();
    }

    @Override
    public void askGrabStuffMove(ArrayList<Position> positions) {
        this.getCorrectSelector().askGrabStuffMove(positions);
    }

    @Override
    public void askGrabStuffGrabWeapon(ArrayList<WeaponCardV> toPickUp) {
        this.getCorrectSelector().askGrabStuffGrabWeapon(toPickUp);
    }

    @Override
    public void askGrabStuffSwitchWeapon(ArrayList<WeaponCardV> toPickUp, ArrayList<WeaponCardV> toSwitch) {
        this.getCorrectSelector().askGrabStuffSwitchWeapon(toPickUp, toSwitch);
    }

    @Override
    public void askPowerUpToDiscard(ArrayList<PowerUpCardV> toDiscard) {
        this.getCorrectSelector().askPowerUpToDiscard(toDiscard);
    }

    @Override
    public void askWhatReaload(ArrayList<WeaponCardV> toReload) {
        this.getCorrectSelector().askWhatReaload(toReload);
    }

    @Override
    public void askSpawn(ArrayList<PowerUpCardV> powerUpCards) {
        this.getCorrectSelector().askSpawn(powerUpCards);
    }

    @Override
    public void askShootOrMove(){
      this.getCorrectSelector().askShootOrMove();
    }

    @Deprecated
    @Override
    public void askShootReloadMove() {
        this.getCorrectSelector().askShootReloadMove();
    }

    @Override
    public void askWhatWep(ArrayList<WeaponCardV> loadedCardInHand) {
        this.getCorrectSelector().askWhatWep(loadedCardInHand);
    }

    @Override
    public void askWhatEffect(ArrayList<EffectV> possibleEffects) {
        this.getCorrectSelector().askWhatEffect(possibleEffects);
    }

    @Override
    public void askEffectInputs(EffectInfoType inputType, List<Object> possibleInputs) {
        this.getCorrectSelector().askEffectInputs(inputType, possibleInputs);
    }

    @Override
    public void askReconnectionNickname(ReconnectionEvent RE) {
        this.getCorrectSelector().askReconnectionNickname(RE);
    }

    @Override
    public void askNickname() {
        this.getCorrectSelector().askNickname();
    }

    @Override
    public void askPaymentInformation(SelectorEventPaymentInformation SEPaymentInformormation) {
        this.getCorrectSelector().askPaymentInformation(SEPaymentInformormation);
    }

    @Override
    public void askPowerUpToUse(SelectorEventPowerUpCards powerUpCards) {
        this.getCorrectSelector().askPowerUpToUse(powerUpCards);
    }

    @Override
    public void wantToUsePowerUpOrNot() {
        this.getCorrectSelector().wantToUsePowerUpOrNot();
    }


}
