package it.polimi.se2019.view.components;


public class WeaponCardV extends CardV {
    private AmmoListV pickUpCost;

    private AmmoListV reloadCost;

    private boolean isLoaded;

    private String description;

    private String name;

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public AmmoListV getPickUpCost() {
        return pickUpCost;
    }

    public AmmoListV getReloadCost() {
        return reloadCost;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPickUpCost(AmmoListV pickUpCost) {
        this.pickUpCost = pickUpCost;
    }

    public void setReloadCost(AmmoListV reloadCost) {
        this.reloadCost = reloadCost;
    }
}
