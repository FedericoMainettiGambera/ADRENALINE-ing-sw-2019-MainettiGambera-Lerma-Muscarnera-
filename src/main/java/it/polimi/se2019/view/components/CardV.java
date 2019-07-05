package it.polimi.se2019.view.components;

import java.io.Serializable;
/**equivalent view class of Card class in the model
 * @author FedericoMainettiGambera
 * @author LudoLerma*/
public class CardV implements Serializable {
    private String ID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
