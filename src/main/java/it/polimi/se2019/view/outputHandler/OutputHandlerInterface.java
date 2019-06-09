package it.polimi.se2019.view.outputHandler;

import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.model.events.stateEvent.StateEvent;
import it.polimi.se2019.view.components.OrderedCardListV;

public interface OutputHandlerInterface {
    public void gameCreated();
    public void stateChanged(StateEvent StE);
    public void setFinalFrenzy(ModelViewEvent MVE);
    public void finalFrenzyBegun(ModelViewEvent MVE);
    public void newKillshotTrack(ModelViewEvent MVE);
    public void newPlayersList(ModelViewEvent MVE);
    public void newBoard(ModelViewEvent MVE);
    public void deathOfPlayer(ModelViewEvent MVE);
    public void movingCardsAround(OrderedCardListV from, OrderedCardListV to, ModelViewEvent MVE);
    public void shufflingCards(ModelViewEvent MVE);
    public void newColor(ModelViewEvent MVE);
    public void newNickname(ModelViewEvent MVE);
    public void newPosition(ModelViewEvent MVE);
    public void newScore(ModelViewEvent MVE);
    public void addDeathCounter(ModelViewEvent MVE);
    public void setFinalFrenzyBoard(ModelViewEvent MVE);
    public void newAmmoBox(ModelViewEvent MVE);
    public void newDamageTracker(ModelViewEvent MVE);
    public void newMarksTracker(ModelViewEvent MVE);
    public void setCurrentPlayingPlayer(ModelViewEvent MVE);
    public void setStartingPlayer(ModelViewEvent MVE);
    public void newPlayer(ModelViewEvent MVE);
    public void setAFK(ModelViewEvent MVE);
    public void showInputTimer(int currentTime, int totalTime);
    public void showConnectionTimer(int currentTime, int totalTime);
    public void cantReachServer();
}
