package it.polimi.se2019.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EffectInfo implements Serializable {

    public List<EffectInfoElement> getEffectInfoElement() {
        return effectInfoElement;
    }

    private List<EffectInfoElement> effectInfoElement;

    public EffectInfo() {
        effectInfoElement = new ArrayList<EffectInfoElement>();
    }


}
