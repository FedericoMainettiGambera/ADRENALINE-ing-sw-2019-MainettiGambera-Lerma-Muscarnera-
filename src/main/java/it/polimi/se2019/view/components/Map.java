package it.polimi.se2019.view.components;

import it.polimi.se2019.model.events.ModelViewEvent;

public class Map implements ViewComponent {


    private Square[][] map;

    public Map(){

        this.map=null;

    }

    public Square[][] getMap() {
        return map;
    }

    public void setMap(Square[][] map) {
        this.map = map;
    }

    @Override
    public void display(ModelViewEvent MVE) {

    }
}
