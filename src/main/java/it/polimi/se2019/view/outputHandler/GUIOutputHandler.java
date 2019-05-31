package it.polimi.se2019.view.outputHandler;
import javafx.application.*;
import javafx.animation.*;
import javafx.event.EventType;
import javafx.scene.*;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.FileInputStream;


public class GUIOutputHandler extends Application{

    Label label;

    public static void main(String[] args){

        launch(args);
    }


    public void start(Stage stage)throws Exception{

       VBox vbox=new VBox();




        Image image=new Image(new FileInputStream("src/main/Files/Images/Map/Map2.jpg"));


        ImageView imageView=new ImageView(image);
        imageView.setPreserveRatio(false);
        imageView.resize(50000,60000);


        vbox.getChildren().add(imageView);



        Scene scene=new Scene(vbox, 300, 200);
        imageView.fitWidthProperty().bind(scene.widthProperty());
        imageView.fitHeightProperty().bind(scene.heightProperty());

        stage.setScene(scene);
        stage.setTitle("ADRENALINE");



        stage.show();


    }

}