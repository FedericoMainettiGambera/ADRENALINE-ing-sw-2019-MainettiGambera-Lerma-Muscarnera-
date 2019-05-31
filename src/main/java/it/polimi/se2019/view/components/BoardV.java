package it.polimi.se2019.view.components;

import it.polimi.se2019.model.enumerations.CardinalPoint;

import java.io.Serializable;

public class BoardV implements Serializable {
    private SquareV[][] map;

    public void setMap(SquareV[][] map) {
        this.map = map;
    }

    public SquareV[][] getMap() {
        return map;
    }

    @Override
    public String toString(){
        String s = "";
        for (int i = 0; i < this.map.length; i++) {
            for (int j = 0; j < this.map[0].length; j++) {

                if(this.map[i][j] == null){
                    s+="----------------------------------";
                }
                else {
                    s += this.map[i][j].getSquareType();
                    s += " " + this.map[i][j].getColor();
                    s += " [" + i + "][" + j + "]";
                    s += " n:" + this.map[i][j].getSide(CardinalPoint.north);
                    s += " e:" + this.map[i][j].getSide(CardinalPoint.east);
                    s += " s:" + this.map[i][j].getSide(CardinalPoint.south);
                    s += " w:" + this.map[i][j].getSide(CardinalPoint.west);
                }
                s+= "      ";
            }
            s+="\n\n";
        }
        return s;
    }
}
