package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.view.components.EffectV;
import java.util.List;

public class SelectorEventEffect extends SelectorEvent {
    private List<EffectV> possibleEffects;
    public SelectorEventEffect(SelectorEventTypes selectorEventType, List<EffectV> possibleEffects){
        super(selectorEventType);
        this.possibleEffects = possibleEffects;
    }

    public List<EffectV> getPossibleEffects() {
        return possibleEffects;
    }
}
