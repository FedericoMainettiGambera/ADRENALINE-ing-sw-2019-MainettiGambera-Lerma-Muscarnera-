package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.controller.ModelGate;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayersList;
import it.polimi.se2018.model.events.ViewControllerEvent;
import it.polimi.se2018.view.View;

import java.io.ObjectOutputStream;

public class GameSetUpState implements State {

    private ObjectOutputStream objectOutputStream;
    private int numberOfEvents;

    public void GameSetUpState(){
        this.numberOfEvents=0;

    }
    @Override
    public void doAction(ViewControllerEvent VCE){

        if(numberOfEvents==0) {
             System.out.println(ModelGate.model.getPlayerList().getPlayer("User1").getNickname());
            this.objectOutputStream = ModelGate.model.getPlayerList().getPlayer("User1").getOos();
            //ask for gameSetUp
            numberOfEvents++;
            System.out.println("gimmi info");

        }

        else if(numberOfEvents==1){




        }


    }
}
