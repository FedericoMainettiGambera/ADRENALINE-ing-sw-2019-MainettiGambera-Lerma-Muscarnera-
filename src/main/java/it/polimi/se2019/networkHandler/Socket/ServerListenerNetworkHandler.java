
package it.polimi.se2019.networkHandler.Socket;



import it.polimi.se2019.model.events.Event;
import it.polimi.se2019.view.components.View;
import it.polimi.se2019.view.outputHandler.OutputHandlerGate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**listens to event received from the server
 * @author FedericoMainettigambera
 * @author LudoLerma */
public class ServerListenerNetworkHandler extends Observable implements Runnable {

    private static final Logger logger= Logger.getLogger(ServerListenerNetworkHandler.class.getName());
    /**socket of the client*/
    private Socket socket;
    /**from which the object is received */
    private ObjectInputStream ois;
    /**constructor
     * @param socket to set socket attribute
     * @param view to set ois attribute
     * @param ois to set view attribute
     * */
    ServerListenerNetworkHandler(Socket socket, ObjectInputStream ois, View view){

        this.socket = socket;
        this.ois = ois;
        this.addObserver(view);
    }

    /**sends all the received object from server to view*/
    @Override
    public void run() {
        while(this.socket.isConnected()){
            try {
                Object o = this.ois.readObject();

                Event event = (Event)o;
                this.setChanged();
                this.notifyObservers(event);
            }
            catch (InternalError e){
                logger.severe("Something went Wrong in class ServerListenerNetworkHandler..." + e.getMessage());
                logger.log(Level.SEVERE, "EXCEPTION", e);
            }
            catch (IOException|ClassNotFoundException e) {
                OutputHandlerGate.getCorrectOutputHandler(OutputHandlerGate.getUserIterface()).cantReachServer();
                break;
            }
        }
    }
}
