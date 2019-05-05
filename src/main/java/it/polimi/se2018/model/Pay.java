package it.polimi.se2018.model;


/***/
public class Pay extends Action {

    /***/
    @Override
    public void updateSettingsFromFile() {
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
            return;
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
        /*AmmoCubes retVal = new AmmoCubes()
        getActionInfo().getActionDetails().getFileSelectedActionDetails().setAmmoCubesCost(


        );*/
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