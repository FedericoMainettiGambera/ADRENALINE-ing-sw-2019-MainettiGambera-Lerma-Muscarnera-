package it.polimi.se2019.ModelTest;

import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.UsableInputTableRowType;
import org.junit.Test;

import java.util.List;

public class TestPlayerHistory {
    @Test
    public void testCronologia() throws Exception {
        Player p = new Player();
        PlayerHistory T = new PlayerHistory(p);
        Effect effect1 = new Effect();
                         effect1.setName("effetto 1");
        Effect effect2 = new Effect();
                         effect2.setName("effetto 2");
        Effect effect3 = new Effect();
                         effect3.setName("effetto 3");

        WeaponCard card1 = new WeaponCard("1");
        WeaponCard card2 = new WeaponCard("2");

        Object row1[] = new Object[10];
        row1[0] = p;

        Object row2[] = new Object[10];
/*
        p.getPlayerHistory().addRecord(card1,effect1,row1);
        p.getPlayerHistory().addRecord(card1,effect1,row2);
        p.getPlayerHistory().addRecord(card2,effect2,row1);
        p.getPlayerHistory().addRecord(card2,effect2,row2);
        p.incrementTurnID();
        p.getPlayerHistory().addRecord(card2,effect3,row1);
        p.getPlayerHistory().addRecord(card2,effect3,row1);
        p.incrementTurnID();
        p.getPlayerHistory().addRecord(card2,effect3,row1);
        p.getPlayerHistory().addRecord(card2,effect2,row1);
        p.getPlayerHistory().addRecord(card2,effect3,row1);
        p.incrementTurnID();
        p.getPlayerHistory().addRecord(card2,effect2,row1);
        p.getPlayerHistory().addRecord(card2,effect2,row2);
        p.incrementTurnID();
        p.getPlayerHistory().addRecord(card2,effect2,row2);
        p.getPlayerHistory().addRecord(card2,effect2,row2);*/
        p.getPlayerHistory().addRecord(card2,effect2,row1);
        p.getPlayerHistory().addRecord(card2,effect2,row2);
        p.incrementTurnID();
        p.getPlayerHistory().addRecord(card2,effect2,row1);
        p.getPlayerHistory().addRecord(card2,effect3,row1);
        p.getPlayerHistory().addRecord(card2,effect2,row1);
        p.getPlayerHistory().addRecord(card2,effect2,row1);
        //p.getPlayerHistory().addRecord(card2,effect3,row2);
        //p.incrementTurnID();
        //T.show();
        p.getPlayerHistory().show();
        for(List<PlayerHistoryElement> l: T.rawDataSplittenByBlockId()) {
            for(PlayerHistoryElement r: l) {
                r.show();
            }
            System.out.println("-----");
        }
        PreConditionMethodsInverted PIgate = new PreConditionMethodsInverted();
        ActionContext fakeContext = new ActionContext();
        fakeContext.setPlayer(p);
        PlayersList pl = new PlayersList();
        pl.addPlayer(p);
        pl.addPlayer(new Player());pl.addPlayer(new Player());pl.addPlayer(new Player());
        pl.addPlayer(new Player());pl.addPlayer(new Player());pl.addPlayer(new Player());
        for(Player x: pl.getPlayers())
            x.setPosition(0,0);
        fakeContext.setPlayerList(pl);
        System.out.println(
               PIgate.notFirstExecuted(fakeContext, UsableInputTableRowType.typePlayer,new ActionDetails(),new Object(),null,null).size()
        );
        /*for(int i = 0; i <=  p.getTurnID() ;i++) {
            System.out.println("al turno " + i + ":" );
            System.out.println("[" + T.getTurnChunkR(i).getStartBlockId() + ", " + T.getTurnChunkR(i).getCurrentBlockId() +"]");
            for (PlayerHistoryElement h : T.getBlockR(T.getTurnChunkR(i).getStartBlockId()).getHistoryElementList()) {
                 h.show();
            }
        }*/
    }
}
