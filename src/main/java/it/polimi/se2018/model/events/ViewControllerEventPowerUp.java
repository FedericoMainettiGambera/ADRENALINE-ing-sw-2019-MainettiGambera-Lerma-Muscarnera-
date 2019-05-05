package it.polimi.se2018.model.events;

import it.polimi.se2018.model.PowerUpCard;

public class ViewControllerEventPowerUp extends ViewControllerEvent{
    private PowerUpCard powerUpCard;

    public ViewControllerEventPowerUp(PowerUpCard powerUpCard){
        this.powerUpCard = powerUpCard;
    }

    public PowerUpCard getPowerUpCard(){
        return this.powerUpCard;
    }
}
