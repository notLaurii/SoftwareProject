package com.mygdx.game.management;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.Player;
import com.mygdx.game.interfaces.Inventory;
import com.mygdx.game.weapons.Weapon;

import java.util.ArrayList;

import static com.mygdx.game.management.MyGdxGame.*;

public class GameManager {
    private int level;
    private int room;
    private boolean gameRunning = true;
    private Inventory inventory;
    private ArrayList<String> allAbilities;
    private ArrayList<String> allWeapons;
    private ArrayList<String> allSkins;
    private ArrayList<String> unlockedAbilities;
    private ArrayList<String> unlockedWeapons;
    private ArrayList<String> unlockedSkins;

    public GameManager(int playerId, int level, ArrayList<String> allAbilities, ArrayList<String> allWeapons, ArrayList<String> allSkins, ArrayList<String> unlockedAbilities, ArrayList<String> unlockedWeapons, ArrayList<String> unlockedSkins) {
        this.level=level;
        this.allAbilities=allAbilities;
        this.allWeapons=allWeapons;
        this.allSkins=allSkins;
        this.unlockedAbilities=unlockedAbilities;
        this.unlockedWeapons=unlockedWeapons;
        this.unlockedSkins=unlockedSkins;
    }

    public void update(float deltaTime) {
        Player player = levelManager.getPlayer();
        if(Gdx.input.isKeyJustPressed(Input.Keys.I)||Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (inventory != null) {
                gameRunning = true;
                inventory = null;
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
                if (gameRunning) {
                    inventory = new Inventory();
                    inventory.create();
                    gameRunning = false;
                }
            }
        }

        if(Math.ceil(player.getX()+player.getSpeed() * deltaTime)>=(gameMap.getPixelWidth()-player.getWidth()))
            if(levelManager.isEntitiesCreated()&&levelManager.noEnemiesLeft()) {
                if (level == room) {
                    System.out.println(level + "; " + room);
                    setLevel(level + 1);
                }
                if (level == room+1 || room == 0) {
                    FileHandle folder = Gdx.files.internal("Map/Level" + level);
                    gameSaver.savePlayerData(player);
                    gameSaver.saveGameProgress(player);
                    if (folder.exists())
                        levelManager.switchLevel();
                    else {
                        goToMainRoom();
                    }
                }
            }
    }

    public void goToMainRoom() {
        room=0;
        levelManager.switchLevel(room);
    }

    public int getLevel() {
        return level;
    }
    public void setLevel(int amount) {
        level=amount;
    }

    public void render(OrthographicCamera cam, SpriteBatch batch) {
        gameMap.render(cam, batch);
        if(!gameRunning) {
            inventory.render();
        }
    }

    public int getRoom() {
        return room;
    }
    public void setRoom(int i) {
        room=i;
    }
    public boolean isGameRunning() {
        return gameRunning;
    }

    public void setGameRunning(boolean b) { gameRunning=b;
    }

    public ArrayList<String> getAllAbilities() {
        return allAbilities;
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

    public ArrayList<String> getUnlockedAbilities() {
        return unlockedAbilities;
    }

    public void setUnlockedAbilities(ArrayList<String> unlockedAbilities) {
        this.unlockedAbilities = unlockedAbilities;
    }
    public ArrayList<String> getUnlockedWeapons() {
        return unlockedWeapons;
    }

    public void setUnlockedWeapons(ArrayList<String> unlockedWeapons) {
        this.unlockedWeapons = unlockedWeapons;
    }

    public ArrayList<String> getUnlockedSkins() {
        return unlockedSkins;
    }

    public void setUnlockedSkins(ArrayList<String> unlockedSkins) {
        this.unlockedSkins = unlockedSkins;
    }

    public Inventory getInventory() {
        return inventory;
    }
    public void updateInventory() {
        inventory=new Inventory();
        inventory.create();
    }
}
