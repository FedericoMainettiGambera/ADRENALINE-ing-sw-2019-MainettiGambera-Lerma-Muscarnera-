package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.model.Board;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.events.viewControllerEvents.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.WaitForPlayerInput;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.view.components.PowerUpCardV;

public class TagBackGranadeState implements State{
    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(TurnState.class.getName());

    private Thread inputTimer;

    private State nextState;
    private List<Player> damagedPlayers;
    private Player shootingPlayer;
    private List<PowerUpCard> listOfTagBackgranade;
    private Player currentPlayer;

    public TagBackGranadeState(State nextState, List<Player> damagedPlayers, Player shootingPlayer){
        out.println("<SERVER> New state: " + this.getClass());
        this.nextState= nextState;
        this.shootingPlayer = shootingPlayer;
        this.damagedPlayers = damagedPlayers;
    }

    //concept: continuously ask to all the player in the damagedPlayer if they want to use the TagbackGranade untill the list is empty
    @Override
    public void askForInput(Player nullPlayer) {
        //player to ask is null
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player");
        if(!this.damagedPlayers.isEmpty()) {
            //set the currentPlayer as the first damagedPlayer
            this.currentPlayer = this.damagedPlayers.get(0);

            //update the listOfTagbackGranades
            this.listOfTagBackgranade = getListOfTagBackGranade(currentPlayer);

            if(!this.listOfTagBackgranade.isEmpty()){
                out.println("<SERVER> player don't have tagbackgranades.");
                askNextDamagedPlayer();
            }
            else {
                //println listOfTagBackGranade
                out.println("<SERVER> possible TagBakc Granades that " + currentPlayer.getNickname() + " can use:");
                for (PowerUpCard p : this.listOfTagBackgranade) {
                    out.println("         " + p.getName() + "    COLOR: " + p.getColor() + "   ID: " + p.getID());
                }

                //transform listOfTagbackGranade in V types
                List<PowerUpCardV> listOfTagBackGranadesV = new ArrayList<>();
                for (PowerUpCard p : this.listOfTagBackgranade) {
                    listOfTagBackGranadesV.add(p.buildPowerUpCardV());
                }

                //check if he can see the shooting player
                if(canSee(currentPlayer, shootingPlayer)) {
                    try {
                        SelectorGate.getCorrectSelectorFor(currentPlayer).setPlayerToAsk(currentPlayer);
                        //ask him what TagBackgranade want to use or if he wants to "skip" the tagbackgranade state
                        SelectorGate.getCorrectSelectorFor(currentPlayer).askTagBackGranade(listOfTagBackGranadesV);
                        this.inputTimer = new Thread(new WaitForPlayerInput(this.currentPlayer, this.getClass().toString()));
                        this.inputTimer.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    out.println("<SERVER> player can't see who shot him and so he can't use the tagBackgranade.");
                    askNextDamagedPlayer();
                }
            }
        }
        else{
            out.println("<SERVER> ended asking to use Tagback granades to damaged players");
            passToNextState();
        }
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        this.inputTimer.interrupt();
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventInt VCEInt = (ViewControllerEventInt)VCE;
        //the answer is an int that represents the number of the TagBackGranade the user wants to use from the listOfTagBackGranade.
        //if the int exceeds the size of the listOfTagBackGranade, it means the player doesn't want to use it

        if(VCEInt.getInput() != this.listOfTagBackgranade.size()){//means the player want to use it
            //take the correct TagBackGranade from the listOfTagbackgranade
            PowerUpCard chosenTagBackGranade = this.listOfTagBackgranade.get(VCEInt.getInput());
            out.println("<SERVER> chosen tag back granade is : " + chosenTagBackGranade.getName() + "    COLOR: " + chosenTagBackGranade.getColor() + "    ID: " + chosenTagBackGranade.getID());

            //mark the shooting player
            out.println("<SERVER> marking the shooting player");
            this.shootingPlayer.addMarksFrom(currentPlayer,1);

            //discard the tagBackGranade
            out.println("<SERVER> discarding th echone tag back granade");
            this.currentPlayer.getPowerUpCardsInHand().moveCardTo(ModelGate.model.getPowerUpDiscardPile(), chosenTagBackGranade.getID());
        }
        else{//the player doesn't want to use the tag back granade
            out.println("<SERVER> player doesn't want to use tagBackGranade");
        }
        askNextDamagedPlayer();
    }

    public boolean canSee(Player currentPlayer, Player shootingPlayer){
        List<Player> playersViewable = Board.getCanSeePlayerFrom(currentPlayer.getPosition());
        for (Player p: playersViewable) {
            if(p.getNickname().equals(shootingPlayer.getNickname())){
                return true;
            }
        }
        return false;
    }

    public void askNextDamagedPlayer(){
        //remove the currentPlayer from this.listOfDamagedPlayers
        this.damagedPlayers.remove(currentPlayer);
        //repeat the process with the next damaged player
        askForInput(null);
    }

    public void passToNextState(){
        ViewControllerEventHandlerContext.setNextState(this.nextState);
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
    }

    public List<PowerUpCard> getListOfTagBackGranade(Player player){
        List<PowerUpCard> listOfTagBackGranade = new ArrayList<>();
        for (PowerUpCard p : player.getPowerUpCardsInHand().getCards()) {
            if(p.getName().equalsIgnoreCase("TAGBACK GRANADE")){
                listOfTagBackGranade.add(p);
            }
        }
        return listOfTagBackGranade;
    }

    @Override
    public void handleAFK() {
        //TODO
        //this.playerToAsk.setAFKWithNotify(true);
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");

    }
}