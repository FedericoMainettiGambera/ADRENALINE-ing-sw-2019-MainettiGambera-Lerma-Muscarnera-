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

/**this class controls the initial scene of the game
 * @author LudoLerma
 * @author FedericoMainettiGambera*/
public class InitialSceneController implements Initializable {

    /**label with the title*/
    @FXML private Label titleLabel;
    /**textfield where to input the nickname*/
    @FXML private TextField nicknameTextField;
    /**content of the nicknameTextFiels*/
    private String nicknameContent = "";
    /**a toggle group for selecting the connection methods*/
    private ToggleGroup networkConnectionToggleGroup;
    /**rmi radioButton*/
    @FXML private RadioButton rmiRadio;
    /**socket radio button*/
    @FXML private RadioButton socketRadio;
    private String netWorkConnection = "";
    /**textfield where to insert the ip*/
    @FXML private TextField ipTextField;
    /**content of the textfield of where to insert the ip*/
    private String ipContent = "";
    /**textfield where to insert the port*/
    @FXML private TextField portTextField;
    /**content of the portTextField*/
    private String portContent = "";
    /**button to start the game*/
    @FXML private Button startButton;
    /**the label of the log*/
    @FXML private Label logLabel;
    /**read rules hyperlink*/
    @FXML private Hyperlink readRulesHyperlink;
    /**hyperlink for passing in cli modality*/
    @FXML private Hyperlink cliHyperlink;
    /**initialize the scene*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.networkConnectionToggleGroup = new ToggleGroup();
        this.rmiRadio.setToggleGroup(this.networkConnectionToggleGroup);
        this.socketRadio.setToggleGroup(this.networkConnectionToggleGroup);
    }
    /**@return the initialSceneController*/
    private InitialSceneController getInitialSceneController(){
        return ((InitialSceneController)GUIstarter.getStageController());
    }

    /**when the start button is pressed, the input inserted are parsed*/
    @FXML
    public void onStartButtonPressed(){
        Platform.runLater(new Thread(()-> getInitialSceneController().parseInputs()));
    }

    /**parse the input inserted in the initial scene
     * such as the ip, the port, the nickname and the method connection
     * and attempt a connection*/
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

    /**in case some of the input isn't correct, this function is called
     * it shows a message of error near by the wrong field inserted*/
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
    /**in case the connection attempt fails,
     * this function is called,
     * it enable the user to make a second attempt*/
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
    /**in case the connection attempt succeds,
     * this function is called and the loading scene is called*/
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

    /**if the Cli hyperlink is pressed, the stage is hidden and the cli mode game is started*/
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
    /**if the user hit the enter key, the game is started
     * @param event key event*/
    @FXML
    public void onEnterPressed(KeyEvent event){
        if(!this.startButton.isDisabled() && event.getCode().equals(KeyCode.ENTER)) {
            this.onStartButtonPressed();
        }
    }
    /**if the rmi connection is selected, the port text field is disabled*/
    @FXML
    public void onRMISelected(){
        this.portTextField.setDisable(true);
    }

    /**if the socket connection is selected, the port text field is abled*/
    @FXML
    public void onSocketSelected(){
        this.portTextField.setDisable(false);
    }

}
