package it.polimi.se2019.model;


import it.polimi.se2019.model.enumerations.EffectInfoType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.se2019.model.enumerations.EffectInfoType.*;

/***/
public class Effect implements Serializable {
    public WeaponCard getOf() {
        return of;
    }

    public void setOf(WeaponCard of) {
        this.of = of;
    }

    WeaponCard of;

    public Object[][] getFilledInputs() {
        return filledInputs;
    }

    /*-****************************************************************************************************CONSTRUCTOR*/
    private Object[][] filledInputs;

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
    }


    public Effect() {
        this.actions = new ArrayList<Action>();
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
    private List<Action> actions;

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
    public List<EffectInfoType> requestedInputs() {
        List<EffectInfoType> returnValue = new ArrayList<>();
        for(EffectInfoElement a: getEffectInfo().getEffectInfoElement()) {
           returnValue.add(a.getEffectInfoTypelist());
        }
        return returnValue;
    }
    public void handleInput(Object[][] input) {
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