package it.polimi.se2019.view;

import it.polimi.se2019.view.components.*;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.List;


public class UpdateMap implements Runnable{

    private GameSceneController getGameSceneController() {
        return ((GameSceneController) GUIstarter.getStageController());
    }

    public void callRunInThisThread(){
        updateMap();
    }

    @Override
    public void run() {
        updateMap();
    }

    private void updateMap(){
        if (ViewModelGate.getModel() != null && ViewModelGate.getModel().getBoard() != null && ViewModelGate.getModel().getBoard().getMap() != null) {

            SquareV[][] map = ViewModelGate.getModel().getBoard().getMap();

            StackPane[][] mainImagesMap = getGameSceneController().getMainImagesmap();

            StackPane[][] eventListenerMap = new StackPane[map.length][map[0].length];

            Platform.runLater(()-> {
                //deletes precedent map
                for (int i = 0; i < map.length; i++) { //map.length == 3
                    for (int j = 0; j < map[0].length; j++) { // map[0].lenght == 4
                        mainImagesMap[i][j].getChildren().clear();
                    }
                }

                //add a layer of to be used for eventListeners
                for (int i = 0; i < map.length; i++) { //map.length == 3
                    for (int j = 0; j < map[0].length; j++) { // map[0].lenght == 4
                        eventListenerMap[i][j] = new StackPane();
                        mainImagesMap[i][j].getChildren().add(eventListenerMap[i][j]);
                    }
                }

                //builds new map on top of the eventListener layer
                for (int i = 0; i < map.length; i++) { //map.length == 3
                    for (int j = 0; j < map[0].length; j++) { // map[0].lenght == 4
                        SquareV currentSquareV = map[i][j];
                        StackPane currentStackPaneSquare = eventListenerMap[i][j];
                        if ((currentSquareV != null) && (currentSquareV.getClass().toString().contains("NormalSquare"))) {
                            //normal square
                            showNormalSquare((NormalSquareV) currentSquareV, currentStackPaneSquare);
                        } else  if(currentSquareV != null){
                            //spawn point square
                            showSpawnPoint((SpawnPointSquareV) currentSquareV, currentStackPaneSquare);
                        }
                        //else: is a null square-> show nothing.
                    }
                }
            });
        }
    }

    private void showNormalSquare(NormalSquareV square, StackPane mainStackPane){
        VBox squareContent = new VBox();

        mainStackPane.getChildren().add(squareContent);

        //place ammo card if there is
        AmmoCardV ammoCard = null;
        if (!square.getAmmoCards().getCards().isEmpty()) {
            ammoCard = square.getAmmoCards().getCards().get(0);
        }
        if (ammoCard != null) {
            StackPane ammoImage = new StackPane(new Label(ammoCard.getID())); //don't use a label, but set the image
            ammoImage.setUserData(ammoCard);
            //TODO LUDO: ammoImage.addEventHandler(MouseEvent.MOUSE_ENTERED, getGameSceneController().getShowAmmo());
            squareContent.getChildren().add(ammoImage);
            VBox.setVgrow(ammoImage, Priority.ALWAYS);
        }
        //else: no card to show

        //place players in the square
        List<PlayerV> playersToShow = getPlayers(square.getX(), square.getY());
        HBox playersHBox = buildPlayers(playersToShow);
        if (!playersHBox.getChildren().isEmpty()) {
            squareContent.getChildren().add(playersHBox);
            VBox.setVgrow(playersHBox, Priority.ALWAYS);
        }
    }

    private void showSpawnPoint(SpawnPointSquareV square, StackPane mainStackpane) {

        VBox squareContent = new VBox();

        mainStackpane.getChildren().add(squareContent);

        //places weapon cards on the square
        List<WeaponCardV> weaponCardVS = square.getWeaponCards().getCards();
        if (!weaponCardVS.isEmpty()) {
            HBox weaponsHBox = new HBox();
            for (WeaponCardV weapon : weaponCardVS) {
                StackPane weaponImage = new StackPane(new Label(weapon.getName())); //don't use a label, but set the image
                weaponImage.setUserData(weapon);
                weaponImage.addEventHandler(MouseEvent.MOUSE_ENTERED, getGameSceneController().getShowWeaponCardsEventHandler());
                weaponsHBox.getChildren().add(weaponImage);
                HBox.setHgrow(weaponImage, Priority.ALWAYS);
            }
            squareContent.getChildren().add(weaponsHBox);
            VBox.setVgrow(weaponsHBox, Priority.ALWAYS);
        }

        //place players in the square
        List<PlayerV> playersToShow = getPlayers(square.getX(), square.getY());
        HBox playersHBox = buildPlayers(playersToShow);
        if (!playersHBox.getChildren().isEmpty()) {
            squareContent.getChildren().add(playersHBox);
            VBox.setVgrow(playersHBox, Priority.ALWAYS);
        }
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

    private HBox buildPlayers(List<PlayerV> playersToShow){
        HBox hBox = new HBox();
        for (PlayerV p: playersToShow) {
            StackPane playerStackPane = new StackPane(new Label(p.getNickname())); //don't use a label, but set the image
            playerStackPane.setUserData(p);
            playerStackPane.addEventHandler(MouseEvent.MOUSE_ENTERED, getGameSceneController().getShowPlayerEventHandler());
            setPlayerStackPane(new PlayerStackPanesTracker(p.getNickname(), playerStackPane));
            hBox.getChildren().add(playerStackPane);
            HBox.setHgrow(playerStackPane, Priority.ALWAYS);
        }
        return hBox;
    }

    private class PlayerStackPanesTracker{
        private String nickname;
        private StackPane playerStackPane;
        PlayerStackPanesTracker(String nickname, StackPane playerStackPane){
            this.nickname = nickname;
            this.playerStackPane = playerStackPane;
        }
        private String getNickname(){
            return nickname;
        }
        private StackPane getPlayerStackPane(){
            return playerStackPane;
        }
    }

    //keeps track of all players stack pane
    private static List<PlayerStackPanesTracker> playersStackPaneList = new ArrayList<>();
    public static StackPane getPlayerStackPane(String nickname){
        for (PlayerStackPanesTracker playerStackPanesTracker: playersStackPaneList) {
            if(playerStackPanesTracker.getNickname().equals(nickname)){
                System.out.println("        returning player stackPane: " +playerStackPanesTracker.getPlayerStackPane().getChildren().toString());
                return playerStackPanesTracker.getPlayerStackPane();
            }
        }
        return null;
    }
    private static void setPlayerStackPane(PlayerStackPanesTracker newPlayerStackPanesTracker){
        for (PlayerStackPanesTracker oldStackPanesTracker: playersStackPaneList) {
            if(oldStackPanesTracker.getNickname().equals(newPlayerStackPanesTracker.getNickname())){
                playersStackPaneList.remove(oldStackPanesTracker);
                playersStackPaneList.add(newPlayerStackPanesTracker);
                System.out.println("        playersStackPaneListTracker.size() : " + playersStackPaneList.size());
                return;
            }
        }
        System.out.println("        playersStackPaneListTracker.size() : " + playersStackPaneList.size());
        playersStackPaneList.add(newPlayerStackPanesTracker);
    }
}