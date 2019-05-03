package it.polimi.se2018.model;


import it.polimi.se2018.model.enumerations.AmmoCubesColor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.se2018.model.enumerations.AmmoCubesColor.*;


/***/
public class WeaponCard extends Card {

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

    public WeaponCard(String ID) throws FileNotFoundException,IOException,InstantiationException {
        /***
         * It Allows To Load a card from file. In the final project there will be a folder
         * containg a series of "Card%number%.set" where %number% is the ID of the card
         * described by the file.
         *
         * The parser is quite simple.
         * Every Line contains info about the content of the next lines or is informative content per se.
         * It also uses the "Class" class to determinate Classes of actions.
         *     Example Content: "cards17.set"  ''' the code is indentated only to be more readable,the final file is not.
         *
         *         PICK UP COST
         *          r
         *          3
         *         PICK UP COST
         *          y
         *          1
         *         RELOAD COST
         *          b
         *          5
         *         NEW EFFECT
         *           ACTION
         *               %X%
         *                  ACTION INFO
         *                          PRECONDITION
         *                              ThereAreNoWallsBetweenPlayerAndTarget
         *                          DAMAGE
         *                              3
         *                          SQUARE MOVEMENT
         *                              1
         *                  END
         *              %Y%
         *              %Z%
         *         ...
         *         END
         *         NEW EFFECT
         *          ACTION
         *              %Z%
         *          END
         *
         * This File describes a card that needs 3 red cubes and 1 yellow cubes to be picked up,5 blue cubes  to reload
         * and has 2 effects: The first executes the atomic actions %X% and %Y%, the second executes the atomic action
         * %Z%
         * */

        super(ID);

            effects = new ArrayList<Effect>();
            this.pickUpCost = new AmmoList();
            this.reloadCost = new AmmoList();
            BufferedReader reader = new BufferedReader(new FileReader("Card" + ID + ".set"));
            try {
                String line;
                line = reader.readLine();

                while (line != null) {
                    if (line.equals("PICK UP COST")) {
                        line = reader.readLine();
                        String secondLine = reader.readLine();

                        if (line.equals("y"))
                            pickUpCost.addAmmoCubesOfColor(yellow, Integer.parseInt(secondLine));
                        if (line.equals("b"))
                            pickUpCost.addAmmoCubesOfColor(blue, Integer.parseInt(secondLine));
                        if (line.equals("r"))
                            pickUpCost.addAmmoCubesOfColor(red, Integer.parseInt(secondLine));
                    }

                    if (line.equals("RELOAD COST")) {
                        line = reader.readLine();
                        String secondLine = reader.readLine();
                        this.reloadCost = new AmmoList();
                        if (line.equals("y"))
                            reloadCost.addAmmoCubesOfColor(yellow, Integer.parseInt(secondLine));
                        if (line.equals("b"))
                            reloadCost.addAmmoCubesOfColor(blue, Integer.parseInt(secondLine));
                        if (line.equals("r"))
                            reloadCost.addAmmoCubesOfColor(red, Integer.parseInt(secondLine));
                    }

                    if (line.equals("NEW EFFECT"))
                        effects.add(new Effect());

                    if (line.equals("ACTIONS")) {
                        line = reader.readLine();                                    // seek the File Cursor to the next Line
                        while (!line.equals("END")) {
                            try {
                                Class<?> Cref = Class.forName(line);
                                Action demo = (Action) Cref.newInstance();

                                /* Action info */
                                line = reader.readLine();                                    //
                                ActionInfo actionInfo = new ActionInfo();

                                if(line.equals("ACTION INFO")) {

                                    line = reader.readLine();
                                    while(!line.equals("END")) {
                                        if(line.equals("PRECONDITION"))
                                        {

                                            line = reader.readLine();                                       // set PreCondition Method
                                            actionInfo.setPreConditionMethodName(line);

                                        }
                                        if(line.equals("DAMAGE"))
                                        {
                                            line = reader.readLine();                                        // changes Damage
                                            actionInfo.getActionDetails().setDamage(Integer.parseInt(line));

                                        }
                                        if(line.equals("SQUARE MOVEMENT"))
                                        {
                                            line = reader.readLine();                                        // changes Damage
                                            actionInfo.getActionDetails().setSquareMovement(Integer.parseInt(line));

                                        }
                                        line = reader.readLine();
                                    }
                                    line = reader.readLine();

                                } else {

                                    /*nothing*/

                                }
                                //System.out.println(effect.size());
                                demo.setActionInfo(actionInfo);
                                effects.get(effects.size() - 1).getActions().add(demo);
                            } catch (Exception e) {

                                System.out.println("errore" + e.toString());
                            }



                        }

                    }

                    line = reader.readLine();
                }
                reader.close();
            } catch( IOException E) {
                /**/
            } finally {
                reader.close();
            }
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
        return pickUpCost;
    }

    /***/
    public AmmoList getReloadCost() {
        return reloadCost;
    }

    /***/
    public boolean isLoaded() {
        return isLoaded;
    }

    /***/
    public List<Effect> getEffects() {
        return effects;
    }
}