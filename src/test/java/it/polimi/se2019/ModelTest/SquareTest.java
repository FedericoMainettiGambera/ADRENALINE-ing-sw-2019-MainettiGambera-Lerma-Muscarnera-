package it.polimi.se2019.ModelTest;

import it.polimi.se2019.model.AmmoCard;
import it.polimi.se2019.model.NormalSquare;
import it.polimi.se2019.model.SpawnPointSquare;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.enumerations.CardinalPoint;
import it.polimi.se2019.model.enumerations.SquareSide;
import it.polimi.se2019.model.enumerations.SquareTypes;
import it.polimi.se2019.view.components.NormalSquareV;
import it.polimi.se2019.view.components.SpawnPointSquareV;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**implemented to test the methods of model element square*/
public class SquareTest{


    private NormalSquare normalSquare=new NormalSquare(0,0,null,SquareSide.nothing,SquareSide.nothing,SquareSide.wall,
            SquareTypes.normal,'r');

    private SpawnPointSquare spawnPointSquare=new SpawnPointSquare(1,1,null,SquareSide.nothing,SquareSide.nothing,SquareSide.wall,
            SquareTypes.spawnPoint,'r');

    /**tests the methods of normal square and Spawn point square who didn't get to be tested somewhere else
     * @throws Exception different kind of exception may occur*/
    @Test
    public void testWhatsLeft() throws Exception{

        //normal square
        AmmoCard ammoCard=new AmmoCard("8");
        normalSquare.getAmmoCards().addCard(ammoCard);

         assertEquals(SquareSide.nothing, normalSquare.getSide(CardinalPoint.east));
         assertEquals(SquareSide.nothing, normalSquare.getSide(CardinalPoint.south));
         assertEquals(SquareSide.wall, normalSquare.getSide(CardinalPoint.west));
         assertEquals(SquareTypes.normal, normalSquare.getSquareType());
         assertEquals('r', normalSquare.getColor());
         assertNull(normalSquare.getSide(CardinalPoint.north));
         assertEquals(ammoCard, normalSquare.getAmmoCards().getCard("8"));

         NormalSquareV normalSquareV =normalSquare.buildNormalSquareV(normalSquare);

         assertEquals(0,normalSquareV.getX());
         assertEquals(0,normalSquareV.getY());
         assertEquals(SquareSide.nothing, normalSquareV.getSide(CardinalPoint.east));
         assertEquals(SquareSide.nothing, normalSquareV.getSide(CardinalPoint.south));
         assertEquals(SquareSide.wall, normalSquareV.getSide(CardinalPoint.west));
         assertEquals(SquareTypes.normal, normalSquareV.getSquareType());
         assertEquals('r', normalSquareV.getColor());
         assertNull(normalSquareV.getSide(CardinalPoint.north));
         assertEquals(1, normalSquareV.getAmmoCards().getCards().size());


         //spawn Square

        //normal square
        WeaponCard weaponCard=new WeaponCard("8");
        spawnPointSquare.getWeaponCards().addCard(weaponCard);

        assertEquals(SquareSide.nothing, spawnPointSquare.getSide(CardinalPoint.east));
        assertEquals(SquareSide.nothing, spawnPointSquare.getSide(CardinalPoint.south));
        assertEquals(SquareSide.wall, spawnPointSquare.getSide(CardinalPoint.west));
        assertEquals(SquareTypes.spawnPoint, spawnPointSquare.getSquareType());
        assertEquals('r', spawnPointSquare.getColor());
        assertNull(spawnPointSquare.getSide(CardinalPoint.north));
        assertEquals(weaponCard, spawnPointSquare.getWeaponCards().getCard("8"));

        SpawnPointSquareV spawnPointSquareV = spawnPointSquare.builSpawnPointSquareV(spawnPointSquare);

        assertEquals(1,spawnPointSquareV.getX());
        assertEquals(1,spawnPointSquareV.getY());
        assertEquals(SquareSide.nothing, spawnPointSquareV.getSide(CardinalPoint.east));
        assertEquals(SquareSide.nothing, spawnPointSquareV.getSide(CardinalPoint.south));
        assertEquals(SquareSide.wall, spawnPointSquareV.getSide(CardinalPoint.west));
        assertEquals(SquareTypes.spawnPoint, spawnPointSquareV.getSquareType());
        assertEquals('r', spawnPointSquareV.getColor());
        assertNull(spawnPointSquareV.getSide(CardinalPoint.north));
        assertEquals(1, spawnPointSquareV.getWeaponCards().getCards().size());



    }


}
