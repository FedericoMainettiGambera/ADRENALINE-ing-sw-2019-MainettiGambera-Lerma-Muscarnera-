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

/**this class controls the fxml of the loading scene
 * @author LudoLerma
 * @author FedericoMainettiGambera*/
public class LoadingSceneController implements Initializable {

    /**stackPanes representing the users*/
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

    /**Labels with the nicknames of the users*/
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

    /**the progress bar label*/
    @FXML
    private Label labelProgress;
    /**the progress bar*/
    @FXML
    private ProgressBar progressBar;

    /**the progress bar goal*/
    private double progressionBarGoal;
    /***/
    private static final String USER_INACTIVE = "userLabelInactive";
    /***/
    private static final String USER_ACTIVE = "userLabelActive";


    /**initialize the loading scene controller
     * launches a thread from progressionBarThread class
     * calls newPlayersList() function*/
    @Override
    public void initialize(URL location, ResourceBundle resources){
        progressionBarGoal=0.0;
        labelProgress.setText("");
        (new Thread(new ProgressBarThread())).start();
        newPlayersList();
    }

    /**depending on the number of connections initialize the stackPaneUsers*/
    public void newPlayersList(){
        if(ViewModelGate.getModel()==null || ViewModelGate.getModel().getPlayers() == null || ViewModelGate.getModel().getPlayers().getPlayers() == null){
            return;
        }
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
    /**initialize a stackPane representing a user with a css style class
     * there is one for each possible user that may connect*/
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
    /**initialize a stackPane representing a user with a css style class
     * there is one for each possible user that may connect*/
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
    /**initialize a stackPane representing a user with a css style class
     * there is one for each possible user that may connect*/
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
    /**initialize a stackPane representing a user with a css style class
     * there is one for each possible user that may connect*/
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
    /**initialize a stackPane representing a user with a css style class
     * there is one for each possible user that may connect*/
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

    /**a class that implements a thread that takes care of incrementing the progression bar*/
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


    /**modify the progress of the progression bar depending on the
     * @param currentTime in reference to the
     * @param totalTime to pass */
    public void modifyProgress(int currentTime, int totalTime){
        if(currentTime == totalTime || progressionBarGoal>=1){
            this.progressBar.setProgress(1.0);
        }
        else {
            double toAdd = (1 - progressionBarGoal) / (totalTime - currentTime);
            progressionBarGoal += toAdd;
        }
    }


    /**change the scene to the game scene*/
    public void changeScene(){
        Platform.runLater(()->{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/FXML/GAME2.fxml"));
            Parent root;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                GUIstarter.showError(this,"COULDN'T LOAD: " +  getClass().getResource("/FXML/GAME2.fxml"), e);
                return;
            }
            GUIstarter.setStageController(fxmlLoader.getController());
            Scene scene = new Scene(root, 1200, 900);
            scene.setFill(Color.BLACK);

            //hide old stage
            GUIstarter.getStage().setScene(scene);
            GUIstarter.getStage().centerOnScreen();
            GUIstarter.getStage().setMaximized(true);
        });
    }
}
