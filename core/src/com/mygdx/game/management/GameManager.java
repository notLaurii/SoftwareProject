package com.mygdx.game.management;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.Player;
import com.mygdx.game.interfaces.GameEndScreen;
import com.mygdx.game.interfaces.Interface;
import com.mygdx.game.interfaces.Inventory;
import com.mygdx.game.interfaces.LevelEndScreen;
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
    private ArrayList<String> purchasableAbilities;
    private ArrayList<String> purchasableWeapons;
    private ArrayList<String> purchasableSkins;
    private ArrayList<String> unlockedAbilities;
    private ArrayList<String> unlockedWeapons;
    private ArrayList<String> unlockedSkins;
    private ArrayList<Interface> openInterfaces=new ArrayList<>();
    private InputManager inputManager;

    public GameManager(int playerId, int level, ArrayList<String> allAbilities, ArrayList<String> allWeapons, ArrayList<String> allSkins, ArrayList<String> purchasableAbilities, ArrayList<String> purchasableWeapons, ArrayList<String> purchasableSkins,ArrayList<String> unlockedAbilities, ArrayList<String> unlockedWeapons, ArrayList<String> unlockedSkins) {
        this.level=level;
        this.allAbilities=allAbilities;
        this.allWeapons=allWeapons;
        this.allSkins=allSkins;
        this.purchasableAbilities=purchasableAbilities;
        this.purchasableWeapons=purchasableWeapons;
        this.purchasableSkins=purchasableSkins;
        this.unlockedAbilities=unlockedAbilities;
        this.unlockedWeapons=unlockedWeapons;
        this.unlockedSkins=unlockedSkins;
        this.inputManager=new InputManager();
    }

    public void update(float deltaTime) {//kümmert sich um die offenen Interfaces und hält das Spiel an, falls eines offen ist. Managed das erreichte Level und speichert das Spiel beim Weitergehen ins nächste Level.
        Player player = levelManager.getPlayer();
        if(!openInterfaces.contains(inventory)) {
            inventory = null;
        }
        if(openInterfaces.isEmpty())
            gameRunning = true;
        if(gameRunning)
            if (Math.ceil(player.getX()+player.getSpeed() * deltaTime)>=(gameMap.getPixelWidth()-player.getWidth())) {
                if (levelManager.isEntitiesCreated() && levelManager.noEnemiesLeft()) {
                    if (level == room) {
                        setLevel(level + 1);
                    }
                    if (level == room + 1 || room == 0) {
                        if (level == 2) {
                            purchasableWeapons.add("donut");
                        }
                        FileHandle folder = Gdx.files.internal("Map/Level" + level);
                        gameSaver.savePlayerData(player);
                        gameSaver.saveGameProgress(player);
                        if (folder.exists())
                            new LevelEndScreen();
                        else
                            new GameEndScreen();
                        setGameRunning(false);
                    }
                }
            }
        inputManager.update(deltaTime);
    }

    public int getLevel() {
        return level;
    }
    public void setLevel(int amount) {
        level=amount;
    }

    public void render(OrthographicCamera cam, SpriteBatch batch) {//rendert die offenen Interfaces
        gameMap.render(cam, batch);
        for(Interface openInterface : openInterfaces) {
            openInterface.render();
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

    public ArrayList<String> getAllWeapons() {
        return allWeapons;
    }

    public ArrayList<String> getAllSkins() {
        return allSkins;
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

    public void setUnlockedWeapons(ArrayList<String> unlockedWeapons) {
        this.unlockedWeapons = unlockedWeapons;
    }

    public ArrayList<String> getUnlockedSkins() {
        return unlockedSkins;
    }

    public void setUnlockedSkins(ArrayList<String> unlockedSkins) {
        this.unlockedSkins = unlockedSkins;
    }
    public ArrayList<Interface> getOpenInterfaces() {
        return openInterfaces;
    }

    public void setOpenInterfaces(ArrayList<Interface> openInterfaces) {
        this.openInterfaces = openInterfaces;
    }

    public Inventory getInventory() {
        return inventory;
    }
    public void updateInventory() {//updatet das Inventar nach einer Veränderung
        openInterfaces.remove(inventory);
        inventory=new Inventory();
        inventory.create();
    }

    public void setInventory(Inventory inventory) { this.inventory=inventory;
    }

    public boolean getGameRunning() {
        return gameRunning;
    }
}
