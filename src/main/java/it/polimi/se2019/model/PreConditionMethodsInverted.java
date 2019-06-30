package it.polimi.se2019.model;

import it.polimi.se2019.model.enumerations.CardinalPoint;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.enumerations.UsableInputTableRowType;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.se2019.model.enumerations.EffectInfoType.singleTarget;
import static it.polimi.se2019.model.enumerations.EffectInfoType.targetListBySquare;
import static it.polimi.se2019.model.enumerations.UsableInputTableRowType.typePlayer;
import static it.polimi.se2019.model.enumerations.UsableInputTableRowType.typeSquare;

public class PreConditionMethodsInverted {
    /********/
    public List<Object> isScannerMode(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {List<Object> retVal = new ArrayList<>();
        return alwaysTrue(actionContext,type,actionDetails,inputs,inputSlots,contextEffect);
    }

    public List<Object> isPunisherMode(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {List<Object> retVal = new ArrayList<>();
        return alwaysTrue(actionContext,type,actionDetails,inputs,inputSlots,contextEffect);
    }


    public List<Object> isBarbequeMode(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {List<Object> retVal = new ArrayList<>();
        return alwaysTrue(actionContext,type,actionDetails,inputs,inputSlots,contextEffect);
    }
    public List<Object> isReaperMode(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {List<Object> retVal = new ArrayList<>();
        return alwaysTrue(actionContext,type,actionDetails,inputs,inputSlots,contextEffect);
    }
    public List<Object> isFocusShot(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {List<Object> retVal = new ArrayList<>();
        return alwaysTrue(actionContext,type,actionDetails,inputs,inputSlots,contextEffect);
    }
    public List<Object> isTurretTripod(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {
        return alwaysTrue(actionContext, type, actionDetails, inputs, inputSlots, contextEffect);
    }
    public List<Object> isNanoTracerMode(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {List<Object> retVal = new ArrayList<>();
        return alwaysTrue(actionContext, type, actionDetails, inputs, inputSlots, contextEffect);
    }


    public List<Object> withChainReaction(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {List<Object> retVal = new ArrayList<>();
        return alwaysTrue(actionContext, type, actionDetails, inputs, inputSlots, contextEffect);
    }
    public List<Object> withHighVoltage(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {List<Object> retVal = new ArrayList<>();
        return alwaysTrue(actionContext, type, actionDetails, inputs, inputSlots, contextEffect);
    }
    public List<Object> isBlackHole(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {List<Object> retVal = new ArrayList<>();
        return alwaysTrue(actionContext, type, actionDetails, inputs, inputSlots, contextEffect);
    }
    public List<Object> isLongBarrelMode(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {List<Object> retVal = new ArrayList<>();
        return alwaysTrue(actionContext, type, actionDetails, inputs, inputSlots, contextEffect);
    }
    public List<Object> withRocketJump(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {List<Object> retVal = new ArrayList<>();
        return alwaysTrue(actionContext, type, actionDetails, inputs, inputSlots, contextEffect);
    }
    public  List<Object> withFragmentingWarHead(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {List<Object> retVal = new ArrayList<>();
        return alwaysTrue(actionContext, type, actionDetails, inputs, inputSlots, contextEffect);
    }
    public  List<Object> inRocketFistMode(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {List<Object> retVal = new ArrayList<>();
        return alwaysTrue(actionContext, type, actionDetails, inputs, inputSlots, contextEffect);
    }
    public  List<Object> withShadowStep(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {List<Object> retVal = new ArrayList<>();
        return alwaysTrue(actionContext, type, actionDetails, inputs, inputSlots, contextEffect);
    }

    public  List<Object>  withChargedShot (ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {List<Object> retVal = new ArrayList<>();
        return alwaysTrue(actionContext, type, actionDetails, inputs, inputSlots, contextEffect);
    }
    public  List<Object> isPolverizeMode(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {List<Object> retVal = new ArrayList<>();
        return alwaysTrue(actionContext, type, actionDetails, inputs, inputSlots, contextEffect);
    }

    /*********/
    public List<Object> pureCardinalMovement(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {
        List<Object> retVal = new ArrayList<>();
        if(type.equals(typePlayer)) {
            // tutti i player possiedono almeno un punto in cui muoversi dritto
            // si da per scontato che venga scelto prima il player
            // vedi distancefromOriginalpositionis1
            for(Player p: actionContext.getPlayerList().getPlayersOnBoard())
            {
                retVal.add(p);
            }
        }
        if(type.equals(typeSquare)) {
            if(actionDetails.getUserSelectedActionDetails().getTargetList().size() > 0)
            {
                // target selezionato
                Player target = actionDetails.getUserSelectedActionDetails().getTarget();
                for(Square[] R: actionContext.getBoard().getMap())
                    for(Square C: R) {
                        if(C != null) {
                            if (
                                    ((C.getCoordinates().getX() - target.getPosition().getX()) == 0) ||
                                            ((C.getCoordinates().getY() - target.getPosition().getY()) == 0)
                            )       {
                                retVal.add(C);
                            }
                        }
                    }

            } else {
                // target non selezionato
                // lista dei quadrati "perpendicolari a un player"
                for (Square[] R : actionContext.getBoard().getMap())
                    for (Square C : R) {
                        if (C != null) {
                            for(Player p: actionContext.getPlayerList().getPlayersOnBoard()) {
                                if (
                                        ((C.getCoordinates().getX() - p.getPosition().getX()) == 0) ||
                                                ((C.getCoordinates().getY() - p.getPosition().getY()) == 0)
                                )       {
                                    if(!retVal.contains(C))
                                        retVal.add(C);
                                }
                            }
                        }
                    }

            }
        }
        return retVal;
    }


    public List<Object> youWillSee(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {
        List<Object> retVal = new ArrayList<>();
        Square   newSquare   = actionDetails.getUserSelectedActionDetails().getChosenSquare();
        if( newSquare != null) {
            // square già selezionato
            Position newPosition = actionDetails.getUserSelectedActionDetails().getChosenSquare().getCoordinates();
            Position oldPosition = new Position(
                    actionContext.getPlayer().getPosition().getX(),
                    actionContext.getPlayer().getPosition().getY()
            );

            actionContext.getPlayer().setPosition(newPosition);
            retVal = youCanSee(actionContext, typePlayer, actionDetails, inputs, inputSlots, contextEffect);
            actionContext.getPlayer().setPosition(oldPosition);
        } else {
            // square non ancora selezionato
            if(type == typePlayer) {
                // un player per cui esiste almeno uno square in cui è visibile
                // --> tutti gli square
                for(Player p: actionContext.getPlayerList().getPlayersOnBoard()) {
                    retVal.add(p);
                }
            }
            if(type == typeSquare) {
                // uno square su cui posto il player gli rende visibile almeno un giocatore
                for(Square[] r: actionContext.getBoard().getMap()) {
                    for(Square c: r) {
                        if (c != null)
                        {
                            // cambio posizione nel contesto da player.pos a c.coord
                            List<Object> buffer = new ArrayList<>();
                            Position oldPosition = new Position(
                                    actionContext.getPlayer().getPosition().getX(),
                                    actionContext.getPlayer().getPosition().getY()
                            );
                            actionContext.getPlayer().setPosition(c.getCoordinates());
                            // chiamo you can see
                            int n = youCanSee(actionContext, typePlayer, actionDetails, inputs, inputSlots, contextEffect).size() - 1; // numero di player visibili meno il player
                            System.out.println("<SERVER> BUFFER DI YOUWILLSEE " + n);
                            // ripristino il contesto
                            actionContext.getPlayer().setPosition(oldPosition);
                            // (return di you can see).size > 0 --> aggiungo C  a retVal
                            if (n > 0) retVal.add(c);
                        }
                    }
                }
            }
        }
        return retVal;
    }
    public List<Object> differentSingleTargets(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {
        List<Object> retVal = new ArrayList<>();
        if(type.equals(typePlayer)) {
            System.out.println("entro in");
            for(Player p: actionContext.getPlayerList().getPlayersOnBoard()) {



                System.out.println("@" + p.getNickname());
                retVal.add(p);
            }
            int eieCounter = 0;
            int singleTargetCounter = 0;
            for( EffectInfoType slot: inputSlots) {
                if(slot == singleTarget)
                    singleTargetCounter++;
            }

            for (Object[] r : (List<Object[]>) inputs)
            {
                System.out.println("ROW");
                System.out.println("{");
                System.out.println("input slot :" + inputSlots.get(eieCounter) + "| " + inputSlots.get(eieCounter).getClass());
                System.out.println("slot content : " + r[0]);
                System.out.println("}");
                if( inputSlots.get(eieCounter).toString().equals("singleTarget")) {
                    retVal.remove((Player) r[0]);
                }
                eieCounter++;
            }
            System.out.println("@@@@@@@@@@ò" + retVal);
            if(singleTargetCounter  < retVal.size())
                return retVal;
            else
                return new ArrayList<>(); // return vuoto se non ci sono abbastanza target diversi
        }


        if(type.equals(typeSquare)) {
            for(Square[] s: actionContext.getBoard().getMap()) {
                for(Square t: s)
                    retVal.add(t);
            }

        }
        return retVal;
    }
    public List<Object> distanceOfTargetFromPlayerExactlyOne(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {
        return distanceOfTargetFromPlayerSquareIs1(actionContext,type,actionDetails,inputs,inputSlots,contextEffect);
    }

    public List<Object> targetInLastSquareSelected(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {
        List<Object> retVal = new ArrayList<>();

        if (type.equals(UsableInputTableRowType.typePlayer)) {
            for (Player t : actionContext.getPlayerList().getPlayersOnBoard())
                retVal.add(t);
        }
        if (type.equals(typeSquare)) {
            for (Square y[] : actionContext.getBoard().getMap()) {
                for (Square x : y) {
                    retVal.add(x);
                }
            }
        }

        return retVal;
    }

    public List<Object> youCanSeeTP(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {
        List<Object> retVal = new ArrayList<>();
        Player me = actionContext.getPlayer();
        PlayersList playersList = actionContext.getPlayerList();
        if(type.equals(typePlayer)) {
            try {
                PreConditionMethods preConditionMethods = new PreConditionMethods();
                for (Player t : playersList.getPlayersOnBoard()) {
                    ;
                    ActionDetails actionDetails1 = new ActionDetails();
                    actionDetails1.getUserSelectedActionDetails().setTarget(t);
                    Position oldPos = new Position(
                            t.getPosition().getX(),
                            t.getPosition().getY()
                    );
                    t.setPositionWithoutNotify(t.getTemporaryPosition());
                    if (preConditionMethods.youCanSee(actionDetails1, actionContext)) {
                        retVal.add(t);
                    }
                    t.setPositionWithoutNotify(oldPos);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            ;
        }
        if(type.equals(typeSquare)) {
            for(Square[] r: actionContext.getBoard().getMap()){
                for (Square c: r){
                    if(c!=null)
                        retVal.add(c);
                }
            }
        }
        return retVal;
    }

        public List<Object>  sameCardinalDirectionOfTargets(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {
        List<Object> retVal = new ArrayList<>();
        List<Player> listOfTargets = new ArrayList<>();
        int realCounter = 0;
        int singleTargets = 0;
        for(EffectInfoType e : inputSlots)
            if((e == singleTarget) || (e == targetListBySquare))
                singleTargets++;

        for(Object[] e : (List<Object[]>) inputs)   {
            if(inputSlots.get(realCounter).equals(singleTarget)) {
                listOfTargets.add(
                        (Player) e[0]
                );

            }
            if(inputSlots.get(realCounter).equals(targetListBySquare)) {
                Square A = (Square) e[0];
                Player first = null;
                for(Player p: actionContext.getPlayerList().getPlayersOnBoard()) {
                    if(p.getPosition().equalPositions(A.getCoordinates())) {
                        first = p;
                    }
                }
                listOfTargets.add(
                            first
                        );

            }
            realCounter++;
        }

        if(listOfTargets.size() == 0 ) {
            int minimumTargets = singleTargets;
            // lista vuota: tutti i target possibili
            Position pPos = actionContext.getPlayer().getPosition();
            List<Object> buffer = new ArrayList<>();
            // north
            buffer = new ArrayList<>();
            for(Player p : actionContext.getPlayerList().getPlayersOnBoard()) {
                if(!p.getPosition().equalPositions(pPos) && !buffer.contains(p))
                {
                    // non è il player
                    if(p.getPosition().getY() == pPos.getY())
                    if(p.getPosition().getX() < pPos.getX()) {
                        buffer.add(p);
                    }
                }
            }
            if(buffer.size() >= minimumTargets) {     // ci sono almeno due target sulla stessa direzione
                for(Object o: buffer)
                    retVal.add(o);
            }
            // south
            buffer = new ArrayList<>();
            for(Player p : actionContext.getPlayerList().getPlayersOnBoard()) {
                if(!p.getPosition().equalPositions(pPos) && !buffer.contains(p))
                {
                    // non è il player
                    if(p.getPosition().getY() == pPos.getY())
                        if(p.getPosition().getX() > pPos.getX()) {
                            buffer.add(p);
                        }
                }
            }
            if(buffer.size() >= minimumTargets) {     // ci sono almeno due target sulla stessa direzione
                for(Object o: buffer)
                    retVal.add(o);
            }
            // east
            buffer = new ArrayList<>();
            for(Player p : actionContext.getPlayerList().getPlayersOnBoard()) {
                if(!p.getPosition().equalPositions(pPos) && !buffer.contains(p))
                {
                    // non è il player
                    if(p.getPosition().getY() > pPos.getY())
                        if(p.getPosition().getX() == pPos.getX()) {
                            buffer.add(p);
                        }
                }
            }
            if(buffer.size() >= minimumTargets) {     // ci sono almeno due target sulla stessa direzione
                for(Object o: buffer)
                    retVal.add(o);
            }
            // west
            buffer = new ArrayList<>();
            for(Player p : actionContext.getPlayerList().getPlayersOnBoard()) {
                if(!p.getPosition().equalPositions(pPos) && !buffer.contains(p))
                {
                    // non è il player
                    if(p.getPosition().getY() < pPos.getY())
                        if(p.getPosition().getX() == pPos.getX()) {
                            buffer.add(p);
                        }
                }
            }
            if(buffer.size() >= minimumTargets) {     // ci sono almeno due target sulla stessa direzione
                for(Object o: buffer)
                    retVal.add(o);
            }
        } else {
            if(listOfTargets.size() == 1) {
                Position pPos = actionContext.getPlayer().getPosition();
                Position tPos =  listOfTargets.get(0).getPosition();

                CardinalPoint direction = null;             // inizializzo la direzione
                int deltaX = pPos.getY() - tPos.getY();
                int deltaY = pPos.getX() - tPos.getX();

                if(deltaX < 0) {
                    // east
                    direction = CardinalPoint.east;
                }
                if(deltaX > 0) {
                    // west
                    direction = CardinalPoint.west;
                }
                if(deltaY < 0) {
                    // south
                    direction = CardinalPoint.south;
                }
                if(deltaY > 0) {
                    // north
                    direction = CardinalPoint.north;
                }
                Player p = actionContext.getPlayer();
                for(Player t: actionContext.getPlayerList().getPlayersOnBoard()) {

                    if(!t.equals(listOfTargets.get(0)) && !t.equals(actionContext.getPlayer())) {
                        if(direction == CardinalPoint.south)
                        {
                            if((t.getPosition().getY() ==  p.getPosition().getY())) {
                                if((t.getPosition().getX() > p.getPosition().getX())) {
                                    if(!retVal.contains(t))
                                        retVal.add(t);
                                }
                            }
                        }
                        if(direction == CardinalPoint.north)
                        {
                            if((t.getPosition().getY() ==  p.getPosition().getY())) {
                                if((t.getPosition().getX() < p.getPosition().getX())) {
                                    if(!retVal.contains(t))
                                        retVal.add(t);
                                }
                            }
                        }
                        if(direction == CardinalPoint.east)
                        {
                            if((t.getPosition().getX() ==  p.getPosition().getX())) {
                                if((t.getPosition().getY() > p.getPosition().getY())) {
                                    if(!retVal.contains(t))
                                        retVal.add(t);
                                }
                            }
                        }
                        if(direction == CardinalPoint.west)
                        {
                            if((t.getPosition().getX() ==  p.getPosition().getX())) {
                                if((t.getPosition().getY() < p.getPosition().getY())) {
                                    if(!retVal.contains(t))
                                        retVal.add(t);
                                }
                            }
                        }
                    }
                }
                System.out.println("direzione:" + direction);
            }
        }



        return retVal;
    }


    public List<Object> distanceFromOriginalPositionIs1 (ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {

        List<Object> retVal = new ArrayList<>();
        try {
            if (type.equals(typeSquare)) {

                if (actionDetails.getUserSelectedActionDetails().getTargetList().size() > 0) {
                    System.out.println("// target list not empty");
                    System.out.println("// pos: " + actionDetails.getUserSelectedActionDetails().getTarget().getPosition().humanString());
                    // Position chosenSquarePosition = actionDetails.getUserSelectedActionDetails().getChosenSquare().getCoordinates();
                    for (Square[] R : actionContext.getBoard().getMap()) {
                        for (Square C : R) {
                            if (C != null) {
                                System.out.println("> POSIZIONE  " + C.getCoordinates().humanString() + ": " + actionContext.getBoard().distanceFromTo(
                                        C.getCoordinates(),
                                        actionDetails.getUserSelectedActionDetails().getTarget().getPosition()
                                ));
                                if (actionContext.getBoard().distanceFromTo(
                                        C.getCoordinates(),
                                        actionDetails.getUserSelectedActionDetails().getTarget().getPosition()
                                ) == (1 + 0)) {
                                    // la distanza è 1
                                    // controllo che ci sia un player li sopra
                                    /*for (Player P : actionContext.getPlayerList().getPlayersOnBoard()) {
                                        System.out.println("testo " + P.getNickname() + " : " + P.getPosition().humanString());
                                        if (P.getPosition().equals(C.getCoordinates())) {
                                            System.out.println("la posizione è uguale");
                                            if (!retVal.contains(C)) {
                                                System.out.println("inserisco");
                                                retVal.add(C);
                                            }
                                        }
                                    }*/
                                    if (!retVal.contains(C)) {
                                        System.out.println("inserisco");
                                        retVal.add(C);
                                    }
                                }
                            }

                        }
                    }
                } else {

                    System.out.println("  // target list empty");
                    for (Square[] R : actionContext.getBoard().getMap()) {
                        for (Square C : R) {
                            if (C != null)
                            {

                                for (Player P : actionContext.getPlayerList().getPlayersOnBoard()) {
                                    /*if(!P.equals(actionContext.getPlayer()))*/ {              // il player non ci interessa
                                        System.out.println("> POSIZIONE  " + P.getPosition().humanString() + ": " + actionContext.getBoard().distanceFromTo(
                                                P.getPosition(),
                                                C.getCoordinates()
                                        ));
                                        if (actionContext.getBoard().distanceFromTo(
                                                C.getCoordinates(),
                                                P.getPosition()) == (1 + 0)) {
                                            if (!retVal.contains(C)) {
                                                retVal.add(C);
                                                System.out.println("aggiungo");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

            }
            if (type.equals(typePlayer)) {
                if (actionDetails.getUserSelectedActionDetails().getChosenSquare() != null) {
                    System.out.println(actionDetails.getUserSelectedActionDetails().getChosenSquare().getCoordinates().humanString());
                    System.out.println("// square selezionato");
                    System.out.println(actionDetails.getUserSelectedActionDetails().getChosenSquare().getCoordinates().humanString());
                    for (Player C : actionContext.getPlayerList().getPlayersOnBoard()) {
                        System.out.println("> POSIZIONE  " + C.getPosition().humanString() + ": " + actionContext.getBoard().distanceFromTo(
                                C.getPosition(),
                                actionDetails.getUserSelectedActionDetails().getChosenSquare().getCoordinates()
                        ));
                        if (actionContext.getBoard().distanceFromTo(
                                C.getPosition(),
                                actionDetails.getUserSelectedActionDetails().getChosenSquare().getCoordinates()
                        ) == (1 + 0)) {
                            // la distanza è 1
                            retVal.add(C);
                        }


                    }

                } else {
                    System.out.println(";// square non ancora selezionato");
                    for (Player C : actionContext.getPlayerList().getPlayersOnBoard()) {
                        for (Square[] R : actionContext.getBoard().getMap()) {
                            for (Square T : R) {
                                if(T != null)
                                    if (actionContext.getBoard().distanceFromTo(
                                            T.getCoordinates(),
                                            C.getPosition()) == (1 + 0)) {
                                        if (!retVal.contains(C))
                                            retVal.add(C);
                                    }

                            }
                        }
                    }
                }
            }
        }catch (Exception e) {
            System.out.println("---- "+ e);

        }
        System.out.println("123 " + retVal);
        return retVal;
    }
    public List<Object> distanceFromOriginalPositionIs1NOPLAYER (ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {

        List<Object> retVal = new ArrayList<>();
        try {
            if (type.equals(typeSquare)) {

                if (actionDetails.getUserSelectedActionDetails().getTargetList().size() > 0) {
                    System.out.println("// target list not empty");
                    System.out.println("// pos: " + actionDetails.getUserSelectedActionDetails().getTarget().getPosition().humanString());
                    // Position chosenSquarePosition = actionDetails.getUserSelectedActionDetails().getChosenSquare().getCoordinates();
                    for (Square[] R : actionContext.getBoard().getMap()) {
                        for (Square C : R) {
                            if (C != null) {
                                System.out.println("> POSIZIONE  " + C.getCoordinates().humanString() + ": " + actionContext.getBoard().distanceFromTo(
                                        C.getCoordinates(),
                                        actionDetails.getUserSelectedActionDetails().getTarget().getPosition()
                                ));
                                if (actionContext.getBoard().distanceFromTo(
                                        C.getCoordinates(),
                                        actionDetails.getUserSelectedActionDetails().getTarget().getPosition()
                                ) == (1 + 0)) {
                                    // la distanza è 1
                                    // controllo che ci sia un player li sopra
                                    /*for (Player P : actionContext.getPlayerList().getPlayersOnBoard()) {
                                        System.out.println("testo " + P.getNickname() + " : " + P.getPosition().humanString());
                                        if (P.getPosition().equals(C.getCoordinates())) {
                                            System.out.println("la posizione è uguale");
                                            if (!retVal.contains(C)) {
                                                System.out.println("inserisco");
                                                retVal.add(C);
                                            }
                                        }
                                    }*/
                                    if (!retVal.contains(C)) {
                                        System.out.println("inserisco");
                                        retVal.add(C);
                                    }
                                }
                            }

                        }
                    }
                } else {

                    System.out.println("  // target list empty");
                    for (Square[] R : actionContext.getBoard().getMap()) {
                        for (Square C : R) {
                            if (C != null)
                            {

                                for (Player P : actionContext.getPlayerList().getPlayersOnBoard()) {
                                    if(!P.equals(actionContext.getPlayer())) {              // il player non ci interessa
                                        System.out.println("> POSIZIONE  " + P.getPosition().humanString() + ": " + actionContext.getBoard().distanceFromTo(
                                                P.getPosition(),
                                                C.getCoordinates()
                                        ));
                                        if (actionContext.getBoard().distanceFromTo(
                                                C.getCoordinates(),
                                                P.getPosition()) == (1 + 0)) {
                                            if (!retVal.contains(C)) {
                                                retVal.add(C);
                                                System.out.println("aggiungo");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

            }
            if (type.equals(typePlayer)) {
                if (actionDetails.getUserSelectedActionDetails().getChosenSquare() != null) {
                    System.out.println(actionDetails.getUserSelectedActionDetails().getChosenSquare().getCoordinates().humanString());
                    System.out.println("// square selezionato");
                    System.out.println(actionDetails.getUserSelectedActionDetails().getChosenSquare().getCoordinates().humanString());
                    for (Player C : actionContext.getPlayerList().getPlayersOnBoard()) {
                        System.out.println("> POSIZIONE  " + C.getPosition().humanString() + ": " + actionContext.getBoard().distanceFromTo(
                                C.getPosition(),
                                actionDetails.getUserSelectedActionDetails().getChosenSquare().getCoordinates()
                        ));
                        if (actionContext.getBoard().distanceFromTo(
                                C.getPosition(),
                                actionDetails.getUserSelectedActionDetails().getChosenSquare().getCoordinates()
                        ) == (1 + 0)) {
                            // la distanza è 1
                            retVal.add(C);
                        }


                    }
                    if(retVal.contains(actionContext.getPlayer()))
                        retVal.remove(actionContext.getPlayer());
                } else {
                    System.out.println(";// square non ancora selezionato");
                    for (Player C : actionContext.getPlayerList().getPlayersOnBoard()) {
                        for (Square[] R : actionContext.getBoard().getMap()) {
                            for (Square T : R) {
                                if(T != null)
                                    if (actionContext.getBoard().distanceFromTo(
                                            T.getCoordinates(),
                                            C.getPosition()) == (1 + 0)) {
                                        if (!retVal.contains(C))
                                            retVal.add(C);
                                    }

                            }
                        }
                    }
                }
            }
        }catch (Exception e) {
            System.out.println("---- "+ e);

        }
        System.out.println("123 " + retVal);
        return retVal;
    }


    public List<Object>  distanceFromOriginalPositionLessThan2 (ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {

        List<Object> retVal = new ArrayList<>();
        try {
            if (type.equals(typeSquare)) {

                if (actionDetails.getUserSelectedActionDetails().getTargetList().size() > 0) {
                    System.out.println("// target list not empty");
                    System.out.println("// pos: " + actionDetails.getUserSelectedActionDetails().getTarget().getPosition().humanString());

                    // Position chosenSquarePosition = actionDetails.getUserSelectedActionDetails().getChosenSquare().getCoordinates();
                    for (Square[] R : actionContext.getBoard().getMap()) {
                        for (Square C : R) {
                            if (C != null) {
                                System.out.println("> POSIZIONE  " + C.getCoordinates().humanString() + ": " + actionContext.getBoard().distanceFromTo(
                                        C.getCoordinates(),
                                        actionDetails.getUserSelectedActionDetails().getTarget().getPosition()
                                ));
                                if (actionContext.getBoard().distanceFromTo(
                                        C.getCoordinates(),
                                        actionDetails.getUserSelectedActionDetails().getTarget().getPosition()
                                ) <= (2)) {
                                    // la distanza è 1
                                    // controllo che ci sia un player li sopra
                                    /*for (Player P : actionContext.getPlayerList().getPlayersOnBoard()) {
                                        System.out.println("testo " + P.getNickname() + " : " + P.getPosition().humanString());
                                        if (P.getPosition().equals(C.getCoordinates())) {
                                            System.out.println("la posizione è uguale");
                                            if (!retVal.contains(C)) {
                                                System.out.println("inserisco");
                                                retVal.add(C);
                                            }
                                        }
                                    }*/
                                    if (!retVal.contains(C)) {
                                        System.out.println("inserisco");
                                        retVal.add(C);
                                    }
                                }
                            }

                        }
                    }
                } else {

                    System.out.println("  // target list empty");
                    for (Square[] R : actionContext.getBoard().getMap()) {
                        for (Square C : R) {
                            if (C != null)
                            {

                                for (Player P : actionContext.getPlayerList().getPlayersOnBoard()) {
                                    /* if(!P.equals(actionContext.getPlayer())) */ {              // il player non ci interessa
                                        System.out.println("> POSIZIONE  " + P.getPosition().humanString() + " vs " + C.getCoordinates().humanString() + ":" + actionContext.getBoard().distanceFromTo(
                                                P.getPosition(),
                                                C.getCoordinates()
                                        ));
                                        if (actionContext.getBoard().distanceFromTo(
                                                C.getCoordinates(),
                                                P.getPosition()) <= (2)) {
                                            if (!retVal.contains(C)) {
                                                retVal.add(C);
                                                System.out.println("aggiungo ");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

            }
            if (type.equals(typePlayer)) {
                if (actionDetails.getUserSelectedActionDetails().getChosenSquare() != null) {
                    System.out.println(actionDetails.getUserSelectedActionDetails().getChosenSquare().getCoordinates().humanString());
                    System.out.println("// square selezionato");
                    for (Player C : actionContext.getPlayerList().getPlayersOnBoard()) {
                        System.out.println("> POSIZIONE  " + C.getPosition().humanString() + ": " + actionContext.getBoard().distanceFromTo(
                                C.getPosition(),
                                actionDetails.getUserSelectedActionDetails().getChosenSquare().getCoordinates()
                        ));
                        if (actionContext.getBoard().distanceFromTo(
                                C.getPosition(),
                                actionDetails.getUserSelectedActionDetails().getChosenSquare().getCoordinates()
                        ) <= (2)) {
                            // la distanza è 1
                            retVal.add(C);
                        }


                    }

                } else {
                    System.out.println(";// square non ancora selezionato");
                    for (Player C : actionContext.getPlayerList().getPlayersOnBoard()) {
                        for (Square[] R : actionContext.getBoard().getMap()) {
                            for (Square T : R) {
                                if(T != null)

                                    if (actionContext.getBoard().distanceFromTo(
                                            T.getCoordinates(),
                                            C.getPosition()) <= (2)) {
                                        if (!retVal.contains(C))
                                            retVal.add(C);
                                    }

                            }
                        }
                    }
                }
            }
        }catch (Exception e) {
            System.out.println("---- "+ e);

        }
        System.out.println("123 " + retVal);
        return retVal;
    }


    public List<Object> distanceFromOriginalSquareIs1(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {
        /*Target.square */

        List<Object> retVal = new ArrayList<>();
        if (type.equals(UsableInputTableRowType.typePlayer)) {
            for (Player target : actionContext.getPlayerList().getPlayersOnBoard()) {
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
        if (type.equals(typeSquare)) {
            for (Square y[] : actionContext.getBoard().getMap()) {
                for (Square x : y) {
                    retVal.add(x);
                }
            }
        }
        System.out.println(retVal);
        return retVal;
    }
    public List<Object> previousEffectTarget(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {

        int thisTurn = actionContext.getPlayer().getPlayerHistory().getCurrentTurnId();
        List<Player> targets = new ArrayList<>();
        try {
            actionContext.getPlayer().getPlayerHistory().getTurnChunkR(thisTurn).show();
            targets = actionContext.getPlayer().getPlayerHistory().getTurnChunkR(thisTurn).getTargets();
        } catch ( Exception e) {}
        for(Player p: targets)
            System.out.println(p.getNickname());

        List<Object> retVal = new ArrayList<>();
        System.out.println("tipo input " + type);
        if (type.equals(UsableInputTableRowType.typePlayer)) {
            System.out.println("A");
            for (Player t : targets)
                retVal.add(t);
        }
        if (type.equals(typeSquare)) {
            System.out.println("B");
            for (Square y[] : actionContext.getBoard().getMap()) {
                for (Square x : y) {
                    if(x!=null)
                        retVal.add(x);
                }
            }
        }
        return retVal;
    }

    public List<Object> alwaysTrue(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {
        List<Object> retVal = new ArrayList<>();
        System.out.println("tipo input " + type);
        if (type.equals(UsableInputTableRowType.typePlayer)) {
            System.out.println("A");
            for (Player t : actionContext.getPlayerList().getPlayersOnBoard())
                retVal.add(t);
        }
        if (type.equals(typeSquare)) {
            System.out.println("B");
            for (Square y[] : actionContext.getBoard().getMap()) {
                for (Square x : y) {
                    if(x!=null)
                        retVal.add(x);
                }
            }
        }
        return retVal;
    }

    public List<Object> alwaysFalse(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {
        List<Object> retVal = new ArrayList<>();
        return retVal;
    }

    public List<Object> atLeastOneMoveAway(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {
        List<Object> retVal = new ArrayList<>();
        System.out.println("# verifico che la distanza sia almeno uno");
        Player user = actionContext.getPlayer();
        for(Player target: actionContext.getPlayerList().getPlayersOnBoard()) {
            try {
                System.out.println("# la distanza è " + actionContext.getBoard().distanceFromTo(target.getPosition(), user.getPosition()));
                if (actionContext.getBoard().distanceFromTo(target.getPosition(), user.getPosition()) > 1)
                {
                    System.out.println("#aggiungo " + target.getNickname());
                    retVal.add(target);
                }
            } catch (Exception e) { System.out.println("---- " + e);}
        }
        return retVal;
    }

    public List<Object> targetOnYourSquare(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {
        List<Object> retVal = new ArrayList<>();
        Player me = actionContext.getPlayer();
        PlayersList playersList = actionContext.getPlayerList();
        try {
            for (Player t : playersList.getPlayersOnBoard()) {
                if (me.getPosition().equalPositions(t.getPosition())) {
                    retVal.add(t);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        ;
        return retVal;


    }

    public List<Object> targetNotOnYourSquare(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {
        List<Object> retVal = new ArrayList<>();
        Player me = actionContext.getPlayer();
        PlayersList playersList = actionContext.getPlayerList();
        try {
            for (Player t : playersList.getPlayersOnBoard()) {
                if (!me.getPosition().equalPositions(t.getPosition())) {
                    retVal.add(t);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        ;
        return retVal;

    }
    public List<Object> notFirstNorSecondExecuted(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect)
    {
        // TODO: passare il record corrente della playerHistory
        int minimum = 2 ;
        List<Object> retVal = new ArrayList<>();
        boolean fill = false;
        System.out.println("TURNO >> " + actionContext.getPlayer().getTurnID());
        try {
            actionContext.getPlayer().getPlayerHistory().show();
        }catch(Exception e) {
            System.out.println("tabella vuota");
        }
        PlayerHistory historyCurrentTurn = actionContext.getPlayer().getPlayerHistory().getTurnChunkR(
                actionContext.getPlayer().getTurnID()
        );
        System.out.println("turno corrente ----------------------------");
        historyCurrentTurn.show();
        System.out.println("split per blocco --------------------------");
        for(List<PlayerHistoryElement> l : historyCurrentTurn.rawDataSplittenByBlockId()) {
            System.out.println("{");
            for(PlayerHistoryElement r: l)
            {
                r.show();
            }
            System.out.println("}");
            System.out.println("--------");
        }
        if((historyCurrentTurn.rawDataSplittenByBlockId().size()) >= minimum)
        {
            if(historyCurrentTurn.rawDataSplittenByBlockId().get(0).size() > 0 )
                fill = true;
        }

        System.out.println(fill);

        if(type.equals(typeSquare)) {
            if(fill) {
                for(Square[] R: actionContext.getBoard().getMap()){
                    for(Square C: R){
                        if(C!=null)
                            retVal.add(C);
                    }
                }

            }
        }
        if(type.equals(UsableInputTableRowType.typePlayer)) {
            if(fill) {
                for(Player p: actionContext.getPlayerList().getPlayersOnBoard()) {
                    System.out.println("aggiungo " + p.getNickname());
                    retVal.add(p);
                }
            }
        }
        return retVal;
    }
    public List<Object> previousTargetCanSee(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {
        List<Object> retVal = new ArrayList<>();
        System.out.println("in " + actionContext.getPlayer().getPlayerHistory().getSize());
        PreConditionMethods preConditionMethods = new PreConditionMethods();
        Player lastTarget = null;
        if(actionContext.getPlayer().getPlayerHistory().getSize() > 0)
            lastTarget =  (Player) ((Object[])actionContext.getPlayer().getPlayerHistory().getLast().getInput())[0];

        for (Player p : actionContext.getPlayerList().getPlayersOnBoard()) {
            ActionDetails actionDetails1 = new ActionDetails();
            actionDetails1.getUserSelectedActionDetails().setTarget(p);
            ActionContext actionContext1 = new ActionContext();
            actionContext1.setPlayer(lastTarget);
            actionContext1.setPlayerList(actionContext.getPlayerList());
            actionContext1.setBoard(actionContext.getBoard());


            if(lastTarget == null) {retVal.add(p);System.out.println("niente target prima " );}
            else {
                System.out.println((actionContext.getPlayer().getPlayerHistory().getSize()) + " giri di controllo");
                actionContext1.setPlayer(lastTarget);
                actionContext1.setPlayerList(actionContext.getPlayerList());
                actionContext1.setBoard(actionContext.getBoard());


                if (preConditionMethods.youCanSee(actionDetails1, actionContext1)) {
                    System.out.println("i visibili dall'ultimo : " + p.getNickname());
                    retVal.add(p);
                }
                if(retVal.contains(lastTarget))
                    retVal.remove(lastTarget);
            }
        }
        System.out.println("fine");
        return retVal;
    }
    public List<Object> youCantSee(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {

        List<Object> retVal = new ArrayList<>();
        if(type.equals(typePlayer)) {
            Player me = actionContext.getPlayer();
            PlayersList playersList = actionContext.getPlayerList();
            try {
                List<Object> bufferVal = youCanSee(actionContext, type, actionDetails, inputs, inputSlots, contextEffect);
                for (Player p : actionContext.getPlayerList().getPlayersOnBoard()) {
                    retVal.add(p);
                }
                for (Object o : bufferVal) {
                    retVal.remove((Player) o);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        if(type.equals(typeSquare)) {
            for(Square[] r: actionContext.getBoard().getMap()) {
                for(Square c: r) {
                    if(c!=null)
                        retVal.add(c);
                }
            }
        }
        return retVal;
    }
    public List<Object> hasASquareWithDistance1ThatPlayerCanSee(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {
        List<Object> retVal = new ArrayList<>();
        if(type == typePlayer) {
            for(Player p: actionContext.getPlayerList().getPlayersOnBoard()) {
                Position oldPosition = new Position(
                        actionContext.getPlayer().getPosition().getX(),
                        actionContext.getPlayer().getPosition().getY()
                );

                actionContext.getPlayer().setPositionWithoutNotify(p.getPosition());
                List<Object> SquareWithDistance1ThatPlayerCanSee = hasASquareWithDistance1ThatPlayerCanSee
                        (
                                actionContext,
                                typeSquare,
                                actionDetails,
                                null,
                                null,
                                null
                        );
                if(SquareWithDistance1ThatPlayerCanSee.size() > 0) {
                    retVal.add(p);
                }
                actionContext.getPlayer().setPositionWithoutNotify(oldPosition);
            }
        }
        if(type == typeSquare) {
            List<Object> visibileSquares = youCanSeeThatSquare(
                    actionContext,
                    typeSquare,
                    new ActionDetails(),
                    inputs,
                    inputSlots,
                    contextEffect
            );

            for (Player p : actionContext.getPlayerList().getPlayersOnBoard()) {
                if (!p.equals(actionContext.getPlayer())) {
                    Position oldPosition = new Position(
                            actionContext.getPlayer().getPosition().getX(),
                            actionContext.getPlayer().getPosition().getY()
                    );
                    actionContext.getPlayer().setPositionWithoutNotify(p.getPosition());
                    List<Object> distance1Squares = distanceFromOriginalPositionIs1(
                            actionContext,
                            typeSquare,
                            new ActionDetails(),
                            null,
                            null,
                            null
                    );
                    List<Object> chosable = Effect.intersect(
                            distance1Squares,
                            visibileSquares
                    );
                    for (Object o : chosable) {
                        if (!retVal.contains(o))
                            retVal.add(o);
                    }

                    actionContext.getPlayer().setPositionWithoutNotify(oldPosition);
                }
            }
        }
        return retVal;
    }
    public List<Object> exceptSquaresDistant1FromPlayer(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {
        List<Object> retVal = new ArrayList<>();

        if(type == typePlayer)
        {
            // non c'entrano i player
            for(Player p: actionContext.getPlayerList().getPlayersOnBoard()) {
                retVal.add(p);
            }
        }
        if(type == typeSquare)
        {
            for(Square[] R: actionContext.getBoard().getMap())
                for(Square C: R)
                    retVal.add(C);
            try {
                for (Square[] R : actionContext.getBoard().getMap())
                    for (Square C : R)
                        if (C != null)
                            if (actionContext.getBoard().distanceFromTo(
                                    C.getCoordinates(),
                                    actionContext.getPlayer().getPosition()) == 1)
                                if (retVal.contains(C))
                                    retVal.remove(C);
            } catch(Exception e) {
                // ...
            }
        }
        return retVal;
    }
    public List<Object> notYourSquare(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {
        List<Object> retVal = new ArrayList<>();
        Position playerPosition = actionContext.getPlayer().getPosition();
        if(type == typePlayer) {        // non c'entra con i player,è vera per ogni player
            for (Player p : actionContext.getPlayerList().getPlayersOnBoard()) {
                retVal.add(p);
            }
        }
        if(type == typeSquare) {        // toglie lo square dei playe rdalla lista dei player possibili
            for (Square[] R: actionContext.getBoard().getMap())
                for ( Square C: R)
                    if( C != null)
                        if(!C.getCoordinates().equalPositions(playerPosition))
                            retVal.add(C);

        }
        return retVal;
    }

    public List<Object> youCanSeeThatSquare(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {
    List<Object> retVal = new ArrayList<>();
    if(type == typePlayer) {    // ininfluente sui player
        for(Player p: actionContext.getPlayerList().getPlayersOnBoard())
            retVal.add(p);
    }
    if(type == typeSquare) {
        // lo square non è ancora selezionato
        for(Square[] r: actionContext.getBoard().getMap())
        {
            for(Square c: r) {
                if(c != null) {
                    ActionContext a = new ActionContext();
                    ActionDetails b = new ActionDetails();

                    Player p = new Player();
                    Player t = new Player();
                    p.setPositionWithoutNotify(actionContext.getPlayer().getPosition());
                    t.setPositionWithoutNotify(c.getCoordinates());

                    PlayersList pl = new PlayersList();
                    pl.addPlayer(p);
                    pl.addPlayer(t);

                    a.setPlayer(p);
                    a.setPlayerList(pl);
                    a.setBoard(actionContext.getBoard());

                    b.getUserSelectedActionDetails().setTarget(t);

                    PreConditionMethods PGate = new PreConditionMethods();
                    if(PGate.youCanSee(b,a))
                    {
                        retVal.add(c);
                    }

                }
            }
        }
    }
    return retVal;
    }
    public List<Object> moveOtherPlayer(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {

        List<Object> retVal = new ArrayList<>();
        if(type.equals(typePlayer)) {
            for (Player t : actionContext.getPlayerList().getPlayersOnBoard()) {
                retVal.add(t);
            }
            retVal.remove(actionContext.getPlayer());
        }
        if(type.equals(typeSquare)) {
            for (Square[] r: actionContext.getBoard().getMap()) {
                for(Square c: r)
                    if(c!=null)
                    {
                        retVal.add(c);
                    }
            }
        }
        return retVal;
    }
    public List<Object> youCanSee(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {

        List<Object> retVal = new ArrayList<>();
        Player me = actionContext.getPlayer();
        PlayersList playersList = actionContext.getPlayerList();
        if(type.equals(typePlayer)) {
            try {
                PreConditionMethods preConditionMethods = new PreConditionMethods();
                for (Player t : playersList.getPlayersOnBoard()) {
                    ;
                    ActionDetails actionDetails1 = new ActionDetails();
                    actionDetails1.getUserSelectedActionDetails().setTarget(t);
                    if (preConditionMethods.youCanSee(actionDetails1, actionContext)) {
                        retVal.add(t);
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            ;
        }
        if(type.equals(typeSquare)) {
            for(Square[] r: actionContext.getBoard().getMap()){
                for (Square c: r){
                    if(c!=null)
                        retVal.add(c);
                }
            }
        }
        return retVal;
    }
    public List<Object> distanceOfTargetFromPlayerSquareLessThan2Moves(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect)  {
        List<Object> retVal = new ArrayList<>();

        Player user   = actionContext.getPlayer();

        Square[][] map = actionContext.getBoard().getMap();
        if(type == typePlayer) {
            for(Player p: actionContext.getPlayerList().getPlayersOnBoard())
            {
                try {
                    if (actionContext.getBoard().distanceFromTo(
                            p.getPosition(),
                            actionContext.getPlayer().getPosition()
                    ) <= 2) {
                        retVal.add(p);
                    }
                } catch(Exception e) {
                    System.out.println("distanceOfTargetFromPlayerSquareIs1 : errore " + e);
                }
            }
        }
        if(type == typeSquare) {
            for(Square[] r: actionContext.getBoard().getMap()) {
                for(Square c: r ) {
                    if(c!=null) {
                        retVal.add(c);
                    }
                }
            }
        }
        return retVal;
    }


    public List<Object> notInYourRoom(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect)
    {
        List<Object> retVal = new ArrayList<>();
        Player me = actionContext.getPlayer();
        char playerRoom = actionContext.getBoard().getSquare(me.getPosition()).getColor();
        for(Player t: actionContext.getPlayerList().getPlayersOnBoard()) {
            if(actionContext.getBoard().getSquare(t.getPosition()).getColor() != playerRoom) {
                retVal.add(t);
            }
        }
        return retVal;
    }
    public List<Object> notFirstExecuted(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect)
    {
        // TODO: passare il record corrente della playerHistory
        int minimum = 1;
        List<Object> retVal = new ArrayList<>();
        boolean fill = false;
        System.out.println("TURNO >> " + actionContext.getPlayer().getTurnID());
        try {
            actionContext.getPlayer().getPlayerHistory().show();
        }catch(Exception e) {
            System.out.println("lista vuota");
        }
        PlayerHistory historyCurrentTurn = actionContext.getPlayer().getPlayerHistory().getTurnChunkR(
                actionContext.getPlayer().getTurnID()
        );
        System.out.println("turno corrente ----------------------------");
        historyCurrentTurn.show();
        System.out.println("split per blocco --------------------------");
        for(List<PlayerHistoryElement> l : historyCurrentTurn.rawDataSplittenByBlockId()) {
            System.out.println("{");
            for(PlayerHistoryElement r: l)
            {
                r.show();
            }
            System.out.println("}");
            System.out.println("--------");
        }
        if((historyCurrentTurn.rawDataSplittenByBlockId().size()) >= minimum)
        {
            if(historyCurrentTurn.rawDataSplittenByBlockId().get(0).size() > 0 )
                fill = true;
        }

        System.out.println(fill);

        if(type.equals(typeSquare)) {
            if(fill) {
                for(Square[] R: actionContext.getBoard().getMap()){
                    for(Square C: R){
                        if(C!=null)
                            retVal.add(C);
                    }
                }

            }
        }
        if(type.equals(UsableInputTableRowType.typePlayer)) {
            if(fill) {
                for(Player p: actionContext.getPlayerList().getPlayersOnBoard()) {
                    System.out.println("aggiungo " + p.getNickname());
                    retVal.add(p);
                }
            }
        }
        return retVal;
    }
    public List<Object> distanceOfTargetFromPlayerSquareMoreThan2Moves(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect)
            throws Exception
    {
        List<Object> retVal = new ArrayList<>();

        Player user   = actionContext.getPlayer();
        for(Player target: actionContext.getPlayerList().getPlayersOnBoard())
            if(actionContext.getBoard().distanceFromTo(target.getPosition(), user.getPosition()) > 2)
                retVal.add(target);
        System.out.println("***");
        System.out.println(retVal);
        System.out.println("***");
        return retVal;
    }
    public List<Object> notPreviousTarget(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect)
    {

        List<Object> retVal = new ArrayList<>();

        Player lastTarget;
        if(actionContext.getPlayer().getPlayerHistory().getSize() > 0)
            lastTarget =  (Player) ((Object[])actionContext.getPlayer().getPlayerHistory().getLast().getInput())[0];
        else
            lastTarget = null;

        if(type.equals(typeSquare)) {
            for(Square s[] : actionContext.getBoard().getMap()){
                for(Square t: s){
                    if(t!=null)
                        retVal.add(t);
                }
            }

        }
        if(type.equals(typePlayer)) {
            for(Player p:actionContext.getPlayerList().getPlayersOnBoard())
                retVal.add(p);

            if(lastTarget!=null)
                retVal.remove(lastTarget);
        }

        return retVal;

    }
    public List<Object> distanceOfSquareFromPlayerExactlyOne(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect) {
        List<Object> retVal = new ArrayList<>();
        try {
            if (type.equals(typeSquare)) {

                if (actionDetails.getUserSelectedActionDetails().getTargetList().size() > 0) {
                    System.out.println("// target list not empty");

                    // Position chosenSquarePosition = actionDetails.getUserSelectedActionDetails().getChosenSquare().getCoordinates();
                    for (Square[] R : actionContext.getBoard().getMap()) {
                        for (Square C : R) {
                            if (C != null)
                                if (actionContext.getBoard().distanceFromTo(
                                        C.getCoordinates(),
                                        actionDetails.getUserSelectedActionDetails().getTarget().getPosition()
                                ) == (1)) {
                                    // la distanza è 1
                                    retVal.add(C);
                                }

                        }

                    }
                } else {

                    System.out.println("  // target list empty");
                    for (Square[] R : actionContext.getBoard().getMap()) {
                        for (Square C : R) {
                            if(C!=null)
                                retVal.add(C);
                        }
                    }

                }

            }
            if (type.equals(typePlayer)) {
                if(actionDetails.getUserSelectedActionDetails().getChosenSquare() != null) {
                    // square selezionato
                    for (Player C : actionContext.getPlayerList().getPlayersOnBoard()) {
                        if (actionContext.getBoard().distanceFromTo(
                                C.getPosition(),
                                actionDetails.getUserSelectedActionDetails().getChosenSquare().getCoordinates()
                        ) == (1)) {
                            // la distanza è 1
                            retVal.add(C);
                        }


                    }

                } else {    // square non ancora selezionato
                    for (Player C : actionContext.getPlayerList().getPlayersOnBoard()) {
                        retVal.add(C);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("---- "+ e);

        }
        System.out.println("123 " + retVal);
        return retVal;
    }
    public List<Object> distanceOfTargetFromPlayerSquareIs1(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect)  {
        List<Object> retVal = new ArrayList<>();

        Player user   = actionContext.getPlayer();

        Square[][] map = actionContext.getBoard().getMap();
        if(type == typePlayer) {
            for(Player p: actionContext.getPlayerList().getPlayersOnBoard())
            {
                try {
                    if (actionContext.getBoard().distanceFromTo(
                            p.getPosition(),
                            actionContext.getPlayer().getPosition()
                    ) == 1) {
                        retVal.add(p);
                    }
                } catch(Exception e) {
                    System.out.println("distanceOfTargetFromPlayerSquareIs1 : errore " + e);
                }
            }
        }
        if(type == typeSquare) {
            for(Square[] r: actionContext.getBoard().getMap()) {
                for(Square c: r ) {
                    if(c!=null) {
                        retVal.add(c);
                    }
                }
            }
        }
        return retVal;
    }
}
