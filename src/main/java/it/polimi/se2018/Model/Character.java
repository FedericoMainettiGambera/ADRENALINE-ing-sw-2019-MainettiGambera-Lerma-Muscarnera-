package Model;

import java.util.*;

/**
 * 
 */
public abstract class Character {

    /**
     * Default constructor
     */
    public Character() {
    }

    /**
     * 
     */
    private String nickname;

    /**
     * 
     */
    private PlayersColors color;

    /**
     * 
     */
    private int score;

    /**
     * 
     */
    private Position position;

    /**
     * 
     */
    private PlayerBoard Board;















    /**
     * @return
     */
    public String getNickname() {
        // TODO implement here
        return "";
    }

    /**
     * @return
     */
    public PlayersColors getColor() {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    public void AddPoints() {
        // TODO implement here
    }

    /**
     * @return
     */
    public int getScore() {
        // TODO implement here
        return 0;
    }

    /**
     * @return
     */
    public Position getPosition() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Board getBoard() {
        // TODO implement here
        return null;
    }

}