package it.polimi.se2019.model.events.viewControllerEvents;

import java.util.List;

public class ViewControllerEventPaymentInformation extends ViewControllerEvent {
    private List<Object> answer;
    public ViewControllerEventPaymentInformation(List<Object> answer){
        this.answer = answer;
    }
    public List<Object> getAnswer() {
        return answer;
    }
}
