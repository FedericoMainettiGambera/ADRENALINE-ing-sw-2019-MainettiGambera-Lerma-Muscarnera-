package it.polimi.se2019.model;


import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.enumerations.UsableInputTableRowType;
import it.polimi.se2019.view.components.EffectV;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.se2019.model.enumerations.EffectInfoType.*;

/***/
public class Effect implements Serializable {
    public boolean isMoveDuringEffect() {
        return MoveDuringEffect;
    }

    public void setMoveDuringEffect(boolean moveDuringEffect) {
        MoveDuringEffect = moveDuringEffect;
    }

    private boolean MoveDuringEffect;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public AmmoList getUsageCost() {
        return usageCost;
    }

    public void setUsageCost(AmmoList usageCost) {
        this.usageCost = usageCost;
    }

    private AmmoList usageCost;


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
    /** REFACTORING usableINPUT **/
    private List<Object> retValRowsInitialize() {
        List<UsableInputTableRowType> rowType = new ArrayList<>();
        List<UsableInputTableRowType> frontEndRowType = new ArrayList<>();
        List<List<List<Object>>> retVal = new ArrayList<>();


        for(EffectInfoElement e: this.getEffectInfo().getEffectInfoElement()) {
            if(e.getEffectInfoTypelist() == targetListBySquareOfLastTarget) {
                rowType.add(UsableInputTableRowType.typePlayer);
                frontEndRowType.add(null);

                List<Object> cell = new ArrayList<>();
                Player currentPlayer = this.getActions().get(0).getActionInfo().getActionContext().getPlayer();
                Object lastRow;
                Object lastTarget;

                boolean addAll = false;
                if(currentPlayer.getPlayerHistory().getTurnChunkR(currentPlayer.getTurnID()) != null) {
                    PlayerHistory currentHistory =  currentPlayer.getPlayerHistory().getTurnChunkR(currentPlayer.getTurnID());
                    System.out.println("<SERVER> showing player history ");

                    currentHistory.show();
                    if( currentHistory.getSize() > 0 ) {
                        lastRow = currentPlayer.getPlayerHistory().getTurnChunkR(currentPlayer.getTurnID()).getLast().getInput();
                        lastTarget = ((Player) (((Object[]) lastRow)[0]));
                    } else{
                        addAll = true;
                        lastTarget = null;
                    }
                } else {
                    addAll = true;
                    lastTarget = null;
                }
                System.out.println(lastTarget);
                if(addAll) {
                    for(Player p: getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayersOnBoard())
                        cell.add(p);
                } else {
                    System.out.println(lastTarget);
                    Square lastTargetSquare = this.getActions().get(0).getActionInfo().getActionContext().getBoard().getSquare(
                            ((Player) lastTarget).getPosition()
                    );
                    for (Player t : this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayersOnBoard()) {
                        if(t.getPosition().equalPositions(
                                lastTargetSquare.getCoordinates()
                        ))  {
                            cell.add((Object) t);
                        }
                    }
                }

                List<List<Object>> row = new ArrayList<>();
                row.add(cell);
                retVal.add(row);

            }

            if(e.getEffectInfoTypelist() == targetListBySquare) {
                rowType.add(UsableInputTableRowType.typePlayer);
                frontEndRowType.add(UsableInputTableRowType.typeSquare);


                List<Object> cell = new ArrayList<>();
                for(Player t: this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayersOnBoard())
                    cell.add((Object) t);
                List<List<Object>> row = new ArrayList<>();

                row.add(cell);
                retVal.add(row);

            }

            if(e.getEffectInfoTypelist() == singleTargetBySquare) {
                rowType.add(UsableInputTableRowType.typePlayer);
                frontEndRowType.add(UsableInputTableRowType.typeSquare);


                List<Object> cell = new ArrayList<>();
                for(Player t: this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayersOnBoard())
                    cell.add((Object) t);
                List<List<Object>> row = new ArrayList<>();

                row.add(cell);
                retVal.add(row);

            }
            if(e.getEffectInfoTypelist() == singleTarget) {
                rowType.add(UsableInputTableRowType.typePlayer);
                frontEndRowType.add(UsableInputTableRowType.typePlayer);




                List<Object> cell = new ArrayList<>();
                for(Player t: this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayersOnBoard())
                    cell.add((Object) t);
                List<List<Object>> row = new ArrayList<>();

                row.add(cell);
                retVal.add(row);
            }
            if(e.getEffectInfoTypelist() == twoTargets) {
                rowType.add(UsableInputTableRowType.typePlayer);
                frontEndRowType.add(UsableInputTableRowType.typePlayer);


                List<Object> cell = new ArrayList<>();
                for(Player t: this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayersOnBoard())
                    cell.add((Object) t);
                List<Object> cell2 = new ArrayList<>();
                for(Player t: this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayersOnBoard())
                    cell2.add((Object) t);
                List<List<Object>> row = new ArrayList<>();

                row.add(cell);
                row.add(cell2);     //slot doppio per doppia cardinalità

                retVal.add(row);
            }
            if(e.getEffectInfoTypelist() == threeTargets) {
                rowType.add(UsableInputTableRowType.typePlayer);
                frontEndRowType.add(UsableInputTableRowType.typePlayer);


                List<Object> cell = new ArrayList<>();
                for(Player t: this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayersOnBoard())
                    cell.add((Object) t);
                List<Object> cell2 = new ArrayList<>();
                for(Player t: this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayersOnBoard())
                    cell2.add((Object) t);
                List<List<Object>> row = new ArrayList<>();
                List<Object> cell3 = new ArrayList<>();
                for(Player t: this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayersOnBoard())
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
                    for (Square y[] : this.getActions().get(0).getActionInfo().getActionContext().getBoard().getMap()) {
                        for(Square x: y) {
                            if(x != null)
                                cell.add(x);
                        }
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
                for (Player x : this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayersOnBoard()) {
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
                for (Player x : this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayersOnBoard()) {
                    if(x.getPosition().equalPositions(this.getActions().get(0).getActionInfo().getActionContext().getPlayer().getPosition()))
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


                List<Object> cell = new ArrayList<>();
                for (Square y[] : this.getActions().get(0).getActionInfo().getActionContext().getBoard().getMap()) {
                    for(Square x: y) {
                        try {
                            if (x != null)
                            {
                                if (getActions().get(0).getActionInfo().getActionContext().getBoard().distanceFromTo(
                                        x.getCoordinates(),
                                        getActions().get(0).getActionInfo().getActionContext().getPlayer().getPosition()
                                ) == 1) {
                                    for (Player p : this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayersOnBoard()) {
                                        if (p.getPosition().equalPositions(x.getCoordinates())) {
                                            if (!cell.contains(p))
                                                cell.add(p);
                                        }
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


                List<Object> cell = new ArrayList<>();
                for (Square y[] : this.getActions().get(0).getActionInfo().getActionContext().getBoard().getMap()) {
                    for(Square x: y) {
                        if(x != null)
                            cell.add(x);
                    }
                }
                List<List<Object>> row = new ArrayList<>();

                row.add(cell);
                retVal.add(row);
            }
            /*...*/
        }

        List<Object> returnArray = new ArrayList<>();
         returnArray.add(retVal);
         returnArray.add(rowType);
         returnArray.add(frontEndRowType);
        return returnArray;
    }

    private List<Object> callPIByName(String name,
                                      ActionContext actionContext,
                                      UsableInputTableRowType type,

                                      ActionDetails actionDetails) {
        Object invertedPreConditionOutput = null;
        PreConditionMethodsInverted preConditionMethodsInverted = new PreConditionMethodsInverted();
        try {
            java.lang.reflect.Method method;
            Class<?> c = Class.forName("it.polimi.se2019.model.PreConditionMethodsInverted");
            //(ActionContext actionContext, UsableInputTableRowType type, Integer cardinality, Object inputs, List<EffectInfoElement> inputSlots)
            Class<?>[] paramTypes = {ActionContext.class,UsableInputTableRowType.class,ActionDetails.class,Object.class,List.class,Effect.class};
//                        System.out.println("azione " + a_ + " nome precondizione : " + a_.getActionInfo().getPreConditionMethodName());

            method = c.getDeclaredMethod(name, paramTypes);
            System.out.println("# " +" @@@ eseguo l'inversa di " + name);
            System.out.println("--");
            System.out.println( "@" + this.getActions().get(0).getActionInfo().getActionContext() );
            System.out.println( "@" + type + ">");
            System.out.println( "@" + actionDetails);
            System.out.println( "@" + filledInputs);
            System.out.println( "@" + requestedInputs());
            invertedPreConditionOutput = method.invoke(preConditionMethodsInverted,
                    actionContext,
                    type,
                    actionDetails,
                    filledInputs,
                    requestedInputs(),
                    this
            );
            System.out.println("--");
        } catch (Exception exception) {
            System.out.println("eccezione! " + exception);
            exception.printStackTrace();
        }
        return (List<Object>) invertedPreConditionOutput;
    }
    private Action getAction(int n) {return this.getActions().get(n);}
    private void BackFrontEndAdaptor(List<List<List<Object>>> retVal, List<UsableInputTableRowType> rowType, List<UsableInputTableRowType>  frontEndRowType)
    {
        int frontCounter = 0;
        System.out.println("risultato iniziale " + retVal);
        for(UsableInputTableRowType front: frontEndRowType) {
            UsableInputTableRowType back = rowType.get(frontCounter);
            if (front != null)
                if (front.equals(UsableInputTableRowType.typePlayer)) {
                    if (back.equals(UsableInputTableRowType.typePlayer)) {
                        System.out.println("@@@@@@@@ " + retVal.get(frontCounter).get(0));
                        // già formattato

                    }
                    if (back.equals(UsableInputTableRowType.typeSquare)) {

                        for (Object o : retVal.get(frontCounter).get(0)) {
                            int X;
                            int Y;
                            if (this.isMoveDuringEffect()) {
                                X = ((Player) o).getTemporaryPosition().getX();
                                Y = ((Player) o).getTemporaryPosition().getY();
                            } else {
                                X = ((Player) o).getPosition().getX();
                                Y = ((Player) o).getPosition().getY();
                            }
                            o = this.getActions().get(0).getActionInfo().getActionContext().getBoard().getSquare(X, Y);

                        }


                    }

                }
            if (front != null)
                if (front.equals(UsableInputTableRowType.typeSquare)) {
                    if (back.equals(UsableInputTableRowType.typeSquare)) {

                        // già formattato

                    }
                    if (back.equals(UsableInputTableRowType.typePlayer)) {
                        System.out.println("front : square, back : player");
                        List<Object> newRow = new ArrayList<>();
                        for (Object o : retVal.get(frontCounter).get(0)) {
                            int X;
                            int Y;
                            if (this.isMoveDuringEffect()) {
                                X = ((Player) o).getTemporaryPosition().getX();
                                Y = ((Player) o).getTemporaryPosition().getY(); // TODO if it gives problems remove "Temporary"
                            } else {
                                X = ((Player) o).getPosition().getX();
                                Y = ((Player) o).getPosition().getY();
                            }
                            Position pos = new Position(X, Y);
                            if (!newRow.contains(this.getActions().get(0).getActionInfo().getActionContext().getBoard().getSquare(X, Y)))
                                newRow.add(this.getActions().get(0).getActionInfo().getActionContext().getBoard().getSquare(X, Y));
                            // o = this.getActions().get(0).getActionInfo().getActionContext().getBoard().getSquare(X,Y);

                        }

                        retVal.get(frontCounter).set(0, newRow);
                    }

                }

            frontCounter++;
        }
        System.out.println("risultato iniziale " + retVal);

    }
    public void DamageAndMarkPlayerRemover(List<Object> row,Action action)
    {
        if(action.getClass().equals(Damage.class) || action.getClass().equals(Mark.class))
        {
            Player me = action.getActionInfo().getActionContext().getPlayer();
            if(row.contains(me))
                row.remove(me);
        }
    }
    public List<List<List<Object>>> usableInputs() {

        // initialize rows of retVal
        List<Object> init = retValRowsInitialize();
        List<List<List<Object>>> retVal = ((List<List<List<Object>>>) init.get(0));
        List<UsableInputTableRowType> rowType = ( List<UsableInputTableRowType>) init.get(1);
        List<UsableInputTableRowType> frontEndRowType = ( List<UsableInputTableRowType>) init.get(2);

        int inputCounter = 0;


        // costruisco i collegamenti
        //          destiations(0) = {0,1}  => l'input 1 è collegato all'azione 0 e all'azione 1
        List<List<Integer>> destinations = new ArrayList<>();


        for(int i = 0; i < this.requestedInputs().size();i++) {
            destinations.add(new ArrayList<>());
            // inizializzo destinations

            EffectInfoType input = requestedInputs().get(i);
            List<Integer> inputDest = this.getEffectInfo().getEffectInfoElement().get(i).getEffectInfoTypeDestination();
            if(inputDest.get(0) == 0)
            {
                // all actions
                for(int a = 0;a < this.getActions().size();a++) {
                    destinations.get(i).add(a);
                }
            }   else {
                for( Integer to : inputDest)
                     destinations.get(i).add(to - 1);
            }

        }
        // inizializzo le liste intermedie
        List<List<Object>> intermediateList = new ArrayList<>();
        for(int i = 0; i < this.requestedInputs().size();i++) {
            intermediateList.add(new ArrayList<>());
        }
        // log rowtype
        for(UsableInputTableRowType i: rowType)
            System.out.println("ROW TYPE : "+ i);


        // per ogni input input
        for(int i = 0; i < this.requestedInputs().size();i++) {
            EffectInfoType input = requestedInputs().get(i);
                // per ogni azione a collegata all'input

                     for(Integer a: destinations.get(i)) {
                         System.out.println("<SERVER> ["  + i +"," + a + "] calling " +  getAction(a).getActionInfo().getPreConditionMethodName() );
                         intermediateList.get(i).add(

                            new ArrayList<>()

                         );
                         List<Object> PIresult = callPIByName(
                                 getAction(a).getActionInfo().getPreConditionMethodName(),
                                 getAction(0).getActionInfo().getActionContext(),
                                 rowType.get(i),
                                 getAction(a).getActionInfo().getActionDetails()
                         );

                                 /*for(Object o: PIresult)
                                     ((List<Object>) (intermediateList.get(i).get(
                                             intermediateList.get(i).size() - 1
                                     ))).add(o);*/
                         intermediateList.get(i).set(
                                 intermediateList.get(i).size() - 1,
                                 PIresult);
                                    System.out.println("<SERVER> output: "+ i + "," + (intermediateList.get(i).size() - 1) + ":"+ intermediateList.get(i).get(
                                            intermediateList.get(i).size() - 1
                                    ));

                // lista(i).push(
                //
                //              a.preconditionInversa(
                //                                      ActionContext
                //                                      typePlayer | typeSquare
                //                                      ActionDetails
                //                                      filledInputs
                //                                      requestedInputs()
                //                                      this
                //                                     )
                //
                //              )

                    }
        }
        //      # filtro
        //      per ogni lista(i)
        //      inizializzo listafinale
        //          per ogni lista(i)(a)
        //              retVal(i) = retVal(i) intersecato lista(i)(a)

        // <log>
        int n = 0;
        int m = 0;
        for(List<Object> A: intermediateList )
            {
                System.out.println("<SERVER> input " + n);
                for(Object B: A) {
                    System.out.println("<SERVER> \t \t \t azione " + m + "° dell'input ; " + destinations.get(n).get(m) + "° totale :" + B);
                    System.out.println("\t \t \t \t \t \t precondizione di sopra : " + getAction(destinations.get(n).get(m) ).getActionInfo().getPreConditionMethodName());
                    System.out.println("\t \t \t \t \t \t " + B);
                    m++;
                }
                m = 0;
                n++;
            }

        // </log>
        // intersezione soluzioni
        for(int i = 0; i < this.requestedInputs().size();i++)
        {
            System.out.println("<SERVER> inizio l'intersezione partendo da " + retVal.get(i).get(0));
            for(int a = 0; a < intermediateList.get(i).size(); a++) {
                System.out.println("<SERVER> interseco con " + "[" + i +"," + a + " (" +  destinations.get(i).get(a) +  ")" + "] " + (List<Object>) intermediateList.get(i).get(a));
                System.out.println("         si tratta del dominio di " + getAction(destinations.get(i).get(a)).getActionInfo().getPreConditionMethodName()) ;
                retVal.get(i).set(0,
                        intersect(
                                retVal.get(i).get(0),
                                (List<Object>) intermediateList.get(i).get(a)
                        ));
                System.out.println("<SERVER> ottengo " + retVal.get(i).get(0));
                // rimozione del player nelle azioni che contengono damage
                DamageAndMarkPlayerRemover(retVal.get(i).get(0),getAction(destinations.get(i).get(a)));
                // NOTA BENE: è ridondante, basterebbe farlo una sola volta, ma a livello
                // di scrittura è immensamente più comodo così
            }
        }


        // adattamento dei front e back type
        BackFrontEndAdaptor(retVal,rowType,frontEndRowType);
        return retVal;
    }

    /****/

    public void setOf(WeaponCard of) {
        this.of = of;
    }


    private transient WeaponCard of;

    public List<Object> getFilledInputs() {
        return filledInputs;
    }


// DEBUG
    public void setFilledInputs(List<Object> filledInputs) {
        this.filledInputs = filledInputs;
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
        this.setName("Basic Effect");       // default name
        this.setMoveDuringEffect(false);
        this.usageCost = new AmmoList();    // default cost ( zero )
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
    /*
    public boolean valuateAllPrecondition() {

        boolean retVal = true;
        for(Action a: actions) {
            if(!a.getActionInfo().preCondition()) {
                retVal = false;
            }
        }
        return retVal;

    }*/
    /*
    public boolean validContext(Player player,Board board,PlayersList playersList) {
        ActionContext context = new ActionContext();
        context.setPlayer(player);
        context.setPlayerList(playersList);
        context.setBoard(board);
        setContext(context);
        return valuateAllPrecondition();
    }*/
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
        // ogni volta che una move viene riempita aggiorno la temporary poition
        Object[] buffer = new Object[10];
        int X = 0;
        for(Object x: input) {
            buffer[X] = x;
            X++;
        }
        filledInputs.add(buffer);

       /* for (Object r : filledInputs)
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
        }*/
        int i = 0;
        int j = 0;
        /** TODO add a method to add a row per volta */
        this.getActions().get(0).getActionInfo().getActionContext().getPlayer().getPlayerHistory().addRecord(
                this.getOf(),
                this,
                buffer);
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

                    System.out.println("len " + this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayersOnBoard().size());

                    for(Square s : room ) {
                        for (Player x : this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayersOnBoard()) {

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
                    for(Player po : potential.getPlayersOnBoard()) {
                        if(po.getPosition().equalPositions(me.getPosition()))
                            targets.addPlayer(po);
                    }
                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setTargetList(targets.getPlayersOnBoard());
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

                    if(input[1] != null)
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
                    if(input[1] != null)
                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().addTarget(
                            (Player)input[1]
                    );

                    if(input[2] != null)
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

                    System.out.println("len " + this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayersOnBoard().size());

                    for(Player x: this.getActions().get(0).getActionInfo().getActionContext().getPlayerList().getPlayersOnBoard()) {
                        int a,b;
                        if(isMoveDuringEffect()) {
                            System.out.println("target su una posizione temporanea " + x.getTemporaryPosition().humanString());
                            a = x.getTemporaryPosition().getX();
                            b = x.getTemporaryPosition().getY();
                        } else {
                            System.out.println("target su una posizione fissata " + x.getPosition().humanString());
                            a = x.getPosition().getX();
                            b = x.getPosition().getY();
                        }
                        if(a  == A.getCoordinates().getY())
                            if(b == A.getCoordinates().getX()) {

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
                //squareOfLastTargetSelected

                // squareByTarget
                if(e.getEffectInfoTypelist().toString().equals(squareByTarget.toString())) {
                    Player target = (Player) input[0];
                    Board  board = getActions().get(0).getActionInfo().getActionContext().getBoard();

                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setChosenSquare(
                            getActions().get(position).getActionInfo().getActionContext().getBoard().getSquare(target.getPosition())
                    );
                    Object[][] adaptor = new Object[10][10];
                    adaptor[0][0] = getActions().get(position).getActionInfo().getActionContext().getBoard().getSquare(target.getPosition());
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
                    for(Player x: getActions().get(position).getActionInfo().getActionContext().getPlayerList().getPlayersOnBoard()) {
                        if(x.getPosition().equalPositions(positionOfLastTarget)) {
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
                /** temporary position */

                if(this.getActions().get(position).getClass().equals(Move.class)) {
                    if( this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().getTargetList().size() != 0 )
                    {
                        if(this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().getChosenSquare() != null) {

                            for(Player t: this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().getTargetList()) {
                                t.setTemporaryPosition(
                                        this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().getChosenSquare().getCoordinates()
                                );
                                System.out.println("sposto transitoriamente" + t.getNickname() + " da " + t.getPosition().humanString() + " a " + t.getTemporaryPosition().humanString());

                            }

                        }
                    }
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
    public List<List<Player>> Exec() {

        List<List<Player>> retVal = new ArrayList<>();

        System.out.println("######## EXEC INIZIATA ###########");
        boolean isExecutable = true;
        /*gestione effect info */
        System.out.println("inizializzo effetto");


            for (Action a : this.actions) {
                /*@*/ System.out.println("esecuzione > " + a.toString());
                //System.out.println("> " + a.getActionInfo().getActionContext().getActionContextFilteredInputs().get(0));
                a.Exec();
                /*refreshing the context of the action*/
                if(a.getClass().equals(Damage.class)) {
                    retVal.add(new ArrayList<>());
                    for(Player p: a.getActionInfo().getActionDetails().getUserSelectedActionDetails().getTargetList()){
                        if(p != null)
                            retVal.get(retVal.size() - 1).add(p);
                    }
                }
            }

        System.out.println("######## EXEC FINITA ###########");


        return retVal;
    }

    public EffectV buildEffectV(){
        EffectV effectV = new EffectV();
        effectV.setDescription(this.description);
        effectV.setEffectInfo(this.effectInfo);
        effectV.setEffectName(this.effectName);
        return effectV;
    }

}