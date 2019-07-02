package it.polimi.se2019.view.outputHandler;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.model.NormalSquare;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.SpawnPointSquare;

import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.model.enumerations.PlayersColors;
import it.polimi.se2019.model.events.Event;
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

import javafx.scene.text.Text;
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
    /**********************************/
    /**** Update of kill shot track  **/            /*  LUCA  */
    /**********************************/

    private void updateKillShotTrack() {
        System.out.println("UPDATE KILLSHOT TRACK");
        (new Thread(new UpdateKillShotTrack())).start();
    }
    private class UpdateKillShotTrack implements Runnable{
        @Override
        /** */
        public void run() {
            Platform.runLater(
                    this::runner
            );
        }
        /** Private methods         */
        /**         Specific        */
        private String colorToString(PlayersColors color) {
            String stringColor = "";
            switch (color)
            {
                case blue:
                    stringColor = "Blue";
                    break;
                case green:
                    stringColor = "Green";
                    break;
                case yellow:
                    stringColor = "Yellow";
                    break;
                case gray:
                    stringColor = "Gray";
                    break;
                case purple:
                    stringColor = "Purple";
                    break;
            }
            return stringColor;
        }
        private void setSkull(StackPane target)
        {
            target.getStyleClass().add("skull");
        }
        private void setKill(StackPane target,PlayersColors color)
        {

            target.getStyleClass().add("kill" + colorToString(color));
        }
        private void setOverKill(StackPane target, PlayersColors color)
        {

            target.getStyleClass().add("overKill" + colorToString(color));
        }
        /**         General         */
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
        }

        private void runner() {
            /* load lists */
                List<KillsV> kills = ViewModelGate.getModel().getKillshotTrack().getKillsV();
                List<StackPane> killSlots = new ArrayList<>();
            /* check if killShotTrack is initalized */
            if ( kills != null) {
                /* fill stackPane list */
                                    killSlots.add(getGameSceneController().getKillMainImage1());
                                    killSlots.add(getGameSceneController().getKillMainImage2());
                                    killSlots.add(getGameSceneController().getKillMainImage3());
                                    killSlots.add(getGameSceneController().getKillMainImage4());
                                    killSlots.add(getGameSceneController().getKillMainImage5());
                                    killSlots.add(getGameSceneController().getKillMainImage6());
                                    killSlots.add(getGameSceneController().getKillMainImage7());
                                    killSlots.add(getGameSceneController().getKillMainImage8());
                /* for every killsV    */
                                    for(int k = 0;k < kills.size();k++) {
                                        KillsV    currentKill       = kills.get(k);
                                        StackPane currentKillSlot   = killSlots.get(k);
                                            if(currentKill.isSkull()) {
                                                    // la current kill è uno skull

                                                       removePrevious(currentKillSlot);

                                                       setSkull(currentKillSlot) ;

                                            } else {
                                                   if(!currentKill.isOverKill()) {
                                                   //   la current kill è un kill
                                                       removePrevious(currentKillSlot);
                                                       PlayersColors color =  ViewModelGate.getModel().getPlayers().getPlayer(currentKill.getKillingPlayer()).getColor();
                                                       setKill(currentKillSlot,color);
                                                                                 }  else  {
                                                   //   la current kill è una overkill
                                                       removePrevious(currentKillSlot);
                                                       PlayersColors color =  ViewModelGate.getModel().getPlayers().getPlayer(currentKill.getKillingPlayer()).getColor();
                                                       setOverKill(currentKillSlot, color);
                                                                                          }

                                                   }

                                    }

                                }
        }
    }
    private void updatePlayer()    {
        System.out.println("UPDATE PLAYER");
        updatePowerUpCards();
        updateWeaponCards();
        updatePlayerBoard();
    }                                   // ...

    /** */
    private void updatePowerUpCards(){  // luca
        System.out.println("UPDATE POWER UP CARDS");
        (new Thread(new UpdatePowerUpCards())).start();
    }
    private class UpdatePowerUpCards implements  Runnable {
        @Override
        public void run() {
            Platform.runLater(this::runner);
        }
        /***/
        private void removePrevious(StackPane target) {
            int numberOfCards = 4;
            for(int i = 1; i < numberOfCards ; i++) {
                target.getStyleClass().remove("powerUpCard" + i);
            }
        }
        /***/
        private void runner() {
            PlayerV me = ViewModelGate.getModel().getPlayers().getPlayer(
                    ViewModelGate.getMe()
            );

            OrderedCardListV<PowerUpCardV> powerUpCards = me.getPowerUpCardInHand();
            if(powerUpCards != null)
            {
                // verified weaponCards is not null
                // initialize stackPaneList

                List<StackPane> powerStackPanes = new ArrayList<>();

                powerStackPanes.add(getGameSceneController().getPowerUpCardMainImage1());
                powerStackPanes.add(getGameSceneController().getPowerUpCardMainImage2());

                for(int p = 0; p < powerUpCards.getCards().size();p++) {
                    PowerUpCardV currentW = powerUpCards.getCards().get(p);
                    StackPane   currentStackPane = powerStackPanes.get(p);
                    removePrevious(currentStackPane);
                    currentStackPane.getStyleClass().add("powerUpCard" + currentW.getID()); //TODO luca le classi che hai messo nel css vanno solo da 1 a 4
                }

            }
        }
    }
    /** */
    private void updateWeaponCards() {
        System.out.println("UPDATE WEAPON CARDS");
        (new Thread(new UpdateWeaponCards())).start();
    }
    private class UpdateWeaponCards implements Runnable {
        @Override
        public void run() {
            Platform.runLater(this::runner);
        }
        /***/
        private void removePrevious(StackPane target) {
            int numberOfCards = 21;
            for(int i = 1; i < numberOfCards ; i++) {
                target.getStyleClass().remove("weaponCard" + i);
            }
        }
        /***/
        private void runner() {
            PlayerV me = ViewModelGate.getModel().getPlayers().getPlayer(
                    ViewModelGate.getMe()
            );

            OrderedCardListV<WeaponCardV> weaponCards = me.getWeaponCardInHand();
            if(weaponCards != null)
            {
                // verified weaponCards is not null
                // initialize stackPaneList

                List<StackPane> weaponStackPanes = new ArrayList<>();

                weaponStackPanes.add(getGameSceneController().getWeaponCardMainImage1());
                weaponStackPanes.add(getGameSceneController().getWeaponCardMainImage2());
                weaponStackPanes.add(getGameSceneController().getWeaponCardMainImage3());

                for(int w = 0; w < weaponCards.getCards().size();w++) {
                    WeaponCardV currentW = weaponCards.getCards().get(w);
                    StackPane   currentStackPane = weaponStackPanes.get(w);
                    removePrevious(currentStackPane);
                    currentStackPane.getStyleClass().add("weaponCard" + currentW.getID());
                }

            }
        }
    }

    private void updatePlayerBoard() {
        System.out.println("UPDATE PLAYER BOARD"); //MOMENTANEO
        updateDamage();
        updateMarks();
        updateDeaths();
        updateNickname();
        updateAmmobox();
    }



   /**update the damage track of the player*/
    private void updateDamage(){
        System.out.println("UPDATE DAMAGES"); //MOMENTANEO
        (new Thread(new UpdateDamage())).start();
    }
    /**this class implements a thread launched in updateDamage function in order to modify the damage board of the player*/
    private class UpdateDamage implements Runnable{

        /**the said thread launched look for the right player and
         * updates their damage board, it checks one by one all of the damage slots,
         * removes the previous css style class
         * and applies the right new one*/
        @Override
        public void run(){
            Platform.runLater(()->{
                for (PlayerV player: ViewModelGate.getModel().getPlayers().getPlayers()){
                    if(player.getNickname().equals(ViewModelGate.getMe())){
                      updateDamage(player);
                    }
                }
            });
        }

        private void updateDamage(PlayerV player){
            int i = 0;
            if(player.getDamageTracker() == null || player.getDamageTracker().getDamageSlotsList().isEmpty()){
                emptyDamages(i);
            }
            else {
                for(DamageSlotV damageSlot : player.getDamageTracker().getDamageSlotsList()){
                    if(i<getGameSceneController().getDamagesMainImage().size()){
                        addDamages(damageSlot, i);
                        i++;
                    }
                }
                if(i<getGameSceneController().getDamagesMainImage().size()){
                    emptyDamages(i);
                }
            }
        }


        /**set the damage image with the right css style class after having removed the previous one
         * @param i is to get the corresponding stack pane
         * @param damageSlot is the damageSlotV whose style is meant to be replaced*/
        private void addDamages(DamageSlotV damageSlot, int i){

            PlayersColors color = damageSlot.getShootingPlayerColor();
            for (PlayerV player: ViewModelGate.getModel().getPlayers().getPlayers()){
                if(player.getNickname().equals(damageSlot.getShootingPlayerNickname())) {
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

        private String damageEmpty="damageEmpty";
        /**applies the "damageEmpty" style sheet to all of the damage images*/
        private void emptyDamages(int i){

            for (int j = i; j < getGameSceneController().getDamagesMainImage().size(); j++){
                removePrevious(getGameSceneController().getDamagesMainImage().get(j));
                getGameSceneController().getDamagesMainImage().get(j).getStyleClass().add(damageEmpty);

            }
        }
        /**@param damage from whose previous style class is removed*/
        private void removePrevious(StackPane damage){

            damage.getStyleClass().remove("damageBlu");
            damage.getStyleClass().remove("damagePurple");
            damage.getStyleClass().remove("damageYellow");
            damage.getStyleClass().remove("damageGreen");
            damage.getStyleClass().remove("damageGray");
            damage.getStyleClass().remove(damageEmpty);


        }
    }


    /**update the marks track of the client*/
    private void updateMarks(){
        System.out.println("UPDATE MARKS"); //MOMENTANEO
        (new Thread(new UpdateMarks())).start();
    }



/**this class implements a thread launched in updateMarks in order to modify the mark board of the player*/
    private class UpdateMarks implements Runnable{

    /**the said thread launched look for the right player and
     * updates their damage board, it checks one by one all of the mark slots,
     * removes the previous css style class
     * and applies the right new one*/
        @Override
        public void run(){

            Platform.runLater(this::run2);
        }

        /**update marks*/
        private void updateMarks(PlayerV player){

            int i = 0;
            if(player.getMarksTracker().getMarkSlotsList().isEmpty()||player.getMarksTracker()==null){
                emptyMarks(0);
            }

            else {
                for (MarkSlotV markSlot : player.getMarksTracker().getMarkSlotsList()) {
                    setMarks(i, markSlot);
                    i++;
                }
                if(i<getGameSceneController().getMarkMainImage().size()){
                    emptyMarks(i);
                }
            }
        }
        /**set the markslot with the right css style class after having removed the previous one
         * @param i is to get the corresponding stack pane
         * @param markSlot is the markSlotV whose style is meant to be replaced*/
        private void setMarks(int i, MarkSlotV markSlot){
            PlayersColors color = ViewModelGate.getModel().getPlayers().getPlayer(markSlot.getMarkingPlayer()).getColor();
            ((Label)(getGameSceneController().getMarkMainImage().get(i).getChildren().get(0))).setText(Integer.toString(markSlot.getQuantity()));

            for (PlayerV player: ViewModelGate.getModel().getPlayers().getPlayers()){
                if(player.getNickname().equals(markSlot.getMarkingPlayer())) {
                    getGameSceneController().getDamagesMainImage().get(i).setUserData(player);
                }
            }

            switch (color) {
                case blue:
                        removePrevious(getGameSceneController().getMarkMainImage().get(i));
                       (getGameSceneController().getMarkMainImage().get(i).getStyleClass()).add("markBlue");
                   break;
                case
                        purple:removePrevious(getGameSceneController().getMarkMainImage().get(i));
                    (getGameSceneController().getMarkMainImage().get(i).getStyleClass()).add("markPurple");
                    break;
                case gray:removePrevious(getGameSceneController().getMarkMainImage().get(i));
                    (getGameSceneController().getMarkMainImage().get(i).getStyleClass()).add("markGray");
                    break;
                case green:removePrevious(getGameSceneController().getMarkMainImage().get(i));
                    (getGameSceneController().getMarkMainImage().get(i).getStyleClass()).add("markGreen");
                    break;
                case yellow:removePrevious(getGameSceneController().getMarkMainImage().get(i));
                    (getGameSceneController().getMarkMainImage().get(i).getStyleClass()).add("markYellow");
                    break;
                default:removePrevious(getGameSceneController().getMarkMainImage().get(i));
                    (getGameSceneController().getMarkMainImage().get(i).getStyleClass()).add(markEmpty);
                    break;
            }

        }

        private String markEmpty="markEmpty";
        /**applies the "markEmpty" css style class to all of the marks*/
        private void emptyMarks(int i) {

            for (int j = i; j < getGameSceneController().getMarkMainImage().size(); j++) {
                removePrevious(getGameSceneController().getMarkMainImage().get(j));
                getGameSceneController().getMarkMainImage().get(j).getStyleClass().add(markEmpty);

            }
        }

        /**@param  mark from which previous style class is removed*/
        private void removePrevious(StackPane mark){

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

    /**updates the death track of the player*/
    private void updateDeaths(){
        System.out.println("UPDATE DEATHS"); //MOMENTANEO
        (new Thread(new UpdateDeaths())).start();
    }



    /**this class implements a thread launched in updateDeaths in order to modify the death track of the player*/
    private class UpdateDeaths implements Runnable{

        /**the said thread launched look for the right player and
         * updates their death tracker, it extrapolate which one to update by using the function "player.getNumberOfDeath()",
         * removes the previous css style class
         * and applies the right new one*/
        @Override
        public void run(){

            Platform.runLater(()->{

                for (PlayerV player: ViewModelGate.getModel().getPlayers().getPlayers()){

                    if(player.getNickname().equals(ViewModelGate.getMe())&&(player.getNumberOfDeaths()!=0)){
                            removePrevious(player);
                            getGameSceneController().getDeathMainImage().get(player.getNumberOfDeaths()-1).getStyleClass().add("deathSkull");

                    }
                }
            });
        }

        /**@param player is the needed to get the index of the deathSlot from which previous style class is removed*/
        private void removePrevious(PlayerV player){

            getGameSceneController().getDeathMainImage().get(player.getNumberOfDeaths()-1).getStyleClass().remove("deathEmpty");
            getGameSceneController().getDeathMainImage().get(player.getNumberOfDeaths()-1).getStyleClass().remove("deathSkull");



        }


    }
    /**update the player's nickname*/
    private void updateNickname(){
        System.out.println("UPDATE NICKNAME"); //MOMENTANEO
        (new Thread(new UpdateNickname())).start();
    }



    /**this class implements a thread launched in updateNickname in order to set the player's NickName*/
    private class UpdateNickname implements Runnable{

        /**the said thread launched look for the right player and
         * sets their nickname,
         * removes the previous css style class
         * and applies the right new one*/
        @Override
        public void run(){

            Platform.runLater(()->{

                for (PlayerV player: ViewModelGate.getModel().getPlayers().getPlayers()){

                    if(player.getNickname().equals(ViewModelGate.getMe())){

                        removePrevious();
                        getGameSceneController().getNicknameLabel().setText(player.getNickname());
                        getGameSceneController().getNicknameLabel().getStyleClass().add("nicknameStyle");
                        PlayersColors color=player.getColor();
                        switch (color){
                            case yellow:getGameSceneController().getNicknameBackGround().getStyleClass().add("nicknameBackgroundYellow");
                            break;
                            case blue: getGameSceneController().getNicknameBackGround().getStyleClass().add("nicknameBackgroundBlue");
                            break;
                            case green:getGameSceneController().getNicknameBackGround().getStyleClass().add("nicknameBackgroundGreen");
                            break;
                            case gray:getGameSceneController().getNicknameBackGround().getStyleClass().add("nicknameBackgroundGray");
                            break;
                            case purple:getGameSceneController().getNicknameBackGround().getStyleClass().add("nicknameBackgroundPurple");
                            break;
                            default:getGameSceneController().getNicknameBackGround().getStyleClass().add("nicknameBackground");

                        }



                    }
                }
            });
        }

        /**previous style class is removed*/
        private void removePrevious(){

            getGameSceneController().getNicknameLabel().getStyleClass().remove("nicknameStyle");
            getGameSceneController().getNicknameBackGround().getStyleClass().remove("nicknameBackground");

        }
    }
    /**update the ammo boxe*/
    private void updateAmmobox(){
        System.out.println("UPDATE AMMO BOX"); //MOMENTANEO
        (new Thread(new UpdateAmmobox())).start();
    }



    /**this class implements a thread launched in updateAmmobox in order to modify the box containing the ammunitions
     *  owned by the player*/
    private class UpdateAmmobox implements Runnable{

        /**the said thread launched look for the right player and
         * updates their ammo box, it checks one by one all of the ammo in the ammo box,
         * removes the previous css style class
         * and applies the right new one*/
        @Override
        public void run(){

            Platform.runLater(this::run2);
        }

        private String ammoEmpty="ammoEmpty";
        /**empty all the ammolists*/
        private void emptyAmmos(int i, String color){

            switch (color){
                case "blue":
                    for (int j = i; j < getGameSceneController().getAmmosMainImage(AmmoCubesColor.blue).size(); j++){
                    removePrevious(getGameSceneController().getDamagesMainImage().get(j));
                    getGameSceneController().getAmmosMainImage(AmmoCubesColor.blue).get(j).getStyleClass().add(ammoEmpty);
                    }
                    break;
                case"red":
                    for(int j = i; j < getGameSceneController().getAmmosMainImage(AmmoCubesColor.red).size(); j++){
                    removePrevious(getGameSceneController().getDamagesMainImage().get(j));
                    getGameSceneController().getAmmosMainImage(AmmoCubesColor.red).get(j).getStyleClass().add(ammoEmpty);
                }
                    break;
                case"yellow":
                    for(int j = i; j < getGameSceneController().getAmmosMainImage(AmmoCubesColor.yellow).size(); j++) {
                        removePrevious(getGameSceneController().getDamagesMainImage().get(j));
                        getGameSceneController().getAmmosMainImage(AmmoCubesColor.yellow).get(j).getStyleClass().add(ammoEmpty);
                    }
                    break;


                default:
                    for (AmmoCubesColor colors : AmmoCubesColor.values()){
                        for (StackPane ammo : getGameSceneController().getAmmosMainImage(colors)){
                            removePrevious(ammo);
                            ammo.getStyleClass().add(ammoEmpty);
                        }
                    }
                    break;

            }
        }

        /**@param player the player who ammo box needs to be updated,
         * this function add the dued ammunitions*/
        private void addAmmos(PlayerV player){

            int b=0;
            int r=0;
            int y=0;
            for(AmmoCubesV ammo : player.getAmmoBox().getAmmoCubesList()){
                AmmoCubesColor color = ammo.getColor();
                switch (color){
                    case blue:
                        while(b<ammo.getQuantity()){
                            removePrevious(getGameSceneController().getAmmosMainImage(color).get(b));
                            (getGameSceneController().getAmmosMainImage(color).get(b).getStyleClass()).add("ammoBlue");
                            b++;
                        }
                        break;
                    case red:
                        while(r<ammo.getQuantity()) {
                            removePrevious(getGameSceneController().getAmmosMainImage(color).get(r));
                            (getGameSceneController().getAmmosMainImage(color).get(r).getStyleClass()).add("ammoRed");
                            r++;
                        }
                        break;
                    case yellow:
                        while(y<ammo.getQuantity()) {
                            removePrevious(getGameSceneController().getAmmosMainImage(color).get(y));
                            (getGameSceneController().getAmmosMainImage(color).get(y).getStyleClass()).add("ammoYellow");
                            y++;
                        }
                        break;
                    default:
                        break;
                }
            }
            if(b<getGameSceneController().getAmmosMainImage(AmmoCubesColor.blue).size()-1){
                emptyAmmos(b, "blue");
            }
            if(r<getGameSceneController().getAmmosMainImage(AmmoCubesColor.red).size()-1){
                emptyAmmos(r, "red");
            }
            if(y<getGameSceneController().getAmmosMainImage(AmmoCubesColor.yellow).size()-1){
                emptyAmmos(y, "yellow");
            }

        }

        /**@param ammo whose previous style class is removed*/
        private void removePrevious(StackPane ammo){

            ammo.getStyleClass().remove("ammoBlue");
            ammo.getStyleClass().remove("ammoRed");
            ammo.getStyleClass().remove("ammoYellow");
            ammo.getStyleClass().remove("ammoEmpty");


        }

        /**method that update the ammo box*/
        private void run2() {
            for (PlayerV player : ViewModelGate.getModel().getPlayers().getPlayers()) {
                if (player.getNickname().equals(ViewModelGate.getMe())) {
                    if (player.getAmmoBox().getAmmoCubesList().isEmpty()||player.getAmmoBox()==null) {
                        emptyAmmos(0, "all");
                    }
                  else{  addAmmos(player); }
                }
            }
        }
    }

    /*TODO*/
    private void updateMap()   {
        System.out.println("UPDATE MAP"); //MOMENTANEO
        new Thread(new UpdateMap()).start();
    }
    private class UpdateMap implements Runnable{
        @Override
        public void run() {
            if (ViewModelGate.getModel() != null && ViewModelGate.getModel().getBoard() != null && ViewModelGate.getModel().getBoard().getMap() != null) {
                SquareV[][] map = ViewModelGate.getModel().getBoard().getMap();
                StackPane[][] backgroundMap = getGameSceneController().getBackgroundsMap();
                StackPane[][] mainImagesMap = getGameSceneController().getMainImagesmap();
                for (int i = 0; i < map.length; i++) { //map.length == 3
                    for (int j = 0; j < map[0].length; j++) { // map[0].lenght == 4
                        SquareV currentSquareV = map[i][j];
                        StackPane currentBackGroundSquare = backgroundMap[i][j];
                        StackPane currentMainImageSquare = mainImagesMap[i][j];
                        if(currentSquareV==null){
                            //empty square
                            showEmptySquare(currentBackGroundSquare, currentMainImageSquare);
                        }
                        else if(currentSquareV.getClass().toString().contains("NormalSquare")){
                            //normal square
                            showNormalSquare((NormalSquareV) currentSquareV, currentBackGroundSquare, currentMainImageSquare);
                        }
                        else{
                            //spawn point square
                            showSpawnPoint((SpawnPointSquareV) currentSquareV, currentBackGroundSquare, currentMainImageSquare);
                        }
                    }
                }
            }
        }
        private void showEmptySquare(StackPane background, StackPane mainImage){
            Platform.runLater(()->{
                //nothing (?) TODO
                mainImage.getChildren().removeAll(mainImage.getChildren());
            });
        }
        private void showNormalSquare(NormalSquareV square, StackPane background, StackPane mainImage){
            List<PlayerV> playersToShow = getPlayers(square.getX(), square.getY());
            Platform.runLater(()->{
                VBox squareContent = new VBox();
                mainImage.getChildren().add(squareContent);

                AmmoCardV ammoCard = null;
                if(!square.getAmmoCards().getCards().isEmpty()) {
                    ammoCard = square.getAmmoCards().getCards().get(0);
                }
                if(ammoCard!=null){
                    StackPane ammoImage = new StackPane(new Label(ammoCard.getID())); //don't use a label, but set the image
                    ammoImage.setUserData(ammoCard);
                    squareContent.getChildren().add(ammoImage);
                    VBox.setVgrow(ammoImage, Priority.ALWAYS);
                }

                HBox playersHBox = buildPlayers(playersToShow);
                if(!playersHBox.getChildren().isEmpty()){
                    squareContent.getChildren().add(playersHBox);
                    VBox.setVgrow(playersHBox, Priority.ALWAYS);
                }
            });
        }
        private void showSpawnPoint(SpawnPointSquareV square,StackPane background, StackPane mainImage){
            List<PlayerV> playersToShow = getPlayers(square.getX(), square.getY());
            Platform.runLater(()->{
                VBox squareContent = new VBox();
                mainImage.getChildren().add(squareContent);

                List<WeaponCardV> weaponCardVS = square.getWeaponCards().getCards();
                if(!weaponCardVS.isEmpty()){
                    HBox weaponsHBox = new HBox();
                    for (WeaponCardV w:weaponCardVS) {
                        StackPane weaponImage = new StackPane(new Label(w.getName())); //don't use a label, but set the image
                        weaponImage.setUserData(w);
                        weaponsHBox.getChildren().add(weaponImage);
                        HBox.setHgrow(weaponImage,Priority.ALWAYS);
                    }
                    squareContent.getChildren().add(weaponsHBox);
                    VBox.setVgrow(weaponsHBox, Priority.ALWAYS);
                }

                HBox playersHBox = buildPlayers(playersToShow);
                if(!playersHBox.getChildren().isEmpty()){
                    squareContent.getChildren().add(playersHBox);
                    VBox.setVgrow(playersHBox, Priority.ALWAYS);
                }
            });
        }
        private List<PlayerV> getPlayers(int x, int y){
            List<PlayerV> players =new ArrayList<>();
            for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
                if(p.getY()!=null && p.getX()!=null && p.getX() == x && p.getY() == y){
                    players.add(p);
                }
            }
            return players;
        }

        private HBox buildPlayers(List<PlayerV> playersToShow){
            HBox hBox = new HBox();
            for (PlayerV p: playersToShow) {
                StackPane player = new StackPane(new Label(p.getNickname())); //don't use a label, but set the image
                player.setUserData(p);
                hBox.getChildren().add(player);
                HBox.setHgrow(player, Priority.ALWAYS);
            }
            return hBox;
        }

    }

    private void updateStateBar(Event stateEvent) {
        System.out.println("UPDATE STATE BAR"); //MOMENTANEO


        (new Thread(new UpdateStateBar((StateEvent) stateEvent))).start();
    }

    private class UpdateStateBar implements Runnable{

        StateEvent stateEvent;
         UpdateStateBar(StateEvent stateEvent){
            this.stateEvent=stateEvent;
        }

        @Override
        public void run(){
            Platform.runLater(()->{

                String title;
                String description;

                switch(stateEvent.getState()){
                   case"BotMoveState":
                       title="BOT IS MOVING";
                       description="is moving the bot";
                       break;
                   case"BotShootState":
                       title="BOT IS ABOUT TO SHOOT";
                       description="is choosing whom the bot may be about to blow the head off";
                       break;
                   case"ChooseHowToPayState":
                       title="PAYMENT DETAILS ARE BEING DISCUSSED";
                       description="is choosing how to purchase";
                       break;
                   case"FFSetUpState":
                       title="FINAL FRENZY PREPARATION";
                       description="we are working for you fun!(and bloody revenge)";
                       break;
                   case"FinalScoringState":
                       title="CALCULATING YOUR FINAL SCORE";
                       description="Can you figure out who is going to win?";
                       break;
                   case"FirstSpawnState":
                       title="SPAWNING FOR THE FIRST TIME";
                       description="is spawning for his first time!\n" +
                               "Let's show some warmth all of you!";
                       break;
                   case"GrabStuffState":
                       title="CHOOSING WHERE TO GRAB";
                       description="is choosing where to grab! Should you stay or should you go?";
                       break;
                   case"GrabStuffStateDrawAndDiscardPowerUp":
                       title="DISCARDING A POWER UP";
                       description="is discarding a power up card";
                       break;
                   case"GrabStuffStateDrawPowerUp":
                       title="DRAWING A POWER UP";
                       description="is drawing a power up!";
                       break;
                   case "GrabStuffStateGrab":
                       title="GRABBING SOMETHING";
                       description="is grabbing something! I wonder what it will be...";
                        break;
                   case"GrabStuffStateGrabWeapon":
                       title="GRABBING A WEAPON";
                       description="is grabbing a weapon! Watch out you all!!!";
                       break;
                   case"GrabStuffStateMove":
                       title="MOVING BEFORE GRABBING";
                       description="decided to move!";
                       break;
                   case"PowerUpAskForInputState":
                       title="POWER UP DETAILS ARE BEING DISCUSSED";
                       description="is deciding how to use his hidden powers...";
                       break;
                   case"PowerUpState":
                       title="CLEARING IDEAS ON POWER UPS";
                       description="is deciding what of his multiple abilities as a wizard is going to use against you";
                       break;
                   case"ReloadState":
                       title="RELOADING YOUR WEAPONS";
                       description="is reloading his weapons! Beware! ";
                       break;
                   case"RunAroundState":
                       title="RUN AROUND!";
                       description="decided to go for a walk";
                       break;
                   case"ScoreKillsState":
                       title="SCORING THE DEAD";
                       description="Working for your rank to update";
                       break;
                   case"ShootPeopleAskForInputState":
                       title="SHOOTING DETAILS ARE BEING DISCUSSED";
                       description="is deciding what to do with his weapons";
                       break;
                   case"ShootPeopleChooseEffectState":
                       title="WEAPON MODES ARE BEING DEFINED";
                       description="is deciding how to make you suffer";
                       break;
                   case"ShootPeopleChooseWepState":
                       title="WEAPON TO USE IS BEING CHOSEN";
                       description="is deciding what will make your brain blow out";
                       break;
                   case"ShootPeopleState":
                       title="SHOOTING PEOPLE";
                       description="is shooting";
                       break;
                   case"SpawnState":
                       title="SPAWNING PLAYER";
                       description="is rising againg from the dead";
                       break;
                   case"TagBackGranadeState":
                       title="TAGBACK GRENADE IN ACTION";
                       description="is using tagbackgrenade";
                       break;
                   case"TargetingScopeState":
                       title="TARGETING SCOPE IN ACTION";
                       description="is using targetingscope";
                       break;
                   case"TurnState":
                       title="TURN STATE";
                       description="is thinking! What will he do?";
                       break;
                   case"WantToPlayPowerUpState":
                       title="ASKING PLAYER FOR POWER UP TO BE USED";
                       description="is deciding whether to use a power up or not";
                       break;
                   default:
                       title="Unpredictable switch of the game";
                       description="whoop i fell";
                       break;
                }
                Text player;
                Text descr=new Text(description);

                (getGameSceneController().getStateTitle()).setText(title);

                if(ViewModelGate.getMe().equals(ViewModelGate.getModel().getPlayers().getCurrentPlayingPlayer())) {
                   player=new Text("you");
                }
                else{
                   player=new Text(ViewModelGate.getModel().getPlayers().getCurrentPlayingPlayer());
                }
                //TODO ludo penso che questa cosa non vada bene (da fede) : getGameSceneController().setStateDescription(new TextFlow(player, descr));
                // forse questa va meglio:
                // getGameSceneController().getStateDescription().getChildren().addAll(player,descr);
                // ma comunque devi prima occuparti di cancellare il contenuto che ha già
            });

        }
    }

    /**update the progress indicator to match the timer count down for the player*/
    private void updateProgressIndicator(int currentTime, int totalTime){
        new Thread(new UpdateProgressIndicator(currentTime, totalTime)).start();
    }
    private class UpdateProgressIndicator implements Runnable{
        private int currentTime;
        private int totalTime;
        private UpdateProgressIndicator(int currentTime, int totalTime){
            this.currentTime = currentTime;
            this.totalTime = totalTime;
        }
        @Override
        public void run() {
            if(GUIstarter.getStageController().getClass().toString().contains("GameSceneController")) {
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
        if(ViewModelGate.getModel().isHasFinalFrenzyBegun()) {
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
        }
        else{
            //TODO:
            //    show playerlist during game..
            updatePlayer();
            updateMap();
            updateKillShotTrack();
        }
    }
    private void showPlayerListInLoadingScene(){
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
        updateMap();
    }

    @Override
    public void shufflingCards(ModelViewEvent modelViewEvent) {
        // TODO LASCIATELO ALLA FINE LUCA
        updatePlayer();
        updateMap();
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
        GUIstarter.showError(this,"can't reach server",null);
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