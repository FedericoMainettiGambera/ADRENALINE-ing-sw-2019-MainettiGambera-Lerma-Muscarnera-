package it.polimi.se2019.view.selector;

import it.polimi.se2019.model.Position;
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
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GUISelector implements SelectorV {

    private String networkConnection;

    GUISelector(String networkConnection){
        this.networkConnection = networkConnection;
    }

    private GameSceneController getGameSceneController(){
        return (GameSceneController)GUIstarter.getStageController();
    }

    private void makeNodeHoverable(Node node){
        node.setOnMouseEntered(e->{
            ((Node)e.getSource()).setStyle("-fx-background-color: #ffb523");
        });
        node.setOnMouseExited(e->{
            ((Node)e.getSource()).setStyle("-fx-background-color: #0e1d24");
        });
    }

    private StackPane doneButton(){
        //ELEMENTS
        StackPane stackPane = new StackPane();
        Button button = new Button("DONE");

        makeNodeHoverable(button);

        //STRUCTURE
        stackPane.getChildren().add(button);

        //PROPERTIES
        stackPane.prefHeightProperty().bind(getGameSceneController().getSelectorSection().heightProperty().divide(4));

        return stackPane;
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

    private VBox getScrollContent(ScrollPane scrollPane){
        return (VBox)((AnchorPane)scrollPane.getContent()).getChildren().get(0);
    }

    private void fillScrollContent(List<Node> nodes, VBox scrollContent){
        for (Node n: nodes) {
            scrollContent.getChildren().add(n);
        }
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
                }
            }
            //ask Map, ask FF, ask Bot, ask number of skulls
            ScrollPane request = builRequest();
            Platform.runLater(()-> {
                getGameSceneController().changeSelectorSection(request, 0.0, 0.0, 0.0, 0.0);
                Platform.runLater(()->{
                    //TODO:
                    //request.setVvalue(0.0);
                    //don't know why it doesn't works
                });
            });
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
            HBox isBotRequest = null;
            if(canBot) {
                isBotRequest = isBotRequest();
                scrollContent.getChildren().add(isBotRequest);
            }

            //number of skulls
            VBox numberOfSkullsRequest = numberOfSkullsRequest();
            scrollContent.getChildren().add(numberOfSkullsRequest);

            StackPane doneButton = doneButton();
            //EVENTS
            ((Button)doneButton.getChildren().get(0)).setOnAction(e->{
                getGameSceneController().removeSelectorSection();
                System.out.println("DONE");
                getGameSceneController().sendToServer(new ViewControllerEventGameSetUp("normalMode", this.choosenMap, this.numberOfSkulls, this.ifFinalFrenzy, this.isBot));
            });
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
                mapMainImage.setOnMouseClicked(e -> {
                    this.choosenMap = ((Node)e.getSource()).getId();
                    System.out.println(((Node)e.getSource()).getId());
                });
                makeNodeHoverable(mapMainImage);
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
                this.ifFinalFrenzy = true;
                System.out.println("true");
            });
            no.setOnAction(e-> {
                this.ifFinalFrenzy = false;
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
                this.isBot = true;
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
    }


    //##################################################################################################################
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
    private class AskFirstSpawnPosition extends Thread {
        private List<PowerUpCardV> powerUpCards;
        private boolean spawnBot;
        private String botSpawn;
        private String cardID;
        public  AskFirstSpawnPosition(List<PowerUpCardV> powerUpCards, boolean spawnBot){
            this.powerUpCards = powerUpCards;
            this.spawnBot=spawnBot;

            this.botSpawn = "red";
            this.cardID=powerUpCards.get(0).getID();
        }
        @Override
        public void run() {
            ScrollPane request = buildRequest();
            Platform.runLater(()-> {
                getGameSceneController().changeSelectorSection(request, 0.0, 0.0, 0.0, 0.0);
            });
        }
        private ScrollPane buildRequest(){
            ScrollPane request = buildBasicScrollPane();
            VBox scrollContent = getScrollContent(request);

            //BOT REQUEST
            if(spawnBot){
                VBox vBox = new VBox();
                StackPane title = new StackPane(new Label("Choose where you want to spawn the bot"));
                StackPane red = new StackPane(new Label("red"));
                StackPane blue = new StackPane(new Label("blue"));
                StackPane yellow = new StackPane(new Label("yellow"));
                vBox.getChildren().add(title);
                vBox.getChildren().add(red);
                vBox.getChildren().add(blue);
                vBox.getChildren().add(yellow);
                scrollContent.getChildren().add(vBox);
                VBox.setVgrow(title, Priority.ALWAYS);
                VBox.setVgrow(red, Priority.ALWAYS);
                VBox.setVgrow(blue, Priority.ALWAYS);
                VBox.setVgrow(yellow, Priority.ALWAYS);
                makeNodeHoverable(red);
                makeNodeHoverable(blue);
                makeNodeHoverable(yellow);
                vBox.prefHeightProperty().bind(getGameSceneController().getSelectorSection().heightProperty());

                red.setOnMouseClicked(e->{
                    this.botSpawn = "red";
                    System.out.println("red");
                });
                blue.setOnMouseClicked(e->{
                    this.botSpawn = "blue";
                    System.out.println("blue");
                });
                yellow.setOnMouseClicked(e->{
                    this.botSpawn = "yellow";
                    System.out.println("yellow");
                });
            }

            //QUESTION
            StackPane stackPane = new StackPane();
            Label question = new Label("What power up do you want to discard and spawn to?");
            stackPane.getChildren().add(question);
            stackPane.prefHeightProperty().bind(getGameSceneController().getSelectorSection().heightProperty().divide(6));
            scrollContent.getChildren().add(stackPane);

            //POWER UP REQUEST
            List<StackPane> powerUps = new ArrayList<>();
            for (PowerUpCardV powerUpCardV: this.powerUpCards) {
                StackPane stackPaneBackground = new StackPane();
                StackPane stackPaneMainImage = new StackPane();
                stackPaneMainImage.setUserData(powerUpCardV.getID());
                stackPaneBackground.getChildren().add(stackPaneMainImage);

                stackPaneMainImage.setOnMouseClicked(e->{
                    this.cardID = (String)((StackPane)e.getSource()).getUserData();
                    System.out.println("ID:" + this.cardID);
                });
                makeNodeHoverable(stackPaneMainImage);
                powerUps.add(stackPaneBackground);
            }

            HBox hBox = new HBox();
            for (StackPane powerUp:powerUps) {
                hBox.getChildren().add(powerUp);
                HBox.setHgrow(powerUp, Priority.ALWAYS);
            }
            hBox.prefHeightProperty().bind(getGameSceneController().getSelectorSection().heightProperty());
            scrollContent.getChildren().add(hBox);

            for (Node n: scrollContent.getChildren()) {
                VBox.setVgrow(n, Priority.ALWAYS);
            }

            //DONE BUTTON
            StackPane doneButton = doneButton();
            ((Button)doneButton.getChildren().get(0)).setOnAction(e->{
                getGameSceneController().removeSelectorSection();
                ViewControllerEventTwoString viewControllerEventTwoString = new ViewControllerEventTwoString(cardID, botSpawn);
                getGameSceneController().sendToServer(viewControllerEventTwoString);
                System.out.println("DONE");
            });
            scrollContent.getChildren().add(doneButton);

            return request;
        }

    }


    //##################################################################################################################
    @Override
    public void askTurnAction(int actionNumber, boolean canUsePowerUp, boolean canUseBot) {
        new Thread(new AskTurnAction(canUsePowerUp, canUseBot)).start();
    }

    private class AskTurnAction extends Thread{
        private boolean canUsePowerUp;
        private boolean canUseBot;
        private String chosenAction;
        private AskTurnAction(boolean canUsePowerUp, boolean canUseBot){
            this.canUseBot = canUseBot;
            this.canUsePowerUp = canUsePowerUp;
        }
        @Override
        public void run(){
            ScrollPane request = buildRequest();
            Platform.runLater(()-> {
                getGameSceneController().changeSelectorSection(request, 0.0, 0.0, 0.0, 0.0);
            });
        }
        private ScrollPane buildRequest(){
            ScrollPane request = buildBasicScrollPane();
            VBox scrollContent = getScrollContent(request);

            //possible requests
            List<String> requests = new ArrayList<>(Arrays.asList("run around", "grab stuff", "shoot people"));
            if(canUseBot) {
                requests.add("use Bot");
            }
            if(canUsePowerUp) {
                requests.add("use power up");
            }

            for (String s:requests) {
                StackPane stackPane = new StackPane();
                Label label = new Label(s);

                scrollContent.getChildren().add(stackPane);
                stackPane.getChildren().add(label);

                stackPane.prefHeightProperty().bind(getGameSceneController().getSelectorSection().heightProperty().divide(requests.size()));

                stackPane.setUserData(s);

                VBox.setVgrow(stackPane, Priority.ALWAYS);

                stackPane.setOnMouseClicked(e->{
                    this.chosenAction = (String)((StackPane)e.getSource()).getUserData();

                    System.out.println(this.chosenAction);

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
        public void run (){

        }
        private StackPane buildRequest(){
            //TODO
            return new StackPane();
        }
    }

    //##################################################################################################################
    @Override
    public void askRunAroundPosition(List<Position> positions) {
        new Thread(new AskRunAroundPosition(positions)).start();
    }
    private class AskRunAroundPosition extends Thread{
        private List<Position> positions;
        private AskRunAroundPosition(List<Position> positions){
            this.positions = positions;
        }
        @Override
        public void run(){
            ScrollPane request = buildRequest();
            Platform.runLater(()-> getGameSceneController().changeSelectorSection(request, 0.0, 0.0, 0.0, 0.0));
        }
        private ScrollPane buildRequest(){
            ScrollPane request = buildBasicScrollPane();
            VBox scrollContent = getScrollContent(request);

            StackPane stackPane = new StackPane(new Label("Choose where to move: "));

            stackPane.prefHeightProperty().bind(getGameSceneController().getSelectorSection().heightProperty());

            scrollContent.getChildren().add(stackPane);

            //TODO
            //per ora forzo la prima posizione
            System.out.println("forcing position: " + this.positions.get(0).humanString());
            positionChosed(this.positions.get(0).getX(), this.positions.get(0).getY());
            Platform.runLater(()->getGameSceneController().removeSelectorSection());
            //quindi sostituisci queste tre righe

            return request;
        }
        private void positionChosed(int x, int y){
            ViewControllerEventPosition viewControllerEventPosition = new ViewControllerEventPosition(x,y);

            getGameSceneController().sendToServer(viewControllerEventPosition);
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
            StackPane stackPane1 = new StackPane(new Label("move to another position and grab there"));
            StackPane stackPane2 = new StackPane(new Label("grab where you are without moving"));

            request.getChildren().addAll(stackPane1,stackPane2);

            //PROPERTIES
            stackPane1.setUserData("move");
            stackPane2.setUserData("grab");
            stackPane1.prefHeightProperty().bind(getGameSceneController().getSelectorSection().heightProperty());
            stackPane2.prefHeightProperty().bind(getGameSceneController().getSelectorSection().heightProperty());
            VBox.setVgrow(stackPane1, Priority.ALWAYS);
            VBox.setVgrow(stackPane2, Priority.ALWAYS);
            makeNodeHoverable(stackPane1);
            makeNodeHoverable(stackPane2);

            //EVENTS
            stackPane1.setOnMouseClicked(e->{
                ViewControllerEventString viewControllerEventString = new ViewControllerEventString((String)((StackPane)e.getSource()).getUserData());
                getGameSceneController().removeSelectorSection();
                getGameSceneController().sendToServer(viewControllerEventString);
            });
            stackPane2.setOnMouseClicked(e->{
                ViewControllerEventString viewControllerEventString = new ViewControllerEventString((String)((StackPane)e.getSource()).getUserData());
                getGameSceneController().removeSelectorSection();
                getGameSceneController().sendToServer(viewControllerEventString);
            });

            return request;
        }
    }


    //##################################################################################################################
    @Override
    public void askGrabStuffMove(List<Position> positions) {

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
            HBox request = buildRequest();
            Platform.runLater(()-> getGameSceneController().changeSelectorSection(request, 0.0, 0.0, 0.0, 0.0));
        }
        private HBox buildRequest(){
            //STRUCTURE
            HBox hBox = new HBox();

            //OTHERS
            for (WeaponCardV weaponCardV: toPickUp) {
                StackPane stackPaneBackground = new StackPane();
                StackPane stackPaneMainImage = new StackPane();
                stackPaneBackground.getChildren().add(stackPaneMainImage);

                stackPaneMainImage.setUserData(weaponCardV.getID());

                makeNodeHoverable(stackPaneMainImage);

                HBox.setHgrow(stackPaneBackground, Priority.ALWAYS);

                hBox.getChildren().add(stackPaneBackground);

                //EVENTS
                stackPaneMainImage.setOnMouseClicked(e->{
                    String toPickUp = (String)((StackPane)e.getSource()).getUserData();
                    System.out.println("ID: " + toPickUp);
                    ViewControllerEventString viewControllerEventString = new ViewControllerEventString(toPickUp);
                    getGameSceneController().sendToServer(viewControllerEventString);
                    getGameSceneController().removeSelectorSection();
                });

            }

            //PROPERTIES
            hBox.prefHeightProperty().bind(getGameSceneController().getSelectorSection().heightProperty());

            return hBox;
        }
    }

    //##################################################################################################################
    @Override
    public void askGrabStuffSwitchWeapon(List<WeaponCardV> toPickUp, List<WeaponCardV> toSwitch) {

    }


    //##################################################################################################################
    @Override
    public void askPowerUpToDiscard(List<PowerUpCardV> toDiscard) {

    }


    //##################################################################################################################
    @Override
    public void askWhatReaload(List<WeaponCardV> toReload) {

    }


    //##################################################################################################################
    @Override
    public void askSpawn(List<PowerUpCardV> powerUpCards) {

    }


    //##################################################################################################################
    @Override
    public void askShootOrMove() {

    }


    //##################################################################################################################
    @Deprecated
    @Override
    public void askShootReloadMove() {

    }


    //##################################################################################################################
    @Override
    public void askWhatWep(List<WeaponCardV> loadedCardInHand) {

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
            ScrollPane request = buildRequest();
            Platform.runLater(()-> getGameSceneController().changeSelectorSection(request, 0.0, 0.0, 0.0, 0.0));
        }
        private ScrollPane buildRequest(){
            ScrollPane request = buildBasicScrollPane();
            VBox scrollContent = getScrollContent(request);

            for (int i = 0; i<possibleEffects.size(); i++) {
                EffectV effectV = possibleEffects.get(i);

                //STRUCTURE
                StackPane stackPane = new StackPane(new Label(effectV.getEffectName()));
                scrollContent.getChildren().add(stackPane);

                //PROPERTIES
                stackPane.prefHeightProperty().bind(getGameSceneController().getSelectorSection().heightProperty().divide(possibleEffects.size()));
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

    }


    //##################################################################################################################
    @Override
    public void askReconnectionNickname(ReconnectionEvent RE) {

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
            //TODO
            //  show a pop up that ask for a new nickname
        }
    }


    //##################################################################################################################
    @Override
    public void askPaymentInformation(SelectorEventPaymentInformation SEPaymentInformormation) {

    }


    //##################################################################################################################
    @Override
    public void askPowerUpToUse(SelectorEventPowerUpCards powerUpCards) {

    }


    //##################################################################################################################
    @Override
    public void wantToUsePowerUpOrNot() {

    }


    //##################################################################################################################
    @Override
    public void askBotShoot(SelectorEventPlayers SEPlayers) {

    }


    //##################################################################################################################
    @Override
    public void askTargetingScope(List<PowerUpCardV> listOfTargetingScopeV, List<Object> possiblePaymentsV, List<PlayerV> damagedPlayersV) {

    }


    //##################################################################################################################
    @Override
    public void askTagBackGranade(List<PowerUpCardV> listOfTagBackGranade) {

    }
}
