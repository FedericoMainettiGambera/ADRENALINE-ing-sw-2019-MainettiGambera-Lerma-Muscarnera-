package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.controller.ModelGate;
import it.polimi.se2018.controller.ViewControllerEventHandlerContext;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.Square;
import it.polimi.se2018.model.enumerations.AmmoCubesColor;
import it.polimi.se2018.model.enumerations.SpawnPointColors;
import it.polimi.se2018.model.enumerations.SquareTypes;
import it.polimi.se2018.model.events.ViewControllerEvent;
import it.polimi.se2018.model.events.ViewControllerEventPowerUp;

public class FirstSpawnState implements State {

    public FirstSpawnState(){
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {

        ViewControllerEventPowerUp VCEPowerUp = (ViewControllerEventPowerUp)VCE;

        char spawnPointColor;
        if(VCEPowerUp.getPowerUpCard().getColor() == AmmoCubesColor.red) {
            spawnPointColor = 'r';
        }
        else if(VCEPowerUp.getPowerUpCard().getColor() == AmmoCubesColor.blue) {
            spawnPointColor = 'b';
        }
        else{
            spawnPointColor = 'y';
        }

        //find the Position
        Position position = null;
        Square[][] map = ModelGate.model.getBoard().getBoard();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if((map[i][j].getColor() == spawnPointColor)&&(map[i][j].getSquareType() == SquareTypes.spawnPoint)){
                    position = map[i][j].getCoordinates();
                }
            }
        }
        ModelGate.model.getPlayerList().getCurrentPlayingPlayer().setPosition(position.getX(),position.getY());


        //discard the Power up card
        ModelGate.model.getPlayerList().getCurrentPlayingPlayer().getPowerUpCardsInHand().moveCardTo(ModelGate.model.getPowerUpDiscardPile(),VCEPowerUp.getPowerUpCard().getID());

        //set next State
        ViewControllerEventHandlerContext.setNextState(new TurnState());

        //ask to CurrentPlayingPlayer to Take his turn.

    }
}
