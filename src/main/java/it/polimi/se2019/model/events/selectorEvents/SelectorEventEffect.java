package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.view.components.EffectV;
import java.util.List;

/**implements a selector event for when a effect is needed as input
 * @author LudoLerma
 * @author FedericoMainettiGambera*/
public class SelectorEventEffect extends SelectorEvent {
    /**a list of the possible effects to be used*/
    private List<EffectV> possibleEffects;
    /**constructor,
     * @param selectorEventType type of selector event
     * @param possibleEffects to set the attribute possibleEffects*/
    public SelectorEventEffect(SelectorEventTypes selectorEventType, List<EffectV> possibleEffects){
        super(selectorEventType);
        this.possibleEffects = possibleEffects;
    }

    /**@return possibleEffects*/
    public List<EffectV> getPossibleEffects() {
        return possibleEffects;
    }
}
