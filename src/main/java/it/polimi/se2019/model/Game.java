package it.polimi.se2019.model;


import java.io.File;
import java.util.Observable;

/***/
public class Game extends Observable {

    /***/
    public Game() {
    }

    /***/
    private KillShotTrack killshotTrack;

    /***/
    private PlayersList players;

    /***/
    private Bot bot;

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

    /***/
    private Board board;

    private boolean isFinalFrenzy;

    public Player getCurrentPlayingPlayer(){
        return this.getPlayerList().getCurrentPlayingPlayer();
    }

    public void setNextPlayingPlayer(){
        this.getPlayerList().setNextPlayingPlayer();
    }

    public void setFinalFrenzy(boolean isFinalFrenzy){
        this.isFinalFrenzy = isFinalFrenzy;
        setChanged();
        notifyObservers();
    }

    public boolean getFinalFrenzy(){
        return this.isFinalFrenzy;
    }


    /***/
    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
        setChanged();
        notifyObservers();
    }

    /***/
    public KillShotTrack getKillshotTrack() {
        return killshotTrack;
    }

    public void setKillshotTrack(KillShotTrack killshotTrack) {
        this.killshotTrack = killshotTrack;
        setChanged();
        notifyObservers();
    }

    /***/
    public PlayersList getPlayerList() {
        return players;
    }

    public void buildDecks() {

        this.powerUpDeck = new OrderedCardList<>();
        this.weaponDeck = new OrderedCardList<>();
        this.ammoDeck = new OrderedCardList<>();
        this.ammoDiscardPile = new OrderedCardList<>();
        this.powerUpDiscardPile = new OrderedCardList<>();

        //builds weapon cards
        File directory = new File("Cards");     // insert here path to weapon cards folder
        int fileCount = directory.list().length;
        for(int i = 0; i< fileCount;i++) {
             try {
                 this.weaponDeck.addCard(new WeaponCard("" + i));
             }
             catch(Exception e) {
                 e.printStackTrace();
                 return;
             }
        }

        //builds power up cards
        directory = new File("Cards");          // insert here path to power up cards folder
        fileCount = directory.list().length;
        for(int i = 0; i< fileCount;i++) {
            try {
                this.powerUpDeck.addCard(new PowerUpCard("" + i));
            }
            catch(Exception e) {
                e.printStackTrace();
                return;
            }
        }

        //builds ammo cards
        directory = new File("Cards");          // insert here path to ammo cards folder
        fileCount = directory.list().length;
        for(int i = 0; i< fileCount;i++) {
            try {
                //TODO: this.ammoDeck.addCard(new AmmoCard("" + i));
            }
            catch(Exception e) {
                e.printStackTrace();
                return;
            }
        }

    }

    public void setPlayerList(PlayersList players) {
        this.players = players;
        setChanged();
        notifyObservers();
    }

    /***/
    public OrderedCardList<PowerUpCard> getPowerUpDeck() {
        return powerUpDeck;
    }

    public void setPowerUpDeck(OrderedCardList<PowerUpCard> powerUpDeck) {
        this.powerUpDeck = powerUpDeck;
        setChanged();
        notifyObservers();
    }

    /***/
    public OrderedCardList<AmmoCard> getAmmoDeck() {
        return ammoDeck;
    }

    public void setAmmoDeck(OrderedCardList<AmmoCard> ammoDeck) {
        this.ammoDeck = ammoDeck;
        setChanged();
        notifyObservers();
    }

    /***/
    public OrderedCardList<WeaponCard> getWeaponDeck() {
        return weaponDeck;
    }

    public void setWeaponDeck(OrderedCardList<WeaponCard> weaponDeck) {
        this.weaponDeck = weaponDeck;
        setChanged();
        notifyObservers();
    }

    /***/
    public  Board getBoard() {
        return this.board;
    }

    public void setBoard(Board board) {
        this.board = board;
        setChanged();
        notifyObservers();
    }

    /***/
    public OrderedCardList<AmmoCard> getAmmoDiscardPile() {
        return ammoDiscardPile;
    }

    public void setAmmoDiscardPile(OrderedCardList<AmmoCard> ammoDiscardPile) {
        this.ammoDiscardPile = ammoDiscardPile;
        setChanged();
        notifyObservers();
    }

    /***/
    public OrderedCardList<PowerUpCard> getPowerUpDiscardPile() {
        return powerUpDiscardPile;
    }

    public void setPowerUpDiscardPile(OrderedCardList<PowerUpCard> powerUpDiscardPile) {
        this.powerUpDiscardPile = powerUpDiscardPile;
        setChanged();
        notifyObservers();
    }
}
