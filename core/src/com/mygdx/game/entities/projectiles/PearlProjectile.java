package com.mygdx.game.entities.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityType;
import com.mygdx.game.world.GameMap;

import static com.mygdx.game.management.MyGdxGame.levelManager;

public class PearlProjectile extends Projectile{
    private Texture image;
    private String direction;

    private float speed = 3;


    public PearlProjectile(GameMap map, Entity shooter) {
        super(shooter.getX(), shooter.getY(), EntityType.PEARL, map, 10, shooter);
        this.image=new Texture("Entity/Projectile/pearlProjectile.png");
        this.direction=getShooter().getDirection();
    }

    @Override
    public void update(float deltaTime, float gravity) {
        followPlayer();
        super.update(deltaTime, gravity);

    }

    public void followPlayer() {
        float x = levelManager.getPlayer().getX() - getX();
        float y = levelManager.getPlayer().getY() - getY();
        if(isEntityInRange(levelManager.getPlayer(), 5,10)) {
            levelManager.getPlayer().takeDamage(attackDamage);
            levelManager.entitiesToRemove.add(this);
            levelManager.getPlayer().setSpeed(levelManager.getPlayer().getSpeed()/2);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    levelManager.getPlayer().setSpeed(levelManager.getPlayer().getSpeed()*2);
                }
            },2);
        } else {
            if (Math.abs(y) > 50) {
                if(x > 0) {
                    moveX(speed/4);
                }
                if (x < 0) {
                    moveX(-speed/4);
                }
            }
                if (x > 0) {
                    moveX(speed);
                }
                if (x < 0) {
                    moveX(-speed);
                }


            if (Math.abs(x) > 50) {
                if(y > 0) {
                    moveY(speed/4);
                }
                if (y < 0) {
                    moveY(-speed/4);
                }
            } else {
                if(y > 0) {
                    moveY(speed);
                }
                if (y < 0) {
                    moveY(-speed);
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
    }

}
