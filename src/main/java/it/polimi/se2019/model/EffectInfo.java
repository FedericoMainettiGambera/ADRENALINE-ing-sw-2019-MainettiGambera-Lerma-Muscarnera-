package it.polimi.se2019.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EffectInfo implements Serializable {

    public List<EffectInfoElement> itNeeds() {                 // Alias of previous function
        return effectInfoElement;
    }


    public List<EffectInfoElement> getEffectInfoElement() {
        return effectInfoElement;
    }

    private List<EffectInfoElement> effectInfoElement;

    public EffectInfo() {
        effectInfoElement = new ArrayList<EffectInfoElement>();
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
