package it.polimi.se2019.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadingSceneController implements Initializable {

    private String nickname;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }
}
