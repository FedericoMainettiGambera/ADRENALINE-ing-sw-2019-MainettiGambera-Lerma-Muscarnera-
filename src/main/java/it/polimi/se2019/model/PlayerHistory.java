package it.polimi.se2019.model;

import java.util.ArrayList;
import java.util.List;

public class PlayerHistory {
    public List<PlayerHistoryElement> getHistoryElementList() {
        return historyElementList;
    }

    List<PlayerHistoryElement> historyElementList;
    Player owner;

    private Effect lastEffect;

    public int getCurrentBlockId() {
        return currentBlockId;
    }

    public void setCurrentBlockId(int currentBlockId) {
        this.currentBlockId = currentBlockId;
    }

    public int getCurrentInputId() {
        return currentInputId;
    }

    public void setCurrentInputId(int currentInputId) {
        this.currentInputId = currentInputId;
    }

    public int getStartBlockId() {
        return startBlockId;
    }

    private int    startBlockId;
    private int    currentBlockId;
    private int    currentInputId;

    public int getCurrentTurnId() {
        return currentTurnId;
    }

    private int    currentTurnId;
    // TODO ADD
    public PlayerHistory getBlock(int BI) {
        PlayerHistory retVal = new PlayerHistory(this.owner);
        int start = (historyElementList.size() - 1);
        int current = start;
        while((current >= 0) && (historyElementList.get(current).getBlockId() != BI) )
            current--;

        while((current >= 0) && (historyElementList.get(current).getBlockId() != (BI-1)) )  {
            retVal.historyElementList.add(historyElementList.get(current));
            current--;
        }
        return retVal;
    }
    public PlayerHistory getBlockR(int BI) {
        PlayerHistory retVal = new PlayerHistory(this.owner);

        int start = (historyElementList.size() - 1);
        int current = start;
        int from, to;
        while((current >= 0) && (historyElementList.get(current).getBlockId() != BI) )
            current--;

        to = current;

        while((current > 0) && (historyElementList.get(current-1).getBlockId() != (BI-1)) )
            current--;

        from = current;

        if(historyElementList.size() > 0 )
            if(from != -1)
        for(int i = from; i <= to;i++) {
            retVal.historyElementList.add(historyElementList.get(i));
        }

        return retVal;
    }
    public PlayerHistory getTurnChunkR(int T) {
        PlayerHistory retVal = new PlayerHistory(this.owner);
        int start = (historyElementList.size() - 1);
        int current = start;
        int from, to;
        while((current >= 0) && (historyElementList.get(current).getTurnId() != T) )
            current--;

        to = current;

        while((current > 0) && (historyElementList.get(current-1).getTurnId() != (T-1)) )
            current--;

        from = current;
        if(from!=-1)
        if(historyElementList.size() > 0) {
            retVal.startBlockId = historyElementList.get(from).getBlockId();
            retVal.currentBlockId = historyElementList.get(to).getBlockId();
            for(int i = from; i <= to;i++) {
                retVal.historyElementList.add(historyElementList.get(i));
            }
        } else {
            retVal.startBlockId = 0;
            retVal.currentBlockId = 0;
        }


        return retVal;
    }
    public List<Player> getTargets() {
        List<Player> retVal = new ArrayList<>();
        for(PlayerHistoryElement r: this.getHistoryElementList()) {
            int j = 0;
            while(((Object[]) r.getInput())[j] != null) {
                if( ((Object[]) r.getInput())[j].getClass().equals(Player.class)) {
                    if(!retVal.contains(((Object[]) r.getInput())[j])) {
                        retVal.add((Player) ((Object[]) r.getInput())[j]);
                    }
                }
                j++;
            }
        }
        return retVal;

    }
    public List<List<PlayerHistoryElement>> rawDataSplittenByBlockId() {
        List<List<PlayerHistoryElement>> retVal = new ArrayList<>();
        for(int i = this.getStartBlockId(); i <= this.getCurrentBlockId();i++) {
            retVal.add(this.getBlockR(i).historyElementList);
        }
        return retVal;
    }
    public PlayerHistory getTurnChunk(int T) {
        PlayerHistory retVal = new PlayerHistory(this.owner);
        int start = (historyElementList.size() - 1);
        int current = start;
        while((current >= 0) && (historyElementList.get(current).getTurnId() != T) )
            current--;

        while((current >= 0) && (historyElementList.get(current).getTurnId() != (T-1)) )  {
            retVal.historyElementList.add(historyElementList.get(current));
            current--;
        }
        return retVal;
    }

    public void addRecord(Card card,Effect e,Object input) {
        if((lastEffect != e) || (owner.getTurnID() != currentTurnId)) {
            // si sta introducendo l'input di un nuovo effetto
            // il block id aumenta di 1
            // si azzera inputId
            currentBlockId++;
            currentInputId = 0;
        }
        else {
            // l'effetto è lo stesso di prima
            currentInputId++;
        }

        lastEffect = e;
        currentTurnId = owner.getTurnID();
        PlayerHistoryElement playerHistoryElement = new PlayerHistoryElement();
        playerHistoryElement.setContextCard(card);
        playerHistoryElement.setContextEffect(e);
        playerHistoryElement.setInput(input);
        playerHistoryElement.setBlockId(currentBlockId);
        playerHistoryElement.setInputId(currentInputId);
        playerHistoryElement.setTurnId(owner.getTurnID());
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

    public void show() {
        System.out.println("carta \t\t\t effetto \t\t input row     \t\t\t\t\t BI \t II \t TURNO ");
        for(PlayerHistoryElement p: historyElementList) {
            p.show();
        }

    }

    public PlayerHistory(Player owner) {
        this.owner = owner;
        this.startBlockId = 0;
        lastEffect = null;
        currentBlockId = 0;
        currentInputId = 0;
        currentTurnId = owner.getTurnID();
        historyElementList = new ArrayList<>();
    }
}
