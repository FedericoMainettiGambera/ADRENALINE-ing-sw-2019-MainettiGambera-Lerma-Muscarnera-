package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.ShootPeopleAskForInputState;
import it.polimi.se2019.model.Effect;
import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventListOfObject;
import it.polimi.se2019.view.components.PlayerV;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class ShootPeopleAskForInputTest{

    @Test
    public void shootPeopleAskForInput() throws IOException {

        ModelGate.setModel(new FakeModel().create());
        Game game=ModelGate.getModel();
        game.buildDecks();

        WeaponCard weaponCard=game.getWeaponDeck().getFirstCard();
        Effect effect=weaponCard.getEffects().get(0);

        ShootPeopleAskForInputState shootPeopleAskForInputState=new ShootPeopleAskForInputState(effect, weaponCard, 1 );

        List<Object> list=new ArrayList<>();
        list.add(new PlayerV());

        ViewControllerEvent viewControllerEvent=new ViewControllerEventListOfObject(list);


    }
}
