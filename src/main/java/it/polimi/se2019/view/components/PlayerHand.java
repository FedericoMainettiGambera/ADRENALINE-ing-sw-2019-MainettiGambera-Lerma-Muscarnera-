package it.polimi.se2019.view.components;


import it.polimi.se2019.model.events.ModelViewEvent;

public class PlayerHand implements ViewComponent {

    private WeaponCards weaponCards;
    private PowerUpCards powerUpCards;

    public PlayerHand(){


        this.powerUpCards=new PowerUpCards();
        this.weaponCards=new WeaponCards();
    }


    public PowerUpCards getPowerUpCards() {
        return powerUpCards;
    }

    public WeaponCards getWeaponCards() {
        return weaponCards;
    }

    @Override
    public void display(ModelViewEvent MVE) {

    }
}
