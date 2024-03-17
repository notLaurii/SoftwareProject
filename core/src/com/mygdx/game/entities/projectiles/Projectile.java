package com.mygdx.game.entities.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityType;
import com.mygdx.game.entities.Player;
import com.mygdx.game.management.LevelManager;
import com.mygdx.game.world.GameMap;

import java.util.ArrayList;

import static com.mygdx.game.management.MyGdxGame.levelManager;

public abstract class Projectile extends Entity {
        protected int speed;
        protected boolean inAir = false;
        protected boolean visible = false; // Variable für Sichtbarkeit
        protected float flyTimer=0;
        protected Entity shooter;
        protected ArrayList<Entity> entitiesHit= new ArrayList<>();

        public Projectile(float x, float y, EntityType type, GameMap map, float attackDamage, Entity shooter) {
            super(x, y, type, map, 100, attackDamage);
            this.shooter=shooter;
        }

        @Override
        public void update(float deltaTime, float gravity) {
            checkHit(this.getAttackDamage());
        }

    public void checkHit(float damage) {
        ArrayList<Entity> entities = levelManager.getEntities();
        for(Entity entity : entities) {
            if (entity != getShooter() && entity != this && entity.isEntityInRange(this, 0, 0)) {
                    if (!entitiesHit.contains(entity)) {
                        entity.takeDamage(damage);
                        entitiesHit.add(entity);
                    }
                }
            }
        }



        public abstract void render(SpriteBatch batch);

        public Entity getShooter() {
            return shooter;
        }

        public void setThrown(boolean thrown) {
            this.inAir = inAir;
        }

}
