package it.polimi.se2019.view;

import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.enumerations.PlayersColors;
import it.polimi.se2019.model.events.*;
import it.polimi.se2019.networkHandler.NetworkHandler;
import it.polimi.se2019.networkHandler.Socket.SocketNetworkHandler;
import it.polimi.se2019.virtualView.Selector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class ViewSelector implements Selector {

    @Override
    public void askGameSetUp() {

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

        ViewControllerEventGameSetUp VCEGameSetUp = new ViewControllerEventGameSetUp(gameMode,mapChoice,numberOfStartingSkulls,isFinalFrenzy,isBotActive);
        try {
            SocketNetworkHandler.oos.writeObject(VCEGameSetUp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askPlayerSetUp() {
        BufferedReader br = null;
        String nickaname = "defaultUserName";
        PlayersColors color = PlayersColors.purple;
        try {
            System.out.println("<CLIENT> Insert your nickname: ");
            br = new BufferedReader(new InputStreamReader(System.in));
            nickaname = br.readLine();
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
        ViewControllerEventPlayerSetUp VCEPlayerSetUp = new ViewControllerEventPlayerSetUp(nickaname, color);
        try {
            SocketNetworkHandler.oos.writeObject(VCEPlayerSetUp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askFirstSpawnPosition(ArrayList<PowerUpCard> powerUpCards) {
        System.out.println("<CLIENT> choose the PowerUp to discard and spawn to: ");
        for (int i = 0; i < powerUpCards.size(); i++) {
            System.out.println("    " + powerUpCards.get(i).getID() + ": " + powerUpCards.get(i).getColor());
        }

        Scanner br = new Scanner(System.in);
        String cardID = br.nextLine();

        ViewControllerEventString VCEString = new ViewControllerEventString(cardID);
        try {
            SocketNetworkHandler.oos.writeObject(VCEString);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void askTurnAction(int actionNumber) {
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
        try {
            SocketNetworkHandler.oos.writeObject(VCEString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askRunAroundPosition(ArrayList<Position> positions) {
        System.out.println("<CLIENT> choose where to move: ");
        for (int i = 0; i < positions.size(); i++) {
            Position pos = positions.get(i);
            System.out.println("    " + i + ") X:"+  pos.getX() + " Y:" + pos.getY());
        }
        Scanner br = new Scanner(System.in);

        int choosenPosition = br.nextInt();

        ViewControllerEventPosition VCEPosition = new ViewControllerEventPosition(positions.get(choosenPosition).getX(),positions.get(choosenPosition).getY());
        try {
            SocketNetworkHandler.oos.writeObject(VCEPosition);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askGrabStuffAction() {
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
        ViewControllerEventString VCEString = new ViewControllerEventString(action);
        try {
            SocketNetworkHandler.oos.writeObject(VCEString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askGrabStuffMove(ArrayList<Position> positions) {
        askRunAroundPosition(positions);
    }

    @Override
    public void askGrabStuffGrabWeapon(ArrayList<WeaponCard> toPickUp) {
        System.out.println("Choose number to pick up:");

        Scanner br = new Scanner(System.in);

        for (int i = 0; i < toPickUp.size(); i++) {
            System.out.println( "   " +i + ") " + toPickUp.get(i).getID() + ":\n" + toPickUp.get(i).getPickUpCost());
        }

        String toPickUpID = toPickUp.get(br.nextInt()).getID();

        ViewControllerEventString VCEString = new ViewControllerEventString(toPickUpID);
        try {
            SocketNetworkHandler.oos.writeObject(VCEString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askGrabStuffSwitchWeapon(ArrayList<WeaponCard> toPickUp, ArrayList<WeaponCard> toSwitch) {
        System.out.println("you already have the maximum quantity of weapon you can hold.");
        System.out.println("Choose one to pick up and one to discard.");
        System.out.println("To pick up:");
        for (int i = 0; i < toPickUp.size(); i++) {
            System.out.println( "   " +i + ") " + toPickUp.get(i).getID() + ":\n" + toPickUp.get(i).getPickUpCost().getAmmoCubesList().toString());
        }

        Scanner br = new Scanner(System.in);
        int choosenToPickUp = br.nextInt();

        String toPickUpID = toPickUp.get(choosenToPickUp).getID();

        System.out.println("Switch with:");
        for (int i = 0; i < toSwitch.size(); i++) {
            System.out.println( "   " +i + ") " + toSwitch.get(i).getID());
        }

        int choosenToDiscard= br.nextInt();

        String toDiscardID = toSwitch.get(choosenToDiscard).getID();

        ViewControllerEventTwoString VCETwoString = new ViewControllerEventTwoString(toPickUpID, toDiscardID);
        try {
            SocketNetworkHandler.oos.writeObject(VCETwoString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askPowerUpToDiscard(ArrayList<PowerUpCard> toDiscard) {

    }

    @Override
    public void askIfReload() {

    }

    @Override
    public void askWhatReaload(ArrayList<WeaponCard> toReload) {

    }

    @Override
    public void askSpawn(ArrayList<PowerUpCard> powerUpCards) {

    }
}
