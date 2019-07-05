package it.polimi.se2019.view;

import it.polimi.se2019.controller.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
/**this class starts the GUI
 * @author LudoLerma
 *     @author FedericoMainettiGambera*/
public class GUIstarter extends Application {

    /**the main stage*/
    private static Stage stage;
    /**the controller of the stage*/
    private static Object stageController;

    /**@return stage*/
    static Stage getStage(){
        return stage;
    }

    /**@param stage to set stage attribute*/
    static void setStage(Stage stage){
        GUIstarter.stage = stage;
    }
    /**@return stageController*/
    public static Object getStageController(){
        return stageController;
    }
    /**@param stageController  to set stageController attribute*/
    static void setStageController(Object stageController){
        GUIstarter.stageController = stageController;
    }

    /**@param primaryStage the stage to be started
     * this method load the fxml of the initial scene and show it on the primary stage
     * */
    @Override
    public void start(Stage primaryStage) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("FXML/INITIALSCENE1.fxml"));
        Parent root;
        try {
            root = fxmlLoader.load();
            Scene scene= new Scene(root, 430, 529);
            scene.setFill(Color.TRANSPARENT);

            primaryStage.setResizable(false);
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.setTitle("Adrenaline LOG-IN");

            setStage(primaryStage);
            setStageController(fxmlLoader.getController());

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            if(Controller.getUserInterface().equalsIgnoreCase("GUI")){
                showError(this, "COULDN'T LOAD FILE", e);
            }
        }

    }

    /**instead of having directly a main class here, the controller launches this method.*/
    public static void begin(){
        launch();
    }

    /**@param callingClass the class in which an error occurred
     * @param e the exception that occurred
     * @param error the string representing the error
     * this method shows an error*/
    public static void showError(Object callingClass, String error, Exception e){
        //show a pop up with the given message and exception
        if (Platform.isFxApplicationThread()) {
            buildMessage(callingClass, error, e);
        } else {
            Platform.runLater(() -> buildMessage(callingClass, error, e));
        }
    }

    /**@param callingClass the class in which an error occurred
     * @param e the exception that occurred
     * @param error the string representing the error
     * this method build a message of error*/
    private static void buildMessage(Object callingClass, String error, Exception e){
        Stage s = new Stage();
        VBox v = new VBox();
        v.setStyle("-fx-background-color: #a0a0a0");
        v.setStyle("-fx-padding: 20px");

        Label l1 = new Label("MESSAGE: [ " + error + " ]");
        l1.setStyle("-fx-padding: 10px 0");
        l1.setStyle("-fx-font-weight: bold");
        v.getChildren().add(l1);

        if(e != null) {
            Label l2 = new Label(e.getMessage());
            l2.setStyle("-fx-text-fill: darkRed");
            v.getChildren().add(l2);
        }

        Label l3 = new Label();
        l3.setStyle("-fx-text-fill: darkRed");
        if(callingClass.getClass().toString().contains("String")){
            l3.setText("called from class: " + callingClass);
        }
        else {
            l3.setText("called from class: " + callingClass.getClass().toString());
        }
        v.getChildren().add(l3);

        Scene sc = new Scene(v);
        s.setTitle("MESSAGE");
        s.setScene(sc);
        s.show();
    }

}
