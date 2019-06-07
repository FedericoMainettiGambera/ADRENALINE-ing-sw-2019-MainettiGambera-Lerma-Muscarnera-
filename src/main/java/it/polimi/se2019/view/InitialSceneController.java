package it.polimi.se2019.view;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

    @FXML
    public void onStartButtonPressed(){
        //TODO
        //Connect to server using the Controller static method:
        //              Controller.connect(String networkConnection, String userInterface, String IP, String Port);
        //change stage: make it full screen
        //change scene: LOADSCENE1.fxml
    }

    @FXML
    public void onCLIHyperlinkPressed(Event event){
        //chiude lo stage:
        ((Node)(event.getSource())).getScene().getWindow().hide();
        //fa partire CLI:
        GUIstarter.user.startClientSocketOrRMIWithCLI();
    }

    @FXML
    public void onReconnectionPressed(){
        //TODO
    }

}
