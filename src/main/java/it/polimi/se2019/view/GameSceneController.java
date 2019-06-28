package it.polimi.se2019.view;

import it.polimi.se2019.model.events.Event;
import it.polimi.se2019.view.outputHandler.CLIOutputHandler;
import it.polimi.se2019.view.selector.ViewSelector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class GameSceneController implements Initializable {

    //root
    @FXML
    private HBox root;



    //------------------------------------------------------------------
    //interactive section
    @FXML
    private VBox interactiveSection;


    //------------------------------1
    //1-selector section
    @FXML
    private AnchorPane selectorSection;
    @FXML private Label selectorLabel;
    //create at run time... TODO.

    //------------------------------2
    //2-information section
    @FXML
    private AnchorPane informationSection;
    //create at run time... TODO.


    //------------------------------------------------------------------
    //game section
    @FXML
    private AnchorPane gameSection;


    //------------------------------1
    //1-state section
    @FXML
    private AnchorPane stateSection;
    @FXML private Label stateLabel;
    //... TODO.


    //------------------------------2
    //2-killshot track section
    @FXML
    private AnchorPane killshotTrackSection;
    //2.1-killshot track VBox
    @FXML
    private VBox killshotTrackVBox;
    //2.1.n-killshot n-th kill background & main image
    //n=1
    @FXML private StackPane killBackground1;
    @FXML private StackPane killMainImage1;
    //n=2
    @FXML private StackPane killBackground2;
    @FXML private StackPane killMainImage2;
    //n=3
    @FXML private StackPane killBackground3;
    @FXML private StackPane killMainImage3;
    //n=4
    @FXML private StackPane killBackground4;
    @FXML private StackPane killMainImage4;
    //n=5
    @FXML private StackPane killBackground5;
    @FXML private StackPane killMainImage5;
    //n=6
    @FXML private StackPane killBackground6;
    @FXML private StackPane killMainImage6;
    //n=7
    @FXML private StackPane killBackground7;
    @FXML private StackPane killMainImage7;
    //n=8
    @FXML private StackPane killBackground8;
    @FXML private StackPane killMainImage8;



    //------------------------------3
    //3-player section
    @FXML
    private AnchorPane playerSection;

    //3.1-player HBox
    @FXML
    private HBox playerHBox;

    //3.1.1-players power up cards
    @FXML
    private VBox powerUpCardsVBox;
    //3.1.1.1-power up cards title
    @FXML private StackPane powerUpCardsTitle;
    //3.1.1.2-power up cards images
    @FXML private HBox powerUpCardsBackground;
    @FXML private StackPane powerUpCardBackground1;
    @FXML private StackPane powerUpCardMainImage1;
    @FXML private StackPane powerUpCardBackground2;
    @FXML private StackPane powerUpCardMainImage2;

    //3.1.2-player main statistics
    @FXML
    private GridPane playerStats;

    //3.1.2[0,0]- players marks
    @FXML private HBox playerMarks;
    @FXML private StackPane backgroundMark1;
    @FXML private StackPane mainImageMark1;
    @FXML private StackPane backgroundMark2;
    @FXML private StackPane mainImageMark2;
    @FXML private StackPane backgroundMark3;
    @FXML private StackPane mainImageMark3;
    @FXML private StackPane backgroundMark4;
    @FXML private StackPane mainImageMark4;
    @FXML private StackPane backgroundMark5;
    @FXML private StackPane mainImageMark5;

    //3.1.2[0,1]- player damages & deaths
    @FXML private VBox playerDamagesAndDeathsVBox;

    //3.1.2[0,1].1 - player damages
    @FXML private HBox playerDamagesHbox;
    @FXML private StackPane damageBackground1;
    @FXML private StackPane damageMainImage1;
    @FXML private StackPane damageBackground2;
    @FXML private StackPane damageMainImage2;
    @FXML private StackPane damageBackground3;
    @FXML private StackPane damageMainImage3;
    @FXML private StackPane damageBackground4;
    @FXML private StackPane damageMainImage4;
    @FXML private StackPane damageBackground5;
    @FXML private StackPane damageMainImage5;
    @FXML private StackPane damageBackground6;
    @FXML private StackPane damageMainImage6;
    @FXML private StackPane damageBackground7;
    @FXML private StackPane damageMainImage7;
    @FXML private StackPane damageBackground8;
    @FXML private StackPane damageMainImage8;
    @FXML private StackPane damageBackground9;
    @FXML private StackPane damageMainImage9;
    @FXML private StackPane damageBackground10;
    @FXML private StackPane damageMainImage10;
    @FXML private StackPane damageBackground11;
    @FXML private StackPane damageMainImage11;
    @FXML private StackPane damageBackground12;
    @FXML private StackPane damageMainImage12;
    @FXML private StackPane damageBackground13;
    @FXML private StackPane damageMainImage13;

    //3.1.2[0,1].2 - player deaths
    @FXML private HBox playerDeathsHbox;
    @FXML private StackPane deathBackground1;
    @FXML private StackPane deathMainImage1;
    @FXML private StackPane deathBackground2;
    @FXML private StackPane deathMainImage2;
    @FXML private StackPane deathBackground3;
    @FXML private StackPane deathMainImage3;
    @FXML private StackPane deathBackground4;
    @FXML private StackPane deathMainImage4;
    @FXML private StackPane deathBackground5;
    @FXML private StackPane deathMainImage5;
    @FXML private StackPane deathBackground6;
    @FXML private StackPane deathMainImage6;
    @FXML private StackPane deathBackground7;
    @FXML private StackPane deathMainImage7;

    //3.1.2[1,0]- player nickname
    @FXML private StackPane playerNicknameBackground;
    @FXML private Label playerNickname;

    //3.1.2[1,1]- player ammo box
    @FXML private GridPane playerAmmoBox;
    @FXML private StackPane backgroundRed1;
    @FXML private StackPane mainImageRed1;
    @FXML private StackPane backgroundRed2;
    @FXML private StackPane mainImageRed2;
    @FXML private StackPane backgroundRed3;
    @FXML private StackPane mainImageRed3;
    @FXML private StackPane backgroundYellow1;
    @FXML private StackPane mainImageYellow1;
    @FXML private StackPane backgroundYellow2;
    @FXML private StackPane mainImageYellow2;
    @FXML private StackPane backgroundYellow3;
    @FXML private StackPane mainImageYellow3;
    @FXML private StackPane backgroundBlue1;
    @FXML private StackPane mainImageBlue1;
    @FXML private StackPane backgroundBlue2;
    @FXML private StackPane mainImageBlue2;
    @FXML private StackPane backgroundBlue3;
    @FXML private StackPane mainImageBlue3;

    //3.1.3-player weapon cards
    @FXML
    private VBox weaponCardsVBox;
    //3.1.3.1-weapon cards title
    @FXML private StackPane weaponCardsTitle;
    //3.1.3.2-weapon cards images
    @FXML private HBox weaponCardsBackground;
    @FXML private StackPane weaponCardBackground1;
    @FXML private StackPane weaponCardMainImage1;
    @FXML private StackPane weaponBackground2;
    @FXML private StackPane weaponCardMainImage2;
    @FXML private StackPane weaponBackground3;
    @FXML private StackPane weaponCardMainImage3;


    //------------------------------4
    //4-board section
    @FXML
    private AnchorPane boardSection;
    //canvas... TODO.



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initialize everything
        //      1) all css classes to the corresponding element (we'll manipulates images with css classes)
        //      2) initialize the canvas
        //      3) add everything that is already setted in the ViewModel (for example the PlayerList, the current State, Timers, etc...)
        //CLIOutputHandler.showGeneralStatusOfTheGame();
    }

    /**sends an Event to the server in a new Thread, using the SendToServerThread class*/
    private void sendToServer(Event event){
        (new SendToServerThread(event)).start();
    }
    /**Thread to send an Event to the server*/
    private class SendToServerThread extends Thread{
        private Event event;
        private SendToServerThread(Event event){
            this.event = event ;
        }
        @Override
        public void run(){
            ViewSelector.sendToServer(this.event);
        }
    }


}
