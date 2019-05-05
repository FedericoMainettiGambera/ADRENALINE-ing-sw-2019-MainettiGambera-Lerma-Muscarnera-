package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.controller.ModelGate;
import it.polimi.se2018.model.events.ViewControllerEvent;
import it.polimi.se2018.model.events.ViewControllerEventGameSetUp;

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
            this.objectOutputStream = ModelGate.model.getPlayerList().getPlayer("User1").getOos();
            //ask for gameSetUp
            numberOfEvents++;
        }
        else if(numberOfEvents==1){
            ViewControllerEventGameSetUp VCEGameSetUp = (ViewControllerEventGameSetUp)VCE;
            //crea il gioco
            //setta il prossimo State
            //Chiedi la Prossima cosa all'utente corretto
        }
    }
}
