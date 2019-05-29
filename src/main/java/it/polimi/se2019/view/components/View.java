package it.polimi.se2019.view.components;

import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.PlayersColors;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.model.events.selectorEvents.*;
import it.polimi.se2019.view.selector.ViewSelector;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {

    private ViewSelector selector;

    private String networkConnection;

    private String userInterface;


    public View(String networkConnection, String userInterface){
        this.userInterface = userInterface;
        this.networkConnection = networkConnection;
        this.selector = new ViewSelector(networkConnection);
        ViewModelGate.setModel(new GameV());
    }

    @Override
    public void update(Observable o, Object arg){
        SelectorEvent SE = null;
        ModelViewEvent MVE = null;
        if(arg.getClass().toString().contains("ModelViewEvent")){
            MVE = (ModelViewEvent)arg;
            try{
                this.callCorrectComponent(MVE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(arg.getClass().toString().contains("SelectorEvent")){
            SE = (SelectorEvent)arg;
            this.callCorrectSelector(SE);
        }
    }

    public void callCorrectComponent(ModelViewEvent MVE) throws Exception {
        //Component name
        String componentClassName = MVE.getComponent().getClass().toString();
        String[] parts = componentClassName.split("\\.");
        componentClassName = parts[componentClassName.length()-1];

        //data
        String information = MVE.getInformation();

        //System.out.println("                                        <CLIENT> The " + className + " in the server has changed and it has sent this object: " + classNameArg);

        switch (componentClassName){/*
            case "GameV":
                if(classNameArg.equals("String")){
                    String arg = (String)MVE.getArg();
                    Game obs = (Game)MVE.getO();
                    if(arg.contains("FINAL FRENZY SETTED")){
                        if(arg.contains("true")) {
                            ViewModelGate.model = obs;
                            if(this.userInterface.equals("CLI")) {
                                System.out.println("<CLIENT> FF SET TO TRUE");
                            }
                            else{
                                //UPDATE GUI
                            }
                        }
                        else{
                            ViewModelGate.model = obs;
                            if(this.userInterface.equals("CLI")) {
                                System.out.println("<CLIENT> FF SET TO FALSE");
                            }
                            else{
                                //UPDATE GUI
                            }
                        }
                    }
                    else if(arg.contains("FINAL FRENZY HAS BEGUN")){
                        if(arg.contains("true")) {
                            ViewModelGate.model = obs;
                            if(this.userInterface.equals("CLI")) {
                                System.out.println("<CLIENT> FF BEGUN");
                            }
                            else{
                                //UPDATE GUI
                            }
                        }
                        else{
                            ViewModelGate.model = obs;
                            if(this.userInterface.equals("CLI")) {
                                System.out.println("<CLIENT> FF NOT BEGUN");
                            }
                            else{
                                //UPDATE GUI
                            }
                        }
                    }
                }
                else if(classNameArg.equals("KillshotTrack")) {
                    KillShotTrack arg = (KillShotTrack) MVE.getArg();
                    ViewModelGate.model.setKillshotTrack(arg);
                    if(this.userInterface.equals("CLI")) {
                        System.out.println("<CLIENT> KILLSHOTTRACK SETTED");
                    }
                    else{
                        //UPDATE GUI
                    }

                }
                else if(classNameArg.equals("PlayersList")) {
                    PlayersList arg = (PlayersList)MVE.getArg();
                    ViewModelGate.model.setPlayerList(arg);
                    if(this.userInterface.equals("CLI")) {
                        System.out.println("<CLIENT> PLAYERLIST SETTED");

                    }
                    else{
                        //UPDATE GUI
                    }
                }
                else if(classNameArg.equals("Board")) {
                    Board arg = (Board)MVE.getArg();
                    ViewModelGate.model.setBoard(arg);
                    if(this.userInterface.equals("CLI")) {
                        System.out.println("<CLIENT> BOARD SETTED");
                    }
                    else{
                        //UPDATE GUI
                    }
                }
                break;

            case "KillShotTrackV":
                //MVE.getArg() is null
                if(this.userInterface.equals("CLI")) {
                    ViewModelGate.model.setKillshotTrack((KillShotTrack) MVE.getO());
                    if (this.userInterface.equals("CLI")) {
                        System.out.println("<CLIENT> DEATH OF A PLAYER");
                    } else {
                        //UPDATE GUI
                    }
                }
                break;

            case "OrderedCardListV":
                if(classNameArg.equals("WeaponCard")){
                    if(this.userInterface.equals("CLI")) {
                        System.out.println("<CLIENT> CARD HAS BEEN ADDED TO WEAPONCARD DECK, how can i know what ordered card list has changed???");
                    }
                    else{
                        //UPDATE GUI
                    }
                }
                else if(classNameArg.equals("PowerUpCard")){
                    if(this.userInterface.equals("CLI")) {
                        System.out.println("<CLIENT> CARD HAS BEEN ADDED TO POWER UP DECK, how can i know what ordered card list has changed???");
                    }
                    else{
                        //UPDATE GUI
                    }
                }
                else if(classNameArg.equals("AmmoCard")){
                    if(this.userInterface.equals("CLI")) {
                        System.out.println("<CLIENT> CARD HAS BEEN ADDED TO AMMO CARD DECK, how can i know what ordered card list has changed???");
                    }
                    else{
                        //UPDATE GUI
                    }
                }
                else if(classNameArg.equals("OrderedCardList")){
                    if(this.userInterface.equals("CLI")) {
                        System.out.println("<CLIENT> ONE OR MORE CARDS HAS BEEN MOVED, how can i know what ordered card list has changed???");
                    }
                    else{
                        //UPDATE GUI
                    }
                }
                else if(classNameArg.equals("null")){
                    //MVE.getArg() is null
                    if(this.userInterface.equals("CLI")) {
                        System.out.println("<CLIENT> ORDERED CARD LIST HAS BEEN SHUFFLED, how can i know what ordered card list has changed???");
                    }
                    else{
                        //UPDATE GUI
                    }
                }
                break;

            case "PlayerV":
                if(classNameArg.equals("PlayersColors")){
                    PlayersColors arg = (PlayersColors)MVE.getArg();
                    Player obs = (Player)MVE.getO();
                    ViewModelGate.model.getPlayerList().overwritePlayer(obs.getNickname(), obs);
                    if(this.userInterface.equals("CLI")) {
                        System.out.println("<CLIENT> PLAYER'S COLOR HAS BEEN SET");
                    }
                    else{
                        //UPDATE GUI
                    }
                }
                else if(classNameArg.equals("String")){
                    String arg = (String)MVE.getArg();
                    Player obs = (Player)MVE.getO();
                    if(arg.equals("DEATH")){
                        ViewModelGate.model.getPlayerList().getPlayer(obs.getNickname()).addDeath();
                        if(this.userInterface.equals("CLI")) {
                            System.out.println("<CLIENT> PLAYER'S DEATH COUNTER++");
                        }
                        else{
                            //UPDATE GUI
                        }

                    }
                    else if(arg.equals("FINAL FRENZY BOARD SETTED")){
                        ViewModelGate.model.getPlayerList().getPlayer(obs.getNickname()).makePlayerBoardFinalFrenzy();
                        if(this.userInterface.equals("CLI")) {
                            System.out.println("<CLIENT> PLAYER'S FINAL FRENZY BOARD HAS BEEN SET");
                        }
                        else{
                            //UPDATE GUI
                        }
                    }
                    else {
                        String oldName = arg.split("_")[1];
                        String newName = arg.split("_")[0];
                        ViewModelGate.model.getPlayerList().getPlayer(oldName).setNickname(newName);
                        if(this.userInterface.equals("CLI")) {
                            System.out.println("<CLIENT> PLAYER'S NICKNAME HAS BEEN SET");
                        }
                        else{
                            //UPDATE GUI
                        }
                    }
                }
                else if(classNameArg.equals("Position")){
                    Player obs = (Player)MVE.getO();
                    Position arg = (Position)MVE.getArg();
                    ViewModelGate.model.getPlayerList().getPlayer(obs.getNickname()).setPosition(arg);
                    if(this.userInterface.equals("CLI")) {
                        System.out.println("<CLIENT> PLAYER'S POSITION HAS BEEN SET");
                    }
                    else{
                        //UPDATE GUI
                    }
                }
                else if(classNameArg.equals("Integer")){
                    Player obs = (Player)MVE.getO();
                    Integer arg = (Integer)MVE.getArg();
                    ViewModelGate.model.getPlayerList().getPlayer(obs.getNickname()).setScore(arg);
                    if(this.userInterface.equals("CLI")) {
                        System.out.println("<CLIENT> PLAYER'S SCORE HAS BEEN SET");
                    }
                    else{
                        //UPDATE GUI
                    }
                }
                else if(classNameArg.equals("AmmoList")){
                    Player obs = (Player)MVE.getO();
                    AmmoList arg = (AmmoList)MVE.getArg();
                    ViewModelGate.model.getPlayerList().getPlayer(obs.getNickname()).getBoard().setAmmoBox(arg);
                    if(this.userInterface.equals("CLI")) {
                        System.out.println("<CLIENT> PLAYER'S AMMO BOX HAS CHANGED (ADDED OR REMOVED AMMO CUBES...)");
                    }
                    else{
                        //UPDATE GUI
                    }
                }
                else if(classNameArg.equals("DamageTracker")){
                    Player obs = (Player)MVE.getO();
                    DamagesTracker arg = (DamagesTracker)MVE.getArg();
                    ViewModelGate.model.getPlayerList().getPlayer(obs.getNickname()).getBoard().setDamagesTracker(arg);
                    if(this.userInterface.equals("CLI")) {
                        System.out.println("<CLIENT> PLAYER'S DAMAGE TRACKER HAS CHANGED (RESET OR ADDED DAMAGES)");
                    }
                    else{
                        //UPDATE GUI
                    }

                }
                else if(classNameArg.equals("MarksTracker")){
                    Player obs = (Player)MVE.getO();
                    MarksTracker arg = (MarksTracker)MVE.getArg();
                    ViewModelGate.model.getPlayerList().getPlayer(obs.getNickname()).getBoard().setMarksTracker(arg);
                    if(this.userInterface.equals("CLI")) {
                        System.out.println("<CLIENT> PLAYER'S MARKS TRACKER HAS CHANGED (ADDED OR DELETED MARKS)");
                    }
                    else{
                        //UPDATE GUI
                    }
                }
                break;

            case "PlayersListV":
                if(classNameArg.equals("String")){
                    String arg = (String)MVE.getArg();
                    PlayersList obs = (PlayersList)MVE.getO();
                    if(arg.equals("CURRENT PLAYING PLAYER")){
                        ViewModelGate.model.setPlayerList(obs);
                        if(this.userInterface.equals("CLI")) {
                            System.out.println("<CLIENT> CURRENT PLAYING PLAYER HAS BEEN SETTED");
                        }
                        else{
                            //UPDATE GUI
                        }
                    }
                    else if(arg.equals("STARTING PLAYER")){
                        ViewModelGate.model.setPlayerList(obs);
                        if(this.userInterface.equals("CLI")) {
                            System.out.println("<CLIENT> STARTING PLAYING PLAYER HAS BEEN SETTED");
                        }
                        else{
                            //UPDATE GUI
                        }
                    }
                    else if(arg.equals("NEXT PLAYING PLAYER")){
                        ViewModelGate.model.setPlayerList(obs);
                        if(this.userInterface.equals("CLI")) {
                            System.out.println("<CLIENT> CURRENT PLAYING PLAYER HAS CHANGED AS NExT PLAYING PLAYER");
                        }
                        else{
                            //UPDATE GUI
                        }
                    }
                    else if(arg.equals("NEW PLAYER")){
                        ViewModelGate.model.setPlayerList(obs);
                        if(this.userInterface.equals("CLI")) {
                            System.out.println("<CLIENT> NEW PLAYER HAS BEEN ADDED TO THE PLAYERS LIST");
                        }
                        else {
                            //UPDATE GUI
                        }
                    }
                }
                break;

            default:
                throw new Exception("<CLIENT> MVE NOT RECOGNIZED");
            */

        }

    }

    public void callCorrectSelector(SelectorEvent SE){
        SelectorEventTypes SET = SE.getSelectorEventTypes();
        switch (SET) {
            case askGameSetUp:
                this.selector.askGameSetUp();
                break;

            case askPlayerSetUp:
                this.selector.askPlayerSetUp();
                break;

            case askFirstSpawnPosition:
                this.selector.askFirstSpawnPosition(((SelectorEventPowerUpCards)SE).getPowerUpCards());
                break;

            case askTurnAction:
                this.selector.askTurnAction(((SelectorEventInt)SE).getNumber());
                break;

            case askRunAroundPosition:
                this.selector.askRunAroundPosition(((SelectorEventPositions)SE).getPositions());
                break;

            case askGrabStuffAction:
                this.selector.askGrabStuffAction();
                break;

            case askGrabStuffMove:
                this.selector.askGrabStuffMove(((SelectorEventPositions)SE).getPositions());
                break;

            case askGrabStuffGrabWeapon:
                this.selector.askGrabStuffGrabWeapon(((SelectorEventWeaponCards)SE).getWeaponCards());

                break;

            case askGrabStuffSwitchWeapon:
                this.selector.askGrabStuffSwitchWeapon(((SelectorEventDoubleWeaponCards)SE).getWeaponCards1(), ((SelectorEventDoubleWeaponCards)SE).getWeaponCards2());
                break;

            case askPowerUpToDiscard:
                this.selector.askPowerUpToDiscard(((SelectorEventPowerUpCards)SE).getPowerUpCards());
                break;

            case askWhatReaload:
                this.selector.askWhatReaload(((SelectorEventWeaponCards)SE).getWeaponCards());
                break;

            case askSpawn:
                this.selector.askSpawn(((SelectorEventPowerUpCards)SE).getPowerUpCards());
                break;

            case askShootOrMove:
                this.selector.askShootOrMove();
                break;

            default: break;
        }
    }
}
