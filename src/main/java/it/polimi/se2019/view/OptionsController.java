package it.polimi.se2019.view;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.networkHandler.sendPingRequest;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OptionsController extends Application implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources){

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader=new FXMLLoader();
        Parent root=fxmlLoader.load(getClass().getClassLoader().getResource("FXML/GAME2.fxml"));
        Scene scene =new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
