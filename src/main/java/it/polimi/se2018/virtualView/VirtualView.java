package it.polimi.se2018.virtualView;

import java.util.Observable;
import java.util.Observer;

/**
 * E' observer del model (inoltra ModelViewEvent)
 * E' observable dal controller (riceve ViewControllerEvent)
 */
public class VirtualView implements Observer {

    @Override
    public void update(Observable o, Object arg) {

    }
}
