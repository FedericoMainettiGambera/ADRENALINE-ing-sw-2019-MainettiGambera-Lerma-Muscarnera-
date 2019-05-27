package it.polimi.se2019.model;


import it.polimi.se2019.model.enumerations.EffectInfoType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.se2019.model.enumerations.AmmoCubesColor.*;


/***/
public class WeaponCard extends Card implements Serializable {

    /***/

   public WeaponCard(String ID, AmmoList pickUpCost, AmmoList reloadCost, List<Effect> effects) {
        super(ID);
        this.isLoaded = true;
        this.effects = effects;
        this.pickUpCost = pickUpCost;
        this.reloadCost = reloadCost;
    }
    /***/
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
                    ///*@*/ System.out.println("il nome e'"+ line);
                }
                if(line.equals("RELOAD COST")) {
                    String A = reader.readLine();	// colore
                    String B = reader.readLine();	// quantità
                    ///*@*/ System.out.println("il costo di ricarica e' " + B + " di colore " + A );
                    this.reloadCost = new AmmoList();
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
                }
                if(line.equals("EXPECTED INPUT")) {

                    EffectInfo effectInfo = new EffectInfo();		// inizializza la lista degl input

                    line = reader.readLine();		// parametro
                    while(!line.equals("END")) {

                        ///*@*/ System.out.println("parametro input del parametro atteso:\t <" + line + ">");
                        effectInfo.getEffectInfoTypelist().add(EffectInfoType.valueOf(line));	//aggiunge il tipo di input
                        line = reader.readLine(); 	//parametro
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
    private AmmoList pickUpCost;

    /***/
    private AmmoList reloadCost;

    /***/
    private boolean isLoaded;

    /***/
    public List<Effect> effects;

    /***/
    public AmmoList getPickUpCost() {
        AmmoList r = new AmmoList();
        int i = 0;
        for(AmmoCubes c : reloadCost.getAmmoCubesList()) {
            if(i == 0)  {
                r.addAmmoCubesOfColor(c.getColor(),c.getQuantity());
            }
            i++;

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
    }

    /***/
    public List<Effect> getEffects() {
        return effects;
    }

    public void Play(int effectNumber) {
        getEffects().get(effectNumber).Exec();
    }
}