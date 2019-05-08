package it.polimi.se2019.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * THIS CLASS SHOULD NEVER BE DIRECTLY ACCESSED, INSTEAD USE METHODS FROM THE "Person" CLASS.
 * The MarksTracker class keeps track of the number of marks a player has received from each player in game.
 * @author FedericoMainettiGambera
 * */
public class MarksTracker extends Observable {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor:
     * initialize the markSlotsList with a new Arraylist of MarkSlots.
     * */
    public MarksTracker() {
        markSlotsList = new ArrayList<>();
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /**mark slots list*/
    private List<MarkSlots> markSlotsList;

    /*-********************************************************************************************************METHODS*/
    /*Do not to use this methods directly. Instead use methods from the "Person" class.*/

    /**avoid this method if possible, do not access directly attributes, but use method that interact with them for you.
     * @return
     * */
    public List<MarkSlots> getMarkSlotsList() {
        return markSlotsList;
    }

    /**add marks.
     * This method uses the MarkSlots.addQuantity(int quantity) method that makes sure that the resulting quantity of
     * marks is never more than GameConstant.MaxNumberOfMarkFromPlayer.
     * @param markingPlayer
     * @param quantity
     * */
    public void addMarksFrom(Player markingPlayer, int quantity) throws IllegalArgumentException {
        if(quantity < 0)
            throw new IllegalArgumentException();
        if(markingPlayer == null)
            throw new IllegalArgumentException();
        for(int i = 0; i<markSlotsList.size(); i++) {
            if(markSlotsList.get(i).getMarkingPlayer() == markingPlayer){
                markSlotsList.get(i).addQuantity(quantity);
                setChanged();
                notifyObservers();
                return;
            }
        }
        markSlotsList.add(new MarkSlots(markingPlayer, quantity));
        setChanged();
        notifyObservers();
        return;
    }

    /**@param markingPlayer
     * @return number of marks received from a specific player
     * */
    public int getNumberOfMarksSlotFrom(Player markingPlayer) {
        for(int i = 0; i<markSlotsList.size(); i++){
            if(markSlotsList.get(i).getMarkingPlayer() == markingPlayer){
                return markSlotsList.get(i).getQuantity();
            }
        }
        return 0;
    }

    /**deletes all marks received from a specific player
     * @param markingPlayer
     * */
    public void deleteMarksFromPlayer(Player markingPlayer){
        for(int i = 0; i<markSlotsList.size(); i++) {
            if(markSlotsList.get(i).getMarkingPlayer() == markingPlayer){
                markSlotsList.remove(i);
                setChanged();
                notifyObservers();
                return;
            }
        }
    }
}