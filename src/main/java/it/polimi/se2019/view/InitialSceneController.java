package it.polimi.se2019.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class InitialSceneController implements Initializable {
    @FXML private TextField nicknameTextField;

    @FXML private RadioButton RMIRadio;
    @FXML private RadioButton socketRadio;

    @FXML private TextField IPTextField;
    @FXML private TextField PORTTextField;

    @FXML private Button startButton;

    @FXML private Hyperlink reconnectHyperlink;
    @FXML private Hyperlink CLIHyperlink;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }
}
