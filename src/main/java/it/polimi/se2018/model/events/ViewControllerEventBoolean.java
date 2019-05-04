package it.polimi.se2018.model.events;

public class ViewControllerEventBoolean extends ViewControllerEvent {
    private boolean input;

    public void ViewControllerEventBoolean(boolean input){
        this.input = input;
    }

    public boolean getInput(){
        return this.input;
    }
}
