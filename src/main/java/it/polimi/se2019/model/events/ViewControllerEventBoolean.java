package it.polimi.se2019.model.events;

public class ViewControllerEventBoolean extends ViewControllerEvent {
    private boolean input;

    public ViewControllerEventBoolean(boolean input){
        super();
        this.input = input;
    }

    public boolean getInput(){
        return this.input;
    }
}
