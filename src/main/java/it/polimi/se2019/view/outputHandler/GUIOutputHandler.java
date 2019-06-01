package it.polimi.se2019.view.outputHandler;
import it.polimi.se2019.view.components.BoardV;
import it.polimi.se2019.view.components.ViewModelGate;
import javafx.application.*;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.*;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.event.*;

import javafx.scene.canvas.Canvas;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Observable;


public class GUIOutputHandler extends Application{



    public static void main(String[] args){

        launch(args);
    }


    public void start(Stage stage)throws Exception {

        VBox vbox = new VBox();
//        BoardV boardV = ViewModelGate.getModel().getBoard();

        ListView<String> listView = new ListView<String>();


        ObservableList<String> list = FXCollections.observableArrayList();
        listView.setItems(list);
        list.add("map0");
        list.add("map1");
        list.add("map2");
        list.add("map3");


        listView.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                ObservableList<String> selectedItems = listView.getSelectionModel().getSelectedItems();

                for (String s : selectedItems) {

                   Image map=null;
                    switch (s){
                        case "map2":
                            try {
                                map = new Image(new FileInputStream("src/main/Files/Images/Map/map2.png"));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "map0":
                            try {
                                map = new Image(new FileInputStream("src/main/Files/Images/Map/Map0.png"));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "map1":
                            try {
                                map = new Image(new FileInputStream("src/main/Files/Images/Map/Map1.png"));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "map3":
                            try {
                                map = new Image(new FileInputStream("src/main/Files/Images/Map/map3.png"));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            break;
                        default:
                            //nothing

                    }

                    ImageView imageView = new ImageView(map);
                    imageView.setPreserveRatio(false);
                    imageView.resize(500, 600);
                    imageView.autosize();
                    imageView.setPreserveRatio(false);
                    Scene scene=new Scene(vbox, 600, 600);
                    imageView.fitWidthProperty().bind(scene.widthProperty());
                    imageView.fitHeightProperty().bind(scene.heightProperty());
                    VBox vbox1=new VBox();
                    vbox.getChildren().add(imageView);

                    stage.setScene(scene);
                }

            }

        });



        Pane  root = new Pane();
        Scene scene = new Scene(root,600,600);
        listView.getOpaqueInsets();
        listView.autosize();
        root.getChildren().add(listView);
        scene.fillProperty();

        stage.setScene(scene);
        stage.show();
    }






    }

