package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.view.components.WeaponCardV;

import java.util.ArrayList;

public class SelectorEventWeaponCards extends SelectorEvent {

    private ArrayList<WeaponCardV> weaponCards;

    public SelectorEventWeaponCards(SelectorEventTypes selectorEventType,ArrayList<WeaponCardV> weaponCards) {
        super(selectorEventType);
        this.weaponCards = weaponCards;
    }

    public ArrayList<WeaponCardV> getWeaponCards(){
        return this.weaponCards;
    }

}
