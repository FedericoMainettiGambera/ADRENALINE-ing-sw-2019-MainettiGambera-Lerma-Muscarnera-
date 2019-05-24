package it.polimi.se2019.networkHandler.Socket;

import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.events.Event;
import it.polimi.se2019.model.events.SelectorEvent;
import it.polimi.se2019.model.events.SelectorEventWeaponCards;
import it.polimi.se2019.view.components.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;
import java.util.Observable;

import static it.polimi.se2019.model.enumerations.SelectorEventTypes.askGrabStuffGrabWeapon;

public class ServerListenerNetworkHandler extends Observable implements Runnable {

    private Socket socket;

    private boolean isSocketLive;

    private ObjectInputStream ois;

    private View view;

    public ServerListenerNetworkHandler(Socket socket, ObjectInputStream ois, View view){
            this.socket = socket;
            this.isSocketLive = true;
            this.ois = ois;
            this.view = view;

            this.addObserver(this.view);
    }

    public void closeSocket(){
        try {
            this.socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.isSocketLive=false;
    }

    @Override
    public void run() {
        while(isSocketLive){
            try {
                Event E = (Event)this.ois.readObject();

                //TODO: CAN'T UNDERSTAND WHY THIS KEEP HAPPENING (ALSO CHECK VirtualViewSelector CLASS)
                if(E.getClass().toString().contains("SelectorEvent")) {
                    SelectorEvent SE = (SelectorEvent) E;
                    if (SE.getSelectorEventTypes() == askGrabStuffGrabWeapon) {
                        List<WeaponCard> carte = ((SelectorEventWeaponCards) SE).getWeaponCards();
                        System.out.println("<CLIENT>OBJECT LISTEN CONTAINS:");
                        for (int i = 0; i < carte.size(); i++) {
                            System.out.println("    <CLIENT> " + i + ") " + carte.get(i).getID());
                        }
                    }
                }

                this.setChanged();
                this.notifyObservers(E);
            }
            catch (IOException|ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
