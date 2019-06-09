package it.polimi.se2019.model;

import it.polimi.se2019.model.enumerations.UsableInputTableRowType;

import java.util.ArrayList;
import java.util.List;

public class PreConditionMethodsInverted {
    public List<Object> targetInLastSquareSelected(ActionContext actionContext, UsableInputTableRowType type, Integer cardinality, Object inputs, List<EffectInfoElement> inputSlots) {
        List<Object> retVal = new ArrayList<>();

        if (type.equals(UsableInputTableRowType.typePlayer)) {
            for (Player t : actionContext.getPlayerList().getPlayers())
                retVal.add(t);
        }
        if (type.equals(UsableInputTableRowType.typeSquare)) {
            for (Square y[] : actionContext.getBoard().getMap()) {
                for (Square x : y) {
                    retVal.add(x);
                }
            }
        }

        return retVal;
    }

    public List<Object> distanceFromOriginalSquareIs1(ActionContext actionContext, UsableInputTableRowType type, Integer cardinality, Object inputs, List<EffectInfoElement> inputSlots) {
        /*Target.square */

        List<Object> retVal = new ArrayList<>();
        if (type.equals(UsableInputTableRowType.typePlayer)) {
            for (Player target : actionContext.getPlayerList().getPlayers()) {
                Position A = actionContext.getPlayer().getPosition();
                Position B = target.getPosition();
                int Distance = (A.getX() - B.getX()) * (A.getX() - B.getX()) +
                        (A.getY() - B.getY()) * (A.getY() - B.getY());

                System.out.println("# " + Distance);
                if (Distance == 1) {
                    retVal.add(B);
                }
            }
        }
        if (type.equals(UsableInputTableRowType.typeSquare)) {
            for (Square y[] : actionContext.getBoard().getMap()) {
                for (Square x : y) {
                    retVal.add(x);
                }
            }
        }
        return retVal;
    }

    public List<Object> alwaysTrue(ActionContext actionContext, UsableInputTableRowType type, Integer cardinality, Object inputs, List<EffectInfoElement> inputSlots) {
        List<Object> retVal = new ArrayList<>();
        System.out.println("tipo input " + type);
        if (type.equals(UsableInputTableRowType.typePlayer)) {
            System.out.println("A");
            for (Player t : actionContext.getPlayerList().getPlayers())
                retVal.add(t);
        }
        if (type.equals(UsableInputTableRowType.typeSquare)) {
            System.out.println("B");
            for (Square y[] : actionContext.getBoard().getMap()) {
                for (Square x : y) {
                    retVal.add(x);
                }
            }
        }
        return retVal;
    }

    public List<Object> alwaysFalse(ActionContext actionContext, UsableInputTableRowType type, Integer cardinality, Object inputs, List<EffectInfoElement> inputSlots) {
        List<Object> retVal = new ArrayList<>();
        return retVal;
    }

    public List<Object> targetOnYourSquare(ActionContext actionContext, UsableInputTableRowType type, Integer cardinality, Object inputs, List<EffectInfoElement> inputSlots) {
        List<Object> retVal = new ArrayList<>();
        Player me = actionContext.getPlayer();
        PlayersList playersList = actionContext.getPlayerList();
        try {
            for (Player t : playersList.getPlayers()) {
                if (me.getPosition().equals(t.getPosition())) {
                    retVal.add(t);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        ;
        return retVal;


    }

    public List<Object> targetNotOnYourSquare(ActionContext actionContext, UsableInputTableRowType type, Integer cardinality, Object inputs, List<EffectInfoElement> inputSlots) {
        List<Object> retVal = new ArrayList<>();
        Player me = actionContext.getPlayer();
        PlayersList playersList = actionContext.getPlayerList();
        try {
            for (Player t : playersList.getPlayers()) {
                if (!me.getPosition().equals(t.getPosition())) {
                    retVal.add(t);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        ;
        return retVal;

    }

    public List<Object> youCanSee(ActionContext actionContext, UsableInputTableRowType type, Integer cardinality, Object inputs, List<EffectInfoElement> inputSlots) {

        List<Object> retVal = new ArrayList<>();
        Player me = actionContext.getPlayer();
        PlayersList playersList = actionContext.getPlayerList();
        try {
            for (Player t : playersList.getPlayers()) {
                if (actionContext.getBoard().getSquare(t.getPosition()).getColor() == actionContext.getBoard().getSquare(me.getPosition()).getColor()) {
                    retVal.add(t);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        ;
        return retVal;
    }

    public List<Object> sameCardinalDirectionOfTargets(ActionContext actionContext, UsableInputTableRowType type, Integer cardinality, Object inputs, List<EffectInfoElement> inputSlots)
    {
        // TODO : rivedi
        // restituisce tutti i possibili insiemi di target lungo gli stessi punti cardinali
        List<Object> retVal = new ArrayList<>();
        Player me = actionContext.getPlayer();
        Board  board = actionContext.getBoard();
        List<Object> retValNORTH = new ArrayList<>();
        List<Object> retValSOUTH = new ArrayList<>();
        List<Object> retValEAST =  new ArrayList<>();
        List<Object> retValWEST = new ArrayList<>();

        // north;
        for(int y = me.getPosition().getY();y > 0; y --) {
            int x = me.getPosition().getX();
            for (Player p : actionContext.getPlayerList().getPlayers()) {
                if (p.getPosition().equals(new Position(x,y))) {
                    retValNORTH.add(p);
                }
                }
        }
        // south
        for(int y = me.getPosition().getY();y < 3; y ++) {
            int x = me.getPosition().getX();
            for (Player p : actionContext.getPlayerList().getPlayers()) {
                if (p.getPosition().equals(new Position(x,y))) {
                    retValSOUTH.add(p);
                }
            }
        }
        // east
        for(int x = me.getPosition().getX();x > 0; x --) {
            int y = me.getPosition().getY();
            for (Player p : actionContext.getPlayerList().getPlayers()) {
                if (p.getPosition().equals(new Position(x,y))) {
                    retValEAST.add(p);
                }
            }
        }
        // west
        for(int x = me.getPosition().getX();x > 0; x --) {
            int y = me.getPosition().getY();
            for (Player p : actionContext.getPlayerList().getPlayers()) {
                if (p.getPosition().equals(new Position(x,y))) {
                    retValWEST.add(p);
                }
            }
        }
        retVal.add(retValNORTH);
        retVal.add(retValEAST);
        retVal.add(retValWEST);
        retVal.add(retValSOUTH);


        // a questo punto effectInfoElement contiene un riferimento
        return retVal;
    }
    public List<Object> notInYourRoom(ActionContext actionContext, UsableInputTableRowType type, Integer cardinality, Object inputs, List<EffectInfoElement> inputSlots)
    {
        List<Object> retVal = new ArrayList<>();
        Player me = actionContext.getPlayer();
        char playerRoom = actionContext.getBoard().getSquare(me.getPosition()).getColor();
        for(Player t: actionContext.getPlayerList().getPlayers()) {
            if(actionContext.getBoard().getSquare(t.getPosition()).getColor() != playerRoom) {
                retVal.add(t);
            }
        }
        return retVal;
    }
    public List<Object> notFirstExecuted(ActionContext actionContext, UsableInputTableRowType type, Integer cardinality, Object inputs, List<EffectInfoElement> inputSlots)
    {
        // TODO: passare il record corrente della playerHistory
        List<Object> retVal = new ArrayList<>();
        for(int x = (actionContext.getPlayer().getPlayerHistory().historyElementList.size()-1);x>=0;x--){

        }

        if(type.equals(UsableInputTableRowType.typeSquare)) {

        }
        if(type.equals(UsableInputTableRowType.typePlayer)) {

        }
        return retVal;
    }
    public List<Object> distanceOfTargetFromPlayerSquareMoreThan2Moves(ActionContext actionContext, UsableInputTableRowType type, Integer cardinality, Object inputs, List<EffectInfoElement> inputSlots)
    throws Exception
    {
        List<Object> retVal = new ArrayList<>();

        Player user   = actionContext.getPlayer();
        for(Player target: actionContext.getPlayerList().getPlayers())
            if(actionContext.getBoard().distanceFromTo(target.getPosition(), user.getPosition()) == 2)
                retVal.add(target);


        return retVal;
    }
    public List<Object> notPreviousTarget(ActionContext actionContext, UsableInputTableRowType type, Integer cardinality, Object inputs, List<EffectInfoElement> inputSlots)
    {

        List<Object> retVal = new ArrayList<>();
        for(Player t:actionContext.getPlayerList().getPlayers())
            retVal.add(t);
        Player previousTarget = null;
        System.out.println("***********************************"  + inputs);
        for(Object row : (List<Object>) inputs) {
            if(((Object[]) row)[0].getClass().toString().equals("class it.polimi.se2019.model.Player")) {
                System.out.println("***********************************" + ((Object[]) row)[0].getClass());
            retVal.remove(((Object[]) row)[0]);
            break;
            }
        }
        return retVal;

    }
        public List<Object> distanceOfTargetFromPlayerSquareIs1(ActionContext actionContext, UsableInputTableRowType type, Integer cardinality, Object inputs, List<EffectInfoElement> inputSlots)  {
        List<Object> playerList = new ArrayList<>();

        Player user   = actionContext.getPlayer();

        Square[][] map = actionContext.getBoard().getMap();

        for(int x = (user.getPosition().getX() - 1); x < (user.getPosition().getX() + 1);x++) {
            for(int y = (user.getPosition().getX() - 1); y < (user.getPosition().getX() + 1);y++) {

                for(Player possibile: actionContext.getPlayerList().getPlayers()) {
                    if((possibile.getPosition().getX() == x) && (possibile.getPosition().getY() == y)) {
                        if(!playerList.contains(possibile)) {
                            playerList.add(possibile);
                        }
                    }

                }

            }

        }

        return playerList;
    }
}
