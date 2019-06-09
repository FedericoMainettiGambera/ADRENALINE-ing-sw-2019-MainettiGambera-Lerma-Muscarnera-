package it.polimi.se2019.view;

import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.view.components.PlayersListV;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
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
        (new progressBarThread()).start();
        progressionBarGoal = 0.4;
    }

    public void newPlayersList(ModelViewEvent MVE){
        int numberOfConnection=((PlayersListV)MVE.getComponent()).getPlayers().size();
        if(numberOfConnection>=1) {
            stackPaneUser1.getStyleClass().add("User1Active");
            labelUser1.setText(((PlayersListV)MVE.getComponent()).getPlayers().get(0).getNickname());

            if(labelUser1.getStyleClass().contains("userLabelInactive")){
                labelUser1.getStyleClass().removeAll("userLabelInactive");
                labelUser1.getStyleClass().add("userLabelActive");
            }
        }
        if(numberOfConnection>=2) {
            stackPaneUser2.getStyleClass().add("User2Active");
            labelUser2.setText(((PlayersListV)MVE.getComponent()).getPlayers().get(1).getNickname());

            if(labelUser2.getStyleClass().contains("userLabelInactive")){
                labelUser2.getStyleClass().removeAll("userLabelInactive");
                labelUser2.getStyleClass().add("userLabelActive");
            }
        }
        if(numberOfConnection>=3) {
            stackPaneUser3.getStyleClass().add("User3Active");
            labelUser3.setText(((PlayersListV)MVE.getComponent()).getPlayers().get(2).getNickname());

            if(labelUser3.getStyleClass().contains("userLabelInactive")){
                labelUser3.getStyleClass().removeAll("userLabelInactive");
                labelUser3.getStyleClass().add("userLabelActive");
            }
        }
        if(numberOfConnection>=4) {
            stackPaneUser4.getStyleClass().add("User4Active");
            labelUser4.setText(((PlayersListV)MVE.getComponent()).getPlayers().get(3).getNickname());

            if(labelUser4.getStyleClass().contains("userLabelInactive")){
                labelUser4.getStyleClass().removeAll("userLabelInactive");
                labelUser4.getStyleClass().add("userLabelActive");
            }
        }
        if(numberOfConnection>=5) {
            stackPaneUser5.getStyleClass().add("User5Active");
            labelUser5.setText(((PlayersListV)MVE.getComponent()).getPlayers().get(4).getNickname());

            if(labelUser5.getStyleClass().contains("userLabelInactive")){
                labelUser5.getStyleClass().removeAll("userLabelInactive");
                labelUser5.getStyleClass().add("userLabelActive");
            }
        }
    }

    private class progressBarThread extends Thread{
        public boolean stop;
        @Override
         public void run(){
            stop=false;
            while(!stop){
               if(progressBar.getProgress()<=LoadingSceneController.progressionBarGoal){
                   progressBar.setProgress(progressBar.getProgress()+0.001);
                   double number=0.000;
                   labelProgress.setText(""+number);
                   try {
                       TimeUnit .MILLISECONDS.sleep(50);
                       number+=0.001;
                   } catch (InterruptedException e) {
                       stop=true;
                   }
               }

            }
        }
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }
}
