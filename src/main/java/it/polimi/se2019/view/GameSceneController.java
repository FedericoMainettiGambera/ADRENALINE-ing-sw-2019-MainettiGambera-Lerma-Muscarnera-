package it.polimi.se2019.view;

import it.polimi.se2019.controller.randomStageBuilderForTesting;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameSceneController implements Initializable {

    @FXML
    private GridPane gridPaneMap;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void fixRatioMap(){
        gridPaneMap.prefHeightProperty().bind(gridPaneMap.widthProperty().multiply(0.75));
    }
}
