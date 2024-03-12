package com.mygdx.game.entities.projectiles;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityType;
import com.mygdx.game.entities.Player;
import com.mygdx.game.management.LevelManager;
import com.mygdx.game.world.GameMap;

import java.util.ArrayList;
import java.util.Objects;

import static com.mygdx.game.management.MyGdxGame.levelManager;

public class DonutProjectile extends Projectile {

    private static final int speed = 200;
    private float airTime = 0;
    private float maxAirTime = 5;
    private float normalAirTime = 2.5f;
    private Texture image;
    private float damage;
    private String direction;
    private float rotation = 0f;


    public DonutProjectile(GameMap map, Entity shooter, float damage) {
        super(shooter.getX(), shooter.getY(), EntityType.DONUTPROJECTILE, map, 0, shooter);
        this.damage=damage;
        this.direction=getShooter().getDirection();
        loadAnimationFrames("shootDonut", 1, 0, 6, 0.2f, "Entity/Projectile/DonutProjectile"+direction+".png");
    }

    @Override
    public void update(float deltaTime, float gravity) {
        checkHit(damage);
        super.update(deltaTime, gravity);
        airTime += deltaTime;
        if (airTime >= maxAirTime)
            levelManager.entitiesToRemove.add(this);
        float delta = speed * deltaTime;
        if(Objects.equals(this.direction, "Left")) {
            if(map.doesEntityCollideWithMap(getX()-delta, getY(), getWidth(), getHeight())) {
                levelManager.entitiesToRemove.add(this);
            }
            moveX(-delta);
        }
        else {
            if(map.doesEntityCollideWithMap(getX()+delta, getY(), getWidth(), getHeight())) {
                levelManager.entitiesToRemove.add(this);
                }
            moveX(delta);
        }
        updateAnimation(deltaTime);
    }

    @Override
    public void render(SpriteBatch batch) {
        float frameX = currentFrame * frameWidth;
        float frameY = 0; // Der Y-Wert im Bild bleibt 0, da es sich um eine einzelne Zeile handelt
        // Zeichnen der Bilder
        batch.draw(animationTexture, pos.x, pos.y, 0, 0, 16, 16, 1, 1, 0, (int)frameX, (int)frameY, frameWidth, frameHeight, false, false);
    }
}
