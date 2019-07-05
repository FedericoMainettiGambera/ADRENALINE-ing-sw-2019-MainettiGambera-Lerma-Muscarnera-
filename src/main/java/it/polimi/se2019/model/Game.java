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

/** takes track of the entire state of the game
 * @author LudoLerma
 *@author FedericoMainettiGambera */
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

    /**the number of clients connected at a given time*/
    private static int numberOfClientsConnected=0;
    /**indicates if the game has begun*/
    private static boolean hasGameBegun = false;
    /**@return hasGameBegun */
    public static boolean isHasGameBegun() {
        return hasGameBegun;
    }
    /**@param hasGameBegun boolean value */
    public static void setHasGameBegun(boolean hasGameBegun) {
        Game.hasGameBegun = hasGameBegun;
    }
    /**@return  numberOfClientsConnected */
    public int getNumberOfClientsConnected(){
        return numberOfClientsConnected;
    }
    /**indicates if the timer has begun*/
    private static boolean hasTimerBegun = false;
/**@param numberOfClientsConnected, set the numberOfClientConnected attirbute*/
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
    /**game has a reference to the current state*/
    private String currentState;

    /**@param currentState, set the currentState attribute */
    public void setCurrentState(String currentState){
        this.currentState = currentState;
        setChanged();
        notifyObservers(new StateEvent(this.currentState));
    }


    /** reference to all the player playing or afk */
    private PlayersList players;

    /** need to know if bot is active or not*/
    private boolean isBotActive;

    /** @return boolean value that indicates if the bot is active*/
    public boolean isBotActive() {
        return isBotActive;
    }
    /**@param isBotActive, set the isBotActive attribute*/
    public void setBotActive(boolean isBotActive){
        this.isBotActive = isBotActive;
    }

    /**reference to the power up deck list*/
    private OrderedCardList<PowerUpCard> powerUpDeck;

    /**reference to the weapon card deck list*/
    private OrderedCardList<WeaponCard> weaponDeck;

    /**reference to the ammo cards deck list*/
    private OrderedCardList<AmmoCard> ammoDeck;

    /**reference to the power up discard pile list*/
    private OrderedCardList<PowerUpCard> powerUpDiscardPile;

    /**reference to the ammo discard pile list*/
    private OrderedCardList<AmmoCard> ammoDiscardPile;
    /**indicates if the final frenzy has begun*/
    private boolean hasFinalFrenzyBegun;

    /**reference to the board*/
    private Board board;
    /**indicates if there is the final frenzy mode*/
    private boolean isFinalFrenzy;

    /**reference to the VVsocket so that it can be registered in all the model.*/
    private transient VirtualView virtualViewSocket;
    /**reference to the VVrmi so that it can be registered in all the model.*/
    private transient VirtualView virtualViewRmi;
    /**it  registers in all the model.*/
    public void setVirtualView(VirtualView virtualViewSocket, VirtualView virtualViewRmi){
        this.virtualViewRmi = virtualViewRmi;
        this.virtualViewSocket = virtualViewSocket;
    }
    /** @return virtualViewSocket*/
    public VirtualView getSocketVirtualView(){
        return this.virtualViewSocket;
    }
    /** @return virtualViewRmi */
    public VirtualView getRMIVirtualView(){
        return this.virtualViewRmi;
    }
    /**register the clients as observers of the game to be notified of the changes of it*/
    public void registerVirtualView(){
        this.addObserver(this.virtualViewRmi);
        this.addObserver(this.virtualViewSocket);
        System.out.println("    VirtualView added to the Game's observers");
        this.getPlayerList().addObserver(this.virtualViewSocket);
        this.getPlayerList().addObserver(this.virtualViewRmi);
        System.out.println("    VirtualView added to the PlayerList's observers");
    }
    /** @return reference to the current playin player*/
    public Player getCurrentPlayingPlayer(){
        return this.getPlayerList().getCurrentPlayingPlayer();
    }
    /** @param isFinalFrenzy  boolean value that indicates if there is final frenzy*/
    public void setFinalFrenzy(boolean isFinalFrenzy){
        this.isFinalFrenzy = isFinalFrenzy;
        setChanged();
        notifyObservers(new ModelViewEvent(this.isFinalFrenzy, ModelViewEventTypes.setFinalFrenzy));
    }
    /** @return boolean value that indicates if final frenzy is active*/
    public boolean isFinalFrenzy(){
        return this.isFinalFrenzy;
    }
    /** @return boolean value that indicates if final frenzy has begun*/
    public boolean hasFinalFrenzyBegun(){
        return this.hasFinalFrenzyBegun;
    }

    public void triggerFinalFrenzy(boolean hasFinalFrenzyBegun){
        this.hasFinalFrenzyBegun = hasFinalFrenzyBegun;
        setChanged();
        notifyObservers(new ModelViewEvent(this.hasFinalFrenzyBegun, ModelViewEventTypes.finalFrenzyBegun));
    }

    /**@return a reference to the killshot track*/
    public KillShotTrack getKillshotTrack() {
        return killshotTrack;
    }
   /**@param killshotTrack set a reference to the killshot track*/
    public void setKillshotTrack(KillShotTrack killshotTrack) {
        this.killshotTrack = killshotTrack;
        setChanged();
        notifyObservers(new ModelViewEvent(this.killshotTrack.getNumberOfRemainingSkulls(), ModelViewEventTypes.newKillshotTrack, this.killshotTrack.buildKillshotTrackV()));
    }

    /**@return players*/
    public PlayersList getPlayerList() {
        return players;
    }

    /**Build all of the decks to be used*/
    public void buildDecks() {

        //builds weapon cards
        OrderedCardList<WeaponCard> tempWeaponDeck = new OrderedCardList<>("weaponDeck");
        File directory = new File("src/main/Files/cards/weaponCards");     // insert here path to weapon cards folder
        int fileCount = Objects.requireNonNull(directory.list()).length;
        for(int i = 1; i< fileCount+1; i++) {
            try {
                int id = /*1 + i %*/ i;
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
                        // to delete next line
                        System.out.println(card.getID() + " = " + card.getName() + " , " + card.getColor());

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
                AmmoCard tempAmmoCard = new AmmoCard(i+ "");
                tempAmmoDeck.addCard(tempAmmoCard);
                System.out.println("         ammo has:");
                System.out.println("         powerUp: " + tempAmmoCard.isPowerUp());
                for (AmmoCubes a:tempAmmoCard.getAmmunitions().getAmmoCubesList()) {
                    System.out.println("         " + a.getColor() + ": " + a.getQuantity());
                }
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

    /**@param players to set a reference to the playerList*/
    public void setPlayerList(PlayersList players) {
        this.players = players;
        setChanged();
        notifyObservers(new ModelViewEvent(this.players.buildPlayersListV(), ModelViewEventTypes.newPlayersList));
    }

/**@param MVE to notify all observers of the new event*/
    public void notifyClients(ModelViewEvent MVE){
        setChanged();
        notifyObservers(MVE);
    }

    /**@return powerUpDeck*/
    public OrderedCardList<PowerUpCard> getPowerUpDeck() {
        return powerUpDeck;
    }

    /**@return ammoDeck*/
    public OrderedCardList<AmmoCard> getAmmoDeck() {
        return ammoDeck;
    }

    /**@return weaponDeck*/
    public OrderedCardList<WeaponCard> getWeaponDeck() {
        return weaponDeck;
    }

    /**@return board*/
    public  Board getBoard() {
        return this.board;
    }

    /**@param board ,set a reference to the board*/
    public void setBoard(Board board) {
        this.board = board;
        setChanged();
        notifyObservers(new ModelViewEvent(this.board.buildBoardV(), ModelViewEventTypes.newBoard, this.board.getChosenMap()));
    }

    /**@return ammoDiscardPile*/
    public OrderedCardList<AmmoCard> getAmmoDiscardPile() {
        return ammoDiscardPile;
    }

    /**@return powerUpDiscardPile*/
    public OrderedCardList<PowerUpCard> getPowerUpDiscardPile() {
        return powerUpDiscardPile;
    }

    /**build an equivalent structure for the view purpose
     * @return GameV reference */
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
