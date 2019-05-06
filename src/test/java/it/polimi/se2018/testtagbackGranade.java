package it.polimi.se2018;

import it.polimi.se2018.model.Action;
import it.polimi.se2018.model.ActionInfo;
import it.polimi.se2018.model.tagbackGranade;
import org.junit.Test;

public class testtagbackGranade {
    @Test
    public void test_testbackgrande() {
        Action T = new tagbackGranade(new ActionInfo());
        T.Exec();

    }
}
