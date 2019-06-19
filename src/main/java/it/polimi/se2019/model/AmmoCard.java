package it.polimi.se2019.model;


import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.view.components.AmmoCardV;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.se2019.model.GameConstant.probabilityDenominatorAmmoCardWithPowerUp;

/***/
public class AmmoCard extends Card implements Serializable {

    /***/
    public AmmoCard(String ID, AmmoList ammunitions, boolean isPowerUp){
        super(ID);
        this.ammunitions = ammunitions;
        this.isPowerUp = isPowerUp;
    }

    /***/
    private AmmoList ammunitions;

    /***/
    private boolean isPowerUp;

    public PowerUpCard getPowerUpCardPointer() {
        return powerUpCardPointer;
    }

    private PowerUpCard powerUpCardPointer;
    /***/
    public AmmoList getAmmunitions() {
        return ammunitions;
    }

    /***/
    public boolean isPowerUp() {
        return isPowerUp;
    }
    public AmmoCard(String ID) {
        super(ID);
        List<AmmoCubesColor> Colors = new ArrayList<>();
        Colors.add(AmmoCubesColor.yellow);
        Colors.add(AmmoCubesColor.red);
        Colors.add(AmmoCubesColor.blue);

        int randomFactorIsPowerUp = ((int) Math.ceil(Math.random() * 1000))% probabilityDenominatorAmmoCardWithPowerUp;

        ammunitions = new AmmoList();
        if(randomFactorIsPowerUp == 0) {
            isPowerUp = true;
            try {
                int randomPowerUpCard = ((int) Math.ceil(Math.random() * 1000))%4 + 1;
                powerUpCardPointer = new PowerUpCard("" + randomPowerUpCard );
            } catch(Exception e) {
                System.out.println("<SERVER> errore nella generazione della carta powerup");
            }
            int A = ((int) Math.ceil(Math.random() * 1000))% 3;
            int B = ((int) Math.ceil(Math.random() * 1000))% 3;
            ammunitions.addAmmoCubesOfColor(Colors.get(A) ,1);
            ammunitions.addAmmoCubesOfColor(Colors.get(B) ,1);
        } else {
            isPowerUp = false;
            int A = ((int) Math.ceil(Math.random() * 1000))% 3;
            int B = ((int) Math.ceil(Math.random() * 1000))% 3;
            int C = ((int) Math.ceil(Math.random() * 1000))% 3;
            ammunitions.addAmmoCubesOfColor(Colors.get(A) ,1);
            ammunitions.addAmmoCubesOfColor(Colors.get(B) ,1);
            ammunitions.addAmmoCubesOfColor(Colors.get(C) ,1);
        }


    }
    public AmmoCardV buildAmmoCardV(){
        AmmoCardV ammoCardV=new AmmoCardV();
        ammoCardV.setAmmoList(this.ammunitions.buildAmmoListV());
        ammoCardV.setPowerUp(this.isPowerUp());
        ammoCardV.setID(this.getID());
        return ammoCardV;
    }
}