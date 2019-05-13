package it.polimi.se2019.model.events;

public class ViewControllerEventInt extends ViewControllerEvent {
    private int input;

    public ViewControllerEventInt(int Input){
        super();
        this.input = input;
    }

    public int getInput(){
        return this.input;
    }
}
