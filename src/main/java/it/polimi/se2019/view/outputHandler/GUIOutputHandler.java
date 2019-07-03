package it.polimi.se2019.view.outputHandler;

import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.model.enumerations.PlayersColors;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.model.events.stateEvent.StateEvent;
import it.polimi.se2019.view.GUIstarter;
import it.polimi.se2019.view.GameSceneController;
import it.polimi.se2019.view.LoadingSceneController;
import it.polimi.se2019.view.components.*;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class GUIOutputHandler implements OutputHandlerInterface {
    // prendi le informazioni da view.modelGate
    /*
     * viewModelGate.getModel()
     *                           entri nel model letteralmente
     * viewModelGate.getMe()
     *                           stringa che rappresenta il nick name del player corrente
     * */
    // dopo che hai fatto l'accesso alla struttura dati devi aggiornare la grafica
    /*
     * getGameSceneController()
     *                           ti ritorna il controller con cui puoi modificare la grafica
     *                           ES.
     *                           getGameSceneController().getKillBackground1().getStyleClass()
     *
     *                           ritorna il puntatore alle classi css dell oggetto KillBackground1
     *  */

    /*
     * creo la lista di kills
     * per ogni kill %n% nella lista di kills
     *       aggiorna la css class in gekKillMainImage %n%
     * */
    // tutte le update devono lavorare tramite thread
    // creo una classe con lo stesso nome del metodo
    // es.
    //
    // private void update%xxx% () {
    //      Platform.runLater( () -> {
    //      controllo con viewModelGate
    //      getGameSceneController().metodidicambiamentodellascena1(...)
    //      getGameSceneController().metodidicambiamentodellascena2(...)
    //      ...
    //      getGameSceneController().metodidicambiamentodellascenan(...)
    //      })
    // }
    // }

    /**
     * launches a UpdateKillShotTrack thread
     */
    private void updateKillShotTrack() {
        System.out.println("UPDATE KILLSHOT TRACK");
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
                if (ViewModelGate.getModel().getKillshotTrack().getKillsV() != null) {
                    if(ViewModelGate.getModel().getKillshotTrack().getKillsV().get(0).isSkull()) {
                        i = ViewModelGate.getModel().getKillshotTrack().getKillsV().size();
                        setAllSkull(i);
                    } else {
                        for (KillsV killV : ViewModelGate.getModel().getKillshotTrack().getKillsV()){
                            if (killV.isSkull()) {
                                removePrevious(getGameSceneController().getKills().get(i));
                                getGameSceneController().getKills().get(i).getStyleClass().add("skull");
                            } else {
                                getGameSceneController().getKills().get(i).setUserData(ViewModelGate.getModel().getPlayers().getPlayer(killV.getKillingPlayer()));
                                removePrevious(getGameSceneController().getKills().get(i));
                                setKillOrOverKill(getGameSceneController().getKills().get(i), killV);

                            }
                            i++;
                        }
                        assert i >= 5;
                        if (i < getGameSceneController().getKills().size()) {
                            setEmpty(i);
                        }
                    }
                }

                    }

            );
        }

        /**
         * remove the previous css style class
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
            System.out.println("killshot"+slot.getKillingPlayer()+slot.isSkull()+"");


        }

        /**
         * @param numberOfStartingSkulls is the number of stack panes to be set as skulls
         */
        private void setAllSkull(int numberOfStartingSkulls) {
            for (int i = 0; i < numberOfStartingSkulls; i++) {
                removePrevious(getGameSceneController().getKills().get(i));
                getGameSceneController().getKills().get(i).getStyleClass().add("skull");
            }
            System.out.println("killshot"+numberOfStartingSkulls);

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
        System.out.println("UPDATE PLAYER");
        updatePowerUpCards();
        updateWeaponCards();
        updatePlayerBoard();
    }

    /**
     *launches a UpdatePowerUpCards thread
     */
    private void updatePowerUpCards() {
        System.out.println("UPDATE POWER UP CARDS");
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

                PlayerV me = ViewModelGate.getModel().getPlayers().getPlayer(ViewModelGate.getMe());

                if (me.getPowerUpCardInHand() != null) {
                    int i = 0;
                    for (PowerUpCardV powerUp : me.getPowerUpCardInHand().getCards()) {
                        getGameSceneController().getListOfPowerUpCardsMainImage().get(i).setUserData(powerUp);
                        removePrevious(getGameSceneController().getListOfPowerUpCardsMainImage().get(i));
                        getGameSceneController().getListOfPowerUpCardsMainImage().get(i).getStyleClass().add("powerUpCard" + powerUp.getID());
                        System.out.println(me.getNickname()+"ha"+powerUp.getID()+"");
                        i++;
                    }
                    if (i < getGameSceneController().getListOfPowerUpCardsMainImage().size()) {
                        for (int j = i; j < getGameSceneController().getListOfPowerUpCardsMainImage().size(); j++) {
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
            int numberOfCards = 24;
            for (int i = 1; i <= numberOfCards; i++) {
                target.getStyleClass().remove("powerUpCard" + i);

            }
        }


    }


    /**launches a UpdateWeaponCards thread*/
    private void updateWeaponCards() {
        System.out.println("UPDATE WEAPON CARDS");
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

                PlayerV me = ViewModelGate.getModel().getPlayers().getPlayer(ViewModelGate.getMe());

                if (me.getWeaponCardInHand().getCards() != null){
                    int i=0;
                    for (WeaponCardV weapon : me.getWeaponCardInHand().getCards()){

                        getGameSceneController().getWeaponCardsMainImage().get(i).setUserData(weapon);
                        removePrevious(getGameSceneController().getWeaponCardsMainImage().get(i));
                        getGameSceneController().getWeaponCardsMainImage().get(i).getStyleClass().add("weaponCard"+weapon.getID());
                        System.out.println(me.getNickname()+"ha"+weapon.getID()+"");
                        i++;
                    }

                    if(i<getGameSceneController().getWeaponCardsMainImage().size()){
                        for (int j = i; j < getGameSceneController().getWeaponCardsMainImage().size(); j++) {
                            removePrevious(getGameSceneController().getWeaponCardsMainImage().get(j));
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
        System.out.println("UPDATE PLAYER BOARD"); //MOMENTANEO
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
        System.out.println("UPDATE DAMAGES"); //MOMENTANEO
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
                for (PlayerV player : ViewModelGate.getModel().getPlayers().getPlayers()) {
                    if (player.getNickname().equals(ViewModelGate.getMe())) {
                        updateDamage(player);
                    }
                }
            });
        }

        private void updateDamage(PlayerV player) {
            int i = 0;
            if (player.getDamageTracker() == null || player.getDamageTracker().getDamageSlotsList().isEmpty()) {
                emptyDamages(i);
            } else {
                for (DamageSlotV damageSlot : player.getDamageTracker().getDamageSlotsList()) {
                    if (i < getGameSceneController().getDamagesMainImage().size()) {
                        addDamages(damageSlot, i);
                        i++;
                    }
                }
                if (i < getGameSceneController().getDamagesMainImage().size()) {
                    emptyDamages(i);
                }
            }
        }


        /**
         * set the damage image with the right css style class after having removed the previous one
         *
         * @param i          is to get the corresponding stack pane
         * @param damageSlot is the damageSlotV whose style is meant to be replaced
         */
        private void addDamages(DamageSlotV damageSlot, int i) {

            PlayersColors color = damageSlot.getShootingPlayerColor();
            for(PlayerV player : ViewModelGate.getModel().getPlayers().getPlayers()) {
                if (player.getNickname().equals(damageSlot.getShootingPlayerNickname())) {
                    getGameSceneController().getDamagesMainImage().get(i).setUserData(player);
                }
            }


            switch (color) {
                case blue:
                    removePrevious(getGameSceneController().getDamagesMainImage().get(i));
                    (getGameSceneController().getDamagesMainImage().get(i).getStyleClass()).add("damageBlue");
                    break;
                case purple:
                    removePrevious(getGameSceneController().getDamagesMainImage().get(i));
                    (getGameSceneController().getDamagesMainImage().get(i).getStyleClass()).add("damagePurple");
                    break;
                case gray:
                    removePrevious(getGameSceneController().getDamagesMainImage().get(i));
                    (getGameSceneController().getDamagesMainImage().get(i).getStyleClass()).add("damageGray");
                    break;
                case green:
                    removePrevious(getGameSceneController().getDamagesMainImage().get(i));
                    (getGameSceneController().getDamagesMainImage().get(i).getStyleClass()).add("damageGreen");
                    break;
                case yellow:
                    removePrevious(getGameSceneController().getDamagesMainImage().get(i));
                    (getGameSceneController().getDamagesMainImage().get(i).getStyleClass()).add("damageYellow");
                    break;
                default:
                    removePrevious(getGameSceneController().getDamagesMainImage().get(i));
                    (getGameSceneController().getDamagesMainImage().get(i).getStyleClass()).add(damageEmpty);
            }
        }

        private String damageEmpty = "damageEmpty";

        /**
         * applies the "damageEmpty" style sheet to all of the damage images
         */
        private void emptyDamages(int i) {

            for (int j = i; j < getGameSceneController().getDamagesMainImage().size(); j++) {
                removePrevious(getGameSceneController().getDamagesMainImage().get(j));
                getGameSceneController().getDamagesMainImage().get(j).getStyleClass().add(damageEmpty);

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
            damage.getStyleClass().remove(damageEmpty);


        }
    }


    /**
     * update the marks track of the client
     */
    private void updateMarks() {
        System.out.println("UPDATE MARKS"); //MOMENTANEO
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
         */
        private void updateMarks(PlayerV player) {

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
         */
        private void emptyMarks(int i) {

            for (int j = i; j < getGameSceneController().getMarkMainImage().size(); j++) {
                removePrevious(getGameSceneController().getMarkMainImage().get(j));
                getGameSceneController().getMarkMainImage().get(j).getStyleClass().add(markEmpty);

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
            for (PlayerV player : ViewModelGate.getModel().getPlayers().getPlayers()) {

                if (player.getNickname().equals(ViewModelGate.getMe())) {

                    updateMarks(player);
                }
            }
        }
    }

    /**
     * updates the death track of the player
     */
    private void updateDeaths() {
        System.out.println("UPDATE DEATHS"); //MOMENTANEO
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

                for (PlayerV player : ViewModelGate.getModel().getPlayers().getPlayers()) {

                    if (player.getNickname().equals(ViewModelGate.getMe()) && (player.getNumberOfDeaths() != 0)) {
                        removePrevious(player);
                        getGameSceneController().getDeathMainImage().get(player.getNumberOfDeaths() - 1).getStyleClass().add("deathSkull");

                    }
                }
            });
        }

        /**
         * @param player is the needed to get the index of the deathSlot from which previous style class is removed
         */
        private void removePrevious(PlayerV player) {

            getGameSceneController().getDeathMainImage().get(player.getNumberOfDeaths() - 1).getStyleClass().remove("deathEmpty");
            getGameSceneController().getDeathMainImage().get(player.getNumberOfDeaths() - 1).getStyleClass().remove("deathSkull");


        }


    }

    /**
     * update the player's nickname
     */
    private void updateNickname() {
        System.out.println("UPDATE NICKNAME"); //MOMENTANEO
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

                for (PlayerV player : ViewModelGate.getModel().getPlayers().getPlayers()) {

                    if (player.getNickname().equals(ViewModelGate.getMe())) {

                        removePrevious();
                        getGameSceneController().getNicknameLabel().setText(player.getNickname());
                        getGameSceneController().getNicknameLabel().getStyleClass().add("nicknameStyle");
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
        System.out.println("UPDATE AMMO BOX"); //MOMENTANEO
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

            Platform.runLater(this::run2);
        }

        private String ammoEmpty = "ammoEmpty";

        /**
         * empty all the ammolists
         */
        private void emptyAmmos(int i, String color) {

            switch (color) {
                case "blue":
                    for (int j = i; j < getGameSceneController().getAmmosMainImage(AmmoCubesColor.blue).size(); j++) {
                        removePrevious(getGameSceneController().getDamagesMainImage().get(j));
                        getGameSceneController().getAmmosMainImage(AmmoCubesColor.blue).get(j).getStyleClass().add(ammoEmpty);
                    }
                    break;
                case "red":
                    for (int j = i; j < getGameSceneController().getAmmosMainImage(AmmoCubesColor.red).size(); j++) {
                        removePrevious(getGameSceneController().getDamagesMainImage().get(j));
                        getGameSceneController().getAmmosMainImage(AmmoCubesColor.red).get(j).getStyleClass().add(ammoEmpty);
                    }
                    break;
                case "yellow":
                    for (int j = i; j < getGameSceneController().getAmmosMainImage(AmmoCubesColor.yellow).size(); j++) {
                        removePrevious(getGameSceneController().getDamagesMainImage().get(j));
                        getGameSceneController().getAmmosMainImage(AmmoCubesColor.yellow).get(j).getStyleClass().add(ammoEmpty);
                    }
                    break;


                default:
                    for (AmmoCubesColor colors : AmmoCubesColor.values()) {
                        for (StackPane ammo : getGameSceneController().getAmmosMainImage(colors)) {
                            removePrevious(ammo);
                            ammo.getStyleClass().add(ammoEmpty);
                        }
                    }
                    break;

            }
        }

        /**
         * @param player the player who ammo box needs to be updated,
         *               this function add the dued ammunitions
         */
        private void addAmmos(PlayerV player) {

            int b = 0;
            int r = 0;
            int y = 0;
            for (AmmoCubesV ammo : player.getAmmoBox().getAmmoCubesList()) {
                AmmoCubesColor color = ammo.getColor();
                switch (color) {
                    case blue:
                        while (b < ammo.getQuantity()) {
                            removePrevious(getGameSceneController().getAmmosMainImage(color).get(b));
                            (getGameSceneController().getAmmosMainImage(color).get(b).getStyleClass()).add("ammoBlue");
                            b++;
                        }
                        break;
                    case red:
                        while (r < ammo.getQuantity()) {
                            removePrevious(getGameSceneController().getAmmosMainImage(color).get(r));
                            (getGameSceneController().getAmmosMainImage(color).get(r).getStyleClass()).add("ammoRed");
                            r++;
                        }
                        break;
                    case yellow:
                        while (y < ammo.getQuantity()) {
                            removePrevious(getGameSceneController().getAmmosMainImage(color).get(y));
                            (getGameSceneController().getAmmosMainImage(color).get(y).getStyleClass()).add("ammoYellow");
                            y++;
                        }
                        break;
                    default:
                        break;
                }
            }
            if (b < getGameSceneController().getAmmosMainImage(AmmoCubesColor.blue).size()) {
                emptyAmmos(b, "blue");
            }
            if (r < getGameSceneController().getAmmosMainImage(AmmoCubesColor.red).size()) {
                emptyAmmos(r, "red");
            }
            if (y < getGameSceneController().getAmmosMainImage(AmmoCubesColor.yellow).size()) {
                emptyAmmos(y, "yellow");
            }

        }

        /**
         * @param ammo whose previous style class is removed
         */
        private void removePrevious(StackPane ammo) {

            ammo.getStyleClass().remove("ammoBlue");
            ammo.getStyleClass().remove("ammoRed");
            ammo.getStyleClass().remove("ammoYellow");
            ammo.getStyleClass().remove("ammoEmpty");


        }

        /**
         * method that update the ammo box
         */
        private void run2() {
            for (PlayerV player : ViewModelGate.getModel().getPlayers().getPlayers()) {
                if (player.getNickname().equals(ViewModelGate.getMe())) {
                    if (player.getAmmoBox().getAmmoCubesList().isEmpty() || player.getAmmoBox() == null) {
                        emptyAmmos(0, "all");
                    } else {
                        addAmmos(player);
                    }
                }
            }
        }
    }


    private void updateMap()   {
        System.out.println("UPDATE MAP: started"); //MOMENTANEO
        new Thread(new UpdateMap()).start();
    }

    private static List<StackPane> listOfPlayersStackPane = new ArrayList<>();
    public static List<StackPane> getListOfPlayersStackPane(){
        return listOfPlayersStackPane;
    }
    public static StackPane getplayerStackPane(String nickname){
        for (StackPane stackPane: getListOfPlayersStackPane()) {
            if(stackPane.getUserData().equals(nickname)){
                return stackPane;
            }
        }
        return null;
    }
    public static void setPlayerStackPane(String nickname, StackPane newStackPane){
        if(getplayerStackPane(nickname)==null){
            getListOfPlayersStackPane().add(newStackPane);
        }
        else {
            getListOfPlayersStackPane().set(getListOfPlayersStackPane().indexOf(getplayerStackPane(nickname)), newStackPane);
        }
    }

    private class UpdateMap implements Runnable{
        @Override
        public void run() {
            if (ViewModelGate.getModel() != null && ViewModelGate.getModel().getBoard() != null && ViewModelGate.getModel().getBoard().getMap() != null) {
                SquareV[][] map = ViewModelGate.getModel().getBoard().getMap();
                StackPane[][] mainImagesMap = getGameSceneController().getMainImagesmap();
                for (int i = 0; i < map.length; i++) { //map.length == 3
                    for (int j = 0; j < map[0].length; j++) { // map[0].lenght == 4
                        SquareV currentSquareV = map[i][j];
                        System.out.println("UPDATE MAP: updating [" + i + "][" + j + "]");
                        StackPane currentMainImageSquare = mainImagesMap[i][j];
                        if(currentSquareV==null){
                            //empty square
                            showEmptySquare(currentMainImageSquare);
                        }
                        else if(currentSquareV.getClass().toString().contains("NormalSquare")){
                            //normal square
                            showNormalSquare((NormalSquareV) currentSquareV, currentMainImageSquare);
                        }
                        else{
                            //spawn point square
                            showSpawnPoint((SpawnPointSquareV) currentSquareV, currentMainImageSquare);
                        }
                        System.out.println("UPDATE MAP: updated  [" + i + "][" + j + "]");
                    }
                }
            }
        }
        private void showEmptySquare(StackPane mainImage){
            Platform.runLater(()->{
                //nothing (?) TODO
                mainImage.getChildren().removeAll(mainImage.getChildren());
            });
        }
        private void showNormalSquare(NormalSquareV square, StackPane mainImage){
            List<PlayerV> playersToShow = getPlayers(square.getX(), square.getY());
            Platform.runLater(()->{
                VBox squareContent = new VBox();
                mainImage.getChildren().add(squareContent);

                    AmmoCardV ammoCard = null;
                    if (!square.getAmmoCards().getCards().isEmpty()) {
                        ammoCard = square.getAmmoCards().getCards().get(0);
                    }
                    if (ammoCard != null) {
                        StackPane ammoImage = new StackPane(new Label(ammoCard.getID())); //don't use a label, but set the image
                        ammoImage.setUserData(ammoCard);
                        squareContent.getChildren().add(ammoImage);
                        VBox.setVgrow(ammoImage, Priority.ALWAYS);
                    }

                    HBox playersHBox = buildPlayers(playersToShow);
                    if (!playersHBox.getChildren().isEmpty()) {
                        squareContent.getChildren().add(playersHBox);
                        VBox.setVgrow(playersHBox, Priority.ALWAYS);
                    }
                });
            }

            private void showSpawnPoint(SpawnPointSquareV square, StackPane mainImage) {
                List<PlayerV> playersToShow = getPlayers(square.getX(), square.getY());
                Platform.runLater(() -> {
                    VBox squareContent = new VBox();
                    mainImage.getChildren().add(squareContent);

                    List<WeaponCardV> weaponCardVS = square.getWeaponCards().getCards();
                    if (!weaponCardVS.isEmpty()) {
                        HBox weaponsHBox = new HBox();
                        for (WeaponCardV w : weaponCardVS) {
                            StackPane weaponImage = new StackPane(new Label(w.getName())); //don't use a label, but set the image
                            weaponImage.setUserData(w);
                            weaponsHBox.getChildren().add(weaponImage);
                            HBox.setHgrow(weaponImage, Priority.ALWAYS);
                        }
                        squareContent.getChildren().add(weaponsHBox);
                        VBox.setVgrow(weaponsHBox, Priority.ALWAYS);
                    }

                    HBox playersHBox = buildPlayers(playersToShow);
                    if (!playersHBox.getChildren().isEmpty()) {
                        squareContent.getChildren().add(playersHBox);
                        VBox.setVgrow(playersHBox, Priority.ALWAYS);
                    }
                });
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
            hBox.setUserData("players");
            for (PlayerV p: playersToShow) {
                StackPane playerStackPane = new StackPane(new Label(p.getNickname())); //don't use a label, but set the image
                playerStackPane.setUserData(p.getNickname());
                setPlayerStackPane(p.getNickname(), playerStackPane);
                hBox.getChildren().add(playerStackPane);
                HBox.setHgrow(playerStackPane, Priority.ALWAYS);
            }
            return hBox;
        }

    }

    /**
     * launches a UpdateStateBar thread
     *
     * @param stateEvent needed to know in which state the game is in a given moment
     */
    private void updateStateBar(StateEvent stateEvent) {
        System.out.println("UPDATE STATE BAR"); //MOMENTANEO


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
     * launch a UpdateProgressIndicator thread
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

    private GameSceneController getGameSceneController() {
        return ((GameSceneController) GUIstarter.getStageController());
    }

    private LoadingSceneController getLoadingSceneController() {
        return ((LoadingSceneController) GUIstarter.getStageController());
    }

    @Override
    public void gameCreated() {
        //probably empty
    }

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


    @Override
    public void setFinalFrenzy(ModelViewEvent modelViewEvent) {
        // shot ViewModelGate.getModel().isFinalFrenzy()
        //updateStateBar(modelViewEvent);
    }

    @Override
    public void finalFrenzyBegun(ModelViewEvent modelViewEvent) {
        if (ViewModelGate.getModel().isHasFinalFrenzyBegun()) {
            //show final frenzy has begun TODO
        }
    }

    @Override
    public void newKillshotTrack(ModelViewEvent modelViewEvent) {
        //update killshotTrack
        updateKillShotTrack();
    }

    @Override
    public void newPlayersList(ModelViewEvent modelViewEvent) {
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        if (GUIstarter.getStageController().getClass().toString().contains("LoadingSceneController")) {
            showPlayerListInLoadingScene(); // TODO
        } else {
            //TODO:
            //    show playerlist during game..
            updatePlayer();
            updateMap();
            updateKillShotTrack();
        }
    }

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


    @Override
    public void newBoard(ModelViewEvent modelViewEvent) {
        //update map
        updateMap();
    }

    @Override
    public void deathOfPlayer(ModelViewEvent modelViewEvent) {
        //update killshot track
        updateKillShotTrack();
        //update playerList
        // TODO
        updatePlayer();
    }

    @Override
    public void movingCardsAround(OrderedCardListV from, OrderedCardListV to, ModelViewEvent modelViewEvent) {
        // TODO LASCIATELO ALLA FINE LUCA
        //update changed cards
        updatePlayer();
    }

    @Override
    public void shufflingCards(ModelViewEvent modelViewEvent) {
        // TODO LASCIATELO ALLA FINE LUCA
        updatePlayer();
    }

    @Override
    public void newColor(ModelViewEvent modelViewEvent) {
        //update players
        updatePlayer();
    }

    @Override
    public void newNickname(ModelViewEvent modelViewEvent) {
        //update Players
        updatePlayer();
    }

    @Override
    public void newPosition(ModelViewEvent modelViewEvent) {
        //update map
        updateMap();
    }

    @Override
    public void newScore(ModelViewEvent modelViewEvent) {
        //update players
        // probably empty
        updatePlayer();
    }

    @Override
    public void addDeathCounter(ModelViewEvent modelViewEvent) {
        //update plaers
        updatePlayer();
    }

    @Override
    public void setFinalFrenzyBoard(ModelViewEvent modelViewEvent) {
        //update Players
        updatePlayer();
    }

    @Override
    public void newAmmoBox(ModelViewEvent modelViewEvent) {
        //update players
        updatePlayer();
    }

    @Override
    public void newDamageTracker(ModelViewEvent modelViewEvent) {
        //update players
        updatePlayer();
    }

    @Override
    public void newMarksTracker(ModelViewEvent modelViewEvent) {
        //update players
        updatePlayer();
    }

    @Override
    public void setCurrentPlayingPlayer(ModelViewEvent modelViewEvent) {
        //update statebar
        //updateStateBar(modelViewEvent);
    }

    @Override
    public void setStartingPlayer(ModelViewEvent modelViewEvent) {
        //update statebar
        //updateStateBar(modelViewEvent);
    }

    @Override
    public void newPlayer(ModelViewEvent modelViewEvent) {
        //update players
        updatePlayer();
    }

    @Override
    public void setAFK(ModelViewEvent modelViewEvent) {
        //update players
        updatePlayer();
    }

    @Override
    public void showInputTimer(int currentTime, int totalTime) {
        //update state section
        updateProgressIndicator(currentTime, totalTime);
    }

    @Override
    public void showConnectionTimer(int currentTime, int totalTime) {
        if (GUIstarter.getStageController().getClass().toString().contains("LoadingSceneController")) {
            ((LoadingSceneController) GUIstarter.getStageController()).modifyProgress(currentTime, totalTime);
        }
    }

    @Override
    public void cantReachServer() {
        //show error pop up
        // TODO
        GUIstarter.showError(this, "can't reach server", null);
    }

    @Override
    public void succesfullReconnection() {
        //TODO
        //start the GAME.fxml
        //update everything in the gui
    }

    @Override
    public void disconnect() {
        //show pop up for disconnection TODO
    }

    @Override
    public void finalScoring(ModelViewEvent modelViewEvent) {
        //final scene TODO
    }


}




