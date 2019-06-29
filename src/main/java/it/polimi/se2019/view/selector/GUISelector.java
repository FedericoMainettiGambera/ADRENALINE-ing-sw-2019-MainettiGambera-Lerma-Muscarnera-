package it.polimi.se2019.view.selector;

import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPaymentInformation;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPlayers;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPositions;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPowerUpCards;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventGameSetUp;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventNickname;
import it.polimi.se2019.view.GUIstarter;
import it.polimi.se2019.view.GameSceneController;
import it.polimi.se2019.view.components.*;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class GUISelector implements SelectorV {

    private String networkConnection;
    private int actionNumber;
    private boolean canUsePowerUp;
    private boolean canUseBot;

    GUISelector(String networkConnection){
        this.networkConnection = networkConnection;
    }

    private GameSceneController getGameSceneController(){
        return (GameSceneController)GUIstarter.getStageController();
    }


    @Override
    public void askGameSetUp(boolean canBot) {
        new Thread(new AskGameSetUp(canBot)).start();
    }
    private class AskGameSetUp implements Runnable{
        private boolean canBot;
        private String choosenMap = "map0";
        private boolean ifFinalFrenzy = false;
        private boolean isBot;
        private int numberOfSkulls;
        private AskGameSetUp(boolean canBot){
            this.canBot = canBot;
        }
        @Override
        public void run() {
            //waits the GameScene has been loaded
            while(!GUIstarter.getStageController().getClass().toString().contains("GameSceneController")){
                try {
                    TimeUnit.MILLISECONDS.sleep(150);
                } catch (InterruptedException e) {
                    GUIstarter.showError(this, "ERROR WAITING FOR GAME SCENE CONTROLLER TO LOAD", e);
                }
            }
            //ask Map, ask FF, ask Bot, ask number of skulls
            Node request = builRequest();
            Platform.runLater(()-> {
                getGameSceneController().changeSelectorSection(request, 0.0, 0.0, 0.0, 0.0);
                Platform.runLater(()->((ScrollPane)request).setVvalue(1.0));
            });
        }
        private Node builRequest(){
            //main structure
            AnchorPane requestContainer = new AnchorPane();
            VBox scrollContent = new VBox();
            requestContainer.getChildren().add(scrollContent);
            AnchorPane.setTopAnchor(scrollContent, 0.0);
            AnchorPane.setRightAnchor(scrollContent, 0.0);
            AnchorPane.setBottomAnchor(scrollContent, 0.0);
            AnchorPane.setLeftAnchor(scrollContent, 0.0);
            //result:
            ScrollPane request = new ScrollPane(requestContainer);
            request.setFitToWidth(true);


            //building Nodes for the actuals requests:

            //map request
            HBox mapRequest = buildMapRequest();
            scrollContent.getChildren().add(mapRequest);

            //is Final frenzy
            HBox isFinalFrenzyRequest = isFinalFrenzyRequest();
            scrollContent.getChildren().add(isFinalFrenzyRequest);

            //is Bot
            HBox isBotRequest = null;
            if(canBot) {
                isBotRequest = isBotRequest();
                scrollContent.getChildren().add(isBotRequest);
            }

            //number of skulls
            VBox numberOfSkullsRequest = numberOfSkullsRequest();
            scrollContent.getChildren().add(numberOfSkullsRequest);

            StackPane doneButton = doneButton();
            scrollContent.getChildren().add(doneButton);


            //binding height property
            //seems useless, but not sure
            /*if(canBot){
                requestContainer.prefHeightProperty().bind((mapRequest).prefHeightProperty().add((isFinalFrenzyRequest).prefHeightProperty()).add((numberOfSkullsRequest).prefHeightProperty()).add((isBotRequest).prefHeightProperty()));
            }
            else {
                requestContainer.prefHeightProperty().bind((mapRequest).prefHeightProperty().add((isFinalFrenzyRequest).prefHeightProperty()).add((numberOfSkullsRequest).prefHeightProperty()));
            }
            */

            return request;
        }

        private HBox buildMapRequest(){
            //ELEMENTS
            HBox hBox = new HBox();
            VBox vBoxLeft = new VBox();
            VBox vBoxRight = new VBox();

            //STRUCTURE
            hBox.getChildren().addAll(vBoxLeft, vBoxRight);

            //PROPERTIES
            hBox.prefHeightProperty().bind(getGameSceneController().getSelectorSection().heightProperty());
            HBox.setHgrow(vBoxLeft, Priority.ALWAYS);
            HBox.setHgrow(vBoxRight, Priority.ALWAYS);

            //OTHERS
            List<StackPane> maps = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                StackPane mapMainImage = new StackPane();
                mapMainImage.setStyle("-fx-background-color: rgba(146, 255, 88, 0.5)");
                StackPane mapBackground = new StackPane(mapMainImage);
                mapBackground.setStyle("-fx-background-color: #00f3ff");

                mapMainImage.setId("map" + i);

                //EVENTS
                mapMainImage.setOnMouseClicked(e -> {
                    this.choosenMap = ((Node)e.getSource()).getId();
                    System.out.println(((Node)e.getSource()).getId());
                });
                mapMainImage.setOnMouseEntered(e->{
                    ((Node)e.getSource()).setStyle("-fx-background-color:  #ffb523");
                });
                mapMainImage.setOnMouseExited(e->{
                    ((Node)e.getSource()).setStyle("-fx-background-color: rgba(146, 255, 88, 0.5)");
                });
                maps.add(mapBackground);
            }

            vBoxLeft.getChildren().addAll(maps.get(0), maps.get(2));
            vBoxRight.getChildren().addAll(maps.get(1), maps.get(3));

            vBoxLeft.setVgrow(maps.get(0), Priority.ALWAYS);
            vBoxLeft.setVgrow(maps.get(1), Priority.ALWAYS);
            vBoxRight.setVgrow(maps.get(2), Priority.ALWAYS);
            vBoxRight.setVgrow(maps.get(3), Priority.ALWAYS);

            return hBox;
        }

        private HBox isFinalFrenzyRequest(){
            //ELEMENTS
            HBox hbox = new HBox();

            StackPane left = new StackPane();
            StackPane center = new StackPane();
            StackPane right = new StackPane();

            Label label = new Label("FINAL FRENZY: ");

            RadioButton yes = new RadioButton("ACTIVE");
            RadioButton no = new RadioButton("NON-ACTIVE");
            ToggleGroup group = new ToggleGroup();

            //STRUCTURE
            hbox.getChildren().addAll(left,center,right);
            left.getChildren().add(label);
            center.getChildren().add(yes);
            right.getChildren().add(no);

            //PROPERTIES
            yes.setToggleGroup(group);
            no.setToggleGroup(group);
            no.setSelected(true);
            HBox.setHgrow(left, Priority.ALWAYS);
            HBox.setHgrow(center, Priority.ALWAYS);
            HBox.setHgrow(right, Priority.ALWAYS);
            hbox.prefHeightProperty().bind(getGameSceneController().getSelectorSection().heightProperty().divide(4));

            //EVENTS
            yes.setOnAction(e-> {
                this.isBot = true;
                System.out.println("true");
            });
            no.setOnAction(e-> {
                this.isBot = false;
                System.out.println("false");
            });

            return hbox;
        }

        private HBox isBotRequest() {
            //ELEMENTS
            HBox hbox = new HBox();

            StackPane left = new StackPane();
            StackPane center = new StackPane();
            StackPane right = new StackPane();

            Label label = new Label("TERMINATOR: ");

            ToggleGroup group = new ToggleGroup();
            RadioButton yes = new RadioButton("USE");
            RadioButton no = new RadioButton("DON'T USE");

            //STRUCTURE
            hbox.getChildren().addAll(left,center,right);
            left.getChildren().add(label);
            center.getChildren().add(yes);
            right.getChildren().add(no);

            //PROPERTIES
            no.setSelected(true);
            yes.setToggleGroup(group);
            no.setToggleGroup(group);
            HBox.setHgrow(left, Priority.ALWAYS);
            HBox.setHgrow(center, Priority.ALWAYS);
            HBox.setHgrow(right, Priority.ALWAYS);
            hbox.prefHeightProperty().bind(getGameSceneController().getSelectorSection().heightProperty().divide(4));

            //EVENTS
            yes.setOnAction(e-> {
                this.ifFinalFrenzy = true;
                System.out.println("true");
            });
            no.setOnAction(e-> {
                this.ifFinalFrenzy = false;
                System.out.println("false");
            });

            return hbox;
        }

        private VBox numberOfSkullsRequest() {
            //ELEMENTS
            VBox vbox = new VBox();

            StackPane stackPane = new StackPane();
            Label label = new Label("Choose number of starting skulls: ");

            HBox hbox = new HBox();

            List<StackPane> stackpanes = new ArrayList<>();
            List<RadioButton> radioButtons = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                stackpanes.add(new StackPane());
                radioButtons.add(new RadioButton(""+(i+5)));
            }

            ToggleGroup group = new ToggleGroup();

            //STRUCTURE
            vbox.getChildren().addAll(stackPane, hbox);
            hbox.getChildren().addAll(stackpanes);
            stackPane.getChildren().add(label);
            for (int i = 0; i < stackpanes.size(); i++) {
                stackpanes.get(i).getChildren().add(radioButtons.get(i));
                HBox.setHgrow(stackpanes.get(i), Priority.ALWAYS);
                radioButtons.get(i).setToggleGroup(group);
            }

            //PROPERTIES
            radioButtons.get(0).setSelected(true);
            VBox.setVgrow(label,Priority.ALWAYS);
            label.setStyle("-fx-border-color: blue");
            VBox.setVgrow(hbox, Priority.ALWAYS);

            vbox.prefHeightProperty().bind(getGameSceneController().getSelectorSection().heightProperty().divide(2));

            //EVENTS
            for (int i = 0; i < radioButtons.size(); i++) {
                int value = i+5;
                radioButtons.get(i).setUserData(value);
                radioButtons.get(i).setOnAction(e->{
                    this.numberOfSkulls = (Integer)((RadioButton)e.getSource()).getUserData();
                    System.out.println(this.numberOfSkulls);
                });
            }

            return vbox;
        }

        private StackPane doneButton(){
            //ELEMENTS
            StackPane stackPane = new StackPane();
            Button button = new Button("DONE");

            //STRUCTURE
            stackPane.getChildren().add(button);

            //EVENTS
            button.setOnAction(e->{
                System.out.println("DONE");
                getGameSceneController().sendToServer(new ViewControllerEventGameSetUp("normalMode", this.choosenMap, this.numberOfSkulls, this.ifFinalFrenzy, this.isBot));
            });

            //PROPERTIES
            stackPane.prefHeightProperty().bind(getGameSceneController().getSelectorSection().heightProperty().divide(4));

            return stackPane;
        }
    }

    @Deprecated
    @Override
    public void askPlayerSetUp() {

    }

    @Override
    public void askFirstSpawnPosition(List<PowerUpCardV> powerUpCards, boolean spawnBot) {

    }

    @Override
    public void askTurnAction(int actionNumber, boolean canUsePowerUp, boolean canUseBot) {

        this.actionNumber = actionNumber;
        this.canUsePowerUp = canUsePowerUp;
        this.canUseBot = canUseBot;
    }

    @Override
    public void askBotMove(SelectorEventPositions SEPositions) {

    }

    @Override
    public void askRunAroundPosition(List<Position> positions) {

    }

    @Override
    public void askGrabStuffAction() {

    }

    @Override
    public void askGrabStuffMove(List<Position> positions) {

    }

    @Override
    public void askGrabStuffGrabWeapon(List<WeaponCardV> toPickUp) {

    }

    @Override
    public void askGrabStuffSwitchWeapon(List<WeaponCardV> toPickUp, List<WeaponCardV> toSwitch) {

    }

    @Override
    public void askPowerUpToDiscard(List<PowerUpCardV> toDiscard) {

    }

    @Override
    public void askWhatReaload(List<WeaponCardV> toReload) {

    }

    @Override
    public void askSpawn(List<PowerUpCardV> powerUpCards) {

    }

    @Override
    public void askShootOrMove() {

    }

    @Deprecated
    @Override
    public void askShootReloadMove() {

    }

    @Override
    public void askWhatWep(List<WeaponCardV> loadedCardInHand) {

    }

    @Override
    public void askWhatEffect(List<EffectV> possibleEffects) {

    }

    @Override
    public void askEffectInputs(EffectInfoType inputType, List<Object> possibleInputs) {

    }

    @Override
    public void askReconnectionNickname(ReconnectionEvent RE) {

    }

    private boolean nicknameIsAvailable = true;
    @Override
    public void askNickname() {
        if(nicknameIsAvailable) {
            ViewSelector.sendToServer(new ViewControllerEventNickname(ViewModelGate.getMe()));
            nicknameIsAvailable = false;
        }
        else{
            //TODO
            //  show a pop up that ask for a new nickname
        }
    }

    @Override
    public void askPaymentInformation(SelectorEventPaymentInformation SEPaymentInformormation) {

    }

    @Override
    public void askPowerUpToUse(SelectorEventPowerUpCards powerUpCards) {

    }

    @Override
    public void wantToUsePowerUpOrNot() {

    }

    @Override
    public void askBotShoot(SelectorEventPlayers SEPlayers) {

    }

    @Override
    public void askTargetingScope(List<PowerUpCardV> listOfTargetingScopeV, List<Object> possiblePaymentsV, List<PlayerV> damagedPlayersV) {

    }

    @Override
    public void askTagBackGranade(List<PowerUpCardV> listOfTagBackGranade) {

    }
}
