package it.polimi.se2019.view.components;


import java.io.Serializable;
/**equivalent view class of Game class in the model
 *  @author FedericoMainettiGambera
 * @author LudoLerma*/
public class GameV implements Serializable {
    private KillShotTrackV killshotTrack;

    private PlayersListV players;

    private OrderedCardListV<PowerUpCardV> powerUpDeck;

    private OrderedCardListV<WeaponCardV> weaponDeck;

    private OrderedCardListV<AmmoCardV> ammoDeck;

    private OrderedCardListV<PowerUpCardV> powerUpDiscardPile;

    private OrderedCardListV<AmmoCardV> ammoDiscardPile;

    private boolean hasFinalFrenzyBegun;

    private boolean isFinalFrenzy;

    private BoardV board;

    private boolean isBotActive;

    public boolean isBotActive() {
        return isBotActive;
    }

    public void setBotActive(boolean isBotActive){
        this.isBotActive = isBotActive;
    }

    public PlayerV getMe() throws Exception {
        for (PlayerV p: this.players.getPlayers()) {
            if(p.getNickname().equals(ViewModelGate.getMe())){
                return p;
            }
        }
        throw new Exception("Your Player doesn't exist in the PlayerList");
    }

    public void setPowerUpDiscardPile(OrderedCardListV<PowerUpCardV> powerUpDiscardPile) {
        this.powerUpDiscardPile = powerUpDiscardPile;
    }

    public void setAmmoDiscardPile(OrderedCardListV<AmmoCardV> ammoDiscardPile) {
        this.ammoDiscardPile = ammoDiscardPile;
    }

    public void setWeaponDeck(OrderedCardListV<WeaponCardV> weaponDeck) {
        this.weaponDeck = weaponDeck;
    }

    public void setAmmoDeck(OrderedCardListV<AmmoCardV> ammoDeck) {
        this.ammoDeck = ammoDeck;
    }

    public void setBoard(BoardV board) {
        this.board = board;
    }

    public void setPowerUpDeck(OrderedCardListV<PowerUpCardV> powerUpDeck) {
        this.powerUpDeck = powerUpDeck;
    }

    public void setPlayers(PlayersListV players) {
        this.players = players;
    }

    public void setKillshotTrack(KillShotTrackV killshotTrack) {
        this.killshotTrack = killshotTrack;
    }

    public OrderedCardListV<AmmoCardV> getAmmoDeck() {
        return ammoDeck;
    }

    public OrderedCardListV<AmmoCardV> getAmmoDiscardPile() {
        return ammoDiscardPile;
    }

    public OrderedCardListV<PowerUpCardV> getPowerUpDeck() {
        return powerUpDeck;
    }

    public KillShotTrackV getKillshotTrack() {
        return killshotTrack;
    }

    public boolean isFinalFrenzy() {
        return isFinalFrenzy;
    }

    public boolean isHasFinalFrenzyBegun() {
        return hasFinalFrenzyBegun;
    }

    public BoardV getBoard() {
        return board;
    }

    public OrderedCardListV<PowerUpCardV> getPowerUpDiscardPile() {
        return powerUpDiscardPile;
    }

    public void setHasFinalFrenzyBegun(boolean hasFinalFrenzyBegun) {
        this.hasFinalFrenzyBegun = hasFinalFrenzyBegun;
    }

    public void setFinalFrenzy(boolean finalFrenzy) {
        isFinalFrenzy = finalFrenzy;
    }

    public OrderedCardListV<WeaponCardV> getWeaponDeck() {
        return weaponDeck;
    }

    public PlayersListV getPlayers() {
        return players;
    }
}
