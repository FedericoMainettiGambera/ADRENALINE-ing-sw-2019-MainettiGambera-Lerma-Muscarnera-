package it.polimi.se2019.model.events;

import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;

import java.util.ArrayList;

public class SelectorEventDoubleWeaponCards extends SelectorEvent {

    private ArrayList<WeaponCard> weaponCards1;

    private ArrayList<WeaponCard> weaponCards2;

    public SelectorEventDoubleWeaponCards(SelectorEventTypes selectorEventType, ArrayList<WeaponCard> weaponCards1, ArrayList<WeaponCard> weaponCards2) {
        super(selectorEventType);
        this.weaponCards1 = weaponCards1;
        this.weaponCards2 = weaponCards2;
    }

    public ArrayList<WeaponCard> getWeaponCards1() {
        return weaponCards1;
    }

    public ArrayList<WeaponCard> getWeaponCards2() {
        return weaponCards2;
    }
}
