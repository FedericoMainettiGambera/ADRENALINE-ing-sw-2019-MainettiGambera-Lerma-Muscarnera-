package it.polimi.se2019.model;


import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.view.components.AmmoCardV;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
        ammunitions = new AmmoList();
        List<AmmoCubesColor> Colors = new ArrayList<>();

        Colors.add(AmmoCubesColor.yellow);
        Colors.add(AmmoCubesColor.red);
        Colors.add(AmmoCubesColor.blue);
        Colors.add(AmmoCubesColor.yellow);

        int idUnparsed = Integer.parseInt(ID);
        int id = 0;
        if( idUnparsed <= 18) {
            id = (idUnparsed - 1) / 3 + 1;
        } else {

        }
        if(idUnparsed >= 19) {
            // poweup
            int idParsed = 7+ (idUnparsed-19)/2;
            isPowerUp = true;
            AmmoCubesColor A = null;
            AmmoCubesColor B= null;
            System.out.println(idParsed);
            if(idParsed <= 9) {
                A = Colors.get(idParsed - 7);
                B = Colors.get(idParsed - 7);
                ammunitions.addAmmoCubesOfColor(A, 1);
                ammunitions.addAmmoCubesOfColor(B, 1);
            }
            if(idParsed > 9) {
                int idParsedParsed = idParsed - 9;
                 A = Colors.get(((idParsedParsed)%4)%3);
                 B = Colors.get(((idParsedParsed)%4)%3 + 1);
                    ammunitions.addAmmoCubesOfColor(A, 1);
                    ammunitions.addAmmoCubesOfColor(B, 1);
            }
        } else {
            // cube
            isPowerUp = false;
            AmmoCubesColor A = Colors.get( ((id-1)/2));
            AmmoCubesColor B = Colors.get(  (((id-1)/2) + 2 + -1* id%2)%3  );
            ammunitions.addAmmoCubesOfColor(A,1);
            ammunitions.addAmmoCubesOfColor(B,2);
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