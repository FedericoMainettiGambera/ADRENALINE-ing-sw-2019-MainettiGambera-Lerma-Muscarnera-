package it.polimi.se2019.view.outputHandler;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.model.events.stateEvent.StateEvent;
import it.polimi.se2019.view.GUIstarter;
import it.polimi.se2019.view.InitialSceneController;
import it.polimi.se2019.view.LoadingSceneController;
import it.polimi.se2019.view.components.OrderedCardListV;
import javafx.fxml.FXML;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import java.util.concurrent.TimeUnit;


public class GUIOutputHandler extends Application implements OutputHandlerInterface{

    private FXMLLoader fxmlLoader;

    public static void main(String[] args) {
        launch(args);
    }

    public InitialSceneController controller;

    @FXML
    Parent root;
    @FXML
    MenuItem menuItem;
    @FXML
    @Override
    public void start( Stage primaryStage) throws Exception{

        fxmlLoader=new FXMLLoader();
        root = fxmlLoader.load(getClass().getClassLoader().getResource("INITIAL STAGE.fxml"));
        root.setId("pane");
        Scene scene = new Scene(root, 600, 530);
        primaryStage.setTitle("Hello World");
        scene.getStylesheets().addAll(this.getClass().getClassLoader().getResource("styleInitialScene1.css").toExternalForm());

        controller= fxmlLoader.getController();
        // menuItem.setId("MenuItem");
        //menuItem.getStyleClass().addAll(this.getClass().getClassLoader().getResource("MenuItem.css").toExternalForm());

        //primaryStage.setResizable(false);



        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @Override
    public void gameCreated() {

    }

    @Override
    public void stateChanged(StateEvent stateEvent) {
        if(stateEvent.getState().contains("GameSetUpState")){
            while(true) {
                if (GUIstarter.stageController.getClass().toString().contains("LoadingSceneController")) {
                    ((LoadingSceneController)GUIstarter.stageController).changeScene("FXML/GAME.fxml");
                    break;
                } else {
                    try {
                        TimeUnit.MILLISECONDS.sleep(300);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void setFinalFrenzy(ModelViewEvent modelViewEvent) {

    }

    @Override
    public void finalFrenzyBegun(ModelViewEvent modelViewEvent) {

    }

    @Override
    public void newKillshotTrack(ModelViewEvent modelViewEvent) {

    }

    @Override
    public void newPlayersList(ModelViewEvent modelViewEvent){
        boolean done = false;
        while(!done) {
            if (GUIstarter.stageController.getClass().toString().contains("LoadingSceneController")) {
                ((LoadingSceneController) GUIstarter.stageController).newPlayersList(modelViewEvent);
                done = true;
            }
            else{
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

    }

    @Override
    public void deathOfPlayer(ModelViewEvent modelViewEvent) {

    }

    @Override
    public void movingCardsAround(OrderedCardListV from, OrderedCardListV to, ModelViewEvent modelViewEvent) {

    }

    @Override
    public void shufflingCards(ModelViewEvent modelViewEvent) {

    }

    @Override
    public void newColor(ModelViewEvent modelViewEvent) {

    }

    @Override
    public void newNickname(ModelViewEvent modelViewEvent) {

    }

    @Override
    public void newPosition(ModelViewEvent modelViewEvent) {

    }

    @Override
    public void newScore(ModelViewEvent modelViewEvent) {

    }

    @Override
    public void addDeathCounter(ModelViewEvent modelViewEvent) {

    }

    @Override
    public void setFinalFrenzyBoard(ModelViewEvent modelViewEvent) {

    }

    @Override
    public void newAmmoBox(ModelViewEvent modelViewEvent) {

    }

    @Override
    public void newDamageTracker(ModelViewEvent modelViewEvent) {

    }

    @Override
    public void newMarksTracker(ModelViewEvent modelViewEvent) {

    }

    @Override
    public void setCurrentPlayingPlayer(ModelViewEvent modelViewEvent) {
    }

    @Override
    public void setStartingPlayer(ModelViewEvent modelViewEvent) {

    }

    @Override
    public void newPlayer(ModelViewEvent modelViewEvent) {

    }

    @Override
    public void setAFK(ModelViewEvent modelViewEvent) {

    }

    @Override
    public void showInputTimer(int currentTime, int totalTime) {

    }

    @Override
    public void showConnectionTimer(int currentTime, int totalTime) {
        if(GUIstarter.stageController.getClass().toString().contains("LoadingSceneController")) {
            ((LoadingSceneController) GUIstarter.stageController).modifyProgress(currentTime, totalTime);
        }
    }

    @Override
    public void cantReachServer() {
        //TODO
    }

    @Override
    public void succesfullReconnection() {

    }

    @Override
    public void disconnect() {

    }

    @Override
    public void finalScoring(ModelViewEvent modelViewEvent) {

    }
}

