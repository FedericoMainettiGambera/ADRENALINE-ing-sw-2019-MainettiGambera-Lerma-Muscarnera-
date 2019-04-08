package it.polimi.se2018.model;

import java.util.*;


public class Game {

    
    public Game() {
    }

    
    private KillShotTrack killshotTrack;

    
    private PlayersList players;

    
    private Turns turn;

    
    private Bot bot;

    
    private OrderedCardList<PowerUpCard> powerUpDeck;

    
    private OrderedCardList<WeaponCard> weaponDeck;

    
    private OrderedCardList<AmmoCard> ammoDeck;

    
    private OrderedCardList<PowerUpCard> powerUpDiscardPile;

    
    private OrderedCardList<AmmoCard> ammoDiscardPile;

    
    private Board board;

    
    private static boolean gameSingleton;


}