package it.polimi.se2019.networkHandler.RMI;

import it.polimi.se2019.model.events.Event;
import it.polimi.se2019.view.components.View;

import java.util.Observable;

public class RMIObsHandler extends Observable {

    public RMIObsHandler(View view){
        this.addObserver(view);
    }


   public void  notify(Event E){

        System.out.println("I'MHEREEEEE" + E.getClass());
       this.setChanged();
       this.notifyObservers(E);

   }
}
