package it.polimi.se2018;

import it.polimi.se2018.model.Action;
import it.polimi.se2018.model.ActionInfo;
import it.polimi.se2018.model.Damage;
import org.junit.Test;

public class TestAction {
    @Test
    public void testAction() {
        Action a = new Damage();

    }
    @Test
    public void testActionSecondConstructor() {
        Action a = new Damage(new ActionInfo());

    }
    @Test
    public void testGetters() {
        Action a = new Damage();
        a.getActionInfo();
    }
    @Test
    public void testSetters() {
        Action a = new Damage();
        String b = "12";
        a.getActionInfo().getActionDetails().getFileSelectedActionDetails().addFileSettingData((Object) b);
        a.updateSettingsFromFile();
        a.setDefaultSetting();
        a.setActionInfo(new ActionInfo());

    }
}
