package it.polimi.se2019.model;


import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.view.components.AmmoCardV;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** information regarding ammunition are stored and mostly handled in this class
 * @author FedericoMainettiGambera */
public class AmmoCard extends Card implements Serializable {

    /**constructor any ammunition is initialized  with a list of ammos(ie "1 yellow, 1 blue"),
     * @param ammunitions contains this list
     * a boolean attributed is set as well, it's name is
     * @param isPowerUp  it indicates whether an ammoCard allows you to draw a PowerUpCard or not
     * @param ID is needed to know specifically how to iniziliaze each single istance, since most ammoCard are different
     * from one another*/
    public AmmoCard(String ID, AmmoList ammunitions, boolean isPowerUp){
        super(ID);
        this.ammunitions = ammunitions;
        this.isPowerUp = isPowerUp;
    }

    /** list of ammos */
    private AmmoList ammunitions;

    /**whether the ammocard allows you to fraw a power up or not*/
    private boolean isPowerUp;

    /**return a list of ammos */
    public AmmoList getAmmunitions() {
        return ammunitions;
    }

    /**return the boolean value of isPowerUp*/
    public boolean isPowerUp() {
        return isPowerUp;
    }
   /**build a card from a given
    * @param ID, indeed, it allows to distinguish between different group of ammocards*/
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
            //System.out.println(idParsed);
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
    /**build the equivalent class in the view, but with just the minimum information needed*/
    public AmmoCardV buildAmmoCardV(){
        AmmoCardV ammoCardV=new AmmoCardV();
        ammoCardV.setAmmoList(this.ammunitions.buildAmmoListV());
        ammoCardV.setPowerUp(this.isPowerUp());
        ammoCardV.setID(this.getID());
        return ammoCardV;
    }
}