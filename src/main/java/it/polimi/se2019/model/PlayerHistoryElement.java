package it.polimi.se2019.model;

public class PlayerHistoryElement {
    Card   contextCard;

    public Card getContextCard() {
        return contextCard;
    }

    public void setContextCard(Card contextCard) {
        this.contextCard = contextCard;
    }

    public Effect getContextEffect() {
        return contextEffect;
    }

    public void setContextEffect(Effect contextEffect) {
        this.contextEffect = contextEffect;
    }

    public Object getInput() {
        return input;
    }

    public void setInput(Object input) {
        this.input = input;
    }

    Effect contextEffect;
    Object input;
}
