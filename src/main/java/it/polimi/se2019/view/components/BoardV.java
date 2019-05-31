package it.polimi.se2019.view.components;

import it.polimi.se2019.model.enumerations.CardinalPoint;

import java.io.FileInputStream;
import java.io.Serializable;
import javafx.scene.image.Image;

public class BoardV implements Serializable {
    private SquareV[][] map;
    private String chosenMap;

    public String getChosenMap() {
        return chosenMap;
    }

    public void setChosenMap(String chosenMap) {
        this.chosenMap = chosenMap;
    }

    public void setMap(SquareV[][] map) {
        this.map = map;
    }

    public SquareV[][] getMap() {
        return map;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < this.map.length; i++) {
            for (int j = 0; j < this.map[0].length; j++) {

                if (this.map[i][j] == null) {
                    s += "----------------------------------";
                } else {
                    s += this.map[i][j].getSquareType();
                    s += " " + this.map[i][j].getColor();
                    s += " [" + i + "][" + j + "]";
                    s += " n:" + this.map[i][j].getSide(CardinalPoint.north);
                    s += " e:" + this.map[i][j].getSide(CardinalPoint.east);
                    s += " s:" + this.map[i][j].getSide(CardinalPoint.south);
                    s += " w:" + this.map[i][j].getSide(CardinalPoint.west);
                }
                s += "      ";
            }
            s += "\n\n";
        }
        return s;
    }

    public Image GUIchosenMap() throws Exception {
        Image map=null;
        switch (chosenMap) {
            case "map2":
                map = new Image(new FileInputStream("src/main/Files/Images/Map/map2.png"));
            case "map0":
                map = new Image(new FileInputStream("src/main/Files/Images/Map/Map0.png"));
            case "map1":
                map = new Image(new FileInputStream("src/main/Files/Images/Map/map1.png"));
            case "map3":
                map = new Image(new FileInputStream("src/main/Files/Images/Map/map3.png"));
            default:
                    //nothing

        }

        return map;

    }


}
