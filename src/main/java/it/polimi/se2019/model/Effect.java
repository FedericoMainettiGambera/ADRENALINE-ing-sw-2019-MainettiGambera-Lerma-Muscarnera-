package it.polimi.se2019.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.se2019.model.enumerations.EffectInfoType.*;

/***/
public class Effect implements Serializable {
    /*-****************************************************************************************************CONSTRUCTOR*/

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
    public void handleInput(Object[][] input) {
        int i= 0;
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
            for(Integer p: p_arr_2) {

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


                // singleTarget select
                if(e.getEffectInfoTypelist().toString().equals(singleTarget.toString())) {
                   this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setTarget(
                           (Player)input[i][0]
                   );

                   for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/
                            a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(input[i],"Target"));
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
                    i ++;
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



                // simpleSquareSelect
                if(e.getEffectInfoTypelist().toString().equals(simpleSquareSelect.toString())) {
                    this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setChosenSquare((Square)input[i][0]);

                    for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/
                        a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(input[i],"Square"));
                    i++;
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


        }
    }
    /***/
    public boolean Exec() {
        boolean isExecutable = true;
        /*gestione effect info */

        for(Action a:this.actions){
            if(a.getActionInfo().preCondition() == false ) {            // checks if all the preConditions are true
                isExecutable = false;
            }
        }
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