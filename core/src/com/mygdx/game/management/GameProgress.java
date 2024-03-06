package com.mygdx.game.management;

import com.mygdx.game.weapons.Weapon;

import java.util.ArrayList;
import java.util.List;
//Speichert Attribute der Spielstände
public class GameProgress {
    private int playerId;
    private int level;
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

