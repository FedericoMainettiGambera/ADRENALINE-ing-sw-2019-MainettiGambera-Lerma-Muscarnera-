package it.polimi.se2019.view;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.view.components.ViewModelGate;
import javafx.application.Platform;
import javafx.event.Event;
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

    @FXML private Hyperlink reconnectHyperlink;
    @FXML private Hyperlink cliHyperlink;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.networkConnectionToggleGroup = new ToggleGroup();
        this.rmiRadio.setToggleGroup(this.networkConnectionToggleGroup);
        this.socketRadio.setToggleGroup(this.networkConnectionToggleGroup);
    }

    @FXML
    public void onStartButtonPressed() throws IOException {
        //TODO probably clicking the button will freezy temporary the gui because it is tryin to do others stuff
        //     so you should do all of this in a separate thread, probably works fine using only Platform.runLater(...)
        //     if Platform.runLater() doesn't work, make some research about the javafx.concurrent.Task Class, it may help you
        //     otherwise you should use Task and Service to do the work (optimal solution!!)
        Platform.runLater(new Thread(()->{
            ((InitialSceneController)GUIstarter.stageController).parseInputs();
        }));

        //check if all field are full
        if(!nicknameContent.equals("") && !netWorkConnection.equals("") && !ipContent.equals("") && !portContent.equals("")){
            //clear the log label
            this.logLabel.setText("");

            ViewModelGate.setMe(this.nicknameContent);

            //Connect to server using the Controller's static method
            (new Thread(){
                @Override
                public void run () {
                    if (Controller.connect(netWorkConnection.toUpperCase(), "GUI", ipContent, portContent)) {
                        //connection was succesfull!
                        Platform.runLater(new Thread(()->{
                            ((InitialSceneController)GUIstarter.stageController).connectionSuccesfull();
                        }));
                    } else {
                        //say that connection wasn't possible
                        Platform.runLater(new Thread(()->{
                            ((InitialSceneController)GUIstarter.stageController).connectionFailed();
                        }));
                    }
                }
            }).start();
        }
        else{
            Platform.runLater(new Thread(()->{
                ((InitialSceneController)GUIstarter.stageController).wrongInputs();
            }));
        }
    }

    private void parseInputs(){
        //disable buttons
        this.startButton.setDisable(true);
        this.reconnectHyperlink.setDisable(true);
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
            //ask select RMI or Socket
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
        this.reconnectHyperlink.setDisable(false);
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
        this.reconnectHyperlink.setDisable(false);
        this.cliHyperlink.setDisable(false);
    }

    private void connectionSuccesfull() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("FXML/LOADINGSCENE1.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
            GUIstarter.stageController = fxmlLoader.getController();
            Scene scene = new Scene(root);
            scene.setFill(Color.BLACK);

            //hide old stage
            GUIstarter.stage.hide();

            Stage loadingStage = new Stage();
            GUIstarter.stage = loadingStage;

            loadingStage.initStyle(StageStyle.DECORATED);
            loadingStage.setTitle("Adrenaline");
            loadingStage.setScene(scene);
            loadingStage.show();
        } catch (IOException e) {
            //TODO show that resource is missing...
            e.printStackTrace();
        }
    }

    @FXML
    public void onCLIHyperlinkPressed(Event event) throws IOException {
        //chiude lo stage:
        GUIstarter.stage.hide();
        //fa partire CLI:
        GUIstarter.user.startClientSocketOrRMIWithCLI();
    }

    @FXML
    public void onReconnectionPressed(){
        //TODO delete (?)
    }

    @FXML
    public void onEnterPressed(KeyEvent event) throws IOException {
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
