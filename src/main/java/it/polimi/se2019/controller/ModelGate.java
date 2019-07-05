package it.polimi.se2019.controller;

import it.polimi.se2019.model.Game;

/**a gateway for the model
 *
 * /**starts the server
 *  * @author LudoLerma
 *  * @author FedericoMainettiGambera
 *  * */
public class ModelGate {

    private ModelGate(){

        //
    }

   /**a reference to the game*/
    private static Game model = new Game();
    /**@return  model, return the reference*/
    public static Game getModel() {
        return model;
    }
    /**@param  model, set the model*/
    public static void setModel(Game model) {
        ModelGate.model = model;
    }
}