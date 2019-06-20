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
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.logging.Logger;

public class GameSetUpState implements State {

    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(GameSetUpState.class.getName());

    private ObjectOutputStream objectOutputStream;

    private Player playerToAsk;

    private Thread inputTimer;

    public GameSetUpState(){
        out.println("<SERVER> New state: " + this.getClass());
    }

    @Override
    public void askForInput(Player playerToAsk) {
        this.playerToAsk = playerToAsk;
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        out.println("<SERVER> Setting hasGameBegun to true.");
        ModelGate.model.hasGameBegun = true;

        out.println("<SERVER> Setting Starting Player.");
        ModelGate.model.getPlayerList().setStartingPlayer(playerToAsk);
        ModelGate.model.getPlayerList().setCurrentPlayingPlayer(ModelGate.model.getPlayerList().getStartingPlayer());

        //ask for gameSetUp to the player
        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
            SelectorGate.getCorrectSelectorFor(playerToAsk).askGameSetUp();
            this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
            this.inputTimer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doAction(ViewControllerEvent VCE){
        this.inputTimer.interrupt();
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventGameSetUp VCEGameSetUp = (ViewControllerEventGameSetUp)VCE;

        out.println("<SERVER> Setting up Game in normal mode.");

        try {
            out.println("<SERVER> Creating Map: " + VCEGameSetUp.getMapChoice());
            ModelGate.model.setBoard(new Board(VCEGameSetUp.getMapChoice(), ModelGate.model.getSocketVirtualView(), ModelGate.model.getRMIVirtualView()));
        }
        catch (IOException|NullPointerException e){
           logger.severe("Creating map went wrong"+e.getCause()+ Arrays.toString(e.getStackTrace()));

        }

        out.println("<SERVER> MAP: \n" + ModelGate.model.getBoard().toString());

        out.println("<SERVER> Creating Killshot Track with " +
                            VCEGameSetUp.getNumberOfStartingSkulls() +
                            " number of starting skulls.");
        ModelGate.model.setKillshotTrack(new KillShotTrack(VCEGameSetUp.getNumberOfStartingSkulls(), ModelGate.model.getSocketVirtualView(), ModelGate.model.getRMIVirtualView()));

        out.println("<SERVER> Setting Final Frenzy: " + VCEGameSetUp.isFinalFrezy());
        ModelGate.model.setFinalFrenzy(VCEGameSetUp.isFinalFrezy());

        out.println("<SERVER> Setting a Bot: "+ VCEGameSetUp.isBotActive());
        ModelGate.model.setBot(new Bot(VCEGameSetUp.isBotActive()));

        //registering VV as Observer of the Decks
        ModelGate.model.getWeaponDeck().addObserver(ModelGate.model.getSocketVirtualView());
        ModelGate.model.getWeaponDeck().addObserver(ModelGate.model.getRMIVirtualView());

        ModelGate.model.getPowerUpDeck().addObserver(ModelGate.model.getSocketVirtualView());
        ModelGate.model.getPowerUpDeck().addObserver(ModelGate.model.getRMIVirtualView());

        ModelGate.model.getAmmoDeck().addObserver(ModelGate.model.getSocketVirtualView());
        ModelGate.model.getAmmoDeck().addObserver(ModelGate.model.getRMIVirtualView());

        //create cards
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
                    out.println("<SERVER> Placed Weapond cards on square [" + i + "][" + j + "]");

                }
            }
        }

        for (Player p :ModelGate.model.getPlayerList().getPlayers()) {

            out.println("<SERVER> Adding Observers to the Player weapons and power ups");
            p.getWeaponCardsInHand().addObserver(ModelGate.model.getSocketVirtualView());
            p.getWeaponCardsInHand().addObserver(ModelGate.model.getRMIVirtualView());
            p.getPowerUpCardsInHand().addObserver(ModelGate.model.getSocketVirtualView());
            p.getWeaponCardsInHand().addObserver(ModelGate.model.getRMIVirtualView());

            //draw two power up cards
            out.println("<SERVER> draw two power up cards.");
            for(int i = 0; i < 2; i++){
                ModelGate.model.getPowerUpDeck().moveCardTo(
                        p.getPowerUpCardsInHand(),
                        ModelGate.model.getPowerUpDeck().getFirstCard().getID()
                );
            }

            //set starting ammocubes
            out.println("<SERVER> setting starting ammo cubes");
            for(AmmoCubesColor color: AmmoCubesColor.values() ) {
                p.getPlayerBoard().addAmmoCubes(color, GameConstant.NumberOfStartingAmmos);
            }
        }

        //setting next State
        ViewControllerEventHandlerContext.setNextState(new FirstSpawnState());
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
    }

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
