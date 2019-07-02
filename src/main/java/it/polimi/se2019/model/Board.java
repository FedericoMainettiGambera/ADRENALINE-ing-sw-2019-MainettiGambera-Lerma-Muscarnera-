package it.polimi.se2019.model;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.model.enumerations.CardinalPoint;
import it.polimi.se2019.model.enumerations.SquareSide;
import it.polimi.se2019.model.enumerations.SquareTypes;
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
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.se2019.model.enumerations.CardinalPoint.*;





/**build and keep the board of the game*/
public class Board{

    private final static Logger logger=Logger.getLogger(Board.class.getName());
    private VirtualView vvrmi;

    private VirtualView vvsocket;

    /** constructor
     * @param chosenMap indicates the one map chosen between the 4 available
     * @param vvrmi it keeps a connection with the RMI system
     * @param vvsocket it keeps a connection with the Socket System */
    public Board(String chosenMap, VirtualView vvsocket, VirtualView vvrmi) throws IOException{
        this.vvsocket = vvsocket;
        this.vvrmi = vvrmi;
        this.board = buildMap(chosenMap);
        this.chosenMap=chosenMap;
    }

    /**@return board*/
    public Square[][] getMap() {
        return board;
    }

    /**list of attributes*/
    private Square[][] board;
    private BufferedReader bufferedReader;
    private Square[] spawnPointslist = new Square[3];
    private String chosenMap;

    /** from a specified
     * @param position this function allows you to get the equivalent
     * @return Square*/
    public Square getSquare(Position position){
        return this.board[position.getX()][position.getY()];
    }

    /**@param X it's equivalent of the previous function but requires coordinates
     * @param Y it's equivalent of the previous function but requires coordinates
     * @return Square*/
    public Square getSquare(int X, int Y){
        return this.board[X][Y];
    }

    /**
     * function used in controller to get the SpawnSquare where players want to spawn
     * @param color of the spawn point the player want to spawn in
     * */
    public Position getSpawnpointOfColor(AmmoCubesColor color){
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

        throw new IllegalStateException("Square not found.");
    }

    /**@param pos indicates the position you want to know the room of
     * @return room , a list of Squares which the said room is composed of
     * */
     List<Square> getRoomFromPosition(Position pos){
        List<Square> room = new ArrayList<>();
        Square originalPos = this.getSquare(pos);
        char roomColor=originalPos.getColor();
         for (Square[] squares : this.board) {
             for (int j = 0; j < this.board[0].length; j++) {
                 if (squares[j] != null && squares[j].getColor() == roomColor) {
                     room.add(squares[j]);
                 }
             }
         }
        return room;
    }

    /**@param from starting position
     * @param to final position
     * @return int value that indicates the distance between the said positions
     * */
    public int distanceFromTo(Position from, Position to) throws Exception {
        boolean found = false;
        int distanceN = 0;

        //checks if the position exists on the board
        if(from.getY()>=this.board[0].length || from.getX()>=this.board.length || to.getY()>=this.board[0].length || to.getX()>=this.board.length){
            throw new Exception("the desired positions are outside the map dimensions");
        }
        if(from.getY()<0 || from.getX()<0 || to.getY()<0 || to.getX()<0){
            throw new Exception("cannot search for distance of negative positions.");
        }
        if(this.board[from.getX()][from.getY()] == null || this.board[to.getX()][to.getY()]==null){
            throw new Exception("the selected square doesn't exist in the map.");
        }

        ArrayList<Position> positionsAtDistanceN = new ArrayList<>();
        ArrayList<Position> toAddInNextIteration = new ArrayList<>();
        ArrayList<Position> tempPosition;

        //checks if from and to are the same position.
        positionsAtDistanceN.add(from);
        if(contains(positionsAtDistanceN,to)){
            return distanceN;
        }

        //cycle until found the desired distance.
        while (true){
            //increment distance
            distanceN++;

            //add to positionsAtDistanceN all the new reachable positions from the new distanceN value
            Position p;
            Position tempP;
            toAddInNextIteration = new ArrayList<>();
            for (int i = 0; i < positionsAtDistanceN.size() ; i++) {
                p=positionsAtDistanceN.get(i);

                tempPosition= possiblePositions(p,1);
                for (int j = 0; j < tempPosition.size(); j++) {
                    tempP = tempPosition.get(j);

                    if(!contains(positionsAtDistanceN,tempP)){
                        toAddInNextIteration.add(tempP);
                    }
                }
            }
            positionsAtDistanceN.addAll(toAddInNextIteration);

            //chacks if it has found the "to" position
            if(contains(positionsAtDistanceN,to)){
                return distanceN;
            }

            //if distanceN is >= 4*3 (dimension of the map, i've put 15 for sureness..) it should have visited the whole grid
            if(distanceN >= 15){
                throw new IllegalStateException("couldn't calculate the distance, already visited the entire map but have not found the \"to\" position.");
            }
        }
    }

    /**@param chosenMap indicates which map has been chosen to play with
     * this function build the map from a given file
     * */
    private Square[][] buildMap(String chosenMap) throws IOException{
        Square[][] map = new Square[3][4];
        SquareSide[] sides = new SquareSide[4];
        SquareTypes type;
        char color;
        char type2;
        String line = null;
        int s =0;
        FileReader fileReader;


        //apre file
        try{
            fileReader = new FileReader("src/main/Files/map");
            bufferedReader = new BufferedReader(fileReader);
            line = bufferedReader.readLine();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "EXCEPTION ", e );
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
                        if(this.vvrmi !=null && this.vvsocket !=null) {
                            NS.getAmmoCards().addObserver(this.vvrmi);
                            NS.getAmmoCards().addObserver(this.vvsocket);
                        }
                        map[i][j] = NS;

                    }
                    //se no crea uno spawnPoint
                    else {

                        type = SquareTypes.spawnPoint;
                        SpawnPointSquare SPS = new SpawnPointSquare(i, j, sides[0], sides[1], sides[2], sides[3], type, color);
                        if(this.vvrmi !=null && this.vvsocket !=null) {
                            SPS.getWeaponCards().addObserver(this.vvrmi);
                            SPS.getWeaponCards().addObserver(this.vvsocket);
                        }
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

        }
        return map;
    }
/**it is a Minimum Paths algorythm used to calculate the position allowed
 * @param startingPosition from a given starting position with a given with a given
 * @param numberOfMoves  number of moves
 * @return an arraylist of possible positions where to move
 * */
    public ArrayList<Position> possiblePositions(Position startingPosition, int numberOfMoves){
        ArrayList<Position> possiblePositions = new ArrayList<>();
        possiblePositions.add(startingPosition);
        ArrayList<Position> possiblePositionsToAddInNextCycle = new ArrayList<>();
        Square tempSquare;
        Position tempPos = startingPosition;
        boolean toAdd = true;
        for (int i = 0; i < numberOfMoves; i++) {
            for (int j = 0; j < possiblePositions.size(); j++) {

                tempSquare = this.board[possiblePositions.get(j).getX()][possiblePositions.get(j).getY()];

                //NORTH
                if( ( tempSquare.getSide(CardinalPoint.north) != SquareSide.wall ) //non ci sia un muro
                        && (possiblePositions.get(j).getX()!=0)  //non siamo sul bordo della mappa
                        && (this.board[possiblePositions.get(j).getX()-1][possiblePositions.get(j).getY()] != null)){ //la casella che stiamo osservando non sia vuota
                    tempPos = new Position(possiblePositions.get(j).getX()-1,possiblePositions.get(j).getY());
                    if(!contains(possiblePositions, tempPos) && !contains(possiblePositionsToAddInNextCycle,tempPos)){
                        possiblePositionsToAddInNextCycle.add(tempPos);
                    }
                }

                //SOUTH
                if( ( tempSquare.getSide(south) != SquareSide.wall ) //non ci sia un muro
                        && (possiblePositions.get(j).getX() != this.board.length-1)  //non siamo sul bordo della mappa
                        && (this.board[possiblePositions.get(j).getX()+1][possiblePositions.get(j).getY()] != null)){ //la casella che stiamo osservando non sia vuota
                    tempPos = new Position(possiblePositions.get(j).getX()+1,possiblePositions.get(j).getY());
                    if(!contains(possiblePositions, tempPos) && !contains(possiblePositionsToAddInNextCycle,tempPos)){
                        possiblePositionsToAddInNextCycle.add(tempPos);
                    }
                }

                //EAST
                if(( tempSquare.getSide(east) != SquareSide.wall ) //non ci sia un muro
                        && (possiblePositions.get(j).getY() != this.board[0].length-1)  //non siamo sul bordo della mappa
                        && (this.board[possiblePositions.get(j).getX()][possiblePositions.get(j).getY()+1] != null)){ //la casella che stiamo osservando non sia vuota
                    tempPos = new Position(possiblePositions.get(j).getX(),possiblePositions.get(j).getY()+1);
                    if(!contains(possiblePositions, tempPos) && !contains(possiblePositionsToAddInNextCycle,tempPos)){
                        possiblePositionsToAddInNextCycle.add(tempPos);
                    }
                }

                //WEST
                if(( tempSquare.getSide(CardinalPoint.west) != SquareSide.wall ) //non ci sia un muro
                        && (possiblePositions.get(j).getY() != 0)  //non siamo sul bordo della mappa
                        && (this.board[possiblePositions.get(j).getX()][possiblePositions.get(j).getY()-1] != null)){ //la casella che stiamo osservando non sia vuota
                    tempPos = new Position(possiblePositions.get(j).getX(),possiblePositions.get(j).getY()-1);
                    if(!contains(possiblePositions, tempPos) && !contains(possiblePositionsToAddInNextCycle,tempPos)){
                        possiblePositionsToAddInNextCycle.add(tempPos);
                    }
                }
            }
            possiblePositions.addAll(possiblePositionsToAddInNextCycle);
            possiblePositionsToAddInNextCycle.clear();
        }
        return possiblePositions;
    }

    /**make the board understandable for human user*/
    public String toString(){
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {

                if(this.board[i][j] == null){
                    s.append("----------------------------------");
                }
                else {

                    s.append(this.board[i][j].getSquareType());
                    s.append(" ").append(this.board[i][j].getColor());
                    s.append(" [").append(i).append("][").append(j).append("]");
                    s.append(" n:").append(this.board[i][j].getSide(CardinalPoint.north));
                    s.append(" e:").append(this.board[i][j].getSide(east));
                    s.append(" s:").append(this.board[i][j].getSide(south));
                    s.append(" w:").append(this.board[i][j].getSide(CardinalPoint.west));
                }
                s.append("      ");
            }
            s.append("\n\n");
        }
        return s.toString();
    }

    /**@param positions with a given list of positions
       @param pos  and a given position
        @return a boolean value that indicates if the array contains the position
     */
    public boolean contains(ArrayList<Position> positions, Position pos){
        for (Position position : positions) {
            if (position.equalPositions(pos)) {
                return true;
            }
        }
        return false;
    }

    /**@return a list of player in the same room as the position
     * @param pos given
     * */
    public static List<Player> getPlayersInRoom(Position pos){
        List<Position> positionsList=new ArrayList<>();
        List<Player> players=new ArrayList<>();

        System.out.println("<SERVER-model> checking players in room: " + ModelGate.getModel().getBoard().getSquare(pos).getColor());

        List<Square> squareList= ModelGate.getModel().getBoard().getRoomFromPosition(pos);
        for (Square square : squareList){
            positionsList.add(square.getCoordinates());
        }

        for (Player p: ModelGate.getModel().getPlayerList().getPlayersOnBoard()){
            for (Position position: positionsList){
                if(p.getPosition().equalPositions(position)){
                    players.add(p);
                }
            }
        }

        System.out.println("<SERVER-model> players found are:");
        for (Player p: players) {
            System.out.println("               " + p.getNickname());
        }

        return players;
    }

    public static List<Player> getCanSeePlayerFrom(Position pos){

        System.out.println("<SERVER> searching for the players that can be seen from position " +pos.humanString());

        //takes all players in the bot's room
        System.out.println("         checking current position room");
        List<Player> players = new ArrayList<>(getPlayersInRoom(pos));

        Square botSquare=ModelGate.getModel().getBoard().getSquare(pos);

        //takes all players in the adjacent rooms:
        System.out.println("         checking north room");
        if(botSquare.getSide(CardinalPoint.north).equals(SquareSide.door)){
            players.addAll(getPlayersInRoom(new Position(pos.getX()-1, pos.getY())));
        }
        System.out.println("         checking south room");
        if(botSquare.getSide(CardinalPoint.south).equals(SquareSide.door)){
            players.addAll(getPlayersInRoom(new Position(pos.getX()+1, pos.getY())));
        }
        System.out.println("         checking east room");
        if(botSquare.getSide(CardinalPoint.east).equals(SquareSide.door)){
            players.addAll(getPlayersInRoom(new Position(pos.getX(), pos.getY()+1)));
        }
        System.out.println("         checking west room");
        if(botSquare.getSide(CardinalPoint.west).equals(SquareSide.door)){
            players.addAll(getPlayersInRoom(new Position(pos.getX(), pos.getY()-1)));
        }
        return players;
    }


/**build an equivalent class of this for the view usage  for graphical and safeness  reasons
 * @return BoardV
 * */
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