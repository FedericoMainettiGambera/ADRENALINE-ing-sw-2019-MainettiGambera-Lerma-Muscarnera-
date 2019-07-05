package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.view.components.WeaponCardV;
import java.util.List;

/**a event implemented to ask the player to choose a weapon between the one the list weaponCards
 * @author FedericoMainettiGambera
 * @author LudoLerma */
public class SelectorEventWeaponCards extends SelectorEvent {

    /**attribute weaponCards contains a list of weaponCards the player is asked to choose beetwen*/
    private List<WeaponCardV> weaponCards;

    /**constructor,
     * @param selectorEventType the type of selectorEvent
     * @param weaponCards to set weaponCards*/
    public SelectorEventWeaponCards(SelectorEventTypes selectorEventType, List<WeaponCardV> weaponCards) {
        super(selectorEventType);
        this.weaponCards = weaponCards;
    }

    /**@return weaponCards*/
    public List<WeaponCardV> getWeaponCards(){
        return this.weaponCards;
    }

}
