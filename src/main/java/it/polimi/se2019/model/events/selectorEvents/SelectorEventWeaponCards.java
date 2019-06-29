package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.view.components.WeaponCardV;
import java.util.List;

public class SelectorEventWeaponCards extends SelectorEvent {

    private List<WeaponCardV> weaponCards;

    public SelectorEventWeaponCards(SelectorEventTypes selectorEventType, List<WeaponCardV> weaponCards) {
        super(selectorEventType);
        this.weaponCards = weaponCards;
    }

    public List<WeaponCardV> getWeaponCards(){
        return this.weaponCards;
    }

}
