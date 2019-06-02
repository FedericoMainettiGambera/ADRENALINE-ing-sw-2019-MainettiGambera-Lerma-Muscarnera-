package it.polimi.se2019.view.outputHandler;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.text.LabelView;
import javax.swing.text.Position;
import java.io.FileInputStream;

public class GUIInizialScene extends Application implements EventHandler<ActionEvent>{



    public static void main(String[] args){

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        Image image=new Image(new FileInputStream("src/main/Files/Images/InizialImage/Adrenaline.png"));
        ImageView imageView=new ImageView(image);

        Image image1=new Image(new FileInputStream("src/main/Files/Images/InizialImage/NewGame.png"));
        ImageView imageView1=new ImageView(image1);

        Image image2=new Image(new FileInputStream("src/main/Files/Images/InizialImage/Settings.png"));
        ImageView imageView2=new ImageView(image2);

        StackPane pane=new StackPane();
        Scene scene=new Scene(pane, 500, 500);
        imageView.fitWidthProperty().bind(scene.widthProperty());
        imageView.fitHeightProperty().bind(scene.heightProperty());

        imageView1.setFitWidth(150);
        imageView1.setFitHeight(25);
        imageView2.setFitWidth(150);
        imageView2.setFitHeight(25);
        imageView1.setSmooth(true);


        StackPane pane1=new StackPane();
        VBox vbox=new VBox();

        vbox.getChildren().addAll(imageView1,imageView2);

        pane.getChildren().add(imageView);
       // pane.getChildren().add(imageView1);
        pane1.getChildren().addAll(vbox);


       // pane.setAlignment(Pos.CENTER_LEFT);


        pane.getChildren().add(pane1);
        pane1.setAlignment(Pos.CENTER_RIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();


    }


    @Override
    public void handle(ActionEvent event) {

    }
}
