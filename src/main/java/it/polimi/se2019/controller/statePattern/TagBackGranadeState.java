package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.model.Board;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.events.viewControllerEvents.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.WaitForPlayerInput;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.view.components.PowerUpCardV;

/**implements the tag back grenade functionality*/
public class TagBackGranadeState implements State{

    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(TurnState.class.getName());
    /** countdown till the AFK status*/
    private Thread inputTimer;
    /**the following state to be set*/
    private State nextState;
    /**a list of player that have recently been damaged*/
    private List<Player> damagedPlayers;
    /**the player that have recently been giving damage to the player in the above-mentioned list*/
    private Player shootingPlayer;
    /** a list of power up cards all of tag back grenade kind*/
    private List<PowerUpCard> tagBackGrenadeList;
    /**the current playing player*/
    private Player currentPlayer;

    /**constructor,
     * @param shootingPlayer above-mentioned
     * @param damagedPlayers above-mentioned
     * @param nextState above-mentioned*/
    public TagBackGranadeState(State nextState, List<Player> damagedPlayers, Player shootingPlayer){
        out.println("<SERVER> New state: " + this.getClass());
        this.nextState= nextState;
        this.shootingPlayer = shootingPlayer;
        this.damagedPlayers = damagedPlayers;
    }

    /**there will be as many request of input as many tagback grenade power up card the player is holding in their hand
     * */
    @Override
    public void askForInput(Player nullPlayer) {
        //player to ask is null
        out.println("<SERVER> ("+ this.getClass() +") Asking the Player an input");

        if(!this.damagedPlayers.isEmpty()){
            setCurrentPlayer();
            //update the listOfTagbackGrenades
            setListOfTagBackGranade(currentPlayer);

            if(this.tagBackGrenadeList.isEmpty()){
                out.println("<SERVER> player don't have any tagback grenades.");
                askNextDamagedPlayer();
            }
            else {
                //println listOfTagBackGranade
                List<PowerUpCardV> listOfTagBackGranadesV=printAndTransformTagBack();

                //check if he can see the shooting player
                if(canSee(currentPlayer, shootingPlayer)){
                    try {
                        SelectorGate.getCorrectSelectorFor(currentPlayer).setPlayerToAsk(currentPlayer);
                        //ask him what TagBackgranade want to use or if he wants to "skip" the tagbackgranade state
                        SelectorGate.getCorrectSelectorFor(currentPlayer).askTagBackGranade(listOfTagBackGranadesV);
                        this.inputTimer = new Thread(new WaitForPlayerInput(this.currentPlayer, this.getClass().toString()));
                        this.inputTimer.start();
                    } catch (Exception e) {
                       logger.log(Level.SEVERE, "EXCEPTION", e);
                    }
                }
                else{
                    out.println("<SERVER> player can't see who shot him and so he can't use the tagBackgranade.");
                    askNextDamagedPlayer();
                }
            }
        }
        else{
            out.println("<SERVER> ended asking to use Tagback granedes to damaged players");
            passToNextState();
        }
    }

    public void setCurrentPlayer(){
        this.currentPlayer = this.damagedPlayers.get(0);
    }

    /**@return a list of powerupcardV of tagback grenades*/
   public List<PowerUpCardV> printAndTransformTagBack(){

       out.println("<SERVER> possible TagBack Grenades that " + currentPlayer.getNickname() + " can use:");
       for (PowerUpCard p : this.tagBackGrenadeList) {
           out.println("         " + p.getName() + "    COLOR: " + p.getColor() + "   ID: " + p.getID());
       }
       //transform listOfTagbackGranade in V types
       List<PowerUpCardV> listOfTagBackGranadesV = new ArrayList<>();
       for (PowerUpCard p : this.tagBackGrenadeList) {
           listOfTagBackGranadesV.add(p.buildPowerUpCardV());
       }
        return listOfTagBackGranadesV;
   }

    /**@param viewControllerEvent needed to be passed to the giveMark function,*/
    @Override
    public void doAction(ViewControllerEvent viewControllerEvent) {

        this.inputTimer.interrupt();
        giveMark(viewControllerEvent);
        askNextDamagedPlayer();
    }

    /**@param currentPlayer the one who is using the power up card
     * @param shootingPlayer the player who gave them damage
     * @return boolean value that indicates if the current player can see the shootin player
     * */
    public boolean canSee(Player currentPlayer, Player shootingPlayer){
        List<Player> playersViewable = Board.getCanSeePlayerFrom(currentPlayer.getPosition());
        for (Player p: playersViewable) {
            if(p.getNickname().equals(shootingPlayer.getNickname())){
                return true;
            }
        }
        return false;
    }

    /**@param viewControllerEvent from the user needed to extrapolate the information about the willing of the user to
     * use tagback granade, if they want to use it, a damage of the chosen color is added to the shooting player,
     */
    public void giveMark(ViewControllerEvent viewControllerEvent){

        out.println("<SERVER> player has answered before the timer ended.");
        out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventInt viewControllerEventInt = (ViewControllerEventInt)viewControllerEvent;
        //the answer is an int that represents the number of the TagBackGranade the user wants to use from the listOfTagBackGranade.
        //if the int exceeds the size of the listOfTagBackGranade, it means the player doesn't want to use it

        if(viewControllerEventInt.getInput() != this.tagBackGrenadeList.size()){//means the player want to use it
            //take the correct TagBackGranade from the listOfTagbackgranade
            PowerUpCard chosenTagBackGranade = this.tagBackGrenadeList.get(viewControllerEventInt.getInput());
            out.println("<SERVER> chosen tag back granade is : " + chosenTagBackGranade.getName() + "    COLOR: " + chosenTagBackGranade.getColor() + "    ID: " + chosenTagBackGranade.getID());

            //mark the shooting player
            out.println("<SERVER> marking the shooting player");
            this.shootingPlayer.addMarksFrom(currentPlayer,1);

            //discard the tagBackGranade
            out.println("<SERVER> discarding th echone tag back granade");
            this.currentPlayer.getPowerUpCardsInHand().moveCardTo(ModelGate.getModel().getPowerUpDiscardPile(), chosenTagBackGranade.getID());
        }
        else{//the player doesn't want to use the tag back granade
            out.println("<SERVER> player doesn't want to use tagBackGranade");
        }

    }

    private void askNextDamagedPlayer(){
        //remove the currentPlayer from this.listOfDamagedPlayers
        this.damagedPlayers.remove(currentPlayer);
        //repeat the process with the next damaged player
        askForInput(null);
    }


    /**pass to the following  state*/
    private void passToNextState(){
        ViewControllerEventHandlerContext.setNextState(this.nextState);
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.getModel().getCurrentPlayingPlayer());
    }

    public void setListOfTagBackGranade(Player player){
        List<PowerUpCard> listOfTagBackGranade = new ArrayList<>();
        for (PowerUpCard p : player.getPowerUpCardsInHand().getCards()) {
            if(p.getName().equalsIgnoreCase("TAGBACK GRANADE")){
                listOfTagBackGranade.add(p);
            }
        }
        this.tagBackGrenadeList=listOfTagBackGranade;
    }


    /**if the player doesn't answer to the askForInput request before the timer expires,
     * they will be set AFK
     * */
    @Override
    public void handleAFK() {
        //TODO
        //this.playerToAsk.setAFKWithNotify(true);
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");

    }
}