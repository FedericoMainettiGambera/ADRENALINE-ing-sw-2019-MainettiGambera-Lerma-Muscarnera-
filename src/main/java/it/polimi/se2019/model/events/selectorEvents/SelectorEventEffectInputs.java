package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;

import java.util.List;

public class SelectorEventEffectInputs extends SelectorEvent {
    private List<EffectInfoType> effectInputs;
    public SelectorEventEffectInputs(SelectorEventTypes selectorEventType,List<EffectInfoType> effectInputs) {
        super(selectorEventType);
        this.effectInputs = effectInputs;
    }
    public List<EffectInfoType> getEffectInputs() {
        return effectInputs;
    }
}
