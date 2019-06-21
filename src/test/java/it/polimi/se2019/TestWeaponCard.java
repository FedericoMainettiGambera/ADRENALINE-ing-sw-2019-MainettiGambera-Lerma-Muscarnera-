package it.polimi.se2019;

import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.CardinalPoint;
import it.polimi.se2019.model.enumerations.SquareSide;
import it.polimi.se2019.model.enumerations.SquareTypes;
import it.polimi.se2019.virtualView.VirtualView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

//TODO
public class TestWeaponCard {
    private String charRepeat(char c,int n) {
        String retVal = "";
        for(int i = 0 ; i < n;i++) {
            retVal += c;
        }
        return retVal;

    }
    private void showMap(Player user,PlayersList playersList,Board board,List<Object> possible) {
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
                    Mappa[Y][X] = new NormalSquare(Y,X,SquareSide.wall,SquareSide.wall,SquareSide.wall,SquareSide.wall, SquareTypes.normal,'X');
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
                    if(cell.getCoordinates().equals(x.getPosition())) {
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

    @Test
    public void testCosti() throws Exception {
        Board board = new Board("0",new VirtualView(),new VirtualView());
        for(Square[] r: board.getMap()) {
            for (Square c: r)
                System.out.println(c);
            System.out.println("\n");
        }
        List<Player> user = new ArrayList<>();
        user.add(new Player());user.add(new Player());user.add(new Player());user.add(new Player());user.add(new Player());
        Player user1 = user.get(0);
        Player user2 = user.get(1);
        Player user3 = user.get(2);
        Player user4 = user.get(3);
        Player user5 = user.get(4);

        user1.setNickname("Aldo");
        user2.setNickname("Bruno");
        user3.setNickname("Carlo");
        user4.setNickname("Dario");
        user5.setNickname("Elena");

        PlayersList playerList = new PlayersList();
        playerList.getPlayers().add(user1);
        playerList.getPlayers().add(user2);
        playerList.getPlayers().add(user3);
        playerList.getPlayers().add(user4);
        playerList.getPlayers().add(user5);

        user1.setPosition(0, 1);
        user2.setPosition(0, 1);
        user3.setPosition(0 , 2);                 //   same position
        user4.setPosition(0, 0);
        user5.setPosition(2, 0);
        WeaponCard w = new WeaponCard(12+"");
        w.passContext(user1,playerList,board);
        Object[] row = new Object[10];
        row[0] = w.getEffects().get(0).usableInputs().get(0).get(0).get(0);
        w.getEffects().get(0).handleRow(w.getEffects().get(0).getEffectInfo().getEffectInfoElement().get(0),row);
        w.getEffects().get(0).Exec();
        showMap(user1,playerList,board,w.getEffects().get(0).usableInputs().get(0).get(0));


              System.out.println("^^^^^ CARTA ");
        }


    /*
    @Test
    public void test() {
        try {
            for(int i = 1;i< 8;i++) {
                WeaponCard weaponCard = new WeaponCard(i + "");
                System.out.println("#" + i + ":");
                try {
                    for(EffectInfoElement e: weaponCard.getEffects().get(0).getEffectInfo().getEffectInfoElement())
                        System.out.println(" input mode " + e.getEffectInfoTypelist());
                } catch( NullPointerException e) {
                    System.out.println("errore:" + "non specificato");

                }
            }

        } catch (Exception e) {
            System.out.println("errore : " + e.toString());


        }
    }
    @Test
    public void testAlpha() throws Exception{
        int caso = 2;
        String ID = "Test";
        if(caso == 1) {
            testerA(ID);
        }
        else {
            WeaponCard weaponCard = new WeaponCard(ID);
            System.out.println(weaponCard.getEffects().size());

        }
    }
    private void testerA(String ID) throws Exception{
        BufferedReader reader = new BufferedReader(new FileReader("src/main/Files/cards/weaponCards/card"+ID+".set"));
        List<Effect> effects = new ArrayList<>();

        try {
            String line;
            line = reader.readLine();

            while (line != null) {
                if (line.equals("NEW EFFECT"))
                    effects.add(new Effect());

                System.out.println(line);
                line = reader.readLine();
            }
        } catch(Exception e) {;}
        System.out.println("---");
        System.out.println(effects.size());
    }
    @Test
    public void testExecuting() {
        try {
            Player user   = new Player();

            Player target = new Player();
            Player target2 = new Player();
            Player target3 = new Player();

            user.setNickname("Paolo");
            user.setPosition(0,0);

            target.setNickname("Luca");
            target2.setNickname("Marco");
            target3.setNickname("Pippo");

            PlayersList playerList = new PlayersList();
            playerList.addPlayer(user);
            playerList.addPlayer(target);
            playerList.addPlayer(target2);
            playerList.addPlayer(target3);

            Square A = new NormalSquare(1,0, SquareSide.wall,SquareSide.wall,SquareSide.wall,SquareSide.wall, SquareTypes.normal,'r');
            Square B = new NormalSquare(2,0, SquareSide.wall,SquareSide.wall,SquareSide.wall,SquareSide.wall,SquareTypes.normal,'g');


            target2.setPosition(1,0);


            WeaponCard weaponCard = new WeaponCard("Test2");
            Object i[][] = new Object[10][10];
            i[0][0] = target;
            i[0][1] = target2;
            i[0][2] = target3;
            for(Action a : weaponCard.getEffects().get(0).getActions()) {
                a.getActionInfo().getActionContext().setPlayer(user);
                a.getActionInfo().getActionContext().setPlayerList(playerList);
            }



            weaponCard.getEffects().get(0).handleInput(i);



            for(Action a : weaponCard.getEffects().get(0).getActions()) {

                System.out.println("utente: " + a.getActionInfo().getActionContext().getPlayer().getNickname());


                System.out.println("precondizione\t" + a.getActionInfo().getPreConditionMethodName());
                for(ActionContextFilteredInput b:  a.getActionInfo().getActionContext().getActionContextFilteredInputs()) {
                    System.out.println(a.toString() + "ha ricevuto come input " + Arrays.deepToString(b.getContent()) + " di tipo " + b.getType());
                }
                a.getActionInfo().getActionContext().setPlayer(user);

            }

            if(weaponCard.getEffects().get(0).Exec()) {
                System.out.println("Eseguita!");
            } else {
                System.out.println("non eseguita");
            }
        } catch(Exception e) {

            System.out.println("##" + e.toString());
        }

    }
    @Test
    public void testAttackBySquare() throws Exception {

        Board board = new Board("2",new VirtualView(),new VirtualView());
        Player user1 = new Player();
        Player user2 = new Player();
        Player user3 = new Player();
        Player user4 = new Player();
        System.out.println(".");
        user1.setNickname("Aldo");
        user2.setNickname("Bruno");
        user3.setNickname("Carlo");
        user4.setNickname("Dario");
        System.out.println(".");
        PlayersList playerList = new PlayersList();
        playerList.getPlayers().add(user1);
        playerList.getPlayers().add(user2);
        playerList.getPlayers().add(user3);
        playerList.getPlayers().add(user4);
        user1.setPosition(1,0);
        user2.setPosition(2,0);
        user3.setPosition(3,0);                 //   same position
        user4.setPosition(2,0);                 //                  users
        System.out.println(".");
        Object[][] o = new Object[10][10];
        Square A =  new NormalSquare(0,0, SquareSide.wall,SquareSide.wall,SquareSide.wall,SquareSide.wall, SquareTypes.normal,'r');
        String B =  "Bruno";
        System.out.println("x");
        o[0][0]      =  board.getSquare(1,0);
        o[1][0]      =  board.getSquare(0,0);

        WeaponCard weaponCard = new WeaponCard("21");
        for(Effect e : weaponCard.getEffects())
        for(Action a : e.getActions()) {
            a.getActionInfo().getActionContext().setPlayer(user1);
            a.getActionInfo().getActionContext().setPlayerList(playerList);
            a.getActionInfo().getActionContext().setBoard(board);
        }
        // gestione dell'input
        for(Square[] x : board.getMap()) {
            String linea = "\n";
            for(Square y: x) {
                linea += y.getColor() + "\t\t\t\t\t";
            }
            linea += "\n";
            for(Square y: x) {

                int output = 0;
                if(y.getSide(CardinalPoint.north) == SquareSide.wall) {
                    output = 1;
                }
                if(y.getSide(CardinalPoint.north) == SquareSide.door) {
                    output = 2;
                }
                if(y.getSide(CardinalPoint.north) == SquareSide.nothing) {
                    output = 0;
                }
                linea += "|\t\t" + output  + "\t\t|\t";
            }
            linea += "\n";
            for(Square y: x) {
                int output = 0;
                if(y.getSide(CardinalPoint.east) == SquareSide.wall) {
                    output = 1;
                }
                if(y.getSide(CardinalPoint.east) == SquareSide.door) {
                    output = 2;
                }
                if(y.getSide(CardinalPoint.east) == SquareSide.nothing) {
                    output = 0;
                }
                int output2 = 0;
                if(y.getSide(CardinalPoint.west) == SquareSide.wall) {
                    output2 = 1;
                }
                if(y.getSide(CardinalPoint.west) == SquareSide.door) {
                    output2 = 2;
                }
                if(y.getSide(CardinalPoint.west) == SquareSide.nothing) {
                    output2 = 0;
                }
                linea += "|" + output + "             " + output2 + "|\t";
            }
            linea += "\n";
            for(Square y: x) {
                int output = 0;
                if(y.getSide(CardinalPoint.south) == SquareSide.wall) {
                    output = 1;
                }
                if(y.getSide(CardinalPoint.south) == SquareSide.door) {
                    output = 2;
                }
                if(y.getSide(CardinalPoint.south) == SquareSide.nothing) {
                    output = 0;
                }
                linea += "|\t\t" + output  + "\t\t|\t";
            }
            linea += "\n";
            System.out.println(linea);
        }
        // caricamento contesto
        int effectId = 1;
        user2.setPosition(user1.getPosition());
        System.out.println("/----------------/");
        o[0][0]      =  user2;
        o[1][0]      =  board.getSquare(2,1) ;
      //  o[1][2]      =  user4 ;
        weaponCard.getEffects().get(effectId).handleInput(o);
        weaponCard.getEffects().get(effectId).Exec();

        System.out.println("/--/");
        System.out.println("status");
        System.out.println("/--/");


    }
    @Test
    public void testPlayerHistory() throws Exception{

        Player me = new Player();
        me.setNickname("A");

        Player ne = new Player();
        ne.setNickname("B");

        Player oe = new Player();
        oe.setNickname("C");
        PlayersList p = new PlayersList();
        p.addPlayer(me);
        p.addPlayer(ne);
        p.addPlayer(oe);

        Board board = new Board("2",new VirtualView(),new VirtualView());
        WeaponCard w = new WeaponCard("1");
        for(Action a : w.getEffects().get(0).getActions()) {
            a.getActionInfo().getActionContext().setPlayer(me);
            a.getActionInfo().getActionContext().setPlayerList(p);
            a.getActionInfo().getActionContext().setBoard(board);
        }
        int i;
        Object[][] o = new Object[10][10];
        o[0][0] = me;
        w.effects.get(0).handleInput(o);
        o[0][0] = ne;
        w.effects.get(0).handleInput(o);
        for(i = 0; i < me.getPlayerHistory().getSize();i++) {
            System.out.println("#" + ((Player)(((Object[][])me.getPlayerHistory().getRecord(i).getInput())[0][0])).getNickname());
        }
        o[0][0] = oe;
        w.effects.get(0).handleInput(o);

        for(i = 0; i < me.getPlayerHistory().getSize();i++) {
            System.out.println("#" + ((Player)(((Object[][])me.getPlayerHistory().getRecord(i).getInput())[0][0])).getNickname());
        }
    }
    @Test
    public void testCard9() {
        try {
            System.out.println(".");
            WeaponCard weaponCard = new WeaponCard("21");
            System.out.println(".");
            System.out.println(weaponCard.getEffects().size());
            for(Effect e:weaponCard.getEffects() ) {

                for(EffectInfoElement ei: e.getEffectInfo().getEffectInfoElement()) {
                    System.out.println("questo input '" + ei.getEffectInfoTypelist().toString() +"'");
                    for(Integer x:ei.getEffectInfoTypeDestination())
                        System.out.println("\te' per:" + x);
                }
                System.out.println(e.toString());
                int j = 0;
                for(Action a: e.getActions()) {
                    System.out.println("quando " + a.getActionInfo().getPreConditionMethodName() + "() e' vera, allora" );
                    System.out.println((++j) + "\t" + a.toString());
                    for(Object o : a.getActionInfo().getActionDetails().getFileSelectedActionDetails().getFileSettingData()) {
                        System.out.println("\t\tValue : "+ o.toString());
                    }
                }
                System.out.println("--");

            }
        } catch(Exception e) {
            System.out.println("##" + e.toString());

        }
    }



    @Test
    public void testUsable() throws Exception {


        Board board = new Board("2",new VirtualView(),new VirtualView());
        List<Player> user = new ArrayList<>();
        user.add(new Player());user.add(new Player());user.add(new Player());user.add(new Player());user.add(new Player());
        Player user1 = user.get(0);
        Player user2 = user.get(1);
        Player user3 = user.get(2);
        Player user4 = user.get(3);
        Player user5 = user.get(4);

        user1.setNickname("Aldo");
        user2.setNickname("Bruno");
        user3.setNickname("Carlo");
        user4.setNickname("Dario");
        user5.setNickname("Elena");

        PlayersList playerList = new PlayersList();
        playerList.getPlayers().add(user1);
        playerList.getPlayers().add(user2);
        playerList.getPlayers().add(user3);
        playerList.getPlayers().add(user4);
        playerList.getPlayers().add(user5);

        user1.setPosition(0, 1);
        user2.setPosition(0, 1);
        user3.setPosition(0 , 2);                 //   same position
        user4.setPosition(0, 0);
        user5.setPosition(2, 0);
            int i = 1;
            int j = 20;
            int effectN = 0;
            int effectM = 1;
            int currentPlayer = 1;
            for(i = j;i <= j;i++) {
                System.out.println("#############################################");
                System.out.println("#############################################");
                System.out.println("#############################################");
                System.out.println("#############################################");
                System.out.println("#############################################");
                System.out.println("#############################################");
                System.out.println("#############################################");
                System.out.println("#############################################");
                System.out.println("#############################################");
                System.out.println("#############################################");
                System.out.println("#############################################");
                System.out.println("#############################################");
                System.out.println("#############################################");
                System.out.println("#############################################");
                System.out.println("#############################################");
                System.out.println("#############################################");
                System.out.println("#############################################");
                System.out.println("#############################################");
                System.out.println("#############################################");
                for(i = 1; i <= 21;i++) {
                    WeaponCard w = new WeaponCard(i + "");
                    w.passContext(user.get(currentPlayer), playerList, board);
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> CHECKERRRR " + w.usableEffects());
                }
                try {
                    System.out.println("\n\n\n*************" + i + "***********\n\n\n");
                    WeaponCard w = new WeaponCard(i + "");
                    System.out.println(w.getEffects());
                    w.passContext(user.get(currentPlayer), playerList, board);
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>" + w.usableEffects().size());
                    Object[] row = new Object[10];
                    row[0] = user1;
                    row[1] = user2;
                    w.getEffects().get(effectN).handleRow(w.getEffects().get(effectN).getEffectInfo().getEffectInfoElement().get(0),row);

                    showMap(user.get(currentPlayer), playerList, board, w.getEffects().get(effectN).usableInputs().get(0).get(0));

//                    showMap(user.get(currentPlayer), playerList, board, w.getEffects().get(effectN).usableInputs().get(1).get(0));
                    //row[0] =    board.getSquare(user5.getPosition());
                    //w.getEffects().get(effectN).handleRow(w.getEffects().get(effectN).getEffectInfo().getEffectInfoElement().get(1),row);
                    //showMap(user.get(currentPlayer), playerList, board, w.getEffects().get(effectN).usableInputs().get(1).get(0));
                    //for (Effect e : w.getEffects()) {
                       // System.out.println(w.getEffects().get(effectN) + ">>>>>>>>>>>>>>>>>>>>>>>" + w.getEffects().get(effectN).usableInputs());

                         row[0] = board.getSquare(user3.getPosition());
                      //  showMap(user2, playerList, board, w.getEffects().get(effectM).usableInputs().get(0).get(0));
                    //showMap(user2, playerList, board, w.getEffects().get(0).usableInputs().get(1).get(0));
                        //row[0] = board.getSquare(0,2);
                       // System.out.println("possibile " + w.getEffects().get(0).usableInputs().get(1).get(0));
                        //w.getEffects().get(0).handleRow(w.getEffects().get(0).getEffectInfo().getEffectInfoElement().get(1),row);
//                        showMap(user2, playerList, board, w.getEffects().get(0).usableInputs().get(1).get(0));
                   //     w.getEffects().get(1).Exec();

                /*    Object[] r = new Object[10];
                    int inputCorrente = 0;
                    // il primo input è player
                    // r al momento è vuota
                    r[0] = new Object();
                    w.getEffects().get(effectN).handleRow(w.getEffects().get(effectN).getEffectInfo().getEffectInfoElement().get(inputCorrente),r);
                    inputCorrente++;
                    // il secondo input è simpleSquareSelect
                    r[0] = board.getSquare(0,0);
                    w.getEffects().get(effectN).handleRow(w.getEffects().get(effectN).getEffectInfo().getEffectInfoElement().get(inputCorrente),r);
                    */
    /*
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
    }*/
}

