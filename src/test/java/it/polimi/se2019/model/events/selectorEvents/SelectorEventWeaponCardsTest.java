package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.ControllerTest.FakeModel;
import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.view.components.GameV;
import it.polimi.se2019.view.components.WeaponCardV;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SelectorEventWeaponCardsTest {

    @Test
    void getWeaponCards() throws IOException {

        List<WeaponCardV> weapons =new ArrayList<>();
        Game game=new FakeModel().create();
        game.buildDecks();

        GameV game1=game.buildGameV();
        weapons.add( game1.getWeaponDeck().getCards().get(0));






        SelectorEvent event=new SelectorEventWeaponCards(SelectorEventTypes.askWhatReaload,weapons);
        assertEquals(weapons.get(0), ((SelectorEventWeaponCards) event).getWeaponCards().get(0));
    }
}