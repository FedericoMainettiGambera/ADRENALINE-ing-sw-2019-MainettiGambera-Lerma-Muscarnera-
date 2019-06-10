package it.polimi.se2019.view.outputHandler;
import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.PlayersList;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.model.events.stateEvent.StateEvent;
import it.polimi.se2019.view.GUIstarter;
import it.polimi.se2019.view.LoadingSceneController;
import it.polimi.se2019.view.components.OrderedCardListV;
import it.polimi.se2019.view.components.PlayersListV;
import javafx.application.*;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import static it.polimi.se2019.view.LoadingSceneController.progressionBarGoal;


public class GUIOutputHandler extends Application implements OutputHandlerInterface{

    private FXMLLoader fxmlLoader;

    public static void main(String[] args) {
        launch(args);
    }

    public ControllerInizialScene controller;

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
    public void stateChanged(StateEvent StE) {

    }

    @Override
    public void setFinalFrenzy(ModelViewEvent MVE) {

    }

    @Override
    public void finalFrenzyBegun(ModelViewEvent MVE) {

    }

    @Override
    public void newKillshotTrack(ModelViewEvent MVE) {

    }

    @Override
    public void newPlayersList(ModelViewEvent MVE){
        boolean done = false;
        while(!done) {
            if (GUIstarter.stageController.getClass().toString().contains("LoadingSceneController")) {
                ((LoadingSceneController) GUIstarter.stageController).newPlayersList(MVE);
                done = true;
            }
            else{
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }


    @Override
    public void newBoard(ModelViewEvent MVE) {

    }

    @Override
    public void deathOfPlayer(ModelViewEvent MVE) {

    }

    @Override
    public void movingCardsAround(OrderedCardListV from, OrderedCardListV to, ModelViewEvent MVE) {

    }

    @Override
    public void shufflingCards(ModelViewEvent MVE) {

    }

    @Override
    public void newColor(ModelViewEvent MVE) {

    }

    @Override
    public void newNickname(ModelViewEvent MVE) {

    }

    @Override
    public void newPosition(ModelViewEvent MVE) {

    }

    @Override
    public void newScore(ModelViewEvent MVE) {

    }

    @Override
    public void addDeathCounter(ModelViewEvent MVE) {

    }

    @Override
    public void setFinalFrenzyBoard(ModelViewEvent MVE) {

    }

    @Override
    public void newAmmoBox(ModelViewEvent MVE) {

    }

    @Override
    public void newDamageTracker(ModelViewEvent MVE) {

    }

    @Override
    public void newMarksTracker(ModelViewEvent MVE) {

    }

    @Override
    public void setCurrentPlayingPlayer(ModelViewEvent MVE) {
    }

    @Override
    public void setStartingPlayer(ModelViewEvent MVE) {

    }

    @Override
    public void newPlayer(ModelViewEvent MVE) {

    }

    @Override
    public void setAFK(ModelViewEvent MVE) {

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
}

