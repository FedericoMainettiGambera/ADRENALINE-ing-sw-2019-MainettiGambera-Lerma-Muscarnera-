package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.GrabStuffState;
import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventString;
import org.junit.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGrabStaffState{

    private FakeModel fakeModel = new FakeModel();

    @Test
    public void testGrabStaffState() throws IOException{

        ModelGate.setModel(fakeModel.create());
            ViewControllerEventString viewControllerEventString=  new ViewControllerEventString("palla");

            GrabStuffState state=new GrabStuffState(1);

            String choice=state.readInput(viewControllerEventString);

            assertEquals("palla", choice);
    }
}
