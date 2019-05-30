package it.polimi.se2019.view.components;

import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.ModelViewEventTypes;
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
        ModelViewEventTypes information = MVE.getInformation();

        switch (information){
            //from Game class
            case setFinalFrenzy:
                ViewModelGate.getModel().setFinalFrenzy((boolean)MVE.getComponent());
                break;
            case finalFrenzyBegun:
                ViewModelGate.getModel().setHasFinalFrenzyBegun((boolean)MVE.getComponent());
                break;
            case newKillshotTrack:
                ViewModelGate.getModel().setKillshotTrack(new KillShotTrackV());
                ViewModelGate.getModel().getKillshotTrack().setNumberOfStartingSkulls((int)MVE.getComponent());
            case newPlayersList:
                ViewModelGate.getModel().setPlayers((PlayersListV)MVE.getComponent());

            //from KillshotTrack class
            case deathOfPlayer:
                ViewModelGate.getModel().setKillshotTrack((KillShotTrackV)MVE.getComponent());
                break;

            //from OrderedCardList class
            case movingFknCardsAround:
                //TODO
                break;
            case shufflingFknCardsAround:
                //TODO
                break;

            //from Player class or Person class
            case newColor:
                if (ViewModelGate.getModel().getPlayers() != null) {
                    for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                        if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                            p.setColor((PlayersColors) MVE.getComponent());
                            break;
                        }
                    }
                }
                break;
            case newNickname:
                if (ViewModelGate.getModel().getPlayers() != null) {
                    for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                        if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                            p.setNickname((String) MVE.getComponent());
                            break;
                        }
                    }
                }
                break;
            case newPosition:
                if (ViewModelGate.getModel().getPlayers() != null) {
                    for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                        if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                            p.setX(((Position) MVE.getComponent()).getX());
                            p.setY(((Position) MVE.getComponent()).getY());
                            break;
                        }
                    }
                }
                break;
            case newScore:
                if (ViewModelGate.getModel().getPlayers() != null) {
                    for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                        if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                            p.setScore((int) MVE.getComponent());
                            break;
                        }
                    }
                }
                break;
            case addDeathCounter:
                if (ViewModelGate.getModel().getPlayers() != null) {
                    for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                        if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                            p.setNumberOfDeaths((int) MVE.getComponent());
                            break;
                        }
                    }
                }
                break;
            case setFinalFrenzyBoard:
                if (ViewModelGate.getModel().getPlayers() != null) {
                    for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                        if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                            p.setHasFinalFrenzyBoard((boolean) MVE.getComponent());
                            break;
                        }
                    }
                }
                break;
            case newAmmoBox:
                if (ViewModelGate.getModel().getPlayers() != null) {
                    for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                        if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                            p.setAmmoBox((AmmoListV) MVE.getComponent());
                            break;
                        }
                    }
                }
                break;
            case newDamageTracker:
                if (ViewModelGate.getModel().getPlayers() != null) {
                    for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                        if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                            p.setDamageTracker((DamageTrackerV) MVE.getComponent());
                            break;
                        }
                    }
                }
                break;
            case newMarksTracker:
                if (ViewModelGate.getModel().getPlayers() != null) {
                    for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                        if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                            p.setMarksTracker((MarksTrackerV) MVE.getComponent());
                            break;
                        }
                    }
                }
                break;

            //from PlayerList class
            case setCurrentPlayingPlayer:
                if (ViewModelGate.getModel().getPlayers() != null) {
                    for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                        if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                            ViewModelGate.getModel().getPlayers().setCurrentPlayingPlayer(p.getNickname());
                            break;
                        }
                    }
                }
                break;
            case setStartingPlayer:
                if(ViewModelGate.getModel().getPlayers() != null) {
                    for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                        if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                            ViewModelGate.getModel().getPlayers().setStartingPlayer(p.getNickname());
                            break;
                        }
                    }
                }
                break;
            case newPlayer:
                ViewModelGate.getModel().getPlayers().getPlayers().add((PlayerV)MVE.getComponent());

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
