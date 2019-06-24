package it.polimi.se2019.virtualView.RMIREDO;

public interface RmiInterface{

    public void send(Object o);

    public void connect(RmiInterface client);


}
