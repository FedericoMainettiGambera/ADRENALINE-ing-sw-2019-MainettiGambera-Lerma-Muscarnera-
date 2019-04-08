package it.polimi.se2018.model;

import it.polimi.se2018.model.AmmoList;
import it.polimi.se2018.model.Card;
import it.polimi.se2018.model.Effect;

import java.util.List;


public class WeaponCard extends Card {

    
    public WeaponCard() {
    }

    
    private AmmoList pickUpCost;

    
    private AmmoList reloadCost;

    
    private boolean isLoaded;

    
    public List<Effect> effects;


}