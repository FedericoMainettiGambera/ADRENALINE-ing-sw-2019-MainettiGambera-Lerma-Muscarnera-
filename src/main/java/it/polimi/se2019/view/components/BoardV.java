package it.polimi.se2019.view.components;

import it.polimi.se2019.model.enumerations.CardinalPoint;

import java.io.FileInputStream;
import java.io.IOException;
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
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < this.map.length; i++) {
            for (int j = 0; j < this.map[0].length; j++) {

                if (this.map[i][j] == null) {
                    s.append("----------------------------------");
                } else {
                    s.append(this.map[i][j].getSquareType());
                    s.append(" ").append(this.map[i][j].getColor());
                    s.append(" [").append(i).append("][").append(j).append("]");
                    s.append(" n:").append(this.map[i][j].getSide(CardinalPoint.north));
                    s.append(" e:").append(this.map[i][j].getSide(CardinalPoint.east));
                    s.append(" s:").append(this.map[i][j].getSide(CardinalPoint.south));
                    s.append(" w:").append(this.map[i][j].getSide(CardinalPoint.west));
                }
                s.append("      ");
            }
            s.append("\n\n");
        }
        return s.toString();
    }

    public Image GUIchosenMap(String chosenMap) throws IOException{
        Image mapImage=null;
        switch (chosenMap) {
            case "map2":
                mapImage = new Image(new FileInputStream("src/main/Files/Images/Map/map2.png"));
                break;
            case "map0":
                mapImage = new Image(new FileInputStream("src/main/Files/Images/Map/Map0.png"));
                break;
            case "map1":
                mapImage = new Image(new FileInputStream("src/main/Files/Images/Map/map1.png"));
                break;
            case "map3":
                mapImage = new Image(new FileInputStream("src/main/Files/Images/Map/map3.png"));
                break;
            default:
                    //nothing

        }

        return mapImage;

    }


}
