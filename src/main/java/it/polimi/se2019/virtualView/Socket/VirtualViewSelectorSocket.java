package it.polimi.se2019.virtualView.Socket;

import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.CardinalPoint;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.model.enumerations.SquareTypes;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;
import it.polimi.se2019.model.events.selectorEvents.*;
import it.polimi.se2019.view.components.*;
import it.polimi.se2019.virtualView.Selector;
import it.polimi.se2019.virtualView.VirtualViewSelector;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.se2019.model.enumerations.CardinalPoint.east;
import static it.polimi.se2019.model.enumerations.CardinalPoint.south;

public class VirtualViewSelectorSocket extends VirtualViewSelector implements Selector {

    private Player playerToAsk;

    public void setPlayerToAsk(it.polimi.se2019.model.Player playerToAsk){
        this.playerToAsk = playerToAsk;
    }

    public Player getPlayerToAsk() {
        return playerToAsk;
    }

    @Override
    public  void askGameSetUp() {
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEvent(SelectorEventTypes.askGameSetUp));
    }

    @Deprecated
    @Override
    public void askPlayerSetUp() {
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEvent(SelectorEventTypes.askPlayerSetUp));
    }

    @Override
    public void askFirstSpawnPosition(ArrayList<PowerUpCard> powerUpCards) {
        ArrayList<PowerUpCardV> powerUpCardsV= new ArrayList<>();
        for (PowerUpCard c : powerUpCards) {
            powerUpCardsV.add(c.buildPowerUpCardV());
        }
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventPowerUpCards(SelectorEventTypes.askFirstSpawnPosition, powerUpCardsV));
    }

    @Override
    public void askTurnAction(int actionNumber) {
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventInt(SelectorEventTypes.askTurnAction, actionNumber));
    }

    @Override
    public void askRunAroundPosition(ArrayList<Position> positions) {
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventPositions(SelectorEventTypes.askRunAroundPosition, positions));
    }

    @Override
    public void askGrabStuffAction() {
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEvent(SelectorEventTypes.askGrabStuffAction));
    }

    @Override
    public void askGrabStuffMove(ArrayList<Position> positions) {
        SocketVirtualView.sendToClient(playerToAsk, (new SelectorEventPositions(SelectorEventTypes.askGrabStuffMove, positions)));
    }

    @Override
    public void askGrabStuffGrabWeapon(ArrayList<WeaponCard> toPickUp) {
        ArrayList<WeaponCardV> weaponCardsV= new ArrayList<>();
        for (WeaponCard c : toPickUp) {
            weaponCardsV.add(c.buildWeapondCardV());
        }
        SelectorEventWeaponCards SE = new SelectorEventWeaponCards(SelectorEventTypes.askGrabStuffGrabWeapon, weaponCardsV);
        SocketVirtualView.sendToClient(playerToAsk, SE);
    }

    @Override
    public void askGrabStuffSwitchWeapon(ArrayList<WeaponCard> toPickUp, ArrayList<WeaponCard> toSwitch) {
        ArrayList<WeaponCardV> weaponCardsVtoSwitch= new ArrayList<>();
        for (WeaponCard c : toSwitch) {
            weaponCardsVtoSwitch.add(c.buildWeapondCardV());
        }
        ArrayList<WeaponCardV> weaponCardsVtoPickUp= new ArrayList<>();
        for (WeaponCard c : toPickUp) {
            weaponCardsVtoPickUp.add(c.buildWeapondCardV());
        }
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventDoubleWeaponCards(SelectorEventTypes.askGrabStuffSwitchWeapon, weaponCardsVtoPickUp, weaponCardsVtoSwitch));
    }

    @Override
    public void askPowerUpToDiscard(ArrayList<PowerUpCard> toDiscard) {
        ArrayList<PowerUpCardV> powerUpCardsV= new ArrayList<>();
        for (PowerUpCard c : toDiscard) {
            powerUpCardsV.add(c.buildPowerUpCardV());
        }
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventPowerUpCards(SelectorEventTypes.askPowerUpToDiscard, powerUpCardsV));
    }

    @Override
    public void askWhatReaload(ArrayList<WeaponCard> toReload) {
        ArrayList<WeaponCardV> weaponCardsV= new ArrayList<>();
        for (WeaponCard c : toReload) {
            weaponCardsV.add(c.buildWeapondCardV());
        }
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventWeaponCards(SelectorEventTypes.askWhatReaload,weaponCardsV));
    }

    @Override
    public void askSpawn(ArrayList<PowerUpCard> powerUpCards) {
        ArrayList<PowerUpCardV> powerUpCardsV= new ArrayList<>();
        for (PowerUpCard c : powerUpCards) {
            powerUpCardsV.add(c.buildPowerUpCardV());
        }
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventPowerUpCards(SelectorEventTypes.askSpawn, powerUpCardsV));
    }

    @Override
    public void askShootOrMove(){
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEvent(SelectorEventTypes.askShootOrMove));
    }

    @Deprecated
    @Override
    public void askShootReloadMove(){
        ObjectOutputStream oos = this.playerToAsk.getOos();
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEvent(SelectorEventTypes.askShootReloadMove));
    }

    @Override
    public void askWhatWep(ArrayList<WeaponCard> loadedCardInHand) {
        ArrayList<WeaponCardV> weaponCardsV= new ArrayList<>();
        for (WeaponCard c : loadedCardInHand) {
            weaponCardsV.add(c.buildWeapondCardV());
        }
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventWeaponCards(SelectorEventTypes.askWhatWep, weaponCardsV));
    }

    @Override
    public void askWhatEffect(ArrayList<Effect> possibleEffects) {
        ArrayList<EffectV> effectsV = new ArrayList<>();
        for (Effect e : possibleEffects) {
            effectsV.add(e.buildEffectV());
        }
        SocketVirtualView.sendToClient(playerToAsk, new SelectorEventEffect(SelectorEventTypes.askWhatEffect, effectsV));
    }

    @Override
    public void askEffectInputs(EffectInfoType inputType, List<Object> possibleInputs) {
        List<Object> possibleInputsV = new ArrayList<>();
        if(possibleInputs.size()!=0) { //TODO BO STAROBA QUA NON DOVREBBE ESSERCI ; L'HO MESSA PER FARE FORZA BRUTA E SPERARE DI NON AVERE PROBLEMI DI INDEX OUT OF BOUND CHE MI DA CERTE VOLTE
            if(possibleInputs.get(0) != null) { // TODO         STA ROBA PURE NON DOVREBBE ESSERCI; L'HO MESSA PERCHE' ALCUNE VOLTE MI DA PORBLEMI DI NULL POINTER EXCEPTION, COME SE CI FOSSERO DELLE LISTE DI INPUT TUTTE A NULL
                                                // TODO         MA COMUNQUE NON E' ABBASTANZA, PERCHé CERTE VOLTE DEGLI ELEMENTI SONO NULL
                                                // TODO         PROBABILMENTE IL PROBLEMA E' CHE NELLA MAPPA CI SONO DEGLI SQUARE A NULL
                if (possibleInputs.get(0).getClass().toString().contains("Player")) {
                    for (Object p : possibleInputs) {
                        possibleInputsV.add(((Player) p).buildPlayerV());
                    }
                } else {
                    for (Object s : possibleInputs) {
                        if (s.getClass().toString().contains("NormalSquare")) {
                            possibleInputsV.add(((NormalSquare) s).buildNormalSquareV((NormalSquare) s));
                        } else {
                            possibleInputsV.add(((SpawnPointSquare) s).builSpawnPointSquareV((SpawnPointSquare) s));
                        }
                    }
                }

                SocketVirtualView.sendToClient(playerToAsk, new SelectorEventEffectInputs(inputType, possibleInputsV));
            }
        }
    }

    @Override
    public void askReconnectionNickname(ReconnectionEvent RE) {
        //must be empty...
    }

    @Override
    public void askNickname() {
        //must be empty
    }

    @Override
    public void askPaymentInformation(SelectorEventPaymentInformation SEPaymentInformation) {
        SocketVirtualView.sendToClient(playerToAsk, SEPaymentInformation);
    }
}
