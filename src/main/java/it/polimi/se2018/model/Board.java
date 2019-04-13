package it.polimi.se2018.model;

/***/
public class Board {

    /***/
    public Board(String chosenMap) {
        if(chosenMap == "map 0 Name Here"){
            this.board=buildMap0();
        }
        else if(chosenMap == "map 1 Name Here"){
            this.board=buildMap1();
        }
        else if(chosenMap == "map 2 Name Here"){
            this.board=buildMap2();
        }
        else if(chosenMap == "map 3 Name Here"){
            this.board=buildMap3();
        }
    }

    /***/
    private Square[][] board;

    /***/
    private Square[][] buildMap0(){
        Square[][] map = new Square[3][4];

        return map;
    }

    /***/
    private Square[][] buildMap1(){
        Square[][] map = new Square[3][4];

        return map;
    }

    /***/
    private Square[][] buildMap2(){
        Square[][] map = new Square[3][4];

        return map;
    }

    /***/
    private Square[][] buildMap3(){
        Square[][] map = new Square[3][4];

        return map;
    }

}