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

public class GameSetUpState implements State {

    private ObjectOutputStream objectOutputStream;

    private Player playerToAsk;

    private Thread inputTimer;

    public GameSetUpState(){
        System.out.println("<SERVER> New state: " + this.getClass());
    }

    @Override
    public void askForInput(Player playerToAsk) {
        this.playerToAsk = playerToAsk;
        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        System.out.println("<SERVER> Setting hasGameBegun to true.");
        ModelGate.model.hasGameBegun = true;

        System.out.println("<SERVER> Setting Starting Player.");
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
        System.out.println("<SERVER> player has answered before the timer ended.");

        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventGameSetUp VCEGameSetUp = (ViewControllerEventGameSetUp)VCE;

        if(VCEGameSetUp.getGameMode().equals("normalMode")){
            System.out.println("<SERVER> Setting up Game in normal mode.");

            try {
                System.out.println("<SERVER> Creating Map: " + VCEGameSetUp.getMapChoice());
                ModelGate.model.setBoard(new Board(VCEGameSetUp.getMapChoice(), ModelGate.model.getSocketVirtualView(), ModelGate.model.getRMIVirtualView()));
            }
            catch (IOException e){
                e.printStackTrace();
            }
            catch (NullPointerException e){
                e.printStackTrace();
            }
            System.out.println("<SERVER> MAP: \n" + ModelGate.model.getBoard().toString());

            System.out.println("<SERVER> Creating Killshot Track with " +
                                VCEGameSetUp.getNumberOfStartingSkulls() +
                                " number of starting skulls.");
            ModelGate.model.setKillshotTrack(new KillShotTrack(VCEGameSetUp.getNumberOfStartingSkulls(), ModelGate.model.getSocketVirtualView(), ModelGate.model.getRMIVirtualView()));

            System.out.println("<SERVER> Setting Final Frenzy: " + VCEGameSetUp.isFinalFrezy());
            ModelGate.model.setFinalFrenzy(VCEGameSetUp.isFinalFrezy());

            System.out.println("<SERVER> Setting a Bot: "+ VCEGameSetUp.isBotActive());
            ModelGate.model.setBot(new Bot(VCEGameSetUp.isBotActive()));

            //registering VV as Observer of the Decks
            ModelGate.model.getWeaponDeck().addObserver(ModelGate.model.getSocketVirtualView());
            ModelGate.model.getWeaponDeck().addObserver(ModelGate.model.getRMIVirtualView());

            ModelGate.model.getPowerUpDeck().addObserver(ModelGate.model.getSocketVirtualView());
            ModelGate.model.getPowerUpDeck().addObserver(ModelGate.model.getRMIVirtualView());

            ModelGate.model.getAmmoDeck().addObserver(ModelGate.model.getSocketVirtualView());
            ModelGate.model.getAmmoDeck().addObserver(ModelGate.model.getRMIVirtualView());

            //create cards
            System.out.println("<SERVER> Building decks.");
            ModelGate.model.buildDecks();


            System.out.println("<SERVER> Adding 100 fake ammo cards to the ammoDeck.");
            AmmoList ammoList = new AmmoList();
            ammoList.addAmmoCubesOfColor(AmmoCubesColor.yellow, 2);
            OrderedCardList<AmmoCard> orderedCardListAmmo = new OrderedCardList<>("ammoDeck");
            for (int i = 0; i < 100; i++) {
                orderedCardListAmmo.getCards().add(new AmmoCard("fake", ammoList, false));
            }
            orderedCardListAmmo.moveAllCardsTo(ModelGate.model.getAmmoDeck());


            System.out.println("<SERVER> Adding 100 fake PowerUpCards to the powerUpDeck.");
            OrderedCardList<PowerUpCard> orderedCardListPowerUp = new OrderedCardList<>("powerUpDeck");
            for (int i = 0; i < 100; i++) {
                orderedCardListPowerUp.getCards().add(new PowerUpCard());
            }
            orderedCardListPowerUp.moveAllCardsTo(ModelGate.model.getPowerUpDeck());

            //shuffles cards
            System.out.println("<SERVER> Shuffling decks");
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
                        System.out.println("<SERVER> Placed Ammo card on square [" + i + "][" + j + "]");
                    }
                    else if((timeSquare!=null) && (timeSquare.getSquareType()==SquareTypes.spawnPoint)){

                        OrderedCardList<WeaponCard> weaponCards=((SpawnPointSquare)timeSquare).getWeaponCards();
                        for(int t=0; t<3; t++){
                            ModelGate.model.getWeaponDeck().moveCardTo(
                                    weaponCards,
                                    ModelGate.model.getWeaponDeck().getFirstCard().getID()
                            );
                        }
                        System.out.println("<SERVER> Placed Weapond cards on square [" + i + "][" + j + "]");

                    }
                }
            }

            //setting next State
            ViewControllerEventHandlerContext.setNextState(new PlayerSetUpState());
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());

        } else if(VCEGameSetUp.getGameMode().equals("turretMode")){
            //build game in turret mode
        } else if(VCEGameSetUp.getGameMode().equals("dominationMode")){
            //build game in domination mode
        }

    }

    @Override
    public void handleAFK() {
        this.playerToAsk.setIsAFK(true);
        System.out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        ModelGate.model.getPlayerList().setNextPlayingPlayer();
        ViewControllerEventHandlerContext.setNextState(new GameSetUpState());
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
    }
}
