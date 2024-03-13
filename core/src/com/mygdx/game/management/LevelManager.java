package com.mygdx.game.management;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityData;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.Tnt;
import com.mygdx.game.entities.enemies.Elf;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.enemies.Slime;
import com.mygdx.game.entities.items.Gold;
import com.mygdx.game.entities.projectiles.BoomerangProjectile;
import com.mygdx.game.world.TiledGameMap;

import java.util.ArrayList;

import static com.mygdx.game.management.MyGdxGame.*;

public class LevelManager{
    public ArrayList<Entity> entities;
    public ArrayList<Entity> entitiesToRemove = new ArrayList<>();
    public ArrayList<Entity> entitiesToAdd = new ArrayList<>();
    private Player player;
    private boolean entitiesCreated=false;
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
                        for(int i = 0; i<((Enemy) entity).getGoldDrop();i++) {
                            if(((Enemy) entity).getGoldDrop()<=1) {
                                entitiesToAdd.add(new Gold(entity.getX(), entity.getY() + 20, gameMap));
                            } else entitiesToAdd.add(new Gold(entity.getX() + randomNumberGenerator(-17,17), entity.getY() + 20, gameMap));
                        }
                    }
                }
                if(entity.isEntityInRange(player,5,5)) {
                    if (entity instanceof Gold) {
                        entitiesToRemove.add(entity);
                        player.changeGold(+1);
                    }
                }
            }

            // Entferne die Entitäten aus der Liste nach der Iteration
            entities.removeAll(entitiesToRemove);
            entities.addAll(entitiesToAdd);
            entitiesToRemove.removeAll(entitiesToRemove);
            entitiesToAdd.removeAll(entitiesToAdd);
            player.getWeapon().update(deltaTime);
        }
    }
    public void render(OrthographicCamera camera, SpriteBatch batch) {
        for(Entity entity : entities) {
            entity.render(batch);
        }
    }

    public void switchLevel() {
        gameManager.setRoom(gameManager.getLevel());
        create();
        gameMap = new TiledGameMap();
    }

    public void switchLevel(int level) {
        gameManager.setRoom(level);
        create();
        gameMap = new TiledGameMap();
    }

    public boolean noEnemiesLeft() {
        for(Entity entity : entities)
            if (entity.getCategory().equals("enemy"))
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
            return new Player(entityData.getId(), entityData.getX(), entityData.getY(), gameMap, entityData.getMaxHealth(), entityData.getHealth(), entityData.getAttackDamage(), entityData.getSpeed(), entityData.getJumpVelocity(), entityData.getWeaponID(), entityData.getSkin());
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
}
