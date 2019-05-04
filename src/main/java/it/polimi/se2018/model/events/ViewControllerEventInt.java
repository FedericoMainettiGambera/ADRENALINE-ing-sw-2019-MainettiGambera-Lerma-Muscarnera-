package it.polimi.se2018.model.events;

public class ViewControllerEventInt extends ViewControllerEvent {
    private int input;

    public void ViewControllerEventInt(int Input){
        this.input = input;
    }

    public int getInput(){
        return this.input;
    }
}
