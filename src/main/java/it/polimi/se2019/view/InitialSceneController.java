package it.polimi.se2019.view;

import it.polimi.se2019.controller.Controller;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class InitialSceneController implements Initializable {
    @FXML private Label titleLabel;

    @FXML private TextField nicknameTextField;
    private String nicknameContent = "";

    @FXML private RadioButton RMIRadio;
    @FXML private RadioButton socketRadio;
    private String netWorkConnection = "";

    @FXML private TextField IPTextField;
    private String IPContent = "";
    @FXML private TextField PORTTextField;
    private String PORTContent = "";

    @FXML private Button startButton;

    @FXML private Label logLabel;

    @FXML private Hyperlink reconnectHyperlink;
    @FXML private Hyperlink CLIHyperlink;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }

    @FXML
    public void onStartButtonPressed(){
        this.nicknameContent = this.nicknameTextField.getText();
        //TODO
        this.netWorkConnection = "SOCKET";
        this.IPContent = this.IPTextField.getText();
        this.PORTContent = this.PORTTextField.getText();

        String titleLabelContent = "CONNECTING...";
        this.titleLabel.setText(titleLabelContent);

        //Connect to server using the Controller's static method
        if(!nicknameTextField.equals("") && !netWorkConnection.equals("") && !IPContent.equals("") && !PORTContent.equals("")){

            if(Controller.connect(netWorkConnection.toUpperCase(),"GUI",IPContent,PORTContent)) {
                //TODO
                //connection was succesfull!
                //change stage: make it full screen
                //pass nicknameContent to the next scene
                //change scene: LOADSCENE1.fxml
            }
            else {
                String errorLabelContent = "Connection wasn't possible";
                this.logLabel.setText(errorLabelContent);
                titleLabelContent = "ADRENALINE LOG-IN:";
                this.titleLabel.setText(titleLabelContent);
            }

        }
        else{

            titleLabelContent = "ADRENALINE LOG-IN:";
            this.titleLabel.setText(titleLabelContent);

            String errorLabelContent = "missing:";
            //show missing data
            if(nicknameTextField.equals("")){
                //ask to fill nickname
                errorLabelContent += " nickname ";
            }
            if(netWorkConnection.equals("")){
                //ask select RMI or Socket
                errorLabelContent += " connection ";
            }
            if(IPContent.equals("")){
                //Ask to insert IP
                errorLabelContent += " IP ";
            }
            if(PORTContent.equals("")){
                //Ask to insert Port
                errorLabelContent += " PORT ";
            }
            this.logLabel.setText(errorLabelContent);
        }
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
