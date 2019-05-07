package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.controller.ModelGate;
import it.polimi.se2018.controller.ViewControllerEventHandlerContext;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.PowerUpCard;
import it.polimi.se2018.model.Square;
import it.polimi.se2018.model.enumerations.AmmoCubesColor;
import it.polimi.se2018.model.enumerations.SquareTypes;
import it.polimi.se2018.model.events.ViewControllerEvent;
import it.polimi.se2018.model.events.ViewControllerEventString;

public class FirstSpawnState implements State {

    public FirstSpawnState(){
    }

    @Override
    public void askForInput(Player playerToAsk){
        //ask to "playerToAsk" inputs
    }

    @Override
    public void doAction(ViewControllerEvent VCE) throws NullPointerException {
        if(VCE == null){ //ask player
            Player playingPlayer = ModelGate.model.getPlayerList().getCurrentPlayingPlayer();
            this.askForInput(playingPlayer);
        }
        else {
            ViewControllerEventString VCEPowerUpId = (ViewControllerEventString) VCE;

            PowerUpCard cardChosen = ModelGate.model.getPlayerList().getCurrentPlayingPlayer().getPowerUpCardsInHand().getCard(VCEPowerUpId.getInput());

            char spawnPointColor;
            if (cardChosen.getColor() == AmmoCubesColor.red) {
                spawnPointColor = 'r';
            } else if (cardChosen.getColor() == AmmoCubesColor.blue) {
                spawnPointColor = 'b';
            } else {
                spawnPointColor = 'y';
            }

            //find the Position
            Position position = null;
            Square[][] map = ModelGate.model.getBoard().getBoard();


            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    if ((map[i][j].getColor() == spawnPointColor) && (map[i][j].getSquareType() == SquareTypes.spawnPoint)) {
                        position = map[i][j].getCoordinates();
                    }
                }
            }
            ModelGate.model.getPlayerList().getCurrentPlayingPlayer().setPosition(position.getX(), position.getY());


            //discard the Power up card
            ModelGate.model.getPlayerList().getCurrentPlayingPlayer().getPowerUpCardsInHand().moveCardTo(ModelGate.model.getPowerUpDiscardPile(), VCEPowerUpId.toString());

            //set next State
            ViewControllerEventHandlerContext.setNextState(new TurnState());

            //ask to CurrentPlayingPlayer to Take his turn.
        }
    }
}
