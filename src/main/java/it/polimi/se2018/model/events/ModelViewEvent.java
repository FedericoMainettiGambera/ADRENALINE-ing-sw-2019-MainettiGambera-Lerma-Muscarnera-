package it.polimi.se2018.model.events;

import it.polimi.se2018.model.enumerations.ModelViewEventType;

/**this class holds all the information needed to update the View when the Model changes*/
public class ModelViewEvent extends Event {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor
     * */
    public ModelViewEvent(ModelViewEventType component){
        this.component = component;
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /**contains information about wich component of the model generated the Event*/
    private ModelViewEventType component;

    /*-********************************************************************************************************METHODS*/

    /**@return
     */
    public ModelViewEventType getComponent() {
        return component;
    }

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
