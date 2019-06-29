package it.polimi.se2019.ModelTest;

import it.polimi.se2019.model.AmmoList;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.PlayerBoard;
import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import org.junit.Test;

import static junit.framework.TestCase.*;

/**tests some of the remaining functions left to test
 * such as canPayAmmoCubes, either with a color and quantity as parameter or with an ammolist
 *PayAmmoCubes, either with a color and quantity as parameter or with an ammolist
 * and deleteMarksFromPlayer */
public class PlayerBoardTest {

    @Test
    public void playerBoard(){

        Player player=new Player();
        player.getPlayerBoard().addAmmoCubes(AmmoCubesColor.blue, 3);
        AmmoList ammoList=new AmmoList();
        ammoList.addAmmoCubesOfColor(AmmoCubesColor.red, 2);



        PlayerBoard playerBoard=player.getPlayerBoard();

        assertTrue(playerBoard.canPayAmmoCubes(AmmoCubesColor.blue, 2));
        assertFalse(playerBoard.canPayAmmoCubes(ammoList));

        assertTrue(playerBoard.payAmmoCubes(AmmoCubesColor.blue, 2));
        assertFalse(playerBoard.payAmmoCubes(ammoList));

        Player player1=new Player();
        player1.setNickname("Adam");


        playerBoard.addMarksFrom(player1, 4);
        playerBoard.deleteMarksFromPlayer(player1);

        assertEquals(0,playerBoard.getMarksFrom(player1));



    }
}
