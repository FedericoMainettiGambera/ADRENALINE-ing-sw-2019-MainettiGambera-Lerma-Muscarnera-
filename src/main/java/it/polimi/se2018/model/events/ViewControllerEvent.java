package it.polimi.se2018.model.events;

/**this class holds all the user-input information*/
public class ViewControllerEvent extends Event {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor
     * */
    public ViewControllerEvent(){
    }

    /*-*****************************************************************************************************ATTRIBUTES*/

    /*-********************************************************************************************************METHODS*/
    /**@return all the information that contains but in a String version
     */
    @Override
    public String stringify(){
        return super.toString();
    }

    /**builds the event from a String ( inverse of toString())
     * */
    @Override
    public void parse(String informations){
        super.parse(informations);
    }
}
