package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;

import java.util.ArrayList;

public class SelectorEventWeaponCards extends SelectorEvent {

    private ArrayList<WeaponCard> weaponCards;

    public SelectorEventWeaponCards(SelectorEventTypes selectorEventType,ArrayList<WeaponCard> weaponCards) {
        super(selectorEventType);
        this.weaponCards = weaponCards;
    }

    public ArrayList<WeaponCard> getWeaponCards(){
        return this.weaponCards;
    }

}
