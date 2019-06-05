package it.polimi.se2019.model;


import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.view.components.PowerUpCardV;

import java.io.*;

import static it.polimi.se2019.model.enumerations.AmmoCubesColor.yellow;

/***/
public class PowerUpCard extends Card implements Serializable {

    /***/
    public PowerUpCard(String ID, AmmoCubesColor color, Effect specialEffect) {
        super(ID);
        this.color = color;
        this.specialEffect = specialEffect;
    }

    public PowerUpCard(){
        super("fake");
        this.color = yellow;
        this.specialEffect = null;
    }



    public PowerUpCard(String ID) throws FileNotFoundException, IOException,InstantiationException, Exception  {
        super(ID);

        BufferedReader reader = new BufferedReader(new FileReader("src/main/Files/cards/powerUpCards/card"+ID+".set"));
        String line = reader.readLine();
        while(line != null) {


            if(line.equals("NAME")) {
                line = reader.readLine();
                ///*@*/ System.out.println("il nome e'"+ line);
            }

            if(line.equals("NEW EFFECT")) {
                ///*@*/ System.out.println("\tnuovo effetto corrente");
                specialEffect = new Effect();
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
                specialEffect.setEffectInfo(effectInfo);				//setta gli input
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
                    specialEffect.getActions().add(demo);


                }

            }

            line = reader.readLine();

        }
        reader.close();
    }


    /***/
    private AmmoCubesColor color;

    /***/
    private Effect specialEffect;

    /***/
    public AmmoCubesColor getColor() {
        return color;
    }

    /***/
    public Effect getSpecialEffect() {
        return specialEffect;
    }
    private String name="";
    private String description="";

    public void Play() {
        getSpecialEffect().Exec();

    }

    public PowerUpCardV buildPowerUpCardV(){
        PowerUpCardV powerUpCardV= new PowerUpCardV();
        powerUpCardV.setName(this.name);
        powerUpCardV.setColor(this.color);
        powerUpCardV.setDescription(description);
        powerUpCardV.setID(this.getID());


        return powerUpCardV;
    }
}
