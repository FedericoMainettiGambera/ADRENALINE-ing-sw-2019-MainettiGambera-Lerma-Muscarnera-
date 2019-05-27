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


    public View(String networkConnection){
        this.networkConnection = networkConnection;
        this.selector = new ViewSelector(networkConnection);
    }

    @Override
    public void update(Observable o, Object arg){
        SelectorEvent SE = null;
        ModelViewEvent MVE = null;
        if(arg.getClass().toString().contains("ModelViewEvent")){
            MVE = (ModelViewEvent)arg;
            try {
                this.callCorrectComponent(MVE, o);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if(arg.getClass().toString().contains("SelectorEvent")){
            SE = (SelectorEvent)arg;
            this.callCorrectSelector(SE);
        }
    }

    public void callCorrectComponent(ModelViewEvent MVE, Observable o) throws Exception {
        String className = MVE.getO().getClass().toString();
        String[] classNames = className.split("\\.");
        className = classNames[classNames.length-1]; //contains the name of the class.


        String classNameArg = "";
        if(MVE.getArg() != null) {
            classNameArg = MVE.getArg().getClass().toString();
            String[] classNamesArg = classNameArg.split("\\.");
            classNameArg = classNamesArg[classNamesArg.length - 1]; //contains the name of the class.
        }
        else{
            classNameArg = "null";
        }

        System.out.println("                                        <CLIENT> The " + className + " in the server has changed and it has sent this object: " + classNameArg);

        switch (className){
            case "Game":
                if(classNameArg.equals("String")){
                    String arg = (String)MVE.getArg();
                    if(arg.equals("FINAL FRENZY SETTED")){
                        System.out.println("<CLIENT> FF SETTED");
                    }
                    else if(arg.equals("FINAL FRENZY HAS BEGUN")){
                        System.out.println("<CLIENT> FF BEGUN");
                    }
                }
                else if(classNameArg.equals("KillshotTrack")) {
                    KillShotTrack arg = (KillShotTrack) MVE.getArg();
                    System.out.println("<CLIENT> KILLSHOTTRACK SETTED");
                }
                else if(classNameArg.equals("PlayersList")) {
                    PlayersList arg = (PlayersList)MVE.getArg();
                    System.out.println("<CLIENT> PLAYERLIST SETTED");
                }
                else if(classNameArg.equals("Board")) {
                    Board arg = (Board)MVE.getArg();
                    System.out.println("<CLIENT> BOARD SETTED");
                }
                break;

            case "KillShotTrack":
                //MVE.getArg() is null
                System.out.println("<CLIENT> DEATH OF A PLAYER");
                break;

            case "OrderedCardList":
                if(classNameArg.equals("WeaponCard")){
                    System.out.println("<CLIENT> CARD HAS BEEN ADDED TO WEAPONCARD DECK");
                }
                else if(classNameArg.equals("PowerUpCard")){
                    System.out.println("<CLIENT> CARD HAS BEEN ADDED TO POWER UP DECK");
                }
                else if(classNameArg.equals("AmmoCard")){
                    System.out.println("<CLIENT> CARD HAS BEEN ADDED TO AMMO CARD DECK");
                }
                else if(classNameArg.equals("OrderedCardList")){
                    System.out.println("<CLIENT> ONE OR MORE CARDS HAS BEEN MOVED TO ARG FROM O");
                }
                else if(classNameArg.equals("null")){
                    //MVE.getArg() is null
                    System.out.println("<CLIENT> O HAS BEEN SHUFFLED");
                }
                break;

            case "Player":
                if(classNameArg.equals("PlayersColors")){
                    PlayersColors arg = (PlayersColors)MVE.getArg();
                    System.out.println("<CLIENT> PLAYER'S COLOR HAS BEEN SETTED");
                }
                else if(classNameArg.equals("String")){
                    String arg = (String)MVE.getArg();
                    if(arg.equals("DEATH")){
                        System.out.println("<CLIENT> PLAYER'S DEATH COUNTER HAS BEEN UPDATED");
                    }
                    else if(arg.equals("FINAL FRENZY BOARD SETTED")){
                        System.out.println("<CLIENT> PLAYER'S FINAL FRENZY BOARD HAS BEEN SET");
                    }
                    else {
                        System.out.println("<CLIENT> PLAYER'S NICKNAME HAS BEEN SET");
                    }
                }
                else if(classNameArg.equals("Position")){
                    Position arg = (Position)MVE.getArg();
                    System.out.println("<CLIENT> PLAYER'S POSITION HAS BEEN SET");
                }
                else if(classNameArg.equals("Integer")){
                    Integer arg = (Integer)MVE.getArg();
                    System.out.println("<CLIENT> PLAYER'S SCORE HAS BEEN SET");
                }
                else if(classNameArg.equals("AmmoList")){
                    AmmoList arg = (AmmoList)MVE.getArg();
                    System.out.println("<CLIENT> PLAYER'S AMMO BOX HAS CHANGED (ADDED OR REMOVED AMMO CUBES...)");
                }
                else if(classNameArg.equals("DamageTracker")){
                    DamagesTracker arg = (DamagesTracker)MVE.getArg();
                    System.out.println("<CLIENT> PLAYER'S DAMAGE TRACKER HAS CHANGED (RESET OR ADDED DAMAGES)");
                }
                else if(classNameArg.equals("MarksTracker")){
                    MarksTracker arg = (MarksTracker)MVE.getArg();
                    System.out.println("<CLIENT> PLAYER'S MARKS TRACKER HAS CHANGED (ADDED OR DELETED MARKS)");
                }
                break;

            case "PlayersList":
                if(classNameArg.equals("String")){
                    String arg = (String)MVE.getArg();
                    if(arg.equals("CURRENT PLAYING PLAYER")){
                        System.out.println("<CLIENT> CURRENT PLAYING PLAYER HAS BEEN SETTED");
                    }
                    else if(arg.equals("STARTING PLAYER")){
                        System.out.println("<CLIENT> STARTING PLAYING PLAYER HAS BEEN SETTED");
                    }
                    else if(arg.equals("NEXT PLAYING PLAYER")){
                        System.out.println("<CLIENT> CURRENT PLAYING PLAYER HAS CHANGED AS NExT PLAYING PLAYER");
                    }
                    else if(arg.equals("NEW PLAYER")){
                        System.out.println("<CLIENT> NEW PLAYER HAS BEEN ADDED TO THE PLAYERS LIST");
                    }
                }
                break;

            default:
                throw new Exception("<CLIENT> MVE NOT RECOGNIZED");

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

            case askIfReload:
                this.selector.askIfReload();
                break;

            case askWhatReaload:
                this.selector.askWhatReaload(((SelectorEventWeaponCards)SE).getWeaponCards());
                break;

            case askSpawn:
                this.selector.askSpawn(((SelectorEventPowerUpCards)SE).getPowerUpCards());
                break;

            default: break;
        }
    }
}
