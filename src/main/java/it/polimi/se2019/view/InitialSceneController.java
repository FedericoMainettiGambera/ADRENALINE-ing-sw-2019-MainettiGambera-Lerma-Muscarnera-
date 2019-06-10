package it.polimi.se2019.view;

import it.polimi.se2019.controller.Controller;
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

public class InitialSceneController implements Initializable {
    @FXML private Label titleLabel;

    @FXML private TextField nicknameTextField;
    private String nicknameContent = "";

    private ToggleGroup networkConnectionToggleGroup;
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
        this.networkConnectionToggleGroup = new ToggleGroup();
        this.RMIRadio.setToggleGroup(this.networkConnectionToggleGroup);
        this.socketRadio.setToggleGroup(this.networkConnectionToggleGroup);
    }

    @FXML
    public void onStartButtonPressed() throws IOException {
        //disable buttons
        this.startButton.setDisable(true);
        this.reconnectHyperlink.setDisable(true);
        this.CLIHyperlink.setDisable(true);

        //change title
        String titleLabelContent = "CONNECTING...";
        this.titleLabel.setText(titleLabelContent);

        //nickname
        this.nicknameContent = this.nicknameTextField.getText();
        //IP
        this.IPContent = this.IPTextField.getText();
        //PORT
        this.PORTContent = this.PORTTextField.getText();
        //networkConnection
        if(this.networkConnectionToggleGroup.getSelectedToggle()==null){
            this.netWorkConnection = "";
        }
        else if(this.networkConnectionToggleGroup.getSelectedToggle().equals(this.RMIRadio)){
            this.netWorkConnection = "RMI";
            this.PORTContent = "1099";
        }
        else if(this.networkConnectionToggleGroup.getSelectedToggle().equals(this.socketRadio)){
            this.netWorkConnection = "SOCKET";
        }
        else {
            this.netWorkConnection = "";
        }

        //check if all field are full
        if(!nicknameContent.equals("") && !netWorkConnection.equals("") && !IPContent.equals("") && !PORTContent.equals("")){
            //clear the log label
            this.logLabel.setText("");

            //Connect to server using the Controller's static method
            if(Controller.connect(netWorkConnection.toUpperCase(),"GUI",IPContent,PORTContent)) {
                //connection was succesfull!

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getClassLoader().getResource("LOADINGSCENE1.fxml"));
                Parent root = fxmlLoader.load();
                GUIstarter.stageController=fxmlLoader.getController();
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

                ((LoadingSceneController)fxmlLoader.getController()).setNickname(this.nicknameContent);
            }
            else {
                //say that connection wasn't possible
                String errorLabelContent = "Connection wasn't possible, retry!";
                this.logLabel.setText(errorLabelContent);
                //reset title
                titleLabelContent = "ADRENALINE LOG-IN:";
                this.titleLabel.setText(titleLabelContent);
                //enable buttons
                this.startButton.setDisable(false);
                this.reconnectHyperlink.setDisable(false);
                this.CLIHyperlink.setDisable(false);
            }

        }
        else{
            //reset title
            titleLabelContent = "ADRENALINE LOG-IN:";
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
            if(IPContent.equals("")){
                //Ask to insert IP
                errorLabelContent += "[IP]";
            }
            if(PORTContent.equals("")){
                //Ask to insert Port
                errorLabelContent += "[PORT]";
            }
            this.logLabel.setText(errorLabelContent);
            //enable buttons
            this.startButton.setDisable(false);
            this.reconnectHyperlink.setDisable(false);
            this.CLIHyperlink.setDisable(false);
        }
    }

    @FXML
    public void onCLIHyperlinkPressed(Event event) throws IOException {
        //chiude lo stage:
        ((Node)(event.getSource())).getScene().getWindow().hide();
        //fa partire CLI:
        GUIstarter.user.startClientSocketOrRMIWithCLI();
    }

    @FXML
    public void onReconnectionPressed(){
        //TODO
    }

    @FXML
    public void onEnterPressed(KeyEvent event) throws IOException {
        if(!this.startButton.isDisabled() && event.getCode().equals(KeyCode.ENTER)) {
            this.onStartButtonPressed();
        }
    }

    @FXML
    public void onRMISelected(){
        this.PORTTextField.setDisable(true);
    }

    @FXML
    public void onSocketSelected(){
        this.PORTTextField.setDisable(false);
    }

}
