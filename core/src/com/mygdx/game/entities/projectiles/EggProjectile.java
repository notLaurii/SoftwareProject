package com.mygdx.game.entities.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityType;
import com.mygdx.game.world.GameMap;

import java.util.TimerTask;

import static com.mygdx.game.management.MyGdxGame.levelManager;

public class EggProjectile extends Projectile {
    private Texture image;
    private float attackRangeX = 10;
    private float attackRangeY = 0;

    private boolean isOnPlayer = false;
    public EggProjectile(GameMap map, Entity shooter) {
        super(shooter.getX(), shooter.getY(), EntityType.EGG, map, 30, shooter);
        this.image=new Texture("Entity/Projectile/eggProjectile.png");
    }
    @Override
    public void update(float deltaTime, float gravity) {
        super.update(deltaTime, gravity);
        float newY = pos.y;
        this.velocityY -= gravity * deltaTime * getWeight();
        newY += this.velocityY * deltaTime;

        if (map.doesEntityCollideWithMap(pos.x, newY, getWidth(), getHeight())) {
            if (velocityY < 0) {
                this.pos.y = (float) Math.floor(pos.y);
                grounded = true;
            }
            this.velocityY = 0;
        }	else {
            this.pos.y = newY;
            grounded = false;
        }

            if(!isOnPlayer) {
                attackPlayer(levelManager.getPlayer(),attackRangeX,attackRangeY);
                isOnPlayer = true;
            }
            if(isGrounded()) {
                image = new Texture("Entity/Projectile/fried_egg.png");
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        remove();
                    }
                }, 3);
            }



    }
    public void remove() {
        levelManager.entitiesToRemove.add(this);
    }



    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
    }
}
