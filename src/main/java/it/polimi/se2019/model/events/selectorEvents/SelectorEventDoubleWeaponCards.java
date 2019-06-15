package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.view.components.WeaponCardV;

import java.util.ArrayList;

public class SelectorEventDoubleWeaponCards extends SelectorEvent {

    private ArrayList<WeaponCardV> weaponCards1;

    private ArrayList<WeaponCardV> weaponCards2;

    public SelectorEventDoubleWeaponCards(SelectorEventTypes selectorEventType, ArrayList<WeaponCardV> weaponCards1, ArrayList<WeaponCardV> weaponCards2) {
        super(selectorEventType);
        this.weaponCards1 = weaponCards1;
        this.weaponCards2 = weaponCards2;
    }

    public ArrayList<WeaponCardV> getWeaponCards1() {
        return weaponCards1;
    }

    public ArrayList<WeaponCardV> getWeaponCards2() {
        return weaponCards2;
    }
}
