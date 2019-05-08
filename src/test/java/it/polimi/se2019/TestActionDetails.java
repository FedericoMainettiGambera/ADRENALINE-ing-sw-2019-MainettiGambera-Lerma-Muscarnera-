package it.polimi.se2019;

import it.polimi.se2019.model.ActionDetails;
import org.junit.Test;

public class TestActionDetails {
    @Test
    public void testActionDetails() {
        ActionDetails A = new ActionDetails();
    }
    @Test
    public void testGetters() {
        ActionDetails A = new ActionDetails();
        A.getUserSelectedActionDetails();
        A.getFileSelectedActionDetails();

    }
}
