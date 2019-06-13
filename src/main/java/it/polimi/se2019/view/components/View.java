package it.polimi.se2019.view.components;

import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.enumerations.ModelViewEventTypes;
import it.polimi.se2019.model.enumerations.PlayersColors;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;
import it.polimi.se2019.model.events.selectorEvents.*;
import it.polimi.se2019.model.events.stateEvent.StateEvent;
import it.polimi.se2019.model.events.timerEvent.TimerEvent;
import it.polimi.se2019.networkHandler.RMI.RMINetworkHandler;
import it.polimi.se2019.networkHandler.Socket.SocketNetworkHandler;
import it.polimi.se2019.view.outputHandler.CLIOutputHandler;
import it.polimi.se2019.view.outputHandler.GUIOutputHandler;
import it.polimi.se2019.view.outputHandler.OutputHandlerGate;
import it.polimi.se2019.view.selector.ViewSelector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class View implements Observer {

    private ViewSelector selector;

    private String networkConnection;

    private String userInterface;


    public View(String networkConnection, String userInterface){
        this.userInterface = userInterface;

        OutputHandlerGate.setUserIterface(userInterface);

        if(userInterface.equals("CLI")){
            OutputHandlerGate.setCLIOutputHandler(new CLIOutputHandler());
        }
        else{
            OutputHandlerGate.setGUIOutputHandler(new GUIOutputHandler());
        }
        this.networkConnection = networkConnection;
        this.selector = new ViewSelector(networkConnection, userInterface);

        ViewModelGate.setModel(new GameV());
        ViewModelGate.getModel().setPlayers(new PlayersListV());
        ViewModelGate.getModel().getPlayers().setPlayers(new ArrayList<>());

        OutputHandlerGate.getCorrectOutputHandler(this.userInterface).gameCreated();

    }

    @Override
    public void update(Observable o, Object arg){
        SelectorEvent SE = null;
        ModelViewEvent MVE = null;
        StateEvent StE = null;
        TimerEvent TE = null;
        ReconnectionEvent RE = null;
        if(arg.getClass().toString().contains("ModelViewEvent")){
            MVE = (ModelViewEvent)arg;
            this.callCorrectComponent(MVE);
        }
        else if(arg.getClass().toString().contains("SelectorEvent")){
            SE = (SelectorEvent)arg;
            this.callCorrectSelector(SE);
        }
        else if(arg.getClass().toString().contains("StateEvent")){
            StE = (StateEvent)arg;

            OutputHandlerGate.getCorrectOutputHandler(this.userInterface).stateChanged(StE);
        }
        else if(arg.getClass().toString().contains("TimerEvent")){
            TE = (TimerEvent)arg;
            if(TE.getContext().equalsIgnoreCase("input")) {
                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).showInputTimer(TE.getCurrentTime(), TE.getTotalTime());
            }
            else{
                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).showConnectionTimer(TE.getCurrentTime(), TE.getTotalTime());
            }
        }
        else if(arg.getClass().toString().contains("ReconnectionEvent")){
            RE = (ReconnectionEvent)arg;
            this.selector.askReconnectionNickname(RE);
        }
        else{
            try {
                throw new Exception("Event not recognized");
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void callCorrectComponent(ModelViewEvent MVE)  {
        ModelViewEventTypes information = MVE.getInformation();

        switch (information){

            //from Game class
            case setFinalFrenzy:
                ViewModelGate.getModel().setFinalFrenzy((boolean)MVE.getComponent());

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).setFinalFrenzy(MVE);
                break;


            case finalFrenzyBegun:
                ViewModelGate.getModel().setHasFinalFrenzyBegun((boolean)MVE.getComponent());

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).finalFrenzyBegun(MVE);
                break;


            case newKillshotTrack:
                ViewModelGate.getModel().setKillshotTrack(new KillShotTrackV());
                ViewModelGate.getModel().getKillshotTrack().setNumberOfStartingSkulls((int)MVE.getComponent());

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).newKillshotTrack(MVE);
                break;


            case newPlayersList:
                ViewModelGate.getModel().setPlayers((PlayersListV)MVE.getComponent());
                if(ViewModelGate.getModel()==null){
                    ViewModelGate.setMe(((PlayersListV)MVE.getComponent()).getPlayers().get(((PlayersListV)MVE.getComponent()).getPlayers().size()-1).getNickname());
                }

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).newPlayersList(MVE);
                break;


            case newBoard:
                ViewModelGate.getModel().setBoard((BoardV)MVE.getComponent());

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).newBoard(MVE);
                break;


            //from KillshotTrack class
            case deathOfPlayer:
                ViewModelGate.getModel().setKillshotTrack((KillShotTrackV)MVE.getComponent());

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).deathOfPlayer(MVE);
                break;


            //from OrderedCardList class
            case movingCardsAround:
                OrderedCardListV from = (OrderedCardListV)MVE.getComponent();
                OrderedCardListV to = (OrderedCardListV)MVE.getExtraInformation1();
                setOrderedCardListV(from);
                if(to!=null) {
                    setOrderedCardListV(to);
                }

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).movingCardsAround(from, to, MVE);
                break;


            case shufflingCards:
                setOrderedCardListV((OrderedCardListV)MVE.getComponent());

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).shufflingCards(MVE);
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

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).newColor(MVE);
                break;


            case newNickname:
                boolean found = false;
                if (ViewModelGate.getModel().getPlayers() != null) {
                    if(ViewModelGate.getModel().getPlayers().getPlayers()==null){
                        ViewModelGate.getModel().getPlayers().setPlayers(new ArrayList<>());
                    }
                    for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                        if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                            p.setNickname((String) MVE.getComponent());
                            found = true;
                            break;
                        }
                    }
                    if(!found){
                        PlayerV playerV = new PlayerV();
                        playerV.setNickname((String) MVE.getComponent());
                        ViewModelGate.getModel().getPlayers().getPlayers().add(playerV);
                    }
                }

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).newNickname(MVE);
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

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).newPosition(MVE);
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

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).newScore(MVE);
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

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).addDeathCounter(MVE);
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

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).setFinalFrenzyBoard(MVE);
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

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).newAmmoBox(MVE);
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

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).newDamageTracker(MVE);
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

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).newMarksTracker(MVE);
                break;


            //from PlayerList class
            case setCurrentPlayingPlayer:
                if (ViewModelGate.getModel().getPlayers() != null) {
                    ViewModelGate.getModel().getPlayers().setCurrentPlayingPlayer((String)MVE.getComponent());
                }
                else{
                    ViewModelGate.getModel().setPlayers(new PlayersListV());
                    ViewModelGate.getModel().getPlayers().setCurrentPlayingPlayer((String)MVE.getComponent());
                }

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).setCurrentPlayingPlayer(MVE);
                break;



            case setStartingPlayer:
                if(ViewModelGate.getModel().getPlayers() != null) {
                    ViewModelGate.getModel().getPlayers().setStartingPlayer((String)MVE.getComponent());
                }
                else{
                    ViewModelGate.getModel().setPlayers(new PlayersListV());
                    ViewModelGate.getModel().getPlayers().setStartingPlayer((String)MVE.getComponent());
                }

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).setStartingPlayer(MVE);
                break;



            case newPlayer:
                boolean alreadyExist = false;
                for (PlayerV p: ViewModelGate.getModel().getPlayers().getPlayers()) {
                    if(p.getNickname().equals(((PlayerV)MVE.getComponent()).getNickname())){
                        alreadyExist = true;
                    }
                }
                if(!alreadyExist) {
                    ViewModelGate.getModel().getPlayers().getPlayers().add((PlayerV) MVE.getComponent());
                    if (ViewModelGate.getMe() == null) {
                        ViewModelGate.setMe(((PlayerV) MVE.getComponent()).getNickname());
                    }
                }

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).newPlayer(MVE);
                break;


            case setAFK:
                if (ViewModelGate.getModel().getPlayers() != null) {
                    for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                        if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                            p.setIsAFK((boolean) MVE.getComponent());
                            break;
                        }
                    }
                }
                if(ViewModelGate.getMe().equals((String) MVE.getExtraInformation1() )){
                    if(networkConnection.equalsIgnoreCase("SOCKET")){
                        try {
                            SocketNetworkHandler.disconnect();
                        } catch (IOException e) {
                            System.err.println("PROBLEMS DISCONNECTING FROM SERVER.");
                            OutputHandlerGate.getCorrectOutputHandler(OutputHandlerGate.getUserIterface()).cantReachServer();
                        }
                    }
                    else{
                        RMINetworkHandler.disconnect(); //todo
                    }
                }

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).setAFK(MVE);
                break;

            case resetGame:
                ViewModelGate.setModel((GameV)MVE.getComponent());

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).setAFK(MVE);
                break;

            default:
                try {
                    throw new Exception("<CLIENT> MVE NOT RECOGNIZED");
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }

        }

    }

    public void setOrderedCardListV(OrderedCardListV orderedCardListV){
        if(orderedCardListV.getContext().contains("powerUpDeck")){
            ViewModelGate.getModel().setPowerUpDeck(orderedCardListV);
        }
        else if(orderedCardListV.getContext().contains("weaponDeck")){
            ViewModelGate.getModel().setWeaponDeck(orderedCardListV);
        }
        else if(orderedCardListV.getContext().contains("powerUpDeck")){
            ViewModelGate.getModel().setAmmoDeck(orderedCardListV);
        }
        else if(orderedCardListV.getContext().contains("ammoDiscardPile")){
            ViewModelGate.getModel().setAmmoDiscardPile(orderedCardListV);
        }
        else if(orderedCardListV.getContext().contains("powerUpDiscardPile")){
            ViewModelGate.getModel().setPowerUpDiscardPile(orderedCardListV);
        }
        else if(orderedCardListV.getContext().contains("PowerUpInHand")){
            String nickname = orderedCardListV.getContext().split(":")[0];
            for (PlayerV p: ViewModelGate.getModel().getPlayers().getPlayers()) {
                if(p.getNickname().equals(nickname)){
                    p.setPowerUpCardInHand(orderedCardListV);
                    return;
                }
            }
        }
        else if(orderedCardListV.getContext().contains("WeaponInHand")){
            String nickname = orderedCardListV.getContext().split(":")[0];
            for (PlayerV p: ViewModelGate.getModel().getPlayers().getPlayers()) {
                if(p.getNickname().equals(nickname)){
                    p.setWeaponCardInHand(orderedCardListV);
                    return;
                }
            }
        }
        else if(orderedCardListV.getContext().contains("normalSquare")){
            String X = orderedCardListV.getContext().split("-")[1];
            String Y = orderedCardListV.getContext().split("-")[2];

            ((NormalSquareV)ViewModelGate.getModel().getBoard().getMap()[Integer.parseInt(X)][Integer.parseInt(Y)]).setAmmoCards(orderedCardListV);
        }
        else if(orderedCardListV.getContext().contains("spawnPoint")){
            String X = orderedCardListV.getContext().split("-")[1];
            String Y = orderedCardListV.getContext().split("-")[2];
            ((SpawnPointSquareV)ViewModelGate.getModel().getBoard().getMap()[Integer.parseInt(X)][Integer.parseInt(Y)]).setWeaponCards(orderedCardListV);        }
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

            case askWhatWep:
                this.selector.askWhatWep(((SelectorEventWeaponCards)SE).getWeaponCards());
                break;

            case askWhatEffect:
                this.selector.askWhatEffect(((SelectorEventEffect)SE).getPossibleEffects());
                break;

            case askEffectInputs:
                this.selector.askEffectInputs(((SelectorEventEffectInputs)SE).getInputType(),((SelectorEventEffectInputs)SE).getPossibleInputs());
                break;

            default: break;
        }
    }
}
