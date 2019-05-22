package it.polimi.se2019.view;

import it.polimi.se2019.model.enumerations.EventTypes;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.model.enumerations.SpawnPointDamageTypes;
import it.polimi.se2019.model.events.*;
import it.polimi.se2019.virtualView.Selector;

import java.util.Observable;
import java.util.Observer;

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
            //TODO
        } else if(arg.getClass().toString().contains("SelectorEvent")){
            SE = (SelectorEvent)arg;
            this.callCorrectSelector(SE);
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
