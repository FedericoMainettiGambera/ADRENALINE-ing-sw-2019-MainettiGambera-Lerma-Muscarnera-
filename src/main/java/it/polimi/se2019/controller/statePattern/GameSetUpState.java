package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.model.enumerations.SquareTypes;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventGameSetUp;
import it.polimi.se2019.controller.WaitForPlayerInput;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.logging.Logger;


/** this class set up the game options as desired by the user*/
public class GameSetUpState implements State {

    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(GameSetUpState.class.getName());

    private Player playerToAsk;

    private Thread inputTimer;

    public GameSetUpState(){
        out.println("<SERVER> New state: " + this.getClass());
    }

    /** this functions ask the required information to the starting player
     * if there's less than 5 human player, make possible to add a bot
     * @param playerToAsk refers to the starting player
     * */
    @Override
    public void askForInput(Player playerToAsk) {

        gameHasBegun(playerToAsk);

        //ask for gameSetUp to the player
        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
           //if there's 5 player no room for bot
            if(ModelGate.model.getPlayerList().getPlayers().size()==GameConstant.maxNumberOfPlayerPerGame) {
                SelectorGate.getCorrectSelectorFor(playerToAsk).askGameSetUp(false);
            }
            else SelectorGate.getCorrectSelectorFor(playerToAsk).askGameSetUp(true);
            this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
            this.inputTimer.start();
        } catch (Exception e) {
            logger.severe("Exception occurred:"+" "+ " "+ Arrays.toString(e.getStackTrace())+e.getClass()+e.getCause());

        }
    }

    public void gameHasBegun(Player playerToAsk){

        this.playerToAsk = playerToAsk;

        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        out.println("<SERVER> Setting hasGameBegun to true.");

        Game.setHasGameBegun(true);

        out.println("<SERVER> Setting Starting Player.");
        ModelGate.model.getPlayerList().setStartingPlayer(playerToAsk);
        ModelGate.model.getPlayerList().setCurrentPlayingPlayer(ModelGate.model.getPlayerList().getStartingPlayer());


    }

    /**
     * this doAction initialize the Game as indicated in
     * @param viewControllerEvent  received from the user
     * 1 creates the map
     * 2 create the killshot track with desired number of skulls
     * 3 sets or doesnt set the Final Frenzy Mode
     * 4 if possible and desired, sets a botx
     * */
    @Override
    public void doAction(ViewControllerEvent viewControllerEvent){

        this.inputTimer.interrupt();

        gameSetUp(viewControllerEvent);

        createDecks();

        preparePlayers();

        //setting next State
        ViewControllerEventHandlerContext.setNextState(new FirstSpawnState());
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
    }

    /**set up the game extrapolating the information needed from
     * @param viewControllerEvent, which contains the options chosen by the starting player*/

    public void gameSetUp(ViewControllerEvent viewControllerEvent){

        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventGameSetUp viewControllerEventGameSetUp = (ViewControllerEventGameSetUp)viewControllerEvent;

        out.println("<SERVER> Setting up Game in normal mode.");

        try {
            out.println("<SERVER> Creating Map: " + viewControllerEventGameSetUp.getMapChoice());
            ModelGate.model.setBoard(new Board(viewControllerEventGameSetUp.getMapChoice(), ModelGate.model.getSocketVirtualView(), ModelGate.model.getRMIVirtualView()));
        }
        catch (IOException|NullPointerException e){
            logger.severe("Creating map went wrong"+e.getCause()+ Arrays.toString(e.getStackTrace()));

        }

        out.println("<SERVER> MAP: \n" + ModelGate.model.getBoard().toString());

        out.println("<SERVER> Creating Killshot Track with " +
                viewControllerEventGameSetUp.getNumberOfStartingSkulls() +
                " number of starting skulls.");
        ModelGate.model.setKillshotTrack(new KillShotTrack(viewControllerEventGameSetUp.getNumberOfStartingSkulls(), ModelGate.model.getSocketVirtualView(), ModelGate.model.getRMIVirtualView()));

        out.println("<SERVER> Setting Final Frenzy: " + viewControllerEventGameSetUp.isFinalFrezy());
        ModelGate.model.setFinalFrenzy(viewControllerEventGameSetUp.isFinalFrezy());

        out.println("<SERVER> Setting a Bot: "+ viewControllerEventGameSetUp.isBotActive());
        if(viewControllerEventGameSetUp.isBotActive()) {
            ModelGate.model.getPlayerList().addPlayer(new Player(true));
            ModelGate.model.setBotActive(true);
        }
        else {
            ModelGate.model.setBotActive(false);
        }
    }



    /**set players ready to play*/
    public void preparePlayers(){

        for (Player p :ModelGate.model.getPlayerList().getPlayers()) {

            if (!p.isBot()){

                out.println("<SERVER> Adding Observers to the Player weapons and power ups");
             if((ModelGate.model.getSocketVirtualView()!=null)) {
                    p.getWeaponCardsInHand().addObserver(ModelGate.model.getSocketVirtualView());
                    p.getPowerUpCardsInHand().addObserver(ModelGate.model.getSocketVirtualView());
                }
                if(ModelGate.model.getRMIVirtualView()!=null){
                    p.getWeaponCardsInHand().addObserver(ModelGate.model.getRMIVirtualView());
                    p.getWeaponCardsInHand().addObserver(ModelGate.model.getRMIVirtualView());
                }

                //draw two power up cards
                out.println("<SERVER> draw two power up cards.");
                for (int i = 0; i < 2; i++) {
                    ModelGate.model.getPowerUpDeck().moveCardTo(
                            p.getPowerUpCardsInHand(),
                            ModelGate.model.getPowerUpDeck().getFirstCard().getID()
                    );
                }

                //set starting ammocubes
                out.println("<SERVER> setting starting ammo cubes");
                for (AmmoCubesColor color : AmmoCubesColor.values()) {
                    p.getPlayerBoard().addAmmoCubes(color, GameConstant.NumberOfStartingAmmos);
                }
            }
        }
    }

    /**create Decks, shuffle them and place the cards on the Board*/
    public void createDecks(){

        //registering VV as Observer of the Decks
        if((ModelGate.model.getSocketVirtualView()!=null)){
            ModelGate.model.getWeaponDeck().addObserver(ModelGate.model.getSocketVirtualView());
            ModelGate.model.getPowerUpDeck().addObserver(ModelGate.model.getSocketVirtualView());
            ModelGate.model.getAmmoDeck().addObserver(ModelGate.model.getSocketVirtualView());
        }

        if(ModelGate.model.getRMIVirtualView()!=null){
            ModelGate.model.getWeaponDeck().addObserver(ModelGate.model.getRMIVirtualView());
            ModelGate.model.getPowerUpDeck().addObserver(ModelGate.model.getRMIVirtualView());
            ModelGate.model.getAmmoDeck().addObserver(ModelGate.model.getRMIVirtualView());
        }


        out.println("<SERVER> Building decks.");

        ModelGate.model.buildDecks();

        //shuffles cards
        out.println("<SERVER> Shuffling decks");
        ModelGate.model.getPowerUpDeck().shuffle();
        ModelGate.model.getAmmoDeck().shuffle();
        ModelGate.model.getWeaponDeck().shuffle();

        //place cards on the board
        for(int i =0; i<ModelGate.model.getBoard().getMap().length;i++){

            for(int j=0; j<ModelGate.model.getBoard().getMap()[0].length; j++){

                Square timeSquare=ModelGate.model.getBoard().getSquare(i,j);//lol cuz its a temporary square

                if( (timeSquare!=null) && (timeSquare.getSquareType() == SquareTypes.normal) ){
                    OrderedCardList<AmmoCard> ammoCards=((NormalSquare)timeSquare).getAmmoCards();
                    ModelGate.model.getAmmoDeck().moveCardTo(
                            ammoCards,
                            ModelGate.model.getAmmoDeck().getFirstCard().getID()
                    );
                    out.println("<SERVER> Placed Ammo card on square [" + i + "][" + j + "]");
                }
                else if((timeSquare!=null) && (timeSquare.getSquareType()==SquareTypes.spawnPoint)){

                    OrderedCardList<WeaponCard> weaponCards=((SpawnPointSquare)timeSquare).getWeaponCards();
                    for(int t=0; t<3; t++){
                        ModelGate.model.getWeaponDeck().moveCardTo(
                                weaponCards,
                                ModelGate.model.getWeaponDeck().getFirstCard().getID()
                        );
                    }
                    out.println("<SERVER> Placed Weapon cards on square [" + i + "][" + j + "]");

                }
            }
        }



    }

    /**
     * set the player AFK in case they don't send required input in a while
     * */
    @Override
    public void handleAFK() {
        this.playerToAsk.setAFKWithNotify(true);
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        ModelGate.model.getPlayerList().setNextPlayingPlayer();
        if(!ViewControllerEventHandlerContext.state.getClass().toString().contains("FinalScoringState")) {
            ViewControllerEventHandlerContext.setNextState(new GameSetUpState());
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
    }
}
