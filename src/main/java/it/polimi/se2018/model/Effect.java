package it.polimi.se2018.model;


import it.polimi.se2018.model.enumerations.EffectType;

import java.util.List;
import java.util.function.Consumer;

/***/
public class Effect {

    /***/
    public Effect(List<Action> actions, EffectType effectType) {
        this.actions = actions;
        this.effectType = effectType;
    }

    /***/
    private String description;

    /***/
    private EffectType effectType;

    /***/
    private List<Action> actions;

    /***/
    public List<Action> getActions() {
        return actions;
    }

    /***/
    public EffectType getEffectType() {
        return effectType;
    }

    /***/
    public void Exec() {
        for(Action a:this.actions){
            a.Exec();
        }
    }

}