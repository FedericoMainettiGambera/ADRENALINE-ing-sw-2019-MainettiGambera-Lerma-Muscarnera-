package it.polimi.se2019.view.outputHandler;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.model.Board;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.enumerations.CardinalPoint;
import it.polimi.se2019.model.enumerations.PlayersColors;
import it.polimi.se2019.model.enumerations.SquareSide;
import it.polimi.se2019.model.enumerations.SquareTypes;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.model.events.stateEvent.StateEvent;
import it.polimi.se2019.view.components.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CLIOutputHandler implements OutputHandlerInterface{

    public void updateUserInterface(String message) {
        System.out.println(message);
    }

    @Override
    public void gameCreated() {
        updateUserInterface("<CLIENT> created GameV and PlayerListV");
    }

    @Override
    public void stateChanged(StateEvent StE) {
        updateUserInterface("<CLIENT> state changed: " + StE.getState());
    }

    @Override
    public void setFinalFrenzy(ModelViewEvent MVE) {
        updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        updateUserInterface("              " + ViewModelGate.getModel().isFinalFrenzy());
    }

    @Override
    public void finalFrenzyBegun(ModelViewEvent MVE) {
        updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        updateUserInterface("              " + ViewModelGate.getModel().isHasFinalFrenzyBegun());
    }

    @Override
    public void newKillshotTrack(ModelViewEvent MVE) {
        updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + "You are playing with" + ViewModelGate.getModel().getKillshotTrack().getNumberOfStartingSkulls() +" number of staring skulls");
    }

    @Override
    public void newPlayersList(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        for (PlayerV p :ViewModelGate.getModel().getPlayers().getPlayers()) {
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + p.getNickname());
        }
    }

    @Override
    public void newBoard(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("" + "MAP:");
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface(ViewModelGate.getModel().getBoard().toString());
    }

    @Override
    public void deathOfPlayer(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + "killshot Track has changed:");
        showKillshotTrack();
    }

    public static void showKillshotTrack(){
        //remember the killshot track works from the end to the start ( the first dead player is the last in the list)
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> Killshot track status: ");
        if(ViewModelGate.getModel().getKillshotTrack()==null){
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("         Sorry but the Killshot track hasn't been initialized yet.");
        }
        else if(ViewModelGate.getModel().getKillshotTrack().getKillsV()==null){
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("         Sorry but the Killshot track is empty.");

        }
        else {
            int counter = 0;
            String s = "   ]";
            for (KillsV k : ViewModelGate.getModel().getKillshotTrack().getKillsV()) {
                if (k.isSkull()) {
                    s += "   SKULL" + s;
                } else {
                    if (!k.isOverKill()) {
                        s += "   KILL:" + k.getKillingPlayer() + s;
                    } else {
                        s += "   OVERKILL:" + k.getOverKillingPlayer() + s;

                    }
                }
                counter++;
            }
            s += "         [" + s;
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface(s);
        }
    }

    @Override
    public void movingCardsAround(OrderedCardListV from, OrderedCardListV to, ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        if(to!=null) {
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + "cards Moved from " + from.getContext());
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface(from.toString());

            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + "to " + to.getContext());
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface(to.toString());
        }
        else{
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + from.getContext() + " cards has changed.");
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface(from.toString());
        }
    }

    @Override
    public void shufflingCards(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        OrderedCardListV cards = ((OrderedCardListV) MVE.getComponent());
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + "cards shuffled: " + (cards.getContext()));
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface(cards.toString());
    }

    @Override
    public void newColor(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
            if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + p.getNickname() +" has changed color to: " + ((PlayersColors) MVE.getComponent()));
                break;
            }
        }
    }

    @Override
    public void newNickname(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + ((String)MVE.getExtraInformation1()) +" has changed Nickname to: " + (String) MVE.getComponent());
    }

    @Override
    public void newPosition(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
            if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + p.getNickname() +" has changed Position to: [" + ((Position) MVE.getComponent()).getX() + "][" + ((Position) MVE.getComponent()).getY() + "]");
                break;
            }
        }
    }

    @Override
    public void newScore(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
            if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + p.getNickname() +" has changed score to: " + (int) MVE.getComponent());
                break;
            }
        }
    }

    @Override
    public void addDeathCounter(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
            if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + p.getNickname() +" has died");
                break;
            }
        }
    }

    @Override
    public void setFinalFrenzyBoard(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
            if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + p.getNickname() +" has setted his board to Final Frenzy");
                break;
            }
        }
    }

    @Override
    public void newAmmoBox(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
            if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + p.getNickname() +" has changed his ammo box: ");
                for (AmmoCubesV a : p.getAmmoBox().getAmmoCubesList()) {
                    OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + a.getColor() + ": " + a.getQuantity());
                }
                break;
            }
        }
    }

    @Override
    public void newDamageTracker(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
            if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + p.getNickname() +"'s damage tracker has changed");
                showDamageTracker(p);
                break;
            }
        }
    }

    public static void showDamageTracker(PlayerV playerToShow) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> Damage tracker of player " + playerToShow.getNickname());
        if (playerToShow.getDamageTracker().getDamageSlotsList()!= null) {
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("         Damage tracker is empty");
        } else {
            //TODO (maybe show the colors instead of the nicknames (?)
            String s = "         [";
            for (DamageSlotV d: playerToShow.getDamageTracker().getDamageSlotsList()) {
                s+="   " + d.getShootingPlayerNickname();
            }
            s+="   ]";
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface(s);
        }
    }

    @Override
    public void newMarksTracker(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
            if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + p.getNickname() +"'s marks tracker has changed");
                showMarksTracker(p);
                break;
            }
        }
    }

    public static void showMarksTracker(PlayerV playerToShow){
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> Marks tracker of player " + playerToShow.getNickname());
        if(playerToShow.getMarksTracker().getMarkSlotsList()!=null){
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("         " + "marks slot is empty");
        }
        else {
            for (MarkSlotV m : playerToShow.getMarksTracker().getMarkSlotsList()) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("         " + m.getQuantity() + " marks (received from " + m.getMarkingPlayer() + ")");
            }
        }
    }

    @Override
    public void setCurrentPlayingPlayer(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " +(String) MVE.getComponent());
    }

    @Override
    public void setStartingPlayer(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + ViewModelGate.getModel().getPlayers().getStartingPlayer());
    }

    @Override
    public void newPlayer(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + "new player added: " + ((PlayerV) MVE.getComponent()).getNickname());
    }

    @Override
    public void setAFK(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        if(ViewModelGate.getMe().equals((String) MVE.getExtraInformation1() )){
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + "You've been set to AFK.");
        }
        else {
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + "player " + (String) MVE.getExtraInformation1() + " AFK status: " + MVE.getComponent());
        }
    }

    @Override
    public void showInputTimer(int currentTime, int totalTime) {
        //OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> " + "Input-Timer-of-"+totalTime+"-seconds: " + currentTime + " second has passed.");
    }

    @Override
    public void showConnectionTimer(int currentTime, int totalTime) {
        //OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> " + "Connection-Timer-of-"+totalTime+"-seconds: " + currentTime + " second has passed.");
    }

    @Override
    public void cantReachServer() {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> WE ARE SORRY BUT SOMETHING WENT WRONG.");
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("         please try to reconnect to the same server to continue game.");
        System.exit(0);
    }

    @Override
    public void succesfullReconnection() {
        System.out.println("<CLIENT> SUCCESFULLY RECONNECTED TO SERVER");
        System.out.println("<CLIENT> your model is up-to-date");
    }

    @Override
    public void disconnect() {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> YOU HAVE BEEN DISCONNECTED \n" +
                "         please try to reconnect to the same server to continue game.");
        System.exit(0);
    }


    public static void main(String[] args) throws IOException { //TODO delete this main
        Board b = new Board("map0", null, null);
        System.out.println(b.toString());
        ViewModelGate.setModel(new GameV());
        ViewModelGate.getModel().setBoard(b.buildBoardV());
        ViewModelGate.getModel().setKillshotTrack(new KillShotTrackV());
        showMap();
    }

    public static void showGeneralStatusOfTheGame(){
        if(ViewModelGate.getModel()!=null) {
            showGameInfo();

            showKillshotTrack();
            for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                showPlayerStatus(p);
            }
            if (ViewModelGate.getModel() != null && ViewModelGate.getModel().getBoard() != null && ViewModelGate.getModel().getBoard().getMap() != null && ViewModelGate.getModel().getPlayers() != null) {
                showMap();
            } else {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> sorry, board or player List is not initialized");
            }
        }
    }

    public static void showGameInfo(){
        //TODO
    }

    public static void showPlayerStatus(PlayerV playerToShow){
        //TODO
    }

    public static void showMap(){
        SquareV[][] map = ViewModelGate.getModel().getBoard().getMap();
        List<String>[][] mapCLI = buildEmptyMap();
        for (int i = 0; i < map.length ; i++) {
            for (int j = 0; j < map[0].length ; j++) {
                if(map[i][j] == null){
                    for (int k = 0; k < 12; k++) {
                        mapCLI[i][j].set(k, "MMMMMMMMMMMMMMMMMMMMMMMMMM");
                    }
                }
                else {

                    //squareType
                    if (map[i][j].getSquareType().equals(SquareTypes.normal)) {
                        mapCLI[i][j].set(1, "M    " + map[i][j].getColor() + ": NORMAL SQUARE    M");
                    } else {
                        mapCLI[i][j].set(1, "M " + map[i][j].getColor() + ": SPAWN POINT SQUARE  M");
                    }

                    //cohordinate
                    mapCLI[i][j].set(2, "M          [" + map[i][j].getX() + "][" + map[i][j].getY() + "]        M");

                    //SQUARE SIDES

                    if (map[i][j].getSide(CardinalPoint.north) == SquareSide.door){
                        mapCLI[i][j].set(0, "MMMMMMMMMM       MMMMMMMMM");
                    }
                    else if(map[i][j].getSide(CardinalPoint.north) == SquareSide.nothing){
                        mapCLI[i][j].set(0, "M                        M");
                    }

                    if (map[i][j].getSide(CardinalPoint.south) == SquareSide.door){
                        mapCLI[i][j].set(11, "MMMMMMMMMM       MMMMMMMMM");
                    }
                    else if(map[i][j].getSide(CardinalPoint.south) == SquareSide.nothing){
                        mapCLI[i][j].set(11, "M                        M");
                    }

                    if(map[i][j].getSide(CardinalPoint.east) == SquareSide.door){
                        for (int k = 4; k < 7 ; k++) {
                            char[] charArray = mapCLI[i][j].get(k).toCharArray();
                            charArray[25] = ' ';
                            mapCLI[i][j].set(k, new String(charArray));
                        }
                    }
                    else if(map[i][j].getSide(CardinalPoint.east) == SquareSide.nothing){
                        for (int k = 1; k < 11 ; k++) {
                            char[] charArray = mapCLI[i][j].get(k).toCharArray();
                            charArray[25] = ' ';
                            mapCLI[i][j].set(k, new String(charArray));
                        }
                    }

                    if(map[i][j].getSide(CardinalPoint.west) == SquareSide.door){
                        for (int k = 4; k < 7 ; k++) {
                            char[] charArray = mapCLI[i][j].get(k).toCharArray();
                            charArray[0] = ' ';
                            mapCLI[i][j].set(k, new String(charArray));
                        }
                    }
                    else if(map[i][j].getSide(CardinalPoint.west) == SquareSide.nothing){
                        for (int k = 1; k < 11 ; k++) {
                            char[] charArray = mapCLI[i][j].get(k).toCharArray();
                            charArray[0] = ' ';
                            mapCLI[i][j].set(k, new String(charArray));
                        }
                    }

                    //PLAYERS
                    if(ViewModelGate.getModel().getPlayers() != null && ViewModelGate.getModel().getPlayers().getPlayers()!=null) {
                        for (int k = 0; k < ViewModelGate.getModel().getPlayers().getPlayers().size(); k++) {
                            PlayerV p = ViewModelGate.getModel().getPlayers().getPlayers().get(k);
                            char[] squareArray = mapCLI[p.getX()][p.getY()].get(3 + k).toCharArray();
                            char[] nameArray = p.getNickname().toCharArray();
                            int nameDimension = nameArray.length;
                            if (nameDimension > 15) {
                                nameDimension = 15;
                            }
                            for (int l = 2; l < nameDimension + 2; l++) {
                                squareArray[l] = nameArray[l - 2];
                            }
                            mapCLI[p.getX()][p.getY()].set(3 + k, new String(squareArray));
                        }
                    }
                }
            }
        }

        List<String> mapStringIntermediate = new ArrayList<>();
        for (int i = 0; i < 3; i++) {//currentSquareRow
            for (int j = 0; j < 12 ; j++) { //currentLine
                String line = "";
                for (int k = 0; k < 4 ; k++) { //currentSquare
                    line += mapCLI[i][k].get(j);
                }
                line+="\n";
                mapStringIntermediate.add(line);
            }
        }

        String mapStringFinal = "";
        for (String s: mapStringIntermediate) {
            mapStringFinal += s;
        }

        OutputHandlerGate.getCLIOutputHandler().updateUserInterface(mapStringFinal);
    }

    public static List<String>[][] buildEmptyMap(){
        List<String>[][] emptyMap = new ArrayList[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                emptyMap[i][j] = buildEmptySquare();
            }
        }
        return emptyMap;
    }

    public static List<String> buildEmptySquare(){
        List<String> emptySquare = new ArrayList<>();
        emptySquare.add("MMMMMMMMMMMMMMMMMMMMMMMMMM");
        for (int i = 0; i < 10; i++) {
            emptySquare.add("M                        M");
        }
        emptySquare.add("MMMMMMMMMMMMMMMMMMMMMMMMMM");
        return emptySquare;
    }

}
