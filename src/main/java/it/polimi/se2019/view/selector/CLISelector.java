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
import it.polimi.se2019.networkHandler.RMI.RMINetworkHandler;
import it.polimi.se2019.networkHandler.Socket.SocketNetworkHandler;
import it.polimi.se2019.view.components.*;
import it.polimi.se2019.view.outputHandler.OutputHandlerGate;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class CLISelector implements SelectorV {

    private String networkConnection;

    static private PrintWriter out= new PrintWriter(System.out, true);

    public CLISelector(String networkConnection){
        this.networkConnection = networkConnection;
    }




    public void sendToServer(Object o){
        if(networkConnection.equals("SOCKET")){
            try {
                SocketNetworkHandler.oos.writeObject(o);
            } catch (IOException e) {
                OutputHandlerGate.getCorrectOutputHandler(OutputHandlerGate.getUserIterface()).cantReachServer();
            }
        }
        else{
            try {
                RMINetworkHandler.client.returnInterface().sendToServer(o);
            } catch (RemoteException e) {
                OutputHandlerGate.getCorrectOutputHandler(OutputHandlerGate.getUserIterface()).cantReachServer();
            }
        }
    }

    public static void showTitleRequest(String title){
        //TODO
    }

    public static void showListOfRequests(List<String> requests){

        int maxLenght = 10;

        for (int i = 0; i < requests.size(); i++) {
            if(requests.get(i).length()+3>maxLenght){
                maxLenght = requests.get(i).length()+3;
            }
        }

        String s;

        s="           .-.---";
        for (int i = 0; i < maxLenght ; i++) {
            s+="-";
        }
        s+="---.-.\n";
        
        s+="          ((o))  ";
        for (int i = 0; i < maxLenght; i++) {
            s+=" ";
        }
        s+="      )\n";

        Random rand = new Random();
        s+="           \\U/___";
        for (int i = 0; i < maxLenght; i++) {
            if(rand.nextInt(3) < 2) {
                s += "_";
            }
            else{
                s+=" ";
            }
        }
        s+=" ____/\n";

        s+="             |   ";
        for (int i = 0; i < maxLenght; i++) {
            s+=" ";
        }
        s+="    |\n";


        for (int i = 0; i < requests.size(); i++) {
            s+="             |   ";
            s+= i + ": ";
            s+= requests.get(i);
            for (int j = 0; j < maxLenght-requests.get(i).length()-3; j++) {
                s+=" ";
            }
            s+="    |\n";
        }

        s+="             |____";
        for (int i = 0; i < maxLenght; i++) {
            if(rand.nextInt(3) < 2) {
                s += "_";
            }
            else{
                s+=" ";
            }
        }
        s+="___|\n";

        s+="            /A\\  ";
        for (int i = 0; i < maxLenght; i++) {
            s+=" ";
        }
        s+="     \\\n";

        s+="           ((o)) ";
        for (int i = 0; i < maxLenght; i++) {
            s+=" ";
        }
        s+="      )\n";

        s+="            '-'--";
        for (int i = 0; i < maxLenght ; i++) {
            s+="-";
        }
        s+= "-----'\n";
        System.out.println(s);
    }


    public static int askNumber(int rangeInit, int rangeEnd){
        if(Controller.randomGame){
            try {
                TimeUnit.MILLISECONDS.sleep(300);   // time to respond TODO CHANGED FROM 300
            } catch (InterruptedException e) {
                e.printStackTrace();
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


    private class AskGameSetUp extends Thread{
        private boolean canBot;

        public AskGameSetUp(boolean canBot){
            this.canBot=canBot;
        }

        @Override
        public void run(){
            String gameMode = "normalMode";
            String mapChoice = "map0";
            int numberOfStartingSkulls = 5;
            boolean isFinalFrenzy = false;
            boolean isBotActive = false;
            String temp;

            //System.out.println("<CLIENT> : Choose Game Mode: [normalMode|...|...]");
            //gameMode = br.nextLine();
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
                int Terminator = askNumber(0, 1);
                isBotActive = Terminator == 0;
            }
            else {
                isBotActive=false;
            }

            //TODO DELETE THIS, FORCING A BOT:
            isBotActive = true;


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

            ViewControllerEventGameSetUp VCEGameSetUp = new ViewControllerEventGameSetUp(gameMode,mapChoice,numberOfStartingSkulls,isFinalFrenzy,isBotActive);

            sendToServer(VCEGameSetUp);
        }
    }
    @Override
    public void askGameSetUp(boolean canBot) {
        AskGameSetUp askGameSetUp = new AskGameSetUp(canBot);
        askGameSetUp.start();
    }



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
            }

            ViewControllerEventPlayerSetUp VCEPlayerSetUp = new ViewControllerEventPlayerSetUp(nickaname, color);
            sendToServer(VCEPlayerSetUp);
        }
    }
    @Deprecated
    @Override
    public void askPlayerSetUp() {
        AskPlayerSetUp aps = new AskPlayerSetUp();
        aps.start();
    }




    private class AskFirstSpawnPosition extends Thread {
        private ArrayList<PowerUpCardV> powerUpCards;
        private boolean spawnBot;
        public  AskFirstSpawnPosition(ArrayList<PowerUpCardV> powerUpCards, boolean spawnBot){
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
            for (int i = 0; i < powerUpCards.size(); i++) {
                requests.add(powerUpCards.get(i).getName() + "       COLOR: " + powerUpCards.get(i).getColor());
            }
            CLISelector.showListOfRequests(requests);
            int choice = askNumber(0,powerUpCards.size()-1);

            String cardID = powerUpCards.get(choice).getID();

            ViewControllerEventTwoString VCETwoString = new ViewControllerEventTwoString(cardID, botSpawn);

            sendToServer(VCETwoString);
        }
    }
    @Override
    public void askFirstSpawnPosition(ArrayList<PowerUpCardV> powerUpCards, boolean spawnBot) {
        AskFirstSpawnPosition afsp = new AskFirstSpawnPosition(powerUpCards, spawnBot);
        afsp.start();
    }




    private class AskTurnAction extends Thread {
        private int actionNumber;
        private boolean canUsePowerUp;

        private boolean canUseBot;

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

            List<String> requests = new ArrayList<>();

            requests.addAll(Arrays.asList("run around", "grab stuff", "shoot people"));
            if(canUseBot) {
                requests.add("use Bot");
            }
            if(canUsePowerUp) {
                requests.add("use power up");
            }

            CLISelector.showListOfRequests(requests);
            int choice = askNumber(0,requests.size()-1);

            //TODO SETTING CHOICE SO THAT IT DOESN'T SHOOT TO PEOPLE
            if(choice == 2){
                choice =  1;
            }

            String action = requests.get(choice);


            ViewControllerEventString VCEString = new ViewControllerEventString(action);

            sendToServer(VCEString);
        }
    }

    @Override
    public void askTurnAction(int actionNumber, boolean canUsePowerUp, boolean canUseBot) {
        AskTurnAction askTurnAction = new AskTurnAction(actionNumber, canUsePowerUp, canUseBot);
        askTurnAction.start();
    }


    private class AskBotMove extends Thread{
        private ArrayList<Position> possiblePositions;
        public AskBotMove(ArrayList<Position> possiblePositions){
            this.possiblePositions = possiblePositions;
        }
        @Override
        public void run(){
            out.println("<CLIENT> choose where you want to move the Bot:");

            ArrayList<String> requests = new ArrayList<>();
            for (int i = 0; i < possiblePositions.size(); i++) {
                Position pos = possiblePositions.get(i);
                requests.add("["+  pos.getX() + "][" + pos.getY() + "]");
            }
            CLISelector.showListOfRequests(requests);

            int choosenPosition = askNumber(0,possiblePositions.size()-1);

            ViewControllerEventPosition VCEPosition = new ViewControllerEventPosition(possiblePositions.get(choosenPosition).getX(),possiblePositions.get(choosenPosition).getY());

            sendToServer(VCEPosition);
        }
    }
    @Override
    public void askBotMove(SelectorEventPositions SEPositions) {
        AskBotMove abm = new AskBotMove(SEPositions.getPositions());
        abm.start();
    }


    private class AskRunAroundPosition extends Thread {
        private ArrayList<Position> positions;
        public AskRunAroundPosition(ArrayList<Position> positions){
            this.positions = positions;
        }
        @Override
        public void run() {
            out.println("\n<CLIENT> choose where to move: ");

            ArrayList<String> requests = new ArrayList<>();
            for (int i = 0; i < positions.size(); i++) {
                Position pos = positions.get(i);
                requests.add("["+  pos.getX() + "][" + pos.getY() + "]");
            }
            CLISelector.showListOfRequests(requests);

            int choosenPosition = askNumber(0,positions.size()-1);

            ViewControllerEventPosition VCEPosition = new ViewControllerEventPosition(positions.get(choosenPosition).getX(),positions.get(choosenPosition).getY());

            sendToServer(VCEPosition);
        }
    }
    @Override
    public void askRunAroundPosition(ArrayList<Position> positions) {
        AskRunAroundPosition arap = new AskRunAroundPosition(positions);
        arap.start();
    }
    @Override
    public void askGrabStuffMove(ArrayList<Position> positions) {
        AskRunAroundPosition arap = new AskRunAroundPosition(positions);
        arap.start();
    }

    private class AskGrabSuffAction extends Thread {
        @Override
        public void run() {
            out.println("\n<CLIENT> What do you want to do?");
            CLISelector.showListOfRequests(Arrays.asList("move to another position and grab there","grab where you are without moving"));

            int choosenAction = askNumber(0,1);

            String action;
            if(choosenAction == 0){
                action = "move";
            }
            else{
                action = "grab";
            }

            ViewControllerEventString VCEString = new ViewControllerEventString(action);

            sendToServer(VCEString);
        }
    }
    @Override
    public void askGrabStuffAction() {
        AskGrabSuffAction agsa = new AskGrabSuffAction();
        agsa.start();
    }




    private class AskGrabStuffGrabWeapon extends Thread {
        private ArrayList<WeaponCardV> toPickUp;
        public AskGrabStuffGrabWeapon(ArrayList<WeaponCardV> toPickUp){
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
            for (int i = 0; i < toPickUp.size(); i++) {
                if(toPickUp.get(i).getPickUpCost().getAmmoCubesList().isEmpty()){
                    requests.add(toPickUp.get(i).getName() + ": FREE");
                }
                else {
                    requests.add(toPickUp.get(i).getName() + ": " + toPickUp.get(i).getPickUpCost());
                }
            }
            CLISelector.showListOfRequests(requests);

            int toPickUpchosen = askNumber(0, toPickUp.size()-1);

            String toPickUpID = toPickUp.get(toPickUpchosen).getID();

            ViewControllerEventString VCEString = new ViewControllerEventString(toPickUpID);

            sendToServer(VCEString);
        }
    }
    @Override
    public void askGrabStuffGrabWeapon(ArrayList<WeaponCardV> toPickUp) {
        AskGrabStuffGrabWeapon agsg = new AskGrabStuffGrabWeapon(toPickUp);
        agsg.start();
    }




    private class AskGrabStuffSwitchWeapon extends Thread {
        private ArrayList<WeaponCardV> toPickUp;
        private ArrayList<WeaponCardV> toSwitch;
        public AskGrabStuffSwitchWeapon(ArrayList<WeaponCardV> toPickUp, ArrayList<WeaponCardV> toSwitch){
            this.toPickUp = toPickUp;
            this.toSwitch = toSwitch;
        }
        @Override
        public void run() {
            if(toPickUp.isEmpty()){
                out.println("\n<CLIENT>you can't pick up any card.");
                return;
            }

            out.println("\n<CLIENT>you already have the maximum quantity of weapon you can hold.");
            out.println("<CLIENT>Choose one to pick up and one to discard.");
            out.println("<CLIENT>To pick up:");
            ArrayList<String> requests = new ArrayList<>();
            for (int i = 0; i < toPickUp.size(); i++) {
                if(toPickUp.get(i).getPickUpCost() == null){
                    requests.add(toPickUp.get(i).getName() + ": free");
                }
                else {
                    requests.add(toPickUp.get(i).getName() + ": " + toPickUp.get(i).getPickUpCost().toString());
                }
            }
            CLISelector.showListOfRequests(requests);

            int chosenToPickUp = askNumber(0,toPickUp.size()-1);

            String toPickUpID = toPickUp.get(chosenToPickUp).getID();

            requests.clear();
            out.println("<CLIENT>Switch with:");
            for (int i = 0; i < toSwitch.size(); i++) {
                if(toSwitch.get(i).getPickUpCost() == null){
                    requests.add(toSwitch.get(i).getName() + ": free");
                }
                else {
                    requests.add(toSwitch.get(i).getName() + ": " + toPickUp.get(i).getPickUpCost().toString());
                }
            }
            CLISelector.showListOfRequests(requests);

            int chosenToDiscard= askNumber(0,toSwitch.size()-1);

            String toDiscardID = toSwitch.get(chosenToDiscard).getID();

            ViewControllerEventTwoString VCETwoString = new ViewControllerEventTwoString(toPickUpID, toDiscardID);

            sendToServer(VCETwoString);
        }
    }
    @Override
    public void askGrabStuffSwitchWeapon(ArrayList<WeaponCardV> toPickUp, ArrayList<WeaponCardV> toSwitch) {
        AskGrabStuffSwitchWeapon agssw = new AskGrabStuffSwitchWeapon(toPickUp, toSwitch);
        agssw.start();
    }




    private class AskPowerUpToDiscard extends Thread {
        private ArrayList<PowerUpCardV> toDiscard;
        private AskPowerUpToDiscard(ArrayList<PowerUpCardV> toDiscard){
            this.toDiscard = toDiscard;
        }
        @Override
        public void run() {
            out.println("\n<CLIENT>You have too many power up in hand. You need to discard one:");
            ArrayList<String> requests = new ArrayList<>();
            for (int i = 0; i < toDiscard.size() ; i++) {
                requests.add(toDiscard.get(i).getName() + ": " + toDiscard.get(i).getColor());
            }
            CLISelector.showListOfRequests(requests);

            int choosen = askNumber(0,toDiscard.size()-1);

            ViewControllerEventInt VCEInt = new ViewControllerEventInt(choosen);

            sendToServer(VCEInt);
        }
    }
    @Override
    public void askPowerUpToDiscard(ArrayList<PowerUpCardV> toDiscard) {
        AskPowerUpToDiscard aputd = new AskPowerUpToDiscard(toDiscard);
        aputd.start();
    }





    private class AskWhatReaload extends Thread {
        private ArrayList<WeaponCardV> toReload;
        public AskWhatReaload(ArrayList<WeaponCardV> toReload){
            this.toReload = toReload;
        }
        @Override
        public void run() {
            out.println("\n<CLIENT> Which weapon do you want to reload?");
            ArrayList<String> requests = new ArrayList<>();
            requests.add("skip reload");
            for (int i = 0; i < toReload.size() ; i++) {
                if(toReload.get(i).getPickUpCost() == null){
                    requests.add(toReload.get(i).getName() + ": free");
                }
                else {
                    requests.add(toReload.get(i).getName() + ": " + toReload.get(i).getPickUpCost().toString());
                }
            }
            CLISelector.showListOfRequests(requests);
            int chosen = askNumber(0, toReload.size());

            ViewControllerEventString VCEString =null;
            if(chosen==0){
                VCEString= new ViewControllerEventString("SKIP");
            }
            else{
                String chosenID = toReload.get(chosen-1).getID();
                VCEString = new ViewControllerEventString(chosenID);
            }

            sendToServer(VCEString);
        }
    }
    @Override
    public void askWhatReaload(ArrayList<WeaponCardV> toReload) {
        AskWhatReaload awr = new AskWhatReaload(toReload);
        awr.start();
    }


    private class AskSpawn extends Thread {
        private ArrayList<PowerUpCardV> powerUpCards;
        public AskSpawn(ArrayList<PowerUpCardV> powerUpCards){
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
            ViewControllerEventString VCEString = new ViewControllerEventString(chosenId);
            sendToServer(VCEString);
        }
    }
    @Override
    public void askSpawn(ArrayList<PowerUpCardV> powerUpCards) {
        AskSpawn as = new AskSpawn(powerUpCards);
        as.start();
    }





    private class AskShootOrMove extends Thread {
        @Override
        public void run() {
            int numberOfMoves;
            numberOfMoves=1;

            out.println("\n<CLIENT> Do you want to:");
            CLISelector.showListOfRequests(Arrays.asList("move before shoting","stay still and shoot"));

            out.println("remember, you can move up to "+numberOfMoves + " squares");

            int chosen = askNumber(0,1);

            ViewControllerEventString VCEstring;
            if(chosen==0){
                VCEstring= new ViewControllerEventString("move");
            }
            else{
                VCEstring = new ViewControllerEventString("shoot");
            }

            sendToServer(VCEstring);
        }
    }
    @Override
    public void askShootOrMove(){
        AskShootOrMove asom = new AskShootOrMove();
        asom.start();
    }




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
            //TODO
            CLISelector.showListOfRequests(Arrays.asList("if you want to move, reload and shoot","if you want to stay still, reload and shot", "TODO !!" ));
            out.println("remember, you can move up to "+numberOfMoves + " squares.");

            int chosen = br.nextInt();
            ViewControllerEventInt VCEint = new ViewControllerEventInt(chosen);

            sendToServer(VCEint);
        }
    }
    @Deprecated
    @Override
    public void askShootReloadMove(){
        AskShootReloadMove asrm = new AskShootReloadMove();
        asrm.start();
    }





    private class AskWhatWep extends Thread {
        private ArrayList<WeaponCardV> loadedCardInHand;
        public AskWhatWep(ArrayList<WeaponCardV> loadedCardInHand){
            this.loadedCardInHand = loadedCardInHand;
        }
        @Override
        public void run() {
            Scanner br = new Scanner(System.in);

            out.println("\n<CLIENT> What weapon do you want to use?");
            ArrayList<String> requests = new ArrayList<>();
            for (int i = 0; i < loadedCardInHand.size() ; i++) {
                requests.add(loadedCardInHand.get(i).getName());
            }
            CLISelector.showListOfRequests(requests);

            int chosen = askNumber(0,loadedCardInHand.size()-1);

            ViewControllerEventInt VCEint = new ViewControllerEventInt(chosen);

            sendToServer(VCEint);
        }
    }
    @Override
    public void askWhatWep(ArrayList<WeaponCardV> loadedCardInHand) {
        AskWhatWep aww = new AskWhatWep(loadedCardInHand);
        aww.start();
    }




    private class AskWhatEffect extends Thread {
        private ArrayList<EffectV> possibleEffects;
        public AskWhatEffect(ArrayList<EffectV> possibleEffects){
            this.possibleEffects = possibleEffects;
        }
        @Override
        public void run() {
            Scanner br = new Scanner(System.in);

            System.out.println("\n<CLIENT> What Effect do you want to use?");
            ArrayList<String> requests = new ArrayList<>();
            for (int i = 0; i < possibleEffects.size() ; i++) {
                requests.add(possibleEffects.get(i).getEffectName());
            }
            CLISelector.showListOfRequests(requests);

            int chosen = askNumber(0,possibleEffects.size()-1);

            ViewControllerEventInt VCEint = new ViewControllerEventInt(chosen);

            sendToServer(VCEint);
        }
    }
    @Override
    public void askWhatEffect(ArrayList<EffectV> possibleEffects) {
        AskWhatEffect awe = new AskWhatEffect(possibleEffects);
        awe.start();
    }





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

            List<Object> answer = new ArrayList<>();

            Scanner br = new Scanner(System.in);

            ArrayList<String> requestsString = new ArrayList<>();

            for (int j = 0; j < request ; j++) {
                if(possibleInputs.isEmpty()){
                    break;
                }
                if(request>=2){
                    out.println("Do you want to chose another one?");
                    CLISelector.showListOfRequests(Arrays.asList("Yes", "No"));
                    int choice = askNumber(0,1);
                    boolean YorN;
                    if(choice==1){
                        break;
                    }
                }

                if(possibleInputs.get(0).getClass().toString().contains("PlayerV")){
                    out.println("<Client> please choose one of the following players: ");
                    PlayerV p;
                    for (int i = 0; i < possibleInputs.size(); i++) {
                        p=(PlayerV)possibleInputs.get(i);
                        requestsString.add(p.getNickname());
                    }
                }
                else{
                    out.println("<Client> please choose one of the following squares: ");
                    SquareV s;
                    for (int i = 0; i < possibleInputs.size(); i++) {
                        s=(SquareV)possibleInputs.get(i);
                        requestsString.add("[" + s.getX() + "][" + s.getY() + "]" );
                    }
                }
                CLISelector.showListOfRequests(requestsString);

                int chosen = askNumber(0,possibleInputs.size()-1);

                answer.add(possibleInputs.get(chosen));
                possibleInputs.remove(possibleInputs.get(chosen));
            }
            ViewControllerEventListOfObject VCEListOfObject = new ViewControllerEventListOfObject(answer);

            sendToServer(VCEListOfObject);
        }

        public int howManyRequest(){
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
    @Override
    public void askEffectInputs(EffectInfoType inputType, List<Object> possibleInputs) {
        AskEffectInputs aei = new AskEffectInputs(inputType, possibleInputs);
        aei.start();
    }




    @Override
    public void askReconnectionNickname(ReconnectionEvent RE) {
        out.println("\nAFK Players are: ");
        ArrayList<String> requests = new ArrayList<>();
        for (int i = 0; i < RE.getListOfAFKPlayers().size(); i++) {
            requests.add(RE.getListOfAFKPlayers().get(i));
        }
        CLISelector.showListOfRequests(requests);
        out.println("What was your nickname?");

        int choice = askNumber(0,RE.getListOfAFKPlayers().size()-1);
        ArrayList<String> answer = new ArrayList<>();
        answer.add(RE.getListOfAFKPlayers().get(choice));

        out.println("<CLIENT> setting \"me\": " + answer.get(0));
        ViewModelGate.setMe(answer.get(0));

        answer.add(this.networkConnection);
        sendToServer(new ReconnectionEvent(answer));
    }




    private class AskNickaname extends Thread{
        @Override
        public void run() {
            Scanner br = new Scanner(System.in);
            out.println("<CLIENT> I'm sorry but the nickname chosen was already taken, please insert a new one:");
            String newNickname = br.nextLine();
            ViewModelGate.setMe(newNickname);
            out.println("<CLIENT> informing the server of your new nickname.");
            sendToServer(new ViewControllerEventNickname(ViewModelGate.getMe()));
        }
    }
    private boolean nicknameIsAvailable = true;
    @Override
    public void askNickname() {
        if(nicknameIsAvailable) {
            out.println("<CLIENT> informing the server of your nickname");
            sendToServer(new ViewControllerEventNickname(ViewModelGate.getMe()));
            nicknameIsAvailable = false;
        }
        else{
            AskNickaname AN = new AskNickaname();
            AN.start();
        }
    }

    private class AskPaymentInformation extends Thread{
        private SelectorEventPaymentInformation SEPaymentInformation;
        public AskPaymentInformation(SelectorEventPaymentInformation SEPaymentInformation){
            this.SEPaymentInformation = SEPaymentInformation;
        }
        @Override
        public void run() {
            ArrayList<String> request = new ArrayList<>();
            ArrayList<Object> answer = new ArrayList<>();
            List<AmmoCubesV> amountToPay = SEPaymentInformation.getAmount().getAmmoCubesList();

            while(true) {
                out.println("<CLIENT> you have to pay the following amount of ammo: ");

                for (AmmoCubesV a : amountToPay) {
                    out.println("         " + a.getColor() + " --> " + a.getQuantity());
                }

                if (SEPaymentInformation.canPayWithoutPowerUps()) {
                    request.add("pay the amount with ammos from the Ammo box");
                }

                for (PowerUpCardV p : SEPaymentInformation.getPossibilities()) {
                    request.add("discard " + p.getName() + " for getting off a " + p.getColor() + " ammo from the total cost");
                }

                showListOfRequests(request);
                int choice = askNumber(0, request.size()-1);

                if (choice == 0 && SEPaymentInformation.canPayWithoutPowerUps()) {
                    out.println("<CLIENT> you decided to pay the amount with your ammos from the Ammo Box");
                    break;
                }
                else{
                    //color of the power up chosen
                    AmmoCubesColor colorOfTheChosenPowerUp;
                    if(SEPaymentInformation.canPayWithoutPowerUps()) {
                        colorOfTheChosenPowerUp = SEPaymentInformation.getPossibilities().get(choice - 1).getColor();
                    }
                    else{
                        colorOfTheChosenPowerUp = SEPaymentInformation.getPossibilities().get(choice).getColor();
                    }

                    //add the chosen power up to the answer
                    if(SEPaymentInformation.canPayWithoutPowerUps()) {
                        answer.add(SEPaymentInformation.getPossibilities().get(choice - 1));
                    }
                    else{
                        answer.add(SEPaymentInformation.getPossibilities().get(choice));
                    }

                    //subtract one unit of the color of the power up chosen from the total cost to pay
                    for (AmmoCubesV ammo: amountToPay) {
                        if(ammo.getColor().equals(colorOfTheChosenPowerUp)){
                            ammo.setQuantity(ammo.getQuantity()-1);
                            if(ammo.getQuantity()<=0){
                                //remove the color from the amount topay
                                amountToPay.remove(ammo);

                                //delete All the power up card with the same color as the one of the power up chosen from the possibilities
                                Iterator<PowerUpCardV> elementListIterator = SEPaymentInformation.getPossibilities().iterator();
                                while (elementListIterator.hasNext()) {
                                    PowerUpCardV element = elementListIterator.next();
                                    if(element.getColor().equals(colorOfTheChosenPowerUp)) {
                                        elementListIterator.remove();
                                    }
                                }

                            }
                            break;
                        }
                    }

                }

                request = new ArrayList<>();

                if(SEPaymentInformation.getPossibilities().isEmpty()){
                    break;
                }
                if(amountToPay.isEmpty()){
                    break;
                }
            }

            ViewControllerEventPaymentInformation VCEPaymentInformation = new ViewControllerEventPaymentInformation(answer);
            sendToServer(VCEPaymentInformation);

        }
    }
    @Override
    public void askPaymentInformation(SelectorEventPaymentInformation SEPaymentInformation) {
        AskPaymentInformation api = new AskPaymentInformation(SEPaymentInformation);
        api.start();
    }


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
            ViewControllerEventTwoString VCEString = new ViewControllerEventTwoString(answer1, answer2);
            sendToServer(VCEString);
        }
    }

    @Override
    public void askPowerUpToUse(SelectorEventPowerUpCards powerUpCards) {
        AskPowerUpToUse aputu = new AskPowerUpToUse(powerUpCards.getPowerUpCards());
        aputu.start();
    }




    private class WantToUsePowerUpOrNot extends Thread{
        @Override
        public void run(){
            out.println("<CLIENT> Do you want to use a power up?");
            CLISelector.showListOfRequests(Arrays.asList("yes", "no"));
            int choice = askNumber(0,1);
            if(choice == 0){
                sendToServer(new ViewControllerEventBoolean(true));
            }
            else{
                sendToServer(new ViewControllerEventBoolean(false));
            }
        }
    }
    @Override
    public void wantToUsePowerUpOrNot() {
        WantToUsePowerUpOrNot wtupon = new WantToUsePowerUpOrNot();
        wtupon.start();
    }




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
            ViewControllerEventString VCE=new ViewControllerEventString(requests.get(choice));
            sendToServer(VCE);
        }
    }
    @Override
    public void askBotShoot(SelectorEventPlayers SEPlayers){

        AskBotShoot abs=new AskBotShoot(SEPlayers.getPlayerVList());
        abs.start();
    }
}

