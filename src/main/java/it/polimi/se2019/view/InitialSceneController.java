package it.polimi.se2019.view;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.view.components.ViewModelGate;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

public class InitialSceneController implements Initializable {

    @FXML private Label titleLabel;

    @FXML private TextField nicknameTextField;
    private String nicknameContent = "";

    private ToggleGroup networkConnectionToggleGroup;
    @FXML private RadioButton rmiRadio;
    @FXML private RadioButton socketRadio;
    private String netWorkConnection = "";

    @FXML private TextField ipTextField;
    private String ipContent = "";
    @FXML private TextField portTextField;
    private String portContent = "";

    @FXML private Button startButton;

    @FXML private Label logLabel;

    @FXML private Hyperlink readRulesHyperlink;
    @FXML private Hyperlink cliHyperlink;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.networkConnectionToggleGroup = new ToggleGroup();
        this.rmiRadio.setToggleGroup(this.networkConnectionToggleGroup);
        this.socketRadio.setToggleGroup(this.networkConnectionToggleGroup);
    }

    private InitialSceneController getInitialSceneController(){
        return ((InitialSceneController)GUIstarter.getStageController());
    }

    @FXML
    public void onStartButtonPressed(){
        Platform.runLater(new Thread(()-> getInitialSceneController().parseInputs()));
    }

    private void parseInputs(){
        //disable buttons
        this.startButton.setDisable(true);
        this.readRulesHyperlink.setDisable(true);
        this.cliHyperlink.setDisable(true);

        //change title
        String titleLabelContent = "CONNECTING...";
        this.titleLabel.setText(titleLabelContent);

        //nickname
        this.nicknameContent = this.nicknameTextField.getText();
        ViewModelGate.setMe(nicknameContent);
        //IP
        this.ipContent = this.ipTextField.getText();
        //PORT
        this.portContent = this.portTextField.getText();
        //networkConnection
        if(this.networkConnectionToggleGroup.getSelectedToggle()==null){
            this.netWorkConnection = "";
        }
        else if(this.networkConnectionToggleGroup.getSelectedToggle().equals(this.rmiRadio)){
            this.netWorkConnection = "RMI";
            this.portContent = "1099";
        }
        else if(this.networkConnectionToggleGroup.getSelectedToggle().equals(this.socketRadio)){
            this.netWorkConnection = "SOCKET";
        }
        else {
            this.netWorkConnection = "";
        }

        //clear the log label
        this.logLabel.setText("");

        (new Thread(()->{
            //check if all field are full
            if(!nicknameContent.equals("") && !netWorkConnection.equals("") && !ipContent.equals("") && !portContent.equals("")){

                ViewModelGate.setMe(this.nicknameContent);

                //Connect to server using the Controller's static method
                (new Thread(() -> {
                    if (Controller.connect(netWorkConnection.toUpperCase(), "GUI", ipContent, portContent)) {
                        //connection was succesfull!
                        Platform.runLater(new Thread(()-> getInitialSceneController().connectionSuccessful()));
                    } else {
                        //say that connection wasn't possible
                        Platform.runLater(new Thread(()-> getInitialSceneController().connectionFailed()));
                    }
                })).start();
            }
            else{
                Platform.runLater(new Thread(()-> getInitialSceneController().wrongInputs()));
            }
        })).start();
    }


    private void wrongInputs(){
        //reset title
        String titleLabelContent = "ADRENALINE LOG-IN:";
        this.titleLabel.setText(titleLabelContent);

        String errorLabelContent = "missing:";
        //show missing data
        if(nicknameContent.equals("")){
            //ask to fill nickname
            errorLabelContent += "[nickname]";
        }
        if(netWorkConnection.equals("")){
            //ask select rmi or socket
            errorLabelContent += "[connection]";
        }
        if(ipContent.equals("")){
            //Ask to insert IP
            errorLabelContent += "[IP]";
        }
        if(portContent.equals("")){
            //Ask to insert Port
            errorLabelContent += "[PORT]";
        }
        this.logLabel.setText(errorLabelContent);
        //enable buttons
        this.startButton.setDisable(false);
        this.readRulesHyperlink.setDisable(false);
        this.cliHyperlink.setDisable(false);
    }

    private void connectionFailed(){
        String errorLabelContent = "Connection wasn't possible, retry!";
        this.logLabel.setText(errorLabelContent);
        //reset title
        String titleLabelContent = "ADRENALINE LOG-IN:";
        this.titleLabel.setText(titleLabelContent);
        //enable buttons
        this.startButton.setDisable(false);
        this.readRulesHyperlink.setDisable(false);
        this.cliHyperlink.setDisable(false);
    }

    private void connectionSuccessful() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("FXML/LOADINGSCENE1.fxml"));
        try {
            Parent root = fxmlLoader.load();

            GUIstarter.setStageController(fxmlLoader.getController());

            Scene scene = new Scene(root);
            scene.setFill(Color.BLACK);

            //hide old stage
            GUIstarter.getStage().hide();

            Stage loadingStage = new Stage();

            GUIstarter.setStage(loadingStage);

            loadingStage.initStyle(StageStyle.DECORATED);
            loadingStage.setTitle("Adrenaline");
            loadingStage.setScene(scene);
            loadingStage.centerOnScreen();
            loadingStage.show();
        } catch (IOException e) {
            GUIstarter.showError(this,"error loading FXML/LOADINGSCENE1.fxml", e);
        }
    }

    @FXML
    public void onCLIHyperlinkPressed(){
        //close stage:
        Platform.runLater(()-> GUIstarter.getStage().hide());
        //starts CLI:
        new Thread(Controller::startClientSocketOrRMIWithCLI).start();
    }

    @FXML
    public void onReadRulesPressed(){
        //TODO show rules
        GUIstarter.showError(this,"TODO: show PDFs of the rules", null);
    }

    @FXML
    public void onEnterPressed(KeyEvent event){
        if(!this.startButton.isDisabled() && event.getCode().equals(KeyCode.ENTER)) {
            this.onStartButtonPressed();
        }
    }

    @FXML
    public void onRMISelected(){
        this.portTextField.setDisable(true);
    }

    @FXML
    public void onSocketSelected(){
        this.portTextField.setDisable(false);
    }

}
