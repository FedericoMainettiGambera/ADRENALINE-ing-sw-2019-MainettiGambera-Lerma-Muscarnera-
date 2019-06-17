package it.polimi.se2019.model;


import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.enumerations.UsableInputTableRowType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.se2019.model.enumerations.EffectInfoType.*;

/***/
public class Effect implements Serializable {
    public WeaponCard getOf() {
        return of;
    }

    private String effectName;

    public String getEffectName(){
        return this.effectName;
    }

    public void setEffectName(String effectName){
        this.effectName= effectName;
    }

    public static List<Object> intersect (List<Object> A,List<Object> B) {
        List<Object> retVal = new ArrayList<>();
        for(Object C: A) {
            if((A.contains(C)) && (B.contains(C))) {
                retVal.add(C);
            }
        }
        return retVal;
    }
    public List<List<List<Object>>> usableInputs() {
        /*matrice 3D degli input (vedi sotto)*/
        List<List<List<Object>>> retVal = new ArrayList<>();
        /*tipo di input per riga*/
        List<UsableInputTableRowType> rowType = new ArrayList<>();
        List<UsableInputTableRowType> frontEndRowType = new ArrayList<>();
        /**
         *  some inputs have two layers
         *  es. targetListBySquare
         *
         *  it requests a square but pass a list of players to the effect
         * */
        List<Integer>                 rowCardinality = new ArrayList<>();

        /*  /////////////////////////////////////////////MATRICE 3D
         *
         *   riga 1:     [1* parte input]  [2* parte input]
         *                       |
         *                       V
         *                       { primo possibile primo parte primo input, secondo possibile prima parte primo input...}
         *
         * */

        /*initializing the rows of retVal table*/
        for(EffectInfoElement e: this.getEffectInfo().getEffectInfoElement()) {

            if(e.getEffectInfoTypelist() == targetListBySquare) {
                rowType.add(UsableInputTableRowType.typePlayer);
                frontEndRowType.add(UsableInputTableRowType.typeSquare);

                rowCardinality.add(1);


                List<Object> cell = new ArrayList<>();
                for(Player t: this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayers())
                    cell.add((Object) t);
                List<List<Object>> row = new ArrayList<>();

                row.add(cell);
                retVal.add(row);

            }
            if(e.getEffectInfoTypelist() == singleTargetBySquare) {
                rowType.add(UsableInputTableRowType.typePlayer);
                frontEndRowType.add(UsableInputTableRowType.typeSquare);

                rowCardinality.add(1);


                List<Object> cell = new ArrayList<>();
                for(Player t: this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayers())
                    cell.add((Object) t);
                List<List<Object>> row = new ArrayList<>();

                row.add(cell);
                retVal.add(row);

            }
            if(e.getEffectInfoTypelist() == singleTarget) {
                rowType.add(UsableInputTableRowType.typePlayer);
                frontEndRowType.add(UsableInputTableRowType.typePlayer);



                rowCardinality.add(1);


                List<Object> cell = new ArrayList<>();
                for(Player t: this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayers())
                    cell.add((Object) t);
                List<List<Object>> row = new ArrayList<>();

                row.add(cell);
                retVal.add(row);
            }
            if(e.getEffectInfoTypelist() == twoTargets) {
                rowType.add(UsableInputTableRowType.typePlayer);
                frontEndRowType.add(UsableInputTableRowType.typePlayer);
                rowCardinality.add(2);


                List<Object> cell = new ArrayList<>();
                for(Player t: this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayers())
                    cell.add((Object) t);
                List<Object> cell2 = new ArrayList<>();
                for(Player t: this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayers())
                    cell2.add((Object) t);
                List<List<Object>> row = new ArrayList<>();

                row.add(cell);
                row.add(cell2);     //slot doppio per doppia cardinalità

                retVal.add(row);
            }
            if(e.getEffectInfoTypelist() == threeTargets) {
                rowType.add(UsableInputTableRowType.typePlayer);
                frontEndRowType.add(UsableInputTableRowType.typePlayer);
                rowCardinality.add(3);


                List<Object> cell = new ArrayList<>();
                for(Player t: this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayers())
                    cell.add((Object) t);
                List<Object> cell2 = new ArrayList<>();
                for(Player t: this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayers())
                    cell2.add((Object) t);
                List<List<Object>> row = new ArrayList<>();
                List<Object> cell3 = new ArrayList<>();
                for(Player t: this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayers())
                    cell3.add((Object) t);

                row.add(cell);
                row.add(cell2);
                row.add(cell3);     //slot triplo per tripla cardinalità

                retVal.add(row);
            }
            if(e.getEffectInfoTypelist() == player){
                List<Object> cell = new ArrayList<>();
                List<List<Object>> row = new ArrayList<>();
                cell.add(this.getActions().get(0).getActionInfo().getActionContext().getPlayer());
                row.add(cell);

                rowType.add(UsableInputTableRowType.typePlayer);
                frontEndRowType.add(null);


                retVal.add(row);
            }

            if(e.getEffectInfoTypelist() == squareByLastTargetSelected){
                List<Object> cell = new ArrayList<>();
                List<List<Object>> row = new ArrayList<>();
                Player lastTarget = null;
                int counter = (this.getEffectInfo().getEffectInfoElement().indexOf(e));
                for(int x = (this.getEffectInfo().getEffectInfoElement().indexOf(e));x>=0;x--) {
                    EffectInfoType slot = this.getEffectInfo().getEffectInfoElement().get(x).getEffectInfoTypelist();
                    if(slot.equals(singleTarget)) {
                        System.out.println(x);
                        if(filledInputs.size() > x)
                            lastTarget = (Player) ((Object[])filledInputs.get(x))[0];
                        break;
                    }
                    counter++;
                }

                if(lastTarget != null)
                    cell.add(this.getActions().get(0).getActionInfo().getActionContext().getBoard().getSquare(lastTarget.getPosition()));
                else {
                    for(Player p: this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayers()) {
                        cell.add(p);
                    }
                }
                row.add(cell);

                rowType.add(UsableInputTableRowType.typeSquare);
                frontEndRowType.add(null);


                retVal.add(row);
            }

            if(e.getEffectInfoTypelist() == playerSquare){
                List<Object> cell = new ArrayList<>();
                List<List<Object>> row = new ArrayList<>();
                cell.add(this.getActions().get(0).getActionInfo().getActionContext().getBoard().getSquare(this.getActions().get(0).getActionInfo().getActionContext().getPlayer().getPosition()));
                row.add(cell);
                rowType.add(UsableInputTableRowType.typeSquare);
                frontEndRowType.add(null);


                retVal.add(row);
            }
            if(e.getEffectInfoTypelist() == targetListByRoom) {
                List<Object> cell = new ArrayList<>();
                for (Player x : this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayers()) {
                    cell.add(x);
                }

                List<List<Object>> row = new ArrayList<>();
                row.add(cell);
                rowType.add(UsableInputTableRowType.typePlayer);
                frontEndRowType.add(UsableInputTableRowType.typeSquare);
                retVal.add(row);
            }
            if(e.getEffectInfoTypelist() == targetListBySameSquareOfPlayer){
                List<Object> cell = new ArrayList<>();
                for (Player x : this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayers()) {
                    if(x.getPosition().equals(this.getActions().get(0).getActionInfo().getActionContext().getPlayer().getPosition()))
                        cell.add(x);
                }

                List<List<Object>> row = new ArrayList<>();
                row.add(cell);
                rowType.add(UsableInputTableRowType.typePlayer);
                frontEndRowType.add(null);
                retVal.add(row);
            }
            if(e.getEffectInfoTypelist() == targetListByDistance1) {
                rowType.add(UsableInputTableRowType.typePlayer);
                frontEndRowType.add(null);
                rowCardinality.add(1);


                List<Object> cell = new ArrayList<>();
                for (Square y[] : this.getActions().get(0).getActionInfo().getActionContext().getBoard().getMap()) {
                    for(Square x: y) {
                        try {
                            if (getActions().get(0).getActionInfo().getActionContext().getBoard().distanceFromTo(
                                    x.getCoordinates(),
                                    getActions().get(0).getActionInfo().getActionContext().getPlayer().getPosition()
                            ) == 1) {
                                for(Player p: this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayers()) {
                                    if(p.getPosition().equals(x.getCoordinates())) {
                                        if(!cell.contains(p))
                                            cell.add(p);
                                    }
                                }
                            }
                        } catch(Exception exx){
                            System.out.println("--- e: " + exx);
                        }
                    }
                }
                List<List<Object>> row = new ArrayList<>();

                row.add(cell);
                retVal.add(row);
            }

            if(e.getEffectInfoTypelist() == simpleSquareSelect) {
                rowType.add(UsableInputTableRowType.typeSquare);
                frontEndRowType.add(UsableInputTableRowType.typeSquare);
                rowCardinality.add(1);


                List<Object> cell = new ArrayList<>();
                for (Square y[] : this.getActions().get(0).getActionInfo().getActionContext().getBoard().getMap()) {
                    for(Square x: y) {
                        cell.add(x);
                    }
                }
                List<List<Object>> row = new ArrayList<>();

                row.add(cell);
                retVal.add(row);
            }
            /*...*/
        }

        int actionCounter = 1;

        // intermediate conditions
        // intermediateList[i][a]
        // liste dei return delle precondizioni per singola azione

        List<List<Object>> intermediateList = new ArrayList<>();

        for(EffectInfoElement e: this.getEffectInfo().getEffectInfoElement()) {
            // aggiungo un array list per ogni input
            intermediateList.add(new ArrayList<>());
            for(Action a: this.getActions()) {

                intermediateList.get(intermediateList.size() - 1).add(null);

            }
        }

        for(Action a: this.getActions()) {


            List<EffectInfoElement> dedicatedInputs = new ArrayList<>();
            /*fill the dedicated inputs*/
            for(EffectInfoElement e: this.getEffectInfo().getEffectInfoElement()) {
                if (e.getEffectInfoTypeDestination().get(0) == 0) // all
                {
                    dedicatedInputs.add(e);
                } else {
                    for (Integer number : e.getEffectInfoTypeDestination()) {

                        if (number == actionCounter) {

                            dedicatedInputs.add(e);

                        }

                    }

                }


            }
            System.out.println("per questa azione " + actionCounter + " sono dedicati " + dedicatedInputs.size() + " inputs. Sono: ");

            int j = 0;
            /*for (EffectInfoElement x : dedicatedInputs)
            {
                int inputIndex =  getEffectInfo().getEffectInfoElement().indexOf(x);
                intermediateList.get(inputIndex).set(actionCounter-1,null);
            }*/
            //
            //

            //
            int inputCounter = 0;

            for(EffectInfoElement eie: dedicatedInputs) {
                List<Integer> totalDestinations = eie.getEffectInfoTypeDestination();
                List<Integer> finalDestinations = new ArrayList<>();
                if(totalDestinations.get(0) == 0)
                {
                    for(int c = 0; c < this.getActions().size();c++)
                        finalDestinations.add(c+1);
                } else {
                    finalDestinations = totalDestinations;
                }
                for (int b : finalDestinations) {
                    Action a_ = this.getActions().get(b-1);
//                    System.out.println("EffectInfoElement " + eie.getEffectInfoTypelist());

                    Object invertedPreConditionOutput = null;
                    PreConditionMethodsInverted preConditionMethodsInverted = new PreConditionMethodsInverted();
                    try {
                        java.lang.reflect.Method method;
                        Class<?> c = Class.forName("it.polimi.se2019.model.PreConditionMethodsInverted");
                        //(ActionContext actionContext, UsableInputTableRowType type, Integer cardinality, Object inputs, List<EffectInfoElement> inputSlots)
                        Class<?>[] paramTypes = {ActionContext.class,UsableInputTableRowType.class,ActionDetails.class,Object.class,List.class,Effect.class};
//                        System.out.println("azione " + a_ + " nome precondizione : " + a_.getActionInfo().getPreConditionMethodName());

                        method = c.getDeclaredMethod(a_.getActionInfo().getPreConditionMethodName(), paramTypes);
                        System.out.println("# " + eie.getEffectInfoTypelist() +" @@@ eseguo l'inversa di " + a_.getActionInfo().getPreConditionMethodName());
                        System.out.println("--");
                        System.out.println( "@" + this.getActions().get(0).getActionInfo().getActionContext() );
                        System.out.println( "@" + rowType.get(inputCounter) + "> " + inputCounter);
                        System.out.println( "@" + a_.getActionInfo().getActionDetails());
                        System.out.println( "@" + filledInputs);
                        System.out.println( "@" + requestedInputs());
                        invertedPreConditionOutput = method.invoke(preConditionMethodsInverted,
                                this.getActions().get(0).getActionInfo().getActionContext(),
                                rowType.get(inputCounter),
                                //rowType.get(getEffectInfo().getEffectInfoElement().indexOf(eie)),
                                a_.getActionInfo().getActionDetails(),
                                filledInputs,
                                requestedInputs(),
                                this
                        );
                        System.out.println("--");
                    } catch (Exception exception) {
                        System.out.println("eccezione! " + exception);
                        exception.printStackTrace();
                    }


                    intermediateList.get(effectInfo.getEffectInfoElement().indexOf(eie)).set(
                            /*inverse Precondition here*/
                            this.getActions().indexOf(a_),
                            invertedPreConditionOutput
                    );
//                    System.out.println("Azione (" + this.getActions().indexOf(a_) + ") , Input (" + effectInfo.getEffectInfoElement().indexOf(eie) + ")   -> " + a.getActionInfo().getPreConditionMethodName() + " -> " + invertedPreConditionOutput);

                }
                inputCounter++;
            }


            actionCounter++;
        }
        //      System.out.println("");
        //      for( List<Object> o: intermediateList)
        //          System.out.println(">>>>" + o);

        //       System.out.println("");
        int iCount = 0;
        int aCount = 0;
//        System.out.println(retVal);

        int i,j,k;
        for(i = 0; i < retVal.size();i++) {
            //          System.out.println("[" + effectInfo.getEffectInfoElement().get(i).getEffectInfoTypelist().toString() + "]" + " parto con l'insieme di tutti i possibili");
            for(j = 0; j < retVal.get(i).size();j++) {
                //if (this.getEffectInfo().getEffectInfoElement().get(i).getEffectInfoTypeDestination().contains(j))
                {
                    // l'input è i
                    //                   System.out.println("@"+ intermediateList.get(i));
                    System.out.println("["+this.getEffectInfo().getEffectInfoElement().get(i).getEffectInfoTypelist() +"] parto da: " + retVal.get(i).get(j));
                    for (k = 0; k < this.getActions().size(); k++) {
                        if(intermediateList.get(i).get(k) != null)
                        {
                            System.out.println("dominio precondizione '" +  this.getActions().get(k).getActionInfo().getPreConditionMethodName() + "' : interseco con.." + intermediateList.get(i).get(k)  );
                            for (Object d : (List<Object>) intermediateList.get(i).get(k)) {
                                if(d.getClass().toString().equals("class it.polimi.se2019.model.Player")) {
                                    System.out.println(((Player) d).getNickname());
                                } else {
                                    System.out.println("[" + ((Square) d).getCoordinates().getX() + ", " + ((Square) d).getCoordinates().getX() + "]");

                                }
                            }
                            retVal.get(i).set(j,
                                    intersect(
                                            retVal.get(i).get(j),
                                            (List<Object>) intermediateList.get(i).get(k)
                                    ));
                        }
                    }
                }
            }


        }


/*        for(List<Object> x:intermediateList) {
        System.out.println("input");
            for (Object y : x) {
            System.out.println("per azione");
                System.out.println(x);
            }
        }*/
        for(Object t:retVal)
            System.out.println("."+t);

//  filtraggio
        System.out.println("filtraggio...");
        int frontCounter = 0;
        System.out.println("risultato iniziale " + retVal);
        for(UsableInputTableRowType front: frontEndRowType) {
            UsableInputTableRowType back = rowType.get(frontCounter);
            if(front != null)
                if(front.equals(UsableInputTableRowType.typePlayer)) {
                    if(back.equals(UsableInputTableRowType.typePlayer)) {
                        System.out.println("@@@@@@@@ " + retVal.get(frontCounter).get(0));
                        // già formattato

                    }  if(back.equals(UsableInputTableRowType.typeSquare)) {

                        for(Object o : retVal.get(frontCounter).get(0)) {
                            int X = ((Player) o).getPosition().getX();
                            int Y = ((Player) o).getPosition().getY();

                            o = this.getActions().get(0).getActionInfo().getActionContext().getBoard().getSquare(X,Y);

                        }


                    }

                }
            if(front != null)
                if(front.equals(UsableInputTableRowType.typeSquare)) {
                    if(back.equals(UsableInputTableRowType.typeSquare)) {

                        // già formattato

                    }  if(back.equals(UsableInputTableRowType.typePlayer)) {
                        System.out.println("front : square, back : player");
                        List<Object> newRow = new ArrayList<>();
                        for(Object o : retVal.get(frontCounter).get(0)) {
                            int X = ((Player) o).getPosition().getX();
                            int Y = ((Player) o).getPosition().getY();
                            Position pos = new Position(X,Y);
                            if(!newRow.contains(this.getActions().get(0).getActionInfo().getActionContext().getBoard().getSquare(X,Y)))
                                newRow.add(this.getActions().get(0).getActionInfo().getActionContext().getBoard().getSquare(X,Y));
                            // o = this.getActions().get(0).getActionInfo().getActionContext().getBoard().getSquare(X,Y);

                        }

                        retVal.get(frontCounter).set(0, newRow);
                    }

                }

            frontCounter++;
        }

        System.out.println("risultato finale " + retVal);
        System.out.println("--------------------------");
        for(int x = 0;x < this.getEffectInfo().getEffectInfoElement().size();x++) {
            System.out.println(this.getEffectInfo().getEffectInfoElement().get(x).getEffectInfoTypelist());
            if(filledInputs.size() > x) {
                System.out.println("<" + rowType.get(x) +">" );
                for (Object y : (Object[]) filledInputs.get(x)) {
                    System.out.println(y);
                }
            }
        }
        return retVal;
    }
    public void setOf(WeaponCard of) {
        this.of = of;
    }


    private transient WeaponCard of;

    public List<Object> getFilledInputs() {
        return filledInputs;
    }

    /*-****************************************************************************************************CONSTRUCTOR*/
    private transient List<Object> filledInputs;

    public EffectInfo getEffectInfo() {
        return effectInfo;
    }

    public void setEffectInfo(EffectInfo effectInfo) {
        this.effectInfo = effectInfo;
    }

    /***/
    private EffectInfo effectInfo;
    public Effect(String description, List<Action> actions) {
        this.description = description;
        this.actions = actions;
        this.effectName = "no Effect Name";
    }


    public Effect() {
        this.filledInputs = new ArrayList<>();
        this.actions = new ArrayList<Action>();
        this.effectName = "no Effect Name";
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /***/
    private String description;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    /***/
    private transient List<Action> actions;

    /*-********************************************************************************************************METHODS*/
    /***/
    public List<Action> getActions() {
        return actions;
    }

    /***/
    public String getDescription() {
        return description;
    }
    /***/
    public boolean valuateAllPrecondition() {

        boolean retVal = true;
        for(Action a: actions) {
            if(!a.getActionInfo().preCondition()) {
                retVal = false;
            }
        }
        return retVal;

    }
    public boolean validContext(Player player,Board board,PlayersList playersList) {
        ActionContext context = new ActionContext();
        context.setPlayer(player);
        context.setPlayerList(playersList);
        context.setBoard(board);
        setContext(context);
        return valuateAllPrecondition();
    }
    public void passContext(Player player,PlayersList playersList,Board board) {


        for(Action a : this.getActions()) {
            a.getActionInfo().getActionContext().setPlayer(player);
            a.getActionInfo().getActionContext().setPlayerList(playersList);
            a.getActionInfo().getActionContext().setBoard(board);
        }
    }
    public List<EffectInfoType> requestedInputs() {
        List<EffectInfoType> returnValue = new ArrayList<>();
        for(EffectInfoElement a: getEffectInfo().getEffectInfoElement()) {
            returnValue.add(a.getEffectInfoTypelist());
        }
        return returnValue;
    }

    public void handleRow(EffectInfoElement e ,Object[] input) {

        Object[] buffer = new Object[10];
        int X = 0;
        for(Object x: input) {
            buffer[X] = x;
            X++;
        }
        filledInputs.add(buffer);

        for (Object r : filledInputs)
        {
            System.out.println("ROW");
            for (Object w :(Object[]) r) {
                if (w != null) {
                    if (w.getClass().toString().equals("class it.polimi.se2019.model.Player"))
                        System.out.println("filled inputs " + ((Player)w).getNickname());
                    if (w.getClass().toString().equals("class it.polimi.se2019.model.Square"))
                        System.out.println("filled inputs [" + ((Square)w).getCoordinates().getX() + "," + ((Square)w).getCoordinates().getY() + "]");
                }
            }
        }
        int i = 0;
        int j = 0;
        /** TODO add a method to add a row per volta */
        this.getActions().get(0).getActionInfo().getActionContext().getPlayer().getPlayerHistory().addRecord(this.getOf(),this,buffer);
        // this.getActions().get(0).getActionInfo().getActionContext().getPlayer().getPlayerHistory().addRecord(this.getOf(),this,buffer);
        {

            List<Integer> p_arr = e.getEffectInfoTypeDestination();
            List<Integer> p_arr_2 = new ArrayList<>();
            if(p_arr.get(0) == 0) {     // TO ALL END
                int k = 0;
                for(Action a: this.getActions())
                    p_arr_2.add((++k));

            } else {
                p_arr_2 = p_arr;
            }
            System.out.println("l'input va " + p_arr_2.toString());

            for(Integer p: p_arr_2) {

                i = j;

                Integer position = p - 1;
                //parser dell'input
                System.out.println("<"+e.getEffectInfoTypelist().toString()+">");
                System.out.println("AZIONE (" + position +  ")" +  this.getActions().get(position) + "of " + this.getActions());

                // player select -- same Player
                if(e.getEffectInfoTypelist().toString().equals(player.toString())) {

                    System.out.println("settaggio del target tramite " +  getActions().get(position).getActionInfo().getActionContext().getPlayer());
                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setTarget(
                            getActions().get(position).getActionInfo().getActionContext().getPlayer()
                    );
                    // i++; /* 0 input */
                    Object[] adaptor = new Object[10];
                    adaptor[0] = getActions().get(position).getActionInfo().getActionContext().getPlayer();
                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/
                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(adaptor,"Target"));

                }

                //targetListByRoom


                if(e.getEffectInfoTypelist().toString().equals(targetListByRoom.toString())) {
                    Square A = (Square) input[0];
                    List<Square> room = getActions().get(0).getActionInfo().getActionContext().getBoard().getRoomFromPosition(A.getCoordinates());

                    System.out.println("y");
                    PlayersList ret = new PlayersList();

                    System.out.println("len " + this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayers().size());

                    for(Square s : room ) {
                        for (Player x : this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayers()) {

                            if (x.getPosition().getY() == s.getCoordinates().getY())
                                if (x.getPosition().getX() == s.getCoordinates().getX()) {

                                    ret.addPlayer(x);
                                    System.out.println("nome");
                                    if(! this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().getTargetList().contains(x))
                                        this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().addTarget(
                                                x
                                        );      //TODO MOLTO ARRAMPICATA SUGLI SPECCHI COME SOLUZIONE IL !CONTAINS
                                }


                        }
                    }
                    System.out.println(".");
                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/ {
                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(input, "Target"));
                    }
                    i++;

                }
                //targetListBySameSquareOfPlayer
                if(e.getEffectInfoTypelist().toString().equals(targetListBySameSquareOfPlayer.toString())) {
                    Player me = getActions().get(position).getActionInfo().getActionContext().getPlayer();
                    PlayersList potential = getActions().get(position).getActionInfo().getActionContext().getPlayerList();
                    PlayersList targets = new PlayersList();
                    for(Player po : potential.getPlayers()) {
                        if(po.getPosition().equals(me.getPosition()))
                            targets.addPlayer(po);
                    }
                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setTargetList(targets.getPlayers());
                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/ {
                        //   System.out.println(".");
                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(input, "Target"));
                    }

                }
                // singleTarget select

                if(e.getEffectInfoTypelist().toString().equals(singleTarget.toString())) {
                    System.out.println("-" + i);
                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setTarget(
                            (Player)(input[0])
                    );
                    System.out.println(">" + i);
                    System.out.println( "singleInput " + ((Player)input[0]).getNickname() +" aggiunto a " + getActions().get(position).getClass().toString());
                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/ {
                        //   System.out.println(".");
                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(input, "Target"));
                    }
                    // System.out.println(".");
                    i++;
                }
                // twoTargets select
                if(e.getEffectInfoTypelist().toString().equals(twoTargets.toString())) {
                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setTarget(
                            (Player)input[0]
                    );

                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().addTarget(
                            (Player)input[1]
                    );

                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/ {

                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(input, "Target"));
                    }
                    i ++;
                }
                // threeTargets select
                if(e.getEffectInfoTypelist().toString().equals(threeTargets.toString())) {
                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setTarget(
                            (Player)input[0]
                    );

                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().addTarget(
                            (Player)input[1]
                    );

                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().addTarget(
                            (Player)input[2]
                    );
                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/ {

                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(input, "Target"));
                    }
                    i++;
                }
                // targetListBySquare
                if(e.getEffectInfoTypelist().toString().equals(targetListBySquare.toString())) {
                    Square A = (Square) input[0];

                    System.out.println("y");
                    PlayersList ret = new PlayersList();

                    System.out.println("len " + this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayers().size());

                    for(Player x: this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayers()) {

                        if(x.getPosition().getY() == A.getCoordinates().getY())
                            if(x.getPosition().getX() == A.getCoordinates().getX()) {

                                // ret.addPlayer(x);
                                System.out.println("nome");
                                this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().addTarget(
                                        x
                                );
                            }




                    }
                    System.out.println(".");
                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/ {
                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(input, "Target"));
                    }
                    i++;

                }

                // playerSquare
                if(e.getEffectInfoTypelist().toString().equals(playerSquare.toString())) {

                    Player me = getActions().get(position).getActionInfo().getActionContext().getPlayer();
                    Object[] adaptor = new Object[10];
                    adaptor[0] = getActions().get(position).getActionInfo().getActionContext().getBoard().getMap()[me.getPosition().getX()][me.getPosition().getY()];

                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setChosenSquare(
                            getActions().get(position).getActionInfo().getActionContext().getBoard().getMap()[me.getPosition().getX()][me.getPosition().getY()]
                    );

                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/
                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(adaptor,"Target"));
                }
                // squareByTarget
                if(e.getEffectInfoTypelist().toString().equals(squareByTarget.toString())) {
                    Player target = (Player) input[0];
                    Board  board = getActions().get(0).getActionInfo().getActionContext().getBoard();

                    int I = 0;
                    int J = 0;

                    int x = 0;
                    int y = 0;
                    for(Square[] a: board.getMap()) {
                        for (Square b : a) {
                            if(target.getPosition().getX() == J)
                                if(target.getPosition().getY() == I)
                                {
                                    x = J;
                                    y = I;
                                }
                            J++;
                        }
                        I++;
                    }

                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setChosenSquare(
                            getActions().get(position).getActionInfo().getActionContext().getBoard().getMap()[x][y]
                    );
                    Object[][] adaptor = new Object[10][10];
                    adaptor[0][0] = getActions().get(position).getActionInfo().getActionContext().getBoard().getMap()[x][y];
                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/
                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(adaptor,"Target"));
                    i++;
                }
                // simpleSquareSelect
                if(e.getEffectInfoTypelist().toString().equals(simpleSquareSelect.toString())) {

                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setChosenSquare((Square)input[0]);

                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/
                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(input,"Square"));
                    i++;
                }
                if(e.getEffectInfoTypelist().toString().equals(targetListByDistance1.toString())) {
                    System.out.println("seleziono i target distanti 1");
                    Player me = getActions().get(position).getActionInfo().getActionContext().getPlayer();
                    List<Player> retVal = new ArrayList<>();
                    PlayersList enemies = getActions().get(0).getActionInfo().getActionContext().getPlayerList();
                    Object[][] adaptor = new Object[10][10];
                    int z = 0;


                    for(Player p_: enemies.getPlayers()) {
                        if(!p_.equals(me))
                            try {
                                System.out.println(p_.getPosition().getX() + "," + p_.getPosition().getY());
                                System.out.println(me.getPosition().getX() + "," + me.getPosition().getY());
                                System.out.println(getActions().get(position).getActionInfo().getActionContext().getBoard().distanceFromTo(p_.getPosition(), me.getPosition()));
                                System.out.println("|||");
                                if (getActions().get(position).getActionInfo().getActionContext().getBoard().distanceFromTo(p_.getPosition(), me.getPosition()) == 1) {
                                    retVal.add(p_);
                                    adaptor[0][z] = p_;
                                }
                            } catch (Exception E) {

                                System.out.println("eccezione " + p_.getNickname() + ":" + E.toString());
                            }
                    }

                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setTargetList(retVal);

                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/
                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(adaptor,"Square"));

                }
                if(e.getEffectInfoTypelist().toString().equals(squareByLastTargetSelected.toString())) {
                    System.out.println("@ ingresso ");
                    Player lastTarget = getActions().get(position - 1).getActionInfo().getActionDetails().getUserSelectedActionDetails().getTarget();
                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setChosenSquare(

                            this.getActions().get(position).getActionInfo().getActionContext().getBoard().getSquare(lastTarget.getPosition())

                    );

                    Object[][] adaptor = new Object[10][10];
                    adaptor[0][0] =   this.getActions().get(position).getActionInfo().getActionContext().getBoard().getSquare(lastTarget.getPosition());

                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/
                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(adaptor,"Square"));

                }
                if(e.getEffectInfoTypelist().toString().equals(targetListBySquareOfLastTarget.toString())) {
                    List<Player> targets = new ArrayList<>();
                    Player lastTarget = this.getActions().get(position - 1).getActionInfo().getActionDetails().getUserSelectedActionDetails().getTarget();
                    Position positionOfLastTarget = lastTarget.getPosition();
                    for(Player x: getActions().get(position).getActionInfo().getActionContext().getPlayerList().getPlayers()) {
                        if(x.getPosition().equals(positionOfLastTarget)) {
                            targets.add(x);
                        }
                    }

                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setTargetList(targets);
                    Object[][] adaptor = new Object[10][10];
                    int z = 0;
                    for(Player c: targets) {
                        adaptor[0][z] = c;
                        z++;
                    }
                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/
                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(adaptor,"Target"));
                }
                // singleTargetBySquare
                if(e.getEffectInfoTypelist().toString().equals(singleTargetBySquare.toString())) {
                    Square A = (Square) input[0];
                    Player B = (Player) input[1];

                    Player ret = new Player();

                    for(Player x: this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayers()) {
                        if(x.equals(B)) {
                            System.out.println("nome");

                            if(x.getPosition().getY() == A.getCoordinates().getY())
                                if(x.getPosition().getX() == A.getCoordinates().getX()) {

                                    ret = x;
                                    System.out.println("nome");
                                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setTarget(
                                            x
                                    );
                                }


                        }

                    }
                    System.out.println(".");
                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/ {
                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(input, "Target"));
                    }
                    i++;

                }

            }
            if(!(i == 0))
                j++;
        }

    }
    public void handleInput( Object[][] input) {
        Object[][] buffer = new Object[10][10];
        int X = 0;
        int Y = 0;
        for(Object[] x:input) {
            for (Object y : x) {
                buffer[X][Y] = y;
                Y++;
            }
            X++;
            Y= 0;
        }

        if(this.getActions().get(0).getActionInfo().getActionContext().getPlayer().getPlayerHistory().getSize() > 0)
            System.out.println("ultimo record: " + ((Player) ((Object[][])  this.getActions().get(0).getActionInfo().getActionContext().getPlayer().getPlayerHistory().getLast().getInput())[0][0] ).getNickname());



        this.getActions().get(0).getActionInfo().getActionContext().getPlayer().getPlayerHistory().addRecord(this.getOf(),this,buffer);

        for(Object[]x : ((Object[][])this.getActions().get(0).getActionInfo().getActionContext().getPlayer().getPlayerHistory().getLast().getInput()) ) {
            System.out.println("Riga");
            for (Object y : x)
                System.out.println("LOG: " + y);

        }

        //TODO : reference to the card
        int i= 0;int j = 0;
        for(EffectInfoElement e: this.getEffectInfo().getEffectInfoElement()) {

            List<Integer> p_arr = e.getEffectInfoTypeDestination();
            List<Integer> p_arr_2 = new ArrayList<>();
            if(p_arr.get(0) == 0) {     // TO ALL END
                int k = 0;
                for(Action a: this.getActions())
                    p_arr_2.add((++k));

            } else {
                p_arr_2 = p_arr;
            }
            System.out.println("l'input va " + p_arr_2.toString());
            for(Integer p: p_arr_2) {
                i = j;
                Integer position = p - 1;
                //parser dell'input
                System.out.println("<"+e.getEffectInfoTypelist().toString()+">");

                // player select -- same Player
                if(e.getEffectInfoTypelist().toString().equals(player.toString())) {

                    System.out.println("settaggio del target tramite " +  getActions().get(position).getActionInfo().getActionContext().getPlayer());
                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setTarget(
                            getActions().get(position).getActionInfo().getActionContext().getPlayer()
                    );
                    // i++; /* 0 input */
                    Object[] adaptor = new Object[10];
                    adaptor[0] = getActions().get(position).getActionInfo().getActionContext().getPlayer();
                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/
                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(adaptor,"Target"));

                }

                //targetListByRoom


                if(e.getEffectInfoTypelist().toString().equals(targetListByRoom.toString())) {
                    Square A = (Square) input[i][0];
                    List<Square> room = getActions().get(0).getActionInfo().getActionContext().getBoard().getRoomFromPosition(A.getCoordinates());

                    System.out.println("y");
                    PlayersList ret = new PlayersList();

                    System.out.println("len " + this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayers().size());

                    for(Square s : room ) {
                        for (Player x : this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayers()) {

                            if (x.getPosition().getY() == s.getCoordinates().getY())
                                if (x.getPosition().getX() == s.getCoordinates().getX()) {

                                    ret.addPlayer(x);
                                    System.out.println("nome");
                                    if(! this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().getTargetList().contains(x))
                                        this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().addTarget(
                                                x
                                        );      //TODO MOLTO ARRAMPICATA SUGLI SPECCHI COME SOLUZIONE IL !CONTAINS
                                }


                        }
                    }
                    System.out.println(".");
                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/ {
                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(input[i], "Target"));
                    }
                    i++;

                }
                //targetListBySameSquareOfPlayer
                if(e.getEffectInfoTypelist().toString().equals(targetListBySameSquareOfPlayer.toString())) {
                    Player me = getActions().get(position).getActionInfo().getActionContext().getPlayer();
                    PlayersList potential = getActions().get(position).getActionInfo().getActionContext().getPlayerList();
                    PlayersList targets = new PlayersList();
                    for(Player po : potential.getPlayers()) {
                        if(po.getPosition().equals(me.getPosition()))
                            targets.addPlayer(po);
                    }
                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setTargetList(targets.getPlayers());
                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/ {
                        //   System.out.println(".");
                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(input[i], "Target"));
                    }

                }
                // singleTarget select

                if(e.getEffectInfoTypelist().toString().equals(singleTarget.toString())) {
                    System.out.println("-" + i);
                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setTarget(
                            (Player)(input[i][0])
                    );
                    System.out.println(">" + i);
                    System.out.println( "singleInput " + ((Player)input[i][0]).getNickname() +" aggiunto a " + getActions().get(position).getClass().toString());
                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/ {
                        //   System.out.println(".");
                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(input[i], "Target"));
                    }
                    // System.out.println(".");
                    i++;
                }
                // twoTargets select
                if(e.getEffectInfoTypelist().toString().equals(twoTargets.toString())) {
                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setTarget(
                            (Player)input[i][0]
                    );

                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().addTarget(
                            (Player)input[i][1]
                    );

                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/ {

                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(input[i], "Target"));
                    }
                    i ++;
                }
                // threeTargets select
                if(e.getEffectInfoTypelist().toString().equals(threeTargets.toString())) {
                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setTarget(
                            (Player)input[i][0]
                    );

                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().addTarget(
                            (Player)input[i][1]
                    );

                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().addTarget(
                            (Player)input[i][2]
                    );
                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/ {

                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(input[i], "Target"));
                    }
                    i++;
                }
                // targetListBySquare
                if(e.getEffectInfoTypelist().toString().equals(targetListBySquare.toString())) {
                    Square A = (Square) input[i][0];

                    System.out.println("y");
                    PlayersList ret = new PlayersList();

                    System.out.println("len " + this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayers().size());

                    for(Player x: this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayers()) {

                        if(x.getPosition().getY() == A.getCoordinates().getY())
                            if(x.getPosition().getX() == A.getCoordinates().getX()) {

                                ret.addPlayer(x);
                                System.out.println("nome");
                                this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().addTarget(
                                        x
                                );
                            }




                    }
                    System.out.println(".");
                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/ {
                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(input[i], "Target"));
                    }
                    i++;

                }

                // playerSquare
                if(e.getEffectInfoTypelist().toString().equals(playerSquare.toString())) {

                    Player me = getActions().get(position).getActionInfo().getActionContext().getPlayer();
                    Object[] adaptor = new Object[10];
                    adaptor[0] = getActions().get(position).getActionInfo().getActionContext().getBoard().getMap()[me.getPosition().getX()][me.getPosition().getY()];

                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setChosenSquare(
                            getActions().get(position).getActionInfo().getActionContext().getBoard().getMap()[me.getPosition().getX()][me.getPosition().getY()]
                    );

                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/
                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(adaptor,"Target"));
                }
                // squareByTarget
                if(e.getEffectInfoTypelist().toString().equals(squareByTarget.toString())) {
                    Player target = (Player) input[i][0];
                    Board  board = getActions().get(0).getActionInfo().getActionContext().getBoard();

                    int I = 0;
                    int J = 0;

                    int x = 0;
                    int y = 0;
                    for(Square[] a: board.getMap()) {
                        for (Square b : a) {
                            if(target.getPosition().getX() == J)
                                if(target.getPosition().getY() == I)
                                {
                                    x = J;
                                    y = I;
                                }
                            J++;
                        }
                        I++;
                    }

                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setChosenSquare(
                            getActions().get(position).getActionInfo().getActionContext().getBoard().getMap()[x][y]
                    );
                    Object[][] adaptor = new Object[10][10];
                    adaptor[0][0] = getActions().get(position).getActionInfo().getActionContext().getBoard().getMap()[x][y];
                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/
                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(adaptor,"Target"));
                    i++;
                }
                // simpleSquareSelect
                if(e.getEffectInfoTypelist().toString().equals(simpleSquareSelect.toString())) {
                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setChosenSquare((Square)input[i][0]);

                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/
                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(input[i],"Square"));
                    i++;
                }
                if(e.getEffectInfoTypelist().toString().equals(targetListByDistance1.toString())) {
                    System.out.println("seleziono i target distanti 1");
                    Player me = getActions().get(position).getActionInfo().getActionContext().getPlayer();
                    List<Player> retVal = new ArrayList<>();
                    PlayersList enemies = getActions().get(0).getActionInfo().getActionContext().getPlayerList();
                    Object[][] adaptor = new Object[10][10];
                    int z = 0;


                    for(Player p_: enemies.getPlayers()) {
                        if(!p_.equals(me))
                            try {
                                System.out.println(p_.getPosition().getX() + "," + p_.getPosition().getY());
                                System.out.println(me.getPosition().getX() + "," + me.getPosition().getY());
                                System.out.println(getActions().get(position).getActionInfo().getActionContext().getBoard().distanceFromTo(p_.getPosition(), me.getPosition()));
                                System.out.println("|||");
                                if (getActions().get(position).getActionInfo().getActionContext().getBoard().distanceFromTo(p_.getPosition(), me.getPosition()) == 1) {
                                    retVal.add(p_);
                                    adaptor[0][z] = p_;
                                }
                            } catch (Exception E) {

                                System.out.println("eccezione " + p_.getNickname() + ":" + E.toString());
                            }
                    }

                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setTargetList(retVal);

                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/
                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(adaptor,"Square"));

                }
                if(e.getEffectInfoTypelist().toString().equals(squareByLastTargetSelected.toString())) {
                    System.out.println("@ ingresso ");
                    Player lastTarget = getActions().get(position - 1).getActionInfo().getActionDetails().getUserSelectedActionDetails().getTarget();
                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setChosenSquare(

                            this.getActions().get(position).getActionInfo().getActionContext().getBoard().getSquare(lastTarget.getPosition())

                    );

                    Object[][] adaptor = new Object[10][10];
                    adaptor[0][0] =   this.getActions().get(position).getActionInfo().getActionContext().getBoard().getSquare(lastTarget.getPosition());

                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/
                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(adaptor,"Square"));

                }
                if(e.getEffectInfoTypelist().toString().equals(targetListBySquareOfLastTarget.toString())) {
                    List<Player> targets = new ArrayList<>();
                    Player lastTarget = this.getActions().get(position - 1).getActionInfo().getActionDetails().getUserSelectedActionDetails().getTarget();
                    Position positionOfLastTarget = lastTarget.getPosition();
                    for(Player x: getActions().get(position).getActionInfo().getActionContext().getPlayerList().getPlayers()) {
                        if(x.getPosition().equals(positionOfLastTarget)) {
                            targets.add(x);
                        }
                    }

                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setTargetList(targets);
                    Object[][] adaptor = new Object[10][10];
                    int z = 0;
                    for(Player c: targets) {
                        adaptor[0][z] = c;
                        z++;
                    }
                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/
                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(adaptor,"Target"));
                }
                // singleTargetBySquare
                if(e.getEffectInfoTypelist().toString().equals(singleTargetBySquare.toString())) {
                    Square A = (Square) input[i][0];
                    String Name = (String) input[i][1];
                    System.out.println("y");
                    Player ret = new Player();
                    System.out.println("len " + this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayers().size());
                    for(Player x: this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayers()) {
                        if(x.getNickname().equals(Name)) {
                            System.out.println("nome");

                            if(x.getPosition().getY() == A.getCoordinates().getY())
                                if(x.getPosition().getX() == A.getCoordinates().getX()) {

                                    ret = x;
                                    System.out.println("nome");
                                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setTarget(
                                            x
                                    );
                                }


                        }

                    }
                    System.out.println(".");
                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/ {
                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(input[i], "Target"));
                    }
                    i++;

                }

            }
            if(!(i == 0))
                j++;
        }
    }
    /***/
    public void setContext(ActionContext actionContext) {

        for(Action a : getActions()) {
            a.getActionInfo().setActionContext(actionContext);
        }

    }
    public boolean Exec() {
        boolean isExecutable = true;
        /*gestione effect info */
        System.out.println("inizializzo effetto");
        for(Action a:this.actions){
            if(a.getActionInfo().preCondition() == false ) {            // checks if all the preConditions are true
                isExecutable = false;
            }
        }
        System.out.println("è eseguibile? "+ isExecutable);
        if(isExecutable) {
            for (Action a : this.actions) {
                /*@*/ System.out.println("esecuzione > " + a.toString());
                //System.out.println("> " + a.getActionInfo().getActionContext().getActionContextFilteredInputs().get(0));
                a.Exec();
                /*refreshing the context of the action*/
                for(Action b: this.actions) {
                    //  a.getActionInfo().setActionContext();
                }
            }
            return true;
        }
        return false;
    }

}