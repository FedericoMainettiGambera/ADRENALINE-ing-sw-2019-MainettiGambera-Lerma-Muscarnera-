package it.polimi.se2019.view.components;
/**a gate to access the equivalent class of the model in view
 *
 * @author FedericoMainettiGambera
 * @author LudoLerma
 * */
public class ViewModelGate {
    /**the model for the view*/
    private static GameV model;
    /**nickname the player whose using this view*/
    private static String me;
    /**
     * @param me to set me*/
    public static void setMe(String me) {
        ViewModelGate.me = me;
    }
    /**@return me*/
    public static String getMe() {
        return me;
    }
    /**@return model*/
    public static GameV getModel() {
        return model;
    }
    /**@param model set the model attribute*/
    public static void setModel(GameV model) {
        ViewModelGate.model = model;
    }
}
