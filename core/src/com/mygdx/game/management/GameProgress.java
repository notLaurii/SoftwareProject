package com.mygdx.game.management;

import com.mygdx.game.weapons.Weapon;

import java.util.List;
//Speichert Attribute der Spielstände
public class GameProgress {
    private int playerId;
    private int level;
    private List<String> abilitiesUnlocked;
    private List<String> weaponsUnlocked;
    private List<String> skinsUnlocked;

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

    public List<String> getAbilitiesUnlocked() {
        return abilitiesUnlocked;
    }

    public void setAbilitiesUnlocked(List<String> skin) {
        this.abilitiesUnlocked = abilitiesUnlocked;
    }

    public List<String> getWeaponsUnlocked() {
        return weaponsUnlocked;
    }

    public void setWeaponsUnlocked(List<String> weaponsUnlocked) {
        this.weaponsUnlocked = weaponsUnlocked;
    }

    public List<String> getSkinsUnlocked() {
        return skinsUnlocked;
    }

    public void setSkinsUnlocked(List<String> skinsUnlocked) {
        this.skinsUnlocked = skinsUnlocked;
    }

}

