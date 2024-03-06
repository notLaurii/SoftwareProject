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

    private ArrayList<String> unlockedWeapons;
    private ArrayList<String> unlockedSkins= new ArrayList<>();

    public GameManager(int playerId, int level, ArrayList<String> unlockedWeapons, ArrayList<String> unlockedSkins) {
        this.level=level;
        this.unlockedWeapons=unlockedWeapons;
        this.unlockedSkins=unlockedSkins;
    }

    public void update(float deltaTime) {
        Player player = levelManager.getPlayer();
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gameRunning = !gameRunning;
            if(!gameRunning) {
                inventory= new Inventory();
                inventory.create();
            }
        }
        if(Math.ceil(player.getX())>=(gameMap.getPixelWidth()-player.getWidth()))
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
