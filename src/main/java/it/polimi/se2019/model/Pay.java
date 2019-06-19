package it.polimi.se2019.model;


import java.io.Serializable;

/***/
@Deprecated
public class Pay extends Action implements Serializable { //TODO DELETE THIS CLASS??

    /***/
    @Override
    public void setDefaultSetting() {

        return;
    }
    public void updateSettingsFromFile() {
        setDefaultSetting();
        Object a = getActionInfo().getActionDetails().getFileSelectedActionDetails().getFileSettingData().get(0);
        Object b = getActionInfo().getActionDetails().getFileSelectedActionDetails().getFileSettingData().get(0);
        String  a_i = "";
        Integer b_i = 0;

        if(ActionInfo.notNullAndNotDefault(a)) {
            try {
                a_i = (String) a;
            } catch(Exception e) {
                return;
            }
        } else {
            return;                                 // this is linked with the next: if this is not defined the next one makes not sense
            }
        if(ActionInfo.notNullAndNotDefault(b)) {
            try {
                b_i = (Integer) a;
            } catch(Exception e) {
                return;
            }
        } else {
            return;
        }
     //  if(b == null) return;
      //  AmmoCubes retVal = new AmmoCubes();
       // getActionInfo().getActionDetails().getFileSelectedActionDetails().setAmmoCubesCost(


    //    ); TODO: convert a_i from string to enum (Color)
    }

    public Pay() {
        super();
        getActionInfo().setPreConditionMethodName("paymentValid");
    }
    public Pay(ActionInfo actionInfo) {
        super(actionInfo);

    }

    /***/
    public void Exec() {
    AmmoCubes ammoCost = getActionInfo().getActionDetails().getFileSelectedActionDetails().getAmmoCubesCost();
    Player player = getActionInfo().getActionContext().getPlayer();
    getActionInfo().getActionDetails().getUserSelectedActionDetails().itNeeds(
            ammoCost
    );

    player.payAmmoCubes(
            ammoCost.getColor(),
            ammoCost.getQuantity()
    );
    }

}