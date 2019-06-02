package it.polimi.se2019.model;

import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.model.enumerations.CardinalPoint;
import it.polimi.se2019.model.enumerations.SquareSide;
import it.polimi.se2019.model.enumerations.SquareTypes;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.view.components.BoardV;
import it.polimi.se2019.view.components.NormalSquareV;
import it.polimi.se2019.view.components.SpawnPointSquareV;
import it.polimi.se2019.view.components.SquareV;
import it.polimi.se2019.virtualView.VirtualView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static it.polimi.se2019.model.enumerations.CardinalPoint.east;
import static it.polimi.se2019.model.enumerations.CardinalPoint.south;


/***/
public class Board{

    public VirtualView VVRMI;
    public VirtualView VVSocket;

    /***/
    public Board(String chosenMap, VirtualView VVSocket, VirtualView VVRMI) throws IOException, NullPointerException {
        this.VVSocket = VVSocket;
        this.VVRMI = VVRMI;
        this.board = buildMap(chosenMap);
        this.chosenMap=chosenMap;
    }

    public Square[][] getMap() {
        return board;
    }

    /***/
    private Square[][] board;
    private FileReader fileReader;
    private BufferedReader bufferedReader;
    private Square[] spawnPointslist = new Square[3];
    private String chosenMap;

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

    public List<Square> getRoomFromPosition(Position pos){
        List<Square> room = new ArrayList<>();
        Square originalPos = this.board[pos.getX()][pos.getY()];
        char roomColor=originalPos.getColor();
        room.add(originalPos);
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                if(this.board[i][j].getColor() == roomColor){
                    room.add(this.board[i][j]);
                }
            }
        }
        return room;
    }

    public int distanceFromTo(Position from, Position to) throws Exception {
        boolean found = false;

        ArrayList<Position> possiblePositions = new ArrayList<>();

        possiblePositions.add(from);

        ArrayList<Position> possiblePositionsToAddInNextCycle = new ArrayList<>();

        Square tempSquare;

        Position tempPos = from;

        int numberOfMoves=0;
        boolean toAdd = true;
        while (!found) {
            if(contains(possiblePositions,to)){
                found = true;
            }
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
                if( ( tempSquare.getSide(south) != SquareSide.wall ) //non ci sia un muro
                        && (possiblePositions.get(j).getX() != this.board.length-1)  //non siamo sul bordo della mappa
                        && (this.board[possiblePositions.get(j).getX()+1][possiblePositions.get(j).getY()] != null)){ //la casella che stiamo osservando non sia vuota
                    tempPos = new Position(possiblePositions.get(j).getX()+1,possiblePositions.get(j).getY());
                    if(!contains(possiblePositions, tempPos) && !contains(possiblePositionsToAddInNextCycle,tempPos)){
                        //System.out.println("adding south: (X: " + tempPos.getX() + ", Y: " + tempPos.getY() +")");
                        possiblePositionsToAddInNextCycle.add(tempPos);
                    }
                }

                //EAST
                if(( tempSquare.getSide(east) != SquareSide.wall ) //non ci sia un muro
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

            numberOfMoves++;
            if(numberOfMoves >= this.board.length*this.board[0].length){
                throw new Exception("can't find square");
            }
        }
        return numberOfMoves;
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
                        NormalSquare NS = new NormalSquare(i, j, sides[0], sides[1], sides[2], sides[3], type, color);
                        NS.getAmmoCards().addObserver(this.VVRMI);
                        NS.getAmmoCards().addObserver(this.VVSocket);
                        map[i][j] = NS;

                    }
                    //se no crea uno spawnPoint
                    else {

                        type = SquareTypes.spawnPoint;
                        SpawnPointSquare SPS = new SpawnPointSquare(i, j, sides[0], sides[1], sides[2], sides[3], type, color);
                        SPS.getWeaponCards().addObserver(this.VVRMI);
                        SPS.getWeaponCards().addObserver(this.VVSocket);
                        map[i][j] = SPS;
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
                if( ( tempSquare.getSide(south) != SquareSide.wall ) //non ci sia un muro
                        && (possiblePositions.get(j).getX() != this.board.length-1)  //non siamo sul bordo della mappa
                        && (this.board[possiblePositions.get(j).getX()+1][possiblePositions.get(j).getY()] != null)){ //la casella che stiamo osservando non sia vuota
                    tempPos = new Position(possiblePositions.get(j).getX()+1,possiblePositions.get(j).getY());
                    if(!contains(possiblePositions, tempPos) && !contains(possiblePositionsToAddInNextCycle,tempPos)){
                        //System.out.println("adding south: (X: " + tempPos.getX() + ", Y: " + tempPos.getY() +")");
                        possiblePositionsToAddInNextCycle.add(tempPos);
                    }
                }

                //EAST
                if(( tempSquare.getSide(east) != SquareSide.wall ) //non ci sia un muro
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
                    s += " e:" + this.board[i][j].getSide(east);
                    s += " s:" + this.board[i][j].getSide(south);
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
        BoardV boardV = new BoardV();
        SquareV[][] squareV = new SquareV[this.board.length][this.board[0].length];
        for (int i = 0; i < this.board.length ; i++) {
            for (int j = 0; j < this.board[0].length ; j++) {
                if(this.board[i][j]!=null){
                    if(this.board[i][j].getSquareType() == SquareTypes.normal) {
                        squareV[i][j] = new NormalSquareV();
                        ((NormalSquareV)squareV[i][j]).setAmmoCards(((NormalSquare)this.board[i][j]).getAmmoCards().buildDeckV());
                    }
                    else if(this.board[i][j].getSquareType() == SquareTypes.spawnPoint){
                        squareV[i][j] = new SpawnPointSquareV();
                        ((SpawnPointSquareV)squareV[i][j]).setWeaponCards(((SpawnPointSquare)this.board[i][j]).getWeaponCards().buildDeckV());
                        ((SpawnPointSquareV)squareV[i][j]).setColor(((SpawnPointSquare)this.board[i][j]).getColor());
                    }
                    squareV[i][j].setColor(this.board[i][j].getColor());
                    squareV[i][j].setSquareType(this.board[i][j].getSquareType());
                    squareV[i][j].setX(this.board[i][j].getCoordinates().getX());
                    squareV[i][j].setY(this.board[i][j].getCoordinates().getY());
                    squareV[i][j].setNorth(this.board[i][j].getSide(CardinalPoint.north));
                    squareV[i][j].setEast(this.board[i][j].getSide(east));
                    squareV[i][j].setSouth(this.board[i][j].getSide(south));
                    squareV[i][j].setWest(this.board[i][j].getSide(CardinalPoint.west));
                }
                else{
                    squareV[i][j] = null;
                }
            }
        }
        boardV.setMap(squareV);
        boardV.setChosenMap(chosenMap);
        return boardV;
    }
}