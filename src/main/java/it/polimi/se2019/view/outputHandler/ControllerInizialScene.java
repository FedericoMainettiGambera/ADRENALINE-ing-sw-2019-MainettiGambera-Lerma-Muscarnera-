package it.polimi.se2019.view.outputHandler;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerInizialScene implements Initializable{

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("View is now loaded!");
    }

    public void setNewGame(MouseEvent mouseEvent) throws Exception{

        System.out.println("CIAO");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("GameSetUp.fxml"));
        Scene scene= new Scene(root, 520, 170);



        Stage primaryStage=(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);


    }

    public void setSettings(MouseEvent mouseEvent) throws IOException {


        System.out.println("CIAO");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("BaseGUI.fxml"));
        Scene scene= new Scene(root, 710, 500);



        Stage primaryStage=(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);

    }

    public void showMap(MouseEvent event) throws IOException {

        String map=null;
        switch (event.getButton().toString()) {
            case "map2":
                map = "map2.png"; break;
            case "map0":
                map = "Map0.png"; break;
            case "map1":
                map = "map1.png"; break;
            case "map3":
                map = "map3.png"; break;
            default:
                //nothing

        }
    }
}








