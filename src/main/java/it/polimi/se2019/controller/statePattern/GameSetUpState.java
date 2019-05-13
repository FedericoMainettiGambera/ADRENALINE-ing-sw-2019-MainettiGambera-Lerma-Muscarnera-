package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.model.enumerations.SquareTypes;
import it.polimi.se2019.model.events.ViewControllerEvent;
import it.polimi.se2019.model.events.ViewControllerEventGameSetUp;
import it.polimi.se2019.virtualView.Socket.VirtualViewSelector;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class GameSetUpState implements State {

    private ObjectOutputStream objectOutputStream;

    public GameSetUpState(){
        System.out.println("<SERVER> New state: " + this.getClass());
    }

    @Override
    public void askForInput(Player playerToAsk) {
        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        //ask for gameSetUp to the player
        SelectorGate.selector.setPlayerToAsk(playerToAsk);
        SelectorGate.selector.askGameSetUp();
    }

    @Override
    public void doAction(ViewControllerEvent VCE){
        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventGameSetUp VCEGameSetUp = (ViewControllerEventGameSetUp)VCE;

        System.out.println("<SERVER>Setting Starting Player.");
        ModelGate.model.getPlayerList().setStartingPlayer(ModelGate.model.getPlayerList().getPlayer("User1"));
        ModelGate.model.getPlayerList().setCurrentPlayingPlayer(ModelGate.model.getPlayerList().getStartingPlayer());

        if(VCEGameSetUp.getGameMode().equals("normalMode")){
            System.out.println("<SERVER>Setting up Game in normal mode.");
            try {
                System.out.println("<SERVER>creating Map: " + VCEGameSetUp.getMapChoice());
                ModelGate.model.setBoard(new Board(VCEGameSetUp.getMapChoice()));
            }
            catch (IOException e){
                e.printStackTrace();
            }
            catch (NullPointerException e){
                e.printStackTrace();
            }
            System.out.println("<SERVER> MAP: \n" + ModelGate.model.getBoard().toString());

            System.out.println("<SERVER>creating Killshot Track with " +
                                VCEGameSetUp.getNumberOfStartingSkulls() +
                                " number of starting skulls.");
            ModelGate.model.setKillshotTrack(new KillShotTrack(VCEGameSetUp.getNumberOfStartingSkulls()));

            System.out.println("<SERVER>Setting Final Frenzy: " + VCEGameSetUp.isFinalFrezy());
            ModelGate.model.setFinalFrenzy(VCEGameSetUp.isFinalFrezy());

            System.out.println("<SERVER>Setting a Bot: "+ VCEGameSetUp.isBotActive());
            ModelGate.model.setBot(new Bot(VCEGameSetUp.isBotActive()));

            //create cards
            System.out.println("<SERVER> Building decks.");
            //ModelGate.model.buildDecks();

            System.out.println("<SERVER> adding 100 fake ammo cards to the ammoDeck.");
            AmmoList ammoList = new AmmoList();
            ammoList.addAmmoCubesOfColor(AmmoCubesColor.yellow, 2);
            for (int i = 0; i < 100; i++) {
                ModelGate.model.getAmmoDeck().getCards().add(new AmmoCard("fake", ammoList, false));
            }
            System.out.println("<SERVER> adding 100 fake WeaponCards to the weaponDeck.");
            for (int i = 0; i < 100; i++) {
                try {
                    ModelGate.model.getWeaponDeck().getCards().add(new WeaponCard("fake"));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("<SERVER> adding 100 fake PowerUpCards to the powerUpDeck.");
            for (int i = 0; i < 100; i++) {
                ModelGate.model.getPowerUpDeck().getCards().add(new PowerUpCard());
            }

            //shuffles cards
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

                    }
                    else if((timeSquare!=null) && (timeSquare.getSquareType()==SquareTypes.spawnPoint)){

                        OrderedCardList<WeaponCard> weaponCards=((SpawnPointSquare)timeSquare).getWeaponCards();
                        for(int t=0; t<3; t++){

                            ModelGate.model.getWeaponDeck().moveCardTo(
                                    weaponCards,
                                    ModelGate.model.getWeaponDeck().getFirstCard().getID()
                            );

                        }

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
}
