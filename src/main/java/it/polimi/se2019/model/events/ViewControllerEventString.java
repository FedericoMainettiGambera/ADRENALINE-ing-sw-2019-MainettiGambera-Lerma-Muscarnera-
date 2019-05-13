package it.polimi.se2019.model.events;

public class ViewControllerEventString extends ViewControllerEvent {

    private String input;

    public ViewControllerEventString(String input){
        super();
        this.input = input;
    }

    public String getInput() {
        return input;
    }
}
