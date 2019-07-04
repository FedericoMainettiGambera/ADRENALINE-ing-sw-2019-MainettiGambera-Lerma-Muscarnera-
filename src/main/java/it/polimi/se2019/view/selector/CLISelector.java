package it.polimi.se2019.view.selector;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.enumerations.PlayersColors;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPaymentInformation;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPlayers;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPositions;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPowerUpCards;
import it.polimi.se2019.model.events.viewControllerEvents.*;
import it.polimi.se2019.view.components.*;

import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CLISelector implements SelectorV {

    private static final Logger logger=Logger.getLogger(CLISelector.class.getName());

    private String networkConnection;

    private static PrintWriter out= new PrintWriter(System.out, true);

    public CLISelector(String networkConnection){
        this.networkConnection = networkConnection;
    }

    public static void showListOfRequests(List<String> requests){

        int maxLenght = 10;

        for (String request : requests) {
            if (request.length() + 3 > maxLenght) {
                maxLenght = request.length() + 3;
            }
        }

        StringBuilder s;

        s = new StringBuilder("           .-.---");
        for (int i = 0; i < maxLenght ; i++) {
            s.append("-");
        }
        s.append("---.-.\n");
        
        s.append("          ((o))  ");
        for (int i = 0; i < maxLenght; i++) {
            s.append(" ");
        }
        s.append("      )\n");

        Random rand = new Random();
        s.append("           \\U/___");
        for (int i = 0; i < maxLenght; i++) {
            if(rand.nextInt(3) < 2) {
                s.append("_");
            }
            else{
                s.append(" ");
            }
        }
        s.append(" ____/\n");

        s.append("             |   ");
        for (int i = 0; i < maxLenght; i++) {
            s.append(" ");
        }
        s.append("    |\n");


        for (int i = 0; i < requests.size(); i++) {
            s.append("             |   ");
            s.append(i).append(": ");
            s.append(requests.get(i));
            for (int j = 0; j < maxLenght-requests.get(i).length()-3; j++) {
                s.append(" ");
            }
            s.append("    |\n");
        }

        s.append("             |____");
        for (int i = 0; i < maxLenght; i++) {
            if(rand.nextInt(3) < 2) {
                s.append("_");
            }
            else{
                s.append(" ");
            }
        }
        s.append("___|\n");

        s.append("            /A\\  ");
        for (int i = 0; i < maxLenght; i++) {
            s.append(" ");
        }
        s.append("     \\\n");

        s.append("           ((o)) ");
        for (int i = 0; i < maxLenght; i++) {
            s.append(" ");
        }
        s.append("      )\n");

        s.append("            '-'--");
        for (int i = 0; i < maxLenght ; i++) {
            s.append("-");
        }
        s.append("-----'\n");
        System.out.println(s);
    }


    public static int askNumber(int rangeInit, int rangeEnd){
        if(Controller.isRandomGame()){
            try {
                TimeUnit.MILLISECONDS.sleep(400);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "EXCEPTION", e);
                Thread.currentThread().interrupt();
            }
            Random rand = new Random();
            int random =  rand.nextInt(rangeEnd+1);
            System.out.println("____________________________________________ANSWER: " + random + "\n");
            return random;
        }
        Scanner sc = new Scanner(System.in);
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.println("<CLIENT> please, make sure to insert a number;");
        }
        int choice = sc.nextInt();
        while(choice<rangeInit || choice>rangeEnd){
            System.out.println("<CLIENT> please, insert a number from " + rangeInit + " to " + rangeEnd);
            while (!sc.hasNextInt()) {
                sc.next();
                System.out.println("<CLIENT> please, make sure to insert a number from " + rangeInit + " to " + rangeEnd);
            }
            choice = sc.nextInt();
        }
        return choice;
    }



    /**implements a dedicated thread that manages to ask the user
     *the information needed to set up the game as they desire
     * collects the answer
     * and sends it to the server using a specific event*/
    private class AskGameSetUp extends Thread{
        private boolean canBot;

        public AskGameSetUp(boolean canBot){
            this.canBot=canBot;
        }

        @Override
        public void run(){
            String gameMode;
            String mapChoice;
            int numberOfStartingSkulls;
            boolean isFinalFrenzy;
            boolean isBotActive;

            gameMode = "normalMode";

            System.out.println("\n<CLIENT> : Choose Map:");
            CLISelector.showListOfRequests(Arrays.asList("map 0","map 1", "map 2", "map 3"));

            int map = askNumber(0,3);
            if(map == 0){
                mapChoice = "map0";
            }
            else if(map == 1){
                mapChoice = "map1";
            }
            else if(map == 2){
                mapChoice = "map2";
            }
            else{
                mapChoice = "map3";
            }

            out.println("\n<CLIENT> : Do you want to play with Final frenzy active?");
            CLISelector.showListOfRequests(Arrays.asList("Yes","No"));
            int FF = askNumber(0,1);
            if(FF==0){
                isFinalFrenzy=true;
            }
            else{
                isFinalFrenzy=false;
            }



            if(canBot) {
                System.out.println("\n<CLIENT> : Do you want to play with the Terminator active?");
                CLISelector.showListOfRequests(Arrays.asList("Yes", "No"));
                int terminator = askNumber(0, 1);
                isBotActive = terminator == 0;
            }
            else {
                isBotActive=false;
            }



            System.out.println("\n<CLIENT> : Choose number of starting skulls:");
            CLISelector.showListOfRequests(Arrays.asList("5 starting skulls","6 starting skulls","7 starting skulls","8 starting skulls"));
            numberOfStartingSkulls = askNumber(0,3);
            if(numberOfStartingSkulls==0){
                numberOfStartingSkulls = 5;
            }
            else if(numberOfStartingSkulls==1){
                numberOfStartingSkulls = 6;
            }
            else if(numberOfStartingSkulls==2){
                numberOfStartingSkulls = 7;
            }
            else{
                numberOfStartingSkulls = 8;
            }


            ViewControllerEventGameSetUp viewControllerEventGameSetUp = new ViewControllerEventGameSetUp(gameMode,mapChoice,numberOfStartingSkulls,isFinalFrenzy,isBotActive);

            ViewSelector.sendToServer(viewControllerEventGameSetUp);
        }
    }

    /**starts a dedicated thread that manages to ask the user
     * the information needed to set up the game as they desire*/
    @Override
    public void askGameSetUp(boolean canBot) {
        AskGameSetUp askGameSetUp = new AskGameSetUp(canBot);
        askGameSetUp.start();
    }


    /**@deprecated  */
    @Deprecated
    private class AskPlayerSetUp extends Thread {

        @Deprecated
        @Override
        public void run() {
            String nickaname = "defaultUserName";
            PlayersColors color = PlayersColors.purple;

            out.println("<CLIENT> Insert your nickname: ");
            Scanner br = new Scanner(System.in);
            nickaname = br.nextLine();

            ViewModelGate.setMe(nickaname);
            out.println("<CLIENT> \"ME\" is: " + nickaname);

            out.println("<CLIENT> ChooseColor: \n" +
                    "    blue,\n" +
                    "    yellow,\n" +
                    "    gray,\n" +
                    "    green,\n" +
                    "    purple");
            String colorChosen = br.nextLine();
            switch (colorChosen) {
                case "blue":
                    color = PlayersColors.blue;
                    break;
                case "yellow":
                    color = PlayersColors.yellow;
                    break;
                case "gray":
                    color = PlayersColors.gray;
                    break;
                case "green":
                    color = PlayersColors.green;
                    break;
                case "purple":
                    color = PlayersColors.purple;
                    break;

                    default: break;
            }

            ViewControllerEventPlayerSetUp viewControllerEventPlayerSetUp = new ViewControllerEventPlayerSetUp(nickaname, color);
            ViewSelector.sendToServer(viewControllerEventPlayerSetUp);
        }
    }
    /**@deprecated  */
    @Deprecated
    @Override
    public void askPlayerSetUp() {
        AskPlayerSetUp aps = new AskPlayerSetUp();
        aps.start();
    }





    /**implements a dedicated thread that manages to ask the user
     *where they want to spawn, on the base of the color of the power up discarded, on their very first turn
     * collects the answer
     * and sends it to the server using a specific event*/
    private class AskFirstSpawnPosition extends Thread {
        private List<PowerUpCardV> powerUpCards;
        private boolean spawnBot;
        public  AskFirstSpawnPosition(List<PowerUpCardV> powerUpCards, boolean spawnBot){
            this.powerUpCards = powerUpCards;
            this.spawnBot=spawnBot;
        }
        @Override
        public void run() {
            String botSpawn = "none";
            if(spawnBot) {
                out.println("\n<CLIENT> choose the color of the SpawnPoint where you want the bot to spawn: ");
                CLISelector.showListOfRequests(Arrays.asList("red", "blue", "yellow"));
                int spawnColorChoice = askNumber(0, 2);
                if(spawnColorChoice == 0){
                    botSpawn="red";
                }
                else if(spawnColorChoice == 1){
                    botSpawn="blue";
                }
                else{
                    botSpawn="yellow";
                }
            }

            out.println("\n<CLIENT> choose the PowerUp to discard and spawn to: ");
            ArrayList<String> requests = new ArrayList<>();
            for (PowerUpCardV powerUpCard : powerUpCards) {
                requests.add(powerUpCard.getName() + "       COLOR: " + powerUpCard.getColor());
            }
            CLISelector.showListOfRequests(requests);
            int choice = askNumber(0,powerUpCards.size()-1);

            String cardID = powerUpCards.get(choice).getID();

            ViewControllerEventTwoString viewControllerEventTwoString = new ViewControllerEventTwoString(cardID, botSpawn);

            ViewSelector.sendToServer(viewControllerEventTwoString);
        }
    }

    /**starts a dedicated thread that manages to ask the user
     * where they want to spawn, on the base of the color of the power up discarded, on their very first turn */
    @Override
    public void askFirstSpawnPosition(List<PowerUpCardV> powerUpCards, boolean spawnBot) {
        AskFirstSpawnPosition afsp = new AskFirstSpawnPosition(powerUpCards, spawnBot);
        afsp.start();
    }




    private class AskTurnAction extends Thread {
        private int actionNumber;
        private boolean canUsePowerUp;

        private boolean canUseBot;

        /**implements a dedicated thread that manages to ask the user
         *what they want to do between the action available during their turn
         * collects the answer
         * and sends it to the server using a specific event*/
        public AskTurnAction(int actionNumber, boolean canUsePowerUp, boolean canUseBot){
            this.actionNumber = actionNumber;
            this.canUsePowerUp = canUsePowerUp;
            this.canUseBot = canUseBot;
        }
        @Override
        public void run() {
            if(actionNumber == 1) {
                out.println("\n<CLIENT> Choose your first action");
            }
            else{
                out.println("\n<CLIENT> Choose your second action");
            }

            List<String> requests = new ArrayList<>(Arrays.asList("run around", "grab stuff", "shoot people"));
            if(canUseBot) {
                requests.add("use Bot");
            }
            if(canUsePowerUp) {
                requests.add("use power up");
            }

            CLISelector.showListOfRequests(requests);
            int choice = askNumber(0,requests.size()-1);

            String action = requests.get(choice);

            ViewControllerEventString viewControllerEventString = new ViewControllerEventString(action);

            ViewSelector.sendToServer(viewControllerEventString);
        }
    }

    /**starts a dedicated thread that manages to ask the user
     * what they want to do between the option available during their turn */
    @Override
    public void askTurnAction(int actionNumber, boolean canUsePowerUp, boolean canUseBot) {
        AskTurnAction askTurnAction = new AskTurnAction(actionNumber, canUsePowerUp, canUseBot);
        askTurnAction.start();
    }


    /**implements a dedicated thread that manages to ask the user
     *where they want the bot to be moved
     * collects the answer
     * and sends it to the server using a specific event*/
    private class AskBotMove extends Thread{
        private List<Position> possiblePositions;
        public AskBotMove(List<Position> possiblePositions){
            this.possiblePositions = possiblePositions;
        }
        @Override
        public void run(){
            out.println("<CLIENT> choose where you want to move the Bot:");

            ArrayList<String> requests = new ArrayList<>();
            for (Position pos : possiblePositions) {
                requests.add("[" + pos.getX() + "][" + pos.getY() + "]");
            }
            CLISelector.showListOfRequests(requests);

            int chosenPosition = askNumber(0,possiblePositions.size()-1);

            ViewControllerEventPosition viewControllerEventPosition = new ViewControllerEventPosition(possiblePositions.get(chosenPosition).getX(),possiblePositions.get(chosenPosition).getY());

            ViewSelector.sendToServer(viewControllerEventPosition);
        }
    }

    /**starts a dedicated thread that manages to ask the user
     * where they want the bot to be moved*/
    @Override
    public void askBotMove(SelectorEventPositions selectorEventPositions) {
        AskBotMove abm = new AskBotMove(selectorEventPositions.getPositions());
        abm.start();
    }


    /**implements a dedicated thread that manages to ask the user
     *where they want to move
     * collects the answer
     * and sends it to the server using a specific event*/
    private class AskRunAroundPosition extends Thread {
        private List<Position> positions;
        public AskRunAroundPosition(List<Position> positions){
            this.positions = positions;
        }
        @Override
        public void run() {
            out.println("\n<CLIENT> choose where to move: ");

            ArrayList<String> requests = new ArrayList<>();
            for (Position pos : positions) {
                requests.add("[" + pos.getX() + "][" + pos.getY() + "]");
            }
            CLISelector.showListOfRequests(requests);

            int choosenPosition = askNumber(0,positions.size()-1);

            ViewControllerEventPosition viewControllerEventPosition = new ViewControllerEventPosition(positions.get(choosenPosition).getX(),positions.get(choosenPosition).getY());

            ViewSelector.sendToServer(viewControllerEventPosition);
        }
    }
    @Override
    public void askRunAroundPosition(List<Position> positions) {
        AskRunAroundPosition arap = new AskRunAroundPosition(positions);
        arap.start();
    }

    /**starts a dedicated thread that manages to ask the user
     * where they want to go to grab*/
    @Override
    public void askGrabStuffMove(List<Position> positions) {
        AskRunAroundPosition arap = new AskRunAroundPosition(positions);
        arap.start();
    }

    /**implements a dedicated thread that manages to ask the user
     *if they want to grab where they are or move and grab somewhere else
     * collects the answer
     * and sends it to the server using a specific event*/
    private class AskGrabSuffAction extends Thread {
        @Override
        public void run() {
            out.println("\n<CLIENT> What do you want to do?");
            CLISelector.showListOfRequests(Arrays.asList("move to another position and grab there","grab where you are without moving"));

            int chosenAction = askNumber(0,1);

            String action;
            if(chosenAction == 0){
                action = "move";
            }
            else{
                action = "grab";
            }

            ViewControllerEventString viewControllerEventString = new ViewControllerEventString(action);

            ViewSelector.sendToServer(viewControllerEventString);
        }
    }


    /**starts a dedicated thread that manages to ask the user
     * if they want to grab where they are or move and grab somewhere else*/
    @Override
    public void askGrabStuffAction() {
        AskGrabSuffAction agsa = new AskGrabSuffAction();
        agsa.start();
    }




    /**implements a dedicated thread that manages to ask the user
     * which weapon they want to pick up, if they can pick up any,
     * collects the answer
     * and sends it to the server using a specific event*/
    private class AskGrabStuffGrabWeapon extends Thread {
        private List<WeaponCardV> toPickUp;
        public AskGrabStuffGrabWeapon(List<WeaponCardV> toPickUp){
            this.toPickUp = toPickUp;
        }
        @Override
        public void run() {
            if(toPickUp.isEmpty()){
                out.println("\n<CLIENT>you can't pick up any card.");
                return;
            }

            out.println("\n<CLIENT>Choose what to pick up:");
            ArrayList<String> requests = new ArrayList<>();
            for (WeaponCardV weaponCardV : toPickUp) {
                if (weaponCardV.getPickUpCost().getAmmoCubesList().isEmpty()) {
                    requests.add(weaponCardV.getName() + ": FREE");
                } else {
                    requests.add(weaponCardV.getName() + ": " + weaponCardV.getPickUpCost());
                }
            }
            CLISelector.showListOfRequests(requests);

            int toPickUpchosen = askNumber(0, toPickUp.size()-1);

            String toPickUpID = toPickUp.get(toPickUpchosen).getID();

            ViewControllerEventString viewControllerEventString = new ViewControllerEventString(toPickUpID);

            ViewSelector.sendToServer(viewControllerEventString);
        }
    }

    /**starts a dedicated thread that manages to ask the user
     * which weapon they want to pick up, if they can pick up any*/
    @Override
    public void askGrabStuffGrabWeapon(List<WeaponCardV> toPickUp) {
        AskGrabStuffGrabWeapon agsg = new AskGrabStuffGrabWeapon(toPickUp);
        agsg.start();
    }




    /**implements a dedicated thread that manages to ask the user
     * which weapon they want to discard and which they want to collect
     * collects the answer
     * and sends it to the server using a specific event*/
    private class AskGrabStuffSwitchWeapon extends Thread {
        private List<WeaponCardV> toPickUp;
        private List<WeaponCardV> toSwitch;
        public AskGrabStuffSwitchWeapon(List<WeaponCardV> toPickUp, List<WeaponCardV> toSwitch){
            this.toPickUp = toPickUp;
            this.toSwitch = toSwitch;
        }

        @Override
        public void run() {
            if(toPickUp.isEmpty()){
                out.println("\n<CLIENT>you can't pick up any card.");
                return;
            }

            String free= ": free";

            out.println("\n<CLIENT>you already have the maximum quantity of weapon you can hold.");
            out.println("<CLIENT>Choose one to pick up and one to discard.");
            out.println("<CLIENT>To pick up:");
            ArrayList<String> requests = new ArrayList<>();
            for (WeaponCardV weaponCardV : toPickUp) {
                if (weaponCardV.getPickUpCost() == null) {
                    requests.add(weaponCardV.getName() + free);
                } else {
                    requests.add(weaponCardV.getName() + ": " + weaponCardV.getPickUpCost().toString());
                }
            }
            CLISelector.showListOfRequests(requests);

            int chosenToPickUp = askNumber(0,toPickUp.size()-1);

            String toPickUpID = toPickUp.get(chosenToPickUp).getID();

            requests.clear();
            out.println("<CLIENT>Switch with:");
            for (WeaponCardV toSwitch1 : toSwitch) {
                if (toSwitch1.getPickUpCost() == null) {
                    requests.add(toSwitch1.getName() + free);
                } else {
                    requests.add(toSwitch1.getName() + ": " + toSwitch1.getPickUpCost().toString());
                }
            }
            CLISelector.showListOfRequests(requests);

            int chosenToDiscard= askNumber(0,toSwitch.size()-1);

            String toDiscardID = toSwitch.get(chosenToDiscard).getID();

            ViewControllerEventTwoString viewControllerEventTwoString = new ViewControllerEventTwoString(toPickUpID, toDiscardID);

            ViewSelector.sendToServer(viewControllerEventTwoString);
        }
    }

    /**starts a dedicated thread that manages to ask the user
     * to swap one of their weapon with one to collect*/
    @Override
    public void askGrabStuffSwitchWeapon(List<WeaponCardV> toPickUp, List<WeaponCardV> toSwitch) {
        AskGrabStuffSwitchWeapon agssw = new AskGrabStuffSwitchWeapon(toPickUp, toSwitch);
        agssw.start();
    }



    /**implements a dedicated thread that manages to ask the user
     * which power up they want to discard
     * collects the answer
     * and sends it to the server using a specific event*/
    private class AskPowerUpToDiscard extends Thread {
        private List<PowerUpCardV> toDiscard;
        private AskPowerUpToDiscard(List<PowerUpCardV> toDiscard){
            this.toDiscard = toDiscard;
        }
        @Override
        public void run() {
            out.println("\n<CLIENT>You have too many power up in hand. You need to discard one:");
            ArrayList<String> requests = new ArrayList<>();
            for (PowerUpCardV powerUpCardV : toDiscard) {
                requests.add(powerUpCardV.getName() + ": " + powerUpCardV.getColor());
            }
            CLISelector.showListOfRequests(requests);

            int chosen = askNumber(0,toDiscard.size()-1);

            ViewControllerEventInt viewControllerEventInt = new ViewControllerEventInt(chosen);

            ViewSelector.sendToServer(viewControllerEventInt);
        }
    }

    /**starts a dedicated thread that manages to ask the user
     * which power up they want to discard*/
    @Override
    public void askPowerUpToDiscard(List<PowerUpCardV> toDiscard) {
        AskPowerUpToDiscard aputd = new AskPowerUpToDiscard(toDiscard);
        aputd.start();
    }




    /**implements a dedicated thread that manages to ask the user
     * which weapon they want to reload
     * collects the answer
     * and sends it to the server using a specific event*/
    private class AskWhatReaload extends Thread {
        private List<WeaponCardV> toReload;
        public AskWhatReaload(List<WeaponCardV> toReload){
            this.toReload = toReload;
        }
        @Override
        public void run() {
            out.println("\n<CLIENT> Which weapon do you want to reload?");
            ArrayList<String> requests = new ArrayList<>();
            requests.add("skip reload");
            for (WeaponCardV weaponCardV : toReload) {
                if (weaponCardV.getPickUpCost() == null) {
                    requests.add(weaponCardV.getName() + ": free");
                } else {
                    requests.add(weaponCardV.getName() + ": " + weaponCardV.getPickUpCost().toString());
                }
            }
            CLISelector.showListOfRequests(requests);
            int chosen = askNumber(0, toReload.size());

            ViewControllerEventString viewControllerEventString =null;
            if(chosen==0){
                viewControllerEventString= new ViewControllerEventString("SKIP");
            }
            else{
                String chosenID = toReload.get(chosen-1).getID();
                viewControllerEventString = new ViewControllerEventString(chosenID);
            }

            ViewSelector.sendToServer(viewControllerEventString);
        }
    }

    /**starts a dedicated thread that manages to ask the user
     *  which weapon they want to reload*/
    @Override
    public void askWhatReaload(List<WeaponCardV> toReload) {
        AskWhatReaload awr = new AskWhatReaload(toReload);
        awr.start();
    }

    /** implements a dedicated thread that manages to ask the user where they want to spawn
     * on the basis of the power up card he chooses to discard
     * collects the answer
     * sends it to the server
     * */
    private class AskSpawn extends Thread {
        private List<PowerUpCardV> powerUpCards;
        public AskSpawn(List<PowerUpCardV> powerUpCards){
            this.powerUpCards = powerUpCards;
        }
        @Override
        public void run() {
            out.println("<CLIENT> choose what power up to discard and spawn to: ");
            List<String> requests = new ArrayList<>();
            for (PowerUpCardV p : powerUpCards) {
                requests.add(p.getName() + "    COLOR: " + p.getColor());
            }
            showListOfRequests(requests);
            int choice = askNumber(0,requests.size()-1);
            String chosenId = powerUpCards.get(choice).getID();
            ViewControllerEventString viewControllerEventString = new ViewControllerEventString(chosenId);
            ViewSelector.sendToServer(viewControllerEventString);
        }
    }

    /**starts a dedicated thread that manages to ask the user where he wants to spawn on the basis of the power up card he chooses to discard*/
    @Override
    public void askSpawn(List<PowerUpCardV> powerUpCards) {
        AskSpawn as = new AskSpawn(powerUpCards);
        as.start();
    }




/** implements a dedicated thread which asks the user if he wants to move before shooting or just shooting
 * collects the answer
 * sends it to the server*/
    private class AskShootOrMove extends Thread {
        @Override
        public void run() {
            int numberOfMoves;
            numberOfMoves=1;

            out.println("\n<CLIENT> Do you want to:");
            CLISelector.showListOfRequests(Arrays.asList("move before shooting","stay still and shoot"));

            out.println("remember, you can move up to "+numberOfMoves + " squares");

            int chosen = askNumber(0,1);

            ViewControllerEventString viewControllerEventString;
            if(chosen==0){
                viewControllerEventString= new ViewControllerEventString("move");
            }
            else{
                viewControllerEventString = new ViewControllerEventString("shoot");
            }

            ViewSelector.sendToServer(viewControllerEventString);
        }
    }
    /**starts a dedicated thread which asks the user if he wants to move before shooting or just shooting*/
    @Override
    public void askShootOrMove(){
        AskShootOrMove asom = new AskShootOrMove();
        asom.start();
    }



    /**@deprecated */
    @Deprecated
    private class AskShootReloadMove extends Thread {
        @Deprecated
        @Override
        public void run() {
            //????
            int numberOfMoves;
            numberOfMoves=1;
            Scanner br = new Scanner(System.in);

            out.println("\n<CLIENT> Do you want to:");
            CLISelector.showListOfRequests(Arrays.asList("if you want to move, reload and shoot","if you want to stay still, reload and shot", "TODO !!" ));
            out.println("remember, you can move up to "+numberOfMoves + " squares.");

            int chosen = br.nextInt();
            ViewControllerEventInt viewControllerEventInt = new ViewControllerEventInt(chosen);

            ViewSelector.sendToServer(viewControllerEventInt);
        }
    }

    /**@deprecated */
    @Deprecated
    @Override
    public void askShootReloadMove(){
        AskShootReloadMove asrm = new AskShootReloadMove();
        asrm.start();
    }




    /**implements a thread that manages to show the possible weapon for the user to use
     * collects the answer
     * then sends it to the server using a specific event*/
    private class AskWhatWep extends Thread {
        private List<WeaponCardV> loadedCardInHand;
        public AskWhatWep(List<WeaponCardV> loadedCardInHand){
            this.loadedCardInHand = loadedCardInHand;
        }
        @Override
        public void run() {

            out.println("\n<CLIENT> What weapon do you want to use?");
            ArrayList<String> requests = new ArrayList<>();
            for (WeaponCardV weaponCardV : loadedCardInHand) {
                requests.add(weaponCardV.getName());
            }
            CLISelector.showListOfRequests(requests);

            int chosen = askNumber(0,requests.size()-1);

            ViewControllerEventInt viewControllerEventInt = new ViewControllerEventInt(chosen);

            ViewSelector.sendToServer(viewControllerEventInt);
        }
    }

    /**starts a thread that manages to show the possible weapon for the user to use*/
    @Override
    public void askWhatWep(List<WeaponCardV> loadedCardInHand) {
        AskWhatWep aww = new AskWhatWep(loadedCardInHand);
        aww.start();
    }




    /** implements a thread that manages to show the possible effect of a card
     * then send the answer to the server by using a specific event */
    private class AskWhatEffect extends Thread {
        private List<EffectV> possibleEffects;
        public AskWhatEffect(List<EffectV> possibleEffects){
            this.possibleEffects = possibleEffects;
        }
        @Override
        public void run() {

            System.out.println("\n<CLIENT> What Effect do you want to use?");
            ArrayList<String> requests = new ArrayList<>();
            for (EffectV possibleEffect : possibleEffects) {
                requests.add(possibleEffect.getEffectName());
            }
            CLISelector.showListOfRequests(requests);

            int chosen = askNumber(0,possibleEffects.size()-1);

            ViewControllerEventInt viewControllerEventInt = new ViewControllerEventInt(chosen);

            ViewSelector.sendToServer(viewControllerEventInt);
        }
    }

    /**starts a thread that manages to show the possible effect of a card*/
    @Override
    public void askWhatEffect(List<EffectV> possibleEffects) {
        AskWhatEffect awe = new AskWhatEffect(possibleEffects);
        awe.start();
    }






    /** implements a dedicated thread that takes care of displaying the possible input for a certain inputType
     * then sends the answer to the server*/
    private class AskEffectInputs extends Thread{
        private EffectInfoType infoType;
        private List<Object> possibleInputs;
        public AskEffectInputs(EffectInfoType inputType, List<Object> possibleInputs){
            this.infoType=inputType;
            this.possibleInputs = possibleInputs;
        }
        @Override
        public void run(){
            out.println("<CLIENT> " + "requested input type: " + infoType);

            int request = howManyRequest();

            boolean firstRequest = true;

            List<Object> answer = new ArrayList<>();

            for (int j = 0; j < request ; j++) {

                ArrayList<String> requestsString = new ArrayList<>();

                if(possibleInputs.isEmpty()){
                    break;
                }
                if(!firstRequest) {
                    if (request >= 2) {
                        out.println("Do you want to chose another one?");
                        CLISelector.showListOfRequests(Arrays.asList("Yes", "No"));
                        int choice = askNumber(0, 1);
                        if (choice == 1) {
                            break;
                        }
                    }
                }
                else{
                    firstRequest = false;
                }

                if(possibleInputs.get(0).getClass().toString().contains("PlayerV")){
                    out.println("<Client> please choose one of the following players: ");
                    PlayerV p;
                    for (Object possibleInput : possibleInputs) {
                        p = (PlayerV) possibleInput;
                        requestsString.add(p.getNickname());
                    }
                }
                else{
                    out.println("<Client> please choose one of the following squares: ");
                    SquareV s;
                    for (Object possibleInput : possibleInputs) {
                        s = (SquareV) possibleInput;
                        requestsString.add("[" + s.getX() + "][" + s.getY() + "]");
                    }
                }
                CLISelector.showListOfRequests(requestsString);

                int chosen = askNumber(0,possibleInputs.size()-1);

                answer.add(possibleInputs.get(chosen));

                possibleInputs.remove(chosen);
            }
            ViewControllerEventListOfObject viewControllerEventListOfObject = new ViewControllerEventListOfObject(answer);

            ViewSelector.sendToServer(viewControllerEventListOfObject);
        }

        private int howManyRequest(){
            if(this.infoType.equals(EffectInfoType.twoTargets)){
                return 2;
            }
            else if(this.infoType.equals(EffectInfoType.threeTargets)){
                return 3;
            }
            /*else if(this.infoType.equals(EffectInfoType.singleTarget) ||
                this.infoType.equals(EffectInfoType.squareByTarget)||
                this.infoType.equals(EffectInfoType.simpleSquareSelect)||
                this.infoType.equals(EffectInfoType.squareByLastTargetSelected)||
                this.infoType.equals(EffectInfoType.targetListBySquareOfLastTarget)||
                this.infoType.equals(EffectInfoType.targetListByDistance1)||
                this.infoType.equals(EffectInfoType.singleTargetBySquare)||
                this.infoType.equals(EffectInfoType.targetListBySquare)||
                this.infoType.equals(EffectInfoType.targetListByRoom)||
                this.infoType.equals(EffectInfoType.singleTargetBySameSquareOfPlayer)||
                this.infoType.equals(EffectInfoType.singleTargetByCardinalDirection)||
                this.infoType.equals(EffectInfoType.twoTargetsByCardinalDirection)||
                this.infoType.equals(EffectInfoType.targetListByCardinalDirection)){*/
            else {
                return 1;
            }
        }
    }
    /**starts a dedicated thread that takes care of displaying the possible input for a certain inputType*/
    @Override
    public void askEffectInputs(EffectInfoType inputType, List<Object> possibleInputs) {
        AskEffectInputs aei = new AskEffectInputs(inputType, possibleInputs);
        aei.start();
    }



   /** takes care of reconnection events asking for a nickname between the ones with which it's possible to effectuate a reconnection
    * collect the needed information
    * then send it to the server using a specific event*/
    @Override
    public void askReconnectionNickname(ReconnectionEvent reconnectionEvent) {
        out.println("\nAFK Players are: ");
        ArrayList<String> requests = new ArrayList<>(reconnectionEvent.getListOfAFKPlayers());
        CLISelector.showListOfRequests(requests);
        out.println("What was your nickname?");

        int choice = askNumber(0,reconnectionEvent.getListOfAFKPlayers().size()-1);
        ArrayList<String> answer = new ArrayList<>();
        answer.add(reconnectionEvent.getListOfAFKPlayers().get(choice));

        out.println("<CLIENT> setting \"me\": " + answer.get(0));
        ViewModelGate.setMe(answer.get(0));

        answer.add(this.networkConnection);
        ReconnectionEvent reconnectEvent = new ReconnectionEvent(answer);
        if(networkConnection.equalsIgnoreCase("RMI")){
            reconnectEvent.setClient(Controller.getRmiNetworkHandler());
        }
        ViewSelector.sendToServer(reconnectEvent);
    }



    /**implements a dedicated thread that manage to ask the user the nickname he wants to use and
     * to check whether the said nickname is available or not
     * then send the answer to the server*/
    private class AskNickaname extends Thread{
        @Override
        public void run() {
            Scanner br = new Scanner(System.in);
            out.println("<CLIENT> I'm sorry but the nickname chosen was already taken, please insert a new one:");
            String newNickname = br.nextLine();
            ViewModelGate.setMe(newNickname);
            out.println("<CLIENT> informing the server of your new nickname.");
            ViewSelector.sendToServer(new ViewControllerEventNickname(ViewModelGate.getMe()));
        }
    }


    /**starts a dedicated thread that manages to ask the user the nickname they want to use and
     * to check whether the said nickname is available or not*/
    private boolean nicknameIsAvailable = true;
    @Override
    public void askNickname() {
        if(nicknameIsAvailable) {
            out.println("<CLIENT> informing the server of your nickname");
            ViewSelector.sendToServer(new ViewControllerEventNickname(ViewModelGate.getMe()));
            nicknameIsAvailable = false;
        }
        else{
            AskNickaname askNickaname = new AskNickaname();
            askNickaname.start();
        }
    }



    /**implements a thread that manages to ask the user how they want to pay an item, then collect the answer and send it to the server*/
    private class AskPaymentInformation extends Thread{
        private SelectorEventPaymentInformation selectorEventPaymentInformation;
        public AskPaymentInformation(SelectorEventPaymentInformation selectorEventPaymentInformation){
            this.selectorEventPaymentInformation = selectorEventPaymentInformation;
        }
        @Override
        public void run() {
            ArrayList<String> request = new ArrayList<>();
            ArrayList<Object> answer = new ArrayList<>();
            List<AmmoCubesV> amountToPay = selectorEventPaymentInformation.getAmount().getAmmoCubesList();

            while(true) {
                out.println("<CLIENT> you have to pay the following amount of ammo: ");

                for (AmmoCubesV a : amountToPay) {
                    out.println("         " + a.getColor() + " --> " + a.getQuantity());
                }

                if (selectorEventPaymentInformation.canPayWithoutPowerUps()) {
                    request.add("pay the amount with ammos from the Ammo box");
                }

                for (PowerUpCardV p : selectorEventPaymentInformation.getPossibilities()) {
                    request.add("discard " + p.getName() + " for getting off a " + p.getColor() + " ammo from the total cost");
                }

                showListOfRequests(request);
                int choice = askNumber(0, request.size()-1);

                if (choice == 0 && selectorEventPaymentInformation.canPayWithoutPowerUps()) {
                    out.println("<CLIENT> you decided to pay the amount with your ammos from the Ammo Box");
                    break;
                }
                else{
                    //color of the power up chosen
                    AmmoCubesColor colorOfTheChosenPowerUp;
                    if(selectorEventPaymentInformation.canPayWithoutPowerUps()) {
                        colorOfTheChosenPowerUp = selectorEventPaymentInformation.getPossibilities().get(choice - 1).getColor();
                    }
                    else{
                        colorOfTheChosenPowerUp = selectorEventPaymentInformation.getPossibilities().get(choice).getColor();
                    }

                    //add the chosen power up to the answer
                    if(selectorEventPaymentInformation.canPayWithoutPowerUps()) {
                        answer.add(selectorEventPaymentInformation.getPossibilities().get(choice - 1));
                    }
                    else{
                        answer.add(selectorEventPaymentInformation.getPossibilities().get(choice));
                    }

                    //subtract one unit of the color of the power up chosen from the total cost to pay
                    for (AmmoCubesV ammo: amountToPay) {
                        if(ammo.getColor().equals(colorOfTheChosenPowerUp)){
                            ammo.setQuantity(ammo.getQuantity()-1);
                            if(ammo.getQuantity()<=0){
                                //remove the color from the amount topay
                                amountToPay.remove(ammo);

                                //delete All the power up card with the same color as the one of the power up chosen from the possibilities
                                selectorEventPaymentInformation.getPossibilities().removeIf(element -> element.getColor().equals(colorOfTheChosenPowerUp));

                            }
                            break;
                        }
                    }

                }

                request = new ArrayList<>();

                if(selectorEventPaymentInformation.getPossibilities().isEmpty()){ //gli rimane solo la possibilità di pagare con la ammo box
                    break;
                }
                if(amountToPay.isEmpty()){ //ha finito di pagare
                    break;
                }
            }

            ViewControllerEventPaymentInformation viewControllerEventPaymentInformation = new ViewControllerEventPaymentInformation(answer);
            ViewSelector.sendToServer(viewControllerEventPaymentInformation);

        }
    }

    /**starts a dedicated thread that manages to ask how you want to complete your payment process*/
    @Override
    public void askPaymentInformation(SelectorEventPaymentInformation selectorEventPaymentInformation) {
        AskPaymentInformation api = new AskPaymentInformation(selectorEventPaymentInformation);
        api.start();
    }


    /**once the user choose they want to use a power up, this class manages to ask them which one they want to use between the ones they are
     * holding in their hand, then send the answer to server using a specific event
     * */
    private class AskPowerUpToUse extends Thread{
        private List<PowerUpCardV> powerUpCardV;
        public AskPowerUpToUse(List<PowerUpCardV> powerUpCards){
            out.println("<CLIENT> choose one of the following power up to use: ");
            this.powerUpCardV = powerUpCards;
        }

        @Override
        public void run() {
            ArrayList<String> request = new ArrayList<>();
            for (PowerUpCardV p : powerUpCardV) {
                request.add(p.getName() + " of color " + p.getColor());
            }
            showListOfRequests(request);
            int choice = askNumber(0,powerUpCardV.size()-1);
            String answer1 = powerUpCardV.get(choice).getName();
            String answer2 = powerUpCardV.get(choice).getColor() +"";
            out.println("<CLIENT> your choice is: " + answer1 + " (" + answer2 + ")");
            ViewControllerEventTwoString viewControllerEventTwoString = new ViewControllerEventTwoString(answer1, answer2);
            ViewSelector.sendToServer(viewControllerEventTwoString);
        }
    }


    /** starts a dedicated thread that manages to ask the user whether he wants to use a power up or not */
    @Override
    public void askPowerUpToUse(SelectorEventPowerUpCards powerUpCards) {
        AskPowerUpToUse aputu = new AskPowerUpToUse(powerUpCards.getPowerUpCards());
        aputu.start();
    }



    /**implements a dedicated thread that manages to ask the user whether he wants to use a power up or not,
     * then send the answer to the server using a specific event*/
    private class WantToUsePowerUpOrNot extends Thread{
        @Override
        public void run(){
            out.println("<CLIENT> Do you want to use a power up?");
            CLISelector.showListOfRequests(Arrays.asList("yes", "no"));
            int choice = askNumber(0,1);
            if(choice == 0){
                ViewSelector.sendToServer(new ViewControllerEventBoolean(true));
            }
            else{
                ViewSelector.sendToServer(new ViewControllerEventBoolean(false));
            }
        }
    }
    @Override
    public void wantToUsePowerUpOrNot() {
        WantToUsePowerUpOrNot wtupon = new WantToUsePowerUpOrNot();
        wtupon.start();
    }



    /** implements a dedicated thread that manages to ask the user whom he wants the bot to shoot
    * then send the answer to the server using a specific event */
    private class AskBotShoot extends Thread{
        private List<PlayerV> playerVList;

        public AskBotShoot(List<PlayerV> playersVList){
            this.playerVList=playersVList;
        }

        @Override
        public void run(){

            List<String> requests=new ArrayList<>();
            out.println("Who do you want the bot to shoot?");
            for (PlayerV p: playerVList){
                requests.add(p.getNickname());
            }
            CLISelector.showListOfRequests(requests);
            int choice=askNumber(0,requests.size()-1);
            ViewControllerEventString viewControllerEventString=new ViewControllerEventString(requests.get(choice));
            ViewSelector.sendToServer(viewControllerEventString);
        }
    }

    /** starts a dedicated thread that manages to ask the user whom he wants the bot to shoot */
    @Override
    public void askBotShoot(SelectorEventPlayers selectorEventPlayers){
        AskBotShoot abs=new AskBotShoot(selectorEventPlayers.getPlayerVList());
        abs.start();
    }


    private class AskTargetingScope extends Thread{
        private List<PowerUpCardV> listOfTargetingScopeV;
        private List<Object> possiblePaymentsV;
        private List<PlayerV> damagedPlayersV;

        private AskTargetingScope(List<PowerUpCardV> listOfTargetingScopeV, List<Object> possiblePaymentsV, List<PlayerV> damagedPlayersV){
            this.listOfTargetingScopeV =listOfTargetingScopeV;
            this.possiblePaymentsV = possiblePaymentsV;
            this.damagedPlayersV = damagedPlayersV;
        }

        @Override
        public void run(){

            //ask what targeting scope to use and the possibility to not use it
            List<String> requestTargetingScope = new ArrayList<>();
            for (PowerUpCardV p: listOfTargetingScopeV) {
                requestTargetingScope.add(p.getName() + "    COLOR: " + p.getColor());
            }
            requestTargetingScope.add("skip");
            out.println("Do you want to use Targeting scope?");
            showListOfRequests(requestTargetingScope);
            Integer chosenTargetingScope = askNumber(0, requestTargetingScope.size()-1);

            int chosenPayingMethod = 0;
            int chosenPlayertoHit = 0;
            if(chosenTargetingScope < requestTargetingScope.size()-1) {
                List<String> requestPossiblePayment = new ArrayList<>();
                for (Object o : possiblePaymentsV) {
                    if (o.getClass().toString().contains("AmmoCube")) {
                        requestPossiblePayment.add("pay using a " + ((AmmoCubesV) o).getColor() + " from the ammo box");
                    } else {
                        requestPossiblePayment.add("pay discarding power up " + ((PowerUpCardV) o).getName() + "    COLOR: " + ((PowerUpCardV) o).getColor());
                    }
                }
                out.println("How do you want to pay?");
                showListOfRequests(requestPossiblePayment);
                chosenPayingMethod = askNumber(0, requestTargetingScope.size() - 1);


                List<String> requestPlayerTohit = new ArrayList<>();
                for (PlayerV p : damagedPlayersV) {
                    requestPlayerTohit.add(p.getNickname());
                }
                out.println("who do you want to inflict the damage to?");
                showListOfRequests(requestPlayerTohit);
                chosenPlayertoHit = askNumber(0, requestTargetingScope.size() - 1);
            }

            List<Object> answer = new ArrayList<>();
            answer.add(chosenTargetingScope);
            answer.add(chosenPayingMethod);
            answer.add(chosenPlayertoHit);

            ViewControllerEventListOfObject viewControllerEventListOfObject = new ViewControllerEventListOfObject(answer);
            ViewSelector.sendToServer(viewControllerEventListOfObject);
        }
    }
    @Override
    public void askTargetingScope(List<PowerUpCardV> listOfTargetingScopeV, List<Object> possiblePaymentsV, List<PlayerV> damagedPlayersV) {
        AskTargetingScope ats = new AskTargetingScope(listOfTargetingScopeV, possiblePaymentsV, damagedPlayersV);
        ats.start();
    }



    private class AskTagBackGranade extends Thread{
        private List<PowerUpCardV> listOfTagBackGranade;
        public AskTagBackGranade(List<PowerUpCardV> listOfTagBackGranade){
            this.listOfTagBackGranade = listOfTagBackGranade;
        }
        @Override
        public void run(){
            out.println("<CLIENT> do you want to use tagback granade?");
            List<String> request = new ArrayList<>();
            for (PowerUpCardV p: listOfTagBackGranade) {
                request.add(p.getName() + "   COLOR: " + p.getColor());
            }
            request.add("skip");

            showListOfRequests(request);
            int choice = askNumber(0, request.size()-1);

            ViewControllerEventInt viewControllerEventInt = new ViewControllerEventInt(choice);
            ViewSelector.sendToServer(viewControllerEventInt);
        }
    }
    @Override
    public void askTagBackGranade(List<PowerUpCardV> listOfTagBackGranade) {
        AskTagBackGranade atbg = new AskTagBackGranade(listOfTagBackGranade);
        atbg.start();
    }
}

