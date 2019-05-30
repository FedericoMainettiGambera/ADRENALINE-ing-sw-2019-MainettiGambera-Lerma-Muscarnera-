package it.polimi.se2019.model;

import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.model.enumerations.CardinalPoint;
import it.polimi.se2019.model.enumerations.SquareSide;
import it.polimi.se2019.model.enumerations.SquareTypes;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.view.components.BoardV;
import it.polimi.se2019.virtualView.VirtualView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;
import java.util.ArrayList;
import java.util.Observable;


/***/
public class Board{

    public VirtualView VV;

    /***/
    public Board(String chosenMap, VirtualView VV) throws IOException, NullPointerException {
        this.VV = VV;
        this.board = buildMap(chosenMap);
    }

    public Square[][] getMap() {
        return board;
    }

    /***/
    private Square[][] board;
    private FileReader fileReader;
    private BufferedReader bufferedReader;
    private Square[] spawnPointslist = new Square[3];

    public Square getSquare(Position position){
        return this.board[position.getX()][position.getY()];
    }

    public Square getSquare(int X, int Y){
        return this.board[X][Y];
    }
    //function used in controller to get the SpawnSquare where players want to spawn



   public Position getSpawnpointOfColor(AmmoCubesColor color)throws NullPointerException, Exception{
        int g=0;

        if(color.equals(AmmoCubesColor.yellow)){
            while(spawnPointslist[g].getColor()!='Y'){
                g++;
            }
            if(spawnPointslist[g].getColor()=='Y'){
                return spawnPointslist[g].getCoordinates();
            }
        }

        else if(color.equals(AmmoCubesColor.red)){
            while(spawnPointslist[g].getColor()!='R'){
                g++;
            }
            if(spawnPointslist[g].getColor()=='R'){
                return spawnPointslist[g].getCoordinates();
            }
        }
        else if(color.equals(AmmoCubesColor.blue)){
            while(spawnPointslist[g].getColor()!='B'){
                g++;
            }
            if(spawnPointslist[g].getColor()=='B'){
                return spawnPointslist[g].getCoordinates();
            }
        }

        throw new Exception("Square not found.");
    }


   private Square[][] buildMap(String chosenMap) throws IOException{
        Square[][] map = new Square[3][4];
        SquareSide[] sides = new SquareSide[4];
        SquareTypes type;
        char color, type2;
        String line = null;
        int s =0;





        //apre file
        try{
            fileReader = new FileReader("src/main/Files/map");
            bufferedReader = new BufferedReader(fileReader);
            line = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //cerca la linea dopo map0,1,2,o 3
        while (!line.contains(chosenMap)){

            line = bufferedReader.readLine();

        }

        //trovata la linea dopo map0, la converte in un array di caratteri
        char[] string = bufferedReader.readLine().toCharArray();

        //crea 4 righe di squares e 3 colonne di squares per la mappa

        for (int i = 0; i < 3; i++) {


            for (int j = 0; j < 4; j++) {

                if (string[0] != '-'){
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
                        spawnPointslist[s]=map[i][j];
                        s++;


                    }

                } else map[i][j] = null;   //- indica che non c'è quell'elemento della mappa

                line=bufferedReader.readLine();
                if(line!=null)
                {string=line.toCharArray();}
                //c'è uno spazio bianco che separa le righe della mappa
                //il while serve a posizionarsi sulla riga giusta


            }

            //c'è uno spazio bianco che separa le righe della mappa
            //il while serve a posizionarsi sulla riga giusta

            line=bufferedReader.readLine();
            if(line!=null)
            {string=line.toCharArray();}

         //    while (string[0] == '\b') {
          //       string = bufferedReader.readLine().toCharArray();
            //  }
        }
        return map;
    }

    public ArrayList<Position> possiblePositions(Position startingPosition, int numberOfMoves){
        ArrayList<Position> possiblePositions = new ArrayList<>();
        possiblePositions.add(startingPosition);
        ArrayList<Position> possiblePositionsToAddInNextCycle = new ArrayList<>();
        Square tempSquare;
        Position tempPos = startingPosition;
        boolean toAdd = true;
        for (int i = 0; i < numberOfMoves; i++) {
            for (int j = 0; j < possiblePositions.size(); j++) {

                //System.out.println("Starting position: " + startingPosition);
                //System.out.println("Calculating possible position considering coordinates (X: " + possiblePositions.get(j).getX() + ", Y: " + possiblePositions.get(j).getY() +")");
                //pay attention to the this.board.lenght and the this.board[0].lenght, they are inverted and makes confusion.

                tempSquare = this.board[possiblePositions.get(j).getX()][possiblePositions.get(j).getY()];

                //NORTH
                if( ( tempSquare.getSide(CardinalPoint.north) != SquareSide.wall ) //non ci sia un muro
                        && (possiblePositions.get(j).getX()!=0)  //non siamo sul bordo della mappa
                        && (this.board[possiblePositions.get(j).getX()-1][possiblePositions.get(j).getY()] != null)){ //la casella che stiamo osservando non sia vuota
                    tempPos = new Position(possiblePositions.get(j).getX()-1,possiblePositions.get(j).getY());
                    if(!contains(possiblePositions, tempPos) && !contains(possiblePositionsToAddInNextCycle,tempPos)){
                        //System.out.println("adding north: (X: " + tempPos.getX() + ", Y: " + tempPos.getY() +")");
                        possiblePositionsToAddInNextCycle.add(tempPos);
                    }
                }

                //SOUTH
                if( ( tempSquare.getSide(CardinalPoint.south) != SquareSide.wall ) //non ci sia un muro
                        && (possiblePositions.get(j).getX() != this.board.length-1)  //non siamo sul bordo della mappa
                        && (this.board[possiblePositions.get(j).getX()+1][possiblePositions.get(j).getY()] != null)){ //la casella che stiamo osservando non sia vuota
                    tempPos = new Position(possiblePositions.get(j).getX()+1,possiblePositions.get(j).getY());
                    if(!contains(possiblePositions, tempPos) && !contains(possiblePositionsToAddInNextCycle,tempPos)){
                        //System.out.println("adding south: (X: " + tempPos.getX() + ", Y: " + tempPos.getY() +")");
                        possiblePositionsToAddInNextCycle.add(tempPos);
                    }
                }

                //EAST
                if(( tempSquare.getSide(CardinalPoint.east) != SquareSide.wall ) //non ci sia un muro
                        && (possiblePositions.get(j).getY() != this.board[0].length-1)  //non siamo sul bordo della mappa
                        && (this.board[possiblePositions.get(j).getX()][possiblePositions.get(j).getY()+1] != null)){ //la casella che stiamo osservando non sia vuota
                    tempPos = new Position(possiblePositions.get(j).getX(),possiblePositions.get(j).getY()+1);
                    if(!contains(possiblePositions, tempPos) && !contains(possiblePositionsToAddInNextCycle,tempPos)){
                        //System.out.println("adding east: (X: " + tempPos.getX() + ", Y: " + tempPos.getY() +")");
                        possiblePositionsToAddInNextCycle.add(tempPos);
                    }
                }

                //WEST
                if(( tempSquare.getSide(CardinalPoint.west) != SquareSide.wall ) //non ci sia un muro
                        && (possiblePositions.get(j).getY() != 0)  //non siamo sul bordo della mappa
                        && (this.board[possiblePositions.get(j).getX()][possiblePositions.get(j).getY()-1] != null)){ //la casella che stiamo osservando non sia vuota
                    tempPos = new Position(possiblePositions.get(j).getX(),possiblePositions.get(j).getY()-1);
                    if(!contains(possiblePositions, tempPos) && !contains(possiblePositionsToAddInNextCycle,tempPos)){
                        //System.out.println("adding west: (X: " + tempPos.getX() + ", Y: " + tempPos.getY() +")");
                        possiblePositionsToAddInNextCycle.add(tempPos);
                    }
                }
            }
            possiblePositions.addAll(possiblePositionsToAddInNextCycle);
            possiblePositionsToAddInNextCycle.clear();
        }
        return possiblePositions;
    }

    public String toString(){
        String s = "";
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {

                if(this.board[i][j] == null){
                    s+="----------------------------------";
                }
                else {
                    s += this.board[i][j].getSquareType();
                    s += " " + this.board[i][j].getColor();
                    s += " [" + i + "][" + j + "]";
                    s += " n:" + this.board[i][j].getSide(CardinalPoint.north);
                    s += " e:" + this.board[i][j].getSide(CardinalPoint.east);
                    s += " s:" + this.board[i][j].getSide(CardinalPoint.south);
                    s += " w:" + this.board[i][j].getSide(CardinalPoint.west);
                }
                s+= "      ";
            }
            s+="\n\n";
        }
        return s;
    }

    public boolean contains(ArrayList<Position> positions, Position pos){
        for (int k = 0; k < positions.size(); k++) {
            if(positions.get(k).equals(pos)){
                return true;
            }
        }
        return false;
    }

    public BoardV buildBoardV(){
        //TODO
        return new BoardV();
    }
}