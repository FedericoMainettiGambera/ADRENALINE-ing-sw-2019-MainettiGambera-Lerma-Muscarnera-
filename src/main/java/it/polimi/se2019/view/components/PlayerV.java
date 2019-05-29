package it.polimi.se2019.view.components;

import it.polimi.se2019.model.enumerations.PlayersColors;

import java.io.Serializable;

public class PlayerV implements Serializable {

    private OrderedCardListV<WeaponCardV> weaponCardInHand;

    private OrderedCardListV<PowerUpCardV> powerUpCardInHand;

    private String nickname;

    private boolean hasFinalFrenzyBoard;

    private PlayersColors color;

    private int score;

    private DamageTrackerV damageTracker;

    private MarksTrackerV marksTracker;

    private AmmoListV ammoBox;

    private int X;

    private int Y;

    public int getY() {
        return Y;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public void setY(int y) {
        Y = y;
    }

    public AmmoListV getAmmoBox() {
        return ammoBox;
    }

    public boolean isHasFinalFrenzyBoard() {
        return hasFinalFrenzyBoard;
    }

    public DamageTrackerV getDamageTracker() {
        return damageTracker;
    }

    public int getScore() {
        return score;
    }

    public MarksTrackerV getMarksTracker() {
        return marksTracker;
    }

    public OrderedCardListV<PowerUpCardV> getPowerUpCardInHand() {
        return powerUpCardInHand;
    }

    public OrderedCardListV<WeaponCardV> getWeaponCardInHand() {
        return weaponCardInHand;
    }

    public PlayersColors getColor() {
        return color;
    }

    public String getNickname() {
        return nickname;
    }

    public void setColor(PlayersColors color) {
        this.color = color;
    }

    public void setAmmoBox(AmmoListV ammoBox) {
        this.ammoBox = ammoBox;
    }

    public void setDamageTracker(DamageTrackerV damageTracker) {
        this.damageTracker = damageTracker;
    }

    public void setHasFinalFrenzyBoard(boolean hasFinalFrenzyBoard) {
        this.hasFinalFrenzyBoard = hasFinalFrenzyBoard;
    }

    public void setMarksTracker(MarksTrackerV marksTracker) {
        this.marksTracker = marksTracker;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPowerUpCardInHand(OrderedCardListV<PowerUpCardV> powerUpCardInHand) {
        this.powerUpCardInHand = powerUpCardInHand;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setWeaponCardInHand(OrderedCardListV<WeaponCardV> weaponCardInHand) {
        this.weaponCardInHand = weaponCardInHand;
    }
}
