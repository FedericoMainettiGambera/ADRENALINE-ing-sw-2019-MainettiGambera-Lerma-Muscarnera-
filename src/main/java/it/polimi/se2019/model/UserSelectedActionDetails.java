package it.polimi.se2019.model;

import it.polimi.se2019.model.enumerations.CardinalPoint;

import java.io.Serializable;

public class UserSelectedActionDetails implements Serializable {
    public void itNeeds(Object ... neededObjects) {           // notify the model/view that these fields need to be filled by the user
        boolean isOk = true;
        for(Object n: neededObjects ) {
            if(n == null) {
                // notify system
                isOk = false;
            }
            if(!isOk) {

                itNeeds(neededObjects);         //TODO: it stops the execution of the program while all the fields are fillen... to improve  ; COMUNICATION WITH THE USER

            }
        }

    }
    public UserSelectedActionDetails() {

    }
    public CardinalPoint getDirection() {
        return direction;
    }
    public void setDirection(CardinalPoint direction) {
        this.direction = direction;
    }
    private CardinalPoint direction;
    public Square getChosenSquare() {
        return chosenSquare;
    }


    public int getGenericQuantity() {
        return genericQuantity;
    }

    public void setGenericQuantity(int genericQuantity) {
        this.genericQuantity = genericQuantity;
    }

    private int genericQuantity;
    private Square chosenSquare;                // selected square


    public Position getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(Position newPosition) {
        this.newPosition = newPosition;
    }

    private Position newPosition;
    public void setChosenSquare(Square chosenSquare) {
        this.chosenSquare = chosenSquare;
    }
    public Player getTarget() {
        return target;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    private Player target;

}