package it.polimi.se2019.view.outputHandler;

import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventGameSetUp;
import it.polimi.se2019.networkHandler.RMI.RMINetworkHandler;
import it.polimi.se2019.networkHandler.Socket.SocketNetworkHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class ControllerInizialScene implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("View is now loaded!");
    }

    public void setNewGame(MouseEvent mouseEvent) throws Exception {

        System.out.println("CIAO");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("GameSetUp.fxml"));
        Scene scene = new Scene(root, 390, 360);


        Stage primaryStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);


    }

    public void setSettings(MouseEvent mouseEvent) throws IOException {


        System.out.println("CIAO");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("BaseGUI.fxml"));
        Scene scene = new Scene(root, 710, 500);


        Stage primaryStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        primaryStage.setScene(scene);

    }

    //GAMESETUPSTAGE*********************************************************************************
    @FXML
    ImageView imageId;

    @FXML
    public void showMap(MouseEvent event) throws IOException{
        String map;

       if(event.getSource().toString().contains("map0")){
        map = "src/main/resources/Map0.png";
       }

       else if((event.getSource().toString().contains("map2"))){
             map = "src/main/resources/map2.png";
       }

       else if((event.getSource().toString().contains("map1"))){
                map = "src/main/resources/Map1.png";}

       else{
            map = "src/main/resources/map3.png";
        }

        Image image=new Image(new FileInputStream(map));
        imageId.setImage(image);

    }

    @FXML
    String mapChoice;
    String numberOfStartingSkulls;
    String FinalFrenzy;
    @FXML
    ToggleGroup map;
    @FXML
    ToggleGroup startingSkulls;
    @FXML
    ToggleGroup finalFrenzy;

    boolean isFinalFrenzy;

    @FXML
    public void GameSetUp(){

        mapChoice=(( RadioButton)(map.getSelectedToggle())).getText();
        numberOfStartingSkulls=(( RadioButton)(startingSkulls.getSelectedToggle())).getText();
        FinalFrenzy=(( RadioButton)(finalFrenzy.getSelectedToggle())).getText();


        if(FinalFrenzy.equals("Yes"))
        { isFinalFrenzy=true; }
        else{isFinalFrenzy=false;}


        String gameMode="normalMode";
        boolean isBotActive=false;



        ViewControllerEventGameSetUp VCEGameSetUp = new ViewControllerEventGameSetUp(gameMode,mapChoice,Integer.parseInt(numberOfStartingSkulls),isFinalFrenzy,isBotActive);
        sendToServer(VCEGameSetUp);

    }

//*****************************************************************************************************

    private String networkConnection;

    public void sendToServer(Object o){
        if(networkConnection.equals("SOCKET")){
            try {
                SocketNetworkHandler.oos.writeObject(o);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                RMINetworkHandler.client.returnInterface().sendToServer(o);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public  void GUISelector(String networkConnection){
        this.networkConnection = networkConnection;
    }


}








