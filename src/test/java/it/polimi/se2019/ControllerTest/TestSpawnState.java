package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.SpawnState;
import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventString;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class TestSpawnState{
/*
   private FakeModel fakeModel=new FakeModel();

   @Test
    public void testSpawnState() throws IOException {

      Game game = fakeModel.create();
       ModelGate.setModel(game);

       PowerUpCard powerUpCard= new PowerUpCard();
       powerUpCard.setName("1");





       powerUpCard.setColor(AmmoCubesColor.red);
       ArrayList<Player> dead=new ArrayList<>();

       dead.add(game.getPlayerList().getPlayer("Alex"));

       ViewControllerEventString vce=new ViewControllerEventString("0");


       game.buildDecks();

       System.out.println(game.getPowerUpDeck().getFirstCard().getID());


       SpawnState state=new SpawnState(dead);

       state.deadDrawPowerUp();

       state.handleVce(vce);

       Position pos=new Position(1,0);


       assertEquals( pos.getX(), game.getPlayerList().getCurrentPlayingPlayer().getPosition().getX() );
       assertEquals( pos.getY(), game.getPlayerList().getCurrentPlayingPlayer().getPosition().getY());



   }
*/
}
