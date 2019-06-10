package it.polimi.se2019.model;

import it.polimi.se2019.model.enumerations.CardinalPoint;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.enumerations.SquareSide;
import it.polimi.se2019.model.enumerations.UsableInputTableRowType;

import java.util.ArrayList;
import java.util.List;

public class PreConditionMethodsInverted {
    public List<Object> targetInLastSquareSelected(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoElement> inputSlots) {
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
    public List<Object> distanceFromOriginalPositionLessThan2(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoElement> inputSlots) {
        List<Object> retVal = new ArrayList<>();
        System.out.println("##############");
        Position A = null;
        System.out.println("##############");
        if(actionDetails.getUserSelectedActionDetails().getTarget() != null) {
            A = actionDetails.getUserSelectedActionDetails().getTarget().getPosition();
        }
        System.out.println("##############");
        try {
            if (type.equals(UsableInputTableRowType.typeSquare)) {
                for (Square[] riga : actionContext.getBoard().getMap())
                    for (Square cella : riga) {
                        Position B = cella.getCoordinates();
                        System.out.println("-");
                        System.out.println("# la distanza [(" + A.getY() + ", " + A.getX() + ") --> (" + B.getY() + ", " + B.getX() + ")] tra i due square è minore di 2?");
                        float Distance = (A.getX() - B.getX()) * (A.getX() - B.getX()) +
                                (A.getY() - B.getY()) * (A.getY() - B.getY());
                        System.out.println("la distanza è " + Distance);
                        if (Distance <= 4) {
                            System.out.println("si");
                        } else {
                            System.out.println("no");
                        }
                        if (Distance <= 4) retVal.add(cella);
                    }
            }
        }catch(Exception e){System.out.println("@@@" + e);}
        if(type.equals(UsableInputTableRowType.typePlayer)) {
            for(Player p: actionContext.getPlayerList().getPlayers())
                retVal.add(p);
        }

        return retVal;
    }
    public List<Object> distanceFromOriginalSquareIs1(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoElement> inputSlots) {
        /*Target.square */

        List<Object> retVal = new ArrayList<>();
        if (type.equals(UsableInputTableRowType.typePlayer)) {
            for (Player target : actionContext.getPlayerList().getPlayers()) {
                Position A = actionContext.getPlayer().getPosition();
                Position B = target.getPosition();
                int Distance = (A.getX() - B.getX()) * (A.getX() - B.getX()) +
                        (A.getY() - B.getY()) * (A.getY() - B.getY());

                System.out.println("# La distanza con "+ target.getNickname() + " è " + Distance);
                if (Distance == 1) {
                    retVal.add(target);
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
        System.out.println(retVal);
        return retVal;
    }

    public List<Object> alwaysTrue(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoElement> inputSlots) {
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

    public List<Object> alwaysFalse(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoElement> inputSlots) {
        List<Object> retVal = new ArrayList<>();
        return retVal;
    }

    public List<Object> targetOnYourSquare(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoElement> inputSlots) {
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

    public List<Object> targetNotOnYourSquare(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoElement> inputSlots) {
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
    public List<Object> youCantSee(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoElement> inputSlots) {

        List<Object> retVal = new ArrayList<>();
        Player me = actionContext.getPlayer();
        PlayersList playersList = actionContext.getPlayerList();
        try {
            for (Player t : playersList.getPlayers()) {
                if (actionContext.getBoard().getSquare(t.getPosition()).getColor() != actionContext.getBoard().getSquare(me.getPosition()).getColor()) {
                    retVal.add(t);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        ;
        return retVal;
    }
    public List<Object> youCanSeeThatSquare(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoElement> inputSlots) {
        List<Object> retVal = new ArrayList<>();
        Player me = actionContext.getPlayer();
        Position playerPosition = me.getPosition();
        Square   playerSquare   = actionContext.getBoard().getSquare(playerPosition);
        if(type.equals(UsableInputTableRowType.typeSquare)) {
            for (Square[] riga : actionContext.getBoard().getMap()) {
                for (Square cella : riga) {
                    if (
                            !playerSquare.getSide(CardinalPoint.north).equals(SquareSide.door) &&
                                    !playerSquare.getSide(CardinalPoint.south).equals(SquareSide.door) &&
                                    !playerSquare.getSide(CardinalPoint.west).equals(SquareSide.door) &&
                                    !playerSquare.getSide(CardinalPoint.east).equals(SquareSide.door)) {
                        /*posizione senza porte*/
                        Square targetSquare = cella;
                        if (targetSquare.getColor() != playerSquare.getColor())
                            retVal.add(targetSquare);
                    } else {
                        Square northSide = null;
                        Square southSide = null;
                        Square eastSide = null;
                        Square westSide = null;

                        if (playerSquare.getSide(CardinalPoint.north).equals(SquareSide.door)) {
                            if (playerPosition.getX() > 0)
                                northSide = actionContext.getBoard().getSquare(playerPosition.getY(), playerPosition.getX() - 1);
                        }

                        if (playerSquare.getSide(CardinalPoint.east).equals(SquareSide.door)) {
                            if (playerPosition.getY() < 2)
                                eastSide = actionContext.getBoard().getSquare(playerPosition.getY() + 1, playerPosition.getX());
                        }

                        if (playerSquare.getSide(CardinalPoint.west).equals(SquareSide.door)) {
                            if (playerPosition.getY() > 0)
                                westSide = actionContext.getBoard().getSquare(playerPosition.getY() - 1, playerPosition.getX());
                        }
                        if (playerSquare.getSide(CardinalPoint.south).equals(SquareSide.door)) {
                            if (playerPosition.getX() < 3)
                                southSide = actionContext.getBoard().getSquare(playerPosition.getY(), playerPosition.getX() + 1);
                        }

                        Square targetSquare = cella;
                        List<Character> colors = new ArrayList<>();
                        colors.add(playerSquare.getColor());
                        if (northSide != null)
                            colors.add(northSide.getColor());
                        if (eastSide != null)
                            colors.add(eastSide.getColor());
                        if (westSide != null)
                            colors.add(westSide.getColor());
                        if (southSide != null)
                            colors.add(southSide.getColor());
                        //System.out.println("colore square player [ " + me.getPosition().getY() + " , " + me.getPosition().getX() + " ] " + actionContext.getBoard().getSquare(me.getPosition()).getColor());
                        //System.out.println("colori disoonibili : " + colors);


                        if (!colors.contains(targetSquare.getColor())) {
                            retVal.add(cella);
                        }

                    }
                }
            }
        }
        if(type.equals(UsableInputTableRowType.typePlayer)) {

            for(Player t: actionContext.getPlayerList().getPlayers()) {
                retVal.add(t);
            }

        }
        System.out.println("         @@@ " + retVal);
        return retVal;
    }
    public List<Object> moveOtherPlayer(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoElement> inputSlots) {

        List<Object> retVal = new ArrayList<>();
        for(Player t: actionContext.getPlayerList().getPlayers()) {
            retVal.add(t);
        }
        retVal.remove(actionContext.getPlayer());
        return retVal;
    }
    public List<Object> youCanSee(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoElement> inputSlots) {

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
    public List<Object> distanceOfTargetFromPlayerSquareLessThan2Moves(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoElement> inputSlots)
    {
        List<Object> retVal  = new ArrayList<>();
        Player me = actionContext.getPlayer();
        try {
            for (Player t : actionContext.getPlayerList().getPlayers()) {
                System.out.println(":::" + t.getPosition().getX() + " , " + t.getPosition().getY());
                if(!t.equals(me))
                    if (actionContext.getBoard().distanceFromTo(me.getPosition(), t.getPosition()) == 2) {
                        System.out.println(":::");
                        retVal.add(t);
                        System.out.println(":::");
                    }

            }
        }catch (Exception e){
            System.out.println("----- eccezione " + e);
        }
        System.out.println("***");
        System.out.println(retVal);
        System.out.println("***");
        return retVal;
    }
    public List<Object> sameCardinalDirectionOfTargets(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots)
    {
        System.out.println("carico lista input disponibili sameCardinal");

        List<Object> retVal = new ArrayList<>();
        try {
            int counter = 0;
            int i = 0;
            Player previous = null;
            for (counter = 0; counter <= ((List<Object>) inputs).size(); counter++) {
            }
            System.out.println("size: " + counter);
            for (i = counter -1; i > 0; i--) {
                System.out.println(":");
                System.out.println(inputSlots.get(i-1));
                System.out.println(":");
                if (inputSlots.get(i).equals(EffectInfoType.singleTarget)) {
                    System.out.println(": found");
                    previous = (Player) ((Object[])((List<Object>) inputs).get(i-1))[0];
                    System.out.println(":");
                }
            }
            System.out.println("*" + previous);
            if (previous == null) {
                /*la lista è ancora piena*/
                for (Player p : actionContext.getPlayerList().getPlayers()) {
                    retVal.add(p);
                }
            } else {
                /*è gia stato consumato il primo attacco*/
                System.out.println("*");
                for (Player current : actionContext.getPlayerList().getPlayers()) {
                    System.out.println("**");
                    if ((
                            ((current.getPosition().getY() - previous.getPosition().getY()) == 0) &&
                                    (current.getPosition().getY() - actionContext.getPlayer().getPosition().getY()) == 0)
                            ||
                            ((current.getPosition().getX() - previous.getPosition().getX()) == 0) &&
                                    (current.getPosition().getX() - actionContext.getPlayer().getPosition().getX()) == 0)
                    {

                        retVal.add(current);

                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("***");
        System.out.println(retVal);
        System.out.println("***");
        return retVal;
    }
    public List<Object> notInYourRoom(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoElement> inputSlots)
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
    public List<Object> notFirstExecuted(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoElement> inputSlots)
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
    public List<Object> distanceOfTargetFromPlayerSquareMoreThan2Moves(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoElement> inputSlots)
            throws Exception
    {
        List<Object> retVal = new ArrayList<>();

        Player user   = actionContext.getPlayer();
        for(Player target: actionContext.getPlayerList().getPlayers())
            if(actionContext.getBoard().distanceFromTo(target.getPosition(), user.getPosition()) == 2)
                retVal.add(target);
        System.out.println("***");
        System.out.println(retVal);
        System.out.println("***");
        return retVal;
    }
    public List<Object> notPreviousTarget(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoElement> inputSlots)
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
    public List<Object> distanceOfTargetFromPlayerSquareIs1(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoElement> inputSlots)  {
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
