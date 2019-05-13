package it.polimi.se2019.view;

import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.enumerations.PlayersColors;
import it.polimi.se2019.model.events.ViewControllerEventGameSetUp;
import it.polimi.se2019.model.events.ViewControllerEventPlayerSetUp;
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

    }

    @Override
    public void askTurnAction(int actionNumber) {

    }

    @Override
    public void askRunAroundPosition(ArrayList<Position> positions) {

    }

    @Override
    public void askGrabStuffAction() {

    }

    @Override
    public void askGrabStuffMove(ArrayList<Position> positions) {

    }

    @Override
    public void askGrabStuffGrabWeapon(ArrayList<WeaponCard> toPickUp) {

    }

    @Override
    public void askGrabStuffSwitchWeapon(ArrayList<WeaponCard> toPickUp, ArrayList<WeaponCard> toSwitch) {

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
