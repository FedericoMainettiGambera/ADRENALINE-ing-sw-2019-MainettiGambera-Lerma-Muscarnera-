package it.polimi.se2019.view.selector;

import it.polimi.se2019.model.Effect;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.virtualView.Selector;

import java.util.ArrayList;
import java.util.List;

public class ViewSelector implements Selector {

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

    public Selector getCorrectSelector(){
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

    @Override
    public void askPlayerSetUp() {
        this.getCorrectSelector().askPlayerSetUp();
    }

    @Override
    public void askFirstSpawnPosition(ArrayList<PowerUpCard> powerUpCards) {
        this.getCorrectSelector().askFirstSpawnPosition(powerUpCards);
    }

    @Override
    public void askTurnAction(int actionNumber) {
        this.getCorrectSelector().askTurnAction(actionNumber);
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
    public void askGrabStuffGrabWeapon(ArrayList<WeaponCard> toPickUp) {
        this.getCorrectSelector().askGrabStuffGrabWeapon(toPickUp);
    }

    @Override
    public void askGrabStuffSwitchWeapon(ArrayList<WeaponCard> toPickUp, ArrayList<WeaponCard> toSwitch) {
        this.getCorrectSelector().askGrabStuffSwitchWeapon(toPickUp, toSwitch);
    }

    @Override
    public void askPowerUpToDiscard(ArrayList<PowerUpCard> toDiscard) {
        this.getCorrectSelector().askPowerUpToDiscard(toDiscard);
    }

    @Override
    public void askWhatReaload(ArrayList<WeaponCard> toReload) {
        this.getCorrectSelector().askWhatReaload(toReload);
    }

    @Override
    public void askSpawn(ArrayList<PowerUpCard> powerUpCards) {
        this.getCorrectSelector().askSpawn(powerUpCards);
    }

    @Override
    public void askShootOrMove(){
      this.getCorrectSelector().askShootOrMove();
    }

    @Override
    public void askShootReloadMove() {
        this.getCorrectSelector().askShootReloadMove();
    }

    @Override
    public void askWhatWep(ArrayList<WeaponCard> loadedCardInHand) {
        this.getCorrectSelector().askWhatWep(loadedCardInHand);
    }

    @Override
    public void askWhatEffect(ArrayList<Effect> possibleEffects) {
        this.getCorrectSelector().askWhatEffect(possibleEffects);
    }

    @Override
    public void askEffectInputs(EffectInfoType inputType, List<Object> possibleInputs) {
        this.getCorrectSelector().askEffectInputs(inputType, possibleInputs);
    }
}
