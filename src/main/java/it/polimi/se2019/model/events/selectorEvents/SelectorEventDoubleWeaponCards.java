package it.polimi.se2019.model.events.selectorEvents;


import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.view.components.WeaponCardV;
import java.util.List;

/**implements the possibility to switch two weapons
 * @author LudoLerma
 * @author FedericoMainettiGambera*/
public class SelectorEventDoubleWeaponCards extends SelectorEvent {

    /**list of weapon card from which the first card to switch need to be chosen*/
    private List<WeaponCardV> weaponCards1;
    /**list of weapon card from which the second card to switch need to be chosen*/
    private List<WeaponCardV> weaponCards2;

    /**constructor,
     * @param selectorEventType type of selector event
     * @param weaponCards1 to set weaponCards1
     * @param weaponCards2 to set weaponCards2*/
    public SelectorEventDoubleWeaponCards(SelectorEventTypes selectorEventType, List<WeaponCardV> weaponCards1, List<WeaponCardV> weaponCards2) {
        super(selectorEventType);
        this.weaponCards1 = weaponCards1;
        this.weaponCards2 = weaponCards2;
    }

    /**@return  weaponCards1*/
    public List<WeaponCardV> getWeaponCards1() {
        return weaponCards1;
    }

    /**@return weaponCards2*/
    public List<WeaponCardV> getWeaponCards2() {
        return weaponCards2;
    }
}
