package it.polimi.se2019.view.outputHandler;

import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.enumerations.PlayersColors;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.model.events.stateEvent.StateEvent;
import it.polimi.se2019.view.components.*;

public class CLIOutputHandler implements OutputHandlerInterface{

    public void updateUserInterface(String message) {
        System.out.println(message);
    }

    @Override
    public void gameCreated() {
        updateUserInterface("<CLIENT> created GameV and PlayerListV");
    }

    @Override
    public void stateChanged(StateEvent StE) {
        updateUserInterface("<CLIENT> state changed: " + StE.getState());
    }

    @Override
    public void setFinalFrenzy(ModelViewEvent MVE) {
        updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        updateUserInterface("              " + ViewModelGate.getModel().isFinalFrenzy());
    }

    @Override
    public void finalFrenzyBegun(ModelViewEvent MVE) {
        updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        updateUserInterface("              " + ViewModelGate.getModel().isHasFinalFrenzyBegun());
    }

    @Override
    public void newKillshotTrack(ModelViewEvent MVE) {
        updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + "number of staring skulls: " + ViewModelGate.getModel().getKillshotTrack().getNumberOfStartingSkulls());
    }

    @Override
    public void newPlayersList(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        for (PlayerV p :ViewModelGate.getModel().getPlayers().getPlayers()) {
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + p.getNickname());
        }
    }

    @Override
    public void newBoard(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("" + "MAP:");
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface(ViewModelGate.getModel().getBoard().toString());
    }

    @Override
    public void deathOfPlayer(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + "killshot Track has changed:");
        for (int i = 0; i < ViewModelGate.getModel().getKillshotTrack().getKillsV().size(); i++) {
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + i + ") is skull:" + ViewModelGate.getModel().getKillshotTrack().getKillsV().get(i).isSkull());
            if(ViewModelGate.getModel().getKillshotTrack().getKillsV().get(i).isSkull()) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + "   killing player:" + ViewModelGate.getModel().getKillshotTrack().getKillsV().get(i).isSkull());
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + "   is Overkill:" + ViewModelGate.getModel().getKillshotTrack().getKillsV().get(i).isOverKill());
                if(ViewModelGate.getModel().getKillshotTrack().getKillsV().get(i).isOverKill()) {
                    OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + "   is skull:" + ViewModelGate.getModel().getKillshotTrack().getKillsV().get(i).isSkull());
                }
            }

        }
    }

    @Override
    public void movingCardsAround(OrderedCardListV from, OrderedCardListV to, ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        if(to!=null) {
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + "cards Moved from " + from.getContext());
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface(from.toString());

            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + "to " + to.getContext());
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface(to.toString());
        }
        else{
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + from.getContext() + " cards has changed.");
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface(from.toString());
        }
    }

    @Override
    public void shufflingCards(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        OrderedCardListV cards = ((OrderedCardListV) MVE.getComponent());
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + "cards shuffled: " + (cards.getContext()));
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface(cards.toString());
    }

    @Override
    public void newColor(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
            if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + p.getNickname() +" has changed color to: " + ((PlayersColors) MVE.getComponent()));
                break;
            }
        }
    }

    @Override
    public void newNickname(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + ((String)MVE.getExtraInformation1()) +" has changed Nickname to: " + (String) MVE.getComponent());
    }

    @Override
    public void newPosition(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
            if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + p.getNickname() +" has changed Position to: [" + ((Position) MVE.getComponent()).getX() + "][" + ((Position) MVE.getComponent()).getY() + "]");
                break;
            }
        }
    }

    @Override
    public void newScore(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
            if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + p.getNickname() +" has changed score to: " + (int) MVE.getComponent());
                break;
            }
        }
    }

    @Override
    public void addDeathCounter(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
            if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + p.getNickname() +" has died");
                break;
            }
        }
    }

    @Override
    public void setFinalFrenzyBoard(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
            if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + p.getNickname() +" has setted his board to Final Frenzy");
                break;
            }
        }
    }

    @Override
    public void newAmmoBox(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
            if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + p.getNickname() +" has changed his ammo box: ");
                for (AmmoCubesV a : p.getAmmoBox().getAmmoCubesList()) {
                    OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + a.getColor() + ": " + a.getQuantity());
                }
                break;
            }
        }
    }

    @Override
    public void newDamageTracker(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
            if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + p.getNickname() +" has changed his damage tracker, damages are listed here: ");
                for (DamageSlotV d : p.getDamageTracker().getDamageSlotsList()) {
                    OutputHandlerGate.getCLIOutputHandler().updateUserInterface("                    " + d.getShootingPlayer());
                }
                break;
            }
        }
    }

    @Override
    public void newMarksTracker(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        for (PlayerV p : ViewModelGate.getModel().getPlayers().getPlayers()) {
            if (p.getNickname().equals((String) MVE.getExtraInformation1())) {
                OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + p.getNickname() +" has changed his marks tracker, marks are listed here: ");
                for (MarkSlotV m : p.getMarksTracker().getMarkSlotsList()) {
                    OutputHandlerGate.getCLIOutputHandler().updateUserInterface("                    " + m.getQuantity() + " marks from " +  m.getMarkingPlayer());
                }
                break;
            }
        }
    }

    @Override
    public void setCurrentPlayingPlayer(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " +(String) MVE.getComponent());
    }

    @Override
    public void setStartingPlayer(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + ViewModelGate.getModel().getPlayers().getStartingPlayer());
    }

    @Override
    public void newPlayer(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + "new player added: " + ((PlayerV) MVE.getComponent()).getNickname());
    }

    @Override
    public void setAFK(ModelViewEvent MVE) {
        OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> MVE: " + MVE.getInformation());
        if(ViewModelGate.getMe().equals((String) MVE.getExtraInformation1() )){
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + "You've been set to AFK.");
        }
        else {
            OutputHandlerGate.getCLIOutputHandler().updateUserInterface("              " + "player " + (String) MVE.getExtraInformation1() + " AFK status: " + MVE.getComponent());
        }
    }

    @Override
    public void showInputTimer(int currentTime, int totalTime) {
        //OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> " + "Input-Timer-of-"+totalTime+"-seconds: " + currentTime + " second has passed.");
    }

    @Override
    public void showConnectionTimer(int currentTime, int totalTime) {
        //OutputHandlerGate.getCLIOutputHandler().updateUserInterface("<CLIENT> " + "Connection-Timer-of-"+totalTime+"-seconds: " + currentTime + " second has passed.");
    }
}
