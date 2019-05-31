package it.polimi.se2019.view.components;

import java.io.Serializable;
import java.util.List;

public class OrderedCardListV<T> implements Serializable {

    private List<T> cards;

    private String context;

    public void setContext(String context) {
        this.context = context;
    }

    public String getContext() {
        return context;
    }

    public List<T> getCards() {
        return cards;
    }

    public void setCards(List<T> cards) {
        this.cards = cards;
    }

    @Override
    public String toString(){
        String s = "";
        if(((!this.getContext().split(":")[0].equals(ViewModelGate.getMe())) && (this.getContext().contains(":powerUpInHand")))
                || (this.getContext().equals("weaponDeck")) || (this.getContext().equals("ammoDeck")) || (this.getContext().equals("powerUpDeck"))) {
            //don't show power ups other player's hand, or the decks
            s = "              Can't show " + this.getContext() + " to you.";
            return s;
        }
        else{
            for (Object c : cards) {
                if(c.getClass().toString().contains("PowerUpCardV")){
                    PowerUpCardV card = (PowerUpCardV)c;
                    s+= "              " + card.getName() + "/" + card.getColor() + ": " + card.getDescription() + "\n";
                }
                else if(c.getClass().toString().contains("WeaponCardV")){
                    WeaponCardV card = (WeaponCardV)c;
                    s+= "              " + card.getName() + ": " + card.getDescription() + ". " + card.getReloadCost().toString() + "\n";
                }
                else{
                    AmmoCardV card = (AmmoCardV)c;
                    s+= "              " + card.getAmmoList().toString();
                    if(card.isPowerUp()){
                        s+= ". Draw a power up";
                    }
                    s+="\n";
                }
            }
            return s;
        }
    }
}
