package it.polimi.se2019.model;

import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.model.enumerations.ModelViewEventTypes;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.model.events.stateEvent.StateEvent;
import it.polimi.se2019.view.components.GameV;
import it.polimi.se2019.virtualView.VirtualView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/** takes track of the entire state of the game*/
public class Game extends Observable implements Serializable {

    private static Logger logger=Logger.getLogger(Game.class.getName());
    /** when instanced, build up all the deck needed*/
    public Game() {
        this.powerUpDeck = new OrderedCardList<>("powerUpDeck");
        this.weaponDeck = new OrderedCardList<>("weaponDeck");
        this.ammoDeck = new OrderedCardList<>("ammoDeck");
        this.ammoDiscardPile = new OrderedCardList<>("ammoDiscardPile");
        this.powerUpDiscardPile = new OrderedCardList<>("powerUpDiscardPile");
    }

    private static int numberOfClientsConnected=0;

    private static boolean hasGameBegun = false;

    public static boolean isHasGameBegun() {
        return hasGameBegun;
    }

    public static void setHasGameBegun(boolean hasGameBegun) {
        Game.hasGameBegun = hasGameBegun;
    }

    public int getNumberOfClientsConnected(){
        return numberOfClientsConnected;
    }

    private static boolean hasTimerBegun = false;

    public static void setNumberOfClientsConnected(int numberOfClientsConnected){
        System.out.println("         MODELGATE: SETTING NUMBER OF CONNECTION");

        Game.numberOfClientsConnected = numberOfClientsConnected;

        if((!hasTimerBegun)&&(numberOfClientsConnected >= GameConstant.MIN_NUMBER_OF_PLAYER_PER_GAME)){
                hasTimerBegun = true;
                Thread t = new Thread(new ConnectionGameCountDown(numberOfClientsConnected));
                t.start();
        }
    }

    /** game has a reference to the killshottrack */
    private KillShotTrack killshotTrack;

    private String currentState;

    public void setCurrentState(String currentState){
        this.currentState = currentState;
        setChanged();
        notifyObservers(new StateEvent(this.currentState));
    }


    /** reference to all the player playing or afk */
    private PlayersList players;

    /** need to know if bot is active or not*/
    private boolean isBotActive;

    public boolean isBotActive() {
        return isBotActive;
    }

    public void setBotActive(boolean isBotActive){
        this.isBotActive = isBotActive;
    }

    /***/
    private OrderedCardList<PowerUpCard> powerUpDeck;

    /***/
    private OrderedCardList<WeaponCard> weaponDeck;

    /***/
    private OrderedCardList<AmmoCard> ammoDeck;

    /***/
    private OrderedCardList<PowerUpCard> powerUpDiscardPile;

    /***/
    private OrderedCardList<AmmoCard> ammoDiscardPile;

    private boolean hasFinalFrenzyBegun;

    /***/
    private Board board;

    private boolean isFinalFrenzy;

    //reference to the VV so that it can be registered in all the model.
    private transient VirtualView virtualViewSocket;
    private transient VirtualView virtualViewRmi;

    public void setVirtualView(VirtualView virtualViewSocket, VirtualView virtualViewRmi){
        this.virtualViewRmi = virtualViewRmi;
        this.virtualViewSocket = virtualViewSocket;
    }

    public VirtualView getSocketVirtualView(){
        return this.virtualViewSocket;
    }

    public VirtualView getRMIVirtualView(){
        return this.virtualViewRmi;
    }

    public void registerVirtualView(){
        this.addObserver(this.virtualViewRmi);
        this.addObserver(this.virtualViewSocket);
        System.out.println("    VirtualView added to the Game's observers");
        this.getPlayerList().addObserver(this.virtualViewSocket);
        this.getPlayerList().addObserver(this.virtualViewRmi);
        System.out.println("    VirtualView added to the PlayerList's observers");
    }

    public Player getCurrentPlayingPlayer(){
        return this.getPlayerList().getCurrentPlayingPlayer();
    }

    public void setFinalFrenzy(boolean isFinalFrenzy){
        this.isFinalFrenzy = isFinalFrenzy;
        setChanged();
        notifyObservers(new ModelViewEvent(this.isFinalFrenzy, ModelViewEventTypes.setFinalFrenzy));
    }

    public boolean isFinalFrenzy(){
        return this.isFinalFrenzy;
    }

    public boolean hasFinalFrenzyBegun(){
        return this.hasFinalFrenzyBegun;
    }

    public void triggerFinalFrenzy(boolean hasFinalFrenzyBegun){
        this.hasFinalFrenzyBegun = hasFinalFrenzyBegun;
        setChanged();
        notifyObservers(new ModelViewEvent(this.hasFinalFrenzyBegun, ModelViewEventTypes.finalFrenzyBegun));
    }

    /***/
    public KillShotTrack getKillshotTrack() {
        return killshotTrack;
    }

    public void setKillshotTrack(KillShotTrack killshotTrack) {
        this.killshotTrack = killshotTrack;
        setChanged();
        notifyObservers(new ModelViewEvent(this.killshotTrack.getNumberOfRemainingSkulls(), ModelViewEventTypes.newKillshotTrack));
    }

    /***/
    public PlayersList getPlayerList() {
        return players;
    }

    public void buildDecks() {

        //builds weapon cards
        OrderedCardList<WeaponCard> tempWeaponDeck = new OrderedCardList<>("weaponDeck");
        File directory = new File("src/main/Files/cards/weaponCards");     // insert here path to weapon cards folder
        int fileCount = Objects.requireNonNull(directory.list()).length;
        for(int i = 1; i< fileCount+1; i++) {
            try {
                int id = /*1 + i %*/ 19;
                WeaponCard card= new WeaponCard("" + id);
                card.reload();
                tempWeaponDeck.addCard(card);
                System.out.println("<SERVER> building weapon cards ID: " + id);
            }
            catch(Exception e) {
                logger.log(Level.SEVERE, "EXCEPTION", e);
                return;
            }
        }

        OrderedCardList<PowerUpCard> tempPowerUpDeck = new OrderedCardList<>("powerUpDeck");
        directory = new File("src/main/Files/cards/powerUpCards");          // insert here path to power up cards folder
        fileCount = Objects.requireNonNull(directory.list()).length;
        List<AmmoCubesColor> Colors = new ArrayList<>();
        Colors.add(AmmoCubesColor.red);
        Colors.add(AmmoCubesColor.yellow);
        Colors.add(AmmoCubesColor.blue);

        System.out.println("<SERVER> powerUp length " + fileCount);
        int idCounter = 0;
        for(int j = 0;j<2;j++)
            for(AmmoCubesColor color: Colors) {
                for (int i = 1; i <= fileCount; i++) {
                    System.out.println("<SERVER> building powerUp cards ID: " + i);
                    try {
                        PowerUpCard card = new PowerUpCard("" + i,idCounter);
                        card.setColor(color);
                        tempPowerUpDeck.addCard(card);
                        idCounter++;
                    } catch (Exception e) {
                        logger.log(Level.SEVERE, "EXCEPTION", e);
                        return;
                    }
                }
            }


        OrderedCardList<AmmoCard> tempAmmoDeck = new OrderedCardList<>("ammoDeck");
        directory = new File("src/main/Files/cards/ammoCards");          // insert here path to ammo cards folder
        int count = 36;
        for(int i = 1; i<= count;i++) {
            System.out.println("<SERVER> building Ammo cards ID: " +i);
            try {
                tempAmmoDeck.addCard(new AmmoCard(i+ ""));
            }
            catch(Exception e) {
                logger.log(Level.SEVERE, "EXCEPTION", e);
                return;
            }
        }


        this.getWeaponDeck().getCards().addAll(tempWeaponDeck.getCards());
        this.getPowerUpDeck().getCards().addAll(tempPowerUpDeck.getCards());
        setChanged();
        notifyObservers(new ModelViewEvent(this.getWeaponDeck().buildDeckV(), ModelViewEventTypes.movingCardsAround));

        this.getAmmoDeck().getCards().addAll(tempAmmoDeck.getCards());
        setChanged();
        notifyObservers(new ModelViewEvent(this.getPowerUpDeck().buildDeckV(), ModelViewEventTypes.movingCardsAround));

        this.getPowerUpDeck().getCards().addAll(tempPowerUpDeck.getCards());
        setChanged();
        notifyObservers(new ModelViewEvent(this.getAmmoDeck().buildDeckV(), ModelViewEventTypes.movingCardsAround));

    }

    public void setPlayerList(PlayersList players) {
        this.players = players;
        setChanged();
        notifyObservers(new ModelViewEvent(this.players.buildPlayersListV(), ModelViewEventTypes.newPlayersList));
    }


    public void notifyClients(ModelViewEvent MVE){
        setChanged();
        notifyObservers(MVE);
    }

    /***/
    public OrderedCardList<PowerUpCard> getPowerUpDeck() {
        return powerUpDeck;
    }

    /***/
    public OrderedCardList<AmmoCard> getAmmoDeck() {
        return ammoDeck;
    }

    /***/
    public OrderedCardList<WeaponCard> getWeaponDeck() {
        return weaponDeck;
    }

    /***/
    public  Board getBoard() {
        return this.board;
    }

    public void setBoard(Board board) {
        this.board = board;
        setChanged();
        notifyObservers(new ModelViewEvent(this.board.buildBoardV(), ModelViewEventTypes.newBoard));
    }

    /***/
    public OrderedCardList<AmmoCard> getAmmoDiscardPile() {
        return ammoDiscardPile;
    }

    /***/
    public OrderedCardList<PowerUpCard> getPowerUpDiscardPile() {
        return powerUpDiscardPile;
    }

    public GameV buildGameV(){
        GameV gameV = new GameV();
        if(this.players!=null) {
            gameV.setPlayers(this.players.buildPlayersListV());
        }
        if(this.board!=null) {
            gameV.setBoard(this.board.buildBoardV());
        }
        gameV.setPowerUpDiscardPile(this.powerUpDiscardPile.buildDeckV());
        gameV.setAmmoDiscardPile(this.ammoDiscardPile.buildDeckV());
        gameV.setAmmoDeck(this.ammoDeck.buildDeckV());
        gameV.setWeaponDeck(this.weaponDeck.buildDeckV());
        gameV.setPowerUpDeck(this.powerUpDeck.buildDeckV());
        if(this.killshotTrack!=null) {
            gameV.setKillshotTrack(this.killshotTrack.buildKillshotTrackV());
        }
        gameV.setHasFinalFrenzyBegun(this.hasFinalFrenzyBegun);
        gameV.setFinalFrenzy(this.isFinalFrenzy);
        gameV.setBotActive(this.isBotActive);

        return gameV;
    }
}
