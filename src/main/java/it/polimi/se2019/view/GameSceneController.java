package it.polimi.se2019.view;

import it.polimi.se2019.model.events.Event;
import it.polimi.se2019.view.selector.ViewSelector;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    @FXML private StackPane ammoBackgroundRed1;
    @FXML private StackPane ammoMainImageRed1;
    @FXML private StackPane ammoBackgroundRed2;
    @FXML private StackPane ammoMainImageRed2;
    @FXML private StackPane ammoBackgroundRed3;
    @FXML private StackPane ammoMainImageRed3;
    @FXML private StackPane ammoBackgroundYellow1;
    @FXML private StackPane ammoMainImageYellow1;
    @FXML private StackPane ammoBackgroundYellow2;
    @FXML private StackPane ammoMainImageYellow2;
    @FXML private StackPane ammoBackgroundYellow3;
    @FXML private StackPane ammoMainImageYellow3;
    @FXML private StackPane ammoBackgroundBlue1;
    @FXML private StackPane ammoMainImageBlue1;
    @FXML private StackPane ammoBackgroundBlue2;
    @FXML private StackPane ammoMainImageBlue2;
    @FXML private StackPane ammoBackgroundBlue3;
    @FXML private StackPane ammoMainImageBlue3;

    //3.1.3-player weapon cards
    @FXML
    private VBox weaponCardsVBox;
    //3.1.3.1-weapon cards title
    @FXML private StackPane weaponCardsTitle;
    //3.1.3.2-weapon cards images
    @FXML private HBox weaponCardsBackground;
    @FXML private StackPane weaponCardBackground1;
    @FXML private StackPane weaponCardMainImage1;
    @FXML private StackPane weaponCardBackground2;
    @FXML private StackPane weaponCardMainImage2;
    @FXML private StackPane weaponCardBackground3;
    @FXML private StackPane weaponCardMainImage3;


    //------------------------------4
    //4-board section
    @FXML
    private AnchorPane boardSection;
    //canvas... TODO.



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initialize everything:
        //      1) all css classes to the corresponding element (we'll manipulates images with css classes)
        //      2) initialize the canvas
        //      3) add everything that is already setted in the ViewModel (for example the PlayerList, the current State, Timers, etc...)

        //killshot track default css classes
        this.killshotTrackSection.getStyleClass().add("emptyKillShotTrackBackground");
        this.killshotTrackVBox.getStyleClass().add("killShotTrackBackground");
        this.killBackground1.getStyleClass().add("killSlotBackground");
        this.killMainImage1.getStyleClass().add("emptyKillSlot");
        this.killBackground2.getStyleClass().add("killSlotBackground");
        this.killMainImage2.getStyleClass().add("emptyKillSlot");
        this.killBackground3.getStyleClass().add("killSlotBackground");
        this.killMainImage3.getStyleClass().add("emptyKillSlot");
        this.killBackground4.getStyleClass().add("killSlotBackground");
        this.killMainImage4.getStyleClass().add("emptyKillSlot");
        this.killBackground5.getStyleClass().add("killSlotBackground");
        this.killMainImage5.getStyleClass().add("emptyKillSlot");
        this.killBackground6.getStyleClass().add("killSlotBackground");
        this.killMainImage6.getStyleClass().add("emptyKillSlot");
        this.killBackground7.getStyleClass().add("killSlotBackground");
        this.killMainImage7.getStyleClass().add("emptyKillSlot");
        this.killBackground8.getStyleClass().add("killSlotBackground");
        this.killMainImage8.getStyleClass().add("emptyKillSlotFF");

        //player default css classes
        this.playerSection.getStyleClass().add("emptyPlayerSectionBackground");
        this.playerHBox.getStyleClass().add("playerSectionBackground");
        //power up cards
        this.powerUpCardsVBox.getStyleClass().add("powerUpCardsBackground");
        this.powerUpCardsTitle.getStyleClass().add("powerUpCardsTitleBackground");
        this.powerUpCardsBackground.getStyleClass().add("powerUpCardsBackground");
        this.powerUpCardBackground1.getStyleClass().add("powerUpCardBackground");
        this.powerUpCardBackground2.getStyleClass().add("powerUpCardBackground");
        this.powerUpCardMainImage1.getStyleClass().add("emptyPowerUpCardMainImage");
        this.powerUpCardMainImage2.getStyleClass().add("emptyPowerUpCardMainImage");
        //statistics
        this.playerStats.getStyleClass().add("playerStatisticsBackground");
        //marks
        this.playerMarks.getStyleClass().add("marksBackground");
        this.backgroundMark1.getStyleClass().add("markBackground");
        this.backgroundMark2.getStyleClass().add("markBackground");
        this.backgroundMark3.getStyleClass().add("markBackground");
        this.backgroundMark4.getStyleClass().add("markBackground");
        this.backgroundMark5.getStyleClass().add("markBackground");
        this.mainImageMark1.getStyleClass().add("markEmpty");
        this.mainImageMark2.getStyleClass().add("markEmpty");
        this.mainImageMark3.getStyleClass().add("markEmpty");
        this.mainImageMark4.getStyleClass().add("markEmpty");
        this.mainImageMark5.getStyleClass().add("markEmpty");
        //damage and deaths
        this.playerDamagesAndDeathsVBox.getStyleClass().add("playerDamageAndDeathsBackground");
        //damage
        this.playerDamagesHbox.getStyleClass().add("damagesBackground");
        this.damageBackground1.getStyleClass().add("damageBackgroundFB");
        this.damageBackground2.getStyleClass().add("damageBackground");
        this.damageBackground3.getStyleClass().add("damageBackgroundAG");
        this.damageBackground4.getStyleClass().add("damageBackground");
        this.damageBackground5.getStyleClass().add("damageBackground");
        this.damageBackground6.getStyleClass().add("damageBackgroundAS");
        this.damageBackground7.getStyleClass().add("damageBackground");
        this.damageBackground8.getStyleClass().add("damageBackground");
        this.damageBackground9.getStyleClass().add("damageBackground");
        this.damageBackground10.getStyleClass().add("damageBackground");
        this.damageBackground11.getStyleClass().add("damageBackgroundK");
        this.damageBackground12.getStyleClass().add("damageBackgroundOK");
        this.damageBackground13.getStyleClass().add("damageBackgroundEXTRA");
        this.damageMainImage1.getStyleClass().add("damageEmpty");
        this.damageMainImage2.getStyleClass().add("damageEmpty");
        this.damageMainImage3.getStyleClass().add("damageEmpty");
        this.damageMainImage4.getStyleClass().add("damageEmpty");
        this.damageMainImage5.getStyleClass().add("damageEmpty");
        this.damageMainImage6.getStyleClass().add("damageEmpty");
        this.damageMainImage7.getStyleClass().add("damageEmpty");
        this.damageMainImage8.getStyleClass().add("damageEmpty");
        this.damageMainImage9.getStyleClass().add("damageEmpty");
        this.damageMainImage10.getStyleClass().add("damageEmpty");
        this.damageMainImage11.getStyleClass().add("damageEmpty");
        this.damageMainImage12.getStyleClass().add("damageEmpty");
        this.damageMainImage13.getStyleClass().add("damageEmpty");
        //death
        this.playerDeathsHbox.getStyleClass().add("deathsBackground");
        this.deathBackground1.getStyleClass().add("deathBackground");
        this.deathBackground2.getStyleClass().add("deathBackground");
        this.deathBackground3.getStyleClass().add("deathBackground");
        this.deathBackground4.getStyleClass().add("deathBackground");
        this.deathBackground5.getStyleClass().add("deathBackground");
        this.deathBackground6.getStyleClass().add("deathBackground");
        this.deathBackground7.getStyleClass().add("deathBackground");
        this.deathMainImage1.getStyleClass().add("deathSkull");
        this.deathMainImage2.getStyleClass().add("deathSkull");
        this.deathMainImage3.getStyleClass().add("deathSkull");
        this.deathMainImage4.getStyleClass().add("deathSkull");
        this.deathMainImage5.getStyleClass().add("deathSkull");
        this.deathMainImage6.getStyleClass().add("deathSkull");
        this.deathMainImage7.getStyleClass().add("deathSkull");
        //nickname
        this.playerNicknameBackground.getStyleClass().add("nicknameBackground");
        //ammo box
        this.playerAmmoBox.getStyleClass().add("ammoBoxBackground");
        this.ammoBackgroundBlue1.getStyleClass().add("ammoBackground");
        this.ammoBackgroundBlue2.getStyleClass().add("ammoBackground");
        this.ammoBackgroundBlue3.getStyleClass().add("ammoBackground");
        this.ammoBackgroundRed1.getStyleClass().add("ammoBackground");
        this.ammoBackgroundRed2.getStyleClass().add("ammoBackground");
        this.ammoBackgroundRed3.getStyleClass().add("ammoBackground");
        this.ammoBackgroundYellow1.getStyleClass().add("ammoBackground");
        this.ammoBackgroundYellow2.getStyleClass().add("ammoBackground");
        this.ammoBackgroundYellow3.getStyleClass().add("ammoBackground");
        this.ammoMainImageBlue1.getStyleClass().add("emptyAmmo");
        this.ammoMainImageBlue2.getStyleClass().add("emptyAmmo");
        this.ammoMainImageBlue3.getStyleClass().add("emptyAmmo");
        this.ammoMainImageRed1.getStyleClass().add("emptyAmmo");
        this.ammoMainImageRed2.getStyleClass().add("emptyAmmo");
        this.ammoMainImageRed3.getStyleClass().add("emptyAmmo");
        this.ammoMainImageYellow1.getStyleClass().add("emptyAmmo");
        this.ammoMainImageYellow2.getStyleClass().add("emptyAmmo");
        this.ammoMainImageYellow3.getStyleClass().add("emptyAmmo");
        //weapon cards
        this.weaponCardsVBox.getStyleClass().add("weaponCardsBackground");
        this.weaponCardsTitle.getStyleClass().add("weaponCardsTitleBackground");
        this.weaponCardsBackground.getStyleClass().add("weaponCardsBackground");
        this.weaponCardBackground1.getStyleClass().add("weaponCardBackground");
        this.weaponCardBackground2.getStyleClass().add("weaponCardBackground");
        this.weaponCardBackground3.getStyleClass().add("weaponCardBackground");
        this.weaponCardMainImage1.getStyleClass().add("emptyWeaponCardMainImage");
        this.weaponCardMainImage2.getStyleClass().add("emptyWeaponCardMainImage");
        this.weaponCardMainImage3.getStyleClass().add("emptyWeaponCardMainImage");
    }

    /**sends an Event to the server in a new Thread, using the SendToServerThread class*/
    public void sendToServer(Event event){
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

    public void changeSelectorSection(Node newSection, Double top, Double right, Double bottom, Double left){
        //delete old section, if any exist
        this.selectorSection.getChildren().clear();

        //append newSection
        this.selectorSection.getChildren().add(newSection);

        if(top!=null) {
            AnchorPane.setTopAnchor(newSection, top);
        }
        if(right!=null) {
            AnchorPane.setRightAnchor(newSection, right);
        }
        if(bottom!=null) {
            AnchorPane.setBottomAnchor(newSection, bottom);
        }
        if(left!=null) {
            AnchorPane.setLeftAnchor(newSection, left);
        }
    }

    public void removeSelectorSection(){
        this.selectorSection.getChildren().clear();

        Label label = new Label("Selector Section");
        StackPane stackPane = new StackPane(label);

        this.selectorSection.getChildren().add(stackPane);
        AnchorPane.setTopAnchor(stackPane, 0.0);
        AnchorPane.setRightAnchor(stackPane, 0.0);
        AnchorPane.setBottomAnchor(stackPane, 0.0);
        AnchorPane.setLeftAnchor(stackPane, 0.0);

    }

    public AnchorPane getSelectorSection(){
        return this.selectorSection;
    }

    /**@return the playerSection*/
    public AnchorPane getPlayerSection(){
        return  this.playerSection;
    }

}
