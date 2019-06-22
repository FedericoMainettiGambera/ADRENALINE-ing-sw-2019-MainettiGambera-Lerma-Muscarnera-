package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.WaitForPlayerInput;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.Square;
import it.polimi.se2019.model.enumerations.CardinalPoint;
import it.polimi.se2019.model.enumerations.SquareSide;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventString;
import it.polimi.se2019.view.components.PlayerV;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class BotShootState implements State{
    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(TurnState.class.getName());

    private Player playerToAsk;

    private Thread inputTimer;

    private State nextState;

    public BotShootState(State nextState){
        out.println("<SERVER> New state: " + this.getClass());
        this.nextState = nextState;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        this.playerToAsk = playerToAsk;
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        List<Player> players = playersCanBotSee();
        List<PlayerV> playersV = new  ArrayList<>();
        for (Player p: players) {
            playersV.add(p.buildPlayerV());
        }
        if(playersV.isEmpty()){
            out.println("<SERVER>bot cant see anybody");
            ViewControllerEventHandlerContext.setNextState(this.nextState);
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
        //ask for input
        else {
            try {
                SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
                SelectorGate.getCorrectSelectorFor(playerToAsk).askBotShoot(playersV);
                this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
                this.inputTimer.start();
            } catch (Exception e) {
                logger.severe("Exception occured  " + e.getClass() + "  " + e.getCause() + Arrays.toString(e.getStackTrace()));
            }
        }
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        this.inputTimer.interrupt();
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        //parse VCE
        ViewControllerEventString VCEString = (ViewControllerEventString)VCE;
        Player player=ModelGate.model.getPlayerList().getPlayer(VCEString.getInput());

        player.addDamages(ModelGate.model.getPlayerList().getPlayer("Terminator"),1);

        out.println("<SERVER> bot giving damage to: "+ VCEString.getInput());

        if(ModelGate.model.getPlayerList().getPlayer("Terminator").hasAdrenalineShootAction()){
            player.addMarksFrom(ModelGate.model.getPlayerList().getPlayer("Terminator"),1);
            out.println("<SERVER> bot giving mark to: "+ VCEString.getInput());
        }

        out.println("<SERVER> setting bot used...");
        ModelGate.model.getPlayerList().getPlayer("Terminator").setBotUsed(true);

        ViewControllerEventHandlerContext.setNextState(this.nextState);
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
    }

    @Override
    public void handleAFK() {
        this.playerToAsk.setAFKWithNotify(true);
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");

        //handle case timer ends before player answers
    }


    private List<Player> playersCanBotSee(){
        List<Player> playersBotCanShoot=new ArrayList<>();

        Position botPosition=ModelGate.model.getPlayerList().getPlayer("Terminator").getPosition();
        playersBotCanShoot.addAll(getPlayersInRoom(botPosition));

        Square botSquare=ModelGate.model.getBoard().getSquare(botPosition);

        //TODO absolutely not sure about the coordinates (just used the same of possiblePositions in Board)
        if(botSquare.getSide(CardinalPoint.north).equals(SquareSide.door)){
           playersBotCanShoot.addAll(getPlayersInRoom(new Position(botPosition.getX()-1, botPosition.getY())));
        }

        if(botSquare.getSide(CardinalPoint.south).equals(SquareSide.door)){
            playersBotCanShoot.addAll(getPlayersInRoom(new Position(botPosition.getX()+1, botPosition.getY())));
        }

        if(botSquare.getSide(CardinalPoint.east).equals(SquareSide.door)){
            playersBotCanShoot.addAll(getPlayersInRoom(new Position(botPosition.getX(), botPosition.getY()+1)));
        }

        if(botSquare.getSide(CardinalPoint.west).equals(SquareSide.door)){
            playersBotCanShoot.addAll(getPlayersInRoom(new Position(botPosition.getX(), botPosition.getY()-1)));
        }

        List<Player> playersBotCanShootFinal = new ArrayList<>();

        Iterator<Player> playerIterator = playersBotCanShoot.iterator();
        while(playerIterator.hasNext()){
            Player p = playerIterator.next();
            boolean duplicate = false;
            if(playersBotCanShootFinal.isEmpty()){
                playersBotCanShootFinal.add(p);
            }
            else {
                for (Player pFinal : playersBotCanShootFinal) {
                    if (p.getNickname().equals(pFinal.getNickname())) {
                        playerIterator.remove();
                    }
                }
            }
        }

        for (Player p: playersBotCanShootFinal) {
            if(p.getNickname().equals(playerToAsk.getNickname())){
                playersBotCanShootFinal.remove(p);
                break;
            }
        }

        out.println("<SERVER> all the player the bot can shoot are:");
        for (Player p: playersBotCanShootFinal) {
            out.println("         " + p.getNickname());
        }

        return playersBotCanShootFinal;
    }

    private List<Player> getPlayersInRoom(Position pos){
        List<Position> positionsList=new ArrayList<>();
        List<Player> players=new ArrayList<>();

        List<Square> squareList= ModelGate.model.getBoard().getRoomFromPosition(pos);
        for (Square square : squareList){
            //TODO check se x y sono giuste o al contrario
            positionsList.add(new Position(square.getCoordinates().getX(), square.getCoordinates().getY()));
        }

        for (Player p: ModelGate.model.getPlayerList().getPlayers()){
            if(!p.isBot()){
                for (Position position: positionsList){
                    if(p.getPosition().equals(position)){
                        players.add(p);
                    }
                }
            }
        }

        return players;
    }

}

