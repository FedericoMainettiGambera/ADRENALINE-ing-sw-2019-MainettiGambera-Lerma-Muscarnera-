package it.polimi.se2019.ModelTest;

import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.view.components.EffectV;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.se2019.model.enumerations.EffectInfoType.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestEffect {
    /*
     * Methods in Effect:
     *       e.setOf();*
     *       e.setName();*
     *       e.getEffectInfo();*
     *       e.requestedInputs();*
     *       e.getActions();*
     *       e.getName();*
     *       e.handleRow(); #*
     *       e.setFilledInputs();
     *       e.passContext();*
     *       e.getOf();*
     *       e.getUsageCost();
     *       e.Exec();*
     *       e.usableInputs();
     * */

    @Test
    public void test1() throws Exception {
        Effect e = new Effect();
        WeaponCard w = new WeaponCard("1");
        e.setOf(w);
        assertEquals(e.getOf(), w);
    }

    @Test
    public void testName() throws Exception {
        Effect e = new Effect();
        String n = "test";
        e.setName(n);
        assertEquals(e.getName(), n);
    }

    @Test
    public void testGetEffectInfo() throws Exception {
        WeaponCard w = new WeaponCard("1");
        Effect e = w.getEffects().get(0);
        e.setEffectInfo(e.getEffectInfo());
        assertNotEquals(
                e.getEffectInfo(),
                null
        );
    }

    @Test
    public void testRequestedInputs() throws Exception {
        WeaponCard w = new WeaponCard("1");
        Effect e = w.getEffects().get(0);
        List<EffectInfoType> inputList = e.requestedInputs();
        for (EffectInfoType i : inputList) {
            assertNotEquals(i, null);
        }
    }

    @Test
    public void testGetActions() throws Exception {
        WeaponCard w = new WeaponCard("1");
        Effect e = w.getEffects().get(0);
        List<Action> a = e.getActions();
        for (Action x : a)
            assertNotEquals(x, null);
    }

   /* @Test
    public void testHandleRow() throws Exception {
        WeaponCard w = new WeaponCard("22");
        Effect e = new Effect();
        e.setOf(w);
        e.getActions().add(new Action());
        e.getActions().add(new Action());
        Action currentAction = e.getActions().get(1);
        List<Player> pl  = new ArrayList<>();
        Player a = new Player();
        a.setNickname("a");
        Player b = new Player();
        b.setNickname("b");
        Player c = new Player();
        c.setNickname("c");
        Player d = new Player();
        d.setNickname("d");
        Player e_ = new Player();
        e_.setNickname("d");

                    pl.add(a);
                    pl.add(b);
                    pl.add(c);
                    pl.add(d);
                    pl.add(e_);
        Board board = new Board("2",null,null);
        PlayersList playersList = new PlayersList();
        for(Player x: pl) {
            playersList.addPlayer(x);
        }
        e.passContext(a,playersList,board);
        a.setPositionWithoutNotify(new Position(0,0));
        b.setPositionWithoutNotify(new Position(0,1));
        c.setPositionWithoutNotify(new Position(2,0));
        d.setPositionWithoutNotify(new Position(1,0));
        e_.setPositionWithoutNotify(new Position(0,0));
        for (EffectInfoType x : EffectInfoType.values()) {
            EffectInfo testEffectInfo = new EffectInfo();
            testEffectInfo.getEffectInfoElement().add(new EffectInfoElement());
            List<Integer> de = new ArrayList<>();
            de.add(2);
            testEffectInfo.getEffectInfoElement().get(0).setEffectInfoTypeDestination(
                    de
            );
            testEffectInfo.getEffectInfoElement().get(0).setEffectInfoTypelist(
                    x
            );
            e.setEffectInfo(testEffectInfo);
            Object[] row = new Object[10];
            List<Player> targetList = currentAction.getActionInfo().getActionDetails().getUserSelectedActionDetails().getTargetList();
            UserSelectedActionDetails  AD = currentAction.getActionInfo().getActionDetails().getUserSelectedActionDetails();
            ActionContext              AC = currentAction.getActionInfo().getActionContext();
            if(x == EffectInfoType.player) {
                //row[0] =  null;
                e.handleRow(e.getEffectInfo().getEffectInfoElement().get(0),row);
                assertEquals(
                        targetList.get(0),
                        a
                );
            }
            if(x == EffectInfoType.singleTarget) {
                row[0] =  b;
                e.handleRow(e.getEffectInfo().getEffectInfoElement().get(0),row);
                assertEquals(
                        targetList.get(0),
                        b
                );
            }
            if(x == EffectInfoType.twoTargets) {
                row[0] =  b;
                row[1] =  c;
                e.handleRow(e.getEffectInfo().getEffectInfoElement().get(0),row);
                assertEquals(
                        targetList.get(0),
                        b
                );
                assertEquals(
                        targetList.get(1),
                        c
                );
            }
            if(x == playerSquare){
                e.handleRow(e.getEffectInfo().getEffectInfoElement().get(0),row);
                assertEquals(
                        AD.getChosenSquare(),
                        board.getSquare(a.getPosition())
                );
            }
            if(x == EffectInfoType.threeTargets) {
                row[0] =  b;
                row[1] =  c;
                row[2] =  d;
                e.handleRow(e.getEffectInfo().getEffectInfoElement().get(0),row);
                assertEquals(
                        targetList.get(0),
                        b
                );
                assertEquals(
                        targetList.get(1),
                        c
                );
                assertEquals(
                        targetList.get(2),
                        d
                );
            }
            if(x == EffectInfoType.targetListBySquare) {
                row[0] = board.getSquare(b.getPosition());
                e.handleRow(e.getEffectInfo().getEffectInfoElement().get(0),row);
                for(Player t: targetList)
                    System.out.println("@ "+  t.getNickname());
                assertEquals(
                        targetList.size(),
                        1
                );

            }
            if(x == EffectInfoType.targetListByRoom) {
                row[0] = board.getSquare(a.getPosition());
                e.handleRow(e.getEffectInfo().getEffectInfoElement().get(0),row);
                for(Player t: targetList)
                assertEquals(
                        board.getSquare(t.getPosition()).getColor(),
                        board.getSquare(targetList.get(0).getPosition()).getColor()
                );
            }
            if(x == targetListBySameSquareOfPlayer) {
                row[0] = board.getSquare(a.getPosition());
                e.handleRow(e.getEffectInfo().getEffectInfoElement().get(0),row);
                for(Player t: targetList)
                    assertEquals(
                            board.getSquare(t.getPosition()),
                            board.getSquare(e.getActions().get(0).getActionInfo().getActionContext().getPlayer().getPosition())
                    );
            }
            if(x == targetListBySquareOfLastTarget) {
                e.getActions().get(0).getActionInfo().getActionDetails().getUserSelectedActionDetails().setTarget(b);
                //row[0] = board.getSquare(0,0);
                e.handleRow(e.getEffectInfo().getEffectInfoElement().get(0),row);
                assertEquals(
                        AD.getTarget(),
                        e.getActions().get(0).getActionInfo().getActionDetails().getUserSelectedActionDetails().getTarget()
                        );
            }
            if(x == squareByLastTargetSelected) {
                e.getActions().get(0).getActionInfo().getActionDetails().getUserSelectedActionDetails().setTarget(b);
                //row[0] = board.getSquare(0,0);
                e.handleRow(e.getEffectInfo().getEffectInfoElement().get(0),row);
                assertEquals(
                        AD.getChosenSquare(),
                        board.getSquare(b.getPosition())
                );
            }
            if(x == EffectInfoType.simpleSquareSelect) {
                row[0] = board.getSquare(0,0);
                e.handleRow(e.getEffectInfo().getEffectInfoElement().get(0),row);
                assertEquals(board.getSquare(0,0), AD.getChosenSquare());
            }
            if(x == targetListBySameSquareOfPlayer) {
                e.handleRow(e.getEffectInfo().getEffectInfoElement().get(0),row);
                for(Player t: targetList)
                    assertEquals(t.getPosition(), a.getPosition());
            }
            if(x == squareByTarget) {
                row[0] = b;
                e.handleRow(e.getEffectInfo().getEffectInfoElement().get(0),row);

                assertEquals(AD.getChosenSquare().getCoordinates().humanString(), board.getSquare(b.getPosition()).getCoordinates().humanString());
            }
            if( x== targetListByDistance1) {
                e.handleRow(e.getEffectInfo().getEffectInfoElement().get(0),row);
                for(Player t: targetList)
                {
                    assertEquals(
                            1,
                            board.distanceFromTo(
                                                AC.getPlayer().getPosition(),
                                                t.getPosition()
                                                )
                    );

                }
            }
            System.out.println("OK " + x);
            currentAction.getActionInfo().getActionDetails().getUserSelectedActionDetails().setTargetList(new ArrayList<>());
            currentAction.getActionInfo().getActionDetails().getUserSelectedActionDetails().setChosenSquare(null);;

        }
    }
    */
    @Test
    public void testExec() {
        Effect e = new Effect();
        e.getActions().add(new Damage());
        Player shooter = new Player();
        e.passContext(shooter,null,null);
        Player target = new Player();
        e.getActions().get(0).getActionInfo().getActionDetails().getUserSelectedActionDetails().setTarget(target);
        e.getActions().get(0).getActionInfo().getActionDetails().getFileSelectedActionDetails().getFileSettingData().add(
                "1");
        e.getActions().add(new Action());

        assertEquals(
                e.Exec().size(),
                1
        );
    }
    @Test
    public void testUsableInputs() throws Exception{
        // ogni carta deve avere almeno un input

        Board board = new Board("2",null,null);
        List<Player> pl  = new ArrayList<>();
        PlayersList playersList = new PlayersList();
        for(Square[] r: board.getMap())
            for(Square c:r)
            {
                if(c!=null) {

                    playersList.addPlayer(new Player());
                    playersList.getPlayers().get(
                            playersList.getPlayers().size()-1
                    ).setPositionWithoutNotify(new Position(c.getCoordinates().getX(), c.getCoordinates().getY()));
                    playersList.getPlayers().get(
                            playersList.getPlayers().size()-1
                    ).setNickname(c.getCoordinates().humanString());

                }
            }
        for(int j = 0; j < playersList.getPlayers().size();j++)
            System.out.println(playersList.getPlayersOnBoard().get(j).getPosition().humanString());
        for(Square[] r: board.getMap())
            for(Square c: r)
        for(int i = 1; i < 21;i++) {
            System.out.println("carta "+ i);
            WeaponCard w = new WeaponCard(""+i);
            playersList.getPlayersOnBoard().get(0).setPositionWithoutNotify(c.getCoordinates());
            w.passContext(playersList.getPlayersOnBoard().get(0),
                          playersList,
                          board);
            for(Effect e: w.getEffects()) {
                System.out.println("\t " + e.getEffectName());
              /*  assertNotEquals(
                      e.usableInputs().get(0).size(),
                      0*/
                //);
            }
        }

    }
    @Test
    public void testEffectV() {
        EffectV effectV = (new Effect()).buildEffectV();
    }
    @Test
    public void testDescription() {
        Effect e = new Effect("",new ArrayList<>());
        e.setDescription("test");
        e.setActions(new ArrayList<>());
        assertEquals(
                e.getActions(),
                new ArrayList<>()
        );
        assertEquals(
                "test",
                e.getDescription()
        );
    }

}