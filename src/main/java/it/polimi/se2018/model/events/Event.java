package it.polimi.se2018.model.events;

/**this class is extended in all the other Events*/
public abstract class Event {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor
     * */
    public Event(){
    }

    /*-*****************************************************************************************************ATTRIBUTES*/

    /*-********************************************************************************************************METHODS*/

    /**@return all the information that contains but in a String version
     */
    public String stringify(){
        return new String("Class not complete. Need to override method toString().");
    }

    /**builds the event from a String ( inverse of toString())
     * */
    public void parse(String informations){
    }
}
