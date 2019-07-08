package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.model.*;

import java.io.IOException;

/**this class implements functions that create a fake model
 * in order to simulate the game and allow the state pattern
 * to be tested in a reasonable context
 * */
public class FakeModel{

    /**fake game to initialize*/
    private static Game game=new Game();
    /**three fakes player to have a player list */
    private Player player1=new Player();
    private Player player2=new Player();
    private Player player3=new Player();



    /**above mentioned player list*/
    private PlayersList playersList=new PlayersList();



    /** function that create the fake game
     * @return game, obviously
     * @throws IOException that may occur while initializing the model
     * */
    public Game create() throws IOException {


        setPlayers();
        game.setPlayerList(playersList);
        game.setBoard(new Board("0", null, null));
        game.setBotActive(true);
        game.setFinalFrenzy(true);
        Player p= game.getPlayerList().getPlayer("Alex");
        game.getPlayerList().setCurrentPlayingPlayer(p);

        return game;
    }

    /**function that add the players to the player list*/
    private void setPlayers(){

        player1.setNickname("Alex");
        playersList.addPlayer(player1);
        player2.setNickname("B");
        playersList.addPlayer(player2);
        player3.setNickname("C");
        playersList.addPlayer(player3);






    }

    /**
     * arbitrary way to get the game instance,
     * @return a reference to the game
     * @throws IOException that may occur while initializing the model
     * */
     static Game getFakeModel() throws IOException {

       return (new FakeModel()).create();
    }


}
