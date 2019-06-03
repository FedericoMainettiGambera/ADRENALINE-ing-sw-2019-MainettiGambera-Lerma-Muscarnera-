package it.polimi.se2019.view.outputHandler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIOutputHandler extends Application{

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception{

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("GUIFirstStage.fxml"));
            Scene scene= new Scene(root, 800, 500);
            primaryStage.setTitle("Hello World");




            primaryStage.setScene(scene);
            primaryStage.show();
        }


    }


