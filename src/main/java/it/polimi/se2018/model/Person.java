package it.polimi.se2018.model;

import it.polimi.se2018.model.enumerations.AmmoCubesColor;
import it.polimi.se2018.model.enumerations.PlayersColors;


/***/
public abstract class Person {

    /***/
    public Person(String nickname, PlayersColors color){
        this.nickname=nickname;
        this.color=color;

        this.board = new PlayerBoard();
        this.score=0;
        this.position=null;
    }


    /*ATTRIBUTES*/

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


    /*METHODS*/

                /*position*/
    /***/
    public void setPosition(int x, int y){
        this.position = new Position(x,y);
    }

    /***/
    public Position getPosition() {
        return position;
    }

                /*points*/
    /***/
    public void AddPoints(int points) {
        this.score+=points;
    }

                /*nickname*/
    /***/
    public String getNickname(){
      return nickname;
    }

                /*color*/
    /***/
    public PlayersColors getColor(){
        return color;
    }

                /*color*/
    /***/
    public int getScore(){
        return score;
    }

                /*PlayerBoard*/
    /** USELESS (?) -> probably YES*/
    public PlayerBoard getPlayerBoard() {
        return board;
    }
    /***/
    public void addDeath() {
        this.board.addDeath();
    }
    /***/
    public int getDeathCounter() {
        return this.board.getDeathCounter();
    }
    /***/
    public void addAmmoCubes(AmmoCubesColor color, int quantity) {
        this.board.addAmmoCubes(color, quantity);
    }
    /***/
    public boolean payAmmoCubes(AmmoCubesColor color, int quantity){
        return this.board.payAmmoCubes(color, quantity);
    }
    /***/
    public void addDamages (Player shootingPlayer, int numberOfDamages){
        this.board.addDamages(shootingPlayer,numberOfDamages);
    }
    /***/
    public void emptyDamagesTracker(){
        this.board.emptyDamagesTracker();
    }
    /***/
    public void addMarksFrom(Player markingPlayer, int quantity){
        this.board.addMarksFrom(markingPlayer,quantity);
    }
    /***/
    public void getMarksFrom(Player markingPlayer){
        this.board.getMarksFrom(markingPlayer);
    }
}
