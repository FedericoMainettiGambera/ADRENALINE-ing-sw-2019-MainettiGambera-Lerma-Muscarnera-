package it.polimi.se2019.model;


import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.view.components.PowerUpCardV;

import java.io.*;

import static it.polimi.se2019.model.enumerations.AmmoCubesColor.*;

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


    public PowerUpCard(String ID) throws FileNotFoundException, IOException ,InstantiationException {

        /**
         *  It Allows to load a PowerUpCard from file, in the same way of weaponcards
         *  every file has the following structure
         *  "card18.set"
         *  1   COLOR
         *  2   y
         *  3   ACTIONS
         *  4   %x%
         *  5   %y%
         *  6   %z%
         *  7   %END%
         *
         *  This File describes a Card that is yellow and has atomic actions %x%,%y% and %z% as special effect.
         *
         * */
        super(ID);
        this.specialEffect = new Effect();


        BufferedReader reader = new BufferedReader(new FileReader("src/main/Files/cards/powerUpCards/card" + ID + ".set"));
        try {
            String line;
            line = reader.readLine();

            while (line != null) {

                if (line.equals("COLOR")) {
                    line = reader.readLine();
                    if (line.equals("y"))
                        this.color = yellow;
                    if (line.equals("r"))
                        this.color = red;
                    if (line.equals("b"))
                        this.color = blue;
                }
                if (line.equals("ACTIONS")) {
                    line = reader.readLine();                                    // seek the File Cursor to the next Line
                    while (!line.equals("END")) {

                        //System.out.println("AZIONE: " + line);
                        try {
                            Class<?> Cref = Class.forName(line);
                            Action demo = (Action) Cref.newInstance();
                            //System.out.println(effect.size());


                            line = reader.readLine();                                    //
                            ActionInfo actionInfo = new ActionInfo();
                            boolean useDefaultPreCondition = true;

                            if(line.equals("ACTION INFO")) {

                                line = reader.readLine();
                                while(!line.equals("END")) {
                                    if(line.equals("PRECONDITION"))
                                    {
                                        useDefaultPreCondition = false;
                                        line = reader.readLine();                                       // set PreCondition Method
                                        actionInfo.setPreConditionMethodName(line);

                                    }
                                    if(line.equals("SET"))
                                    {
                                        line = reader.readLine();                                        // changes Damage
                                        actionInfo.getActionDetails().getFileSelectedActionDetails().addFileSettingData((Object) line);
                                    }
                                    line = reader.readLine();
                                }

                                line = reader.readLine();

                            } else {

                                /*nothing*/

                            }
                            //System.out.println(effect.size());
                            if(useDefaultPreCondition) {
                                actionInfo.setPreConditionMethodName (demo.getActionInfo().getPreConditionMethodName()); /* # */
                            }
                            demo.updateSettingsFromFile();
                            demo.setActionInfo(actionInfo);



                            specialEffect.getActions().add(demo);
                        } catch (Exception e) {

                            System.out.println("errore" + e.toString());
                        }

                        line = reader.readLine();                                    //


                    }

                }
                line = reader.readLine();
            }
        } catch(IOException E) {

        } finally {
            reader.close();
        }
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
