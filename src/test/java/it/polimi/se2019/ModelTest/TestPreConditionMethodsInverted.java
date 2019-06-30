package it.polimi.se2019.ModelTest;

import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.*;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.se2019.model.enumerations.UsableInputTableRowType.typePlayer;
import static it.polimi.se2019.model.enumerations.UsableInputTableRowType.typeSquare;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestPreConditionMethodsInverted {
    private String charRepeat(char c,int n) {
        String retVal = "";
        for(int i = 0 ; i < n;i++) {
            retVal += c;
        }
        return retVal;

    }
    private void showMap(Player user, PlayersList playersList, Board board, List<Object> possible) {
        boolean something = true;
        Square[][] Mappa = new Square[3][4];
        int Y = 0;
        int X = 0;
        for(Square r[] : board.getMap()) {
            X = 0;
            for(Square c: r) {
                if(c!= null)
                    Mappa[Y][X] = c;
                else
                    Mappa[Y][X] = new NormalSquare(Y,X, SquareSide.wall,SquareSide.wall,SquareSide.wall,SquareSide.wall, SquareTypes.normal,'X');
                X++;
            }
            Y++;
        }
        if(possible == null) {
            something  = false;
        } else
        {
            if(possible.size() == 0)
                something = false;
        }
        boolean playerHighlight = false;
        int possibleCounter = 0;

        if(something) {
            if (possible.get(0).getClass().toString().equals("class it.polimi.se2019.model.Player")) {
                playerHighlight = true;
            }
        }
        String output = "";

        int rowCounter = 0;
        for(Square[] row : Mappa)
        {
            int cellCounter = 0;
            for(Square cell: row) {
                String currentOutput = "";
                currentOutput = " ________________ ";
                String doorUp = "__";
                if(cell.getSide(CardinalPoint.north).equals(SquareSide.door)) {
                    doorUp = "    ";
                    currentOutput = " ______" + doorUp + "______ ";
                }
                if ((rowCounter - 1) >= 0)
                    if (cell.getColor() == Mappa[rowCounter - 1][cellCounter].getColor()) {

                        currentOutput = "|" + charRepeat(' ', 16) + "|";
                    }
                output += currentOutput;
                cellCounter++;
            }

            output +='\n';
            for (Square cell : row) {
                char isChoseable = ' ';
                if(something)
                    if(!playerHighlight)
                        if(possible.contains(cell))
                            isChoseable = '#';

                String label = "|COLOR: " + cell.getColor() + "" + isChoseable + "x:"+cell.getCoordinates().getX() + ",y:"+cell.getCoordinates().getY();
                output +=  label + charRepeat(' ',17 - (label).length() )  + "|";

            }
            output +='\n';
            int counter = 0;
            double middle = Math.ceil( (playersList.getPlayers().size() * 0.5) );
            char lastColor = row[0].getColor();
            for(Player x: playersList.getPlayers()) {
                cellCounter = 0;
                for (Square cell : row) {
                    String content = "";
                    if(cell.getColor() == 'X')
                        content = "XXXXXXXXXXXXXX";
                    if(cell.getCoordinates().equalPositions(x.getPosition())) {
                        char brake1 = ' ';
                        char brake2 = ' ';
                        char brake3 = ' ';
                        char brake4 = ' ';
                        if(x.equals(user)) {
                            brake3 = '[';
                            brake4 = ']';
                        }
                        if(something)
                            if(playerHighlight) {

                                for(Object o : possible) {
                                    if(((Player) o).equals(x)) {
                                        brake1 = '<';
                                        brake2 = '>';
                                    }
                                }
                            }
                        content = brake1 +""+ brake3 +  x.getNickname() + brake4 +""+ brake2;
                    }
                    int spaces = (16 - (content.length()))/2;
                    int corrector = 0;
                    if((spaces * 2 + content.length()) < 16) {
                        corrector++;
                    }
                    char charWallWest = '|';
                    char charWallEast = '|';
                    char charWallNorth = '|';
                    char charWallSouth = '|';
                    if(counter == middle) {
                        if(cell.getSide(CardinalPoint.west).equals(SquareSide.door)) {
                            charWallWest = ' ';
                        }
                        if(cell.getSide(CardinalPoint.east).equals(SquareSide.door)) {
                            charWallEast = ' ';
                        }
                    }
                    if(cell.getColor() == lastColor ) {
                        charWallWest = ' ';
                    }
                    if((cellCounter + 1 ) < row.length)
                        if(cell.getColor() == row[cellCounter+1].getColor() ) {
                            charWallEast = ' ';
                        }
                    output += charWallWest + charRepeat(' ', spaces + corrector) + content + charRepeat(' ', spaces  )  + charWallEast;
                    lastColor = cell.getColor();
                    cellCounter++;
                }
                output += '\n';
                counter++;
            }
            cellCounter= 0;
            for(Square cell: row) {
                String currentOutput = "";
                currentOutput = "|________________|";
                String doorDown = "__";
                if(cell.getSide(CardinalPoint.south).equals(SquareSide.door)) {
                    doorDown = "    ";
                    currentOutput = "|______" + doorDown + "______|";
                }

                if((rowCounter + 1 ) < Mappa.length)
                    if(cell.getColor() == Mappa[rowCounter+1][cellCounter].getColor()) {

                        currentOutput = "|" + charRepeat(' ',16) + "|";
                    }
                output += currentOutput;
                cellCounter++;
            }
            output += '\n';
            rowCounter++;
        }
        System.out.println(output);
    }
    private ActionContext fakeContext(int  n,String mappa) throws Exception {
        ActionContext retVal = new ActionContext();
        List<Player> user = new ArrayList<>();
        Board board = new Board(mappa,null,null);



        PlayersList playerList = new PlayersList();
        for(int i = 0; i < 2;i++)
        for(Square[] r : board.getMap())
            for(Square c: r)
            {
                if(c != null) {
                    playerList.addPlayer(new Player());
                    playerList.getPlayers().get(
                            playerList.getPlayers().size() - 1
                    ).setNickname(c.getCoordinates().humanString() + i);
                    playerList.getPlayers().get(
                            playerList.getPlayers().size() - 1
                    ).setPositionWithoutNotify(c.getCoordinates());
                }
            }



        retVal.setBoard(board);
        retVal.setPlayerList(playerList);
        retVal.setPlayer(playerList.getPlayersOnBoard().get(n));
        return retVal;
    }
    private ActionDetails fakeDetails(int n,ActionContext AC) {
        ActionDetails d = new ActionDetails();
        if(n == 0)
            return d;
        if(n == 1)
            {
                d.getUserSelectedActionDetails().setTarget(AC.getPlayerList().getPlayersOnBoard().get(0));
                return d;
            }
        if(n == 2)
            {
                d.getUserSelectedActionDetails().setChosenSquare(
                        AC.getBoard().getSquare(AC.getPlayerList().getPlayersOnBoard().get(0).getPosition())
                );
                return d;
            }
        return d;
    }

    private  List<Object> caller(String name, PreConditionMethodsInverted gate, ActionContext AC, UsableInputTableRowType type,ActionDetails AD)  {
        //(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect)
        try {
            //System.out.println(".");
            java.lang.reflect.Method method;
            //System.out.println(".");
            Class<?> c = Class.forName("it.polimi.se2019.model.PreConditionMethodsInverted");
            //System.out.println(".");
            Class<?>[] paramTypes = {ActionContext.class, UsableInputTableRowType.class, ActionDetails.class, Object.class, List.class, Effect.class};
            //System.out.println(".");
            method = c.getDeclaredMethod(name, paramTypes);
            //System.out.println(".");
            //System.out.println("Il player corrente " + getActionContext().getPlayer().getNickname());
            //System.out.println(".");
            Object returnValue = method.invoke(gate,
                    AC,type,AD,null,null,null);

            return (List<Object>) returnValue;
        }
        catch(Exception E) {
            System.out.println("eccezione! " + E.toString());
            return new ArrayList<>();

        }
        //return false;               // if it throws exception it returns false
    }
    private  List<Object> caller(String name, PreConditionMethodsInverted gate, ActionContext AC, UsableInputTableRowType type,ActionDetails AD,Object input, List<EffectInfoType> slot)  {
        //(ActionContext actionContext, UsableInputTableRowType type, ActionDetails actionDetails, Object inputs, List<EffectInfoType> inputSlots,Effect contextEffect)
        try {
            //System.out.println(".");
            java.lang.reflect.Method method;
            //System.out.println(".");
            Class<?> c = Class.forName("it.polimi.se2019.model.PreConditionMethodsInverted");
            //System.out.println(".");
            Class<?>[] paramTypes = {ActionContext.class, UsableInputTableRowType.class, ActionDetails.class, Object.class, List.class, Effect.class};
            //System.out.println(".");
            method = c.getDeclaredMethod(name, paramTypes);
            //System.out.println(".");
            //System.out.println("Il player corrente " + getActionContext().getPlayer().getNickname());
            //System.out.println(".");
            Object returnValue = method.invoke(gate,
                    AC,type,AD,
                    input,slot,null);

            return (List<Object>) returnValue;
        }
        catch(Exception E) {
            System.out.println("eccezione! " + E.toString());
            return new ArrayList<>();

        }
        //return false;               // if it throws exception it returns false
    }

    @Test
    public void TestBase() throws  Exception {
        PreConditionMethodsInverted PIGATE = new PreConditionMethodsInverted();
        int s = 0;
        Class metodi = PreConditionMethodsInverted.class;
        Method[] m = metodi.getDeclaredMethods();

        for(Square r[]: (new Board("0",null,null).getMap()))
            for(Square c:r)
                if(c!=null)
                    s ++;
        List<UsableInputTableRowType> tot = new ArrayList<>();
        tot.add(typeSquare);
        tot.add(typePlayer);
        for(UsableInputTableRowType t: tot)
        for(Method method : m)
            for(int z = 0;z < 3;z++)
                for(int y=0; y < 1;y++) {
                    for (int x = 0; x < 1; x++) {
                        ActionContext fake = fakeContext(x, y + "");
                        List<Object> output = caller(
                                method.toString().substring(73,method.toString().indexOf('(')),
                                new PreConditionMethodsInverted(),
                                fake,
                                t,
                                fakeDetails(z,fake)
                        );
//                        assertNotEquals(output.size(),0);
                    }
                }
    System.out.println("fine");
    }

    @Test
    public void testdifferentSingleTargets() throws  Exception{
    // load an not complete context if it doesn't fail it fails
    ActionContext AC = fakeContext(0,"0");
    List<EffectInfoType> inputSlots = new ArrayList<>();
    List<Object[]>         inputs     = new ArrayList<>();

    inputSlots.add(EffectInfoType.singleTarget);
    Object[] row = new Object[10];
    row[0]= (AC.getPlayerList().getPlayersOnBoard().get(1));
    inputs.add(row);


        List<Object> output = caller(
                "differentSingleTargets",
                new PreConditionMethodsInverted(),
                AC,
                typePlayer,
                fakeDetails(0,AC),
                inputs,
                inputSlots
        );

        assertNotEquals(
         true,
         output.contains((Player) row[0])
        );
    }
}
