package it.polimi.se2019.model;

import it.polimi.se2019.model.enumerations.EffectInfoType;

import java.util.ArrayList;
import java.util.List;

public class EffectInfo {

    public List<EffectInfoType> getEffectInfoTypelist() {
        return effectInfoTypelist;
    }
    public List<EffectInfoType> itNeeds() {                 // Alias of previous function
        return effectInfoTypelist;
    }

    public void setEffectInfoTypelist(List<EffectInfoType> effectInfoTypelist) {
        this.effectInfoTypelist = effectInfoTypelist;
    }

    private List<EffectInfoType> effectInfoTypelist;


    public EffectInfo() {

        effectInfoTypelist = new ArrayList<EffectInfoType>();
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    private Player executor;
    private int data;

}
