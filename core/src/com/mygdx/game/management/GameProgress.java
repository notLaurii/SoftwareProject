package com.mygdx.game.management;

import com.mygdx.game.weapons.Weapon;

import java.util.ArrayList;
import java.util.List;
//Speichert Attribute der Spielstände
public class GameProgress {
    private int playerId;
    private int level;
    private ArrayList<String> allAbilities;
    private ArrayList<String> allWeapons;
    private ArrayList<String> allSkins;
    private ArrayList<String> purchasableAbilities;
    private ArrayList<String> purchasableWeapons;
    private ArrayList<String> purchasableSkins;
    private ArrayList<String> unlockedAbilities;
    private ArrayList<String> unlockedWeapons;
    private ArrayList<String> unlockedSkins;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArrayList<String> getAllAbilities() {
        return allWeapons;
    }

    public void setAllAbilities(ArrayList<String> allAbilities) {
        this.allAbilities = allAbilities;
    }
    public ArrayList<String> getAllWeapons() {
        return allWeapons;
    }

    public void setAllWeapons(ArrayList<String> allWeapons) {
        this.allWeapons = allWeapons;
    }

    public ArrayList<String> getAllSkins() {
        return allSkins;
    }

    public void setAllSkins(ArrayList<String> allSkins) {
        this.allSkins = allSkins;
    }
    public ArrayList<String> getPurchasableAbilities() {
        return purchasableAbilities;
    }

    public void setPurchasableAbilities(ArrayList<String> purchasableAbilities) {
        this.purchasableAbilities = purchasableAbilities;
    }
    public ArrayList<String> getPurchasableWeapons() {
        return purchasableWeapons;
    }

    public void setPurchasableWeapons(ArrayList<String> purchasableWeapons) {
        this.purchasableWeapons = purchasableWeapons;
    }

    public ArrayList<String> getPurchasableSkins() {
        return purchasableSkins;
    }

    public void setPurchasableSkins(ArrayList<String> purchasableSkins) {
        this.purchasableSkins = purchasableSkins;
    }

    public ArrayList<String> getUnlockedAbilities() {
        return unlockedAbilities;
    }

    public void setUnlockedAbilities(ArrayList<String> unlockedAbilities) {
        this.unlockedAbilities = unlockedAbilities;
    }

    public ArrayList<String> getUnlockedWeapons() {
        return unlockedWeapons;
    }

    public void setUnlockedWeapons(ArrayList<String> unlockedWeapons) {this.unlockedWeapons = unlockedWeapons;}

    public ArrayList<String> getUnlockedSkins() {
        return unlockedSkins;
    }

    public void setUnlockedSkins(ArrayList<String> unlockedSkins) {
        this.unlockedSkins = unlockedSkins;
    }

}

