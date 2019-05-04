package it.polimi.se2018.model;

import it.polimi.se2018.model.enumerations.SquareSide;
import it.polimi.se2018.model.enumerations.SquareTypes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;


/***/
public class Board {

    /***/
    public Board(String chosenMap) throws IOException, NullPointerException {

            this.board=buildMap(chosenMap);

    }

    public Square[][] getBoard() {
        return board;
    }

    /***/
    private Square[][] board;
    private FileReader fileReader;
    private BufferedReader bufferedReader;

    /***/



    private Square[][] buildMap(String chosenMap) throws IOException{
        Square[][] map = new Square[3][4];
        SquareSide[] sides = new SquareSide[4];
        SquareTypes type;
        char color, type2;
        String line=null;



            //apre file
            try {
                fileReader = new FileReader("/Users/LudoLerma/IdeaProjects/ing-sw-2019-MainettiGambera-Lerma-Muscarnera/src/main/Files/map");
                bufferedReader = new BufferedReader(fileReader);
                line=bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //cerca la linea dopo map0,1,2,o 3
            while (!line.contains(chosenMap)) {

                line=bufferedReader.readLine();

            }

            //trovata la linea dopo map0, la converte in un array di caratteri
            char[] string = bufferedReader.readLine().toCharArray();

            //crea 4 righe di squares e 3 colonne di squares per la mappa

            for (int i = 0; i < 3; i++) {


                for(int j = 0; j < 4; j++){

                    if (string[0] != '-') {
                        int index = 0; //serve per creare un array di sides
                        type2 = string[0];   //il primo carattere indica il tipo di square
                        color = string[1];   //il secondo il colore
                        for (int l = 2; index < 4; l++) { //dal terzo carattere iniziano ad essere indicati i sides

                            //north,east,south,west rispettivamente
                            //ci sono quattro sides

                            if (string[l] == 'n') {

                                sides[index] = SquareSide.nothing;
                            } else if (string[l] == 'w') {

                                sides[index] = SquareSide.wall;
                            } else {
                                sides[index] = SquareSide.door;
                            }
                            index++;

                        }


                        //se il primo carattere è una N crea un normal square

                        if (type2 == 'N') {
                            type = SquareTypes.normal;
                            map[i][j] = new NormalSquare(i, j, sides[0], sides[1], sides[2], sides[3], type, color);

                        }
                        //se no crea uno spawnPoint
                        else {

                            type = SquareTypes.spawnPoint;
                            map[i][j] = new SpawnPointSquare(i, j, sides[0], sides[1], sides[2], sides[3], type, color);

                        }

                    } else map[i][j] = null;   //- indica che non c'è quell'elemento della mappa

                    string = bufferedReader.readLine().toCharArray();
                    //c'è uno spazio bianco che separa le righe della mappa
                    //il while serve a posizionarsi sulla riga giusta


                }

                //c'è uno spazio bianco che separa le righe della mappa
                //il while serve a posizionarsi sulla riga giusta

                while (string[0] == ' ') {
                    string = bufferedReader.readLine().toCharArray();
                }


            }
            return map;
        }

 
 
 
 
 
 
 
 




























}