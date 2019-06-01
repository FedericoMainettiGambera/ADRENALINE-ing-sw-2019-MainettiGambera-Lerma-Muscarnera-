package it.polimi.se2019.view.outputHandler;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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

public class GUIInizialScene extends Application{



    public static void main(String[] args){

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        StackPane pane=new StackPane();
        StackPane pane1=new StackPane();
        Image image=new Image(new FileInputStream("src/main/Files/Images/InizialImage/Adrenaline.png"));
        ImageView imageView=new ImageView(image);

        Image image1=new Image(new FileInputStream("src/main/Files/Images/InizialImage/NewGame.png"));
        ImageView imageView1=new ImageView(image1);

        Image image2=new Image(new FileInputStream("src/main/Files/Images/InizialImage/Settings.png"));
        ImageView imageView2=new ImageView(image2);


        Scene scene=new Scene(pane, 500, 500);
        imageView.fitWidthProperty().bind(scene.widthProperty());
        imageView.fitHeightProperty().bind(scene.heightProperty());

        imageView1.setFitWidth(150);
        imageView1.setFitHeight(25);
        imageView2.setFitWidth(150);
        imageView2.setFitHeight(25);
        imageView1.setSmooth(true);

        pane.getChildren().add(imageView);
        pane.getChildren().add(imageView1);
        pane1.getChildren().add(imageView2);

        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){

                GUIOutputHandler gui=new GUIOutputHandler();

                try {
                    gui.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                    launch();
                


            }
        });

        pane.setAlignment(Pos.CENTER_LEFT);


        pane.getChildren().add(pane1);
        pane1.setAlignment(Pos.CENTER_RIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();


    }


}
