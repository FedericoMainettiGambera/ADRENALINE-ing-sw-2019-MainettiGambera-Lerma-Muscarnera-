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

import java.net.URL;
import java.util.ResourceBundle;

public class GUIOutputHandler extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("FIRSTSTAGE1.fxml"));
        Scene scene = new Scene(root, 390, 350);
        primaryStage.setTitle("Hello World");
        primaryStage.setResizable(false);


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


}