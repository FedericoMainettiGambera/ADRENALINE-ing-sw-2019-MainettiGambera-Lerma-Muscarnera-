package it.polimi.se2019.model;

import java.io.Serializable;

/**This class represents the current status of a player's hand.
 * It holds power ups and weapons.
 * @author FedericoMainettiGambera
 * @author LudoLerma */
public class PlayerHand implements Serializable {

    /*-****************************************************************************************************CONSTRUCTOR*/



    /**a string containing the nickname of the player*/
    private String nickname;
    /**@return  nickname*/
    public String getNickname() {
        return nickname;
    }
    /**@param nickname  to set the nickname attribute*/
    public void setNickname(String nickname) {
        this.nickname=nickname;
        this.powerUpCards.setContext(nickname+":powerUpInHand");
        this.weaponCards.setContext(nickname+":weaponInHand");
    }
    /**Constructor:
     * create two OrderedCardList, one for the powerUps and the other for the weapons
     * */
    public PlayerHand() {
        powerUpCards = new OrderedCardList<>(nickname+":powerUpInHand");
        weaponCards = new OrderedCardList<>(nickname+":weaponInHand");
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /**power up cards in player's hand*/
    private OrderedCardList<PowerUpCard> powerUpCards;

    /**weapon cards in player's hand*/
    private OrderedCardList<WeaponCard> weaponCards;

    /*-********************************************************************************************************METHODS*/
    /**@return ordered card list of weapon cards
     * */
    public OrderedCardList<WeaponCard> getWeaponCards() {
        return weaponCards;
    }
    /**@return a list of the weapon cards usable */
    public OrderedCardList<WeaponCard> usableWeapons() {
        OrderedCardList<WeaponCard> retVal = new OrderedCardList<WeaponCard>(null);

        for(WeaponCard w: getWeaponCards().getCards()) {
            System.out.println("testo carta " + w.getName());
            if(w. usableEffects().size() > 0)
            {

                retVal.addCard(w);
            }

        }

        return retVal;

    }

    /**@return ordered card list of power up cards
     * */
    public OrderedCardList<PowerUpCard> getPowerUpCards() {
        return powerUpCards;
    }

    /**@return the specified card in the player's hand or null if the card doesn't exist
     * */
    public WeaponCard getWeaponCard(String ID){
        return getWeaponCards().getCard(ID);
    }

    /**@return the specified card in the player's hand or null if the card doesn't exist
     * */
    public PowerUpCard getPowerUpCard(String ID){
        return getPowerUpCards().getCard(ID);
    }

}