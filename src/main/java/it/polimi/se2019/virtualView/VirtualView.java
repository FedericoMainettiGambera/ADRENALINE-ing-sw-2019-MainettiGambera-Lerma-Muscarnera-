package it.polimi.se2019.virtualView;

import java.util.Observable;
import java.util.Observer;
/**superclass of server socket and rmi server to make them implements observer */
public class VirtualView implements Observer {

    @Override
    public void update(Observable o, Object arg){
    //must be empty
    }
}
