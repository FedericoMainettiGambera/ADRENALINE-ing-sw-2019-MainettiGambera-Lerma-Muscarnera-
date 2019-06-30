package it.polimi.se2019.view.outputHandler;

import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.model.events.stateEvent.StateEvent;
import it.polimi.se2019.view.components.OrderedCardListV;

public interface OutputHandlerInterface {
     void gameCreated();
     void stateChanged(StateEvent stateEvent);
     void setFinalFrenzy(ModelViewEvent modelViewEvent);
     void finalFrenzyBegun(ModelViewEvent modelViewEvent);
     void newKillshotTrack(ModelViewEvent modelViewEvent);
     void newPlayersList(ModelViewEvent modelViewEvent);
     void newBoard(ModelViewEvent modelViewEvent);
     void deathOfPlayer(ModelViewEvent modelViewEvent);
     void movingCardsAround(OrderedCardListV from, OrderedCardListV to, ModelViewEvent modelViewEvent);
     void shufflingCards(ModelViewEvent modelViewEvent);
     void newColor(ModelViewEvent modelViewEvent);
     void newNickname(ModelViewEvent modelViewEvent);
     void newPosition(ModelViewEvent modelViewEvent);
     void newScore(ModelViewEvent modelViewEvent);
     void addDeathCounter(ModelViewEvent modelViewEvent);
     void setFinalFrenzyBoard(ModelViewEvent modelViewEvent);
     void newAmmoBox(ModelViewEvent modelViewEvent);
     void newDamageTracker(ModelViewEvent modelViewEvent);
     void newMarksTracker(ModelViewEvent modelViewEvent);
     void setCurrentPlayingPlayer(ModelViewEvent modelViewEvent);
     void setStartingPlayer(ModelViewEvent modelViewEvent);
     void newPlayer(ModelViewEvent modelViewEvent);
     void setAFK(ModelViewEvent modelViewEvent);
     void showInputTimer(int currentTime, int totalTime);
     void showConnectionTimer(int currentTime, int totalTime);
     void cantReachServer();
     void succesfullReconnection();
     void disconnect();
     void finalScoring(ModelViewEvent modelViewEvent);
}
