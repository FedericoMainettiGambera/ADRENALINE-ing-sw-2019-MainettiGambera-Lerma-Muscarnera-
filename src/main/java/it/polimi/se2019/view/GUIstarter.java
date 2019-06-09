package it.polimi.se2019.view;

import it.polimi.se2019.controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class GUIstarter extends Application {
    public static Controller user;

    public static Stage stage;
    public static Object stageController;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("INITIALSCENE1.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene= new Scene(root, 430, 529);
        scene.setFill(Color.TRANSPARENT);

        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Adrenaline LOG-IN");

        stage=primaryStage;
        stageController=fxmlLoader.getController();

        stage.setScene(scene);
        stage.show();
    }

    //instead of having directly a main class here, the controller launches this method.
    public static void begin(){
        launch();
    }

}
