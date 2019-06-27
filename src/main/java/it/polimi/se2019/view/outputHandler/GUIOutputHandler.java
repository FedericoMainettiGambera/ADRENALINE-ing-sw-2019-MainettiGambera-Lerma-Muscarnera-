package it.polimi.se2019.view.outputHandler;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.model.events.stateEvent.StateEvent;
import it.polimi.se2019.view.GUIstarter;
import it.polimi.se2019.view.GameSceneController;
import it.polimi.se2019.view.InitialSceneController;
import it.polimi.se2019.view.LoadingSceneController;
import it.polimi.se2019.view.components.OrderedCardListV;
import it.polimi.se2019.view.components.ViewModelGate;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import java.util.concurrent.TimeUnit;


public class GUIOutputHandler implements OutputHandlerInterface{

    private GameSceneController getGameSceneController(){
        return ((GameSceneController)GUIstarter.stageController);
    }

    @Override
    public void gameCreated() {
        //probably empty
    }

    @Override
    public void stateChanged(StateEvent stateEvent) {
        //ps really bad code, needed to trigger the loading scene to appear
        if(stateEvent.getState().contains("GameSetUpState")){
            boolean done = false;
            while(!done) {
                if (GUIstarter.stageController.getClass().toString().contains("LoadingSceneController")) {
                    ((LoadingSceneController)GUIstarter.stageController).changeScene("FXML/GAME.fxml");
                    done=true;
                } else { //wait because some times the event sent from the server is faster than the process of changing scene in javafx
                    try {
                        TimeUnit.MILLISECONDS.sleep(300);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        done=true;
                    }
                }
            }
        }
        //else means we are in the Game Scene and we should update the stage bar;
        else{
            Platform.runLater(new UpdateStateBar(stateEvent));
        }
    }
    private class UpdateStateBar extends Thread{
        private StateEvent stateEvent;
        private UpdateStateBar(StateEvent stateEvent){
            this.stateEvent = stateEvent;
        }
        @Override
        public void run(){
            getGameSceneController().updateStateLabel(stateEvent.toString());
        }
    }



    @Override
    public void setFinalFrenzy(ModelViewEvent modelViewEvent) {
        // shot ViewModelGate.getModel().isFinalFrenzy()
    }

    @Override
    public void finalFrenzyBegun(ModelViewEvent modelViewEvent) {
        if(ViewModelGate.getModel().isHasFinalFrenzyBegun()) {
            //show final frenzy has begun
        }
        else{
            //show final frenzy hasn't begun
        }
    }

    @Override
    public void newKillshotTrack(ModelViewEvent modelViewEvent) {
        //update killshotTrack
    }

    @Override
    public void newPlayersList(ModelViewEvent modelViewEvent){
        //TO change...
        //update the player list or:
        boolean done = false;
        while(!done) {
            if (GUIstarter.stageController.getClass().toString().contains("LoadingSceneController")) {
                ((LoadingSceneController) GUIstarter.stageController).newPlayersList(modelViewEvent);
                done = true;
            }
            else{ //wait because some times the event sent from the server is faster than the process of changing scene in javafx
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }


    @Override
    public void newBoard(ModelViewEvent modelViewEvent) {
        //update map
    }

    @Override
    public void deathOfPlayer(ModelViewEvent modelViewEvent) {
        //update killshot track
        //update playerList
    }

    @Override
    public void movingCardsAround(OrderedCardListV from, OrderedCardListV to, ModelViewEvent modelViewEvent) {
        //update changed cards
    }

    @Override
    public void shufflingCards(ModelViewEvent modelViewEvent) {
        //probably empty
    }

    @Override
    public void newColor(ModelViewEvent modelViewEvent) {
        //update players
    }

    @Override
    public void newNickname(ModelViewEvent modelViewEvent) {
        //update Players
    }

    @Override
    public void newPosition(ModelViewEvent modelViewEvent) {
        //update map
    }

    @Override
    public void newScore(ModelViewEvent modelViewEvent) {
        //update players
    }

    @Override
    public void addDeathCounter(ModelViewEvent modelViewEvent) {
        //update plaers
    }

    @Override
    public void setFinalFrenzyBoard(ModelViewEvent modelViewEvent) {
        //update Players
    }

    @Override
    public void newAmmoBox(ModelViewEvent modelViewEvent) {
        //update players
    }

    @Override
    public void newDamageTracker(ModelViewEvent modelViewEvent) {
        //update players
    }

    @Override
    public void newMarksTracker(ModelViewEvent modelViewEvent) {
        //update players
    }

    @Override
    public void setCurrentPlayingPlayer(ModelViewEvent modelViewEvent) {
        //update players
    }

    @Override
    public void setStartingPlayer(ModelViewEvent modelViewEvent) {
        //update players
    }

    @Override
    public void newPlayer(ModelViewEvent modelViewEvent) {
        //update players
    }

    @Override
    public void setAFK(ModelViewEvent modelViewEvent) {
        //update players
    }

    @Override
    public void showInputTimer(int currentTime, int totalTime) {
        //update state section
    }

    @Override
    public void showConnectionTimer(int currentTime, int totalTime) {
        if(GUIstarter.stageController.getClass().toString().contains("LoadingSceneController")) {
            ((LoadingSceneController) GUIstarter.stageController).modifyProgress(currentTime, totalTime);
        }
    }

    @Override
    public void cantReachServer() {
        //show error pop up
    }

    @Override
    public void succesfullReconnection() {
        //TODO
        //start the GAME.fxml
        //update everything in the gui
    }

    @Override
    public void disconnect() {
        //show pop up for disconnection
    }

    @Override
    public void finalScoring(ModelViewEvent modelViewEvent) {
        //final scene
    }
}

