package it.polimi.se2019.view.components;

import it.polimi.se2019.model.EffectInfo;

import java.io.Serializable;
/**equivalent view class of Effect class in the model
 * @author FedericoMainettiGambera
 * @author LudoLerma*/
public class EffectV implements Serializable {
    private String effectName;
    private EffectInfo effectInfo;
    private String description;

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public EffectInfo getEffectInfo() {
        return effectInfo;
    }

    public String getEffectName() {
        return effectName;
    }

    public void setEffectInfo(EffectInfo effectInfo) {
        this.effectInfo = effectInfo;
    }

    public void setEffectName(String effectName) {
        this.effectName = effectName;
    }
}
