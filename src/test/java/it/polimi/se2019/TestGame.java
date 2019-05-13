package it.polimi.se2019;

import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.CardinalPoint;
import it.polimi.se2019.model.enumerations.SquareSide;
import it.polimi.se2019.model.enumerations.SquareTypes;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestGame{

    Game game=new Game();
    Player player= new Player();
    PlayersList playerList= new PlayersList();
    KillShotTrack killShotTrack= new KillShotTrack(6);
    Bot bot= new Bot(true);



    @Test
    public void testMethods() throws Exception{

        playerList.addPlayer(player);
        String s="map0";
        Board board= new Board(s);
        OrderedCardList<PowerUpCard> ppc=new OrderedCardList<>();
        OrderedCardList<AmmoCard> ac= new OrderedCardList<>();
        OrderedCardList<WeaponCard> wc= new OrderedCardList<>();

        game.setFinalFrenzy(true);
        assertEquals(true, game.isFinalFrenzy());
        game.triggerFinalFrenzy(true);
        assertEquals(true, game.hasFinalFrenzyBegun());
        game.setKillshotTrack(killShotTrack);
        assertEquals(false, game.getKillshotTrack().areSkullsOver());
        game.setBot(bot);
        assertEquals(true, game.getBot().isBotActive());

        game.setPlayerList(playerList);
        game.setBoard(board);
        Square[][] map=board.getMap();
        assertEquals(SquareSide.wall,game.getBoard().getSquare(0,0).getSide(CardinalPoint.north));

        game.setPowerUpDeck(ppc);
        game.setAmmoDeck(ac);
        game.setWeaponDeck(wc);
        game.setPowerUpDiscardPile(game.getPowerUpDeck());
        wc=game.getWeaponDeck();
        game.setAmmoDiscardPile(game.getAmmoDeck());




    }
}
