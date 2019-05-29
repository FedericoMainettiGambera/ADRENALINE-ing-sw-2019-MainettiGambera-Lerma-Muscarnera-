package it.polimi.se2019.view.components;

import java.io.Serializable;
import java.util.List;

public class MarksTrackerV implements Serializable {
    private List<MarkSlotV> markSlotsList;

    public List<MarkSlotV> getMarkSlotsList() {
        return markSlotsList;
    }

    public void setMarkSlotsList(List<MarkSlotV> markSlotsList) {
        this.markSlotsList = markSlotsList;
    }
}
