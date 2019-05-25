package it.polimi.se2019.model.events.viewControllerEvents;

public class ViewControllerEventTwoString extends ViewControllerEvent {
    private String input1;

    private String input2;

    public ViewControllerEventTwoString(String input1, String input2){
        super();
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
