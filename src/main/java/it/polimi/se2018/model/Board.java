package it.polimi.se2018.model;

import it.polimi.se2018.model.enumerations.SquareTypes;

import java.io.BufferedReader;
import java.io.FileReader;

/***/
public class Board {

    /***/
    public Board(int ID) {



    }
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

        /* lettura da file della mappa*/




        return map;
    }

    /***/
    private Square[][] buildMap1(){
        Square[][] map = new Square[3][4];

        /* lettura da file della mappa*/

        return map;
    }

    /***/
    private Square[][] buildMap2(){
        Square[][] map = new Square[3][4];

        /* lettura da file della mappa*/

        return map;
    }

    /***/
    private Square[][] buildMap3(){
        Square[][] map = new Square[3][4];

        /* lettura da file della mappa*/

        return map;
    }

}