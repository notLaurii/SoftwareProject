package com.mygdx.game.management;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.entities.*;
import com.mygdx.game.entities.enemies.*;
import com.mygdx.game.entities.items.Gold;
import com.mygdx.game.entities.projectiles.BoomerangProjectile;
import com.mygdx.game.entities.projectiles.PearlProjectile;
import com.mygdx.game.interfaces.DeathScreen;
import com.mygdx.game.world.TiledGameMap;

import java.util.ArrayList;

import static com.mygdx.game.management.MyGdxGame.*;

public class LevelManager{
    private ArrayList<Entity> entities;
    public ArrayList<Entity> entitiesToRemove = new ArrayList<>();
    public ArrayList<Entity> entitiesToAdd = new ArrayList<>();
    private Player player;
    private boolean entitiesCreated=false;

    private boolean gameOver=false;
    private boolean pearlIsOnMap = false;
    public LevelManager() {
    }

    public void create() {
        entities = new ArrayList<Entity>();
        try {
            loadEntitiesFromJson("playerData.json", false);
        } catch (Exception e){
            loadEntitiesFromJson("defaultPlayerData.json", true);
        }
        player=(Player) entities.get(0);
        loadEntitiesFromJson("Map/Level"+ gameManager.getRoom()+"/entities.json", true);
        entitiesCreated=true;
    }

    public void update(float deltaTime) {
        if (gameManager.isGameRunning()) {
            for (Entity entity : entities) {
                entity.update(deltaTime, 9.81f);

                // Überprüfe, ob die Gesundheit null ist und füge sie zur Liste der zu entfernenden Entitäten hinzu
                if (entity.getHealth() <= 0 && entity.getHealth() < entity.getMaxHealth()) {
                    entitiesToRemove.add(entity);
                    if(entity instanceof Enemy) {
                                entitiesToAdd.add(new Gold(entity.getX(), entity.getY() + 20, gameMap, ((Enemy) entity).getGoldDrop()));
                            }
                }
                if(entity.isEntityInRange(player,5,5)) {
                    if (entity instanceof Gold) {
                        entitiesToRemove.add(entity);
                        player.changeGoldAmount(((Gold) entity).getValue());
                    }
                }
            }
            for(Entity entityToRemove : entitiesToRemove) {
                if (entityToRemove instanceof PearlProjectile) pearlIsOnMap = false;
            }
            for (Entity entityToAdd : entitiesToAdd) {
                if (entityToAdd instanceof PearlProjectile) pearlIsOnMap = true;
            }

            // Entferne die Entitäten aus der Liste nach der Iteration
            entities.removeAll(entitiesToRemove);
            entities.addAll(entitiesToAdd);
            entitiesToRemove.removeAll(entitiesToRemove);
            entitiesToAdd.removeAll(entitiesToAdd);
            player.getWeapon().update(deltaTime);
        }
        checkGameOver();
    }
    public void render(OrthographicCamera camera, SpriteBatch batch) {
        for(Entity entity : entities) {
            entity.render(batch);
        }
    }

    public void checkGameOver() {
        if(player.getHealth()<=0&&!gameOver) {
            new DeathScreen();
            gameOver=true;
        }
    }
    public void switchRoom() {
        gameManager.setRoom(gameManager.getLevel());
        create();
        gameMap = new TiledGameMap();
    }

    public void switchRoom(int room) {
        gameManager.setRoom(room);
        create();
        gameMap = new TiledGameMap();
    }

    public boolean noEnemiesLeft() {
        for(Entity entity : entities)
            if (entity instanceof Enemy)
                return false;
        return true;
    }

    protected void loadEntitiesFromJson(String jsonFilePath, boolean internal) {
        Json json = new Json();
        ArrayList<EntityData> entityDataList = json.fromJson(ArrayList.class, EntityData.class, Gdx.files.local(jsonFilePath));
        if(internal)
            entityDataList = json.fromJson(ArrayList.class, EntityData.class, Gdx.files.internal(jsonFilePath));
        for (EntityData entityData : entityDataList) {
            entities.add(createEntityFromData(entityData));
        }
    }
    private Entity createEntityFromData(EntityData entityData) {
        if ("Player".equals(entityData.getType())) {
            return new Player(entityData.getId(), entityData.getX(), entityData.getY(), gameMap, entityData.getMaxHealth(), entityData.getHealth(), entityData.getAttackDamage(), entityData.getSpeed(), entityData.getJumpVelocity(), entityData.getWeaponID(), entityData.getSkin(), entityData.getGoldAmount());
        } else if ("Slime".equals(entityData.getType())) {
            return new Slime(entityData.getX(), entityData.getY(), gameMap, entityData.getMaxHealth(), entityData.getAttackDamage(), entityData.getSpeed(), entityData.getJumpVelocity(), entityData.getWeaponID());
        }
        else if ("Boomerang".equals(entityData.getType())) {
            return new BoomerangProjectile(gameMap, player, 5);
        } else if ("Elf".equals(entityData.getType())) {
            return new Elf(entityData.getX(), entityData.getY(), gameMap, entityData.getMaxHealth(), entityData.getAttackDamage(), entityData.getSpeed(), entityData.getJumpVelocity(), entityData.getWeaponID());
        }
        else if ("Tnt".equals(entityData.getType())) {
            return new Tnt(entityData.getX(), entityData.getY(), gameMap);
        }
        else if ("Shop".equals(entityData.getType())) {
            return new ShopOwner(entityData.getX(), entityData.getY(), gameMap);
        } else if ("Magican".equals(entityData.getType())) {
            return new Magican(entityData.getX(), entityData.getY(), gameMap, entityData.getMaxHealth(), entityData.getJumpVelocity());
        } else if("Bird".equals(entityData.getType())) {
            return  new Bird((entityData.getX()), entityData.getY(), gameMap, entityData.getMaxHealth());
        }
        return null;
    }
    public int randomNumberGenerator (int low, int high) {
        double doubleRandomNumber = Math.random()*high;
        int randomNumber = (int)doubleRandomNumber+low;
        return randomNumber;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isEntitiesCreated() {
        return entitiesCreated;
    }
    public void setEntitiesCreated(boolean created) {
        entitiesCreated=created;
    }
    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public ArrayList getEntitiesToAdd() {
        return entitiesToAdd;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    public boolean pearlIsOnMap() {
        return pearlIsOnMap;
    }
}
