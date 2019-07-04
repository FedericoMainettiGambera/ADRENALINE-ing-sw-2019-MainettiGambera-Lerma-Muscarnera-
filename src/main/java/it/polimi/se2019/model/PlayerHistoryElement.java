package it.polimi.se2019.model;

public class PlayerHistoryElement {
    Card   contextCard;

    public int getBlockId() {
        return blockId;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }

    public int getInputId() {
        return inputId;
    }

    public void setInputId(int inputId) {
        this.inputId = inputId;
    }


    public int getTurnId() {
        return turnId;
    }

    public void setTurnId(int turnId) {
        this.turnId = turnId;
    }

    private int turnId;
    private int blockId;
    private int inputId;


    public void show() {
        if( this.getContextCard() != null &&
            this.getContextEffect() != null )
        System.out.println((
                (WeaponCard) this.getContextCard()).getName() +
                "\t\t" +
                this.getContextEffect().getName() +
                "\t\t" +
                this.getInput() +
                "\t\t\t" +
                this.getBlockId() +
                "\t" + this.getInputId() +
                "\t" + this.getTurnId()
        );
                        //   nome carta                                            // nome effetto                             // input row             // BI                       // II
    }


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
