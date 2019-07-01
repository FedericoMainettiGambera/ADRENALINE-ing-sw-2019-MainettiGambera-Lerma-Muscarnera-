package it.polimi.se2019.view.outputHandler;

import it.polimi.se2019.model.AmmoCubes;
import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.model.enumerations.PlayersColors;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.model.events.stateEvent.StateEvent;
import it.polimi.se2019.view.GUIstarter;
import it.polimi.se2019.view.GameSceneController;
import it.polimi.se2019.view.LoadingSceneController;
import it.polimi.se2019.view.components.*;
import javafx.application.Platform;
import javafx.scene.layout.StackPane;

import java.util.concurrent.TimeUnit;


public class GUIOutputHandler implements OutputHandlerInterface {
    private void updateKillShotTrack() {
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
    }                               // luca
    private void updatePlayer()    {
        updatePowerUpCards();
        updateWeaponCards();
        updatePlayerBoard();
    }                                   // ...
    private void updatePowerUpCards(){

    }                                // luca
    private void updateWeaponCards() {}                                 // luca
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
                    if(player.getNickname().equals(ViewModelGate.getMe())) {
                      updateDamage(player);
                    }
                }
            });
        }

        private void updateDamage(PlayerV player){
            int i = 0;
            if(player.getDamageTracker() == null || player.getDamageTracker().getDamageSlotsList().isEmpty()){
                emptyDamages();
            }
            else {
                for (DamageSlotV damageSlot : player.getDamageTracker().getDamageSlotsList()) {
                    addDamages(damageSlot,i);
                    i++;
                }
            }
        }

        private void addDamages(DamageSlotV damageSlot, int i){

            PlayersColors color = damageSlot.getShootingPlayerColor();

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
                    (getGameSceneController().getDamagesMainImage().get(i).getStyleClass()).add("damageEmpty");
            }
        }
        private void emptyDamages(){

            for (StackPane damage: getGameSceneController().getDamagesMainImage()){
                removePrevious(damage);
                damage.getStyleClass().add("damageEmpty");
            }
        }
        /**@param damage from whose previous style class is removed*/
        private void removePrevious(StackPane damage){

            damage.getStyleClass().remove("damageBlu");
            damage.getStyleClass().remove("damagePurple");
            damage.getStyleClass().remove("damageYellow");
            damage.getStyleClass().remove("damageGreen");
            damage.getStyleClass().remove("damageGray");
            damage.getStyleClass().remove("damageEmpty");


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

            Platform.runLater(()->{

                for (PlayerV player: ViewModelGate.getModel().getPlayers().getPlayers()){

                    if(player.getNickname().equals(ViewModelGate.getMe())) {

                        int i = 0;
                        if(player.getMarksTracker().getMarkSlotsList().isEmpty()||player.getMarksTracker()==null){
                            emptyMarks();
                        }

                        for (MarkSlotV markSlot : player.getMarksTracker().getMarkSlotsList()) {
                            setMarks(i, markSlot);
                            i++;
                        }
                    }
                }
            });
        }

        private void setMarks(int i, MarkSlotV markSlot){
            PlayersColors color = ViewModelGate.getModel().getPlayers().getPlayer(markSlot.getMarkingPlayer()).getColor();

            switch (color) {
                case blue:removePrevious(getGameSceneController().getMarkMainImage().get(i));
                    (getGameSceneController().getMarkMainImage().get(i).getStyleClass()).add("markBlue");
                    break;
                case purple:removePrevious(getGameSceneController().getMarkMainImage().get(i));
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
                    (getGameSceneController().getMarkMainImage().get(i).getStyleClass()).add("markEmpty");
            }

        }
        public void emptyMarks(){

            for (StackPane mark: getGameSceneController().getMarkMainImage()) {

                removePrevious(mark);
                mark.getStyleClass().add("markEmpty");
            }

        }

        /**@param  mark from which previous style class is removed*/
        private void removePrevious(StackPane mark){

            mark.getStyleClass().remove("markBlu");
            mark.getStyleClass().remove("markPurple");
            mark.getStyleClass().remove("markYellow");
            mark.getStyleClass().remove("markGreen");
            mark.getStyleClass().remove("markGray");
            mark.getStyleClass().remove("markEmpty");

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
         * updates their death traker, it extrapolate which one to update by using the function "player.getNumberOfDeath()",
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

                    if(player.getNickname().equals(ViewModelGate.getMe())) {

                        removePrevious();
                        getGameSceneController().getNicknameLabel().setText(player.getNickname());
                        getGameSceneController().getNicknameLabel().getStyleClass().add("nicknameStyle");

                    }
                }
            });
        }

        /**previous style class is removed*/
        private void removePrevious(){

            getGameSceneController().getNicknameLabel().getStyleClass().remove("nicknameStyle");

        }
    }
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

        /**empty all the ammolists*/
        private void emptyAmmos(){
            for (AmmoCubesColor color: AmmoCubesColor.values()){
                for (StackPane ammo: getGameSceneController().getAmmosMainImage(color)){

                    removePrevious(ammo);
                    ammo.getStyleClass().add("emptyAmmo");

                }
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
                    case blue:removePrevious(getGameSceneController().getAmmosMainImage(color).get(b));
                        (getGameSceneController().getAmmosMainImage(color).get(b).getStyleClass()).add("ammoBlue");
                        b++;
                        break;
                    case red:
                        removePrevious(getGameSceneController().getAmmosMainImage(color).get(r));
                        (getGameSceneController().getAmmosMainImage(color).get(r).getStyleClass()).add("ammoRed");
                        r++;
                        break;
                    case yellow:removePrevious(getGameSceneController().getAmmosMainImage(color).get(y));
                        (getGameSceneController().getAmmosMainImage(color).get(y).getStyleClass()).add("ammoYellow");
                        y++;
                        break;
                    default:
                        break;
                }
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
                        emptyAmmos();
                    }
                    addAmmos(player);
                }
            }
        }
    }

    /*TODO*/
    private void updateMap()   {
        System.out.println("UPDATE MAP"); //MOMENTANEO
    }
    private void updateStateBar() {
        System.out.println("UPDATE STATE BAR"); //MOMENTANEO
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
            updateStateBar();
        }
    }



    @Override
    public void setFinalFrenzy(ModelViewEvent modelViewEvent) {
        // shot ViewModelGate.getModel().isFinalFrenzy()
        updateStateBar();
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
        if (GUIstarter.getStageController().getClass().toString().contains("LoadingSceneController")) {
            showPlayerListInLoadingScene(); // TODO
        }
        else{
            //TODO:
            //    show playerlist during game..
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
    }

    @Override
    public void movingCardsAround(OrderedCardListV from, OrderedCardListV to, ModelViewEvent modelViewEvent) {
        // TODO LASCIATELO ALLA FINE LUCA
        //update changed cards
    }

    @Override
    public void shufflingCards(ModelViewEvent modelViewEvent) {
        // TODO LASCIATELO ALLA FINE LUCA
        //probably empty
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
        updateStateBar();
    }

    @Override
    public void setStartingPlayer(ModelViewEvent modelViewEvent) {
        //update statebar
        updateStateBar();
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
        updateStateBar();
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