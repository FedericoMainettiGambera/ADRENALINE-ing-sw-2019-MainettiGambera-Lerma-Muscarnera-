package it.polimi.se2019.view;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.model.enumerations.PlayersColors;
import it.polimi.se2019.model.events.Event;
import it.polimi.se2019.view.components.*;
import it.polimi.se2019.view.selector.ViewSelector;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class GameSceneController implements Initializable {

    //root
    @FXML
    private HBox root;



    //------------------------------------------------------------------
    //interactive section
    @FXML private VBox interactiveSection;


    //------------------------------1
    //1-selector section
    @FXML private AnchorPane selectorSection;
    @FXML private Label selectorLabel;


    //------------------------------2
    //2-information section
    @FXML private AnchorPane informationSection;



    public AnchorPane getInformationSection() {
        return informationSection;
    }

    //------------------------------------------------------------------
    //game section
    @FXML private AnchorPane gameSection;


    //------------------------------1
    //1-state section
    /***/
    @FXML private AnchorPane stateSection;
    /**a label that reports the chosen connection method*/
    @FXML private Label connection;
    /**@return connection*/
    public Label getConnection() {
        return connection;
    }

    /**a label that reports ip address info*/
    @FXML private Label ip;
    /**@return ip*/
    public Label getIp() {
        return ip;
    }

    /**a label that reports connection port info*/
    @FXML private Label port;

    /**@return port*/
    public Label getPort() {
        return port;
    }

    /**a label that indicates in which state the game is in real time*/
    @FXML private Label stateTitle;
    /**@return stateTitle*/
    public Label getStateTitle() {
        return stateTitle;
    }

    /**a textflow that describes what is happening in the game in real time*/
    @FXML private TextFlow stateDescription;

    /**@return stateDescription*/
    public TextFlow getStateDescription() {
        return stateDescription;
    }


    @FXML private ProgressIndicator progressIndicator;
    public ProgressIndicator getProgressIndicator(){
        return this.progressIndicator;
    }


    //------------------------------2
    //2-killshot track section
    @FXML private AnchorPane killshotTrackSection;
    //2.1-killshot track VBox
    @FXML private VBox killshotTrackVBox;

    /**@return a list of StackPanes */
    public List<StackPane> getKills(){

        List<StackPane> kills=new ArrayList<>();

        kills.add(killMainImage1);
        kills.add(killMainImage2);
        kills.add(killMainImage3);
        kills.add(killMainImage4);
        kills.add(killMainImage5);
        kills.add(killMainImage6);
        kills.add(killMainImage7);
        kills.add(killMainImage8);

        return kills;

    }

    //2.1.n-killshot n-th kill background & main image
    //n=1

    @FXML private StackPane killBackground1;
    /**this stackpane contains a image representing a skull, a kill or an overkill, there may be five to eight */
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
    @FXML private AnchorPane playerSection;

    //3.1-player HBox
    @FXML private HBox playerHBox;

    //3.1.1-players power up cards
    @FXML private VBox powerUpCardsVBox;
    //3.1.1.1-power up cards title
    @FXML private StackPane powerUpCardsTitle;
    //3.1.1.2-power up cards images
    @FXML private HBox powerUpCardsBackground;
   /**StackPanes that contain a image representing one of the various Power Up Cards and their backgrounds*/
    @FXML private StackPane powerUpCardBackground1;
    @FXML private StackPane powerUpCardMainImage1;
    @FXML private StackPane powerUpCardBackground2;
    @FXML private StackPane powerUpCardMainImage2;

    /**@return a list of Stack Panes containingPowerUpCardMainImages*/
    public List<StackPane> getListOfPowerUpCardsMainImage(){

        List<StackPane> powerups= new ArrayList<>();

        powerups.add(powerUpCardMainImage1);
        powerups.add(powerUpCardMainImage2);

        return powerups;
    }
    public List<StackPane> getListOfPowerUpCardsBackground(){
        return new ArrayList<>(Arrays.asList(powerUpCardBackground1, powerUpCardBackground2));
    }

    //3.1.2-player main statistics
    @FXML private GridPane playerStats;

    //3.1.2[0,0]- players marks
    /**each one of this stack pane contains a markImage, for each slot, 4 in total, there is a mark main image stack pane and
     * a mark background image, both of them are update at runtime, according to the development of the game*/
    @FXML private HBox playerMarks;
    @FXML private StackPane backgroundMark1;
    @FXML private StackPane mainImageMark1;
    @FXML private StackPane backgroundMark2;
    @FXML private StackPane mainImageMark2;
    @FXML private StackPane backgroundMark3;
    @FXML private StackPane mainImageMark3;
    @FXML private StackPane backgroundMark4;
    @FXML private StackPane mainImageMark4;

    /**@return marksMainImage*/
    public List<StackPane> getMarkMainImage(){

        List<StackPane> marksMainImage=new ArrayList<>();

        marksMainImage.add(mainImageMark1);
        marksMainImage.add(mainImageMark2);
        marksMainImage.add(mainImageMark3);
        marksMainImage.add(mainImageMark4);

        return  marksMainImage;


    }

    //3.1.2[0,1]- player damages & deaths
    @FXML private VBox playerDamagesAndDeathsVBox;

    public VBox getPlayerDamagesAndDeathsVBox(){
        return playerDamagesAndDeathsVBox;}

    //3.1.2[0,1].1 - player damages
    @FXML private HBox playerDamagesHbox;
    public HBox getPlayerHBox(){
        return  playerDamagesHbox;
    }

    /**each one of this stack pane contain a damageImage, for each slot, 13 in total, there is a damage main image stack pane and
     * a damage background image, both of them are update at runtime, according to the development of the game*/
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

    /**@return damages, a list of the stack panes that contain the damage image*/
    public List<StackPane> getDamagesMainImage(){
        List<StackPane> damages=new ArrayList<>();
        damages.add(damageMainImage1);
        damages.add(damageMainImage2);
        damages.add(damageMainImage3);
        damages.add(damageMainImage4);
        damages.add(damageMainImage5);
        damages.add(damageMainImage6);
        damages.add(damageMainImage7);
        damages.add(damageMainImage8);
        damages.add(damageMainImage9);
        damages.add(damageMainImage10);
        damages.add(damageMainImage11);
        damages.add(damageMainImage12);
        damages.add(damageMainImage13);

        return damages;
    }

    public List<StackPane> getDamagesBackGroundImages(){
        List<StackPane> damages=new ArrayList<>();
        damages.add(damageBackground1);
        damages.add(damageBackground2);
        damages.add(damageBackground3);
        damages.add(damageBackground4);
        damages.add(damageBackground5);
        damages.add(damageBackground6);
        damages.add(damageBackground7);
        damages.add(damageBackground8);
        damages.add(damageBackground9);
        damages.add(damageBackground10);
        damages.add(damageBackground11);
        damages.add(damageBackground12);
        damages.add(damageBackground13);
        return damages;

    }
    //3.1.2[0,1].2 - player deaths
    @FXML private HBox playerDeathsHbox;
    /**each one of this stack pane contain a deathImage, for each slot, 7 in total, there is a death main image stack pane and
     * a death background image, both of them are update at runtime, according to the development of the game*/
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

    /**@return deathList, a list of all of the deathImages*/
    public List<StackPane> getDeathMainImage(){
        List<StackPane> deathList=new ArrayList<>();

        deathList.add(deathMainImage1);
        deathList.add(deathMainImage2);
        deathList.add(deathMainImage3);
        deathList.add(deathMainImage4);
        deathList.add(deathMainImage5);
        deathList.add(deathMainImage6);
        deathList.add(deathMainImage7);

        return deathList;
    }

    //3.1.2[1,0]- player nickname
    /**this attribute contains a StackPane in which the label containing the player's Nickname will be framed*/
    @FXML private StackPane playerNicknameBackground;
    /** a label containing the player's nickname*/
    @FXML private Label playerNickname;

    public Label getNicknameLabel(){
        return playerNickname;
    }

    public StackPane getNicknameBackGround(){
        return playerNicknameBackground;
    }
    //3.1.2[1,1]- player ammo box
    /**a grid pane containing the ammunitions of the player*/
    @FXML private GridPane playerAmmoBox;
    /**each one of this stack pane contains a image representing a ammo, for each ammo there is a background image
     * and a main image*/
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

    /**@return ammos, a list of stack panes of the
     * @param color asked color*/
    public List<StackPane> getAmmosMainImage(AmmoCubesColor color){
        List<StackPane> ammos=new ArrayList<>();

            switch(color) {
                case blue:
                ammos.add(ammoMainImageBlue1);
                ammos.add(ammoMainImageBlue1);
                ammos.add(ammoMainImageBlue1);
                break;
                case red:
                ammos.add(ammoMainImageRed1);
                ammos.add(ammoMainImageRed2);
                ammos.add(ammoMainImageRed3);
                break;
                case yellow:
                ammos.add(ammoMainImageYellow1);
                ammos.add(ammoMainImageYellow2);
                ammos.add(ammoMainImageYellow3);
                break;
                default:
                    break;

            }
        return ammos;
    }

    //3.1.3-player weapon cards
    @FXML private VBox weaponCardsVBox;
    //3.1.3.1-weapon cards title
    @FXML private StackPane weaponCardsTitle;
    //3.1.3.2-weapon cards images
    @FXML private HBox weaponCardsBackground;
    /**each of those stack panes may represents one of the various weapon card and their background
     * they represent the one the player is holding*/
    @FXML private StackPane weaponCardBackground1;
    @FXML private StackPane weaponCardMainImage1;
    @FXML private StackPane weaponCardBackground2;
    @FXML private StackPane weaponCardMainImage2;
    @FXML private StackPane weaponCardBackground3;
    @FXML private StackPane weaponCardMainImage3;

    /**@return a list of stack panes containing a main image for a weapon card*/
    public List<StackPane> getWeaponCardsMainImage(){

        List<StackPane> weapons=new ArrayList<>();

        weapons.add(weaponCardMainImage1);
        weapons.add(weaponCardMainImage2);
        weapons.add(weaponCardMainImage3);

        return weapons;
    }
    public List<StackPane> getWeaponCardsBackground(){
        return new ArrayList<>(Arrays.asList(weaponCardBackground1, weaponCardBackground2, weaponCardBackground3));
    }


    //------------------------------4
    //4-board section
    @FXML private AnchorPane boardSection;
    @FXML private StackPane boardBackGround;
    @FXML private GridPane board; //  (4 x 3)

    @FXML private StackPane squareBackground00;
    @FXML private StackPane squareBackground01;
    @FXML private StackPane squareBackground02;
    @FXML private StackPane squareBackground03;
    @FXML private StackPane squareBackground10;
    @FXML private StackPane squareBackground11;
    @FXML private StackPane squareBackground12;
    @FXML private StackPane squareBackground13;
    @FXML private StackPane squareBackground20;
    @FXML private StackPane squareBackground21;
    @FXML private StackPane squareBackground22;
    @FXML private StackPane squareBackground23;

    public StackPane[][] getBackgroundsMap(){
        StackPane[][] backgroundMap = new StackPane[3][4];
        backgroundMap[0][0] = this.squareBackground00;
        backgroundMap[0][1] = this.squareBackground01;
        backgroundMap[0][2] = this.squareBackground02;
        backgroundMap[0][3] = this.squareBackground03;
        backgroundMap[1][0] = this.squareBackground10;
        backgroundMap[1][1] = this.squareBackground11;
        backgroundMap[1][2] = this.squareBackground12;
        backgroundMap[1][3] = this.squareBackground13;
        backgroundMap[2][0] = this.squareBackground20;
        backgroundMap[2][1] = this.squareBackground21;
        backgroundMap[2][2] = this.squareBackground22;
        backgroundMap[2][3] = this.squareBackground23;
        return backgroundMap;
    }

    @FXML private StackPane getSquareMainImage00;
    @FXML private StackPane getSquareMainImage01;
    @FXML private StackPane getSquareMainImage02;
    @FXML private StackPane getSquareMainImage03;
    @FXML private StackPane getSquareMainImage10;
    @FXML private StackPane getSquareMainImage11;
    @FXML private StackPane getSquareMainImage12;
    @FXML private StackPane getSquareMainImage13;
    @FXML private StackPane getSquareMainImage20;
    @FXML private StackPane getSquareMainImage21;
    @FXML private StackPane getSquareMainImage22;
    @FXML private StackPane getSquareMainImage23;

    public StackPane[][] getMainImagesmap(){
        StackPane[][] mainImageMap = new StackPane[3][4];
        mainImageMap[0][0] = this.getSquareMainImage00;
        mainImageMap[0][1] = this.getSquareMainImage01;
        mainImageMap[0][2] = this.getSquareMainImage02;
        mainImageMap[0][3] = this.getSquareMainImage03;
        mainImageMap[1][0] = this.getSquareMainImage10;
        mainImageMap[1][1] = this.getSquareMainImage11;
        mainImageMap[1][2] = this.getSquareMainImage12;
        mainImageMap[1][3] = this.getSquareMainImage13;
        mainImageMap[2][0] = this.getSquareMainImage20;
        mainImageMap[2][1] = this.getSquareMainImage21;
        mainImageMap[2][2] = this.getSquareMainImage22;
        mainImageMap[2][3] = this.getSquareMainImage23;
        return mainImageMap;
    }

    private static ShowPlayerEventHandler showPlayerEventHandler;

    public ShowPlayerEventHandler getShowPlayerEventHandler(){
        return this.showPlayerEventHandler;
    }
    private static ShowSquareEventHandler showSquareEventHandler;

    public ShowSquareEventHandler getShowSquareEventHandler(){
        return this.showSquareEventHandler;
    }
    private static ShowPowerUpCardsEventHandler showPowerUpCardsEventHandler;

    public ShowPowerUpCardsEventHandler getShowPowerUpCardsEventHandler(){
        return this.showPowerUpCardsEventHandler;
    }
    private static ShowWeaponCardsEventHandler showWeaponCardsEventHandler;

    public ShowWeaponCardsEventHandler getShowWeaponCardsEventHandler(){
        return this.showWeaponCardsEventHandler;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources){
        //initialize everything:
        //      1) all css classes to the corresponding element (we'll manipulates images with css classes)
        //      2) initialize the canvas
        //      3) add everything that is already setted in the ViewModel (for example the PlayerList, the current State, Timers, etc...)

        //making board auto-resize
        makeBoardAutoResizing();


        //making selector section and info section resizable
        this.interactiveSection.heightProperty().addListener((observable, oldvalue, newvalue) ->{
            this.selectorSection.setPrefHeight(this.informationSection.getHeight()/(double)2);
            this.informationSection.setPrefHeight(this.informationSection.getHeight()/(double)2);
        });

        //initialize progress indicator for the timer
        this.progressIndicator.setProgress(0.0);

        String killSlotBackground="killSlotBackground";
        String emptyKillSlot="emptyKillSlot";

        getConnection().setText("CONNECTION: "+Controller.getNetworkConnection());
        getConnection().setTextFill(Color.rgb(255, 127, 36));
        getConnection().setFont(Font.font("Courier"));

        getIp().setText("IP: "+Controller.getIp());
        getIp().setTextFill(Color.rgb(255, 127, 36));
        getIp().setFont(Font.font("Courier"));

        getPort().setText("PORT: "+Controller.getPort());
        getPort().setTextFill(Color.rgb(255, 127, 36));
        getPort().setFont(Font.font("Courier"));

        getStateTitle().setText("SETTING UP THE GAME");
        getStateTitle().setTextFill(Color.rgb(255, 127, 36));
        getStateTitle().setFont(Font.font("Courier"));

        Text player;
        Text descr=new Text(" \n setting up the Game!");

        descr.setFill(Color.rgb(255, 127, 36));
        descr.setFont(Font.font("Courier"));

        ammoMainImageBlue1.getStyleClass().clear();
        ammoMainImageBlue1.getStyleClass().add("ammoBlue");
        ammoMainImageRed1.getStyleClass().clear();
        ammoMainImageRed1.getStyleClass().add("ammoRed");
        ammoMainImageYellow1.getStyleClass().clear();
        ammoMainImageYellow1.getStyleClass().add("ammoYellow");


        if (ViewModelGate.getMe().equals(ViewModelGate.getModel().getPlayers().getStartingPlayer())) {
            player = new Text("you are  ");
        } else {
            player = new Text(ViewModelGate.getModel().getPlayers().getStartingPlayer()+"  is ");
        }

        player.setFill(Color.rgb(255, 127, 36));
        player.setFont(Font.font("Courier"));

        getStateDescription().getChildren().clear();
        getStateDescription().getChildren().addAll(player,descr);

        //killshot track default css classes
        this.killshotTrackSection.getStyleClass().add("emptyKillShotTrackBackground");
        this.killshotTrackVBox.getStyleClass().add("killShotTrackBackground");
        this.killBackground1.getStyleClass().add(killSlotBackground);
        this.killMainImage1.getStyleClass().add(emptyKillSlot);
        this.killBackground2.getStyleClass().add(killSlotBackground);
        this.killMainImage2.getStyleClass().add(emptyKillSlot);
        this.killBackground3.getStyleClass().add(killSlotBackground);
        this.killMainImage3.getStyleClass().add(emptyKillSlot);
        this.killBackground4.getStyleClass().add(killSlotBackground);
        this.killMainImage4.getStyleClass().add(emptyKillSlot);
        this.killBackground5.getStyleClass().add(killSlotBackground);
        this.killMainImage5.getStyleClass().add(emptyKillSlot);
        this.killBackground6.getStyleClass().add(killSlotBackground);
        this.killMainImage6.getStyleClass().add(emptyKillSlot);
        this.killBackground7.getStyleClass().add(killSlotBackground);
        this.killMainImage7.getStyleClass().add(emptyKillSlot);
        this.killBackground8.getStyleClass().add(killSlotBackground);
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
        String markBackGround="markBackGround";
        String markEmpty="markEmpty";
        this.playerMarks.getStyleClass().add("marksBackground");
        this.backgroundMark1.getStyleClass().add(markBackGround);
        this.backgroundMark2.getStyleClass().add(markBackGround);
        this.backgroundMark3.getStyleClass().add(markBackGround);
        this.backgroundMark4.getStyleClass().add(markBackGround);
        this.mainImageMark1.getStyleClass().add(markBackGround);
        this.mainImageMark2.getStyleClass().add(markEmpty);
        this.mainImageMark3.getStyleClass().add(markEmpty);
        this.mainImageMark4.getStyleClass().add(markEmpty);
        //damage and deaths
        this.playerDamagesAndDeathsVBox.getStyleClass().add("playerDamageAndDeathsBackground");
        //damage
        String damageBackGround="damageBackground";
        String damageEmpty="damageEmpty";


        this.playerDamagesHbox.getStyleClass().add("damagesBackground");
        this.damageBackground1.getStyleClass().add("damageBackgroundFB");
        this.damageBackground2.getStyleClass().add(damageBackGround);
        this.damageBackground3.getStyleClass().add("damageBackgroundAG");
        this.damageBackground4.getStyleClass().add(damageBackGround);
        this.damageBackground5.getStyleClass().add(damageBackGround);
        this.damageBackground6.getStyleClass().add("damageBackgroundAS");
        this.damageBackground7.getStyleClass().add(damageBackGround);
        this.damageBackground8.getStyleClass().add(damageBackGround);
        this.damageBackground9.getStyleClass().add(damageBackGround);
        this.damageBackground10.getStyleClass().add(damageBackGround);
        this.damageBackground11.getStyleClass().add("damageBackgroundK");
        this.damageBackground12.getStyleClass().add("damageBackgroundOK");
        this.damageBackground13.getStyleClass().add("damageBackgroundEXTRA");

        for (StackPane p : getDamagesMainImage()){
            p.getStyleClass().add(damageEmpty);
        }

        String deathBackground="deathBackground";
        String deathSkull="deathSkull";
        //death
        this.playerDeathsHbox.getStyleClass().add("deathsBackground");
        this.deathBackground1.getStyleClass().add(deathBackground);
        this.deathBackground2.getStyleClass().add(deathBackground);
        this.deathBackground3.getStyleClass().add(deathBackground);
        this.deathBackground4.getStyleClass().add(deathBackground);
        this.deathBackground5.getStyleClass().add(deathBackground);
        this.deathBackground6.getStyleClass().add(deathBackground);
        this.deathBackground7.getStyleClass().add(deathBackground);
        this.deathMainImage1.getStyleClass().add(deathSkull);
        this.deathMainImage2.getStyleClass().add(deathSkull);
        this.deathMainImage3.getStyleClass().add(deathSkull);
        this.deathMainImage4.getStyleClass().add(deathSkull);
        this.deathMainImage5.getStyleClass().add(deathSkull);
        this.deathMainImage6.getStyleClass().add(deathSkull);
        this.deathMainImage7.getStyleClass().add(deathSkull);
        //nickname
        this.playerNicknameBackground.getStyleClass().add("nicknameBackground");
        String ammoBackground="ammoBackground";
        String emptyAmmo="emptyAmmo";
        //ammo box
        this.playerAmmoBox.getStyleClass().add("ammoBoxBackground");
        this.ammoBackgroundBlue1.getStyleClass().add(ammoBackground);
        this.ammoBackgroundBlue2.getStyleClass().add(ammoBackground);
        this.ammoBackgroundBlue3.getStyleClass().add(ammoBackground);
        this.ammoBackgroundRed1.getStyleClass().add(ammoBackground);
        this.ammoBackgroundRed2.getStyleClass().add(ammoBackground);
        this.ammoBackgroundRed3.getStyleClass().add(ammoBackground);
        this.ammoBackgroundYellow1.getStyleClass().add(ammoBackground);
        this.ammoBackgroundYellow2.getStyleClass().add(ammoBackground);
        this.ammoBackgroundYellow3.getStyleClass().add(ammoBackground);
        this.ammoMainImageBlue1.getStyleClass().add(emptyAmmo);
        this.ammoMainImageBlue2.getStyleClass().add(emptyAmmo);
        this.ammoMainImageBlue3.getStyleClass().add(emptyAmmo);
        this.ammoMainImageRed1.getStyleClass().add(emptyAmmo);
        this.ammoMainImageRed2.getStyleClass().add(emptyAmmo);
        this.ammoMainImageRed3.getStyleClass().add(emptyAmmo);
        this.ammoMainImageYellow1.getStyleClass().add(emptyAmmo);
        this.ammoMainImageYellow2.getStyleClass().add(emptyAmmo);
        this.ammoMainImageYellow3.getStyleClass().add(emptyAmmo);
        String weaponCardBackground="weaponCardBackground";
        String emptyWeaponCardMainImage="emptyWeaponCardMainImage";
        //weapon cards
        this.weaponCardsVBox.getStyleClass().add("weaponCardsBackground");
        this.weaponCardsTitle.getStyleClass().add("weaponCardsTitleBackground");
        this.weaponCardsBackground.getStyleClass().add("weaponCardsBackground");
        this.weaponCardBackground1.getStyleClass().add(weaponCardBackground);
        this.weaponCardBackground2.getStyleClass().add(weaponCardBackground);
        this.weaponCardBackground3.getStyleClass().add(weaponCardBackground);
        this.weaponCardMainImage1.getStyleClass().add(emptyWeaponCardMainImage);
        this.weaponCardMainImage2.getStyleClass().add(emptyWeaponCardMainImage);
        this.weaponCardMainImage3.getStyleClass().add(emptyWeaponCardMainImage);


        showPlayerEventHandler=new ShowPlayerEventHandler();
        showPowerUpCardsEventHandler=new ShowPowerUpCardsEventHandler();
        showWeaponCardsEventHandler=new ShowWeaponCardsEventHandler();
        showSquareEventHandler=new ShowSquareEventHandler();

        for (StackPane powerUpCard: getListOfPowerUpCardsMainImage()){
            powerUpCard.addEventHandler(MouseEvent.MOUSE_ENTERED,getShowPowerUpCardsEventHandler());
        }
        for (StackPane weaponCard : getWeaponCardsMainImage()) {
            weaponCard.addEventHandler(MouseEvent.MOUSE_ENTERED, getShowWeaponCardsEventHandler());
        }
        for (StackPane kills: getDeathMainImage()) {
            kills.addEventHandler(MouseEvent.MOUSE_ENTERED, getShowPlayerEventHandler());
        }

        for (StackPane damage: getDamagesMainImage()) {
            damage.addEventHandler(MouseEvent.MOUSE_ENTERED, getShowPlayerEventHandler());
        }

        for (StackPane mark: getMarkMainImage()) {
            mark.addEventHandler(MouseEvent.MOUSE_ENTERED, getShowPlayerEventHandler());
        }

        //initializePlayersImages();

    }


    private void initializePlayersImages(){

        PlayerV player= ViewModelGate.getModel().getPlayers().getPlayer(ViewModelGate.getMe());
        PlayersColors color = player.getColor();
        switch (color) {
            case yellow:
                getNicknameBackGround().getStyleClass().add("nicknameBackgroundYellow");
                break;
            case blue:
                getNicknameBackGround().getStyleClass().add("nicknameBackgroundBlue");
                break;
            case green:
               getNicknameBackGround().getStyleClass().add("nicknameBackgroundGreen");
                break;
            case gray:
                getNicknameBackGround().getStyleClass().add("nicknameBackgroundGray");
                break;
            case purple:
                getNicknameBackGround().getStyleClass().add("nicknameBackgroundPurple");
                break;
            default:
                getNicknameBackGround().getStyleClass().add("nicknameBackground");

        }

    }
    private void makeBoardAutoResizing(){
        //this.boardBakcground resize based on this.boardSection
        this.boardSection.heightProperty().addListener((observable, oldvalue, newvalue) ->
                resizeBoard()
        );
        this.boardSection.widthProperty().addListener((observable, oldvalue, newvalue) ->
                resizeBoard()
        );
    }
    private void resizeBoard(){
        double totalWidth = this.boardSection.getWidth();
        double totalHeight = this.boardSection.getHeight();

        //System.out.println("width: " + totalWidth + ", height: " + totalHeight);

        double topSpacing;
        double rightSpacing;
        double bottomSpacing;
        double leftSpacing;

        if(totalWidth < (totalHeight)*((double)4/(double)3)){ //full width, calculate height
            //System.out.println("FULL WIDTH because:    totalWidth = " + totalWidth +"   <   (totalHeight*((double)4/(double)3)) = " + (totalHeight*((double)4/(double)3)));
            rightSpacing = 0.0;
            leftSpacing = 0.0;

            double heightOfTheBoard = totalWidth*((double)3/(double)4);

            topSpacing = ((totalHeight-heightOfTheBoard)/(double)2);
            bottomSpacing = topSpacing;
        }
        else{ //full height, calculate width
            //System.out.println("FULL HEIGHT because:    totalWidth = " + totalWidth +"   >=   (totalHeight = " + totalHeight + ")*((double)4/(double)3)) = " + (totalHeight*((double)4/(double)3)));
            topSpacing = 0.0;
            bottomSpacing = 0.0;

            double widthOfTheBoard = totalHeight*((double)4/(double)3);

            rightSpacing = ((totalWidth-widthOfTheBoard)/(double)2);
            leftSpacing = rightSpacing;
        }

        //System.out.println(
        //        "top: " + topSpacing + "\n" +
        //        "right: " + rightSpacing + "\n" +
        //        "bottom: " + bottomSpacing + "\n" +
        //        "left: " + leftSpacing
        //);

        AnchorPane.setTopAnchor(this.boardBackGround, topSpacing);
        AnchorPane.setRightAnchor(this.boardBackGround,rightSpacing);
        AnchorPane.setBottomAnchor(this.boardBackGround,bottomSpacing);
        AnchorPane.setLeftAnchor(this.boardBackGround,leftSpacing);
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

        /*
        if(newSection.getClass().toString().contains("ScrollPane")){
            ((AnchorPane)((ScrollPane)newSection).getContent()).heightProperty().addListener(observable -> {
                ((ScrollPane)newSection).setVvalue(0D);
                System.out.println("setVvalue");
                //TODO why this doesn't work? can't find a solution
            });
        }
        */
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


     private class ShowPlayerEventHandler implements EventHandler {

         @Override
         public void handle(javafx.event.Event event){
             if((((Node)event.getSource()).getUserData())!=null) {
                 PlayerV playerToShow = (PlayerV) ((Node) event.getSource()).getUserData();
                 showPlayer(playerToShow);
             }
         }
     }

    private class ShowPowerUpCardsEventHandler implements EventHandler {

        @Override
        public void handle(javafx.event.Event event){
            if((((Node)event.getSource()).getUserData())!=null) {
                PowerUpCardV powerUpCardToShow = (PowerUpCardV) ((Node) event.getSource()).getUserData();
                System.out.println("Showing power up: " + powerUpCardToShow.getID() + ", " + powerUpCardToShow.getName());
                showPowerUpCard(powerUpCardToShow);
            }
        }
    }

    private class ShowWeaponCardsEventHandler implements EventHandler {

        @Override
        public void handle(javafx.event.Event event){

            if((((Node)event.getSource()).getUserData())!=null) {
                WeaponCardV weaponCardVtoShow = (WeaponCardV) ((Node) event.getSource()).getUserData();
                showWeaponCard(weaponCardVtoShow);
            }
        }
    }

    private class ShowSquareEventHandler implements EventHandler {

        @Override
        public void handle(javafx.event.Event event){

            if((((Node)event.getSource()).getUserData())!=null) {
                SquareV squareToShow = (SquareV) ((Node) event.getSource()).getUserData();
                showSquare(squareToShow);
            }
        }
    }




    void showPlayer(PlayerV playerV){
        (new Thread(new ShowPlayer(playerV))).start();
    }

    private class ShowPlayer implements Runnable {
        PlayerV playerV;


        ShowPlayer(PlayerV playerV) {
            this.playerV = playerV;
            System.out.println(playerV.getNickname());
        }

        @Override
        public void run() {
            VBox mainFrame = new VBox();
            Color color=setColor(playerV);


            VBox avatar= new VBox();

            Label name = new Label();
            name.setText(playerV.getNickname());
            name.setTextFill(color);
            name.setFont(Font.font("Courier"));

            avatar.getChildren().add(name);
            VBox.setVgrow(name,Priority.ALWAYS);

            StackPane image=new StackPane();
            avatar.getChildren().add(image);
            VBox.setVgrow(image,Priority.ALWAYS);

            System.out.println(playerV.getNickname());

            mainFrame.getChildren().add(avatar);
            VBox.setVgrow(avatar, Priority.ALWAYS);

            HBox markstracker=new HBox();
            for (MarkSlotV markSlotV: playerV.getMarksTracker().getMarkSlotsList()) {

                StackPane background=new StackPane();

                StackPane mark=new StackPane();

                background.getChildren().add(mark);

                mark.getStyleClass().add(setMarkImage(ViewModelGate.getModel().getPlayers().getPlayer(markSlotV.getMarkingPlayer()).getColor()));

                Label quantity= new Label();
                quantity.setText(""+ markSlotV.getQuantity());
                mark.getChildren().add(quantity);



                markstracker.getChildren().add(background);
                HBox.setHgrow(mark, Priority.ALWAYS);
            }

            mainFrame.getChildren().add(markstracker);
            VBox.setVgrow(markstracker, Priority.ALWAYS);


            HBox damageTracker=new HBox();
            for (DamageSlotV damageSlotV: playerV.getDamageTracker().getDamageSlotsList()) {

                StackPane background=new StackPane();

                StackPane damage=new StackPane();

                background.getChildren().add(damage);

                damage.getStyleClass().add(setDamageImage(ViewModelGate.getModel().getPlayers().getPlayer(damageSlotV.getShootingPlayerNickname()).getColor()));

                damageTracker.getChildren().add(background);
                HBox.setHgrow(damage, Priority.ALWAYS);
            }

            mainFrame.getChildren().add(damageTracker);
            VBox.setVgrow(markstracker, Priority.ALWAYS);



            Label deaths=new Label();
            deaths.setText("THE PLAYER DIED A NUMBER OF TIME EQUALS TO \n :  "+playerV.getNumberOfDeaths());
            deaths.setTextFill(color);
            deaths.setFont(Font.font("Courier"));

            mainFrame.getChildren().add(deaths);
            System.out.println(mainFrame.getChildren().toString());

            Platform.runLater(() -> {

                changeInformationSection(mainFrame);


            });


        }


        private String setMarkImage(PlayersColors color){
            String style;

            switch (color){
                case yellow: style="markYellow";break;
                case blue:style="markBlue";break;
                case green:style="markGreen";break;
                case gray:style="markGray";break;
                case purple:style="markPurple";break;
                default:style="markPlayer";
                    break;
            }
            return style;
        }

        private String setDamageImage(PlayersColors color){
            String style;

            switch (color){
                case yellow: style="damageYellow";break;
                case blue:style="damageBlue";break;
                case green:style="damageGreen";break;
                case gray:style="damageGray";break;
                case purple:style="damagePurple";break;
                default:style="damagePlayer";
                    break;
            }
            return style;
        }
        private Color setColor(PlayerV playerV) {
            Color color;

            switch (playerV.getColor()){
                case yellow: color=Color.rgb(255,166,0);break;
                case blue:color=Color.rgb(0,0,255);break;
                case green:color=Color.rgb(0,255,0);break;
                case gray:color=Color.rgb(39,39,44);break;
                case purple:color=Color.rgb(153,0,118);break;
                default:color=Color.rgb(0,0,0);
                    break;
            }
            return color;
        }
    }

    void showWeaponCard(WeaponCardV weaponCard){

        ( new Thread(new ShowWeaponCard(weaponCard))).start();
    }


    private class ShowWeaponCard implements Runnable{
        WeaponCardV weaponCard;

        ShowWeaponCard(WeaponCardV weaponCard){
            this.weaponCard=weaponCard;
        }

        @Override
        public void run(){

            VBox vBox=new VBox();

            StackPane card=new StackPane();
            VBox.setVgrow(card,Priority.ALWAYS);



            vBox.getChildren().add(card);
            card.getStyleClass().add("weaponCard"+weaponCard.getID());

            System.out.println(vBox.getChildren().toString());

            Platform.runLater(()->{

                changeInformationSection(vBox);

            });

        }

    }


    void showPowerUpCard(PowerUpCardV powerUpCard){

        ( new Thread(new ShowPowerUpCard(powerUpCard))).start();
    }


    private class ShowPowerUpCard implements Runnable{
        PowerUpCardV powerUpCard;

        ShowPowerUpCard(PowerUpCardV powerUpCard){
            this.powerUpCard=powerUpCard;
        }
        @Override
        public void run(){
            VBox vBox=new VBox();

            StackPane card=new StackPane(new Label(powerUpCard.getName()));
            VBox.setVgrow(card,Priority.ALWAYS);

            vBox.getChildren().add(card);
            card.getStyleClass().add("powerUpCard"+powerUpCard.getID());

            Platform.runLater(()-> changeInformationSection(vBox));
        }

    }
    void showSquare(SquareV squareV){
        (new Thread(new ShowSquare(squareV))).start();
    }


    private class ShowSquare implements Runnable{
        SquareV squareV;
        ShowSquare(SquareV squareV){
            this.squareV=squareV;
        }

        @Override
        public void run(){

            VBox mainFrame=new VBox();
            HBox cards=new HBox();
            HBox players=new HBox();

            mainFrame.getChildren().addAll(cards, players);
            VBox.setVgrow(cards, Priority.ALWAYS);
            VBox.setVgrow(players, Priority.ALWAYS);


            if(squareV.getClass().toString().contains("Normal")) {

                List<AmmoCardV> listOfAmmos = ((NormalSquareV) squareV).getAmmoCards().getCards();

                for (AmmoCardV ammo : listOfAmmos) {

                    StackPane card = new StackPane();

                    card.getStyleClass().add("ammoCard" + ammo.getID());
                    cards.getChildren().add(card);

                    HBox.setHgrow(card, Priority.ALWAYS);
                }
            }
            else{

                List<WeaponCardV> listOfWeapons=((SpawnPointSquareV)squareV).getWeaponCards().getCards();

                for (WeaponCardV weaponCardV : listOfWeapons){

                    StackPane card = new StackPane();

                    card.getStyleClass().add("weaponCard" + weaponCardV.getID());
                    cards.getChildren().add(card);

                    HBox.setHgrow(card, Priority.ALWAYS);
                }

            }


            List<PlayerV> playerVS=getPlayers(squareV.getX(), squareV.getY());

            for (PlayerV playerV : playerVS){

                PlayersColors color=playerV.getColor();

                StackPane image = new StackPane();
                Label name=new Label();

                name.setText(playerV.getNickname());
                name.setFont(Font.font("Courier"));

                image.getStyleClass().add(setImage(color));

                VBox player= new VBox();
                player.getChildren().addAll(image, name);

                VBox.setVgrow(name, Priority.ALWAYS);
                VBox.setVgrow(image, Priority.ALWAYS);



                players.getChildren().add(player);



                HBox.setHgrow(player, Priority.ALWAYS);
            }


            Platform.runLater(()->{

                changeInformationSection(mainFrame);

            });

        }

        private String setImage(PlayersColors color){
            String style;

            switch (color){
                case yellow: style="playerYellow";break;
                case blue:style="playerBlue";break;
                case green:style="playerGreen";break;
                case gray:style="playerGray";break;
                case purple:style="playerPurple";break;
                default:style="emptyPlayer";
                    break;
            }
            return style;
        }

        private List<PlayerV> getPlayers(int x, int y) {
            List<PlayerV> players = new ArrayList<>();
            for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                if (p.getY() != null && p.getX() != null && p.getX() == x && p.getY() == y) {
                    players.add(p);
                }
            }
            return players;
        }


    }


    private void changeInformationSection(Node newSection){

        this.informationSection.getChildren().clear();
        this.informationSection.getChildren().add(newSection);

        AnchorPane.setTopAnchor(newSection, 0.0);
        AnchorPane.setRightAnchor(newSection, 0.0);
        AnchorPane.setLeftAnchor(newSection, 0.0);
        AnchorPane.setBottomAnchor(newSection, 0.0);

    }

}
