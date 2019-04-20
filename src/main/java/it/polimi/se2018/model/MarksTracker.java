package it.polimi.se2018.model;


import java.util.ArrayList;
import java.util.List;


/**The MarksTracker class keeps track of the number of marks a player has from every enemies in game
 * THIS CLASS MUST NEVER BE USED, INSTEAD USE THE "Player" CLASS.
 * */
public class MarksTracker {

    /***/
    public MarksTracker() {
        markSlotsList = new ArrayList<>();
    }

    /***/
    private List<MarkSlots> markSlotsList;

    /***/
    public void addMarksFrom(Player markingPlayer, int quantity) {
        for(int i = 0; i<quantity; i++) {
            markSlotsList.add(new MarkSlots(markingPlayer));
        }
    }

    /***/
    public int getNumberOfMarksSlotFrom(Player markingPlayer) {
        for(int i = 0; i<markSlotsList.size(); i++){
            if(markSlotsList.get(i).getMarkingPlayer() == markingPlayer){
                return markSlotsList.get(i).getQuantity();
            }
        }
        return 0;
    }

}