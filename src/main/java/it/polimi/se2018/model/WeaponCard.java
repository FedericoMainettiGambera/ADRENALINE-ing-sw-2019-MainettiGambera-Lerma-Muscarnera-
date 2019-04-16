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
         *     Example Content: "cards17.set"
         *1
         *2          PICK UP COST
         *3          r
         *4          3
         *5          PICK UP COST
         *6          y
         *7          1
         *8          RELOAD COST
         *9          b
         *10         5
         *11         NEW EFFECT
         *12         ACTION
         *13         %X%
         *14         %Y%
         *15         ...
         *16         END
         *17         NEW EFFECT
         *18         ACTION
         *19         %Z%
         *20         END
         *
         * This File describes a card that needs 3 red cubes and 1 yellow cubes to be picked up,5 blue cubes  to reload
         * and hass 2 effects: The first executes the atomic actions %X% and %Y%, the second executes the atomic action
         * %Z%
         * */

        super(ID);

            effects = new ArrayList<Effect>();
            this.pickUpCost = new AmmoList();
            this.reloadCost = new AmmoList();

            BufferedReader reader = new BufferedReader(new FileReader("Card" + ID + ".set"));
            String line;
            line = reader.readLine();

            while (line != null) {
                    if (line.equals("PICK UP COST")) {
                        line = reader.readLine();
                        String secondLine = reader.readLine();

                            if(line.equals("y"))
                                pickUpCost.addAmmoCubesOfColor(yellow,Integer.parseInt(secondLine));
                            if(line.equals("b"))
                                pickUpCost.addAmmoCubesOfColor(blue,Integer.parseInt(secondLine));
                            if(line.equals("r"))
                                pickUpCost.addAmmoCubesOfColor(red,Integer.parseInt(secondLine));
                    }

                    if (line.equals("RELOAD COST")) {
                        line = reader.readLine();
                        String secondLine = reader.readLine();
                        this.reloadCost = new AmmoList();
                        if(line.equals("y"))
                            reloadCost.addAmmoCubesOfColor(yellow,Integer.parseInt(secondLine));
                        if(line.equals("b"))
                            reloadCost.addAmmoCubesOfColor(blue,Integer.parseInt(secondLine));
                        if(line.equals("r"))
                            reloadCost.addAmmoCubesOfColor(red,Integer.parseInt(secondLine));
                    }

                    if (line.equals("NEW EFFECT"))
                        effects.add(new Effect());

                if (line.equals("ACTIONS")) {
                    line = reader.readLine();                                    // seek the File Cursor to the next Line
                    while (!line.equals("END")) {

                        //System.out.println("AZIONE: " + line);
                        try {
                            Class<?> Cref = Class.forName(line);
                            Action demo = (Action) Cref.newInstance();
                            //System.out.println(effect.size());
                            effects.get(effects.size() - 1).getActions().add(demo);
                        } catch (Exception e) {

                            System.out.println("errore" + e.toString());
                        }

                        line = reader.readLine();                                    //


                    }

                }

                line = reader.readLine();
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