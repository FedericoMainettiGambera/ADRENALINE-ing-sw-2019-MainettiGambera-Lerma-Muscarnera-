package it.polimi.se2019.view.components;

import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.model.events.*;
import it.polimi.se2019.view.ViewSelector;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static it.polimi.se2019.model.enumerations.SelectorEventTypes.askGrabStuffGrabWeapon;

public class View implements Observer {

    private Player player;

    private Map map;

    private GameTrack gameTrack;

    private ViewSelector selector;


    public View(){
        this.selector = new ViewSelector();
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
        String component = o.getClass().toString();
        String[] components = component.split(".");
        component = components[components.length-1]; //contains the name of the class.

        switch (component){
            case "Board":
                //pass the MVE to the correct component in the view.
            case "Player":
                //pass the MVE to the correct component in the view.
            case "DeathTracker":
                //pass the MVE to the correct component in the view.
            case "etc with all the model classes that sends Model view events...":
                //pass the MVE to the correct component in the view.
            default:
                //...
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
