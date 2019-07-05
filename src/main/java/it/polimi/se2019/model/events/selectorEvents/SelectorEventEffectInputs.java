package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;

import java.util.List;
/**needed for when a effect type is needed as input
 * @author LudoLerma
 * @author FedericoMainettiGambera
 * */
public class SelectorEventEffectInputs extends SelectorEvent {
    /**the inputType */
    private EffectInfoType inputType;
    /**a list of the possible inputs*/
    private List<Object> possibleInputs;
    /**constructor,
     * @param inputType to set inputType attibute
     * @param possibleInputs to set the possibleInputs attribute*/
    public SelectorEventEffectInputs(EffectInfoType inputType, List<Object> possibleInputs) {
        super(SelectorEventTypes.askEffectInputs);
        this.inputType=inputType;
        this.possibleInputs=possibleInputs;
    }
    /**@return inputType*/
    public EffectInfoType getInputType() {
        return inputType;
    }
    /**@return possibleInputs*/
    public List<Object> getPossibleInputs() {
        return possibleInputs;
    }
}
