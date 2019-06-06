package it.polimi.se2019.view.selector;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.enumerations.PlayersColors;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;
import it.polimi.se2019.model.events.viewControllerEvents.*;
import it.polimi.se2019.networkHandler.RMI.RMINetworkHandler;
import it.polimi.se2019.networkHandler.Socket.SocketNetworkHandler;
import it.polimi.se2019.view.components.PlayerV;
import it.polimi.se2019.view.components.SquareV;
import it.polimi.se2019.view.components.ViewModelGate;
import it.polimi.se2019.virtualView.Selector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLISelector implements Selector {

    private String networkConnection;

    public void sendToServer(Object o){
        if(networkConnection.equals("SOCKET")){
            try {
                SocketNetworkHandler.oos.writeObject(o);
            } catch (IOException e) {
                System.out.println("<CLIENT> Socket connection is closed. Try to reconnect to server using the same Nickname.");
                //e.printStackTrace();
            }
        }
        else{
            try {
                RMINetworkHandler.client.returnInterface().sendToServer(o);
            } catch (RemoteException e) {
                System.out.println("<CLIENT> RMI connection is closed. Try to reconnect to server using the same Nickname.");
            }
        }
    }


    public CLISelector(String networkConnection){
        this.networkConnection = networkConnection;
    }

    private class AskGameSetUp extends Thread{
        @Override
        public void run(){
            String gameMode = "normalMode";
            String mapChoice = "map0";
            int numberOfStartingSkulls = 5;
            boolean isFinalFrenzy = false;
            boolean isBotActive = false;

            String temp= "n";

            Scanner br = new Scanner(System.in);
            System.out.println("<CLIENT> : Choose Game Mode: [normalMode|...|...]");
            gameMode = br.nextLine();
            System.out.println("<CLIENT> : Choose Map: [map0|map1|map2|map3]");
            mapChoice = br.nextLine();
            System.out.println("<CLIENT> : Do you want to play with Final frenzy active? [Y|N]");
            temp = br.nextLine();
            isFinalFrenzy = temp.toLowerCase().equals("y");
            System.out.println("<CLIENT> : Do you want to play with the Terminator active? [Y|N]");
            temp = br.nextLine();
            isBotActive = temp.toLowerCase().equals("y");
            System.out.println("<CLIENT> : Choose number of starting skulls (between 5 and 7)");
            numberOfStartingSkulls = br.nextInt();

            br.close();

            ViewControllerEventGameSetUp VCEGameSetUp = new ViewControllerEventGameSetUp(gameMode,mapChoice,numberOfStartingSkulls,isFinalFrenzy,isBotActive);

            sendToServer(VCEGameSetUp);
        }
    }
    @Override
    public void askGameSetUp() {
        AskGameSetUp askGameSetUp = new AskGameSetUp();
        askGameSetUp.start();
    }



    private class AskPlayerSetUp extends Thread {
        @Override
        public void run() {
            BufferedReader br = null;
            String nickaname = "defaultUserName";
            PlayersColors color = PlayersColors.purple;
            try {
                System.out.println("<CLIENT> Insert your nickname: ");
                br = new BufferedReader(new InputStreamReader(System.in));
                nickaname = br.readLine();

                ViewModelGate.setMe(nickaname);
                System.out.println("<CLIENT> \"ME\" is: " + nickaname);

                System.out.println("<CLIENT> ChooseColor: \n" +
                        "    blue,\n" +
                        "    yellow,\n" +
                        "    gray,\n" +
                        "    green,\n" +
                        "    purple");
                String colorChosen = br.readLine();
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
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ViewControllerEventPlayerSetUp VCEPlayerSetUp = new ViewControllerEventPlayerSetUp(nickaname, color);
            sendToServer(VCEPlayerSetUp);
        }
    }
    @Override
    public void askPlayerSetUp() {
        AskPlayerSetUp aps = new AskPlayerSetUp();
        aps.start();
    }


    private class AskFirstSpawnPosition extends Thread {
        private ArrayList<PowerUpCard> powerUpCards;
        public  AskFirstSpawnPosition(ArrayList<PowerUpCard> powerUpCards){
            this.powerUpCards = powerUpCards;
        }

        @Override
        public void run() {
            System.out.println("<CLIENT> choose the PowerUp to discard and spawn to: ");
            for (int i = 0; i < powerUpCards.size(); i++) {
                System.out.println("    " + powerUpCards.get(i).getID() + ": " + powerUpCards.get(i).getColor());
            }

            Scanner br = new Scanner(System.in);
            String cardID = br.nextLine();

            br.close();

            ViewControllerEventString VCEString = new ViewControllerEventString(cardID);

            sendToServer(VCEString);
        }
    }

    @Override
    public void askFirstSpawnPosition(ArrayList<PowerUpCard> powerUpCards) {
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
                System.out.println("<CLIENT> Choose your first action");
            }
            else{
                System.out.println("<CLIENT> Choose your second action");
            }
            System.out.println(
                    "   run around\n"+
                            "   grab stuff\n"+
                            "   shoot people"
            );

            Scanner br = new Scanner(System.in);

            ViewControllerEventString VCEString = new ViewControllerEventString(br.nextLine());

            br.close();

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
            System.out.println("<CLIENT> choose where to move: ");
            for (int i = 0; i < positions.size(); i++) {
                Position pos = positions.get(i);
                System.out.println("    " + i + ") X:"+  pos.getX() + " Y:" + pos.getY());
            }
            Scanner br = new Scanner(System.in);

            int choosenPosition = br.nextInt();

            br.close();

            ViewControllerEventPosition VCEPosition = new ViewControllerEventPosition(positions.get(choosenPosition).getX(),positions.get(choosenPosition).getY());

            sendToServer(VCEPosition);
        }
    }

    @Override
    public void askRunAroundPosition(ArrayList<Position> positions) {
        AskRunAroundPosition arap = new AskRunAroundPosition(positions);
        arap.start();
    }


    private class AskGrabSuffAction extends Thread {
        @Override
        public void run() {
            System.out.println("<CLIENT> Do you want to:\n" +
                    "   0) move to another position and grab there\n" +
                    "   1) grab where you are without moving\n" +
                    "   ?");
            Scanner br = new Scanner(System.in);

            int choosenAction = br.nextInt();
            String action;
            if(choosenAction == 0){
                action = "move";
            }
            else{
                action = "grab";
            }

            br.close();

            ViewControllerEventString VCEString = new ViewControllerEventString(action);

            sendToServer(VCEString);
        }
    }

    @Override
    public void askGrabStuffAction() {
        AskGrabSuffAction agsa = new AskGrabSuffAction();
        agsa.start();
    }

    @Override
    public void askGrabStuffMove(ArrayList<Position> positions) {
        AskRunAroundPosition arap = new AskRunAroundPosition(positions);
        arap.start();
    }


    private class AskGrabStuffGrabWeapon extends Thread {
        private ArrayList<WeaponCard> toPickUp;
        public AskGrabStuffGrabWeapon(ArrayList<WeaponCard> toPickUp){
            this.toPickUp = toPickUp;
        }
        @Override
        public void run() {
            if(toPickUp.size() == 0){
                System.out.println("<CLIENT>you can't pick up any card.");
                return;
            }

            System.out.println("<CLIENT>Choose number to pick up:");

            Scanner br = new Scanner(System.in);

            for (int i = 0; i < toPickUp.size(); i++) {
                System.out.println( "   " +i + ") " + toPickUp.get(i).getID() + ":\n" + toPickUp.get(i).getPickUpCost());
            }

            String toPickUpID = toPickUp.get(br.nextInt()).getID();

            br.close();

            ViewControllerEventString VCEString = new ViewControllerEventString(toPickUpID);

            sendToServer(VCEString);
        }
    }

    @Override
    public void askGrabStuffGrabWeapon(ArrayList<WeaponCard> toPickUp) {
        AskGrabStuffGrabWeapon agsg = new AskGrabStuffGrabWeapon(toPickUp);
        agsg.start();
    }


    private class AskGrabStuffSwitchWeapon extends Thread {
        private ArrayList<WeaponCard> toPickUp;
        private ArrayList<WeaponCard> toSwitch;
        public AskGrabStuffSwitchWeapon(ArrayList<WeaponCard> toPickUp, ArrayList<WeaponCard> toSwitch){
            this.toPickUp = toPickUp;
            this.toSwitch = toSwitch;
        }
        @Override
        public void run() {
            if(toPickUp.size() == 0){
                System.out.println("<CLIENT>you can't pick up any card.");
                return;
            }

            System.out.println("<CLIENT>you already have the maximum quantity of weapon you can hold.");
            System.out.println("<CLIENT>Choose one to pick up and one to discard.");
            System.out.println("<CLIENT>To pick up:");
            for (int i = 0; i < toPickUp.size(); i++) {
                System.out.println( "   " +i + ") " + toPickUp.get(i).getID() + ":\n" + toPickUp.get(i).getPickUpCost().toString());
            }

            Scanner br = new Scanner(System.in);
            int choosenToPickUp = br.nextInt();

            String toPickUpID = toPickUp.get(choosenToPickUp).getID();

            System.out.println("<CLIENT>Switch with:");
            for (int i = 0; i < toSwitch.size(); i++) {
                System.out.println( "   " +i + ") " + toSwitch.get(i).getID());
            }

            int choosenToDiscard= br.nextInt();

            br.close();

            String toDiscardID = toSwitch.get(choosenToDiscard).getID();

            ViewControllerEventTwoString VCETwoString = new ViewControllerEventTwoString(toPickUpID, toDiscardID);

            sendToServer(VCETwoString);
        }
    }

    @Override
    public void askGrabStuffSwitchWeapon(ArrayList<WeaponCard> toPickUp, ArrayList<WeaponCard> toSwitch) {
        AskGrabStuffSwitchWeapon agssw = new AskGrabStuffSwitchWeapon(toPickUp, toSwitch);
        agssw.start();
    }


    private class AskPowerUpToDiscard extends Thread {
        private ArrayList<PowerUpCard> toDiscard;
        public AskPowerUpToDiscard(ArrayList<PowerUpCard> toDiscard){
            this.toDiscard = toDiscard;
        }
        @Override
        public void run() {
            System.out.println("<CLIENT>You have too many power up in hand. You need to discard one:");
            for (int i = 0; i < toDiscard.size() ; i++) {
                System.out.println("    " + i + ") " + toDiscard.get(i));
            }
            Scanner br = new Scanner(System.in);

            int choosen = br.nextInt();

            br.close();

            ViewControllerEventInt VCEInt = new ViewControllerEventInt(choosen);

            sendToServer(VCEInt);
        }
    }

    @Override
    public void askPowerUpToDiscard(ArrayList<PowerUpCard> toDiscard) {
        AskPowerUpToDiscard aputd = new AskPowerUpToDiscard(toDiscard);
        aputd.start();
    }


    private class AskWhatReaload extends Thread {
        private ArrayList<WeaponCard> toReload;
        public AskWhatReaload(ArrayList<WeaponCard> toReload){
            this.toReload = toReload;
        }
        @Override
        public void run() {
            System.out.println("<CLIENT>Which weapon do you want to reload?");
            for (int i = 0; i < toReload.size() ; i++) {
                System.out.println("    " + (i+1) + ") " + toReload.get(i).getID());
            }
            System.out.println("  Insert 0 if u wanna /skip/ reload.");
            Scanner br = new Scanner(System.in);
            int chosen = br.nextInt();

            br.close();

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
    public void askWhatReaload(ArrayList<WeaponCard> toReload) {
        AskWhatReaload awr = new AskWhatReaload(toReload);
        awr.start();
    }


    private class AskSpawn extends Thread {
        private ArrayList<PowerUpCard> powerUpCards;
        public AskSpawn(ArrayList<PowerUpCard> powerUpCards){
            this.powerUpCards = powerUpCards;
        }
        @Override
        public void run() {
            //TODO??
        }
    }
    @Override
    public void askSpawn(ArrayList<PowerUpCard> powerUpCards) {
        AskSpawn as = new AskSpawn(powerUpCards);
        as.start();
    }


    private class AskShootOrMove extends Thread {
        @Override
        public void run() {
            int numberOfMoves;
            numberOfMoves=1;
            Scanner br = new Scanner(System.in);

            System.out.println("<CLIENT> Do you want to:\n" +
                    "   0) Press 0 if you wanna move before taking your shot\n" +
                    "   1) Press 1 if you want to stay still and shoot\n" +
                    "   ");
            System.out.println("remember, you can move up to:"+numberOfMoves);

            int chosen = br.nextInt();
            br.close();
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


    private class AskShootReloadMove extends Thread {
        @Override
        public void run() {
            int numberOfMoves;
            numberOfMoves=1;
            Scanner br = new Scanner(System.in);

            System.out.println("<CLIENT> Do you want to:\n" +
                    "   0) Press 0 if you wanna move, reload and shoot\n" +
                    "   1) Press 1 if you want to stay still, reload and shot\n" +
                    "   2) Press 2 if you wanna "+
                    "   ");
            System.out.println("remember, you can move up to:"+numberOfMoves);

            int chosen = br.nextInt();
            ViewControllerEventInt VCEint = new ViewControllerEventInt(chosen);
            br.close();
            sendToServer(VCEint);
        }
    }
    @Override
    public void askShootReloadMove(){
        AskShootReloadMove asrm = new AskShootReloadMove();
        asrm.start();
    }


    private class AskWhatWep extends Thread {
        private ArrayList<WeaponCard> loadedCardInHand;
        public AskWhatWep(ArrayList<WeaponCard> loadedCardInHand){
            this.loadedCardInHand = loadedCardInHand;
        }
        @Override
        public void run() {
            Scanner br = new Scanner(System.in);

            System.out.println("<CLIENT> What weapon do you want to use?");
            for (int i = 0; i < loadedCardInHand.size() ; i++) {
                System.out.println("   " + i + ") " + loadedCardInHand.get(i).getID());
            }

            int chosen = br.nextInt();
            br.close();
            ViewControllerEventInt VCEint = new ViewControllerEventInt(chosen);

            sendToServer(VCEint);
        }
    }
    @Override
    public void askWhatWep(ArrayList<WeaponCard> loadedCardInHand) {
        AskWhatWep aww = new AskWhatWep(loadedCardInHand);
        aww.start();
    }

    private class AskWhatEffect extends Thread {
        private ArrayList<Effect> possibleEffects;
        public AskWhatEffect(ArrayList<Effect> possibleEffects){
            this.possibleEffects = possibleEffects;
        }
        @Override
        public void run() {
            Scanner br = new Scanner(System.in);

            System.out.println("<CLIENT> What Effect do you want to use?");
            for (int i = 0; i < possibleEffects.size() ; i++) {
                System.out.println("   " + i + ") " + possibleEffects.get(i).getEffectName());
            }

            int chosen = br.nextInt();
            br.close();
            ViewControllerEventInt VCEint = new ViewControllerEventInt(chosen);

            sendToServer(VCEint);
        }
    }
    @Override
    public void askWhatEffect(ArrayList<Effect> possibleEffects) {
        AskWhatEffect awe = new AskWhatEffect(possibleEffects);
        awe.start();
    }


    private class AskEffectInputs extends Thread{
        private List<EffectInfoType> effectInputs;

        public AskEffectInputs(List<EffectInfoType> effectInputs){
            this.effectInputs = effectInputs;
        }

        public List<Object> askForPlayer(){
            List<Object> listOfString = new ArrayList<>();
            Scanner br = new Scanner(System.in);
            System.out.println("         Possible players: ");
            for (int i = 0; i < ViewModelGate.getModel().getPlayers().getPlayers().size() ; i++) {
                System.out.println("         " + i + ") " + ViewModelGate.getModel().getPlayers().getPlayers().get(i).getNickname());
            }
            int chosen = br.nextInt();
            br.close();
            listOfString.add(ViewModelGate.getModel().getPlayers().getPlayers().get(chosen));
            return listOfString;
        }

        public List<Object> askForPlayerOrNOt(){
            System.out.println("<CLIENT> Do you want to select a player? [Y/N]");
            Scanner br = new Scanner(System.in);
            String decision = br.nextLine();
            List<Object> listOfString;
            br.close();
            if(decision.equalsIgnoreCase("y")){
                listOfString=askForPlayer();
            }
            else{
                listOfString = new ArrayList<>();
                System.out.println("<CLIENT> You decided to not select another player.");
            }
            return listOfString;
        }

        public List<Object> askForNPlayer(){
            Scanner br = new Scanner(System.in);
            List<Object> listOfString = new ArrayList<>();
            while(true) {
                System.out.println("<CLIENT> Do you want to select a player? [Y/N]");
                String decision = br.nextLine();
                br.close();
                if (decision.equalsIgnoreCase("y")) {
                    listOfString.addAll(askForPlayerOrNOt());
                } else {
                    System.out.println("<CLIENT> You decided to not select another player.");
                    return listOfString;
                }
            }
        }

        public List<Object> askForSquare(){
            List<Object> listOfString = new ArrayList<>();
            Scanner br = new Scanner(System.in);
            System.out.println("         Possible squares: ");
            for (int i = 0; i < ViewModelGate.getModel().getBoard().getMap().length ; i++) {
                for (int j = 0; j < ViewModelGate.getModel().getBoard().getMap()[0].length; j++) {
                    if(ViewModelGate.getModel().getBoard().getMap()[i][j]!= null) {
                        System.out.println("         " + "[" + i + "][" + j + "] " + ViewModelGate.getModel().getBoard().getMap()[i][j].getSquareType());
                    }
                }
            }
            System.out.println("<CLIENT> set X:");
            int X = br.nextInt();
            System.out.println("<CLIENT> set Y:");
            int Y = br.nextInt();
            br.close();
            SquareV square = ViewModelGate.getModel().getBoard().getMap()[X][Y];
            listOfString.add(square);
            return listOfString;
        }

        public List<Object> askForNSquare(){
            List<Object> listOfString = askForSquare();
            while(true) {
                System.out.println("<CLIENT> Do you want to select another Square? [Y/N]");
                Scanner br = new Scanner(System.in);
                String decision = br.nextLine();
                br.close();
                if (decision.equalsIgnoreCase("y")) {
                    listOfString.addAll(askForSquare());
                } else {
                    System.out.println("<CLIENT> You decided to not select another square.");
                    return listOfString;
                }
            }
        }

        @Override
        public void run() {
            List<List<Object>> userInputs = new ArrayList<>();
            List<Object> tempList = new ArrayList<>();
            String s ="";
            for (int i = 0; i < this.effectInputs.size() ; i++) {
                switch(this.effectInputs.get(i)){
                    case player:
                        System.out.println("<CLIENT> EffectInputType is: " + this.effectInputs.get(i));
                        System.out.println("         No input required.");
                        break;

                    case singleTarget:
                        System.out.println("<CLIENT> EffectInputType is: " + this.effectInputs.get(i));
                        userInputs.add(askForPlayer());
                        break;

                    case twoTargets:
                        System.out.println("<CLIENT> EffectInputType is: " + this.effectInputs.get(i));
                        tempList = askForPlayer();
                        tempList.addAll(askForPlayerOrNOt());
                        userInputs.add(tempList);
                        break;

                    case playerSquare:
                        System.out.println("<CLIENT> EffectInputType is: " + this.effectInputs.get(i));
                        System.out.println("         No input required.");
                        break;

                    case threeTargets:
                        System.out.println("<CLIENT> EffectInputType is: " + this.effectInputs.get(i));
                        tempList = askForPlayer();
                        tempList.addAll(askForPlayerOrNOt());
                        tempList.addAll(askForPlayerOrNOt());
                        userInputs.add(tempList);
                        break;

                    case squareByTarget:
                        System.out.println("<CLIENT> EffectInputType is: " + this.effectInputs.get(i));
                        userInputs.add(askForSquare());
                        break;

                    case targetListByRoom:
                        System.out.println("<CLIENT> EffectInputType is: " + this.effectInputs.get(i));
                        userInputs.add(askForPlayer());
                        break;

                    case simpleSquareSelect:
                        System.out.println("<CLIENT> EffectInputType is: " + this.effectInputs.get(i));
                        userInputs.add(askForSquare());
                        break;

                    case targetListBySquare:
                        System.out.println("<CLIENT> EffectInputType is: " + this.effectInputs.get(i));
                        userInputs.add(askForPlayer());
                        break;

                    case singleTargetBySquare:
                        System.out.println("<CLIENT> EffectInputType is: " + this.effectInputs.get(i));
                        userInputs.add(askForPlayer());
                        break;

                    case targetListByDistance1:
                        System.out.println("<CLIENT> EffectInputType is: " + this.effectInputs.get(i));
                        userInputs.add(askForPlayer());
                        break;

                    case squareByLastTargetSelected:
                        System.out.println("<CLIENT> EffectInputType is: " + this.effectInputs.get(i));
                        userInputs.add(askForSquare());
                        break;

                    case targetListBySameSquareOfPlayer:
                        System.out.println("<CLIENT> EffectInputType is: " + this.effectInputs.get(i));
                        System.out.println("         No input required.");
                        break;

                    case targetListBySquareOfLastTarget:
                        System.out.println("<CLIENT> EffectInputType is: " + this.effectInputs.get(i));
                        userInputs.add(askForPlayer());
                        break;

                    case singleRoom:
                        System.out.println("<CLIENT> EffectInputType is: " + this.effectInputs.get(i));
                        System.out.println("         No input required.");
                        break;

                    case multipleTargets:
                        System.out.println("<CLIENT> EffectInputType is: " + this.effectInputs.get(i));
                        userInputs.add(askForNPlayer());
                        break;

                    case multipleSquareSelect:
                        System.out.println("<CLIENT> EffectInputType is: " + this.effectInputs.get(i));
                        userInputs.add(askForNSquare());
                        break;

                    case squareOfLastTargetSelected:
                        System.out.println("<CLIENT> EffectInputType is: " + this.effectInputs.get(i));
                        System.out.println("         No input required.");
                        break;

                    case targetBySameSquareOfPlayer:
                        System.out.println("<CLIENT> EffectInputType is: " + this.effectInputs.get(i));
                        System.out.println("         No input required.");
                        break;

                    case targetListByCardinalDirection:
                        System.out.println("<CLIENT> EffectInputType is: " + this.effectInputs.get(i));
                        userInputs.add(askForPlayer());
                        break;

                    case twoTargetsByCardinalDirection:
                        System.out.println("<CLIENT> EffectInputType is: " + this.effectInputs.get(i));
                        userInputs.add(askForPlayer());
                        break;

                    case singleTargetByCardinalDirection:
                        System.out.println("<CLIENT> EffectInputType is: " + this.effectInputs.get(i));
                        userInputs.add(askForPlayer());
                        break;

                    case singleTargetBySameSquareOfPlayer:
                        System.out.println("<CLIENT> EffectInputType is: " + this.effectInputs.get(i));
                        userInputs.add(askForPlayer());
                        break;

                    case targetListByLastTargetSelectedSquare:
                        System.out.println("<CLIENT> EffectInputType is: " + this.effectInputs.get(i));
                        System.out.println("         No input required.");
                        break;

                    default:
                        try {
                            throw new Exception("Uknown EffectInfoType");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                }
            }
            System.out.println("<CLIENT> Stop requesting inputs for the effect.");

            System.out.println("<CLIENT> Your inputs were:");
            String print ="";
            PlayerV tempPlayerV;
            SquareV tempSquareV;
            for (int i = 0; i < userInputs.size(); i++) {
                for (Object inp : userInputs.get(i)) {
                    if(inp.getClass().toString().contains("PlayerV")){
                        tempPlayerV = (PlayerV)inp;
                        System.out.println("         " + tempPlayerV.getNickname());
                    }
                    else{
                        tempSquareV = (SquareV)inp;
                        System.out.println("         [" + tempSquareV.getX() + "][" + tempSquareV.getY() + "]");
                    }
                }
                System.out.println(print);
                print="";
            }

            System.out.println("<CLIENT> Sending inputs to Server.");
            ViewControllerEventListOfListOfObject VCEListOfString= new ViewControllerEventListOfListOfObject(userInputs);
            sendToServer(VCEListOfString);
        }
    }
    @Override
    public void askEffectInputs(List<EffectInfoType> effectInputs) {
        AskEffectInputs aei = new AskEffectInputs(effectInputs);
        aei.start();
    }

}

