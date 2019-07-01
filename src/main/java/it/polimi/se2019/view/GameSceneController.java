package it.polimi.se2019.view;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.model.events.Event;
import it.polimi.se2019.view.outputHandler.GUIOutputHandler;
import it.polimi.se2019.view.selector.ViewSelector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.*;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ArrayList;
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
    //create at run time... TODO.

    //------------------------------2
    //2-information section
    @FXML private AnchorPane informationSection;
    //create at run time... TODO.


    //------------------------------------------------------------------
    //game section
    @FXML private AnchorPane gameSection;


    //------------------------------1
    //1-state section
    @FXML private AnchorPane stateSection;
    @FXML private Label connection;
    @FXML private Label ip;
    @FXML private Label port;
    @FXML private Label stateTitle;
    @FXML private TextFlow stateDescription;

    @FXML private ProgressIndicator progressIndicator;
    public ProgressIndicator getProgressIndicator(){
        return this.progressIndicator;
    }


    //------------------------------2
    //2-killshot track section
    @FXML private AnchorPane killshotTrackSection;
    //2.1-killshot track VBox
    @FXML private VBox killshotTrackVBox;

    public StackPane getKillBackground1() {
        return killBackground1;
    }

    public StackPane getKillMainImage1() {
        return killMainImage1;
    }

    public StackPane getKillBackground2() {
        return killBackground2;
    }

    public StackPane getKillMainImage2() {
        return killMainImage2;
    }

    public StackPane getKillBackground3() {
        return killBackground3;
    }

    public StackPane getKillMainImage3() {
        return killMainImage3;
    }

    public StackPane getKillBackground4() {
        return killBackground4;
    }

    public StackPane getKillMainImage4() {
        return killMainImage4;
    }

    public StackPane getKillBackground5() {
        return killBackground5;
    }

    public StackPane getKillMainImage5() {
        return killMainImage5;
    }

    public StackPane getKillBackground6() {
        return killBackground6;
    }

    public StackPane getKillMainImage6() {
        return killMainImage6;
    }

    public StackPane getKillBackground7() {
        return killBackground7;
    }

    public StackPane getKillMainImage7() {
        return killMainImage7;
    }

    public StackPane getKillBackground8() {
        return killBackground8;
    }

    public StackPane getKillMainImage8() {
        return killMainImage8;
    }

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
    @FXML private AnchorPane playerSection;

    //3.1-player HBox
    @FXML private HBox playerHBox;

    //3.1.1-players power up cards
    @FXML private VBox powerUpCardsVBox;
    //3.1.1.1-power up cards title
    @FXML private StackPane powerUpCardsTitle;
    //3.1.1.2-power up cards images
    @FXML private HBox powerUpCardsBackground;
    @FXML private StackPane powerUpCardBackground1;
    @FXML private StackPane powerUpCardMainImage1;
    @FXML private StackPane powerUpCardBackground2;

    public StackPane getPowerUpCardMainImage2() {
        return powerUpCardMainImage2;
    }
    public StackPane getPowerUpCardMainImage1() {
        return powerUpCardMainImage1;
    }
    @FXML private StackPane powerUpCardMainImage2;

    //3.1.2-player main statistics
    @FXML private GridPane playerStats;

    //3.1.2[0,0]- players marks
    /**each one of this stack pane contain a markImage, for each slot, 4 in total, there is a mark main image stack pane and
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
    @FXML private StackPane weaponCardBackground1;
    @FXML private StackPane weaponCardMainImage1;
    @FXML private StackPane weaponCardBackground2;
    @FXML private StackPane weaponCardMainImage2;
    @FXML private StackPane weaponCardBackground3;
    @FXML private StackPane weaponCardMainImage3;

    public StackPane getWeaponCardMainImage1() {
        return weaponCardMainImage1;
    }
    public StackPane getWeaponCardMainImage2() {
        return weaponCardMainImage2;
    }
    public StackPane getWeaponCardMainImage3() {
        return weaponCardMainImage3;
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
        return mainImageMap;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initialize everything:
        //      1) all css classes to the corresponding element (we'll manipulates images with css classes)
        //      2) initialize the canvas
        //      3) add everything that is already setted in the ViewModel (for example the PlayerList, the current State, Timers, etc...)

        //TODO
        //  faccio in modo che le selezioni reindirizzate alla CLI siano automatiche..
        Controller.setRandomGame(true);

        //making board auto-resize
        makeBoardAutoResizing();

        //initialize progress indicator for the timer
        this.progressIndicator.setProgress(0.0);

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
