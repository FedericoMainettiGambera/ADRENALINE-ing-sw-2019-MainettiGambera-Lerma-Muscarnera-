package it.polimi.se2019.view.components;


import java.util.List;
/**equivalent class in the view
 *  of the weaponCard class in the model
 *   @author FedericoMainettiGambera
 *  @author LudoLerma*/
public class WeaponCardV extends CardV {
    private AmmoListV pickUpCost;

    private AmmoListV reloadCost;

    private boolean isLoaded;

    private String description;

    private String name;

    private List<EffectV> effectVList;

    public void setEffectVList(List<EffectV> effectVList) {
        this.effectVList = effectVList;
    }

    public List<EffectV> getEffectVList() {
        return effectVList;
    }

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
