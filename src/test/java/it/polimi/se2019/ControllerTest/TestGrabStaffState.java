package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.GrabStuffState;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventString;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**tests the functions that implements game logic in GrabStuffState*/
public class TestGrabStaffState{


    /**since the function in this state is private, it is tested with reflection
     * @throws IOException may occur while creating the fake model
     * @throws NoSuchMethodException may occur while trying to access by reflection
     * @throws InvocationTargetException may occur while trying to invoke a private function
     * @throws IllegalAccessException
     * may occur while trying to invoke a private function
     * */
    @Test
    public void testGrabStaffState() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        ModelGate.setModel(new FakeModel().create());
            ViewControllerEventString viewControllerEventString=  new ViewControllerEventString("palla");

            GrabStuffState state=new GrabStuffState(1);

           java.lang.reflect.Method readInput= GrabStuffState.class.getDeclaredMethod("readInput", ViewControllerEvent.class);
           readInput.setAccessible(true);
           String choice=(String)readInput.invoke(state,viewControllerEventString);

            assertEquals("palla", choice);
    }
}
