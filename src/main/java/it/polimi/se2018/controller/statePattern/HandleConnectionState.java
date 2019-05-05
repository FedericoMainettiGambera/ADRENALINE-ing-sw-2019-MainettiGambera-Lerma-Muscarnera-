package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.controller.ModelGate;
import it.polimi.se2018.controller.ViewControllerEventHandlerContext;
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

        if(this.numberOfConnections<=4){

            if(this.numberOfConnections == 0){
                System.out.println("<SERVER>Creating new Game");
                ModelGate.model=new Game();

                System.out.println("<SERVER>Creating new PlayerList with the new Player \"user" + numberOfConnections + "\".");
                Player p = new Player();
                p.setNickname("user" + numberOfConnections);
                p.setIP(VCECIP.getInetAddress().getHostAddress());
                PlayersList pl = new PlayersList();
                pl.addPlayer(p);
                ModelGate.model.setPlayerList(pl);
            }
            else {
                Player p = new Player();
                p.setNickname("user" + numberOfConnections);
                p.setIP(VCECIP.getInetAddress().getHostAddress());

                System.out.println("<SERVER>Adding new Player \"user" + numberOfConnections + "\".");
                synchronized (ModelGate.model.getPlayerList()) {
                    ModelGate.model.getPlayerList().addPlayer(p);
                }
            }

            this.numberOfConnections++;
            System.out.println("<SERVER>Number of Clients connected: " + numberOfConnections);

            //TODO
            if(this.numberOfConnections == 2){
                ViewControllerEventHandlerContext.setNextState(new PlayerSetUpState());
            }
        }
    }
}
