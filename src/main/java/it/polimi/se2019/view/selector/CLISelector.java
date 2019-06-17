package it.polimi.se2019.view.selector;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.enumerations.PlayersColors;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPaymentInformation;
import it.polimi.se2019.model.events.viewControllerEvents.*;
import it.polimi.se2019.networkHandler.RMI.RMINetworkHandler;
import it.polimi.se2019.networkHandler.Socket.SocketNetworkHandler;
import it.polimi.se2019.view.components.*;
import it.polimi.se2019.view.outputHandler.CLIOutputHandler;
import it.polimi.se2019.view.outputHandler.OutputHandlerGate;
import it.polimi.se2019.virtualView.Selector;
import it.polimi.se2019.virtualView.SelectorV;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class CLISelector implements SelectorV {

    private String networkConnection;

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
        if(Controller.randomGame == true){
            try {
                TimeUnit.MILLISECONDS.sleep(100);
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

            System.out.println("\n<CLIENT> : Do you want to play with Final frenzy active?");
            CLISelector.showListOfRequests(Arrays.asList("Yes","No"));
            int FF = askNumber(0,1);
            if(FF==0){
                isFinalFrenzy=true;
            }
            else{
                isFinalFrenzy=false;
            }

            System.out.println("\n<CLIENT> : Do you want to play with the Terminator active?");
            CLISelector.showListOfRequests(Arrays.asList("Yes","No"));
            int Terminator = askNumber(0,1);
            isBotActive = Terminator==0;

            System.out.println("\n<CLIENT> : Choose number of starting skulls:");
            CLISelector.showListOfRequests(Arrays.asList("5 starting skulls","8 starting skulls"));
            numberOfStartingSkulls = askNumber(0,1);
            if(numberOfStartingSkulls==0){
                numberOfStartingSkulls = 5;
            }
            else{
                numberOfStartingSkulls = 8;
            }

            ViewControllerEventGameSetUp VCEGameSetUp = new ViewControllerEventGameSetUp(gameMode,mapChoice,numberOfStartingSkulls,isFinalFrenzy,isBotActive);

            sendToServer(VCEGameSetUp);
        }
    }
    @Override
    public void askGameSetUp() {
        AskGameSetUp askGameSetUp = new AskGameSetUp();
        askGameSetUp.start();
    }



    @Deprecated
    private class AskPlayerSetUp extends Thread {
        @Deprecated
        @Override
        public void run() {
            String nickaname = "defaultUserName";
            PlayersColors color = PlayersColors.purple;

            System.out.println("<CLIENT> Insert your nickname: ");
            Scanner br = new Scanner(System.in);
            nickaname = br.nextLine();

            ViewModelGate.setMe(nickaname);
            System.out.println("<CLIENT> \"ME\" is: " + nickaname);

            System.out.println("<CLIENT> ChooseColor: \n" +
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
        public  AskFirstSpawnPosition(ArrayList<PowerUpCardV> powerUpCards){
            this.powerUpCards = powerUpCards;
        }
        @Override
        public void run() {
            System.out.println("\n<CLIENT> choose the PowerUp to discard and spawn to: ");
            ArrayList<String> requests = new ArrayList<>();
            for (int i = 0; i < powerUpCards.size(); i++) {
                requests.add(powerUpCards.get(i).getName() + ": " + powerUpCards.get(i).getDescription() + ".       COLOR: " + powerUpCards.get(i).getColor());
            }
            CLISelector.showListOfRequests(requests);
            int choice = askNumber(0,powerUpCards.size()-1);

            String cardID = powerUpCards.get(choice).getID();

            ViewControllerEventString VCEString = new ViewControllerEventString(cardID);

            sendToServer(VCEString);
        }
    }
    @Override
    public void askFirstSpawnPosition(ArrayList<PowerUpCardV> powerUpCards) {
        AskFirstSpawnPosition afsp = new AskFirstSpawnPosition(powerUpCards);
        afsp.start();
    }




    private class AskTurnAction extends Thread {
        private int actionNumber;

        public AskTurnAction(int actionNumber){
            this.actionNumber = actionNumber;
        }
        @Override
        public void run() {
            if(actionNumber == 1) {
                System.out.println("\n<CLIENT> Choose your first action");
            }
            else{
                System.out.println("\n<CLIENT> Choose your second action");
            }


            CLISelector.showListOfRequests(Arrays.asList("run around","grab stuff", "shoot people"));

            //FOR DEBUGGING:
            int choice = askNumber(0,2);
            //int choice = askNumber(0,2);

            String action = null;
            if(choice == 0){
                action = "run around";
            }
            else if(choice == 1){
                action = "grab stuff";
            }
            else if(choice == 2){
                action = "shoot people";
            }

            ViewControllerEventString VCEString = new ViewControllerEventString(action);

            sendToServer(VCEString);
        }
    }

    @Override
    public void askTurnAction(int actionNumber) {
        AskTurnAction askTurnAction = new AskTurnAction(actionNumber);
        askTurnAction.start();
    }


    private class AskRunAroundPosition extends Thread {
        private ArrayList<Position> positions;
        public AskRunAroundPosition(ArrayList<Position> positions){
            this.positions = positions;
        }
        @Override
        public void run() {
            System.out.println("\n<CLIENT> choose where to move: ");

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
            System.out.println("\n<CLIENT> What do you want to do?");
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
            if(toPickUp.size() == 0){
                System.out.println("\n<CLIENT>you can't pick up any card.");
                return;
            }

            System.out.println("\n<CLIENT>Choose what to pick up:");
            ArrayList<String> requests = new ArrayList<>();
            for (int i = 0; i < toPickUp.size(); i++) {
                requests.add(toPickUp.get(i).getName() + ": " + toPickUp.get(i).getPickUpCost());
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
            if(toPickUp.size() == 0){
                System.out.println("\n<CLIENT>you can't pick up any card.");
                return;
            }

            System.out.println("\n<CLIENT>you already have the maximum quantity of weapon you can hold.");
            System.out.println("<CLIENT>Choose one to pick up and one to discard.");
            System.out.println("<CLIENT>To pick up:");
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
            System.out.println("<CLIENT>Switch with:");
            for (int i = 0; i < toSwitch.size(); i++) {
                requests.add(toSwitch.get(i).getName());
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
        public AskPowerUpToDiscard(ArrayList<PowerUpCardV> toDiscard){
            this.toDiscard = toDiscard;
        }
        @Override
        public void run() {
            System.out.println("\n<CLIENT>You have too many power up in hand. You need to discard one:");
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
            System.out.println("\n<CLIENT> Which weapon do you want to reload?");
            ArrayList<String> requests = new ArrayList<>();
            requests.add("skip reload");
            for (int i = 0; i < toReload.size() ; i++) {
                requests.add(toReload.get(i).getName() + ": " + toReload.get(i).getReloadCost().toString());
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
            //TODO??
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

            System.out.println("\n<CLIENT> Do you want to:");
            CLISelector.showListOfRequests(Arrays.asList("move before shoting","stay still and shoot"));

            System.out.println("remember, you can move up to "+numberOfMoves + " squares");

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

            System.out.println("\n<CLIENT> Do you want to:");
            //TODO
            CLISelector.showListOfRequests(Arrays.asList("if you want to move, reload and shoot","if you want to stay still, reload and shot", "TODO !!" ));
            System.out.println("remember, you can move up to "+numberOfMoves + " squares.");

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

            System.out.println("\n<CLIENT> What weapon do you want to use?");
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
            System.out.println("<CLIENT> " + "requested input type: " + infoType);

            int request = howManyRequest();

            List<Object> answer = new ArrayList<>();

            Scanner br = new Scanner(System.in);

            ArrayList<String> requestsString = new ArrayList<>();

            for (int j = 0; j < request ; j++) {
                if(possibleInputs.get(0).getClass().toString().contains("Player")){
                    System.out.println("<Client> please choose one of the following players: ");
                    Player p;
                    for (int i = 0; i < possibleInputs.size(); i++) {
                        p=(Player)possibleInputs.get(i);
                        requestsString.add(p.getNickname());
                    }
                }
                else{
                    System.out.println("<Client> please choose one of the following squares: ");
                    Square s;
                    for (int i = 0; i < possibleInputs.size(); i++) {
                        s=(Square)possibleInputs.get(i);
                        requestsString.add("[" + s.getCoordinates().getX() + "][" + s.getCoordinates().getY() + "]" );
                    }
                }
                CLISelector.showListOfRequests(requestsString);

                int chosen = askNumber(0,possibleInputs.size()-1);

                answer.add(possibleInputs.get(chosen));
                possibleInputs.remove(possibleInputs.get(chosen));

                if(request == 999){
                    System.out.println("Do you want to chose another one?");
                    CLISelector.showListOfRequests(Arrays.asList("Yes", "No"));
                    int choice = askNumber(0,1);
                    boolean YorN;
                    if(choice!=0){
                        break;
                    }
                }
            }
            ViewControllerEventListOfObject VCEListOfObject = new ViewControllerEventListOfObject(answer);

            sendToServer(VCEListOfObject);
        }

        public int howManyRequest(){
            if(this.infoType.equals(EffectInfoType.multipleSquareSelect) ||
                this.infoType.equals(EffectInfoType.multipleTargets)){
                return 999; //means multiple choices
            }
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
        System.out.println("\nAFK Players are: ");
        ArrayList<String> requests = new ArrayList<>();
        for (int i = 0; i < RE.getListOfAFKPlayers().size(); i++) {
            requests.add(RE.getListOfAFKPlayers().get(i));
        }
        CLISelector.showListOfRequests(requests);
        System.out.println("What was your nickname?");

        int choice = askNumber(0,RE.getListOfAFKPlayers().size()-1);
        ArrayList<String> answer = new ArrayList<>();
        answer.add(RE.getListOfAFKPlayers().get(choice));

        System.out.println("<CLIENT> setting \"me\": " + answer.get(0));
        ViewModelGate.setMe(answer.get(0));

        answer.add(this.networkConnection);
        sendToServer(new ReconnectionEvent(answer));
    }




    private class AskNickaname extends Thread{
        @Override
        public void run() {
            Scanner br = new Scanner(System.in);
            System.out.println("<CLIENT> I'm sorry but the nickname chosen was already taken, please insert a new one:");
            String newNickname = br.nextLine();
            ViewModelGate.setMe(newNickname);
            System.out.println("<CLIENT> informing the server of your new nickname.");
            sendToServer(new ViewControllerEventNickname(ViewModelGate.getMe()));
        }
    }
    private boolean nicknameIsAvailable = true;
    @Override
    public void askNickname() {
        if(nicknameIsAvailable) {
            System.out.println("<CLIENT> informing the server of your nickname");
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

            while(true) {
                System.out.println("<CLIENT> you have to pay the following amount of ammo: ");

                for (AmmoCubesV a : SEPaymentInformation.getAmount().getAmmoCubesList()) {
                    System.out.println("         " + a.getColor() + " --> " + a.getQuantity());
                }

                if (SEPaymentInformation.canPayWithoutPowerUps()) {
                    request.add("pay the amount with my Ammo box");
                }

                for (PowerUpCardV p : SEPaymentInformation.getPossibilities()) {
                    request.add("use " + p.getName() + " for a " + p.getColor() + " ammo");
                }

                showListOfRequests(request);
                int choice = askNumber(0, request.size());

                if (choice == 0) {
                    break;
                }
                else{
                    answer.add(SEPaymentInformation.getPossibilities().get(choice-1));
                    SEPaymentInformation.getPossibilities().remove(choice-1);
                }

                if(SEPaymentInformation.getPossibilities().size()==0){
                    break;
                }
            }

            ViewControllerEventPaymentInformation VCEPaymentInformation = new ViewControllerEventPaymentInformation(answer);
            sendToServer(VCEPaymentInformation);
        }
    }
    @Override
    public void askPaymentInformation(SelectorEventPaymentInformation SEPaymentInformormation) {
        AskPaymentInformation api = new AskPaymentInformation(SEPaymentInformormation);
        api.start();
    }
}

