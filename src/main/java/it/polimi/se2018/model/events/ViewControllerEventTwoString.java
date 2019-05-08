package it.polimi.se2018.model.events;

public class ViewControllerEventTwoString extends ViewControllerEvent {
    private String input1;

    private  String input2;

    public void ViewControllerEventString(String input1, String input2){
        this.input1 = input1;
        this.input2 = input2;

    }

    public String getInput1() {
        return input1;
    }

    public String getInput2() {
        return input2;
    }
}
