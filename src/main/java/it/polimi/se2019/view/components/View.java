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
import it.polimi.se2019.networkHandler.RMIREDO.RmiNetworkHandler;
import it.polimi.se2019.networkHandler.Socket.SocketNetworkHandler;
import it.polimi.se2019.view.outputHandler.CLIOutputHandler;
import it.polimi.se2019.view.outputHandler.GUIOutputHandler;
import it.polimi.se2019.view.outputHandler.OutputHandlerGate;
import it.polimi.se2019.view.selector.ViewSelector;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {

    private ViewSelector selector;

    private String networkConnection;

    private String userInterface;

    private static PrintWriter out=new PrintWriter(System.out, true);


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
        SelectorEvent selectorEvent = null;
        ModelViewEvent modelViewEvent = null;
        StateEvent stateEvent = null;
        TimerEvent timerEvent = null;
        ReconnectionEvent reconnectionEvent = null;
        if(arg.getClass().toString().contains("ModelViewEvent")){
            modelViewEvent = (ModelViewEvent)arg;
            this.callCorrectComponent(modelViewEvent);
        }
        else if(arg.getClass().toString().contains("SelectorEvent")){
            selectorEvent = (SelectorEvent)arg;
            this.callCorrectSelector(selectorEvent);
        }
        else if(arg.getClass().toString().contains("StateEvent")){
            stateEvent = (StateEvent)arg;
            OutputHandlerGate.getCorrectOutputHandler(this.userInterface).stateChanged(stateEvent);
        }
        else if(arg.getClass().toString().contains("TimerEvent")){
            timerEvent = (TimerEvent)arg;
            if(timerEvent.getContext().equalsIgnoreCase("input")) {
                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).showInputTimer(timerEvent.getCurrentTime(), timerEvent.getTotalTime());
            }
            else{
                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).showConnectionTimer(timerEvent.getCurrentTime(), timerEvent.getTotalTime());
            }
        }
        else if(arg.getClass().toString().contains("ReconnectionEvent")){
            reconnectionEvent = (ReconnectionEvent)arg;
            this.selector.askReconnectionNickname(reconnectionEvent);
        }
        else{
            try {
                throw new IllegalArgumentException("Event not recognized");
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void callCorrectComponent(ModelViewEvent modelViewEvent)  {
        ModelViewEventTypes information = modelViewEvent.getInformation();

        switch (information){

            //from Game class
            case setFinalFrenzy:
                ViewModelGate.getModel().setFinalFrenzy((boolean)modelViewEvent.getComponent());

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).setFinalFrenzy(modelViewEvent);
                break;


            case finalFrenzyBegun:
                ViewModelGate.getModel().setHasFinalFrenzyBegun((boolean)modelViewEvent.getComponent());

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).finalFrenzyBegun(modelViewEvent);
                break;


            case newKillshotTrack:
                ViewModelGate.getModel().setKillshotTrack(new KillShotTrackV());
                ViewModelGate.getModel().getKillshotTrack().setNumberOfStartingSkulls((int)modelViewEvent.getComponent());

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).newKillshotTrack(modelViewEvent);
                break;


            case newPlayersList:
                ViewModelGate.getModel().setPlayers((PlayersListV)modelViewEvent.getComponent());
                if(ViewModelGate.getModel()==null){
                    ViewModelGate.setMe(((PlayersListV)modelViewEvent.getComponent()).getPlayers().get(((PlayersListV)modelViewEvent.getComponent()).getPlayers().size()-1).getNickname());
                }

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).newPlayersList(modelViewEvent);
                break;


            case newBoard:
                ViewModelGate.getModel().setBoard((BoardV)modelViewEvent.getComponent());

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).newBoard(modelViewEvent);
                break;


            //from KillshotTrack class
            case deathOfPlayer:
                ViewModelGate.getModel().setKillshotTrack((KillShotTrackV)modelViewEvent.getComponent());

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).deathOfPlayer(modelViewEvent);
                break;


            //from OrderedCardList class
            case movingCardsAround:
                OrderedCardListV from = (OrderedCardListV)modelViewEvent.getComponent();
                OrderedCardListV to = (OrderedCardListV)modelViewEvent.getExtraInformation1();
                setOrderedCardListV(from);
                if(to!=null) {
                    setOrderedCardListV(to);
                }

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).movingCardsAround(from, to, modelViewEvent);
                break;


            case shufflingCards:
                setOrderedCardListV((OrderedCardListV)modelViewEvent.getComponent());

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).shufflingCards(modelViewEvent);
                break;


            //from Player class or Person class
            case newColor:
                if (ViewModelGate.getModel().getPlayers() != null) {
                    for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                        if (p.getNickname().equals(modelViewEvent.getExtraInformation1())) {
                            p.setColor((PlayersColors) modelViewEvent.getComponent());
                            break;
                        }
                    }
                }

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).newColor(modelViewEvent);
                break;


            case newNickname:
                boolean found = false;
                if (ViewModelGate.getModel().getPlayers() != null) {
                    if(ViewModelGate.getModel().getPlayers().getPlayers()==null){
                        ViewModelGate.getModel().getPlayers().setPlayers(new ArrayList<>());
                    }
                    for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                        if (p.getNickname().equals(modelViewEvent.getExtraInformation1())) {
                            p.setNickname((String) modelViewEvent.getComponent());
                            found = true;
                            break;
                        }
                    }
                    if(!found){
                        PlayerV playerV = new PlayerV();
                        playerV.setNickname((String) modelViewEvent.getComponent());
                        ViewModelGate.getModel().getPlayers().getPlayers().add(playerV);
                    }
                }

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).newNickname(modelViewEvent);
                break;


            case newPosition:
                if (ViewModelGate.getModel().getPlayers() != null) {
                    for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                        if (p.getNickname().equals(modelViewEvent.getExtraInformation1())) {
                            p.setX(((Position) modelViewEvent.getComponent()).getX());
                            p.setY(((Position) modelViewEvent.getComponent()).getY());
                            break;
                        }
                    }
                }

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).newPosition(modelViewEvent);
                break;


            case newScore:
                if (ViewModelGate.getModel().getPlayers() != null) {
                    for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                        if (p.getNickname().equals( modelViewEvent.getExtraInformation1())) {
                            p.setScore((int) modelViewEvent.getComponent());
                            break;
                        }
                    }
                }

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).newScore(modelViewEvent);
                break;


            case addDeathCounter:
                if (ViewModelGate.getModel().getPlayers() != null) {
                    for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                        if (p.getNickname().equals(modelViewEvent.getExtraInformation1())) {
                            p.setNumberOfDeaths((int) modelViewEvent.getComponent());
                            break;
                        }
                    }
                }

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).addDeathCounter(modelViewEvent);
                break;


            case setFinalFrenzyBoard:
                if (ViewModelGate.getModel().getPlayers() != null) {
                    for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                        if (p.getNickname().equals(modelViewEvent.getExtraInformation1())) {
                            p.setHasFinalFrenzyBoard((boolean) modelViewEvent.getComponent());
                            break;
                        }
                    }
                }

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).setFinalFrenzyBoard(modelViewEvent);
                break;


            case newAmmoBox:
                if (ViewModelGate.getModel().getPlayers() != null) {
                    for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                        if (p.getNickname().equals(modelViewEvent.getExtraInformation1())) {
                            p.setAmmoBox((AmmoListV) modelViewEvent.getComponent());
                            break;
                        }
                    }
                }

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).newAmmoBox(modelViewEvent);
                break;


            case newDamageTracker:
                if (ViewModelGate.getModel().getPlayers() != null) {
                    for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                        if (p.getNickname().equals(modelViewEvent.getExtraInformation1())) {
                            p.setDamageTracker((DamageTrackerV) modelViewEvent.getComponent());
                            break;
                        }
                    }
                }

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).newDamageTracker(modelViewEvent);
                break;


            case newMarksTracker:
                if (ViewModelGate.getModel().getPlayers() != null) {
                    for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                        if (p.getNickname().equals(modelViewEvent.getExtraInformation1())) {
                            p.setMarksTracker((MarksTrackerV) modelViewEvent.getComponent());
                            break;
                        }
                    }
                }

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).newMarksTracker(modelViewEvent);
                break;


            //from PlayerList class
            case setCurrentPlayingPlayer:
                if (ViewModelGate.getModel().getPlayers() != null) {
                    ViewModelGate.getModel().getPlayers().setCurrentPlayingPlayer((String)modelViewEvent.getComponent());
                }
                else{
                    ViewModelGate.getModel().setPlayers(new PlayersListV());
                    ViewModelGate.getModel().getPlayers().setCurrentPlayingPlayer((String)modelViewEvent.getComponent());
                }

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).setCurrentPlayingPlayer(modelViewEvent);
                break;



            case setStartingPlayer:
                if(ViewModelGate.getModel().getPlayers() != null) {
                    ViewModelGate.getModel().getPlayers().setStartingPlayer((String)modelViewEvent.getComponent());
                }
                else{
                    ViewModelGate.getModel().setPlayers(new PlayersListV());
                    ViewModelGate.getModel().getPlayers().setStartingPlayer((String)modelViewEvent.getComponent());
                }

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).setStartingPlayer(modelViewEvent);
                break;



            case newPlayer:
                boolean alreadyExist = false;
                for (PlayerV p: ViewModelGate.getModel().getPlayers().getPlayers()) {
                    if(p.getNickname().equals(((PlayerV)modelViewEvent.getComponent()).getNickname())){
                        alreadyExist = true;
                    }
                }
                if(!alreadyExist) {
                    ViewModelGate.getModel().getPlayers().getPlayers().add((PlayerV) modelViewEvent.getComponent());
                    if (ViewModelGate.getMe() == null) {
                        ViewModelGate.setMe(((PlayerV) modelViewEvent.getComponent()).getNickname());
                    }
                }

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).newPlayer(modelViewEvent);
                break;


            case setAFK:
                if (ViewModelGate.getModel().getPlayers() != null) {
                    for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                        if (p.getNickname().equals((String) modelViewEvent.getExtraInformation1())) {
                            p.setIsAFK((boolean) modelViewEvent.getComponent());
                            break;
                        }
                    }
                }
                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).setAFK(modelViewEvent);

                if(ViewModelGate.getMe().equals((String) modelViewEvent.getExtraInformation1() )){
                    if((boolean)modelViewEvent.getComponent()) {
                        if (networkConnection.equalsIgnoreCase("SOCKET")) {
                            SocketNetworkHandler.disconnect();
                        } else {

                        }
                        OutputHandlerGate.getCorrectOutputHandler(this.userInterface).disconnect();
                    }
                    else{
                        //TODO player has been reset to not AFK.
                         out.println("<CLIENT> you are no more AFK yay");
                    }
                }
                break;

            case resetGame:
                ViewModelGate.setModel((GameV)modelViewEvent.getComponent());

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).succesfullReconnection();
                break;


            case finalScoring:

                OutputHandlerGate.getCorrectOutputHandler(this.userInterface).finalScoring(modelViewEvent);
                break;


            default:
                try {
                    throw new IllegalStateException("<CLIENT> MVE NOT RECOGNIZED");
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
            String x = orderedCardListV.getContext().split("-")[1];
            String y = orderedCardListV.getContext().split("-")[2];

            ((NormalSquareV)ViewModelGate.getModel().getBoard().getMap()[Integer.parseInt(x)][Integer.parseInt(y)]).setAmmoCards(orderedCardListV);
        }
        else if(orderedCardListV.getContext().contains("spawnPoint")){
            String x = orderedCardListV.getContext().split("-")[1];
            String y = orderedCardListV.getContext().split("-")[2];
            ((SpawnPointSquareV)ViewModelGate.getModel().getBoard().getMap()[Integer.parseInt(x)][Integer.parseInt(y)]).setWeaponCards(orderedCardListV);        }
    }

    public void callCorrectSelector(SelectorEvent selectorEvent){
        SelectorEventTypes selectorEventTypes = selectorEvent.getSelectorEventTypes();
        switch (selectorEventTypes) {
            case askGameSetUp:
                this.selector.askGameSetUp(((SelectorEventBoolean)selectorEvent).isCanBot());
                break;

            case askFirstSpawnPosition:
                this.selector.askFirstSpawnPosition(((SelectorEventPowerUpCardsAndBoolean)selectorEvent).getPowerUpCards(),((SelectorEventPowerUpCardsAndBoolean)selectorEvent).isSpawnBot());
                break;

            case askTurnAction:
                this.selector.askTurnAction(((SelectorEventTurnAction)selectorEvent).getActionNumber(), ((SelectorEventTurnAction)selectorEvent).canUsePowerUp(), ((SelectorEventTurnAction)selectorEvent).canUseBot());
                break;

            case askRunAroundPosition:
                this.selector.askRunAroundPosition(((SelectorEventPositions)selectorEvent).getPositions());
                break;

            case askGrabStuffAction:
                this.selector.askGrabStuffAction();
                break;

            case askGrabStuffMove:
                this.selector.askGrabStuffMove(((SelectorEventPositions)selectorEvent).getPositions());
                break;

            case askGrabStuffGrabWeapon:
                this.selector.askGrabStuffGrabWeapon(((SelectorEventWeaponCards)selectorEvent).getWeaponCards());

                break;

            case askGrabStuffSwitchWeapon:
                this.selector.askGrabStuffSwitchWeapon(((SelectorEventDoubleWeaponCards)selectorEvent).getWeaponCards1(), ((SelectorEventDoubleWeaponCards)selectorEvent).getWeaponCards2());
                break;

            case askPowerUpToDiscard:
                this.selector.askPowerUpToDiscard(((SelectorEventPowerUpCards)selectorEvent).getPowerUpCards());
                break;

            case askWhatReaload:
                this.selector.askWhatReaload(((SelectorEventWeaponCards)selectorEvent).getWeaponCards());
                break;

            case askSpawn:
                this.selector.askSpawn(((SelectorEventPowerUpCards)selectorEvent).getPowerUpCards());
                break;

            case askShootOrMove:
                this.selector.askShootOrMove();
                break;

            case askWhatWep:
                this.selector.askWhatWep(((SelectorEventWeaponCards)selectorEvent).getWeaponCards());
                break;

            case askWhatEffect:
                this.selector.askWhatEffect(((SelectorEventEffect)selectorEvent).getPossibleEffects());
                break;

            case askEffectInputs:
                this.selector.askEffectInputs(((SelectorEventEffectInputs)selectorEvent).getInputType(),((SelectorEventEffectInputs)selectorEvent).getPossibleInputs());
                break;

            case askNickname:
                this.selector.askNickname();
                break;

            case paymentInformation:
                this.selector.askPaymentInformation((SelectorEventPaymentInformation)selectorEvent);
                break;

            case askPowerUpToUse:
                this.selector.askPowerUpToUse((SelectorEventPowerUpCards)selectorEvent);
                break;

            case wantToUsePowerUpOrNot:
                this.selector.wantToUsePowerUpOrNot();
                break;

            case askBotMove:
                this.selector.askBotMove((SelectorEventPositions)selectorEvent);
                break;

            case askBotShoot:
                this.selector.askBotShoot((SelectorEventPlayers)selectorEvent);
                break;

            case askTargetingScope:
                this.selector.askTargetingScope(((SelectorEventTargetingScope)selectorEvent).getListOfTargetingScopeV(),((SelectorEventTargetingScope)selectorEvent).getPossiblePaymentsV(),((SelectorEventTargetingScope)selectorEvent).getDamagedPlayersV());
                break;

            case askTagBackGranade:
                this.selector.askTagBackGranade(((SelectorEventPowerUpCards)selectorEvent).getPowerUpCards());
                break;

            default: break;
        }
    }
}
