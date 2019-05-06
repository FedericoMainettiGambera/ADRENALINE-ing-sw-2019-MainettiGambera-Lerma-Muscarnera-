package it.polimi.se2018.view;

import java.util.Observable;
import java.util.Observer;

public class View implements Observer {

   private Player player;
   private Map map;
   private GameTrack gameTrack;



    @Override
    public void update(Observable o, Object arg) {

    }
    public void View(){
        this.player=new Player();
        this.map=new Map();
        this.gameTrack=new GameTrack();
    }

    public GameTrack getGameTrack() {
        return gameTrack;
    }

    public Player getPlayer() {
        return player;
    }

    public Map getMap() {
        return map;
    }
}
