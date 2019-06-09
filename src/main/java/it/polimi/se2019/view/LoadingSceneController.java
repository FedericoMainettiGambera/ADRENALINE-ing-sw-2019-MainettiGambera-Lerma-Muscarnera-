package it.polimi.se2019.view;

import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.view.components.PlayersListV;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class LoadingSceneController implements Initializable {


    //progressionBar
    public static double progressionBarGoal;

    //stackPanes
    @FXML
    private StackPane stackPaneUser1;
    @FXML
    private StackPane stackPaneUser2;
    @FXML
    private StackPane stackPaneUser3;
    @FXML
    private StackPane stackPaneUser4;
    @FXML
    private StackPane stackPaneUser5;

    //Labels
    @FXML
    private Label labelUser1;
    @FXML
    private Label labelUser2;
    @FXML
    private Label labelUser3;
    @FXML
    private Label labelUser4;
    @FXML
    private Label labelUser5;

    //progress
    @FXML
    private Label labelProgress;
    @FXML
    private ProgressBar progressBar;



    private String nickname;


    @Override
    public void initialize(URL location, ResourceBundle resources){
        progressionBarGoal=0;
        labelProgress.setText("");
        (new Thread(new progressBarThread())).start();
    }

    public void newPlayersList(ModelViewEvent MVE){
        int numberOfConnection=((PlayersListV)MVE.getComponent()).getPlayers().size();
        if(numberOfConnection>=1) {
            stackPaneUser1.getStyleClass().add("User1Active");
            labelUser1.setText(((PlayersListV)MVE.getComponent()).getPlayers().get(0).getNickname());

            if(labelUser1.getStyleClass().contains("userLabelInactive")){
                labelUser1.getStyleClass().removeAll("userLabelInactive");
                labelUser1.getStyleClass().add("userLabelActive");
                progressionBarGoal+=(1-progressionBarGoal)/5;
            }
        }
        if(numberOfConnection>=2){
            stackPaneUser2.getStyleClass().add("User2Active");
            labelUser2.setText(((PlayersListV)MVE.getComponent()).getPlayers().get(1).getNickname());

            if(labelUser2.getStyleClass().contains("userLabelInactive")){
                labelUser2.getStyleClass().removeAll("userLabelInactive");
                labelUser2.getStyleClass().add("userLabelActive");
                progressionBarGoal+=(1-progressionBarGoal)/4;
            }

        }
        if(numberOfConnection>=3) {
            stackPaneUser3.getStyleClass().add("User3Active");
            labelUser3.setText(((PlayersListV)MVE.getComponent()).getPlayers().get(2).getNickname());

            if(labelUser3.getStyleClass().contains("userLabelInactive")){
                labelUser3.getStyleClass().removeAll("userLabelInactive");
                labelUser3.getStyleClass().add("userLabelActive");
                progressionBarGoal+=(1-progressionBarGoal)/3;

            }
        }
        if(numberOfConnection>=4) {
            stackPaneUser4.getStyleClass().add("User4Active");
            labelUser4.setText(((PlayersListV)MVE.getComponent()).getPlayers().get(3).getNickname());

            if(labelUser4.getStyleClass().contains("userLabelInactive")){
                labelUser4.getStyleClass().removeAll("userLabelInactive");
                labelUser4.getStyleClass().add("userLabelActive");
                progressionBarGoal+=(1-progressionBarGoal)/2;

            }
        }
        if(numberOfConnection>=5) {
            stackPaneUser5.getStyleClass().add("User5Active");
            labelUser5.setText(((PlayersListV)MVE.getComponent()).getPlayers().get(4).getNickname());

            if(labelUser5.getStyleClass().contains("userLabelInactive")){
                labelUser5.getStyleClass().removeAll("userLabelInactive");
                labelUser5.getStyleClass().add("userLabelActive");
                progressionBarGoal+=1;
                this.progressBar.setProgress(1.0);
                try {
                    this.changeScene("GAMESCENE1.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private class progressBarThread implements Runnable{
        public boolean stop;
        @Override
        public void run(){
            stop=false;
            while(!stop){
                if(progressBar.getProgress() >= 1.0){
                    progressBar.setProgress(1.0);
                    break;
                }
                if(progressBar.getProgress()<=LoadingSceneController.progressionBarGoal){
                    progressBar.setProgress(progressBar.getProgress()+0.002);
                    try {
                        TimeUnit .MILLISECONDS.sleep(50);
                    } catch (InterruptedException e) {
                        stop=true;
                    }
                }

            }
        }
    }


    public void modifyProgress(int currentTime, int totalTime){
        if(currentTime == totalTime || progressionBarGoal>=1){
            this.progressBar.setProgress(1.0);
        }
        else {
            double toAdd = (1 - progressionBarGoal) / (totalTime - currentTime);
            progressionBarGoal += toAdd;
            this.progressBar.setProgress(progressionBarGoal);
        }
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public void changeScene(String path) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource(path));
        Parent root = fxmlLoader.load();
        GUIstarter.stageController=fxmlLoader.getController();
        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);

        //hide old stage
        GUIstarter.stage.setScene(scene);
    }
}
