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

/**the controller of the main scene
 * @author LudoLerma
 *   @author FedericoMainettiGambera*/
public class GameSceneController implements Initializable {

    /**root*/
    @FXML
    private HBox root;




    /**interactive section*/
    @FXML private VBox interactiveSection;



    /**1-selector section*/
    @FXML private AnchorPane selectorSection;
    @FXML private Label selectorLabel;



    /**2-information section*/
    @FXML private AnchorPane informationSection;



    public AnchorPane getInformationSection() {
        return informationSection;
    }

    //------------------------------------------------------------------
    /**game section*/
    @FXML private AnchorPane gameSection;


    /**1-state section*/
    @FXML private AnchorPane stateSection;
    /**a label that reports the chosen connection method*/

    @FXML private VBox gameInfoVBox;
    public VBox getGameInfoVbox(){
        return this.gameInfoVBox;
    }

    @FXML private Label currentPlayingPlayerLabel;
    public Label getCurrentPlayingPlayerLabel(){
        return currentPlayingPlayerLabel;
    }
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

    /**progession indicator*/
    @FXML private ProgressIndicator progressIndicator;
    public ProgressIndicator getProgressIndicator(){
        return this.progressIndicator;
    }


    /**2-killshot track section*/
    @FXML private AnchorPane killshotTrackSection;
    /**2.1-killshot track VBox*/
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

    /**2.1.n-killshot n-th kill background and main image*/
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




    /**3-player section*/
    @FXML private AnchorPane playerSection;

    /**3.1-player HBox*/
    @FXML private HBox playerHBox;

    /**3.1.1-players power up cards*/
    @FXML private VBox powerUpCardsVBox;
    /**3.1.1.1-power up cards title*/
    @FXML private StackPane powerUpCardsTitle;
    /**3.1.1.2-power up cards images*/
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
    /**@return a list of stackpanes representing powerUps in hand*/
    public List<StackPane> getListOfPowerUpCardsBackground(){
        return new ArrayList<>(Arrays.asList(powerUpCardBackground1, powerUpCardBackground2));
    }

    /**3.1.2-player main statistics*/
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

    /**3.1.2[0,1]- player damages  deaths*/
    @FXML private VBox playerDamagesAndDeathsVBox;

    /**@return playerDamagesAndDeathsVBox*/
    public VBox getPlayerDamagesAndDeathsVBox(){
        return playerDamagesAndDeathsVBox;}

    /**3.1.2[0,1].1 - player damages*/
    @FXML private HBox playerDamagesHbox;
    /**@return playerDamagesHbox*/
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

    /**@return damages*/
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
    /**3.1.2[0,1].2 - player deaths*/
    @FXML private HBox playerDeathsHbox;

    /**@return playerDamagesHbox*/
    public HBox getPlayerDamagesHbox() {
        return playerDamagesHbox;
    }

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

    /**@return deathList, a list of all of the deathImages*/
    public List<StackPane> getDeathMainImage(){
        List<StackPane> deathList=new ArrayList<>();

        deathList.add(deathMainImage1);
        deathList.add(deathMainImage2);
        deathList.add(deathMainImage3);
        deathList.add(deathMainImage4);
        deathList.add(deathMainImage5);
        deathList.add(deathMainImage6);

        return deathList;
    }

    /**3.1.2[1,0]- player nickname*/
    /**this attribute contains a StackPane in which the label containing the player's Nickname will be framed*/
    @FXML private StackPane playerNicknameBackground;
    /** a label containing the player's nickname*/
    @FXML private Label playerNickname;
    /**@return Label*/
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
                    ammos.add(ammoMainImageBlue2);
                    ammos.add(ammoMainImageBlue3);
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

    /**3.1.3-player weapon cards*/
    @FXML private VBox weaponCardsVBox;
    /**3.1.3.1-weapon cards title*/
    @FXML private StackPane weaponCardsTitle;
    /**3.1.3.2-weapon cards images*/
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
    /**@return the weaponCardsBackGround*/
    public List<StackPane> getWeaponCardsBackground(){
        return new ArrayList<>(Arrays.asList(weaponCardBackground1, weaponCardBackground2, weaponCardBackground3));
    }


    //------------------------------4
    /**4-board section*/
    @FXML private AnchorPane boardSection;

    public StackPane getBoardBackGround() {
        return boardBackGround;
    }

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

    /**@return backGroundMap*/
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
    /**@return mainImageMap*/
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

    /**@return mainStackPaneMap*/
    public StackPane[][] getMainStackPaneMap(){
        StackPane[][] mainStackPaneMap = getMainImagesmap();
        for (int i = 0; i < mainStackPaneMap.length; i++) {
            for (int j = 0; j < mainStackPaneMap[0].length; j++) {
                mainStackPaneMap[i][j] = (StackPane)mainStackPaneMap[i][j].getChildren().get(0);
            }
        }
        return mainStackPaneMap;
    }

    /**dedicated event handler instance reference*/
    private static ShowPlayerEventHandler showPlayerEventHandler;

    /**@return showPlayerEventHandler*/
    public ShowPlayerEventHandler getShowPlayerEventHandler(){
        return showPlayerEventHandler;
    }
    /**dedicated event handler instance reference*/
    private static ShowPowerUpCardsEventHandler showPowerUpCardsEventHandler;
    /**@return showPoerUpCardsEventHandler*/
    public ShowPowerUpCardsEventHandler getShowPowerUpCardsEventHandler(){
        return showPowerUpCardsEventHandler;
    }
    /**dedicated event handler instance reference*/
    private static ShowWeaponCardsEventHandler showWeaponCardsEventHandler;
    /**@return showWeaponCardsEventHandler*/
    public ShowWeaponCardsEventHandler getShowWeaponCardsEventHandler(){
        return showWeaponCardsEventHandler;
    }
    /**dedicated event handler instance reference*/
    private static ShowAmmoCardEventHandler showAmmoCardEventHandler;
    /**@return showAmmoCardEventHandler*/
    public ShowAmmoCardEventHandler getShowAmmoCardEventHandler(){
        return showAmmoCardEventHandler;
    }

    /**initialize the game scene
     * @param location default
     * @param resources default
     *                  //initialize everything:
     *         //      1) all css classes to the corresponding element (we'll manipulates images with css classes)
     *         //      2) initialize the canvas
     *         //      3) add everything that is already setted in the ViewModel (for example the PlayerList, the current State, Timers, etc...)
     *
     *
     * */
    @Override
    public void initialize(URL location, ResourceBundle resources){
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

        getIp().setText("IP: "+Controller.getIp());

        getPort().setText("PORT: "+Controller.getPort());

        getStateTitle().setText("SETTING UP THE GAME");
        getStateTitle().setTextFill(Color.rgb(255, 181, 38));
        getStateTitle().setFont(Font.font("Monospace"));

        Text player;
        Text descr=new Text(" \n setting up the Game!");

        descr.setFill(Color.rgb(255, 181, 38));
        descr.setFont(Font.font("Monospace"));


        if (ViewModelGate.getMe().equals(ViewModelGate.getModel().getPlayers().getStartingPlayer())) {
            player = new Text("you are  ");
        } else {
            player = new Text(ViewModelGate.getModel().getPlayers().getStartingPlayer()+"  is ");
        }

        player.setFill(Color.rgb(255, 181, 38));
        player.setFont(Font.font("Monospace"));

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
        this.deathMainImage1.getStyleClass().add(deathSkull);
        this.deathMainImage2.getStyleClass().add(deathSkull);
        this.deathMainImage3.getStyleClass().add(deathSkull);
        this.deathMainImage4.getStyleClass().add(deathSkull);
        this.deathMainImage5.getStyleClass().add(deathSkull);
        this.deathMainImage6.getStyleClass().add(deathSkull);
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
        showAmmoCardEventHandler = new ShowAmmoCardEventHandler();

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


    }
    /**make the board auto reasizing */
    private void makeBoardAutoResizing(){
        //this.boardBakcground resize based on this.boardSection
        this.boardSection.heightProperty().addListener((observable, oldvalue, newvalue) ->
                resizeBoard()
        );
        this.boardSection.widthProperty().addListener((observable, oldvalue, newvalue) ->
                resizeBoard()
        );
    }
    /**resize board*/
    private void resizeBoard(){
        double totalWidth = this.boardSection.getWidth();
        double totalHeight = this.boardSection.getHeight();

        double topSpacing;
        double rightSpacing;
        double bottomSpacing;
        double leftSpacing;

        if(totalWidth < (totalHeight)*((double)4/(double)3)){ //full width, calculate height
            rightSpacing = 0.0;
            leftSpacing = 0.0;

            double heightOfTheBoard = totalWidth*((double)3/(double)4);

            topSpacing = ((totalHeight-heightOfTheBoard)/(double)2);
            bottomSpacing = topSpacing;
        }
        else{ //full height, calculate width
            topSpacing = 0.0;
            bottomSpacing = 0.0;

            double widthOfTheBoard = totalHeight*((double)4/(double)3);

            rightSpacing = ((totalWidth-widthOfTheBoard)/(double)2);
            leftSpacing = rightSpacing;
        }


        AnchorPane.setTopAnchor(this.boardBackGround, topSpacing);
        AnchorPane.setRightAnchor(this.boardBackGround,rightSpacing);
        AnchorPane.setBottomAnchor(this.boardBackGround,bottomSpacing);
        AnchorPane.setLeftAnchor(this.boardBackGround,leftSpacing);
    }

    /**sends an Event to the server in a new Thread, using the SendToServerThread class
     * @param event to be sent*/
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
    /**@param newSection the new node to be shown
     * @param bottom value of pixel where to anchor the anchor pane
     * @param left value of pixel where to anchor the anchor pane
     * @param right value of pixel where to anchor the anchor pane
     * @param top value of pixel where to anchor the anchor pane
     * */
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
    /**remove previous selector section*/
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
    /**@return selectorSection*/
    public AnchorPane getSelectorSection(){
        return this.selectorSection;
    }

    /**this class implements a eventHandler for showing players data in the information section*/
     private class ShowPlayerEventHandler implements EventHandler {
        /**implements the showPlayer method in case the triggering event contains a userData*/
         @Override
         public void handle(javafx.event.Event event){
             if((((Node)event.getSource()).getUserData())!=null) {
                 PlayerV playerToShow = (PlayerV) ((Node) event.getSource()).getUserData();
                 showPlayer(playerToShow);
             }
         }
     }
    /**this class implements a eventHandler for showing PowerUpCards data in the information section*/
    private class ShowPowerUpCardsEventHandler implements EventHandler {
        /**implements the showPowerUpCard method in case the triggering event contains a userData*/
        @Override
        public void handle(javafx.event.Event event){
            if((((Node)event.getSource()).getUserData())!=null) {
                PowerUpCardV powerUpCardToShow = (PowerUpCardV) ((Node) event.getSource()).getUserData();
                showPowerUpCard(powerUpCardToShow);
            }
        }
    }
    /**this class implements a eventHandler for showing Weapon Cards data in the information section*/
    private class ShowWeaponCardsEventHandler implements EventHandler {
        /**implements the showWeaponCards method in case the triggering event contains a userData*/
        @Override
        public void handle(javafx.event.Event event){

            if((((Node)event.getSource()).getUserData())!=null) {
                WeaponCardV weaponCardVtoShow = (WeaponCardV) ((Node) event.getSource()).getUserData();
                showWeaponCard(weaponCardVtoShow);
            }
        }
    }
    /**this class implements a eventHandler for showing AmmoCard data in the information section*/
    private class ShowAmmoCardEventHandler implements EventHandler{
        /**implements the showAmmoCard method in case the triggering event contains a userData*/
        @Override
        public void handle(javafx.event.Event event) {
            if((((Node)event.getSource()).getUserData())!=null) {
                AmmoCardV ammoCardToShow = (AmmoCardV) ((Node) event.getSource()).getUserData();
                showAmmoCard(ammoCardToShow);
            }
        }
    }



    /**@param playerV is the player to be shown
     * this function launches a thread of the class ShowPlayer*/
    private void showPlayer(PlayerV playerV){
        (new Thread(new ShowPlayer(playerV))).start();
    }
    /**class that implements a thread for building a structure that will be
     * showing information about a player */
    private class ShowPlayer implements Runnable {
        PlayerV playerV;
        /**constructor,
         * @param playerV is the player whose information needs to be displayed*/
        ShowPlayer(PlayerV playerV) {
            this.playerV = playerV;
        }
        /**@param color the color of the player
         * @return a string containing a color*/
        private String getColorStringWithFirstCapitalLetter(PlayersColors color){
            if(color.equals(PlayersColors.gray)){
                return "Gray";
            }
            else if(color.equals(PlayersColors.green)){
                return "Green";
            }
            else if(color.equals(PlayersColors.blue)){
                return "Blue";
            }
            else if(color.equals(PlayersColors.purple)){
                return "Purple";
            }
            else{
                return "Yellow";
            }
        }
        /**@param color the color of the ammo
         * @return a string containing a color*/
        private String getColorStringWithFirstCapitalLetter(AmmoCubesColor color){
            if(color.equals(AmmoCubesColor.yellow)){
                return "Yellow";
            }
            else if(color.equals(AmmoCubesColor.red)){
                return "Red";
            }
            else {
                return "Blue";
            }
        }
        /**the thread that builds the structure where the information will be displayed and that finally calls the
         * Platform.runlater()
         * */
        @Override
        public void run() {
            VBox mainVbox = new VBox(); //contains everything

            //nickname / color
            Label nicknameLabel;
            if(!playerV.isAFK()) {
                nicknameLabel= new Label(playerV.getNickname());
            }
            else {
                nicknameLabel = new Label("AFK: " + playerV.getNickname());
            }
            StackPane nickname = new StackPane(nicknameLabel);
            nickname.getStyleClass().add("nicknameBackground"+getColorStringWithFirstCapitalLetter(playerV.getColor()));
            nicknameLabel.getStyleClass().add("nicknameStyle");
            mainVbox.getChildren().add(nickname);

            HBox hBox = new HBox(); //contains vbox of damages and markd and vbox of ammos
            VBox.setVgrow(hBox, Priority.ALWAYS);
            mainVbox.getChildren().add(hBox);

            VBox vBoxMarksAndDamages = new VBox(); //contains marks and damages
            HBox.setHgrow(vBoxMarksAndDamages, Priority.ALWAYS);
            hBox.getChildren().add(vBoxMarksAndDamages);

            //marks
            HBox hBoxMarks = new HBox();
            VBox.setVgrow(hBoxMarks,Priority.ALWAYS);
            vBoxMarksAndDamages.getChildren().add(hBoxMarks);
            hBoxMarks.getStyleClass().add("marksBackground");
            int numberOfFullMarksSlots = playerV.getMarksTracker().getMarkSlotsList().size();
            for (int i = 0; i < numberOfFullMarksSlots; i++) { // full marks
                StackPane markSlot = new StackPane(new Label(""+ playerV.getMarksTracker().getMarkSlotsList().get(i).getQuantity()));
                HBox.setHgrow(markSlot,Priority.ALWAYS);
                hBoxMarks.getChildren().add(markSlot);
                PlayerV markingPlayer = ViewModelGate.getModel().getPlayers().getPlayer(playerV.getMarksTracker().getMarkSlotsList().get(i).getMarkingPlayer());
                markSlot.getStyleClass().add("mark"+getColorStringWithFirstCapitalLetter(markingPlayer.getColor()));
            }
            for (int i = numberOfFullMarksSlots; i < 5; i++) { //empty marks
                StackPane markSlot = new StackPane(new Label());
                HBox.setHgrow(markSlot,Priority.ALWAYS);
                hBoxMarks.getChildren().add(markSlot);
                markSlot.getStyleClass().add("markEmpty");
            }

            //damages
            HBox hBoxDamages = new HBox();
            VBox.setVgrow(hBoxDamages,Priority.ALWAYS);
            vBoxMarksAndDamages.getChildren().add(hBoxDamages);
            hBoxDamages.getStyleClass().add("damagesBackground");
            int numberOfFullDamageSlots = playerV.getDamageTracker().getDamageSlotsList().size();
            for (int i = 0; i < 12 && i<numberOfFullDamageSlots; i++) { //full damages
                StackPane damageSlotBackground = new StackPane();
                StackPane damageSlotMainImage = new StackPane();
                damageSlotBackground.getChildren().add(damageSlotMainImage);
                HBox.setHgrow(damageSlotBackground,Priority.ALWAYS);
                hBoxDamages.getChildren().add(damageSlotBackground);
                PlayersColors shootingPlayerColor= playerV.getDamageTracker().getDamageSlotsList().get(i).getShootingPlayerColor();
                damageSlotMainImage.getStyleClass().add("damage"+getColorStringWithFirstCapitalLetter(shootingPlayerColor));
            }
            for (int i = numberOfFullDamageSlots; i < 12 ; i++) { //empty damages
                StackPane damageSlotBackground = new StackPane();
                StackPane damageSlotMainImage = new StackPane();
                damageSlotBackground.getChildren().add(damageSlotMainImage);
                HBox.setHgrow(damageSlotBackground,Priority.ALWAYS);
                hBoxDamages.getChildren().add(damageSlotBackground);
                damageSlotMainImage.getStyleClass().add("damageEmpty");
            }
            StackPane damageSlot13 = new StackPane();
            HBox.setHgrow(damageSlot13, Priority.ALWAYS);
            hBoxDamages.getChildren().add(damageSlot13);
            if(numberOfFullDamageSlots>=12){
                damageSlot13.getChildren().add(new Label("+" + (numberOfFullDamageSlots-12)));
            }

            VBox vBoxAmmoCubes = new VBox(); //contains ammo cubes
            HBox.setHgrow(vBoxAmmoCubes, Priority.ALWAYS);
            hBox.getChildren().add(vBoxAmmoCubes);
            //ammo box
            for (AmmoCubesColor ammoCubesColor: AmmoCubesColor.values()) {
                HBox ammosHBox = new HBox();
                VBox.setVgrow(ammosHBox,Priority.ALWAYS);
                vBoxAmmoCubes.getChildren().add(ammosHBox);
                int quantity = 0;
                for (AmmoCubesV a: playerV.getAmmoBox().getAmmoCubesList()) {
                    if(a.getColor().equals(ammoCubesColor)){
                        quantity = a.getQuantity();
                    }
                }
                for (int i = 0; i < quantity; i++) { //ammo full
                    StackPane ammo = new StackPane();
                    HBox.setHgrow(ammo, Priority.ALWAYS);
                    ammosHBox.getChildren().add(ammo);
                    ammo.getStyleClass().add("ammo"+getColorStringWithFirstCapitalLetter(ammoCubesColor));
                }
                for (int i = quantity; i < 3; i++) { //ammo empty
                    StackPane ammo = new StackPane();
                    HBox.setHgrow(ammo, Priority.ALWAYS);
                    ammosHBox.getChildren().add(ammo);
                    ammo.getStyleClass().add("emptyAmmo");
                }
            }

            //number of deaths / final frenzy board
            if(!playerV.isHasFinalFrenzyBoard()) {
                StackPane deaths = new StackPane(new Label("number of deaths: " + playerV.getNumberOfDeaths()));
                mainVbox.getChildren().add(deaths);
            }
            else{
                StackPane deaths = new StackPane(new Label("died " + playerV.getNumberOfDeaths() + " and has Final Frenzy board"));
                mainVbox.getChildren().add(deaths);
            }

            HBox hBoxWeapons = new HBox(); //contains weapons
            VBox.setVgrow(hBoxWeapons, Priority.ALWAYS);
            mainVbox.getChildren().add(hBoxWeapons);
            if(!playerV.getWeaponCardInHand().getCards().isEmpty()) {
                //weapon cards
                for (WeaponCardV weapon : playerV.getWeaponCardInHand().getCards()) {
                    StackPane weaponStackPane = new StackPane(new Label(weapon.getName()));
                    HBox.setHgrow(weaponStackPane, Priority.ALWAYS);
                    hBoxWeapons.getChildren().add(weaponStackPane);
                    weaponStackPane.getStyleClass().add("weaponCard"+weapon.getID());
                }
            }
            else{
                StackPane emptyWeapon = new StackPane(new Label("no weapons"));
                HBox.setHgrow(emptyWeapon, Priority.ALWAYS);
                hBoxWeapons.getChildren().add(emptyWeapon);
            }

            Platform.runLater(() -> changeInformationSection(mainVbox));
        }
    }
    /**@param weaponCard is the weapon to be shown
     * this function launches a thread of the class showWeaponCard*/
    void showWeaponCard(WeaponCardV weaponCard){

        ( new Thread(new ShowWeaponCard(weaponCard))).start();
    }

    /**class that implements a thread for building a structure that will be
     * showing information about the weaponCards */
    private class ShowWeaponCard implements Runnable{
        WeaponCardV weaponCard;
        /**constructor,
         * @param weaponCard to set weaponCard attribute*/
        ShowWeaponCard(WeaponCardV weaponCard){
            this.weaponCard=weaponCard;
        }
        /**thread that builds the structure that will be displaying the weaponCards*/
        @Override
        public void run(){

            VBox vBox=new VBox();

            StackPane card=new StackPane();
            VBox.setVgrow(card,Priority.ALWAYS);



            vBox.getChildren().add(card);
            card.getStyleClass().add("weaponCard"+weaponCard.getID());

            Platform.runLater(()-> changeInformationSection(vBox));

        }

    }

    /**@param powerUpCard is the powerUpCard to be shown,
     * this method builds and calls a thread from the class ShowPowerUpCard */
    void showPowerUpCard(PowerUpCardV powerUpCard){

        ( new Thread(new ShowPowerUpCard(powerUpCard))).start();
    }

    /**a private class that implements a thread that builds a structure where to display a powerUpCard*/
    private class ShowPowerUpCard implements Runnable{
        PowerUpCardV powerUpCard;
        /**constructor,
         *@param powerUpCard to set powerUpCard attribute */
        ShowPowerUpCard(PowerUpCardV powerUpCard){
            this.powerUpCard=powerUpCard;
        }
        /**the method that build the structure where to display the powerUpCardV*/
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

    /**@param ammoCard the ammoCard to be displayed
     * this method instance and start a thread from the class ShowAmmoCard
     * */
    void showAmmoCard(AmmoCardV ammoCard){
        (new Thread(new ShowAmmoCard(ammoCard))).start();
    }
    /**implements a thread that builds a structure for displaying an ammoCard */
    private class ShowAmmoCard implements Runnable{
        private AmmoCardV ammoCard;
        /**constructor,
         * @param ammoCard to set ammoCard attribute*/
        ShowAmmoCard(AmmoCardV ammoCard){
            this.ammoCard = ammoCard;
        }
        /**the thread that builds the structure for displaying the ammoCard */
        @Override
        public void run() {
            VBox mainFrame = new VBox();
            StackPane backGroundStackPane = new StackPane();
            StackPane mainImageStackPane = new StackPane();

            backGroundStackPane.getChildren().add(mainImageStackPane);
            mainFrame.getChildren().add(backGroundStackPane);

            VBox.setVgrow(backGroundStackPane,Priority.ALWAYS);
            VBox.setVgrow(mainImageStackPane, Priority.ALWAYS);

            mainImageStackPane.getStyleClass().add("ammoCard" + ammoCard.getID());

            Platform.runLater(()-> changeInformationSection(mainFrame));
        }
    }

    /**@param newSection is the node that will replace the previous in InformationSection*/
    private void changeInformationSection(Node newSection){

        this.informationSection.getChildren().clear();
        this.informationSection.getChildren().add(newSection);

        AnchorPane.setTopAnchor(newSection, 0.0);
        AnchorPane.setRightAnchor(newSection, 0.0);
        AnchorPane.setLeftAnchor(newSection, 0.0);
        AnchorPane.setBottomAnchor(newSection, 0.0);

    }

}
