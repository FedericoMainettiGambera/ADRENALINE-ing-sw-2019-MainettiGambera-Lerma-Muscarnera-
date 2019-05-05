package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.controller.ModelGate;
import it.polimi.se2018.model.Game;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayersList;
import it.polimi.se2018.model.events.ViewControllerEvent;
import it.polimi.se2018.model.events.ViewControllerEventClientIP;


public class HandleConnectionState implements State {

    private int numberOfConnections;

    public void HandleConnectionState(){
        this.numberOfConnections = 0;
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {

        ViewControllerEventClientIP VCECIP = ((ViewControllerEventClientIP)VCE);

        System.out.println("<SERVER>Event received from client. Content: " + VCECIP.getInetAddress().getHostAddress());

        if(this.numberOfConnections<=5){

            this.numberOfConnections++;
            System.out.println("<SERVER>Number of Clients connected: " + numberOfConnections);

            if(this.numberOfConnections == 0){
                System.out.println("<SERVER>Creating new Game");
                ModelGate.model=new Game();

                System.out.println("<SERVER>Creating new PlayerList");
                ModelGate.model.setPlayerList(new PlayersList());
            }

            Player p = new Player();
            p.setIP(VCECIP.getInetAddress().getHostAddress());

            System.out.println("<SERVER>Adding new Player");
            ModelGate.model.getPlayerList().addPlayer(p);

        }
        else{
            //do nothing and throw away the connection
        }

    }
}
