package it.polimi.se2019.controller.statePattern;

import com.sun.tools.internal.xjc.model.Model;
import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FFSetUpState implements State {

    private static PrintWriter out= new PrintWriter(System.out, true);



    public FFSetUpState(){
        out.println("<SERVER> New state: " + this.getClass());
    }

    @Override
    public void askForInput(Player playerToAsk) {
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");
    }
    /**Final Frenzy has been triggered,
     * we need to get ready for it:
     * 1
     * makes the players boards that has no damages in FinalFrenzy mode,
     * 2
     * we also need to set a dedicated attributed - BeforeorafterStartingPlayer -  in order to understand whether a player will play before of after the starting player, this fact
     * will make all the difference: the action one can unblock are determined by that attribute!
     * */
    @Override
    public void doAction(ViewControllerEvent vce) {
        out.println("<SERVER> "+ this.getClass() +".doAction();");

        ModelGate.model.triggerFinalFrenzy(true);

        List<Player> listOfPlayers = new ArrayList<>(); //list of players ordered from the current playing player
        listOfPlayers.add(ModelGate.model.getCurrentPlayingPlayer()); //add the current playing player
        //search index of the current playing player
        int currentPlayingPlayerIndex = 0;
        while (!ModelGate.model.getPlayerList().getPlayers().get(currentPlayingPlayerIndex).getNickname().equals(ModelGate.model.getCurrentPlayingPlayer().getNickname())){
            currentPlayingPlayerIndex++;
        }
        //adding all player after the current playing player
        for (int i = currentPlayingPlayerIndex; i < ModelGate.model.getPlayerList().getPlayers().size(); i++) {
            listOfPlayers.add(ModelGate.model.getPlayerList().getPlayers().get(i));
        }
        //adding all player before current playing player
        for (int i = 0; i < currentPlayingPlayerIndex ; i++) {
            listOfPlayers.add(ModelGate.model.getPlayerList().getPlayers().get(i));
        }
        //delete the bot
        for (Player p: listOfPlayers) {
            if(p.isBot()){
                listOfPlayers.remove(p);
                break;
            }
        }

        //set last playing player in FF
        ModelGate.model.getCurrentPlayingPlayer().setLastPlayingPlayer();

        int position=-1;
        for(Player player : listOfPlayers) {
            if (player.getBoard().getDamagesTracker().isEmpty()){
                player.makePlayerBoardFinalFrenzy();
            }
            if (ModelGate.model.getPlayerList().getStartingPlayer().getNickname().equals(player.getNickname())) {
                player.setBeforeorafterStartingPlayer(0);
                position = 1;
            } else if (position < 0) {
                player.setBeforeorafterStartingPlayer(position);
                position--;
            } else if (position > 0) {
                player.setBeforeorafterStartingPlayer(position);
                position++;
            }
        }

        //pass the turn
        ModelGate.model.getPlayerList().setNextPlayingPlayer();

        ViewControllerEventHandlerContext.setNextState(new TurnState(1));
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());

    }

    /**
     * set the player AFK in case they don't send required input in a while
     * */
    @Override
    public void handleAFK() {
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
    }
}
