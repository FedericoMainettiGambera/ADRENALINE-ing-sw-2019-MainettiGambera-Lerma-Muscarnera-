package it.polimi.se2019.model;

import it.polimi.se2019.view.components.MarkSlotV;
import it.polimi.se2019.view.components.MarksTrackerV;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * THIS CLASS SHOULD NEVER BE DIRECTLY ACCESSED, INSTEAD USE METHODS FROM THE "Person" CLASS.
 * The MarksTracker class keeps track of the number of marks a player has received from each player in game.
 * @author FedericoMainettiGambera
 * @author LudoLerma
 * */
public class MarksTracker implements Serializable {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor:
     * initialize the markSlotsList with a new Arraylist of MarkSlots.
     * */
     MarksTracker() {
        markSlotsList = new ArrayList<>();
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /**mark slots list*/
    private List<MarkSlots> markSlotsList;

    /*-********************************************************************************************************METHODS*/
    /*Do not to use this methods directly. Instead use methods from the "Person" class.*/

    /**avoid this method if possible, do not access directly attributes, but use method that interact with them for you.
     * @return markSlotsList
     * */
    public List<MarkSlots> getMarkSlotsList() {
        return markSlotsList;
    }

    /**add marks.
     * This method uses the MarkSlots.addQuantity(int quantity) method that makes sure that the resulting quantity of
     * marks is never more than GameConstant.MAX_NUMBER_OF_MARK_FROM_PLAYER.
     * @param markingPlayer the player who's giving the marks
     * @param quantity the quantity of marks to be given
     * */
    public void addMarksFrom(Player markingPlayer, int quantity){
        if(quantity < 0)
            throw new IllegalArgumentException();
        if(markingPlayer == null)
            throw new IllegalArgumentException();
        for (MarkSlots markSlots : markSlotsList) {
            if (markSlots.getMarkingPlayer() == markingPlayer) {
                markSlots.addQuantity(quantity);
                return;
            }
        }
        markSlotsList.add(new MarkSlots(markingPlayer, quantity));

    }

    /**@param markingPlayer the specific player
     * @return number of marks received from a specific player
     * */
     int getNumberOfMarksSlotFrom(Player markingPlayer) {
        for (MarkSlots markSlots : markSlotsList) {
            if (markSlots.getMarkingPlayer() == markingPlayer) {
                return markSlots.getQuantity();
            }
        }
        return 0;
    }

    /**deletes all marks received from a specific player
     * @param markingPlayer the player whose marks are about to be deleted
     * */
     void deleteMarksFromPlayer(Player markingPlayer){
        for(int i = 0; i<markSlotsList.size(); i++) {
            if(markSlotsList.get(i).getMarkingPlayer() == markingPlayer){
                markSlotsList.remove(i);
                return;
            }
        }
    }

    /**builds the equivalent structure for view purposes
     * @return a reference to said structure*/
     MarksTrackerV buildMarksTrackerV(){
        MarksTrackerV marksTrackerV = new MarksTrackerV();
        List<MarkSlotV> listOfMarksSlotV = new ArrayList<>();
        MarkSlotV tempMark;
        for (MarkSlots m : this.markSlotsList) {
            tempMark = new MarkSlotV();
            tempMark.setMarkingPlayer(m.getMarkingPlayer().getNickname());
            tempMark.setQuantity(m.getQuantity());
            listOfMarksSlotV.add(tempMark);
        }
        marksTrackerV.setMarkSlotsList(listOfMarksSlotV);

        return marksTrackerV;
    }
}