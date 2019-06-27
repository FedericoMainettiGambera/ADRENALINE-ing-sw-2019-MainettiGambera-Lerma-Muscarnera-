package it.polimi.se2019.ModelTest;

import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.UsableInputTableRowType;
import it.polimi.se2019.virtualView.VirtualView;
import org.junit.Test;

import java.util.List;

public class TestPlayerHistory {
    @Test
    public void testAlpha() throws Exception {
        WeaponCard w = new WeaponCard("22");
        System.out.println(w.getEffects().get(0).usableInputs().get(0).get(0));
    }
    @Test
    public void testPreconditionwithplayerhistory() throws Exception {
        ActionContext fakeContext = new ActionContext();
        PreConditionMethodsInverted PIgate = new PreConditionMethodsInverted();
        Player p = new Player();
        PlayersList pl = new PlayersList();
        pl.addPlayer(p);
        pl.addPlayer(new Player());
        pl.addPlayer(new Player());
        //pl.addPlayer(new Player());
        Board mappa = new Board("0",new VirtualView(), new VirtualView());
        char c = '0';
        for(Player x: pl.getPlayers()) {
            x.setNickname( (c) + "");
        c++;
        }

        int c2 = 0;

            for( Square C: mappa.getRoomFromPosition(new Position(0,0)))
                if( C != null)
                {
                   if(pl.getPlayers().size() > c2)
                        pl.getPlayers().get(c2).setPosition(C.getCoordinates());
                   c2++;
                }

        p.setPosition(0,0);
        fakeContext.setPlayer(p);
        fakeContext.setPlayerList(pl);

            fakeContext.setBoard(mappa);

        for(Player x: pl.getPlayers()) {
            System.out.println(x.getNickname() + " in " + x.getPosition().humanString());
            c++;
        }
        System.out.println(pl.getPlayersOnBoard());
        WeaponCard w = new WeaponCard("11");
        Effect current = w.getEffects().get(2);
        w.passContext(p,pl,mappa);
        System.out.println("# # #" + current.usableInputs().get(0).get(0));
        /*Object[] row = new Object[10];
        row[0] = pl.getPlayers().get(1);
        System.out.println(" uso " + row[0]);
        current.handleRow(current.getEffectInfo().getEffectInfoElement().get(0), row);
        System.out.println("# # #" + current.usableInputs().get(1).get(0));
        row[0] = mappa.getSquare(0,0);
        System.out.println(" uso " + row[0]);
        current.handleRow(current.getEffectInfo().getEffectInfoElement().get(1), row);
        System.out.println("# # #" + current.usableInputs().get(2).get(0));*/
    }
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
