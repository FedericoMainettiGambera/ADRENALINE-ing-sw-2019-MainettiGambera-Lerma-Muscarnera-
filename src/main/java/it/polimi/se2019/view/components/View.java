package it.polimi.se2019.view.components;

import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.ModelViewEventTypes;
import it.polimi.se2019.model.enumerations.PlayersColors;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.model.events.selectorEvents.*;
import it.polimi.se2019.view.outputHandler.CLIOutputHandler;
import it.polimi.se2019.view.outputHandler.GUIOutputHandler;
import it.polimi.se2019.view.outputHandler.OutputHandlerGate;
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
        OutputHandlerGate.setUserIterface(userInterface);
        if(userInterface.equals("CLI")){
            OutputHandlerGate.setCLIOutputHandler(new CLIOutputHandler());
        }
        else{
            OutputHandlerGate.setGUIOutputHandler(new GUIOutputHandler());
        }
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
                if(userInterface.equals("CLI")){
                    OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
                }
                else{

                }
                break;
            case finalFrenzyBegun:
                ViewModelGate.getModel().setHasFinalFrenzyBegun((boolean)MVE.getComponent());
                if(userInterface.equals("CLI")){
                    OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
                }
                else{

                }
                break;
            case newKillshotTrack:
                ViewModelGate.getModel().setKillshotTrack(new KillShotTrackV());
                ViewModelGate.getModel().getKillshotTrack().setNumberOfStartingSkulls((int)MVE.getComponent());
                if(userInterface.equals("CLI")){
                    OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
                }
                else{

                }
                break;
            case newPlayersList:
                ViewModelGate.getModel().setPlayers((PlayersListV)MVE.getComponent());
                if(userInterface.equals("CLI")){
                    OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
                }
                else{

                }
                break;
            case newBoard:
                ViewModelGate.getModel().setBoard((BoardV)MVE.getComponent());
                if(userInterface.equals("CLI")){
                    OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
                }
                else{

                }
                break;

            //from KillshotTrack class
            case deathOfPlayer:
                ViewModelGate.getModel().setKillshotTrack((KillShotTrackV)MVE.getComponent());
                if(userInterface.equals("CLI")){
                    OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
                }
                else{

                }
                break;

            //from OrderedCardList class
            case movingCardsAround:
                OrderedCardListV from = (OrderedCardListV)MVE.getComponent();
                OrderedCardListV to = (OrderedCardListV)MVE.getExtraInformation1();
                setOrderedCardListV(from);
                setOrderedCardListV(to);
                if(userInterface.equals("CLI")){
                    OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
                }
                else{

                }
                break;
            case shufflingCards:
                setOrderedCardListV((OrderedCardListV)MVE.getComponent());
                if(userInterface.equals("CLI")){
                    OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
                }
                else{

                }
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
                if(userInterface.equals("CLI")){
                    OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
                }
                else{

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
                if(userInterface.equals("CLI")){
                    OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
                }
                else{

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
                if(userInterface.equals("CLI")){
                    OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
                }
                else{

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
                if(userInterface.equals("CLI")){
                    OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
                }
                else{

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
                if(userInterface.equals("CLI")){
                    OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
                }
                else{

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
                if(userInterface.equals("CLI")){
                    OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
                }
                else{

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
                if(userInterface.equals("CLI")){
                    OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
                }
                else{

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
                if(userInterface.equals("CLI")){
                    OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
                }
                else{

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
                if(userInterface.equals("CLI")){
                    OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
                }
                else{

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
                if(userInterface.equals("CLI")){
                    OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
                }
                else{

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
                if(userInterface.equals("CLI")){
                    OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
                }
                else{

                }
                break;
            case newPlayer:
                ViewModelGate.getModel().getPlayers().getPlayers().add((PlayerV)MVE.getComponent());
                if(userInterface.equals("CLI")){
                    OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
                }
                else{

                }
                break;

            default:
                throw new Exception("<CLIENT> MVE NOT RECOGNIZED");

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
            ((NormalSquareV)ViewModelGate.getModel().getBoard().getBoard()[Integer.parseInt(X)][Integer.parseInt(Y)]).setAmmoCards(orderedCardListV);
        }
        else if(orderedCardListV.getContext().contains("spawnPoint")){
            String X = orderedCardListV.getContext().split("-")[1];
            String Y = orderedCardListV.getContext().split("-")[2];
            ((SpawnPointSquareV)ViewModelGate.getModel().getBoard().getBoard()[Integer.parseInt(X)][Integer.parseInt(Y)]).setWeaponCards(orderedCardListV);        }
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
