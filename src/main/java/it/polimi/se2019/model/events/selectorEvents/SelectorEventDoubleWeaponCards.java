package it.polimi.se2019.model.events.selectorEvents;


import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.view.components.WeaponCardV;
import java.util.List;

public class SelectorEventDoubleWeaponCards extends SelectorEvent {

    private List<WeaponCardV> weaponCards1;

    private List<WeaponCardV> weaponCards2;

    public SelectorEventDoubleWeaponCards(SelectorEventTypes selectorEventType, List<WeaponCardV> weaponCards1, List<WeaponCardV> weaponCards2) {
        super(selectorEventType);
        this.weaponCards1 = weaponCards1;
        this.weaponCards2 = weaponCards2;
    }

    public List<WeaponCardV> getWeaponCards1() {
        return weaponCards1;
    }

    public List<WeaponCardV> getWeaponCards2() {
        return weaponCards2;
    }
}
