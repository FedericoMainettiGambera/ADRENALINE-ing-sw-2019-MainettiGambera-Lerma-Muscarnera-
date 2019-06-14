package it.polimi.se2019.model;

import it.polimi.se2019.model.enumerations.ModelViewEventTypes;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.model.events.stateEvent.StateEvent;
import it.polimi.se2019.view.components.GameV;
import it.polimi.se2019.virtualView.VirtualView;

import java.io.File;
import java.io.Serializable;
import java.util.Observable;

/***/
public class Game extends Observable implements Serializable {

    /***/
    public Game() {
        this.powerUpDeck = new OrderedCardList<>("powerUpDeck");
        this.weaponDeck = new OrderedCardList<>("weaponDeck");
        this.ammoDeck = new OrderedCardList<>("ammoDeck");
        this.ammoDiscardPile = new OrderedCardList<>("ammoDiscardPile");
        this.powerUpDiscardPile = new OrderedCardList<>("powerUpDiscardPile");
    }

    private static int numberOfClientsConnected = 0;

    public static boolean hasGameBegun = false;

    public int getNumberOfClientsConnected(){
        return this.numberOfClientsConnected;
    }

    private boolean hasTimerBegun = false;

    public void setNumberOfClientsConnected(int numberOfClientsConnected){
        System.out.println("         MODELGATE: SETTING NUMBER OF CONNECTION");
        this.numberOfClientsConnected = numberOfClientsConnected;
        if(!hasTimerBegun) {
            if (this.numberOfClientsConnected >= GameConstant.minNumberOfPlayerPerGame) {
                this.hasTimerBegun = true;
                Thread t = new Thread(new ConnectionGameCountDown(this.numberOfClientsConnected));
                t.start();
            }
        }
    }

    /***/
    private KillShotTrack killshotTrack;

    private String currentState;

    public void setCurrentState(String currentState){
        this.currentState = currentState;
        setChanged();
        notifyObservers(new StateEvent(this.currentState));
    }

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
    private transient VirtualView VVSOcket;
    private transient VirtualView VVRMI;

    public void setVirtualView(VirtualView VVSocket, VirtualView VVRMI){
        this.VVSOcket = VVSocket;
        this.VVRMI = VVRMI;
    }

    public VirtualView getSocketVirtualView(){
        return this.VVSOcket;
    }

    public VirtualView getRMIVirtualView(){
        return this.VVRMI;
    }

    public void registerVirtualView(){
        this.addObserver(this.VVRMI);
        this.addObserver(this.VVSOcket);
        System.out.println("    VirtualView added to the Game's observers");
        this.getPlayerList().addObserver(this.VVSOcket);
        this.getPlayerList().addObserver(this.VVRMI);
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
        int fileCount = directory.list().length;
        for(int i = 1; i< fileCount+1; i++) {
            try {

                /* TO BUILD A DECK OF CARD YOU WANT ( DEBUGGING ) :*/
                /*
                WeaponCard card= new WeaponCard("" + 1);
                card.reload();
                tempWeaponDeck.addCard(card);
                System.out.println("<SERVER> building weapon cards ID: " + 1 );
                WeaponCard card2= new WeaponCard("" + 2);
                card2.reload();
                tempWeaponDeck.addCard(card2);
                System.out.println("<SERVER> building weapon cards ID: " + 2);
                */


                WeaponCard card= new WeaponCard("" + i);
                card.reload();
                tempWeaponDeck.addCard(card);
                System.out.println("<SERVER> building weapon cards ID: " + i);


            }
            catch(Exception e) {
                e.printStackTrace();
                return;
            }
        }
        /*
        //builds power up cards
        OrderedCardList<PowerUpCard> tempPowerUpDeck = new OrderedCardList<>("powerUpDeck);
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
        OrderedCardList<AmmoCard> tempAmmoDeck = new OrderedCardList<>("ammoDeck");
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
        setChanged();
        notifyObservers(new ModelViewEvent(this.getWeaponDeck().buildDeckV(), ModelViewEventTypes.movingCardsAround));

        //this.getAmmoDeck().getCards().addAll(tempAmmoDeck.getCards());
        //setChanged();
        //notifyObservers(new ModelViewEvent(this.getWeaponDeck().buildDeckV(), ModelViewEventTypes.movingCardsAround));

        //this.getPowerUpDeck.getCards().addAll(tempPowerUpDeck.getCards());
        //setChanged();
        //notifyObservers(new ModelViewEvent(this.getWeaponDeck().buildDeckV(), ModelViewEventTypes.movingCardsAround));

    }

    public void setPlayerList(PlayersList players) {
        this.players = players;
        setChanged();
        notifyObservers(new ModelViewEvent(this.players.buildPlayersListV(), ModelViewEventTypes.newPlayersList));
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

        gameV.setPlayers(this.players.buildPlayersListV());
        gameV.setBoard(this.board.buildBoardV());
        gameV.setPowerUpDiscardPile(this.powerUpDiscardPile.buildDeckV());
        gameV.setAmmoDiscardPile(this.ammoDiscardPile.buildDeckV());
        gameV.setAmmoDeck(this.ammoDeck.buildDeckV());
        gameV.setWeaponDeck(this.weaponDeck.buildDeckV());
        gameV.setPowerUpDeck(this.powerUpDeck.buildDeckV());
        gameV.setKillshotTrack(this.killshotTrack.buildKillshotTrackV());
        gameV.setHasFinalFrenzyBegun(this.hasFinalFrenzyBegun);
        gameV.setFinalFrenzy(this.isFinalFrenzy);

        return gameV;
    }
}
