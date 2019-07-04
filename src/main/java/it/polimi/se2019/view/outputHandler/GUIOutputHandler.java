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
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import java.util.concurrent.TimeUnit;

//TODO ciao ludo, ti lascio dei todo da controllare

//TODO intelliJ di luca non gli permette di fare gli uml, l'idea sarebbe che domani per le 16 ci trovassimo tutti e 3 al poli a lavorare
// e io gli prestavo il pc pe rfare gli uml, che dal mio si riesce

//TODO buonanotte

//TODO ludo sistema bene gli eventHandler che avevamo fatto, ora li ho implementati quindi puoi vedere come vengono mostrate le cose se fai una partita,
// quello del player non sembra andare bene; se hai problemi di layout grafici perchè i componenti non si comportano come pensi, aspettami e ti aiuto

//TODO a quanto pare sono obbligatori i tag @author nel javaDoc delle classi, luca sta facendo i suoi, se hai tempo e nulla da fare, beh...

public class GUIOutputHandler implements OutputHandlerInterface {

    /**
     * launches a UpdateKillShotTrack thread
     */
    private void updateKillShotTrack() {
        (new Thread(new UpdateKillShotTrack())).start();
    }

    private class UpdateKillShotTrack implements Runnable {
        //TODO controlla che la kill shot track sia stata ben inizializzata nel GameSceneController
        //TODO controlla che effettivamente funzioni
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
                        setAllSkull(i); //TODO gli skull sono 8, se la size è 5 cosa te ne fai degli altri 3 che avanzano?
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
                        if (i < getGameSceneController().getKills().size()) { //TODO non si capisce, stando al for di sopra: i == ...size()-1
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

                PlayerV me = ViewModelGate.getModel().getPlayers().getPlayer(ViewModelGate.getMe());

                if (me.getPowerUpCardInHand() != null) {
                    int i = 0;
                    for (PowerUpCardV powerUp : me.getPowerUpCardInHand().getCards()) { // TODO perchè usare un for each se poi usi un contatore?
                        if(i<2) {
                            getGameSceneController().getListOfPowerUpCardsMainImage().get(i).setUserData(powerUp);
                            removePrevious(getGameSceneController().getListOfPowerUpCardsMainImage().get(i));
                            getGameSceneController().getListOfPowerUpCardsMainImage().get(i).getStyleClass().add("powerUpCard" + powerUp.getID());
                            System.out.println("power up card main image numero " + i + " è " + powerUp.getName() + ", ID: " + powerUp.getID());
                            i++;
                        }
                        else{
                            System.out.println("power up card main image numero " + i + " è " + powerUp.getName() + ", ID: " + powerUp.getID());
                        }
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

                    for (WeaponCardV weapon : me.getWeaponCardInHand().getCards()) { // TODO perchè usare un for each se poi usi un contatore?
                        if(i<3){
                            getGameSceneController().getWeaponCardsMainImage().get(i).setUserData(weapon);
                            removePrevious(getGameSceneController().getWeaponCardsMainImage().get(i));
                            getGameSceneController().getWeaponCardsMainImage().get(i).getStyleClass().add("weaponCard" + weapon.getID());
                            System.out.println("weapon card main image numero " + i + " è " + weapon.getName() + ", ID: " + weapon.getID());
                            i++;
                        }
                        else{
                            GUIstarter.showError(this, "THE PLAYER HAS MORE THAN THREE WEAPON CARDS IN HAND", null);
                        }
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
                for (PlayerV player : ViewModelGate.getModel().getPlayers().getPlayers()) { //TODO c'è un metodo nella PlayerListV fatto apposta..
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
                    //TODO non cosiderare la getDamagesMainImage() come un size "affidabile", considera che teoricamente sarebbero 12 slot
                    // ma la getDamagesMainImage() sono 13... dobbiamo usare questo tredicesimo slot come luogo in cui indicare tutti gli extra damage oltre il 12
                    if (i < getGameSceneController().getDamagesMainImage().size()) {
                        addDamages(damageSlot, i);
                        i++;
                    }
                    else{
                        //TODO bisogna riempire il tredicesimo slot
                        // la mia idea era metterci un label con scritto quanti extra damages ci sono, per esempio "+1" o "+2" etc.
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

            if(ViewModelGate.getModel().isHasFinalFrenzyBegun()){
                for (int j = i; j < getGameSceneController().getDamagesMainImage().size(); j++) {
                    removePrevious(getGameSceneController().getDamagesMainImage().get(j));
                    getGameSceneController().getDamagesMainImage().get(j).getStyleClass().add("damageEmptyFF");
                }
            }
            else{
                for (int j = i; j < getGameSceneController().getDamagesMainImage().size(); j++) {
                removePrevious(getGameSceneController().getDamagesMainImage().get(j));
                getGameSceneController().getDamagesMainImage().get(j).getStyleClass().add(damageEmpty);
                }
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
            //TODO lol, loved the "::", ma perchè lo stai facendo? cioè non capisco perchè non scrivi direttamente
            // qui il codice della run2

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
                        removePrevious( getGameSceneController().getDeathMainImage().get(player.getNumberOfDeaths() - 1));
                        getGameSceneController().getDeathMainImage().get(player.getNumberOfDeaths() - 1).getStyleClass().add("deathSkull");
                    }
                }

                if(ViewModelGate.getModel().isHasFinalFrenzyBegun()){ //TODO non usare questa variabile ma quella nel player.ishasFinalFrenzyBoard

                    for(StackPane kills: getGameSceneController().getDamagesMainImage()) {
                        removePrevious(kills);
                        kills.getStyleClass().add("emptyDeathFF");
                        //TODO riempi i label dei punti in 2,1,1,1,1,1---
                    }
                }
                else{
                    //TODO se non sei in final frenzy... comunque riempi i label dei punti nel solito 8,6,4,2,1,1,1
                    //TODO però forse non è necessario perchè sono già inizializzati...
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
        //TODO ma perchè ora ci sono delle gif invece dei color? ps mi van bene le gif
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

            Platform.runLater(this::run2); //TODO ancora non capisco la run2, ma apprezzo i "::"
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
         *               this function add the due ammunitions
         */
        private void addAmmos(PlayerV player) {

            int b = 0;
            int r = 0;
            int y = 0;
            for (AmmoCubesV ammo : player.getAmmoBox().getAmmoCubesList()) {
                AmmoCubesColor color = ammo.getColor();
                switch (color) {
                    case blue:
                        while (b < ammo.getQuantity()) { //TODO usi tutto ma non i for i ahahahha, vabbiè
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
        private void run2() { //TODO controlla che le cose nel model esistano prima di accederci, (p.s. non ci ho dato un occhio nelle altre update, controlla anche in quelle
            for (PlayerV player : ViewModelGate.getModel().getPlayers().getPlayers()) {
                if (player.getNickname().equals(ViewModelGate.getMe())) {
                    //TODO inversi l'ordine di questo if, perchè se fosse null, quando chiami la .getAmmoCubesList() ti darebbe nullPointerException
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
        new Thread(new UpdateMap()).start();
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

                //TODO magari la cambieremo perchè sinceramente non mi piace molto la textArea, forse meglio usare Label di fila che sono resizable
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

        //updateStateBar();
    }

    @Override
    public void finalFrenzyBegun(ModelViewEvent modelViewEvent) {
        if (ViewModelGate.getModel().isHasFinalFrenzyBegun()) {
            //show final frenzy has begun TODO
        }
    }

    @Override
    public void newKillshotTrack(ModelViewEvent modelViewEvent) {

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
        updateNickname();
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

    }

    @Override
    public void addDeathCounter(ModelViewEvent modelViewEvent) {
        //update plaers
        updateDeaths();
    }

    @Override
    public void setFinalFrenzyBoard(ModelViewEvent modelViewEvent) {
      updateDamage();
      updateDeaths();
    }

    @Override
    public void newAmmoBox(ModelViewEvent modelViewEvent) {
        //update players
        updateAmmobox();
    }

    @Override
    public void newDamageTracker(ModelViewEvent modelViewEvent) {
        //update players
        updateDamage();
    }

    @Override
    public void newMarksTracker(ModelViewEvent modelViewEvent) {
        //update players
        updateMarks();
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
        //empty.. ?
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




