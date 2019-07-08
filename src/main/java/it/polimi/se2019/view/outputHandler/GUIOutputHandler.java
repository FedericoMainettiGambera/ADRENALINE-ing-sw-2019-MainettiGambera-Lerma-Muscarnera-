package it.polimi.se2019.view.outputHandler;

import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.model.enumerations.PlayersColors;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.model.events.stateEvent.StateEvent;
import it.polimi.se2019.view.GUIstarter;
import it.polimi.se2019.view.GameSceneController;
import it.polimi.se2019.view.LoadingSceneController;
import it.polimi.se2019.view.UpdateMap;
import it.polimi.se2019.view.components.*;
import it.polimi.se2019.view.selector.GUISelector;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.List;
import java.util.concurrent.TimeUnit;


/**GuiOutPutHandler class,
 * @author LudoLerma
 * @author FedericoMainettiGambera
 * */
public class GUIOutputHandler implements OutputHandlerInterface {

    /**
     * launches a UpdateKillShotTrack thread
     */
    private void updateKillShotTrack() {
        (new Thread(new UpdateKillShotTrack())).start();
    }

    private class UpdateKillShotTrack implements Runnable {
        @Override
        /**
         * fills the StackPanes composing the kill shot track,
         * if the first one is a skull, each of them must be a skull and the method setAllSkull is called,
         * if else, it surely contain a kill or an overKill, so the method setKillOrOverKill is called
         * finally, if the index (which should be in both case the number of starting skulls)
         * is minor of the stack panes, setEmpty function is called*/
        public void run(){
            Platform.runLater( ()->{
                int i = 0;
                if(ViewModelGate.getModel()==null&&ViewModelGate.getModel().getKillshotTrack()==null) {
                    return;
                }
                if (ViewModelGate.getModel().getKillshotTrack().getKillsV() != null) {
                    if(ViewModelGate.getModel().getKillshotTrack().getKillsV().get(0).isSkull()) {
                        i = ViewModelGate.getModel().getKillshotTrack().getKillsV().size();
                        setAllSkull(i);
                    } else {
                        for (KillsV killV : ViewModelGate.getModel().getKillshotTrack().getKillsV()){
                            if (killV.isSkull()) {
                                removePrevious(getGameSceneController().getKills().get(i));
                                getGameSceneController().getKills().get(i).getStyleClass().add("skull");
                                getGameSceneController().getKills().get(i).setUserData(null);
                            } else {
                                getGameSceneController().getKills().get(i).setUserData(ViewModelGate.getModel().getPlayers().getPlayer(killV.getKillingPlayer()));
                                removePrevious(getGameSceneController().getKills().get(i));
                                setKillOrOverKill(getGameSceneController().getKills().get(i), killV);
                            }
                            i++;
                        }
                    }
                    assert i >= 5;
                    if (i < getGameSceneController().getKills().size()){
                        setEmpty(i);
                    }
                }
            });
        }

        /**
         * remove the previous css style class
         * @param target, the stackpane from which the css style must be removed
         */
        private void removePrevious(StackPane target) {      // removePrevious(%1%,%2%,...,%n%)

            target.getStyleClass().remove("skull");
            target.getStyleClass().remove("killGreen");
            target.getStyleClass().remove("killPurple");
            target.getStyleClass().remove("killYellow");
            target.getStyleClass().remove("killGray");
            target.getStyleClass().remove("killBlue");
            target.getStyleClass().remove("overKillGreen");
            target.getStyleClass().remove("overKillPurple");
            target.getStyleClass().remove("overKillYellow");
            target.getStyleClass().remove("overKillGray");
            target.getStyleClass().remove("overKillBlue");
            target.getStyleClass().remove("emptyKillSlot");
        }




        /**
         * @param kill is the reference to the stackpane to modify
         * @param slot is needed to know if the slot is a kill or a overkill too
         */
        private void setKillOrOverKill(StackPane kill, KillsV slot) {

            switch (ViewModelGate.getModel().getPlayers().getPlayer(slot.getKillingPlayer()).getColor()) {
                case purple:
                    if (slot.isOverKill()) {
                        kill.getStyleClass().add("overKillPurple");

                    } else {
                        kill.getStyleClass().add("killPurple");
                    }
                    break;

                case gray:
                    if (slot.isOverKill()) {
                        kill.getStyleClass().add("overKillGray");
                    } else {
                        kill.getStyleClass().add("killGray");
                    }
                    break;

                case green:
                    if (slot.isOverKill()) {
                        kill.getStyleClass().add("overKillGreen");
                    } else {
                        kill.getStyleClass().add("killGreen");
                    }
                    break;

                case blue:
                    if (slot.isOverKill()) {
                        kill.getStyleClass().add("overKillBlue");
                    } else {
                        kill.getStyleClass().add("killBlue");
                    }
                    break;

                case yellow:
                    if (slot.isOverKill()) {
                        kill.getStyleClass().add("overKillYellow");
                    } else {
                        kill.getStyleClass().add("killYellow");
                    }
                    break;
                default:
                    break;

            }


        }

        /**
         * @param numberOfStartingSkulls is the number of stack panes to be set as skulls
         */
        private void setAllSkull(int numberOfStartingSkulls) {
            for (int i = 0; i < numberOfStartingSkulls; i++) {
                removePrevious(getGameSceneController().getKills().get(i));
                getGameSceneController().getKills().get(i).getStyleClass().add("skull");
            }


        }

        /**
         * @param from is the number of stack panes to be set Empty
         */
        private void setEmpty(int from) {
            for (int i = from; i < getGameSceneController().getKills().size(); i++) {
                removePrevious(getGameSceneController().getKills().get(from));
                getGameSceneController().getKills().get(from).getStyleClass().add("emptyKillSlot");
            }
        }
    }


    private void updatePlayer() {
        updatePowerUpCards();
        updateWeaponCards();
        updatePlayerBoard();
    }

    /**
     *launches a UpdatePowerUpCards thread
     */
    private void updatePowerUpCards() {
        (new Thread(new UpdatePowerUpCards())).start();
    }

    /**implements a thread to update PowerUp cards css Style*/
    private class UpdatePowerUpCards implements Runnable {
        /**
         * for each PowerUp card in hand, this function encapsulates a reference to the
         * said PowerUpcard in the stack pane that represents it,
         * calls the function removePrevious,
         * then add the right css Style,
         * if the number of cards hold by the user is minor of the size of the list of
         * stack panes meant to represent the PowerUp cards,
         * all of the remaining one are set "empty" with a specific css style
         * */
        @Override
        public void run() {
            Platform.runLater(()->{
                if(ViewModelGate.getModel()==null&&ViewModelGate.getModel().getPlayers()==null&&ViewModelGate.getModel().getPlayers().getPlayer(ViewModelGate.getMe())==null) {
                    return;
                }

                PlayerV me = ViewModelGate.getModel().getPlayers().getPlayer(ViewModelGate.getMe());
                if (me.getPowerUpCardInHand() != null) {
                    int i = 0;
                    for (PowerUpCardV powerUp : me.getPowerUpCardInHand().getCards()){
                        if(i<2) {
                            getGameSceneController().getListOfPowerUpCardsMainImage().get(i).setUserData(powerUp);
                            removePrevious(getGameSceneController().getListOfPowerUpCardsMainImage().get(i));
                            getGameSceneController().getListOfPowerUpCardsMainImage().get(i).getStyleClass().add("powerUpCard" + powerUp.getID());
                            i++;
                        }
                    }
                    if (i < getGameSceneController().getListOfPowerUpCardsMainImage().size()) {
                        for (int j = i; j < getGameSceneController().getListOfPowerUpCardsMainImage().size(); j++) {

                            getGameSceneController().getListOfPowerUpCardsMainImage().get(i).setUserData(null);

                            removePrevious(getGameSceneController().getListOfPowerUpCardsMainImage().get(j));

                            getGameSceneController().getListOfPowerUpCardsMainImage().get(j).getStyleClass().add("emptyPowerUpCardMainImage");
                        }
                    }
                }

            });
        }

        /**@param target is the stack pane to remove previous css style from*/
        private void removePrevious(StackPane target) {

            target.getStyleClass().remove("emptyPowerUpCardMainImage");

            int numberOfCards = 23;

            for (int i = 0; i <= numberOfCards; i++) {
                target.getStyleClass().remove("powerUpCard" + i);

            }
        }


    }


    /**launches a UpdateWeaponCards thread*/
    private void updateWeaponCards() {
        (new Thread(new UpdateWeaponCards())).start();
    }

    /**implements a thread to update weapon cards in hand css style*/
    private class UpdateWeaponCards implements Runnable {
        /**
         * for each weapon in hand, this function encapsulates a reference to the
         * said weapon card in the stack pane that represents it,
         * call the function removePrevious,
         * then add the right css Style,
         * if the number of cards hold by the user is minor of the size of the list of
         * stack panes meant to represent the weapon cards,
         * all of the remaining one are set "empty" with a specific css style
         * */
        @Override
        public void run() {
            Platform.runLater(()->{

                if(ViewModelGate.getModel()==null&&ViewModelGate.getModel().getPlayers()==null&&ViewModelGate.getModel().getPlayers().getPlayer(ViewModelGate.getMe())==null)
                { return; }

                PlayerV me = ViewModelGate.getModel().getPlayers().getPlayer(ViewModelGate.getMe());

                if(me.getWeaponCardInHand()==null)
                { return; }

                if (me.getWeaponCardInHand().getCards() != null){
                    int i=0;

                    for (WeaponCardV weapon : me.getWeaponCardInHand().getCards()) {
                        if(i<3){
                            getGameSceneController().getWeaponCardsMainImage().get(i).setUserData(weapon);
                            removePrevious(getGameSceneController().getWeaponCardsMainImage().get(i));
                            getGameSceneController().getWeaponCardsMainImage().get(i).getStyleClass().add("weaponCard" + weapon.getID());
                            i++;
                        }
                        else{
                            GUIstarter.showError(this, "THE PLAYER HAS MORE THAN THREE WEAPON CARDS IN HAND", null);
                        }
                    }

                    if(i<getGameSceneController().getWeaponCardsMainImage().size()){
                        for (int j = i; j < getGameSceneController().getWeaponCardsMainImage().size(); j++) {
                            removePrevious(getGameSceneController().getWeaponCardsMainImage().get(j));
                            getGameSceneController().getWeaponCardsMainImage().get(j).setUserData(null);
                            getGameSceneController().getWeaponCardsMainImage().get(j).getStyleClass().add("emptyWeaponCardMainImage");
                        }
                    }

                }

            });
        }

        /**@param target is the Stack pane to remove previous css style from*/
        private void removePrevious(StackPane target) {
            target.getStyleClass().remove("emptyWeaponCardMainImage");
            int numberOfCards = 21;
            for (int i = 1; i <= numberOfCards; i++) {
                target.getStyleClass().remove("weaponCard" + i);
            }
        }



    }

    /**update the player board calling the specific functions */
    private void updatePlayerBoard() {
        updateDamage();
        updateMarks();
        updateDeaths();
        updateNickname();
        updateAmmobox();
    }


    /**
     * update the damage track of the player
     */
    private void updateDamage() {
        (new Thread(new UpdateDamage())).start();
    }

    /**
     * this class implements a thread launched in updateDamage function in order to modify the damage board of the player
     */
    private class UpdateDamage implements Runnable {

        /**
         * the said thread launched look for the right player and
         * updates their damage board, it checks one by one all of the damage slots,
         * removes the previous css style class
         * and applies the right new one
         */
        @Override
        public void run() {
            Platform.runLater(() -> {

                PlayerV player=ViewModelGate.getModel().getPlayers().getPlayer(ViewModelGate.getMe());
                if(ViewModelGate.getModel()==null&&ViewModelGate.getModel().getPlayers()==null&&ViewModelGate.getModel().getPlayers().getPlayer(ViewModelGate.getMe())==null)
                { return; }

                updateDamage(player);


            });
        }

        private void updateDamage(PlayerV player) {
            int i = 0;
            if (player.getDamageTracker() != null) {
                List<StackPane> damagesStackpanes = getGameSceneController().getDamagesMainImage();
                int totalNumberOfDamages = player.getDamageTracker().getDamageSlotsList().size();
                for (int j = 0; j < totalNumberOfDamages && j < 12; j++) { //full damages
                    PlayerV shootingPlayer = ViewModelGate.getModel().getPlayers().getPlayer(player.getDamageTracker().getDamageSlotsList().get(i).getShootingPlayerNickname());
                    damagesStackpanes.get(i).setUserData(shootingPlayer);
                    removePrevious(damagesStackpanes.get(i));
                    damagesStackpanes.get(i).getStyleClass().add("damage" + getColorStringWithFirstCapitalLetter(shootingPlayer.getColor()));
                }
                for (int j = totalNumberOfDamages; j < 12; j++) { // empty damages
                    damagesStackpanes.get(i).setUserData(null);
                    removePrevious(damagesStackpanes.get(i));
                    damagesStackpanes.get(i).getStyleClass().add("damageEmpty");
                }
                if (totalNumberOfDamages >= 12) {
                    damagesStackpanes.get(i).getChildren().clear();
                    damagesStackpanes.get(i).getChildren().add(new Label("+" + (totalNumberOfDamages - 12)));
                }
            }
        }
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

        /**
         * @param damage from whose previous style class is removed
         */
        private void removePrevious(StackPane damage) {

            damage.getStyleClass().remove("damageBlu");
            damage.getStyleClass().remove("damagePurple");
            damage.getStyleClass().remove("damageYellow");
            damage.getStyleClass().remove("damageGreen");
            damage.getStyleClass().remove("damageGray");
            damage.getStyleClass().remove("damageEmpty");


        }
    }


    /**
     * update the marks track of the client
     */
    private void updateMarks() {

        (new Thread(new UpdateMarks())).start();
    }


    /**
     * this class implements a thread launched in updateMarks in order to modify the mark board of the player
     */
    private class UpdateMarks implements Runnable {

        /**
         * the said thread launched look for the right player and
         * updates their damage board, it checks one by one all of the mark slots,
         * removes the previous css style class
         * and applies the right new one
         */
        @Override
        public void run() {

            Platform.runLater(this::run2);

        }

        /**
         * update marks
         * @param player the player to update the marks of
         */
        private void updateMarks(PlayerV player) {
            if(player.getMarksTracker()==null){return;}

            int i = 0;
            if (player.getMarksTracker().getMarkSlotsList().isEmpty() || player.getMarksTracker() == null) {
                emptyMarks(0);
            } else {
                for (MarkSlotV markSlot : player.getMarksTracker().getMarkSlotsList()) {
                    setMarks(i, markSlot);
                    i++;
                }
                if (i < getGameSceneController().getMarkMainImage().size()) {
                    emptyMarks(i);
                }
            }
        }

        /**
         * set the markslot with the right css style class after having removed the previous one
         *
         * @param i        is to get the corresponding stack pane
         * @param markSlot is the markSlotV whose style is meant to be replaced
         */
        private void setMarks(int i, MarkSlotV markSlot) {
            PlayersColors color = ViewModelGate.getModel().getPlayers().getPlayer(markSlot.getMarkingPlayer()).getColor();
            ((Label) (getGameSceneController().getMarkMainImage().get(i).getChildren().get(0))).setText(Integer.toString(markSlot.getQuantity()));

            for (PlayerV player : ViewModelGate.getModel().getPlayers().getPlayers()) {
                if (player.getNickname().equals(markSlot.getMarkingPlayer())) {
                    getGameSceneController().getDamagesMainImage().get(i).setUserData(player);
                    break;
                }
            }

            switch (color) {
                case blue:
                    removePrevious(getGameSceneController().getMarkMainImage().get(i));
                    (getGameSceneController().getMarkMainImage().get(i).getStyleClass()).add("markBlue");
                    break;
                case purple:
                    removePrevious(getGameSceneController().getMarkMainImage().get(i));
                    (getGameSceneController().getMarkMainImage().get(i).getStyleClass()).add("markPurple");
                    break;
                case gray:
                    removePrevious(getGameSceneController().getMarkMainImage().get(i));
                    (getGameSceneController().getMarkMainImage().get(i).getStyleClass()).add("markGray");
                    break;
                case green:
                    removePrevious(getGameSceneController().getMarkMainImage().get(i));
                    (getGameSceneController().getMarkMainImage().get(i).getStyleClass()).add("markGreen");
                    break;
                case yellow:
                    removePrevious(getGameSceneController().getMarkMainImage().get(i));
                    (getGameSceneController().getMarkMainImage().get(i).getStyleClass()).add("markYellow");
                    break;
                default:
                    removePrevious(getGameSceneController().getMarkMainImage().get(i));
                    (getGameSceneController().getMarkMainImage().get(i).getStyleClass()).add(markEmpty);
                    break;
            }

        }

        private String markEmpty = "markEmpty";

        /**
         * applies the "markEmpty" css style class to all of the marks
         * @param i  index
         */
        private void emptyMarks(int i) {

            for (int j = i; j < getGameSceneController().getMarkMainImage().size(); j++) {
                removePrevious(getGameSceneController().getMarkMainImage().get(j));
                getGameSceneController().getMarkMainImage().get(j).getStyleClass().add(markEmpty);
                ((Label) (getGameSceneController().getMarkMainImage().get(j).getChildren().get(0))).setText("");
                getGameSceneController().getMarkMainImage().get(j).setUserData(null);
            }
        }

        /**
         * @param mark from which previous style class is removed
         */
        private void removePrevious(StackPane mark) {

            mark.getStyleClass().remove("markBlu");
            mark.getStyleClass().remove("markPurple");
            mark.getStyleClass().remove("markYellow");
            mark.getStyleClass().remove("markGreen");
            mark.getStyleClass().remove("markGray");
            mark.getStyleClass().remove(markEmpty);

        }

        private void run2() {

            if(ViewModelGate.getModel()==null&&ViewModelGate.getModel().getPlayers()==null&&ViewModelGate.getModel().getPlayers().getPlayer(ViewModelGate.getMe())==null)
            { return; }

            PlayerV player=ViewModelGate.getModel().getPlayers().getPlayer(ViewModelGate.getMe());

                    updateMarks(player);

            }
        }


    /**
     * updates the death track of the player
     */
    private void updateDeaths() {

        (new Thread(new UpdateDeaths())).start();
    }


    /**
     * this class implements a thread launched in updateDeaths in order to modify the death track of the player
     */
    private class UpdateDeaths implements Runnable {

        /**
         * the said thread launched look for the right player and
         * updates their death tracker, it extrapolate which one to update by using the function "player.getNumberOfDeath()",
         * removes the previous css style class
         * and applies the right new one
         */
        @Override
        public void run() {
            Platform.runLater(() -> {

                if(ViewModelGate.getModel()==null&&ViewModelGate.getModel().getPlayers()==null)
                {return;}

                for (PlayerV player : ViewModelGate.getModel().getPlayers().getPlayers()) {

                    if (player.getNickname().equals(ViewModelGate.getMe())) {
                        for (int i = 0; i < player.getNumberOfDeaths(); i++){
                            removePrevious( getGameSceneController().getDeathMainImage().get(i));
                            getGameSceneController().getDeathMainImage().get(i).getStyleClass().add("deathSkull");
                            ((Label) getGameSceneController().getDeathMainImage().get(i).getChildren().get(0)).setText("");
                        }
                        for (int j =  player.getNumberOfDeaths(); j < getGameSceneController().getDeathMainImage().size(); j++) {
                            removePrevious(getGameSceneController().getDeathMainImage().get(j));
                            getGameSceneController().getDeathMainImage().get(j).getStyleClass().add("deathEmpty");
                            if(player.isHasFinalFrenzyBoard()) {
                                ((Label) getGameSceneController().getDeathMainImage().get(j).getChildren().get(0)).setText("" + (Math.max(1, 2-j)));
                            }
                            else{
                                int points = 8 - (2*j);
                                ((Label) getGameSceneController().getDeathMainImage().get(j).getChildren().get(0)).setText("" + (Math.max(1, points)));
                            }
                        }

                        break;
                    }
                }

            });
        }

        /**
         * @param kills is the needed  deathSlot from which previous style class is removed
         */
        private void removePrevious(StackPane kills) {

           kills.getStyleClass().remove("deathEmpty");
           kills.getStyleClass().remove("deathSkull");


        }


    }

    /**
     * update the player's nickname
     */
    private void updateNickname() {

        (new Thread(new UpdateNickname())).start();
    }


    /**
     * this class implements a thread launched in updateNickname in order to set the player's NickName
     */
    private class UpdateNickname implements Runnable {
        /**
         * the said thread launched look for the right player and
         * sets their nickname,
         * removes the previous css style class
         * and applies the right new one
         */
        @Override
        public void run() {

            Platform.runLater(() -> {

                if(ViewModelGate.getModel()==null&&ViewModelGate.getModel().getPlayers()==null)
                {return;}

                for (PlayerV player : ViewModelGate.getModel().getPlayers().getPlayers()) {

                    if (player.getNickname().equals(ViewModelGate.getMe())) {

                        removePrevious();
                        if(!player.isAFK()) {
                            getGameSceneController().getNicknameLabel().setText(player.getNickname());
                        }
                        else{
                            getGameSceneController().getNicknameLabel().setText("AFK: " + player.getNickname());
                        }
                        getGameSceneController().getNicknameLabel().getStyleClass().add("nicknameStyle");
                        if (player.getColor() != null){
                            PlayersColors color = player.getColor();
                            switch (color) {
                                case yellow:
                                    getGameSceneController().getNicknameBackGround().getStyleClass().add("nicknameBackgroundYellow");
                                    break;
                                case blue:
                                    getGameSceneController().getNicknameBackGround().getStyleClass().add("nicknameBackgroundBlue");
                                    break;
                                case green:
                                    getGameSceneController().getNicknameBackGround().getStyleClass().add("nicknameBackgroundGreen");
                                    break;
                                case gray:
                                    getGameSceneController().getNicknameBackGround().getStyleClass().add("nicknameBackgroundGray");
                                    break;
                                case purple:
                                    getGameSceneController().getNicknameBackGround().getStyleClass().add("nicknameBackgroundPurple");
                                    break;
                                default:
                                    getGameSceneController().getNicknameBackGround().getStyleClass().add("nicknameBackground");
                                    break;
                            }


                        }

                    }

                }
            });
        }

        /**
         * previous style class is removed
         */
        private void removePrevious() {

            getGameSceneController().getNicknameLabel().getStyleClass().remove("nicknameStyle");
            getGameSceneController().getNicknameBackGround().getStyleClass().remove("nicknameBackground");

        }
    }

    /**
     * update the ammo boxe
     */
    private void updateAmmobox() {

        (new Thread(new UpdateAmmobox())).start();
    }


    /**
     * this class implements a thread launched in updateAmmobox in order to modify the box containing the ammunitions
     * owned by the player
     */
    private class UpdateAmmobox implements Runnable {

        /**
         * the said thread launched look for the right player and
         * updates their ammo box, it checks one by one all of the ammo in the ammo box,
         * removes the previous css style class
         * and applies the right new one
         */
        @Override
        public void run() {
            Platform.runLater(()-> {

                if(ViewModelGate.getModel()!= null && ViewModelGate.getModel().getPlayers() != null) {
                    PlayerV playerToShow = ViewModelGate.getModel().getPlayers().getPlayer(ViewModelGate.getMe());

                    if (playerToShow != null && playerToShow.getAmmoBox() != null && playerToShow.getAmmoBox().getAmmoCubesList() != null) {
                        //updates Red
                        for (AmmoCubesV ammoCubes: playerToShow.getAmmoBox().getAmmoCubesList()) {
                            if(ammoCubes.getColor().equals(AmmoCubesColor.red)){

                                List<StackPane> redStackPanes = getGameSceneController().getAmmosMainImage(AmmoCubesColor.red);

                                for (int i = 0; i < ammoCubes.getQuantity() ; i++) { //not sure about quantity or quantity-1
                                    removeAllPreviousCssClass(redStackPanes.get(i));
                                    redStackPanes.get(i).getStyleClass().add("ammoRed");
                                }
                                for (int i = ammoCubes.getQuantity(); i < redStackPanes.size(); i++) {
                                    removeAllPreviousCssClass(redStackPanes.get(i));
                                    redStackPanes.get(i).getStyleClass().add("emptyAmmo");
                                }

                            }
                        }
                        //updates blue
                        for (AmmoCubesV ammoCubes: playerToShow.getAmmoBox().getAmmoCubesList()) {
                            if(ammoCubes.getColor().equals(AmmoCubesColor.blue)){

                                List<StackPane> blueStackPane = getGameSceneController().getAmmosMainImage(AmmoCubesColor.blue);

                                for (int i = 0; i < ammoCubes.getQuantity() ; i++) { //not sure about quantity or quantity-1
                                    removeAllPreviousCssClass(blueStackPane.get(i));
                                    blueStackPane.get(i).getStyleClass().add("ammoBlue");
                                }
                                for (int i = ammoCubes.getQuantity(); i < blueStackPane.size(); i++) {
                                    removeAllPreviousCssClass(blueStackPane.get(i));
                                    blueStackPane.get(i).getStyleClass().add("emptyAmmo");
                                }

                            }
                        }
                        //updates yellow
                        for (AmmoCubesV ammoCubes: playerToShow.getAmmoBox().getAmmoCubesList()) {
                            if(ammoCubes.getColor().equals(AmmoCubesColor.yellow)){

                                List<StackPane> yellowStackPane = getGameSceneController().getAmmosMainImage(AmmoCubesColor.yellow);

                                for (int i = 0; i < ammoCubes.getQuantity() ; i++) { //not sure about quantity or quantity-1
                                    removeAllPreviousCssClass(yellowStackPane.get(i));
                                    yellowStackPane.get(i).getStyleClass().add("ammoYellow");
                                }
                                for (int i = ammoCubes.getQuantity(); i < yellowStackPane.size(); i++) {
                                    removeAllPreviousCssClass(yellowStackPane.get(i));
                                    yellowStackPane.get(i).getStyleClass().add("emptyAmmo");
                                }

                            }
                        }
                    }
                }
            });
        }

        /**@param stackPane the stack pane from which previous css style class needs to be removed*/
        public void removeAllPreviousCssClass(StackPane stackPane){
            stackPane.getStyleClass().remove("ammoRed");
            stackPane.getStyleClass().remove("ammoBlue");
            stackPane.getStyleClass().remove("ammoYellow");
            stackPane.getStyleClass().remove("emptyAmmo");
        }
    }

    /**launches a thread to update the map*/
    private void updateMap()   {
        Platform.runLater(()-> (new UpdateMap()).updateMapWithoutNewEventLayer());
    }


    /**
     * launches a UpdateStateBar thread
     *
     * @param stateEvent needed to know in which state the game is in a given moment
     */
    private void updateStateBar(StateEvent stateEvent) {


        (new Thread(new UpdateStateBar((stateEvent)))).start();
    }

    /**
     * implements a thread dedicated to update a section of the that informs clients what is going on
     */
    private class UpdateStateBar implements Runnable {
        /**
         * save the above mentioned event to pass it to the thread, which will extrapolate the information needed
         */
        StateEvent stateEvent;

        UpdateStateBar(StateEvent stateEvent) {
            this.stateEvent = stateEvent;
        }

        /**
         * depending on which state the game is happen to be, a different StateTitle will be set with a different description
         */
        @Override
        public void run() {
            Platform.runLater(() -> {

                String title;
                String description;

                switch (stateEvent.getState()) {
                    case "BotMoveState":
                        title = "BOT IS MOVING";
                        description = " \n  moving the bot";
                        break;
                    case "BotShootState":
                        title = "BOT IS ABOUT TO SHOOT";
                        description = " \nchoosing whom the bot may be about to blow the head off";
                        break;
                    case "ChooseHowToPayState":
                        title = "PAYMENT DETAILS ARE BEING DISCUSSED";
                        description = " \n choosing how to purchase";
                        break;
                    case "FFSetUpState":
                        title = "FINAL FRENZY PREPARATION";
                        description = " \nare waiting! We are working for you fun!(and bloody revenge)";
                        break;
                    case "FinalScoringState":
                        title = "CALCULATING YOUR FINAL SCORE";
                        description = "  \n waiting for the final results! \n" +
                                "Can you figure out who is going to win?";
                        break;
                    case "FirstSpawnState":
                        title = "SPAWNING FOR THE FIRST TIME";
                        description = " \n spawning for the first time!\n" +
                                "Let's show some warmth all of you!";
                        break;
                    case "GrabStuffState":
                        title = "CHOOSING WHERE TO GRAB";
                        description = "  choosing where to grab! ";
                        break;
                    case "GrabStuffStateDrawAndDiscardPowerUp":
                        title = "DISCARDING A POWER UP";
                        description = " \n " +
                                "discarding a power up card";
                        break;
                    case "GrabStuffStateDrawPowerUp":
                        title = "DRAWING A POWER UP";
                        description = "\n  " +
                                "drawing a power up!";
                        break;
                    case "GrabStuffStateGrab":
                        title = "GRABBING SOMETHING";
                        description = " \n  " +
                                "grabbing something! I wonder what it will be...";
                        break;
                    case "GrabStuffStateGrabWeapon":
                        title = "GRABBING A WEAPON";
                        description = " \n " +
                                "grabbing a weapon! Watch out you all!!!";
                        break;
                    case "GrabStuffStateMove":
                        title = "MOVING BEFORE GRABBING";
                        description = " \n  " +
                                " moving somewhere!";
                        break;
                    case "PowerUpAskForInputState":
                        title = "POWER UP DETAILS ARE BEING DISCUSSED";
                        description = " \n " +
                                "deciding how to reveal the hidden powers of cards...";
                        break;
                    case "PowerUpState":
                        title = "CLEARING IDEAS ON POWER UPS";
                        description = "\n  " +
                                " deciding what spell to use ";
                        break;
                    case "ReloadState":
                        title = "RELOADING YOUR WEAPONS";
                        description = "  \n " +
                                "\n" +
                                "reloading those shiny murderers! Beware! ";
                        break;
                    case "RunAroundState":
                        title = "RUN AROUND!";
                        description = " \n" +
                                "\n" +
                                " going for a walk";
                        break;
                    case "ScoreKillsState":
                        title = "SCORING THE DEAD";
                        description = " \n waiting!" +
                                "Working for your rank to update";
                        break;
                    case "ShootPeopleAskForInputState":
                        title = "SHOOTING DETAILS ARE BEING DISCUSSED";
                        description = " \n " +
                                "deciding what to do with those golden weapons";
                        break;
                    case "ShootPeopleChooseEffectState":
                        title = "WEAPON MODES ARE BEING DEFINED";
                        description = "  \n  " +
                                "deciding how to make someone suffer";
                        break;
                    case "ShootPeopleChooseWepState":
                        title = "WEAPON TO USE IS BEING CHOSEN";
                        description = " \n  " +
                                "deciding what will make someone's brain blow out";
                        break;
                    case "ShootPeopleState":
                        title = "SHOOTING PEOPLE";
                        description = " \n  " +
                                "shooting";
                        break;
                    case "SpawnState":
                        title = "SPAWNING PLAYER";
                        description = " \n  " +
                                "rising again from the dead";
                        break;
                    case "TagBackGranadeState":
                        title = "TAGBACK GRENADE IN ACTION";
                        description = " \n " +
                                " using tagbackgrenade";
                        break;
                    case "TargetingScopeState":
                        title = "TARGETING SCOPE IN ACTION";
                        description = " \n  " +
                                " using targetingscope";
                        break;
                    case "TurnState":
                        title = "TURN STATE";
                        description = " \n " +
                                " thinking! " +
                                "What will he do?";
                        break;
                    case "WantToPlayPowerUpState":
                        title = "ASKING PLAYER FOR POWER UP TO BE USED";
                        description = "\n " +
                                "deciding whether to use a power up or not";
                        break;
                    default:
                        title = "Unpredictable switch of the game";
                        description = "whoop i fell";
                        break;
                }

                Text player;
                Text descr = new Text(description);

                (getGameSceneController().getStateTitle()).setText(title);

                if (ViewModelGate.getMe().equals(ViewModelGate.getModel().getPlayers().getCurrentPlayingPlayer())) {
                    player = new Text("you are  ");
                } else {
                    player = new Text(ViewModelGate.getModel().getPlayers().getCurrentPlayingPlayer() + "  is ");
                }

                if(stateEvent.getState().contains("SpawnState")||stateEvent.getState().contains("FFSetUp")||stateEvent.getState().contains("FinalScoring")||stateEvent.getState().contains("ScoreKills")){
                    player = new Text(" ");
                }

                player.setFill(Color.rgb(255, 158, 30));
                player.setFont((Font.font("Courier")));
                descr.setFill(Color.rgb(255, 158, 30));
                descr.setFont((Font.font("Courier")));

                getGameSceneController().getStateDescription().getChildren().clear();
                getGameSceneController().getStateDescription().getChildren().addAll(player, descr);
                getGameSceneController().getStateDescription().setTextAlignment(TextAlignment.CENTER);


            });
        }
    }

    /**
     * launches a UpdateProgressIndicator thread
     *
     * @param currentTime the time that has passed till a given moment
     * @param totalTime   the total time of the count down
     */
    private void updateProgressIndicator(int currentTime, int totalTime) {
        new Thread(new UpdateProgressIndicator(currentTime, totalTime)).start();
    }

    /**
     * implements a thread that updates the progress indicator to match the timer count down for the player
     */
    private class UpdateProgressIndicator implements Runnable {
        private int currentTime;
        private int totalTime;

        private UpdateProgressIndicator(int currentTime, int totalTime) {
            this.currentTime = currentTime;
            this.totalTime = totalTime;
        }

        @Override
        public void run() {
            if (GUIstarter.getStageController().getClass().toString().contains("GameSceneController")) {
                getGameSceneController().getProgressIndicator().setProgress((double) this.currentTime / (double) this.totalTime);
            }
        }
    }

    /**@return the game scene Controller*/
    private GameSceneController getGameSceneController() {
        return ((GameSceneController) GUIstarter.getStageController());
    }

    /**@return the loading scene controller*/
    private LoadingSceneController getLoadingSceneController() {
        return ((LoadingSceneController) GUIstarter.getStageController());
    }

    /***/
    @Override
    public void gameCreated() {
        //empty
    }
    /**@param stateEvent , the state has changed, update the scene*/
    @Override
    public void stateChanged(StateEvent stateEvent) {
        if (stateEvent.getState().contains("GameSetUpState")) {
            //starts the GAME.fxml
            getLoadingSceneController().changeScene();

        } else {
            //update StateBar
            updateStateBar(stateEvent);
        }
    }

    /**@param modelViewEvent , */
    @Override
    public void setFinalFrenzy(ModelViewEvent modelViewEvent) {
        Platform.runLater(()->{
            if (ViewModelGate.getModel().isFinalFrenzy()) {
                StackPane stackPane = new StackPane(new Label("FINAL FRENZY: ACIVE"));
                VBox.setVgrow(stackPane, Priority.ALWAYS);
                getGameSceneController().getGameInfoVbox().getChildren().add(stackPane);
            }
            else{
                StackPane stackPane = new StackPane(new Label("FINAL FRENZY: NON-ACTIVE"));
                VBox.setVgrow(stackPane, Priority.ALWAYS);
                getGameSceneController().getGameInfoVbox().getChildren().add(stackPane);
            }
        });
    }
    /**@param modelViewEvent , final frenzy has begun*/
    @Override
    public void finalFrenzyBegun(ModelViewEvent modelViewEvent) {
        Platform.runLater(()-> {
            if (ViewModelGate.getModel().isHasFinalFrenzyBegun()) {
                StackPane stackPane = new StackPane(new Label("FINAL FRENZY: BEGUN"));
                VBox.setVgrow(stackPane, Priority.ALWAYS);
                getGameSceneController().getGameInfoVbox().getChildren().add(stackPane);
            }
        });
    }
    /**@param modelViewEvent , kill shot track are updated */
    @Override
    public void newKillshotTrack(ModelViewEvent modelViewEvent) {
        updateKillShotTrack();
    }

    /**@param modelViewEvent , a new player has joined the game, update the scene*/
    @Override
    public void newPlayersList(ModelViewEvent modelViewEvent) {
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        if (GUIstarter.getStageController().getClass().toString().contains("LoadingSceneController")) {
            showPlayerListInLoadingScene();
        } else {
            updatePlayer();
            updateMap();
            updateKillShotTrack();
        }
    }

    /**show the player that is connecting to the game in the loading scene */
    private void showPlayerListInLoadingScene() {
        //we are in the loading scene and should update it
        boolean done = false;
        while (!done) {
            if (GUIstarter.getStageController().getClass().toString().contains("LoadingSceneController")) {
                ((LoadingSceneController) GUIstarter.getStageController()).newPlayersList();
                done = true;
            } else { //wait because some times the event sent from the server is faster than the process of changing scene in javafx
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }


    /**@param modelViewEvent, the board is changed, update the map */
    @Override
    public void newBoard(ModelViewEvent modelViewEvent) {
        if(modelViewEvent.getExtraInformation1()!=null) {
            Platform.runLater(() -> {
                getGameSceneController().getBoardBackGround().getStyleClass().add((String)modelViewEvent.getExtraInformation1());
            });
        }
        updateMap();
    }

    /**@param modelViewEvent , a player died, update the players and the kill shot track*/
    @Override
    public void deathOfPlayer(ModelViewEvent modelViewEvent) {
        updateKillShotTrack();
        updatePlayer();
        updateMap();
    }

    /**@param modelViewEvent , cards have been moved*/
    @Override
    public void movingCardsAround(OrderedCardListV from, OrderedCardListV to, ModelViewEvent modelViewEvent) {
        updateMap();
        updatePlayer();
    }

    /**@param modelViewEvent , cards have been shuffled */
    @Override
    public void shufflingCards(ModelViewEvent modelViewEvent) {
        //empty
        updateDeaths();
    }

    /**@param modelViewEvent , color has been set, update the player*/
    @Override
    public void newColor(ModelViewEvent modelViewEvent) {
        updatePlayer();
    }

    /**@param modelViewEvent , a new nickName has ben set, update Nicknames */
    @Override
    public void newNickname(ModelViewEvent modelViewEvent) {
        updateNickname();
    }

    /**@param modelViewEvent , a new position has been set, update the map */
    @Override
    public void newPosition(ModelViewEvent modelViewEvent) {
        updateMap();
    }

    /**@param modelViewEvent ,  a new score*/
    @Override
    public void newScore(ModelViewEvent modelViewEvent) {
        //empty
    }
    /**@param modelViewEvent , some one death counter has incremented, update the deaths */
    @Override
    public void addDeathCounter(ModelViewEvent modelViewEvent) {
        updateDeaths();
        updateKillShotTrack();
        updateMap();
    }
    /**@param modelViewEvent , final frenzy boards have been set, update the damages and the deaths*/
    @Override
    public void setFinalFrenzyBoard(ModelViewEvent modelViewEvent) {
      updateDamage();
      updateDeaths();
    }

    /**@param modelViewEvent , ammo box has changed, update it*/
    @Override
    public void newAmmoBox(ModelViewEvent modelViewEvent) {
        updateAmmobox();
    }
    /**@param modelViewEvent , damage tracker has changed, update it*/
    @Override
    public void newDamageTracker(ModelViewEvent modelViewEvent) {
        updateDamage();
    }
    /**@param modelViewEvent , marks tracker has changed, update it*/
    @Override
    public void newMarksTracker(ModelViewEvent modelViewEvent) {
        updateMarks();
    }
    /**@param modelViewEvent , current playin player has changed */
    @Override
    public void setCurrentPlayingPlayer(ModelViewEvent modelViewEvent) {
        Platform.runLater(()-> {
            if (GUIstarter.getStageController().getClass().toString().contains("GameSceneController")) {
                getGameSceneController().getCurrentPlayingPlayerLabel().setText("TURN OF: " + ViewModelGate.getModel().getPlayers().getCurrentPlayingPlayer());
            }
        });
    }

    /**@param modelViewEvent ,*/
    @Override
    public void setStartingPlayer(ModelViewEvent modelViewEvent) {
        //empty
    }
    /**@param modelViewEvent , */
    @Override
    public void newPlayer(ModelViewEvent modelViewEvent) {
        //empty..
    }
    /**@param modelViewEvent , a player has been set AFK, update the players */
    @Override
    public void setAFK(ModelViewEvent modelViewEvent) {
        if(GUIstarter.getStageController().getClass().toString().contains("GameSceneController")) {
            updatePlayer();
            updateMap(); //TODO not sure
        }
    }
    /**@param currentTime needed to implement the timer
     * @param totalTime needed for implement the timer
     * update the timer*/
    @Override
    public void showInputTimer(int currentTime, int totalTime) {
        updateProgressIndicator(currentTime, totalTime);
    }
    /**@param currentTime needed to update the progression bar from loading scene
     * @param totalTime needed to update the progression bar from loading scene*/
    @Override
    public void showConnectionTimer(int currentTime, int totalTime) {
        if (GUIstarter.getStageController().getClass().toString().contains("LoadingSceneController")) {
            getLoadingSceneController().modifyProgress(currentTime, totalTime);
        }
    }

    /**shows an error  because the player can't reach the server, */
    @Override
    public void cantReachServer() {
        GUIstarter.showError(this, "SERVER IS UNREACHABLE", null);
    }

    /** reconnection succeded event */
    @Override
    public void succesfullReconnection() {
        if(GUIstarter.getStageController().getClass().toString().contains("LoadingSceneController")) {
            getLoadingSceneController().changeScene();

            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                GUIstarter.showError(this, "timer error while waiting for loading scene to set", null);
                Thread.currentThread().interrupt();
            }
            updateMap();
            updateKillShotTrack();
            updatePlayer();
        }
        else{
            updateMap();
            updateKillShotTrack();
            updatePlayer();
        }
    }
    /**disconnection event */
    @Override
    public void disconnect() {
        GUIstarter.showError(this, " YOU HAVE BEEN DISCONNECTED. PLEASE TRY TO RECONNECT AT THE SAME IP ADDRESS AND PORT.", null);
    }

    /**count the incoming modelviewevent */
    int counter=0;
    /**@param modelViewEvent , final scoring has finally arrived, the scene needs to be set again*/
    @Override
    public void finalScoring(ModelViewEvent modelViewEvent){

        Platform.runLater(()->{
            if(counter == 0){
                getGameSceneController().removeSelectorSection();
                getGameSceneController().changeSelectorSection(new VBox(), 0.0,0.0,0.0,0.0);
            }
            StackPane rank=new StackPane(new Label(" " + modelViewEvent.getExtraInformation2() + " :" + modelViewEvent.getComponent() + "  with  " + modelViewEvent.getExtraInformation2()));
            ((VBox)getGameSceneController().getSelectorSection().getChildren().get(0)).getChildren().add(rank);
            counter++;
        });

    }




}




