package it.polimi.se2019.view.outputHandler;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.model.Board;
import it.polimi.se2019.model.Player;
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
/** this class handle the CLI Outputs, it receives Events of various kind(MOSTLY MODELVIEW EVENTS) and manage to show the right output to the right client view*/

public class CLIOutputHandler implements OutputHandlerInterface{


    private String clientMve="<CLIENT> MVE: ";

    public void updateUserInterface(String message) {
        System.out.println(message);
    }
/**inform the right client that the Game and the PlayerList have been initialized*/
    @Override
    public void gameCreated() {
        updateUserInterface("<CLIENT> created GameV and PlayerListV");
    }
/** Anytime a client changes his state, every client gets to know it */
    @Override
    public void stateChanged(StateEvent stateEvent) {
        updateUserInterface("<CLIENT> state changed: " + stateEvent.getState());
    }
/***/
    @Override
    public void setFinalFrenzy(ModelViewEvent modelViewEvent) {
        updateUserInterface(clientMve + modelViewEvent.getInformation());
        updateUserInterface("              FINAL FRENZY: " + ViewModelGate.getModel().isFinalFrenzy());
    }

    @Override
    public void finalFrenzyBegun(ModelViewEvent modelViewEvent) {
        updateUserInterface(clientMve + modelViewEvent.getInformation());
        if(ViewModelGate.getModel().isHasFinalFrenzyBegun()) {
            updateUserInterface("              FINAL FRENZY HAS BEGUN");
        }
        else{
            updateUserInterface("              FINAL FRENZY HASN'T BUGUN");
        }
    }

    @Override
    public void newKillshotTrack(ModelViewEvent modelViewEvent) {
        updateUserInterface(clientMve + modelViewEvent.getInformation());
        showKillshotTrack();
    }

    @Override
    public void newPlayersList(ModelViewEvent modelViewEvent) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface(clientMve + modelViewEvent.getInformation());
        showPlayerList();
    }

    @Override
    public void newBoard(ModelViewEvent modelViewEvent) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface(clientMve + modelViewEvent.getInformation());
        showMap();
    }

    @Override
    public void deathOfPlayer(ModelViewEvent modelViewEvent) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface(clientMve + modelViewEvent.getInformation());
        showKillshotTrack();
        showPlayerList();
    }

    @Override
    public void movingCardsAround(OrderedCardListV from, OrderedCardListV to, ModelViewEvent modelViewEvent) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface(clientMve + modelViewEvent.getInformation());
        if(to!=null) {
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + "cards Moved from " + from.getContext());
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + "to " + to.getContext());
            showOrderedCardList(from);
            showOrderedCardList(to);
        }
        else{
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + from.getContext() + "DECK CREATED");
            String tempContext = from.getContext();
            from.setContext("NEW DECK");
            showOrderedCardList(from);
            from.setContext(tempContext);
        }
    }

    @Override
    public void shufflingCards(ModelViewEvent modelViewEvent) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface(clientMve + modelViewEvent.getInformation());
        OrderedCardListV cards = ((OrderedCardListV) modelViewEvent.getComponent());
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + "cards shuffled: " + (cards.getContext()));
        showOrderedCardList(cards);
    }

    @Override
    public void newColor(ModelViewEvent modelViewEvent) { //TODO change output
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface(clientMve + modelViewEvent.getInformation());
        for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
            if (p.getNickname().equals(modelViewEvent.getExtraInformation1())) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + p.getNickname() +" has changed color to: " + (modelViewEvent.getComponent()));
                break;
            }
        }
    }

    @Override
    public void newNickname(ModelViewEvent modelViewEvent) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface(clientMve + modelViewEvent.getInformation());
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              the new Nickname is: " + modelViewEvent.getComponent());
        showPlayerList();
    }

    @Override
    public void newPosition(ModelViewEvent modelViewEvent) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface(clientMve+ modelViewEvent.getInformation());
        for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
            if (p.getNickname().equals(modelViewEvent.getExtraInformation1())) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + p.getNickname() +" has changed Position to: [" + ((Position) modelViewEvent.getComponent()).getX() + "][" + ((Position) modelViewEvent.getComponent()).getY() + "]");
                break;
            }
        }
        showMap();
    }

    @Override
    public void newScore(ModelViewEvent modelViewEvent) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface(clientMve + modelViewEvent.getInformation());
        for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
            if (p.getNickname().equals( modelViewEvent.getExtraInformation1())) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + p.getNickname() +" has changed score to: " +  modelViewEvent.getComponent());
                break;
            }
        }
    }

    @Override
    public void addDeathCounter(ModelViewEvent modelViewEvent) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface(clientMve + modelViewEvent.getInformation());
        for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
            if (p.getNickname().equals(modelViewEvent.getExtraInformation1())) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + p.getNickname() +" has died");
                showPlayerStatus(p);
                break;
            }
        }
    }

    @Override
    public void setFinalFrenzyBoard(ModelViewEvent modelViewEvent) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface(clientMve + modelViewEvent.getInformation());
        for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
            if (p.getNickname().equals(modelViewEvent.getExtraInformation1())) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + p.getNickname() +" has set his board to Final Frenzy");
                showPlayerStatus(p);
                break;
            }
        }
    }

    @Override
    public void newAmmoBox(ModelViewEvent modelViewEvent) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface(clientMve + modelViewEvent.getInformation());
        for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
            if (p.getNickname().equals(modelViewEvent.getExtraInformation1())) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + p.getNickname() +" has changed his ammo box: ");
                showPlayerStatus(p);
                break;
            }
        }
    }

    @Override
    public void newDamageTracker(ModelViewEvent modelViewEvent) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface(clientMve + modelViewEvent.getInformation());
        for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
            if (p.getNickname().equals(modelViewEvent.getExtraInformation1())) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + p.getNickname() +"'s damage tracker has changed");
                showPlayerStatus(p);
                break;
            }
        }
    }


    @Override
    public void newMarksTracker(ModelViewEvent modelViewEvent) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface(clientMve+ modelViewEvent.getInformation());
        for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
            if (p.getNickname().equals( modelViewEvent.getExtraInformation1())) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + p.getNickname() +"'s marks tracker has changed");
                showPlayerStatus(p);
                break;
            }
        }
    }


    @Override
    public void setCurrentPlayingPlayer(ModelViewEvent modelViewEvent) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface(clientMve + modelViewEvent.getInformation());
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              Current playing player is: " + modelViewEvent.getComponent());
    }

    @Override
    public void setStartingPlayer(ModelViewEvent modelViewEvent) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface(clientMve+ modelViewEvent.getInformation());
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              Starting player is: " + ViewModelGate.getModel().getPlayers().getStartingPlayer());
    }

    @Override
    public void newPlayer(ModelViewEvent modelViewEvent) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface(clientMve + modelViewEvent.getInformation());
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + "new player added: " + ((PlayerV) modelViewEvent.getComponent()).getNickname());
        showPlayerList();
    }

    @Override
    public void setAFK(ModelViewEvent modelViewEvent) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface(clientMve+ modelViewEvent.getInformation());
        if(ViewModelGate.getMe().equals(modelViewEvent.getExtraInformation1() )){
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + "You've been set to AFK.");
        }
        else {
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + "player " + modelViewEvent.getExtraInformation1() + " AFK status: " + modelViewEvent.getComponent());
        }
        for (PlayerV p: ViewModelGate.getModel().getPlayers().getPlayers()) {
            if(p.getNickname().equals(modelViewEvent.getExtraInformation1())){
                showPlayerStatus(p);
            }
        }
    }

    @Override
    public void showInputTimer(int currentTime, int totalTime) {
        //OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> " + "Input-Timer-of-"+totalTime+"-seconds: " + currentTime + " second has passed.");
    }

    @Override
    public void showConnectionTimer(int currentTime, int totalTime) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("                                 <CLIENT" + "Connection-Timer-of-"+totalTime+"-seconds> " + currentTime + " seconds passed.");
    }

    @Override
    public void cantReachServer() {
        showGeneralStatusOfTheGame();
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> WE ARE SORRY BUT SOMETHING WENT WRONG.");
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("         Please, try to reconnect to the same server to continue game.");
        showConnectionInfo();
        System.exit(0);
    }

    @Override
    public void succesfullReconnection() {
        showGeneralStatusOfTheGame();
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> SUCCESFULLY RECONNECTED TO SERVER.");
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("         Your model is up-to-date");
        showConnectionInfo();
    }

    @Override
    public void disconnect() {
        showGeneralStatusOfTheGame();
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> YOU HAVE BEEN DISCONNECTED.");
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("         Please, try to reconnect to the same server to continue game.");
        showConnectionInfo();
        System.exit(0);
    }

    @Override
    public void finalScoring(ModelViewEvent MVE){
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface((String)MVE.getComponent()+" has arrived "+(String)MVE.getExtraInformation2()+" with  "+MVE.getExtraInformation1()+" points   !!!");

    }

    public static void showGeneralStatusOfTheGame(){
        if(ViewModelGate.getModel()!=null) {

            showGameInfo();

            showKillshotTrack();

            showMap();

            showPlayerList();
        }
    }

    public static void showConnectionInfo(){
        if(Controller.networkConnection != null && Controller.ip != null && Controller.port != null){
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("\n<<<<<<<<<<<< CONNECTION INFO >>>>>>>>>>>>");
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("  CONNECTION TYPE: " + Controller.networkConnection);
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("  IP: " + Controller.ip);
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("  PORT: " + Controller.port);
        }
    }

    public static void showGameInfo(){ //TODO bot, gameMode, gameConstants
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("\n<<<<<<<<<<<< GAME INFO >>>>>>>>>>>>");

        if(ViewModelGate.getMe()!=null){
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("YOU ARE PLAYING AS: " + ViewModelGate.getMe());
        }
        else{
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("SORRY WE DON'T KNOW WHICH PLAYER YOU ARE.");
        }

        if(ViewModelGate.getModel()!=null){
            if(ViewModelGate.getModel().isFinalFrenzy()){
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("YOU ARE PLAYING WITH FINAL FRENZY");
            }
            else{
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("YOU ARE PLAYING WITHOUT FINAL FRENZY");
            }
            if(ViewModelGate.getModel().isHasFinalFrenzyBegun()){
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("FINAL FRENZY HAS BEGUN");
            }
            else {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("FINAL FRENZY HASN'T BEGUN");
            }
            if(ViewModelGate.getModel().isBotActive()){
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("YOU ARE PLAYING WITH THE TERMINATOR");
            }
            else{
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("YOU ARE PLAYING WITHOUT THE TERMINATOR");
            }
        }
    }

    public static void showKillshotTrack(){ //TODO
        //remember the killShot track works from the end to the start ( the first dead player is the last in the list)
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("\n<<<<<<<<<<<< KILLSHOT TRACK >>>>>>>>>>>> ");
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("You are playing with " + ViewModelGate.getModel().getKillshotTrack().getNumberOfStartingSkulls() +" number of staring skulls");
        if(ViewModelGate.getModel()!=null) {
            if (ViewModelGate.getModel().getKillshotTrack() == null) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("Sorry, the Killshot track hasn't been initialized yet.");
            } else if (ViewModelGate.getModel().getKillshotTrack().getKillsV() == null) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("Sorry, the Killshot track is empty.");

            } else {
                int counter = 0;
                StringBuilder s = new StringBuilder("    [");
                for (KillsV k : ViewModelGate.getModel().getKillshotTrack().getKillsV()) {
                    if (k.isSkull()) {
                        s.append("    SKULL");
                    } else {
                        if (!k.isOverKill()) {
                            s.append("    KILL-by-").append(k.getKillingPlayer());
                        } else {
                            s.append("    KILL-with-OVERKILL-by-").append(k.getOverKillingPlayer());

                        }
                    }
                    counter++;
                }
                s.append("    ]");
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface(s.toString());
            }
        }
        else{
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("Sorry, the model is null");
        }
    }

    public static void showOrderedCardList(OrderedCardListV cards){
        StringBuilder s = new StringBuilder();
        if(((!cards.getContext().split(":")[0].equals(ViewModelGate.getMe())) && (cards.getContext().contains(":powerUpInHand")))
                || (cards.getContext().equals("weaponDeck")) || (cards.getContext().equals("ammoDeck")) || (cards.getContext().equals("powerUpDeck"))) {
            //don't show power ups other player's hand, or the decks
            s = new StringBuilder("              " + cards.getContext() + ":\n");
            s.append("                 -CAN'T SHOW YOU THIS CONTENT-");
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface(s.toString());
            return;
        }
        else{
            if(cards.getCards().isEmpty()){
                s = new StringBuilder("              " + cards.getContext() + ":\n");
                s.append("                 -EMPTY-");
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface(s.toString());
                return;
            }
            else {
                s = new StringBuilder("              " + cards.getContext() + ":");
                for (Object c : cards.getCards()) {

                    if (c.getClass().toString().contains("PowerUpCardV")) {
                        PowerUpCardV card = (PowerUpCardV) c;
                        s.append("\n                 ").append(card.getName()).append(" (").append(card.getColor()).append(")");
                    }

                    else if (c.getClass().toString().contains("WeaponCardV")) {
                        WeaponCardV card = (WeaponCardV) c;
                        s.append("\n                 ").append(card.getName());
                        StringBuilder ammo = new StringBuilder("Reload Cost:[");
                        if(card.getReloadCost()==null){
                            ammo.append(" EMPTY ] PickUp Cost:[");
                        }
                        else {
                            for (AmmoCubesV a : card.getReloadCost().getAmmoCubesList()) {
                                ammo.append(" ").append(a.getColor()).append(":").append(a.getQuantity()).append(" ");
                            }
                            ammo.append("] PickUp Cost:[");
                        }
                        if(card.getPickUpCost()==null){
                            ammo.append(" EMPTY ]");
                        }
                        else {
                            for (AmmoCubesV a : card.getPickUpCost().getAmmoCubesList()) {
                                ammo.append(" ").append(a.getColor()).append(":").append(a.getQuantity()).append(" ");
                            }
                            ammo.append("]");
                        }
                        s.append("\n                    ").append(ammo);
                    }

                    else {
                        AmmoCardV card = (AmmoCardV) c;
                        StringBuilder ammo = new StringBuilder("AmmoCard:[");
                        for (AmmoCubesV a : card.getAmmoList().getAmmoCubesList()) {
                            ammo.append(" ").append(a.getColor()).append(":").append(a.getQuantity()).append(" ");
                        }
                        ammo.append("]");
                        s.append("\n                 ").append(ammo);
                        if (card.isPowerUp()) {
                            s.append(" + 1 POWER UP");
                        }
                    }

                }
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface(s.toString());
            }
        }
    }

    public static void showPlayerList(){
        if(ViewModelGate.getModel()!=null && ViewModelGate.getModel().getPlayers()!=null && ViewModelGate.getModel().getPlayers().getPlayers()!=null) {
            for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                showPlayerStatus(p);
            }
        }
        else{
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("Sorry, the Model, or the PlayerList or ListOfPlayers are null");
        }
    }

    public static void showPlayerStatus(PlayerV playerToShow){
        List<String> stringPlayer = new ArrayList<>();

        stringPlayer.add("PPPPPPPP"); //top 0

        if(playerToShow!=null && playerToShow.getNickname()!=null) {
            String nickname = playerToShow.getNickname();
            if(playerToShow.isAFK()){
                nickname+= " AFK";
            }
            stringPlayer.add("PP  " + nickname); //nickname 1
        }
        else {
            stringPlayer.add("PP  Sorry, the Player or the Nickname are null");
        }
        stringPlayer.add( "PP  " + markString(playerToShow)); // marks 2

        stringPlayer.add( "PP  " + damageString(playerToShow)); // damage 3

        stringPlayer.add( "PP  " + deathsString(playerToShow)); //deaths 4

        stringPlayer.add( "PP  " + ammoString(playerToShow)); //ammo 5

        stringPlayer.add("PPPPPPPP"); //bottom 6

        int maxLenght = 20;
        for (String s: stringPlayer) {
            if(maxLenght < s.length()){
                maxLenght = s.length();
            }
        }

        for (int i = 0; i < maxLenght ; i++) {
            stringPlayer.set(0, stringPlayer.get(0) + "P"); //top
            stringPlayer.set(6, stringPlayer.get(6) + "P"); //bottom
        }

        for (int i = 1; i < 6; i++) {
            StringBuilder currentLine = new StringBuilder(stringPlayer.get(i)); //iterate through nickname, marks, damage, deaths, ammo
            while (currentLine.length()<maxLenght+4){
                currentLine.append(" ");
            }
            currentLine.append("  PP");
            stringPlayer.set(i, currentLine.toString());
        }

        for (String s: stringPlayer) {
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface(s);
        }


    }

    public static String ammoString(PlayerV playerToShow){
        if( playerToShow != null && playerToShow.getAmmoBox()!= null && playerToShow.getAmmoBox().getAmmoCubesList()!=null) {
            StringBuilder ammos = new StringBuilder("AMMO: [");
            for (AmmoCubesV a : playerToShow.getAmmoBox().getAmmoCubesList()) {
                ammos.append(" ").append(a.getColor()).append(":").append(a.getQuantity()).append(" ");
            }
            ammos.append("]");
            return ammos.toString();
        }
        else{
            return "Sorry, the player or the ammoBox or the AmmoCubesList are null";
        }
    }

    public static String deathsString(PlayerV playerToShow){
        StringBuilder deaths;
        int point;
        if(playerToShow.isHasFinalFrenzyBoard()){
            deaths = new StringBuilder("DEATHS FF : [");
            point = 2 - (playerToShow.getNumberOfDeaths());
        }
        else {
            deaths = new StringBuilder("DEATHS: [");
            point = 8 - (playerToShow.getNumberOfDeaths()*2);
        }
        for (int i = 0; i < playerToShow.getNumberOfDeaths(); i++) {
            deaths.append(" SKULL ");
        }
        while (point > 0) {
            deaths.append(" ").append(point).append(" ");
            point -= 2;
        }

            deaths.append(" 1  1 ");

        deaths.append("]");
        return deaths.toString();
    }

    public static String damageString(PlayerV playerToShow){
        if(playerToShow!=null && playerToShow.getDamageTracker()!=null && playerToShow.getDamageTracker().getDamageSlotsList()!=null) {
            StringBuilder damage = new StringBuilder("DAMAGE: [");
            int damageSlotNumber = 0;
            for (DamageSlotV d : playerToShow.getDamageTracker().getDamageSlotsList()) {
                if (damageSlotNumber == 0) {
                    damage.append(" (FB)").append(d.getShootingPlayerNickname()).append(" ");
                } else if (damageSlotNumber == 2) {
                    damage.append(" (AG)").append(d.getShootingPlayerNickname()).append(" ");
                } else if (damageSlotNumber == 5) {
                    damage.append(" (AS)").append(d.getShootingPlayerNickname()).append(" ");
                } else if(damageSlotNumber == 10){
                    damage.append(" (K)").append(d.getShootingPlayerNickname()).append(" ");
                }else if(damageSlotNumber == 11){
                    damage.append(" (OK)").append(d.getShootingPlayerNickname()).append(" ");
                } else {
                    damage.append(" ").append(d.getShootingPlayerNickname()).append(" ");
                }
                damageSlotNumber++;
            }
            if(damageSlotNumber<12) {
                for (int i = damageSlotNumber; i < 12; i++) {
                    damage.append(" EMPTY ");
                }
            }
            else{
                damage.append(" ... ");
            }
            damage.append("]");
            return damage.toString();
        }
        else {
            return "Sorry, the player or the damageTracker or the damageSlotList  are null";
        }
    }

    public static String markString(PlayerV playerToShow){
        if(playerToShow!=null && playerToShow.getMarksTracker()!=null && playerToShow.getMarksTracker().getMarkSlotsList()!=null) {
            StringBuilder mark = new StringBuilder("MARKS: [");
            for (MarkSlotV m : playerToShow.getMarksTracker().getMarkSlotsList()) {
                mark.append("  ").append(m.getMarkingPlayer()).append(":").append(m.getQuantity()).append("  ");
            }
            mark.append("]");
            return mark.toString();
        }
        else{
            return "Sorry, the player or the marksTracker or the marksSlotsList are null";
        }
    }



    public static void showMap(){
        if (ViewModelGate.getModel() != null && ViewModelGate.getModel().getBoard() != null && ViewModelGate.getModel().getBoard().getMap() != null) {
            SquareV[][] map = ViewModelGate.getModel().getBoard().getMap();
            List<String>[][] mapCLI = buildEmptyMap();
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    if (map[i][j] == null) {
                        for (int k = 0; k < 12; k++) {
                            mapCLI[i][j].set(k, "MMMMMMMMMMMMMMMMMMMMMMMMMM");
                        }
                    } else {

                        //squareType
                        if (map[i][j].getSquareType().equals(SquareTypes.normal)) {
                            mapCLI[i][j].set(1, "M    " + map[i][j].getColor() + ": NORMAL SQUARE    M");
                        } else {
                            mapCLI[i][j].set(1, "M     " + map[i][j].getColor() + ": SPAWN POINT     M");
                        }

                        //cohordinate
                        mapCLI[i][j].set(2, "M          [" + map[i][j].getX() + "][" + map[i][j].getY() + "]        M");

                        //SQUARE SIDES

                        if (map[i][j].getSide(CardinalPoint.north) == SquareSide.door) {
                            mapCLI[i][j].set(0, "MMMMMMMMMM       MMMMMMMMM");
                        } else if (map[i][j].getSide(CardinalPoint.north) == SquareSide.nothing) {
                            mapCLI[i][j].set(0, "M                        M");
                        }

                        if (map[i][j].getSide(CardinalPoint.south) == SquareSide.door) {
                            mapCLI[i][j].set(11, "MMMMMMMMMM       MMMMMMMMM");
                        } else if (map[i][j].getSide(CardinalPoint.south) == SquareSide.nothing) {
                            mapCLI[i][j].set(11, "M                        M");
                        }

                        if (map[i][j].getSide(CardinalPoint.east) == SquareSide.door) {
                            for (int k = 4; k < 7; k++) {
                                char[] charArray = mapCLI[i][j].get(k).toCharArray();
                                charArray[25] = ' ';
                                mapCLI[i][j].set(k, new String(charArray));
                            }
                        } else if (map[i][j].getSide(CardinalPoint.east) == SquareSide.nothing) {
                            for (int k = 1; k < 11; k++) {
                                char[] charArray = mapCLI[i][j].get(k).toCharArray();
                                charArray[25] = ' ';
                                mapCLI[i][j].set(k, new String(charArray));
                            }
                        }

                        if (map[i][j].getSide(CardinalPoint.west) == SquareSide.door) {
                            for (int k = 4; k < 7; k++) {
                                char[] charArray = mapCLI[i][j].get(k).toCharArray();
                                charArray[0] = ' ';
                                mapCLI[i][j].set(k, new String(charArray));
                            }
                        } else if (map[i][j].getSide(CardinalPoint.west) == SquareSide.nothing) {
                            for (int k = 1; k < 11; k++) {
                                char[] charArray = mapCLI[i][j].get(k).toCharArray();
                                charArray[0] = ' ';
                                mapCLI[i][j].set(k, new String(charArray));
                            }
                        }

                        //PLAYERS
                        if (ViewModelGate.getModel().getPlayers() != null && ViewModelGate.getModel().getPlayers().getPlayers() != null) {
                            for (int k = 0; k < ViewModelGate.getModel().getPlayers().getPlayers().size(); k++) {
                                PlayerV p = ViewModelGate.getModel().getPlayers().getPlayers().get(k);
                                if(p.getX()!= null &&p.getY()!=null) {
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
            }

            List<String> mapStringIntermediate = new ArrayList<>();
            for (int i = 0; i < 3; i++) {//currentSquareRow
                for (int j = 0; j < 12; j++) { //currentLine
                    StringBuilder line = new StringBuilder();
                    for (int k = 0; k < 4; k++) { //currentSquare
                        line.append(mapCLI[i][k].get(j));
                    }
                    line.append("\n");
                    mapStringIntermediate.add(line.toString());
                }
            }

            StringBuilder mapStringFinal = new StringBuilder();
            for (String s : mapStringIntermediate) {
                mapStringFinal.append(s);
            }
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("\n<<<<<<<<<<<< MAP: " + ViewModelGate.getModel().getBoard().getChosenMap() + " >>>>>>>>>>>>");
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface(mapStringFinal.toString());
        }
        else{
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("Sorry, the model or the Board or the matrix of squares are null");
        }
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


