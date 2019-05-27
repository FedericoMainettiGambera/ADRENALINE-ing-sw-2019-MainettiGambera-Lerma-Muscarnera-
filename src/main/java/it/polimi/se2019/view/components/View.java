package it.polimi.se2019.view.components;

import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.model.events.selectorEvents.*;
import it.polimi.se2019.view.selector.ViewSelector;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {

    private Player player;

    private Map map;

    private GameTrack gameTrack;

    private ViewSelector selector;

    private String networkConnection;


    public View(String networkConnection){
        this.networkConnection = networkConnection;
        this.selector = new ViewSelector(networkConnection);
        this.player=new Player();
        this.map=new Map();
        this.gameTrack=new GameTrack();
    }

    @Override
    public void update(Observable o, Object arg){
        SelectorEvent SE = null;
        ModelViewEvent MVE = null;
        if(arg.getClass().toString().contains("ModelViewEvent")){
            MVE = (ModelViewEvent)arg;
            this.callCorrectComponent(MVE, o);
        } else if(arg.getClass().toString().contains("SelectorEvent")){
            SE = (SelectorEvent)arg;
            this.callCorrectSelector(SE);
        }
    }

    public void callCorrectComponent(ModelViewEvent MVE, Observable o){
        String className = MVE.getO().getClass().toString();
        String[] classNames = className.split("\\.");
        className = classNames[classNames.length-1]; //contains the name of the class.
        System.out.println("                                        <CLIENT> RECEIVED MVE FROM: " + className);

        String classNameArg = MVE.getO().getClass().toString();
        String[] classNamesArg = classNameArg.split("\\.");
        classNameArg = classNamesArg[classNamesArg.length-1]; //contains the name of the class.
        System.out.println("<CLIENT> The " + className + " in the server has changed and it has sent this object: " + classNameArg);

        switch (className){
            case "Game":
                System.out.println("<CLIENT> MVE RECOGNIZED: Game");
                //pass the MVE to the correct component in the view.
                break;
            case "KillShotTrack":
                System.out.println("<CLIENT> MVE RECOGNIZED: KillShotTrack");
                //pass the MVE to the correct component in the view.
                break;
            case "NormalSquare":
                System.out.println("<CLIENT> MVE RECOGNIZED: NormalSquare");
                //pass the MVE to the correct component in the view.
                break;
            case "OrderedCardList":
                System.out.println("<CLIENT> MVE RECOGNIZED: OrderedCardList");
                //pass the MVE to the correct component in the view.
                break;
            case "Player":
                System.out.println("<CLIENT> MVE RECOGNIZED: Player");
                //pass the MVE to the correct component in the view.
                break;
            case "PlayersList":
                System.out.println("<CLIENT> MVE RECOGNIZED: PlayersList");
                //pass the MVE to the correct component in the view.
                break;
            case "SpawnPointSquare":
                System.out.println("<CLIENT> MVE RECOGNIZED: SpawnPointSquare");
                //pass the MVE to the correct component in the view.
                break;
            default:
                System.out.println("<CLIENT> MVE RECOGNIZED.");
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

    public GameTrack getGameTrack() {
        return gameTrack;
    }

    public Player getPlayer() {
        return player;
    }

    public Map getMap() {
        return map;
    }
}
