package it.polimi.se2019.view.selector;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPaymentInformation;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPlayers;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPositions;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPowerUpCards;
import it.polimi.se2019.model.events.viewControllerEvents.*;
import it.polimi.se2019.view.GUIstarter;
import it.polimi.se2019.view.GameSceneController;
import it.polimi.se2019.view.components.*;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GUISelector implements SelectorV {

    private String networkConnection;

    private String hoverableCssClass = "weirdPadding";

    GUISelector(String networkConnection){
        this.networkConnection = networkConnection;
    }

    private GameSceneController getGameSceneController(){
        return (GameSceneController)GUIstarter.getStageController();
    }

    private void makeNodeHoverable(Node node){
        node.setOnMouseEntered(e-> ((Node)e.getSource()).setStyle("-fx-background-color: #ffb523"));
        node.setOnMouseExited(e-> ((Node)e.getSource()).setStyle("-fx-background-color: #0e1d24"));
    }

    private VBox getScrollContent(ScrollPane scrollPane){
        return (VBox)((AnchorPane)scrollPane.getContent()).getChildren().get(0);
    }

    private HBox buildHBoxRequestOfDoubleStackPanes(int amount){
        HBox hBox = new HBox();
        for (int i = 0; i < amount ; i++) {
            StackPane stackPaneBackground = new StackPane();
            StackPane stackPaneMainImage = new StackPane();
            stackPaneBackground.getChildren().add(stackPaneMainImage);

            HBox.setHgrow(stackPaneBackground, Priority.ALWAYS);

            hBox.getChildren().add(stackPaneBackground);
        }
        return hBox;
    }

    private VBox buildTitleWithContent(String title, Node content){
        VBox vBox = new VBox();
        StackPane stackPane = new StackPane(new Label(title));

        vBox.getChildren().addAll(stackPane,content);

        return vBox;
    }

    //##################################################################################################################
    @Override
    public void askGameSetUp(boolean canBot) {
        new Thread(new AskGameSetUp(canBot)).start();
    }
    private class AskGameSetUp implements Runnable{
        private boolean canBot;
        private String choosenMap = "map0";
        private boolean ifFinalFrenzy = false;
        private boolean isBot = false;
        private int numberOfSkulls = 5;
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
                    Thread.currentThread().interrupt();
                }
            }
            //ask Map, ask FF, ask Bot, ask number of skulls
            ScrollPane request = builRequest();
            Platform.runLater(()-> {
                getGameSceneController().changeSelectorSection(request, 0.0, 0.0, 0.0, 0.0);
                Platform.runLater(()->{
                    //request.setVvalue(0.0); don't know why it doesn't works
                });
            });
        }
        private ScrollPane buildBasicScrollPane(){
            ScrollPane scrollPane = new ScrollPane();
            AnchorPane requestContainer = new AnchorPane();
            VBox scrollContent = new VBox();

            scrollPane.setContent(requestContainer);
            requestContainer.getChildren().add(scrollContent);

            AnchorPane.setTopAnchor(scrollContent, 0.0);
            AnchorPane.setRightAnchor(scrollContent, 0.0);
            AnchorPane.setBottomAnchor(scrollContent, 0.0);
            AnchorPane.setLeftAnchor(scrollContent, 0.0);

            scrollPane.setFitToWidth(true);

            return scrollPane;
        }
        private ScrollPane builRequest(){
            ScrollPane request = buildBasicScrollPane();
            VBox scrollContent = getScrollContent(request);


            //building Nodes for the actuals requests:

            //title
            StackPane titleContainer = new StackPane();
            Label titleLabel = new Label("GAME SET UP");
            titleContainer.getChildren().add(titleLabel);
            scrollContent.getChildren().add(titleContainer);

            //map request
            HBox mapRequest = buildMapRequest();
            scrollContent.getChildren().add(mapRequest);

            //is Final frenzy
            HBox isFinalFrenzyRequest = isFinalFrenzyRequest();
            scrollContent.getChildren().add(isFinalFrenzyRequest);

            //is Bot
            HBox isBotRequest;
            if(canBot) {
                isBotRequest = isBotRequest();
                scrollContent.getChildren().add(isBotRequest);
            }

            //number of skulls
            VBox numberOfSkullsRequest = numberOfSkullsRequest();
            scrollContent.getChildren().add(numberOfSkullsRequest);

            StackPane doneButton = new StackPane(new Label("DONE"));
            makeNodeHoverable(doneButton);
            //EVENTS
            doneButton.setOnMouseClicked(e->{
                getGameSceneController().removeSelectorSection();
                getGameSceneController().sendToServer(new ViewControllerEventGameSetUp("normalMode", this.choosenMap, this.numberOfSkulls, this.ifFinalFrenzy, this.isBot));
            });
            doneButton.setPrefHeight(getGameSceneController().getSelectorSection().getHeight()/6);
            scrollContent.getChildren().add(doneButton);

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
                StackPane mapBackground = new StackPane(mapMainImage);

                mapMainImage.setId("map" + i);

                //EVENTS
                mapMainImage.setOnMouseClicked(e -> this.choosenMap = ((Node)e.getSource()).getId());
                makeNodeHoverable(mapMainImage);
                maps.add(mapBackground);
            }

            vBoxLeft.getChildren().addAll(maps.get(0), maps.get(2));
            vBoxRight.getChildren().addAll(maps.get(1), maps.get(3));

            VBox.setVgrow(maps.get(0), Priority.ALWAYS);
            VBox.setVgrow(maps.get(1), Priority.ALWAYS);
            VBox.setVgrow(maps.get(2), Priority.ALWAYS);
            VBox.setVgrow(maps.get(3), Priority.ALWAYS);

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
            hbox.getChildren().add(left);
            hbox.getChildren().add(center);
            hbox.getChildren().add(right);
            left.getChildren().add(label);
            center.getChildren().add(yes);
            right.getChildren().add(no);

            //PROPERTIES
            hbox.prefHeightProperty().bind(getGameSceneController().getSelectorSection().heightProperty().divide(4));
            yes.setToggleGroup(group);
            no.setToggleGroup(group);
            no.setSelected(true);
            HBox.setHgrow(left, Priority.ALWAYS);
            HBox.setHgrow(center, Priority.ALWAYS);
            HBox.setHgrow(right, Priority.ALWAYS);

            //EVENTS
            yes.setOnAction(e-> this.ifFinalFrenzy = true);
            no.setOnAction(e-> this.ifFinalFrenzy = false);

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
            yes.setOnAction(e-> this.isBot = true);
            no.setOnAction(e-> this.ifFinalFrenzy = false);

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
            VBox.setVgrow(hbox, Priority.ALWAYS);

            vbox.prefHeightProperty().bind(getGameSceneController().getSelectorSection().heightProperty().divide(2));

            //EVENTS
            for (int i = 0; i < radioButtons.size(); i++) {
                int value = i+5;
                radioButtons.get(i).setUserData(value);
                radioButtons.get(i).setOnAction(e-> this.numberOfSkulls = (Integer)((RadioButton)e.getSource()).getUserData());
            }

            return vbox;
        }
    }



    //##################################################################################################################
    /**
     * @deprecated old stuff, now no more used
     */
    @Deprecated
    @Override
    public void askPlayerSetUp() {
        //DEPRECATED
    }


    //##################################################################################################################
    @Override
    public void askFirstSpawnPosition(List<PowerUpCardV> powerUpCards, boolean spawnBot) {
        new Thread(new AskFirstSpawnPosition(powerUpCards, spawnBot)).start();
    }
    private class AskFirstSpawnPosition implements Runnable {
        private List<PowerUpCardV> powerUpCards;
        private boolean spawnBot;
        private String botSpawn;
        private String cardID;
        private AskFirstSpawnPosition(List<PowerUpCardV> powerUpCards, boolean spawnBot){
            this.powerUpCards = powerUpCards;
            this.spawnBot=spawnBot;

            this.botSpawn = "red";
            this.cardID=powerUpCards.get(0).getID();
        }
        @Override
        public void run() {
            VBox request = buildRequest();
            Platform.runLater(()-> getGameSceneController().changeSelectorSection(request, 0.0, 0.0, 0.0, 0.0));
        }
        private VBox buildRequest(){
            VBox request = new VBox();
            request.prefHeightProperty().bind(getGameSceneController().getSelectorSection().heightProperty());

            //BOT REQUEST
            if(spawnBot){
                StackPane title = new StackPane(new Label("Choose where you want to spawn the bot"));
                StackPane red = new StackPane(new Label("red"));
                StackPane blue = new StackPane(new Label("blue"));
                StackPane yellow = new StackPane(new Label("yellow"));

                request.getChildren().addAll(title, red, blue, yellow);


                makeNodeHoverable(red);
                makeNodeHoverable(blue);
                makeNodeHoverable(yellow);

                red.setOnMouseClicked(e-> this.botSpawn = "red");
                blue.setOnMouseClicked(e-> this.botSpawn = "blue");
                yellow.setOnMouseClicked(e-> this.botSpawn = "yellow");

                VBox.setVgrow(title, Priority.ALWAYS);
                VBox.setVgrow(red, Priority.ALWAYS);
                VBox.setVgrow(blue, Priority.ALWAYS);
                VBox.setVgrow(yellow, Priority.ALWAYS);
            }

            //QUESTION
            StackPane stackPane = new StackPane();
            Label question = new Label("What power up do you want to discard and spawn to?");
            stackPane.getChildren().add(question);

            request.getChildren().add(stackPane);

            //POWER UP REQUEST
            List<StackPane> powerUps = new ArrayList<>();
            for (PowerUpCardV powerUpCardV: this.powerUpCards) {
                StackPane stackPaneBackground = new StackPane();
                StackPane stackPaneMainImage = new StackPane();
                stackPaneMainImage.setUserData(powerUpCardV.getID());
                stackPaneBackground.getChildren().add(stackPaneMainImage);

                stackPaneMainImage.setOnMouseClicked(e-> this.cardID = (String)((StackPane)e.getSource()).getUserData());
                makeNodeHoverable(stackPaneMainImage);
                powerUps.add(stackPaneBackground);
            }

            HBox hBox = new HBox();
            for (StackPane powerUp:powerUps) {
                hBox.getChildren().add(powerUp);
                HBox.setHgrow(powerUp, Priority.ALWAYS);
            }
            hBox.prefHeightProperty().bind(getGameSceneController().getSelectorSection().heightProperty().divide(2));
            request.getChildren().add(hBox);

            //DONE BUTTON
            StackPane doneButton = new StackPane(new Label("done"));
            makeNodeHoverable(doneButton);
            doneButton.setOnMouseClicked(e->{
                getGameSceneController().removeSelectorSection();
                ViewControllerEventTwoString viewControllerEventTwoString = new ViewControllerEventTwoString(cardID, botSpawn);
                getGameSceneController().sendToServer(viewControllerEventTwoString);
            });
            VBox.setVgrow(doneButton,Priority.ALWAYS);
            request.getChildren().add(doneButton);

            return request;
        }

    }


    //##################################################################################################################
    @Override
    public void askTurnAction(int actionNumber, boolean canUsePowerUp, boolean canUseBot) {
        new Thread(new AskTurnAction(canUsePowerUp, canUseBot)).start();
    }

    private class AskTurnAction implements Runnable{
        private boolean canUsePowerUp;
        private boolean canUseBot;
        private String chosenAction;
        private AskTurnAction(boolean canUsePowerUp, boolean canUseBot){
            this.canUseBot = canUseBot;
            this.canUsePowerUp = canUsePowerUp;
        }
        @Override
        public void run(){
            VBox request = buildRequest();
            Platform.runLater(()-> getGameSceneController().changeSelectorSection(request, 0.0, 0.0, 0.0, 0.0));
        }
        private VBox buildRequest(){
            VBox request = new VBox();
            request.prefHeightProperty().bind(getGameSceneController().getSelectorSection().heightProperty());

            //possible requests
            List<String> requests = new ArrayList<>();
            if(canUseBot) {
                requests.add("use Bot");
            }
            if(canUsePowerUp) {
                requests.add("use power up");
            }
            requests.addAll(Arrays.asList("run around", "grab stuff", "shoot people"));

            for (String s:requests) {
                StackPane stackPane = new StackPane();
                Label label = new Label(s);

                request.getChildren().add(stackPane);
                stackPane.getChildren().add(label);

                stackPane.prefHeightProperty().bind(getGameSceneController().getSelectorSection().heightProperty().divide(requests.size()));

                stackPane.setUserData(s);

                VBox.setVgrow(stackPane, Priority.ALWAYS);

                stackPane.setOnMouseClicked(e->{
                    this.chosenAction = (String)((StackPane)e.getSource()).getUserData();

                    ViewControllerEventString viewControllerEventString = new ViewControllerEventString(this.chosenAction);

                    getGameSceneController().sendToServer(viewControllerEventString);

                    getGameSceneController().removeSelectorSection();
                });

                makeNodeHoverable(stackPane);
            }

            return request;
        }
    }


    //##################################################################################################################

    @Override
    public void askBotMove(SelectorEventPositions selectorEventPositions) {
        new Thread(new AskBotMove(selectorEventPositions.getPositions())).start();
    }
    private class AskBotMove implements Runnable{
        private List<Position> positions;
        private AskBotMove (List<Position> positions){
            this.positions=positions;
        }
        @Override
        public void run() {

            Platform.runLater(()->
                getGameSceneController().changeSelectorSection(
                        new StackPane(new Label("Choose where to move the bot in the map")),
                        0.0,0.0,0.0,0.0
                )
            );

            makeSquaresHoverableAndSendPositionEvent(positions);
        }
    }

    private void makeSquaresHoverableAndSendPositionEvent(List<Position> positions){
        StackPane[][] backgroundMap = getGameSceneController().getBackgroundsMap();
        for (Position pos : positions) {
            backgroundMap[pos.getX()][pos.getY()].getStyleClass().add(hoverableCssClass);
            backgroundMap[pos.getX()][pos.getY()].setOnMouseClicked(e->{
                ViewControllerEventPosition viewControllerEventPosition = new ViewControllerEventPosition(pos.getX(), pos.getY());
                getGameSceneController().sendToServer(viewControllerEventPosition);
                for (Position position : positions) {
                    backgroundMap[position.getX()][position.getY()].getStyleClass().remove(hoverableCssClass);
                }
                getGameSceneController().removeSelectorSection();
            });
        }
    }

    //##################################################################################################################
    @Override
    public void askRunAroundPosition(List<Position> positions) {
        new Thread(new AskRunAroundPosition(positions)).start();
    }
    private class AskRunAroundPosition implements Runnable{
        private List<Position> positions;
        private AskRunAroundPosition(List<Position> positions){
            this.positions = positions;
        }
        @Override
        public void run(){

            Platform.runLater(()->
                getGameSceneController().changeSelectorSection(
                        new StackPane(new Label("Choose where to move in the map")),
                        0.0,0.0,0.0,0.0
                )
            );

            makeSquaresHoverableAndSendPositionEvent(positions);

        }
    }


    //##################################################################################################################
    @Override
    public void askGrabStuffAction() {
        new Thread(new AskGrabStuffAction()).start();
    }
    private class AskGrabStuffAction implements Runnable{
        @Override
        public void run() {
            VBox request = buildRequest();
            Platform.runLater(()-> getGameSceneController().changeSelectorSection(request, 0.0, 0.0, 0.0, 0.0));
        }
        private VBox buildRequest(){
            //STRUCTURE
            VBox request = new VBox();
            StackPane stackPane1 = new StackPane(new Label("move and grab"));
            StackPane stackPane2 = new StackPane(new Label("grab where you are"));

            request.getChildren().addAll(stackPane1,stackPane2);

            //PROPERTIES
            VBox.setVgrow(stackPane1, Priority.ALWAYS);
            VBox.setVgrow(stackPane2, Priority.ALWAYS);
            makeNodeHoverable(stackPane1);
            makeNodeHoverable(stackPane2);

            //EVENTS
            stackPane1.setOnMouseClicked(e->{
                ViewControllerEventString viewControllerEventString = new ViewControllerEventString("move");
                getGameSceneController().removeSelectorSection();
                getGameSceneController().sendToServer(viewControllerEventString);
            });
            stackPane2.setOnMouseClicked(e->{
                ViewControllerEventString viewControllerEventString = new ViewControllerEventString("grab");
                getGameSceneController().removeSelectorSection();
                getGameSceneController().sendToServer(viewControllerEventString);
            });

            return request;
        }
    }


    //##################################################################################################################
    @Override
    public void askGrabStuffMove(List<Position> positions) {
        new Thread(new AskRunAroundPosition(positions)).start();
    }

    //##################################################################################################################
    @Override
    public void askGrabStuffGrabWeapon(List<WeaponCardV> toPickUp) {
        new Thread(new AskGrabStuffGrabWeapon(toPickUp)).start();
    }
    private class AskGrabStuffGrabWeapon implements Runnable{
        private List<WeaponCardV> toPickUp;
        private AskGrabStuffGrabWeapon(List<WeaponCardV> toPickUp){
            this.toPickUp= toPickUp;
        }
        @Override
        public void run() {
            VBox request = buildRequest();
            Platform.runLater(()-> getGameSceneController().changeSelectorSection(request, 0.0, 0.0, 0.0, 0.0));
        }
        private VBox buildRequest(){
            //STRUCTURE
            HBox hBox = buildHBoxRequestOfDoubleStackPanes(this.toPickUp.size());
            VBox vBox = buildTitleWithContent("Choose what weapon to pick up", hBox);

            //PROPERTIES
            VBox.setVgrow(hBox, Priority.ALWAYS);

            for (int i = 0; i < hBox.getChildren().size() ; i++) {
                StackPane mainStackPane = (StackPane)((StackPane)hBox.getChildren().get(i)).getChildren().get(0);
                makeNodeHoverable(mainStackPane);
                mainStackPane.setUserData(toPickUp.get(i).getID());
                //EVENTS
                mainStackPane.setOnMouseClicked(e->{
                    String toPickUpID = (String)((StackPane)e.getSource()).getUserData();
                    ViewControllerEventString viewControllerEventString = new ViewControllerEventString(toPickUpID);
                    getGameSceneController().removeSelectorSection();
                    getGameSceneController().sendToServer(viewControllerEventString);
                });
            }

            return vBox;
        }
    }

    //##################################################################################################################
    @Override
    public void askGrabStuffSwitchWeapon(List<WeaponCardV> toPickUp, List<WeaponCardV> toSwitch) {
        new Thread(new AskGrabStuffSwitchWeapon(toPickUp, toSwitch)).start();
    }
    private class AskGrabStuffSwitchWeapon implements Runnable{
        private List<WeaponCardV> toPickUp;
        private List<WeaponCardV> toSwitch;
        private String toPickUpID;
        private String toSwitchID;
        private AskGrabStuffSwitchWeapon(List<WeaponCardV> toPickUp, List<WeaponCardV> toSwitch){
            this.toPickUp = toPickUp;
            this.toSwitch = toSwitch;
            toPickUpID = toPickUp.get(0).getID();
            toSwitchID = toSwitch.get(0).getID();
        }
        @Override
        public void run() {
            VBox request = buildRequest();
            Platform.runLater(this::makeWeaponHoverable);
            Platform.runLater(()-> getGameSceneController().changeSelectorSection(request, 0.0, 0.0, 0.0, 0.0));
        }
        private VBox buildRequest(){
            //STRUCTURE
            HBox hBox = buildHBoxRequestOfDoubleStackPanes(this.toPickUp.size());
            VBox vBox = buildTitleWithContent("Choose one to pick up and one to discard from your hand", hBox);

            //PROPERTIES
            VBox.setVgrow(hBox, Priority.ALWAYS);

            //USER DATA
            for (int i = 0; i < hBox.getChildren().size() ; i++) {
                StackPane mainStackPane = (StackPane)((StackPane)hBox.getChildren().get(i)).getChildren().get(0);
                makeNodeHoverable(mainStackPane);
                mainStackPane.setUserData(toPickUp.get(i).getID());
                //EVENTSc
                mainStackPane.setOnMouseClicked(e-> this.toPickUpID = (String)((StackPane)e.getSource()).getUserData());
            }

            //done button
            StackPane doneButton = new StackPane(new Label("DONE"));
            makeNodeHoverable(doneButton);
            doneButton.setOnMouseClicked(e->{
                ViewControllerEventTwoString viewControllerEventTwoString = new ViewControllerEventTwoString(toPickUpID, toSwitchID);
                getGameSceneController().removeSelectorSection();
                unmakeWeaponHoverable();
                getGameSceneController().sendToServer(viewControllerEventTwoString);
            });
            vBox.getChildren().add(doneButton);

            return vBox;
        }
        private void makeWeaponHoverable(){
            for (StackPane weaponCardStackPane: getGameSceneController().getWeaponCardsMainImage()) {
                for (WeaponCardV weaponCardV:toSwitch) {
                    if(((WeaponCardV)weaponCardStackPane.getUserData()).getID().equals(weaponCardV.getID())){
                        weaponCardStackPane.getStyleClass().add(hoverableCssClass);
                        weaponCardStackPane.setOnMouseClicked(e-> this.toSwitchID = weaponCardV.getID());
                    }
                }
            }
        }

        private void unmakeWeaponHoverable(){
            for (StackPane weaponCardStackPane: getGameSceneController().getWeaponCardsMainImage()) {
                for (WeaponCardV weaponCardV:toSwitch) {
                    if(((WeaponCardV)weaponCardStackPane.getUserData()).getID().equals(weaponCardV.getID())){
                        weaponCardStackPane.getStyleClass().remove(hoverableCssClass);
                    }
                }
            }
        }
    }


    //##################################################################################################################
    @Override
    public void askPowerUpToDiscard(List<PowerUpCardV> toDiscard) {
        new Thread(new AskPowerUpToDiscard(toDiscard)).start();
    }
    private class AskPowerUpToDiscard implements Runnable{
        private List<PowerUpCardV> toDiscard;
        private AskPowerUpToDiscard (List<PowerUpCardV> toDiscard){
            this.toDiscard=toDiscard;
        }
        @Override
        public void run() {
            VBox request = buildRequest();
            Platform.runLater(()->getGameSceneController().changeSelectorSection(request, 0.0, 0.0, 0.0, 0.0));
        }
        private VBox buildRequest(){
            HBox hBox = buildHBoxRequestOfDoubleStackPanes(toDiscard.size());

            VBox vBox = buildTitleWithContent("Too many Power up cards, choose one o discard:", hBox);

            //PROPERTIES
            VBox.setVgrow(hBox,Priority.ALWAYS);

            //USER DATA
            for (int i = 0; i < hBox.getChildren().size() ; i++) {
                StackPane mainStackPane = (StackPane)((StackPane)hBox.getChildren().get(i)).getChildren().get(0);
                makeNodeHoverable(mainStackPane);
                mainStackPane.setUserData(i);
                //EVENTS
                mainStackPane.setOnMouseClicked(e->{
                    int chosen = (Integer) ((StackPane)e.getSource()).getUserData();
                    ViewControllerEventInt viewControllerEventInt = new ViewControllerEventInt(chosen);
                    getGameSceneController().sendToServer(viewControllerEventInt);
                    getGameSceneController().removeSelectorSection();
                });
            }

            return vBox;
        }
    }

    //##################################################################################################################
    @Override
    public void askWhatReaload(List<WeaponCardV> toReload) {
        new Thread(new AskWhatReaload(toReload)).start();
    }
    private class AskWhatReaload implements Runnable{
        private List<WeaponCardV> toReload;
        private AskWhatReaload (List<WeaponCardV> toReload){
            this.toReload = toReload;
        }
        @Override
        public void run() {
            VBox request = buildRequest();
            Platform.runLater(this::makeWeaponsHoverable);
            Platform.runLater(()->getGameSceneController().changeSelectorSection(request, 0.0, 0.0, 0.0, 0.0));
        }
        private VBox buildRequest(){
            VBox request = new VBox();
            StackPane stackPane = new StackPane(new Label("choose what weapon to reload from your hand"));
            request.getChildren().add(stackPane);

            StackPane skip = new StackPane(new Label("don't reload"));
            request.getChildren().add(skip);

            VBox.setVgrow(skip, Priority.ALWAYS);
            VBox.setVgrow(stackPane, Priority.ALWAYS);

            makeNodeHoverable(skip);

            skip.setOnMouseClicked(e->{
                String choice = "SKIP";
                getGameSceneController().sendToServer(new ViewControllerEventString(choice));
                getGameSceneController().removeSelectorSection();
            });

            return request;
        }
        private void makeWeaponsHoverable(){
            for (StackPane weaponCardStackPane: getGameSceneController().getWeaponCardsMainImage()) {
                for (WeaponCardV weaponCardV:toReload) {
                    if(((WeaponCardV)weaponCardStackPane.getUserData()).getID().equals(weaponCardV.getID())){
                        weaponCardStackPane.getStyleClass().add(hoverableCssClass);
                        weaponCardStackPane.setOnMouseClicked(e-> {
                            String chosenId = weaponCardV.getID();
                            getGameSceneController().sendToServer(new ViewControllerEventString(chosenId));
                            getGameSceneController().removeSelectorSection();
                        });
                    }
                }
            }
        }
    }

    //##################################################################################################################
    @Override
    public void askSpawn(List<PowerUpCardV> powerUpCards) {
        new Thread(new AskSpawn(powerUpCards)).start();
    }
    private class AskSpawn implements Runnable{
        private List<PowerUpCardV> powerUpCards;
        private AskSpawn (List<PowerUpCardV> powerUpCards){
            this.powerUpCards = powerUpCards;
        }
        @Override
        public void run() {
            VBox request = buildRequest();
            Platform.runLater(()->getGameSceneController().changeSelectorSection(request, 0.0,0.0,0.0,0.0));
        }
        private VBox buildRequest(){
            HBox hBox = buildHBoxRequestOfDoubleStackPanes(powerUpCards.size());
            VBox vBox = buildTitleWithContent("choose power up to discard and spawn to:", hBox);

            //USER DATA
            for (int i = 0; i < hBox.getChildren().size() ; i++) {
                StackPane mainStackPane = (StackPane)((StackPane)hBox.getChildren().get(i)).getChildren().get(0);
                //EVENTS
                mainStackPane.setOnMouseClicked(e->{
                    String chosenId = (String) ((StackPane)e.getSource()).getUserData();
                    getGameSceneController().removeSelectorSection();
                    getGameSceneController().sendToServer(new ViewControllerEventString(chosenId));
                });
                makeNodeHoverable(mainStackPane);
                mainStackPane.setUserData(powerUpCards.get(i).getID());
            }

            //PROPERTIES
            VBox.setVgrow(hBox, Priority.ALWAYS);

            return vBox;
        }
    }
    //##################################################################################################################
    @Override
    public void askShootOrMove() {
        new Thread(new AskShootOrMove()).start();
    }
    private class AskShootOrMove implements Runnable{
        @Override
        public void run() {
            VBox request = buildRequest();
            Platform.runLater(()->getGameSceneController().changeSelectorSection(request, 0.0,0.0,0.0,0.0));
        }
        private VBox buildRequest(){
            VBox vBox = new VBox();

            StackPane stackPane0 = new StackPane(new Label("WHat do you want to do:"));
            StackPane stackPane1 = new StackPane(new Label("Shoot"));
            StackPane stackPane2 = new StackPane(new Label("Move"));

            stackPane1.setUserData("shoot");
            stackPane2.setUserData("move");

            vBox.getChildren().addAll(stackPane0, stackPane1, stackPane2);


            List<StackPane> moveOrShootStackpanes = new ArrayList<>(Arrays.asList(stackPane1, stackPane2));
            for (StackPane stackPane : moveOrShootStackpanes) {
                makeNodeHoverable(stackPane);
                VBox.setVgrow(stackPane, Priority.ALWAYS);
                stackPane.setOnMouseClicked(e->{
                    ViewControllerEventString viewControllerEventString= new ViewControllerEventString((String)((StackPane)e.getSource()).getUserData());
                    getGameSceneController().removeSelectorSection();
                    getGameSceneController().sendToServer(viewControllerEventString);
                });
            }
            return vBox;
        }
    }
    //##################################################################################################################
    /**
     * @deprecated old stuff, now no more used
     */
    @Deprecated
    @Override
    public void askShootReloadMove() {
        //DEPRECATED
    }


    //##################################################################################################################
    @Override
    public void askWhatWep(List<WeaponCardV> loadedCardInHand) {new Thread(new AskWhatWep(loadedCardInHand)).start(); }
    private class AskWhatWep implements Runnable{
        private List<WeaponCardV> loadedCardInHand;
        private AskWhatWep (List<WeaponCardV> loadedCardInHand){
            this.loadedCardInHand=loadedCardInHand;
        }
        @Override
        public void run() {
            Platform.runLater(this::makeWeaponHoverable);
            VBox request = buildRequest();
            Platform.runLater(()->getGameSceneController().changeSelectorSection(request, 0.0,0.0,0.0,0.0));
        }
        private void makeWeaponHoverable(){
            for (StackPane weaponCardStackPane: getGameSceneController().getWeaponCardsMainImage()) {
                for (int i = 0; i < loadedCardInHand.size(); i++) {
                    WeaponCardV weaponCardV = loadedCardInHand.get(i);
                    if(((WeaponCardV)weaponCardStackPane.getUserData()).getID().equals(weaponCardV.getID())){
                        weaponCardStackPane.getStyleClass().add(hoverableCssClass);
                        String chosen = i+"";
                        weaponCardStackPane.setOnMouseClicked(e-> {
                            getGameSceneController().sendToServer(new ViewControllerEventString(chosen));
                            getGameSceneController().removeSelectorSection();
                        });
                    }
                }
            }
        }
        private VBox buildRequest(){
            return new VBox(new StackPane(new Label("chose what weapon to reload from your hand")));
        }

    }

    //##################################################################################################################
    @Override
    public void askWhatEffect(List<EffectV> possibleEffects) {
        new Thread(new AskWhatEffect(possibleEffects)).start();
    }
    private class AskWhatEffect implements Runnable{
        private List<EffectV> possibleEffects;
        private AskWhatEffect(List<EffectV> possibleEffects){
            this.possibleEffects=possibleEffects;
        }
        @Override
        public void run() {
            VBox request = buildRequest();
            Platform.runLater(()-> getGameSceneController().changeSelectorSection(request, 0.0, 0.0, 0.0, 0.0));
        }
        private VBox buildRequest(){
            VBox request = new VBox();

            for (int i = 0; i<possibleEffects.size(); i++) {
                EffectV effectV = possibleEffects.get(i);

                //STRUCTURE
                StackPane stackPane = new StackPane(new Label(effectV.getEffectName()));
                request.getChildren().add(stackPane);

                //PROPERTIES
                VBox.setVgrow(stackPane, Priority.ALWAYS);

                stackPane.setUserData(i);

                makeNodeHoverable(stackPane);

                //EVENT
                stackPane.setOnMouseClicked(e->{
                    Integer index = (Integer)((StackPane)e.getSource()).getUserData();

                    ViewControllerEventInt viewControllerEventInt = new ViewControllerEventInt(index);

                    getGameSceneController().removeSelectorSection();
                    getGameSceneController().sendToServer(viewControllerEventInt);
                });
            }

            return request;
        }
    }


    //##################################################################################################################
    @Override
    public void askEffectInputs(EffectInfoType inputType, List<Object> possibleInputs) {
        CLISelector cliSelector = new CLISelector(this.networkConnection);
        cliSelector.askEffectInputs(inputType, possibleInputs);
        //new Thread(new AskEffectInputs(inputType, possibleInputs)).start();
    }
    private class AskEffectInputs implements Runnable{
        private EffectInfoType inputType;
        private List<Object> possibleInputs;
        private AskEffectInputs (EffectInfoType inputType, List<Object> possibleInputs){
            this.inputType = inputType;
            this.possibleInputs = possibleInputs;
        }
        @Override
        public void run() {
            //TODO
        }
    }

    //##################################################################################################################
    @Override
    public void askReconnectionNickname(ReconnectionEvent reconnectionEvent) {
        Platform.runLater(()-> {
            Stage s = new Stage(StageStyle.UNDECORATED);

            VBox v = new VBox();
            v.setStyle("-fx-padding: 100px;-fx-border-style: solid;-fx-background-color: #0e1d24;-fx-border-color: #ffb523;-fx-border-width: 5px");

            Scene sc = new Scene(v);
            s.setTitle("RECONNECTION");
            s.setScene(sc);
            s.setResizable(false);
            s.centerOnScreen();

            Label l1 = new Label("what was the nickname you where playing with?");
            String l1Style = "-fx-padding: 10px;-fx-font-weight: bold;-fx-font-size: 22px;-fx-text-fill: #ffb523;-fx-font-family: monospace";
            l1.setStyle(l1Style);
            StackPane stackPane = new StackPane(l1);
            stackPane.setStyle("-fx-padding: 20px");

            v.getChildren().add(stackPane);

            HBox hBox = new HBox();

            List<String> nicknames = reconnectionEvent.getListOfAFKPlayers();
            for (String nickname: nicknames) {
                Label nicknameLabel = new Label(nickname);
                nicknameLabel.setStyle("-fx-background-color: #11353e");
                StackPane nicknameStackPane = new StackPane();
                nicknameStackPane.setStyle("-fx-padding: 35px");
                hBox.getChildren().add(nicknameStackPane);
                HBox.setHgrow(nicknameStackPane,Priority.ALWAYS);
                makeNodeHoverable(nicknameStackPane);
                nicknameStackPane.setUserData(nickname);
                nicknameStackPane.setOnMouseClicked(e->{
                    String nicknameChosen = (String) ((StackPane)e.getSource()).getUserData();
                    s.hide();
                    ArrayList<String> answer = new ArrayList<>();
                    answer.add(nicknameChosen);
                    ViewModelGate.setMe(nicknameChosen);
                    answer.add(this.networkConnection);
                    ReconnectionEvent reconnectEvent = new ReconnectionEvent(answer);
                    if(networkConnection.equalsIgnoreCase("RMI")){
                        reconnectEvent.setClient(Controller.getRmiNetworkHandler());
                    }
                    getGameSceneController().sendToServer(reconnectEvent);
                });
            }

            v.getChildren().add(hBox);

            VBox.setVgrow(l1,Priority.ALWAYS);
            VBox.setVgrow(hBox,Priority.ALWAYS);

            s.setAlwaysOnTop(true);
            s.show();
        });
    }

    //##################################################################################################################
    private boolean nicknameIsAvailable = true;
    @Override
    public void askNickname() {
        if(nicknameIsAvailable) {
            ViewSelector.sendToServer(new ViewControllerEventNickname(ViewModelGate.getMe()));
            nicknameIsAvailable = false;
        }
        else{
            Platform.runLater(()-> {
                Stage s = new Stage();

                VBox v = new VBox();
                v.setStyle("-fx-padding: 20px;-fx-border-style: solid;-fx-background-color: #0e1d24;-fx-border-color: #ffb523;-fx-border-width: 5px");

                Scene sc = new Scene(v);
                s.setTitle("NEW NICKNAME");
                s.setScene(sc);
                s.setResizable(false);
                s.centerOnScreen();

                Label l1 = new Label("Sorry, your nickname is already taken, please choose another one");
                l1.setStyle("-fx-padding: 10px;-fx-font-weight: bold;-fx-font-size: 22px;-fx-text-fill: #ffb523;-fx-font-family: monospace");

                v.getChildren().add(l1);

                HBox hBox = new HBox();

                TextField textField = new TextField();
                String textFieldStyle="-fx-text-fill: #ffb523;-fx-background-color: transparent;-fx-font-weight: bold;-fx-font: 18px monospace;-fx-padding:20px;-fx-border-style: solid;-fx-border-color: #ffb523;-fx-border-width: 0px 0px 1px 0px;-fx-border-radius: 1px";
                textField.setStyle(textFieldStyle);
                textField.setOnMouseEntered(e->{
                    String styleToAdd = ";-fx-border-width: 0px 0px 3px 0px;-fx-background-color: #11353e";
                    ((TextField)e.getSource()).setStyle(textFieldStyle+styleToAdd);
                });
                String onFocusToAdd=";-fx-border-width: 0px 0px 3px 0px;-fx-background-color: #11353e";
                textField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
                    if (newPropertyValue) {
                        textField.setStyle(textFieldStyle+onFocusToAdd);
                    }
                    else {
                        textField.setStyle(textFieldStyle);
                    }
                });
                textField.setOnMouseExited(e->{
                    if(!((TextField)e.getSource()).isFocused()) {
                        ((TextField) e.getSource()).setStyle(textFieldStyle);
                    }
                });

                Button button = new Button("SUBMIT");
                String buttonStyle="-fx-font-weight: bold;-fx-font-size: 16px;-fx-text-fill: #ffb523;-fx-border-color: #ffb523;-fx-border-radius: 40px;-fx-border-width: 3px;-fx-padding: 20px;-fx-background-color: #0e1d24;-fx-background-radius: 40px";
                button.setStyle(buttonStyle);
                button.setOnMouseEntered(e->{
                    String styleToAdd = ";-fx-background-color: #ffb523;-fx-text-fill: #212121";
                    ((Button)e.getSource()).setStyle(buttonStyle+styleToAdd);
                });
                button.setOnMouseExited(e-> ((Button)e.getSource()).setStyle(buttonStyle));
                button.setOnMouseClicked(e->{
                    String newNickname = textField.getText();
                    ViewModelGate.setMe(newNickname);
                    s.hide();
                    new Thread(()-> ViewSelector.sendToServer(new ViewControllerEventNickname(ViewModelGate.getMe()))).start();
                });

                StackPane stackPane1 = new StackPane(textField);
                StackPane stackPane2 = new StackPane(button);
                stackPane1.setStyle("-fx-padding: 30px");
                stackPane2.setStyle("-fx-padding: 31px");

                stackPane1.setAlignment(Pos.CENTER_RIGHT);
                stackPane2.setAlignment(Pos.CENTER_LEFT);

                HBox.setHgrow(stackPane1, Priority.ALWAYS);
                HBox.setHgrow(stackPane2, Priority.ALWAYS);

                hBox.getChildren().addAll(stackPane1, stackPane2);

                v.getChildren().add(hBox);

                VBox.setVgrow(l1,Priority.ALWAYS);
                VBox.setVgrow(hBox,Priority.ALWAYS);

                s.show();
            });
        }
    }


    //##################################################################################################################
    @Override
    public void askPaymentInformation(SelectorEventPaymentInformation selectorEventPaymentInformation) {
        List<AmmoCubesV> amountToPay = selectorEventPaymentInformation.getAmount().getAmmoCubesList();
        boolean canPayWithoutPowerUps = selectorEventPaymentInformation.canPayWithoutPowerUps();
        List<PowerUpCardV> possibilities = selectorEventPaymentInformation.getPossibilities();

        new Thread(new AskPaymentInformation(amountToPay, canPayWithoutPowerUps, possibilities, new ArrayList<>())).start();
    }
    private class AskPaymentInformation implements Runnable{
        private List<AmmoCubesV> amountToPay;
        private boolean canPayWithoutPowerUps;
        private List<PowerUpCardV> possibilities;
        private ArrayList<Object> answer;
        private AskPaymentInformation(List<AmmoCubesV> amountToPay, boolean canPayWithoutPowerUps, List<PowerUpCardV> possibilities, ArrayList<Object> answer){
            this.amountToPay = amountToPay;
            this.canPayWithoutPowerUps = canPayWithoutPowerUps;
            this.possibilities = possibilities;
            this.answer = answer;
        }
        @Override
        public void run() {
            VBox request = buildRequest();
            Platform.runLater(()-> getGameSceneController().changeSelectorSection(request, 0.0, 0.0, 0.0, 0.0));
        }
        private VBox buildRequest(){
            VBox request = new VBox();

            VBox toPay = buildToPay();
            request.getChildren().add(toPay);

            VBox options= buildOptions();
            request.getChildren().add(options);
            VBox.setVgrow(options, Priority.ALWAYS);

            return request;
        }
        private VBox buildToPay(){
            VBox vBox= new VBox(new StackPane(new Label("amount to pay")));
            for (AmmoCubesV ammo:this.amountToPay) {
                HBox hBox = buildHBoxRequestOfDoubleStackPanes(3);
                for (int i = 0; i<ammo.getQuantity(); i++) {
                    //set styles
                    StackPane stackPaneMainImage = (StackPane)((StackPane) hBox.getChildren().get(i)).getChildren().get(0);
                    if(ammo.getColor().equals(AmmoCubesColor.red)){
                        //setStyle
                        stackPaneMainImage.setStyle("-fx-background-color: red");
                    }
                    else if (ammo.getColor().equals(AmmoCubesColor.blue)){
                        //setStyle
                        stackPaneMainImage.setStyle("-fx-background-color: blue");
                    }
                    else{ //yellow
                        //setStyle
                        stackPaneMainImage.setStyle("-fx-background-color: yellow");
                    }
                }
                hBox.prefHeightProperty().bind(getGameSceneController().getSelectorSection().heightProperty().divide(3));
                vBox.getChildren().add(hBox);
            }
            return vBox;
        }
        private VBox buildOptions(){

            VBox vBox = new VBox();
            StackPane stackPane = new StackPane(new Label("option for paying"));

            if(canPayWithoutPowerUps){
                canPayWithoutPowerUps(vBox, stackPane);
            }

            for (PowerUpCardV p : possibilities) {
                StackPane possibility = new StackPane(new Label("discard " + p.getName()));
                possibility.setUserData(p);
                vBox.getChildren().add(possibility);
                stackPane.prefHeightProperty().bind(getGameSceneController().getSelectorSection().heightProperty().divide(5));
                makeNodeHoverable(possibility);
                possibility.setOnMouseClicked(e->{
                    AmmoCubesColor colorOfTheChosenPowerUp = ((PowerUpCardV)((StackPane)e.getSource()).getUserData()).getColor();
                    answer.add(((StackPane)e.getSource()).getUserData());
                    //subtract one unit of the color of the power up chosen from the total cost to pay
                    //also deletes the power up cards from the possibilities
                    adjustAmountToPayAndPossibilities(colorOfTheChosenPowerUp);

                    //check stuff to do next:
                    //      if don't have possibilities, end
                    //      if have to pay anything else repeat
                    if( (possibilities.isEmpty()) || (amountToPay.isEmpty()) ){
                        //ens, send to server and remove request
                        ViewControllerEventPaymentInformation viewControllerEventPaymentInformation = new ViewControllerEventPaymentInformation(answer);
                        getGameSceneController().removeSelectorSection();
                        getGameSceneController().sendToServer(viewControllerEventPaymentInformation);
                    }
                    else{
                        //repeat: close the current request, and launch another request with the new possibilities and the current answer
                        getGameSceneController().removeSelectorSection();
                        new Thread(new AskPaymentInformation(amountToPay, canPayWithoutPowerUps, possibilities, answer)).start();
                    }
                });
            }

            for (Node n:vBox.getChildren()) {
                VBox.setVgrow(n,Priority.ALWAYS);
            }
            return vBox;
        }
        private void canPayWithoutPowerUps(VBox vBox, StackPane stackPane){
            StackPane possibility = new StackPane(new Label("use ammos from your Ammo box"));
            vBox.getChildren().add(possibility);
            stackPane.prefHeightProperty().bind(getGameSceneController().getSelectorSection().heightProperty().divide(5));
            makeNodeHoverable(possibility);
            possibility.setOnMouseClicked(e->{
                //end, send event, close request
                ViewControllerEventPaymentInformation viewControllerEventPaymentInformation = new ViewControllerEventPaymentInformation(answer);
                getGameSceneController().removeSelectorSection();
                getGameSceneController().sendToServer(viewControllerEventPaymentInformation);
            });
        }
        private void adjustAmountToPayAndPossibilities(AmmoCubesColor colorOfTheChosenPowerUp){
            for (AmmoCubesV ammo: amountToPay) {
                if(ammo.getColor().equals(colorOfTheChosenPowerUp)){
                    ammo.setQuantity(ammo.getQuantity()-1);
                    if(ammo.getQuantity()<=0){
                        //remove the color from the amount to pay
                        amountToPay.remove(ammo);

                        //delete All the power up card with the same color as the one of the power up chosen from the possibilities
                        possibilities.removeIf(element -> element.getColor().equals(colorOfTheChosenPowerUp));

                    }
                    break;
                }
            }
        }
    }


    //##################################################################################################################
    @Override
    public void askPowerUpToUse(SelectorEventPowerUpCards selectorEventPowerUpCards) {
        List<PowerUpCardV> powerUpCardsV = selectorEventPowerUpCards.getPowerUpCards();
        new Thread(new AkPowerUpToUse(powerUpCardsV)).start();
    }
    private class AkPowerUpToUse implements Runnable{
        private List<PowerUpCardV> powerUpCardsV;
        private AkPowerUpToUse (List<PowerUpCardV> powerUpCardsV){
            this.powerUpCardsV = powerUpCardsV;
        }
        @Override
        public void run(){
            VBox request = buildRequest();
            Platform.runLater(this::makePowerUpHoverable);
            Platform.runLater(()-> getGameSceneController().changeSelectorSection(request, 0.0, 0.0, 0.0, 0.0));
        }
        private VBox buildRequest(){
            return new VBox(new StackPane(new Label("choose the power up to use from your hand")));
        }
        private void makePowerUpHoverable(){
            List<StackPane> powerUpCardsMainImages = getGameSceneController().getListOfPowerUpCardsMainImage();
            for (StackPane stackPanePowerUp: powerUpCardsMainImages) {
                for (PowerUpCardV powerUpCardV : powerUpCardsV) {
                    if (((PowerUpCardV) stackPanePowerUp.getUserData()).getID().equals(powerUpCardV.getID())) {
                        stackPanePowerUp.getStyleClass().add(hoverableCssClass);
                        stackPanePowerUp.setOnMouseClicked(e -> {
                            String answer1 = ((PowerUpCardV) stackPanePowerUp.getUserData()).getName();
                            String answer2 = ((PowerUpCardV) stackPanePowerUp.getUserData()).getColor() + "";
                            getGameSceneController().sendToServer(new ViewControllerEventTwoString(answer1, answer2));
                            getGameSceneController().removeSelectorSection();
                        });
                    }
                }
            }
        }
    }

    //##################################################################################################################
    @Override
    public void wantToUsePowerUpOrNot(){
        new Thread(new AskWantToUsePowerUpOrNot()).start();
    }

    private class AskWantToUsePowerUpOrNot implements Runnable{
        @Override
        public  void run(){
            VBox request = buildRequest();
            Platform.runLater(()-> getGameSceneController().changeSelectorSection(request, 0.0, 0.0, 0.0, 0.0));
        }
        private VBox buildRequest(){

            VBox request = new VBox();

            StackPane title = new StackPane(new Label("Want to use a Power Up?"));
            StackPane stackPane1 = new StackPane(new Label("yes"));
            StackPane stackPane2 = new StackPane(new Label("no"));

            request.getChildren().addAll(title,stackPane1,stackPane2);

            VBox.setVgrow(stackPane1,Priority.ALWAYS);
            VBox.setVgrow(stackPane2, Priority.ALWAYS);

            makeNodeHoverable(stackPane1);
            makeNodeHoverable(stackPane2);

            //EVENTS
            stackPane1.setOnMouseClicked(e-> {
                ViewControllerEventBoolean viewControllerEventBoolean = new ViewControllerEventBoolean(true);
                getGameSceneController().removeSelectorSection();
                getGameSceneController().sendToServer(viewControllerEventBoolean);
            });
            stackPane2.setOnMouseClicked(e-> {
                ViewControllerEventBoolean viewControllerEventBoolean = new ViewControllerEventBoolean(false);
                getGameSceneController().removeSelectorSection();
                getGameSceneController().sendToServer(viewControllerEventBoolean);
            });

            return request;

        }
    }


    //##################################################################################################################
    @Override
    public void askBotShoot(SelectorEventPlayers selectorEventPlayers) {
        CLISelector cliSelector= new CLISelector(networkConnection);
        cliSelector.askBotShoot(selectorEventPlayers);
        //TODO
        //List<PlayerV> playersV = selectorEventPlayers.getPlayerVList();
        //new Thread(new AskBotShoot(playersV)).start();
    }
    private class AskBotShoot implements Runnable{
        private List<PlayerV> playersV;
        private AskBotShoot (List<PlayerV> playersV){
            this.playersV = playersV;
        }
        @Override
        public void run(){

        }
    }

    //##################################################################################################################
    @Override
    public void askTargetingScope(List<PowerUpCardV> listOfTargetingScopeV, List<Object> possiblePaymentsV, List<PlayerV> damagedPlayersV) {
        CLISelector cliSelector= new CLISelector(networkConnection);
        cliSelector.askTargetingScope(listOfTargetingScopeV, possiblePaymentsV, damagedPlayersV);
        //TODO
        //new Thread(new AskTargetingScope(listOfTargetingScopeV, possiblePaymentsV, damagedPlayersV)).start();
    }
    private class AskTargetingScope implements Runnable{
        private List<PowerUpCardV> listOfTargetingScopeV;
        private List<Object> possiblePaymentsV;
        private List<PlayerV> damagedPlayersV;
        private AskTargetingScope (List<PowerUpCardV> listOfTargetingScopeV, List<Object> possiblePaymentsV, List<PlayerV> damagedPlayersV){
            this.listOfTargetingScopeV = listOfTargetingScopeV;
            this.possiblePaymentsV = possiblePaymentsV;
            this.damagedPlayersV = damagedPlayersV;
        }
        @Override
        public void run(){
        }
    }

    //##################################################################################################################
    @Override
    public void askTagBackGranade(List<PowerUpCardV> listOfTagBackGranade) {
        CLISelector cliSelector= new CLISelector(networkConnection);
        cliSelector.askTagBackGranade(listOfTagBackGranade);
        //TODO
        //new Thread(new AskTagBackGranade(listOfTagBackGranade)).start();
    }
    private class AskTagBackGranade implements Runnable{
        private List<PowerUpCardV> listOfTagBackGranade;
        private AskTagBackGranade (List<PowerUpCardV> listOfTagBackGranade){
            this.listOfTagBackGranade = listOfTagBackGranade;
        }
        @Override
        public void run(){

        }
    }
}
