package it.polimi.se2019.view.selector;

import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.virtualView.Selector;

import java.util.ArrayList;

public class ViewSelector implements Selector {

    private it.polimi.se2019.view.selector.CLISelector CLISelector = new CLISelector();

    private it.polimi.se2019.view.selector.GUISelector GUISelector = new GUISelector();


    @Override
    public void askGameSetUp() {
        /*TODO:
        if(il client è in modalità CLI){
            this.CLISelector.askGameSetUp();
        }
        else if(il client è in modalità GUI){
            this.GUISelector.askGameSetUp();
        }
         */
        //per il momento uso direttamente la CLI perchè non abbiamo ancora la grafica:
        this.CLISelector.askGameSetUp();
    }

    @Override
    public void askPlayerSetUp() {
        this.CLISelector.askPlayerSetUp();
    }

    @Override
    public void askFirstSpawnPosition(ArrayList<PowerUpCard> powerUpCards) {
        this.CLISelector.askFirstSpawnPosition(powerUpCards);
    }

    @Override
    public void askTurnAction(int actionNumber) {
        this.CLISelector.askTurnAction(actionNumber);
    }

    @Override
    public void askRunAroundPosition(ArrayList<Position> positions) {
        this.CLISelector.askRunAroundPosition(positions);
    }

    @Override
    public void askGrabStuffAction() {
        this.CLISelector.askGrabStuffAction();
    }

    @Override
    public void askGrabStuffMove(ArrayList<Position> positions) {
        this.CLISelector.askGrabStuffMove(positions);
    }

    @Override
    public void askGrabStuffGrabWeapon(ArrayList<WeaponCard> toPickUp) {
        this.CLISelector.askGrabStuffGrabWeapon(toPickUp);
    }

    @Override
    public void askGrabStuffSwitchWeapon(ArrayList<WeaponCard> toPickUp, ArrayList<WeaponCard> toSwitch) {
        this.CLISelector.askGrabStuffSwitchWeapon(toPickUp, toSwitch);
    }

    @Override
    public void askPowerUpToDiscard(ArrayList<PowerUpCard> toDiscard) {
        this.CLISelector.askPowerUpToDiscard(toDiscard);
    }

    @Override
    public void askIfReload() {
        this.CLISelector.askIfReload();
    }

    @Override
    public void askWhatReaload(ArrayList<WeaponCard> toReload) {
        this.CLISelector.askWhatReaload(toReload);
    }

    @Override
    public void askSpawn(ArrayList<PowerUpCard> powerUpCards) {
        this.CLISelector.askSpawn(powerUpCards);
    }
}
