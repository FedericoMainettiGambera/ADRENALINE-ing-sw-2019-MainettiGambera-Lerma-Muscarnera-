package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.TagBackGranadeState;
import it.polimi.se2019.controller.statePattern.TurnState;
import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventBoolean;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventInt;
import it.polimi.se2019.view.components.PowerUpCardV;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTagBackGrenade{

    @Test
    public void testTagBack(){

        try {
            ModelGate.setModel((new FakeModel()).create());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Game game=ModelGate.getModel();

        game.getCurrentPlayingPlayer().setPosition(0,0);
        game.getPlayerList().getPlayer("B").setPosition(0,0);

        List<Player> players= new ArrayList<>();

        players.add(game.getPlayerList().getPlayer("B"));
        players.add(game.getPlayerList().getPlayer("C"));

        ViewControllerEvent vce= new ViewControllerEventInt(1);

        PowerUpCard powerUpCard=new PowerUpCard();
        powerUpCard.setName("TAGBACK GRANADE");
        powerUpCard.setColor(AmmoCubesColor.blue);
        PowerUpCard powerUpCard2=new PowerUpCard();
        powerUpCard2.setName("TAGBACK GRANADE");
        powerUpCard2.setColor(AmmoCubesColor.red);

        game.getCurrentPlayingPlayer().getPowerUpCardsInHand().addCard(powerUpCard);
        game.getCurrentPlayingPlayer().getPowerUpCardsInHand().addCard(powerUpCard2);

        PowerUpCardV powerUpCardV1=new PowerUpCardV();
        PowerUpCardV powerUpCardV=new PowerUpCardV();
        powerUpCardV.setName("TAGBACK GRANADE");
        powerUpCardV.setColor(AmmoCubesColor.blue);
        powerUpCardV1.setName("TAGBACK GRANADE");
        powerUpCardV1.setColor(AmmoCubesColor.red);





        TagBackGranadeState tagBackGranadeState=new TagBackGranadeState(new TurnState(1),players,game.getCurrentPlayingPlayer());

        tagBackGranadeState.setListOfTagBackGranade(game.getCurrentPlayingPlayer());
        tagBackGranadeState.setCurrentPlayer();
        tagBackGranadeState.giveMark(vce);

        ViewControllerEvent vce1= new ViewControllerEventInt(2);
        tagBackGranadeState.giveMark(vce1);

        tagBackGranadeState.canSee(game.getCurrentPlayingPlayer(), game.getPlayerList().getPlayer("B"));

        List<PowerUpCardV> powerUpCardVList= tagBackGranadeState.printAndTransformTagBack();

        assertEquals("TAGBACK GRANADE",powerUpCardVList.get(0).getName());

    }
}
