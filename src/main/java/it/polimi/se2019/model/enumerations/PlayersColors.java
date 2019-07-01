package it.polimi.se2019.model.enumerations;


import java.util.HashMap;
import java.util.Map;

/***/
public enum PlayersColors {
    blue(0),
    yellow(1),
    gray(2),
    green(3),
    purple(4);

    private int value;
    private static Map map = new HashMap<>();

    private PlayersColors(int value) {
        this.value = value;
    }

    static {
        for (PlayersColors playersColors : PlayersColors.values()) {
            map.put(playersColors.value, playersColors);
        }
    }

    public static PlayersColors valueOf(int playersColors) {
        return (PlayersColors) map.get(playersColors);
    }

    public int getValue() {
        return value;
    }
}