package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.WaitForPlayerInput;
import it.polimi.se2019.model.Board;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventString;
import it.polimi.se2019.view.components.PlayerV;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * this class makes possible for the bot to shoot
 * * @author LudoLerma
 *  * @author FedericoMainettiGambera
 * */
public class BotShootState implements State{

    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(TurnState.class.getName());

    /**the player to be asked the input*/
    private Player playerToAsk;

    /**count down till afk status*/
    private Thread inputTimer;

    /**following state to be set*/
    private State nextState;

    /**name of the bot*/
    private String botNickname="Terminator";

    /**constructor,
     * @param nextState contains data to initialize nextState attribute
     * */
    public BotShootState(State nextState){
        out.println("<SERVER> New state: " + this.getClass());
        this.nextState = nextState;
    }

    /**@param playerToAsk indicates the owner of the bot
     * here, askForInput Function is needed because we need to know whom to shoot, anyway sometimes the bot can't see
     *any target, so no information is needed and the doAction function skipped
     * */
    @Override
    public void askForInput(Player playerToAsk) {

        setPlayerToAsk(playerToAsk);
        List<Player> players = playersCanBotSee();
        List<PlayerV> playersV = new  ArrayList<>();

        for (Player p: players) {
            playersV.add(p.buildPlayerV());
        }

        if(playersV.isEmpty()){
            out.println("<SERVER> bot can't see anybody");
            ViewControllerEventHandlerContext.setNextState(this.nextState);
            ViewControllerEventHandlerContext.getState().askForInput(ModelGate.getModel().getCurrentPlayingPlayer());
        }
        //ask for input
        else {
            try {
                SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
                SelectorGate.getCorrectSelectorFor(playerToAsk).askBotShoot(playersV);
                this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString(), "ask bot shoot"));
                this.inputTimer.start();
            } catch (Exception e) {
                logger.severe("Exception occurred  " + e.getClass() + "  " + e.getCause() + Arrays.toString(e.getStackTrace()));
            }
        }
    }

    /**@param playerToAsk we set here the player to ask */
    private void setPlayerToAsk(Player playerToAsk){
        this.playerToAsk = playerToAsk;
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");
    }

    /**@param vce from which needed information is extrapolated
     * this doAction add damage to a player, chosen by the owner of the bot,
     *  between the ones the bot can see
     * */
    @Override
    public void doAction(ViewControllerEvent vce) {


        this.inputTimer.interrupt();

        List<Player> damagedPlayer=parseVce(vce);

        ViewControllerEventHandlerContext.setNextState(new TagBackGranadeState(this.nextState, damagedPlayer, ModelGate.getModel().getPlayerList().getPlayer("Terminator")));
        ViewControllerEventHandlerContext.getState().askForInput(ModelGate.getModel().getCurrentPlayingPlayer());
    }

    /**parse the
     * @param vce that contains the player the bot needs to shoot
     * if the bot has the adrenaline action unblocked, he gives them a mark too
     * then we set the bot used and ask the damaged player if he wants to use a
     * specific power up
     * */
    private List<Player> parseVce(ViewControllerEvent vce){

        //parse VCE
        ViewControllerEventString vceString = (ViewControllerEventString)vce;
        Player player=ModelGate.getModel().getPlayerList().getPlayer(vceString.getInput());


        player.addDamages(ModelGate.getModel().getPlayerList().getPlayer(botNickname),1);

        out.println("<SERVER> bot giving damage to: "+ vceString.getInput());

        if(ModelGate.getModel().getPlayerList().getPlayer(botNickname).hasAdrenalineShootAction()){
            player.addMarksFrom(ModelGate.getModel().getPlayerList().getPlayer(botNickname),1);
            out.println("<SERVER> bot giving mark to: "+ vceString.getInput());
        }

        out.println("<SERVER> setting bot used...");
        ModelGate.getModel().getPlayerList().getPlayer(botNickname).setBotUsed(true);

        List<Player> damagedPlayer = new ArrayList<>();
        damagedPlayer.add(player);

        return damagedPlayer;
    }

    /**
     * set the player AFK in case they don't send required input in a while
     * */
    @Override
    public void handleAFK() {
        this.playerToAsk.setAFKWithNotify(true);
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        //pass turn
        if(!ViewControllerEventHandlerContext.getState().getClass().toString().contains("FinalScoringState")) {
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.getState().doAction(null);
        }
    }


    /**
     * @return a list of player who are in the same room as the bot or who are in a room adjacent
     * to the Square of the bot and divided by a door
     * */
    private List<Player> playersCanBotSee(){

        out.println("<SERVER> searching for the players the bot can see:");

        Position botPosition=ModelGate.getModel().getPlayerList().getPlayer(botNickname).getPosition();

        List<Player> playersBotCanShoot = Board.getCanSeePlayerFrom(botPosition);

        //remove the player who is using the bot
        for (Player p: playersBotCanShoot){
            if(p.getNickname().equals(playerToAsk.getNickname())){
                out.println("<SERVER> removing from the list of player the current playing player");
                playersBotCanShoot.remove(p);
                break;
            }
        }
        //remove the Terminator who is using the bot
        for (Player p: playersBotCanShoot) {
            if(p.isBot()){
                out.println("<SERVER> removing from the list of player the Terminator");
                playersBotCanShoot.remove(p);
                break;
            }
        }

        out.println("<SERVER> all the player the bot can shoot are:");
        if(playersBotCanShoot.isEmpty()){
            out.println("         can't see anybody.");
        }
        else {
            for (Player p : playersBotCanShoot) {
                out.println("         " + p.getNickname());
            }
        }

        return playersBotCanShoot;
    }



}





