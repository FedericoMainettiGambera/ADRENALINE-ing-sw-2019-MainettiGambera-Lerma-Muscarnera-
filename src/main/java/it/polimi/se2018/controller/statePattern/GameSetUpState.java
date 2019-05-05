package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.controller.ModelGate;
import it.polimi.se2018.controller.ViewControllerEventHandlerContext;
import it.polimi.se2018.model.Board;
import it.polimi.se2018.model.Bot;
import it.polimi.se2018.model.KillShotTrack;
import it.polimi.se2018.model.events.ViewControllerEvent;
import it.polimi.se2018.model.events.ViewControllerEventGameSetUp;

import java.io.IOException;
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
            //ask for gameSetUp to the player
            numberOfEvents++;
        }
        else if(numberOfEvents==1){
            ViewControllerEventGameSetUp VCEGameSetUp = (ViewControllerEventGameSetUp)VCE;

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
                System.out.println("<SERVER>creating Killshot Track with " + VCEGameSetUp.getNumberOfStartingSkulls() + " number of starting skulls.");
                ModelGate.model.setKillshotTrack(new KillShotTrack(VCEGameSetUp.getNumberOfStartingSkulls()));
                System.out.println("<SERVER>Setting Final Frenzy: " + VCEGameSetUp.isFinalFrezy());
                ModelGate.model.setFinalFrenzy(VCEGameSetUp.isFinalFrezy());
                System.out.println("<SERVER>Setting a Bot: "+ VCEGameSetUp.isBotActive());
                ModelGate.model.setBot(new Bot(VCEGameSetUp.isBotActive()));

                //crea le carte


                //setting next State
                ViewControllerEventHandlerContext.setNextState(new PlayerSetUpState());

                //ask first player to Set Up player

            } else if(VCEGameSetUp.getGameMode().equals("turretMode")){
                //build game in turret mode
            } else if(VCEGameSetUp.getGameMode().equals("dominationMode")){
                //build game in domination mode
            }
        }
    }
}
