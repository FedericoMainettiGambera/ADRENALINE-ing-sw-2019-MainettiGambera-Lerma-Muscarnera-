package it.polimi.se2019.model;


import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.virtualView.VirtualView;

import java.io.File;
import java.io.Serializable;
import java.util.Observable;

/***/
public class Game extends Observable implements Serializable {

    /***/
    public Game() {
        this.powerUpDeck = new OrderedCardList<>();
        this.weaponDeck = new OrderedCardList<>();
        this.ammoDeck = new OrderedCardList<>();
        this.ammoDiscardPile = new OrderedCardList<>();
        this.powerUpDiscardPile = new OrderedCardList<>();
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

    private boolean hasFinalFrenzyBegun;

    /***/
    private Board board;

    private boolean isFinalFrenzy;

    //reference to the VV so that it can be registered in all the model.
    private transient VirtualView VV;

    public void setVirtualView(VirtualView VV){
        this.VV = VV;
    }

    public VirtualView getVirtualView(){
        return this.VV;
    }

    public void registerVirtualView(){
        this.addObserver(this.VV);
        System.out.println("    VirtualView added to the Game's observers");
        this.getPlayerList().addObserver(this.VV);
        System.out.println("    VirtualView added to the PlayerList's observers");
        for (int i = 0; i < this.getPlayerList().getPlayers().size() ; i++) {
            this.getPlayerList().getPlayers().get(i).addObserver(this.VV);
        }
        System.out.println("    VirtualView added to the Players' observers");
    }

    public Player getCurrentPlayingPlayer(){
        return this.getPlayerList().getCurrentPlayingPlayer();
    }

    public void setFinalFrenzy(boolean isFinalFrenzy){
        this.isFinalFrenzy = isFinalFrenzy;
        setChanged();
        notifyObservers("FINAL FRENZY SETTED :" + this.isFinalFrenzy);
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
        notifyObservers("FINAL FRENZY HAS BEGUN :" + this.hasFinalFrenzyBegun);
    }


    /***/
    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
        //setChanged();
        //notifyObservers();
    }

    /***/
    public KillShotTrack getKillshotTrack() {
        return killshotTrack;
    }

    public void setKillshotTrack(KillShotTrack killshotTrack) {
        this.killshotTrack = killshotTrack;
        setChanged();
        notifyObservers(this.killshotTrack);
    }

    /***/
    public PlayersList getPlayerList() {
        return players;
    }

    public void buildDecks() {

        //builds weapon cards
        OrderedCardList<WeaponCard> tempWeaponDeck = new OrderedCardList<>();
        File directory = new File("src/main/Files/cards/weaponCards");     // insert here path to weapon cards folder
        int fileCount = directory.list().length;
        for(int i = 1; i< fileCount+1; i++) {
            System.out.println("<SERVER>building weapon cards ID: " + i);
            try {
                 tempWeaponDeck.addCard(new WeaponCard("" + i));
            }
            catch(Exception e) {
                e.printStackTrace();
                return;
            }
        }
        /*
        //builds power up cards
        OrderedCardList<PowerUpCard> tempPowerUpDeck = new OrderedCardList<>();
        directory = new File("src/main/Files/cards/powerUpCards");          // insert here path to power up cards folder
        fileCount = directory.list().length;
        for(int i = 1; i< fileCount;i++) {
            System.out.println("<SERVER>building weapon cards ID: " + i);
            try {
                tempPowerUpDeck.addCard(new PowerUpCard("" + i));
            }
            catch(Exception e) {
                e.printStackTrace();
                return;
            }
        }

        //builds ammo cards
        OrderedCardList<AmmoCard> tempAmmoDeck = new OrderedCardList<>();
        directory = new File("src/main/Files/cards/ammoCards");          // insert here path to ammo cards folder
        fileCount = directory.list().length;
        for(int i = 1; i< fileCount;i++) {
            try {
                //TODO: tempAmmoDeck.addCard(new AmmoCard("" + i));
            }
            catch(Exception e) {
                e.printStackTrace();
                return;
            }
        }
        */

        this.getWeaponDeck().getCards().addAll(tempWeaponDeck.getCards());
        //this.getAmmoDeck().getCards().addAll(tempAmmoDeck.getCards());
        //this.getPowerUpDeck.getCards().addAll(tempPowerUpDeck.getCards());

    }

    public void setPlayerList(PlayersList players) {
        this.players = players;
        setChanged();
        notifyObservers(this.players);
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
        notifyObservers(this.board);
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
