package it.polimi.se2019.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FileSelectedActionDetails implements Serializable {
    List<Object> fileSettingData;       // lista dei dati inseriti via file
    public FileSelectedActionDetails() {
        fileSettingData = new ArrayList<Object>();
    }
    public void addFileSettingData(Object o) {
        fileSettingData.add(o);
    }
    public List<Object> getFileSettingData() {
        return fileSettingData;
    }
    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    private int damage;             // specified in card file

    public int getTargetsQuantity() {
        return targetsQuantity;
    }

    public void setTargetsQuantity(int targetsQuantity) {
        this.targetsQuantity = targetsQuantity;
    }

    private int targetsQuantity;

    public int getSquareMovement() {
        return squareMovement;
    }

    public void setSquareMovement(int squareMovement) {
        this.squareMovement = squareMovement;
    }

    private int squareMovement;                             // maximum movements possible


    public AmmoCubes getAmmoCubesCost() {
        return ammoCubesCost;
    }

    public void setAmmoCubesCost(AmmoCubes ammoCubesCost) {
        this.ammoCubesCost = ammoCubesCost;
    }

    private AmmoCubes ammoCubesCost;


    public int getMarksQuantity() {
        return marksQuantity;
    }

    public void setMarksQuantity(int marksQuantity) {
        this.marksQuantity = marksQuantity;
    }

    private int marksQuantity;

}