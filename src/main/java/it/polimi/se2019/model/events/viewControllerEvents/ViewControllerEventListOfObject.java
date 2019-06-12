package it.polimi.se2019.model.events.viewControllerEvents;

import java.util.List;

public class ViewControllerEventListOfObject extends ViewControllerEvent {
    private List<Object> answer;
    public ViewControllerEventListOfObject(List<Object> answer){
        this.answer = answer;
    }
    public List<Object> getAnswer() {
        return answer;
    }
}
