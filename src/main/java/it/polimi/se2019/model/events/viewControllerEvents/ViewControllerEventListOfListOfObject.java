package it.polimi.se2019.model.events.viewControllerEvents;

import java.util.List;
/**@deprecated */
public class ViewControllerEventListOfListOfObject extends ViewControllerEvent {
    private List<List<Object>> listOfString;
    public ViewControllerEventListOfListOfObject(List<List<Object>> listOfString){
        this.listOfString = listOfString;
    }
    public List<List<Object>> getListOfObject() {
        return listOfString;
    }
}
