package it.polimi.se2018.model;

import it.polimi.se2018.model.enumerations.PlayersColors;


/***/
public abstract class Person {

    /***/
    public Person(String nickname, PlayersColors color, PlayerBoard board){
        this.nickname=nickname;
        this.color=color;
        this.board=board;

        this.score=0;
        this.position=null;
    }

    /***/
    private String nickname;

    /***/
    private PlayersColors color;

    /***/
    private int score;

    /***/
    private Position position;

    /***/
    private PlayerBoard board;

    /***/
    public void setPosition(int x, int y){
        this.position=new Position(x,y);
        return;
    }

    /***/
    public void AddPoints(int points) {
        this.score+=points;
        return;
    }

    /***/
    public String getNickname(){
      return nickname;
    }

    /***/
    public PlayersColors getColor(){
        return color;
    }

    /***/
    public int getScore(){
        return score;
    }

    /***/
    public PlayerBoard getBoard() {
        return board;
    }

    /***/
    public Position getPosition() {
        return position;
    }

}
