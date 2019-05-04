package it.polimi.se2018.model;


/***/
public class Pay extends Action {

    /***/
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