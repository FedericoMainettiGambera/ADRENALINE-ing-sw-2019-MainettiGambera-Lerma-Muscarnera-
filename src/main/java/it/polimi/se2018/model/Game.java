package it.polimi.se2018.model;


/***/
public class Game {

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


    /***/
    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    /***/
    public KillShotTrack getKillshotTrack() {
        return killshotTrack;
    }

    public void setKillshotTrack(KillShotTrack killshotTrack) {
        this.killshotTrack = killshotTrack;
    }

    /***/
    public PlayersList getPlayers() {
        return players;
    }

    public void setPlayers(PlayersList players) {
        this.players = players;
    }

    /***/
    public OrderedCardList<PowerUpCard> getPowerUpDeck() {
        return powerUpDeck;
    }

    public void setPowerUpDeck(OrderedCardList<PowerUpCard> powerUpDeck) {
        this.powerUpDeck = powerUpDeck;
    }

    /***/
    public OrderedCardList<AmmoCard> getAmmoDeck() {
        return ammoDeck;
    }

    public void setAmmoDeck(OrderedCardList<AmmoCard> ammoDeck) {
        this.ammoDeck = ammoDeck;
    }

    /***/
    public OrderedCardList<WeaponCard> getWeaponDeck() {
        return weaponDeck;
    }

    public void setWeaponDeck(OrderedCardList<WeaponCard> weaponDeck) {
        this.weaponDeck = weaponDeck;
    }

    /***/
    public  Board getBoard() {
        return this.board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    /***/
    public OrderedCardList<AmmoCard> getAmmoDiscardPile() {
        return ammoDiscardPile;
    }

    public void setAmmoDiscardPile(OrderedCardList<AmmoCard> ammoDiscardPile) {
        this.ammoDiscardPile = ammoDiscardPile;
    }

    /***/
    public OrderedCardList<PowerUpCard> getPowerUpDiscardPile() {
        return powerUpDiscardPile;
    }

    public void setPowerUpDiscardPile(OrderedCardList<PowerUpCard> powerUpDiscardPile) {
        this.powerUpDiscardPile = powerUpDiscardPile;
    }
}
