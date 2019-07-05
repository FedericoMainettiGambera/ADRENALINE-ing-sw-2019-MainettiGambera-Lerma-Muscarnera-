package it.polimi.se2019.view.components;

import it.polimi.se2019.model.enumerations.AmmoCubesColor;
/**equivalent view class of PowerUpCard class in the model
 * @author FedericoMainettiGambera
 *@author LudoLerma */
public class PowerUpCardV extends CardV {
    private AmmoCubesColor color;

    private String description;

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setColor(AmmoCubesColor color) {
        this.color = color;
    }

    public AmmoCubesColor getColor() {
        return color;
    }
}
