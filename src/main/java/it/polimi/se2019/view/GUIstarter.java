package it.polimi.se2019.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GUIstarter extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("INITIALSTAGE1.fxml"));
        Scene scene= new Scene(root, 420, 461);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    //instead of having directly a main class here, the controller lounches this method.
    public static void begin(){
        launch();
    }
}
