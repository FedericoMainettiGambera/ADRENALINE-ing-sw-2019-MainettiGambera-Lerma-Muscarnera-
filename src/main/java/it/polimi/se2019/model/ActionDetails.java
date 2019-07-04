package it.polimi.se2019.model;

import java.io.Serializable;
/**
 * Every Effect is composed by a list of actions. Every action needs to have some parameters ( for example,
 * Damage needs a "target" and a "quantity of damage" ). Some of these parameters may be defined
 * in the card file and some of these may be defined by Player ( taking again Damage as example, the "quantity
 * of damage" is defined in the card file and the "target" is defined by the player)
 *
 * @author Luca Muscarnera
 * */
public class ActionDetails implements Serializable {

    public ActionDetails() {
        this.userSelectedActionDetails = new UserSelectedActionDetails();
        this.fileSelectedActionDetails = new FileSelectedActionDetails();
    }



    public UserSelectedActionDetails getUserSelectedActionDetails() {
        return userSelectedActionDetails;
    }
    public FileSelectedActionDetails getFileSelectedActionDetails() {
        return fileSelectedActionDetails;
    }

    private UserSelectedActionDetails userSelectedActionDetails;
    private FileSelectedActionDetails fileSelectedActionDetails;

}