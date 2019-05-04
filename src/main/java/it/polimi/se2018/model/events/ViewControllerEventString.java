package it.polimi.se2018.model.events;

public class ViewControllerEventString extends ViewControllerEvent {

    private String input;

    public void ViewControllerEventString(String input){
        this.input = input;
    }

    public String getInput() {
        return input;
    }
}
