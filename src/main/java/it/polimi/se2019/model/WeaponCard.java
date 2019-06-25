package it.polimi.se2019.model;


import it.polimi.se2019.controller.statePattern.ChooseHowToPayState;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.view.components.EffectV;
import it.polimi.se2019.view.components.WeaponCardV;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static it.polimi.se2019.model.enumerations.AmmoCubesColor.*;


/***/
public class WeaponCard extends Card implements Serializable {

    /***/

   public WeaponCard(String ID, AmmoList reloadCost, List<Effect> effects) {
        super(ID);
        this.isLoaded = true;
        this.effects = effects;
        this.reloadCost = reloadCost;

    }
    /***/








    private static List<Object> retFlat;
    public static Object arrayFlattener(Object x,int level) {
        List<Object> retVal = new ArrayList<>();
        /*caso base : l'oggetto è un oggetto*/
        if(level == 0) {
            retFlat = new ArrayList<>();
        }
        if(!x.getClass().toString().equals("class java.util.ArrayList"))
        {
            return x;
        }

        for(Object y: (ArrayList<Object>)x) {
            if(y.getClass().toString().equals("class java.util.ArrayList")) {


                retVal.add(arrayFlattener(y,level + 1));
            } else {
                retFlat.add(y);
                retVal.add(y);
            }

        }
        if(level == 0) {
            int cCur = 0;
            int dCur = 0;
            for(Object c: retFlat){
                for(Object d: retFlat) {
                    if(c.equals(d)) {          // c == d
                        if(cCur != dCur)
                        {
                            //System.out.println("occorrenza " + cCur + "," + dCur);
                            return null;
                        }
                    }
                    dCur++;
                }
                dCur = 0;
                cCur++;
            }
            return retFlat;
        }
        return retVal;
    }
    public static List<Object> cartesianPower(Object A,int n) {
        List<Object> Base = new ArrayList<>();
        List<Object> Result = new ArrayList<>();
        for(Object x: (ArrayList<Object>)A) {       // base = A
            Base.add(x);
        }
        if(n == 1) {
            return (ArrayList<Object>)A;
        }
        for(int i = 1; i <= n;i++) {
            if(i==1)  {
                for(Object y: Base) {
                    Result.add(y);
                }
            } else {
                Result = cardinalProduct(Result, Base,false);
            }
        }
        return Result;
    }

    public static List<Object> cardinalProduct(List<Object> A,List<Object> B,boolean keepSame) {


        List<Object> retVal = new ArrayList<>();
        //System.out.println(A.size());
        // System.out.println(B.size());

        for(Object a: A)
            for(Object b:B) {
                if(!a.equals(b) && !keepSame && (a != null)  && (b!=null)) {
                    List<Object> cell = new ArrayList<>();
                    cell.add(a);
                    cell.add(b);
                    retVal.add((Object) cell);
                }
                // System.out.println("x" + (Object) cell);

    }
        return retVal;
    }












    public void passContext(Player player,PlayersList playersList,Board board) {
        for(Effect e: this.getEffects()) {
            e.passContext(player,playersList,board);
        }
        }

    public List<Effect> usableEffects() {
        List<Effect> retVal = new ArrayList<>();
        for(Effect e:this.getEffects())
            retVal.add(e);

        for(Effect e: this.getEffects()) {
            for(Object p: e.usableInputs()) {
                for(Object possible: (List<Object>) p ) {
                    if (!(((List<Object>) possible).size() > 0)) {
                        if (retVal.contains(e))
                            retVal.remove(e);
                    }
                }
            }

        }

        Iterator<Effect> elementListIterator = retVal.iterator();
        while (elementListIterator.hasNext()) {
            Effect element = elementListIterator.next();

            if(!(new ChooseHowToPayState(element.getActions().get(0).getActionInfo().getActionContext().getPlayer(), element.getUsageCost())).canPayInSomeWay()) {
                elementListIterator.remove();
            }
        }
        return retVal;


    }



    public List<Effect> usable(Player player,Board board,PlayersList playersList) {
        List<List<Object>> cartesianMatrix;

        List<Effect> effectList = new ArrayList<>();                //effetti finali -- inizializzata vuota
        List<Effect> potentialEffects = this.effects;    //effetti iniziali -- inizializzata piena

        for(Effect e: potentialEffects) {       // controllo ogni effetto
            ActionContext context = new ActionContext();
            context.setBoard(board);
            context.setPlayerList(playersList);
            context.setPlayer(player);
            e.setContext(context);                         // costruisce un contesto fittizio
            System.out.println("start");
            for (EffectInfoType input : e.requestedInputs()) {
                if (input == EffectInfoType.player) {
                    boolean correct = true;


                    Object inputGrid[][] = new Object[10][10];
                    inputGrid[0][0] = (Object) e.getActions().get(0).getActionInfo().getActionContext().getPlayer();
                    e.handleInput(inputGrid);

                    for (Action a : e.getActions()) {
                        System.out.println("verifico la condizione di " + a.toString());
                        if (a.getActionInfo().preCondition() == false) {
                            correct = false;
                        }

                    }
                    if (correct) break;      // appena una buona la aggiunge senza reiterare per gli altri player

                    if (correct) {

                        effectList.add(e);

                    }
                }
            }
            return effectList;
        }
        return effectList;


    }

    // WeaponCard from File, polymorphic constructor

    public WeaponCard(String ID) throws FileNotFoundException, IOException,InstantiationException, Exception  {
            super(ID);
            this.effects = new ArrayList<>();
            this.reloadCost = new AmmoList();

            BufferedReader reader = new BufferedReader(new FileReader("src/main/Files/cards/weaponCards/card"+ID+".set"));
            String line = reader.readLine();
            while(line != null) {


                if(line.equals("NAME")) {
                    line = reader.readLine();
                    this.setName(line);
                    ///*@*/ System.out.println("il nome e'"+ line);
                }
                if(line.equals("RELOAD COST")) {
                    String A = reader.readLine();	// colore
                    String B = reader.readLine();	// quantità
                    ///*@*/ System.out.println("il costo di ricarica e' " + B + " di colore " + A );

                    if (A.equals("y"))
                        reloadCost.addAmmoCubesOfColor(yellow, Integer.parseInt(B));
                    if (A.equals("b"))
                        reloadCost.addAmmoCubesOfColor(blue, Integer.parseInt(B));
                    if (A.equals("r"))
                        reloadCost.addAmmoCubesOfColor(red, Integer.parseInt(B));

                }

                if(line.equals("NEW EFFECT")) {
                    ///*@*/ System.out.println("\tnuovo effetto corrente");
                    effects.add(new Effect());
                    effects.get(effects.size() - 1).setOf(this);
                }
                if(line.equals("EFFECT NAME")) {
                    line = reader.readLine();
                    effects.get(effects.size() - 1).setEffectName(line);
                }

                if(line.equals("USAGE COST")) {
                    String A = reader.readLine();	// colore
                    String B = reader.readLine();	// quantità
                    //System.out.println("il costo di ricarica e' " + B + " di colore " + A );
                    Effect currentEffect = effects.get(effects.size()-1);

                    if (A.equals("y"))
                        currentEffect.getUsageCost().addAmmoCubesOfColor(yellow, Integer.parseInt(B));
                    if (A.equals("b"))
                        currentEffect.getUsageCost().addAmmoCubesOfColor(blue, Integer.parseInt(B));
                    if (A.equals("r"))
                        currentEffect.getUsageCost().addAmmoCubesOfColor(red, Integer.parseInt(B));
                    //System.out.println("-");
                }

                if(line.equals("EXPECTED INPUT")) {

                    EffectInfo effectInfo = new EffectInfo();		// inizializza la lista degl input

                    line = reader.readLine();		// parametro
                    while(!line.equals("END")) {

                        ///*@*/ System.out.println("parametro input del parametro atteso:\t <" + line + ">");


                        effectInfo.getEffectInfoElement().add(new EffectInfoElement());
                        effectInfo.getEffectInfoElement().get(effectInfo.getEffectInfoElement().size() - 1).setEffectInfoTypelist(EffectInfoType.valueOf(line));
                        line = reader.readLine(); 	//parametro
                        if(line.equals("TO")) {     // a quale azione è destinato l'input
                            line = reader.readLine(); //parametro numerico
                            if(line.equals("ALL")) {
                                effectInfo.getEffectInfoElement().get(effectInfo.getEffectInfoElement().size() - 1).getEffectInfoTypeDestination().add(0);
                                line = reader.readLine();
                            }
                            while(!line.equals("END")) {
                                effectInfo.getEffectInfoElement().get(effectInfo.getEffectInfoElement().size() - 1).getEffectInfoTypeDestination().add(Integer.parseInt(line));
                                line = reader.readLine(); // parametro
                            }
                            line = reader.readLine();   // input successivo
                        }
                    }
                    effects.get(effects.size() - 1).setEffectInfo(effectInfo);					//setta gli input
                    line = reader.readLine();	//istruzione successiva
                }
                if(line.equals("ACTIONS")) {
                    line = reader.readLine();			// parametro azione
                    while(!line.equals("END")) {
                        ///*@*/ System.out.println("azione nell'evento corrente:" + line);
                        Class<?> Cref = Class.forName("it.polimi.se2019.model." + line);
                        Action demo = (Action) Cref.newInstance();

                        line = reader.readLine();		// parametro azione

                        ActionInfo actionInfo = new ActionInfo();		// carico action info vuota

                        if(line.equals("ACTION INFO")) {
                            ///*$*/System.out.println("//inizio action info");
                            line = reader.readLine();
                            while(!line.equals("END")) {
                                if(line.equals("SET")) {
                                    line = reader.readLine();	// parametro
                                    ///*@*/ System.out.println("info sull'azione corrente nell'evento corrente:" + line);
                                    actionInfo.getActionDetails().getFileSelectedActionDetails().addFileSettingData((Object) line);
                                }
                                if(line.equals("PRECONDITION")) {
                                    line = reader.readLine();	// parametro
                                    ///*@*/ System.out.println("precondizione sull'azione corrente nell'evento corrente:" + line);
                                    actionInfo.setPreConditionMethodName(line);
                                }
                                line = reader.readLine();
                            }
                            ///*$*/System.out.println("//fine action info");
                            line = reader.readLine();
                        }

                        demo.setActionInfo(actionInfo);
                        effects.get(effects.size() - 1).getActions().add(demo);


                    }

                }

                line = reader.readLine();

            }
            reader.close();
        }

    /***/
    private AmmoList reloadCost;

    /***/
    private boolean isLoaded;

    /***/
    public transient List<Effect> effects;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String description="";
    private String name="";

    /***/
    public AmmoList getPickUpCost() {
        AmmoList r = new AmmoList();
        int i = 0;
        int firstQuantity = reloadCost.getAmmoCubesList().get(0).getQuantity();
        if((firstQuantity - 1) == 0) {
            // eliminazione del primo cubo
            for(AmmoCubes c : reloadCost.getAmmoCubesList()) {
                if(i > 0)  {
                    r.addAmmoCubesOfColor(c.getColor(),c.getQuantity());
                }
                i++;
            }
        } else {
            // decrementazione del primo cubo
            for(AmmoCubes c : reloadCost.getAmmoCubesList()) {
                    r.addAmmoCubesOfColor(c.getColor(),c.getQuantity());
            }
            r.getAmmoCubesList().get(0).setQuantity( firstQuantity - 1);
        }

        return r;
    }

    /***/
    public AmmoList getReloadCost() {
        return reloadCost;
    }

    /***/
    public boolean isLoaded() {
        return isLoaded;
    }

    public void reload(){
        this.isLoaded = true;
    }

    public void unload(){
        this.isLoaded = false;
        //TODO should notify and tell to clients
    }

    /***/
    public List<Effect> getEffects() {
        return effects;
    }

    public void Play(int effectNumber) {
        getEffects().get(effectNumber).Exec();
    }

    public WeaponCardV buildWeapondCardV(){
        WeaponCardV weaponCardV= new WeaponCardV();
        if(this.name != null) {
            weaponCardV.setName(this.name);
        }
        weaponCardV.setLoaded(this.isLoaded);
        if(this.description!=null) {
            weaponCardV.setDescription(this.description);
        }
        if(this.reloadCost!= null) {
            weaponCardV.setReloadCost(this.getReloadCost().buildAmmoListV());
        }
        if (this.getPickUpCost() != null) {
            weaponCardV.setPickUpCost(this.getPickUpCost().buildAmmoListV());
        }
        weaponCardV.setID(this.getID());
        if(this.effects!=null){
            List<EffectV> effectV = new ArrayList<>();
            for (Effect e : this.effects) {
                effectV.add(e.buildEffectV());
            }
            weaponCardV.setEffectVList(effectV);
        }

        return weaponCardV;
    }
}