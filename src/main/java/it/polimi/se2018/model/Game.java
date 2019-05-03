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

    /***/
    public KillShotTrack getKillshotTrack() {
        return killshotTrack;
    }

    /***/
    public PlayersList getPlayers() {
        return players;
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
    public Board getBoard() {
        return board;
    }

    /***/
    public OrderedCardList<AmmoCard> getAmmoDiscardPile() {
        return ammoDiscardPile;
    }

    /***/
    public OrderedCardList<PowerUpCard> getPowerUpDiscardPile() {
        return powerUpDiscardPile;
    }

}