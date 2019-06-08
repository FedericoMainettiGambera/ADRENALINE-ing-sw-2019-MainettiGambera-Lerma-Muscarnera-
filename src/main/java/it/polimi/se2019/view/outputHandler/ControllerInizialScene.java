package it.polimi.se2019.view.outputHandler;

import com.sun.deploy.cache.InMemoryLocalApplicationProperties;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.events.Event;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.model.events.stateEvent.StateEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventGameSetUp;
import it.polimi.se2019.networkHandler.RMI.RMINetworkHandler;
import it.polimi.se2019.networkHandler.Socket.SocketNetworkHandler;
import it.polimi.se2019.view.components.OrderedCardListV;
import it.polimi.se2019.view.components.PlayersListV;
import it.polimi.se2019.view.components.View;
import it.polimi.se2019.view.components.ViewModelGate;
import it.polimi.se2019.virtualView.RMI.RMIVirtualView;
import it.polimi.se2019.virtualView.Socket.SocketVirtualView;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.util.Duration;


import javax.swing.event.MenuListener;
import java.awt.event.TextEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ControllerInizialScene implements Initializable, OutputHandlerInterface{

Parent root;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("View is now loaded!");
    }

    public void setNewGame(MouseEvent mouseEvent) throws Exception {

        System.out.println("CIAO");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("GameSetUp.fxml"));
        Scene scene = new Scene(root, 390, 360);


        Stage primaryStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);


    }

    public void setSettings(MouseEvent mouseEvent) throws IOException {


        System.out.println("CIAO");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("BaseGUI.fxml"));
        Scene scene = new Scene(root, 710, 500);


        Stage primaryStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        primaryStage.setScene(scene);

    }

    //GAMESETUPSTAGE*********************************************************************************
    @FXML
    ImageView imageId;

    @FXML
    public void showMap(MouseEvent event) throws IOException {
        String map;

        if (event.getSource().toString().contains("map0")) {
            map = "src/main/resources/Map0.png";
        } else if ((event.getSource().toString().contains("map2"))) {
            map = "src/main/resources/map2.png";
        } else if ((event.getSource().toString().contains("map1"))) {
            map = "src/main/resources/Map1.png";
        } else {
            map = "src/main/resources/map3.png";
        }

        Image image = new Image(new FileInputStream(map));
        imageId.setImage(image);

    }

    @FXML
    String mapChoice;
    String numberOfStartingSkulls;
    String FinalFrenzy;
    @FXML
    ToggleGroup map;
    @FXML
    ToggleGroup startingSkulls;
    @FXML
    ToggleGroup finalFrenzy;

    boolean isFinalFrenzy;

    @FXML
    public void GameSetUp() {

        mapChoice = ((RadioButton) (map.getSelectedToggle())).getText();
        numberOfStartingSkulls = ((RadioButton) (startingSkulls.getSelectedToggle())).getText();
        FinalFrenzy = ((RadioButton) (finalFrenzy.getSelectedToggle())).getText();


        if (FinalFrenzy.equals("Yes")) {
            isFinalFrenzy = true;
        } else {
            isFinalFrenzy = false;
        }


        String gameMode = "normalMode";
        boolean isBotActive = false;


        ViewControllerEventGameSetUp VCEGameSetUp = new ViewControllerEventGameSetUp(gameMode, mapChoice, Integer.parseInt(numberOfStartingSkulls), isFinalFrenzy, isBotActive);
        sendToServer(VCEGameSetUp);

    }

//*****************************************************************************************************

    private String networkConnection;

    public void sendToServer(Object o) {
        if (networkConnection.equals("SOCKET")) {
            try {
                SocketNetworkHandler.oos.writeObject(o);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                RMINetworkHandler.client.returnInterface().sendToServer(o);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void GUISelector(String networkConnection) {
        this.networkConnection = networkConnection;
    }


    //CONNECTION SET UP&&VIEW SET UP***************************************************************************

    public static SocketVirtualView SVV;
    public static RMIVirtualView RMIVV;

    public static SocketNetworkHandler SNH;
    public static RMINetworkHandler RMINH;

    public static ViewControllerEventHandlerContext VCEHC;

    public static View V;


    @FXML
    ImageView User1;
    @FXML
    ImageView User2;
    @FXML
    ImageView User3;
    @FXML
    ImageView User4;
    @FXML
    ImageView User5;

    boolean GUI = false;
    boolean CLI = false;
    boolean RMI = false;
    boolean Socket = false;

    String IP = "";
    int port = 0;


    // public void getIP(InputMethodEvent event){

    //   IP= event.getComposed().toString();


    //}
    // public void getPort(InputMethodEvent event){

    //     port=(Integer.parseInt(event.getComposed().toString()));
    // }


    public void setGUI() {
        GUI = true;
        CLI = false;
    }

    public void setCLI() {
        CLI = true;
        GUI = false;
    }

    public void setRMI() {
        RMI = true;
        Socket = false;
    }

    public void setSocket() {
        Socket = true;
        RMI = false;
    }


    public void setConnection() {

        if (RMI) {
            //creating the View for the user who holds the server
            if (GUI) {
                this.V = new View("RMI", "GUI");
            } else {
                this.V = new View("RMI", "CLI");
            }

            try {
                this.RMINH = new RMINetworkHandler(IP, port, V);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }


        } else {
            //creating the View for the user who holds the server
            if (GUI) {
                this.V = new View("SOCKET", "GUI");
            } else {
                this.V = new View("SOCKET", "CLI");
            }


            //creating the Client for the user who holds the server and connecting it to the server
            try {
                this.SNH = new SocketNetworkHandler(InetAddress.getByName(IP), port, this.V);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    TextField ip;
    @FXML
    TextField porta;



    public void setLoading(MouseEvent event) throws IOException {



        if (((GUI == false && CLI == false)) || ((RMI == false && Socket == false))) {

            Stage popup = new Stage();
            Pane pane = new Pane();
            Label text = new Label();
            text.setText("You need to do choices in life, Susan!");
            pane.getChildren().add(text);
            text.setFont(Font.font("Courier"));
            Scene scene = new Scene(pane, 300, 30);
            popup.setScene(scene);
            popup.setResizable(false);
            popup.showAndWait();
        } else {


            if (CLI == true) {
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Pane pane = new Pane();
                Label text = new Label();
                text.setText("Seeya on the terminal!");
                pane.getChildren().add(text);
                text.setFont(Font.font("Courier"));
                Scene scene = new Scene(pane, 300, 30);
                primaryStage.setScene(scene);
                primaryStage.show();
                PauseTransition delay = new PauseTransition(Duration.seconds(3));
                delay.setOnFinished(event1 -> primaryStage.close());

            }


            //LoadingScene is set
            else{
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("LOADING SCENE.fxml"));
                Scene scene = new Scene(root, 710, 500);
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.setScene(scene);
            }
            IP = ip.getText();
            port = Integer.parseInt(porta.getText());
            setConnection();
        }


        //******END***********************************************************************************************
    }

//****CONNECTION WAITING*****************************************************************************************+


@FXML
public void waitForPlayers(int number) throws FileNotFoundException{


    switch(number){
        case 1: Image image = new Image(new FileInputStream("src/main/resources/giphy.gif"));
        User1.setImage(image);
         break;
        case 2: Image image2 = new Image(new FileInputStream("src/main/resources/giphy-2.gif"));
            User2.setImage(image2);
            break;
    case 3: Image image3 = new Image(new FileInputStream("src/main/resources/giphy-5.gif"));
       User3.setImage(image3);
         break;
    case 4: Image image4 = new Image(new FileInputStream("src/main/resources/giphy-7.gif"));
       User4.setImage(image4);
        break;
    case 5: Image image5 = new Image(new FileInputStream("src/main/resources/giphy-8.gif"));
        User5.setImage(image5);
        break;

    }


}

    @Override
    public void gameCreated() {

    }

    @Override
    public void stateChanged(StateEvent StE) {

    }

    @Override
    public void setFinalFrenzy(ModelViewEvent MVE) {

    }

    @Override
    public void finalFrenzyBegun(ModelViewEvent MVE) {

    }

    @Override
    public void newKillshotTrack(ModelViewEvent MVE) {

    }

    @FXML
    @Override
    public void newPlayersList(ModelViewEvent MVE) {

        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("LOADING SCENE.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int number=((PlayersListV)MVE.getComponent()).getPlayers().size();

        try {
            switch(number){
                case 1:
                    Image image = new Image(new FileInputStream("src/main/resources/giphy.gif"));
                    User1.setImage(image);
                    break;
                case 2: Image image2 = new Image(new FileInputStream("src/main/resources/giphy-2.gif"));
                    User2.setImage(image2);
                    break;
                case 3: Image image3 = new Image(new FileInputStream("src/main/resources/giphy-5.gif"));
                    User3.setImage(image3);
                    break;
                case 4: Image image4 = new Image(new FileInputStream("src/main/resources/giphy-7.gif"));
                    User4.setImage(image4);
                    break;
                case 5: Image image5 = new Image(new FileInputStream("src/main/resources/giphy-8.gif"));
                    User5.setImage(image5);
                    break;

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void newBoard(ModelViewEvent MVE) {

    }

    @Override
    public void deathOfPlayer(ModelViewEvent MVE) {

    }

    @Override
    public void movingCardsAround(OrderedCardListV from, OrderedCardListV to, ModelViewEvent MVE) {

    }

    @Override
    public void shufflingCards(ModelViewEvent MVE) {

    }

    @Override
    public void newColor(ModelViewEvent MVE) {

    }

    @Override
    public void newNickname(ModelViewEvent MVE) {

    }

    @Override
    public void newPosition(ModelViewEvent MVE) {

    }

    @Override
    public void newScore(ModelViewEvent MVE) {

    }

    @Override
    public void addDeathCounter(ModelViewEvent MVE) {

    }

    @Override
    public void setFinalFrenzyBoard(ModelViewEvent MVE) {

    }

    @Override
    public void newAmmoBox(ModelViewEvent MVE) {

    }

    @Override
    public void newDamageTracker(ModelViewEvent MVE) {

    }

    @Override
    public void newMarksTracker(ModelViewEvent MVE) {

    }

    @Override
    public void setCurrentPlayingPlayer(ModelViewEvent MVE) {

    }

    @Override
    public void setStartingPlayer(ModelViewEvent MVE) {

    }

    @Override
    public void newPlayer(ModelViewEvent MVE) {

    }

    @Override
    public void setAFK(ModelViewEvent MVE) {

    }

    @Override
    public void showInputTimer(int currentTime, int totalTime) {

    }

    @Override
    public void showConnectionTimer(int currentTime, int totalTime) {

    }

//***********************************END*****************************************************************************
}







