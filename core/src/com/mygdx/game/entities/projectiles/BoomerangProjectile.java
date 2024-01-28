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
import com.mygdx.game.world.GameMap;

import java.util.ArrayList;

import static com.mygdx.game.world.GameMap.entities;

public class BoomerangProjectile extends Projectile {

    private static final int speed = 500;
    private float airTime = 0;
    private float maxAirTime = 5;
    private float normalAirTime = 2.5f;
    private Texture image;
    private float damage;
    private float currentSpeed=speed;
    private String direction;
    private float rotation = 0f;


    public BoomerangProjectile(GameMap map, Entity shooter, float damage) {
        super(shooter.getX(), shooter.getY(), EntityType.BOOMERANG, map, 0, shooter);
        this.damage=damage;
        this.image=new Texture("Entity/Projectile/boomerang.png");
        this.direction=getShooter().getDirection();
    }

    @Override
    public void update(float deltaTime, float gravity) {
        rotation += 360 * deltaTime / normalAirTime;
        checkHit(damage);
        super.update(deltaTime, gravity);
        airTime += deltaTime;
        if (airTime >= maxAirTime)
            GameMap.entitiesToRemove.add(this);
        currentSpeed -= speed * deltaTime / (normalAirTime /2);
        float delta = currentSpeed * deltaTime;
        if(this.direction=="Left") {
            if(map.doesEntityCollideWithMap(getX()-delta, getY(), getWidth(), getHeight())) {
                airTime = normalAirTime / 2;
                currentSpeed = 0;
            }
            if (airTime > normalAirTime / 2&&this.getX() > shooter.getX())
                GameMap.entitiesToRemove.add(this);
            moveX(-delta);
        }
            else {
            if(map.doesEntityCollideWithMap(getX()+delta, getY(), getWidth(), getHeight())) {
                if (airTime < normalAirTime / 2) {
                    airTime = normalAirTime / 2;
                    currentSpeed = 0;
                }
                else GameMap.entitiesToRemove.add(this);
            }
            if (airTime > normalAirTime / 2&&this.getX() < shooter.getX())
                GameMap.entitiesToRemove.add(this);
                moveX(delta);
        }
    }

    public void setVisible(boolean isVisible) {
        this.visible = isVisible;
    }

    @Override
    public void render(SpriteBatch batch) {
        float originX = getWidth() / 2f;
        float originY = getHeight() / 2f;

        batch.draw(image, pos.x, pos.y, originX, originY, getWidth(), getHeight(), 1f, 1f, rotation, 0, 0, image.getWidth(), image.getHeight(), false, false);
    }
}
