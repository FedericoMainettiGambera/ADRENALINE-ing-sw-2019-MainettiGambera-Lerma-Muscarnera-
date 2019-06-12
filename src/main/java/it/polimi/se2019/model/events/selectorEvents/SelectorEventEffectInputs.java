package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;

import java.util.List;

public class SelectorEventEffectInputs extends SelectorEvent {
    private EffectInfoType inputType;
    private List<Object> possibleInputs;
    public SelectorEventEffectInputs(EffectInfoType inputType, List<Object> possibleInputs) {
        super(SelectorEventTypes.askEffectInputs);
        this.inputType=inputType;
        this.possibleInputs=possibleInputs;
    }
    public EffectInfoType getInputType() {
        return inputType;
    }
    public List<Object> getPossibleInputs() {
        return possibleInputs;
    }
}
