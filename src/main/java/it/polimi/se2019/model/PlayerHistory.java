package it.polimi.se2019.model;

import java.util.ArrayList;
import java.util.List;

public class PlayerHistory {
    List<PlayerHistoryElement> historyElementList;
    Object[][] subRecordTable;
    // TODO ADD
    public void addRecord(Card card,Effect e,Object input) {
        PlayerHistoryElement playerHistoryElement = new PlayerHistoryElement();
        playerHistoryElement.setContextCard(card);
        playerHistoryElement.setContextEffect(e);
        playerHistoryElement.setInput(input);
        historyElementList.add(playerHistoryElement);
    }

    public PlayerHistoryElement getRecord(int n) {
        return historyElementList.get(n);
    }
    public int getSize() {
        return historyElementList.size();
    }
    public PlayerHistoryElement getLast() {
        if(historyElementList.size()>0)
        return historyElementList.get(historyElementList.size() - 1);
        else
            return null;
    }

    public PlayerHistory() {
        historyElementList = new ArrayList<>();
    }
}
