package it.polimi.se2019.view.components;

import it.polimi.se2019.model.enumerations.AmmoCubesColor;

import java.io.Serializable;
/**equivalent view class of the AmmoCube class in the model
 * @author FedericoMainettiGambera
 * @author LudoLerma*/
public class AmmoCubesV implements Serializable {
    private int quantity;

    private AmmoCubesColor color;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setColor(AmmoCubesColor color) {
        this.color = color;
    }

    public AmmoCubesColor getColor() {
        return color;
    }
}
