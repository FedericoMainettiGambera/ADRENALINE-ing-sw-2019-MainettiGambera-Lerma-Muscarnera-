package it.polimi.se2018.model;


import java.util.ArrayList;
import java.util.List;


/***/
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