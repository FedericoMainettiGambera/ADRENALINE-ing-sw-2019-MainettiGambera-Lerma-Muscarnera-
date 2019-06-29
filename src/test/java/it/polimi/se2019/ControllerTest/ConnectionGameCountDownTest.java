/*package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.ConnectionGameCountDown;
import org.junit.Test;

import java.io.IOException;



public class ConnectionGameCountDownTest{



    private ConnectionGameCountDown connectionGameCountDown=new ConnectionGameCountDown(1);

    /**verifies the methods from connectionGameCountDown class
     we can't verify the network, so since this class launchs a thread that counts to ten before initializing the game,
     * the expected behaviour of this test is a count down from 0 to 10 printed on screen and a final statement on the number of connection*/
   /* @Test
    public void connectionGameTest() throws IOException {

        ViewControllerEventHandlerContext.socketVV =null;
        ViewControllerEventHandlerContext.RMIVV =null;
        ModelGate.model.setVirtualView(ViewControllerEventHandlerContext.socketVV, ViewControllerEventHandlerContext.RMIVV);

        ModelGate.model=(new FakeModel()).create();

        connectionGameCountDown.run();

        ConnectionGameCountDown connectionGameCountDown=new ConnectionGameCountDown(0);
        connectionGameCountDown.run();


    }
}
*/