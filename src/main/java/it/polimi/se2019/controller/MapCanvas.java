package it.polimi.se2019.controller;

import it.polimi.se2019.model.Board;
import it.polimi.se2019.view.components.BoardV;
import it.polimi.se2019.view.components.GameV;
import it.polimi.se2019.view.components.ViewModelGate;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MapCanvas extends Application {

    // TEMPORARY MAIN EXAMPLE
    @Override
    public void init() throws IOException {
        //INITIALIZE FAKE MODEL
        Board board = new Board("map0", null, null);
        System.out.println(board.toString());

        BoardV boardV = board.buildBoardV();
        GameV gameV = new GameV();
        gameV.setBoard(boardV);
        ViewModelGate.setModel(gameV);
    }


    @Override
    public void start(Stage stage){
        stage.setTitle("CanvasTesting");
        stage.setResizable(true);

        //HOW TO USE MapCanvas Class:
        //MapCanvas mapCanvas = new MapCanvas();
        //StackPane stackPane = mapCanvas.createMap();
        //mapCanvas.startMap(stackPane, (Canvas)stackPane.getChildren().get(0);
        //...than put the stackPane wherever you want! it will automatically display the content of ViewModelGate.getModel().GetBoard() .
        StackPane root = this.createMap();
        this.startMap();

        Scene scene = new Scene(root, 600, 450);

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args){
        launch();
    }



    //##################################   REAL CLASS

    private StackPane root;

    private Canvas canvas;

    private Rectangle2D[][] squares = new Rectangle2D[4][3];


    public StackPane createMap(){
        root = new StackPane();

        canvas = new Canvas(0,0);

        root.getChildren().add(canvas);

        root.setStyle("-fx-background-color: #ffb523");

        root.widthProperty().addListener((obs, oldValue, newValue) -> {
            this.fixCanvasDimension();
        });
        root.heightProperty().addListener((obs, oldValue, newValue) -> {
            this.fixCanvasDimension();
        });

        return root;
    }

    public void fixCanvasDimension(){
        //resize canvas
        if(((Double)root.getHeight()).intValue()>(((Double)root.getWidth()).intValue()*(3/(double)4))) {
            canvas.setWidth(root.getWidth());
            canvas.setHeight(canvas.getWidth()*(3/(double)4));
        }
        else{
            canvas.setHeight(root.getHeight());
            canvas.setWidth(canvas.getHeight()*(4/(double)3));
        }
        //update squares positions
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[0].length; j++) {
                System.out.println(((double)i*canvas.getWidth()/(double)4) +", " +  (double)i*canvas.getHeight()/(double)3 + ", " + canvas.getWidth()/4.0 + ", " + canvas.getHeight()/3.0);
                squares[i][j]= new Rectangle2D(((double)i*canvas.getWidth()/(double)4), (double)i*canvas.getHeight()/(double)3, canvas.getWidth()/4.0, canvas.getHeight()/3.0);
            }
        }

    }


    public void startMap(){
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //draw on position of mouse clicked
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                //resize the canvas

                //fill the canvas of black
                gc.setFill(Color.ORANGERED);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

                //Image squareImage = new Image(getClass().getClassLoader().getResource("Map0Resized.png").toExternalForm());
                //gc.drawImage(squareImage, 0, 0, canvas.getWidth(), canvas.getHeight());

                //check if all the elements it needs in the ViewModelGate exist
                if(ViewModelGate.getModel()!=null && ViewModelGate.getModel().getBoard()!=null && ViewModelGate.getModel().getBoard().getMap()!=null && ViewModelGate.getModel().getBoard().getChosenMap()!=null){
                    //represent the ViewModelGate.getModel().getBoard()

                    for (int i = 0; i < squares.length; i++) {
                        for (int j = 0; j < squares[0].length; j++) {
                            gc.setFill(Color.TRANSPARENT);
                            gc.setStroke(Color.BLACK);
                            gc.strokeRect(squares[i][j].getMinX(), squares[i][j].getMinY(), squares[i][j].getWidth(), squares[i][j].getHeight());
                        }
                    }
                }
                else{
                    //something is null in the ViewModelGate...
                    //TODO
                }

            }
        }.start();
    }
}