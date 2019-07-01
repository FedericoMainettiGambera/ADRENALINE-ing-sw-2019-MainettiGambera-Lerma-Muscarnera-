package it.polimi.se2019.view;

import it.polimi.se2019.view.components.ViewModelGate;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class LoadingSceneController implements Initializable {

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

    //progressionBar
    private double progressionBarGoal;

    private static final String USER_INACTIVE = "userLabelInactive";
    private static final String USER_ACTIVE = "userLabelActive";



    @Override
    public void initialize(URL location, ResourceBundle resources){
        progressionBarGoal=0.0;
        labelProgress.setText("");
        (new Thread(new ProgressBarThread())).start();
    }

    public void newPlayersList(){
        int numberOfConnection=ViewModelGate.getModel().getPlayers().getPlayers().size();
        if(numberOfConnection>=1 && !stackPaneUser1.getStyleClass().contains("User1Active")) {
            Platform.runLater(new SetUser1());
        }
        if(numberOfConnection>=2 && !stackPaneUser2.getStyleClass().contains("User2Active")){
            Platform.runLater(new SetUser2());
        }
        if(numberOfConnection>=3 && !stackPaneUser3.getStyleClass().contains("User3Active")) {
            Platform.runLater(new SetUser3());
        }
        if(numberOfConnection>=4 && !stackPaneUser4.getStyleClass().contains("User4Active")) {
            Platform.runLater(new SetUser4());
        }
        if(numberOfConnection>=5 && !stackPaneUser5.getStyleClass().contains("User5Active")) {
            Platform.runLater(new SetUser5());
        }
    }
    private class SetUser1 extends Thread{
        @Override
        public void run(){
            stackPaneUser1.getStyleClass().add("User1Active");
            labelUser1.setText(ViewModelGate.getModel().getPlayers().getPlayers().get(0).getNickname());

            if(labelUser1.getStyleClass().contains(USER_INACTIVE)){
                labelUser1.getStyleClass().removeAll(USER_INACTIVE);
                labelUser1.getStyleClass().add(USER_ACTIVE);
                progressionBarGoal+=(1-progressionBarGoal)/5;
            }
        }
    }
    private class SetUser2 extends Thread{
        @Override
        public void run(){
            stackPaneUser2.getStyleClass().add("User2Active");
            labelUser2.setText(ViewModelGate.getModel().getPlayers().getPlayers().get(1).getNickname());

            if(labelUser2.getStyleClass().contains(USER_INACTIVE)){
                labelUser2.getStyleClass().removeAll(USER_INACTIVE);
                labelUser2.getStyleClass().add(USER_ACTIVE);
                progressionBarGoal+=(1-progressionBarGoal)/4;
            }

        }
    }
    private class SetUser3 extends Thread{
        @Override
        public void run(){
            stackPaneUser3.getStyleClass().add("User3Active");
            labelUser3.setText(ViewModelGate.getModel().getPlayers().getPlayers().get(2).getNickname());

            if(labelUser3.getStyleClass().contains(USER_INACTIVE)){
                labelUser3.getStyleClass().removeAll(USER_INACTIVE);
                labelUser3.getStyleClass().add(USER_ACTIVE);
                progressionBarGoal+=(1-progressionBarGoal)/3;
            }
        }
    }
    private class SetUser4 extends Thread{
        @Override
        public void run(){
            stackPaneUser4.getStyleClass().add("User4Active");
            labelUser4.setText(ViewModelGate.getModel().getPlayers().getPlayers().get(3).getNickname());

            if(labelUser4.getStyleClass().contains(USER_INACTIVE)){
                labelUser4.getStyleClass().removeAll(USER_INACTIVE);
                labelUser4.getStyleClass().add(USER_ACTIVE);
                progressionBarGoal+=(1-progressionBarGoal)/2;

            }
        }
    }
    private class SetUser5 extends Thread{
        @Override
        public void run(){
            stackPaneUser5.getStyleClass().add("User5Active");
            labelUser5.setText(ViewModelGate.getModel().getPlayers().getPlayers().get(4).getNickname());

            if(labelUser5.getStyleClass().contains(USER_INACTIVE)){
                labelUser5.getStyleClass().removeAll(USER_INACTIVE);
                labelUser5.getStyleClass().add(USER_ACTIVE);
                progressionBarGoal=1;
                progressBar.setProgress(1.0);
            }
        }
    }


    private class ProgressBarThread implements Runnable{
        @Override
        public void run(){
            boolean stop=false;
            while(!stop){
                if(progressBar.getProgress() >= 1.0){
                    progressBar.setProgress(1.0);
                    break;
                }
                if(progressBar.getProgress()<=progressionBarGoal){
                    progressBar.setProgress(progressBar.getProgress()+0.009);
                    try {
                        TimeUnit .MILLISECONDS.sleep(50);
                    } catch (InterruptedException e) {
                        stop=true;
                        Thread.currentThread().interrupt();
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
        }
    }


    public void changeScene(){
        Platform.runLater(()->{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/FXML/GAME2.fxml"));
            Parent root;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                GUIstarter.showError(this,"COULDN'T FIND: " +  getClass().getResource("/FXML/GAME2.fxml"), e);
                return;
            }
            GUIstarter.setStageController(fxmlLoader.getController());
            Scene scene = new Scene(root, 1200, 900);
            scene.setFill(Color.BLACK);

            //hide old stage
            GUIstarter.getStage().setScene(scene);
            GUIstarter.getStage().centerOnScreen();
        });
    }
}
