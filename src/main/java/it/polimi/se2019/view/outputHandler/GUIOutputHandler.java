package it.polimi.se2019.view.outputHandler;

import it.polimi.se2019.model.enumerations.PlayersColors;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.model.events.stateEvent.StateEvent;
import it.polimi.se2019.view.GUIstarter;
import it.polimi.se2019.view.GameSceneController;
import it.polimi.se2019.view.LoadingSceneController;
import it.polimi.se2019.view.components.DamageSlotV;
import it.polimi.se2019.view.components.OrderedCardListV;
import it.polimi.se2019.view.components.PlayerV;
import it.polimi.se2019.view.components.ViewModelGate;
import javafx.application.Platform;
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
    private void updatePowerUpCards(){}                                // luca
    private void updateWeaponCards() {}                                 // luca
    private void updatePlayerBoard() {
        updateDamage();
        updateMarks();
        updateDeaths();
        updateNickname();
        updateAmmobox();
    }                                 // ...
    private void updateDamage(){

        (new Thread(new UpdateDamage())).start();
    }
    // ludo
    private class UpdateDamage implements Runnable{

        @Override
        public void run(){
            Platform.runLater(()->{

                for (PlayerV player: ViewModelGate.getModel().getPlayers().getPlayers()){

                    int i=0;

                    for(DamageSlotV damageSlot : player.getDamageTracker().getDamageSlotsList()){

                            PlayersColors color= damageSlot.getShootingPlayerColor();
                            switch (color){
                                case blue:(getGameSceneController().getDamagesMainImage().get(i).getStyleClass()).add(".damageBlue");
                                break;
                                case purple:(getGameSceneController().getDamagesMainImage().get(i).getStyleClass()).add(".damagePurple");
                                break;
                                case gray:(getGameSceneController().getDamagesMainImage().get(i).getStyleClass()).add(".damageGray");
                                break;
                                case green:(getGameSceneController().getDamagesMainImage().get(i).getStyleClass()).add(".damageGreen");
                                break;
                                case yellow:(getGameSceneController().getDamagesMainImage().get(i).getStyleClass()).add(".damageYellow");
                                break;
                                default:(getGameSceneController().getDamagesMainImage().get(i).getStyleClass()).add(".damageEmpty");
                            }
                        i++;
                    }

                }
            });
        }
    }
    private void updateMarks() {}                                       // ludo
    private void updateDeaths() {}                                      // ludo
    private void updateNickname() {}                                    // ludo
    private void updateAmmobox() {}                                     // ludo
    /*TODO*/
    private void updateMap()   {}
    private void updateStateBar() {}

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