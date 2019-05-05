package it.polimi.se2018.view;



public class PlayerHand {

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
}
