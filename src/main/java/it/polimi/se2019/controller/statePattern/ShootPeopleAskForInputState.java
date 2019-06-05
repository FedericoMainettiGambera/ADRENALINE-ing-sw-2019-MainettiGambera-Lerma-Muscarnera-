package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.WaitForPlayerInput;
import it.polimi.se2019.model.Effect;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.Square;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventListOfListOfObject;
import it.polimi.se2019.view.components.PlayerV;
import it.polimi.se2019.view.components.SquareV;

import java.util.List;

public class ShootPeopleAskForInputState implements State {
    private Player playerToAsk;

    private Thread inputTimer;

    private Effect chosenEffect;


    public ShootPeopleAskForInputState(Effect chosenEffect){
        System.out.println("<SERVER> New state: " + this.getClass());
        this.chosenEffect = chosenEffect;
    }

    @Override
    public void askForInput(Player playerToAsk){
        this.playerToAsk = playerToAsk;
        System.out.println("<SERVER> (" + this.getClass() + ") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        //ask input
        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
            SelectorGate.getCorrectSelectorFor(playerToAsk).askEffectInputs(this.chosenEffect.requestedInputs());
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

        ViewControllerEventListOfListOfObject VCEListOfObject = (ViewControllerEventListOfListOfObject)VCE;

        System.out.println("<SERVER> " + this.playerToAsk.getNickname() + "'s inputs were:");
        PlayerV tempPlayerV;
        SquareV tempSquareV;
        for (List<Object> ListInp: VCEListOfObject.getListOfObject()) {
            for (Object inp : ListInp) {
                if(inp.getClass().toString().contains("PlayerV")){
                    tempPlayerV = (PlayerV)inp;
                    System.out.println("         " + tempPlayerV.getNickname());
                }
                else{
                    tempSquareV = (SquareV)inp;
                    System.out.println("         [" + tempSquareV.getX() + "][" + tempSquareV.getY() + "]");
                }
            }
        }

        Object[][] inputMatrix = new Object[10][10] ;

        Player tempPlayer;
        Square tempSquare;
        for (int i = 0; i < VCEListOfObject.getListOfObject().size(); i++) {
            for (int j = 0; j < VCEListOfObject.getListOfObject().get(i).size(); j++) {
                if(VCEListOfObject.getListOfObject().get(i).get(j).getClass().toString().contains("PlayerV")){
                    tempPlayerV = (PlayerV)VCEListOfObject.getListOfObject().get(i).get(j);
                    tempPlayer = ModelGate.model.getPlayerList().getPlayer(tempPlayerV.getNickname());
                    inputMatrix[i][j] = tempPlayer;
                }
                else{
                    tempSquareV = (SquareV)VCEListOfObject.getListOfObject().get(i).get(j);
                    tempSquare = ModelGate.model.getBoard().getSquare(tempSquareV.getX(), tempSquareV.getY());
                    inputMatrix[i][j] = tempSquare;
                }
            }
        }
        System.out.println("<SERVER> Matrix 10x10 of Object filled. It contains:");
        String print = "";
        for (int i = 0; i < inputMatrix.length; i++) {
            for (int j = 0; j < inputMatrix[i].length ; j++) {
                if(inputMatrix[i][j] == null){
                    print += "    null ";
                }
                else if(inputMatrix[i][j].getClass().toString().contains("Player")){
                    print += "   Player";
                }
                else if(inputMatrix[i][j].getClass().toString().contains("Square")){
                    print += "   Square";
                }
            }
            print+="\n";
        }
        System.out.println(print);

        System.out.println("<SERVER> calling HandleInput()");
        this.chosenEffect.handleInput(inputMatrix);

        System.out.println("<SERVER> calling Exec()");
        boolean execResult = this.chosenEffect.Exec();

        System.out.println("<SERVER> Exec() return value is: " + execResult);
    }

    @Override
    public void handleAFK() {
        this.playerToAsk.setIsAFK(true);
        System.out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        //pass turn
        ModelGate.model.getPlayerList().setNextPlayingPlayer();
        ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
    }
}
