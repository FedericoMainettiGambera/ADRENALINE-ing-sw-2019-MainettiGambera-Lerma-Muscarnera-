package it.polimi.se2019.view.outputHandler;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.model.events.stateEvent.StateEvent;
import it.polimi.se2019.view.components.OrderedCardListV;
import javafx.application.*;
import javafx.animation.*;
import javafx.scene.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GUIOutputHandler extends Application implements OutputHandlerInterface{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("GUIFirstStage.fxml"));
        Scene scene= new Scene(root, 300, 275);
        primaryStage.setTitle("Hello World");




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
    public void newPlayersList(ModelViewEvent MVE) {

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
}

