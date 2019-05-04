package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.controller.ModelGate;
import it.polimi.se2018.controller.ViewControllerEventHandlerContext;
import it.polimi.se2018.model.events.ViewControllerEvent;
import it.polimi.se2018.model.events.ViewControllerEventBoolean;
import it.polimi.se2018.model.events.ViewControllerEventInt;
import it.polimi.se2018.model.events.ViewControllerEventString;

public class GameSetUpState implements State {

    private int inputReceived;

    private String gameMode;

    private int numberOfStartingSkulls;

    private boolean finalFrenzy;

    private String mapChoice;


    public void GameSetUpState(){
        this.inputReceived = 0;
    }


    @Override
    public void doAction(ViewControllerEvent VCE){

        if(this.inputReceived == 0){
            //set game mode
            this.gameMode = ((ViewControllerEventString)VCE).getInput();
            this.inputReceived++;

            //ask for number of starting skulls;

            return;
        }

        else if(this.inputReceived == 1){
            //set number of starting skulls
            this.numberOfStartingSkulls = ((ViewControllerEventInt)VCE).getInput();
            this.inputReceived++;

            //ask for Final Frenzy

            return;

        }
        else if(this.inputReceived == 2){
            //set final frenzy
            this.finalFrenzy = ((ViewControllerEventBoolean)VCE).getInput();
            this.inputReceived++;

            //ask for map

            return;
        }
        else if(this.inputReceived == 3){
            //set choosen map
            this.mapChoice = ((ViewControllerEventString)VCE).getInput();
            this.inputReceived++;

            this.createGame();

            //set the next state function:
            //ViewControllerEventHandlerContext.setNextState( ** put here next State ** );

            //ask for ...

            return;
        }
    }

    private void createGame(){
        //create the game based on the user inputs:
        //ModelGate.model.etc...
    }
}
