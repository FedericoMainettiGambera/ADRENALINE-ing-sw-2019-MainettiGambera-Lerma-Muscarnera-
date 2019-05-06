package it.polimi.se2018.model.events;

import it.polimi.se2018.model.Position;

public class ViewControllerEventPosition extends ViewControllerEvent {
    private Position position;

    public ViewControllerEventPosition(Position position){
        this.position = position;
    }

    public Position getPosition(){
        return this.position;
    }
}
