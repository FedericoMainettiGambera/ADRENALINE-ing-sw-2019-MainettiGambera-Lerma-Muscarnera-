package it.polimi.se2019.model;

public class ActionContextFilteredInput {
    public Object[] getContent() {
        return content;
    }

    public Object getType() {
        return type;
    }

    private Object[] content;

    public void setContent(Object[] content) {
        this.content = content;
    }

    public void setType(Object type) {
        this.type = type;
    }

    private Object type;
    public ActionContextFilteredInput() {
        content = new Object[100];
        type    = new Object();
    }
    public ActionContextFilteredInput(Object[] x,Object y) {
        content = x;
        type = y;
    }
}
