package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityType;
import com.mygdx.game.weapons.Weapon;
import com.mygdx.game.world.GameMap;

import java.util.Objects;

import static com.mygdx.game.management.MyGdxGame.levelManager;

public class Bird extends Enemy {

    private float speed = 1;
    private final Weapon weapon;
    private boolean beginningCooldown = false;
    private boolean birthday;

    public Bird(float x, float y, GameMap map, float health) {
        super(x, y, EntityType.BIRD, map, health, 0, 0);
        setAnimation("Flying"+ getDirection(),0, 0);
        this.weapon = assignWeapon("egg");

    }
    @Override
    public void update(float deltaTime, float gravity) {
        updateAnimation(deltaTime);
        followPlayer();
        if(Objects.equals(getDirection(), "Right")) setAnimation("FlyingRight",0,0);
        if(Objects.equals(getDirection(), "Left")) setAnimation("FlyingLeft",0,0);
        if(this.weapon != null && beginningCooldown) {
            this.weapon.attack(this.weapon.getAttackDamage());
        } else {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    beginningCooldown = true;
                }
            }, 3);
        }

        //Sterben an Alterschwäche
        if(!birthday) {
            birthday=true;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                health-=1;
                birthday = false;
            }
        },1);
        }
    }
    @Override
    public void render(SpriteBatch batch) {
        float frameX = currentFrame * frameWidth;
        float frameY = 0;
        batch.draw(animationTexture, pos.x, pos.y, 0, 0, getWidth(), getHeight(), 1, 1, 0, (int)frameX, (int)frameY, frameWidth, frameHeight, false, false);
        super.render(batch);
    }

    public void followPlayer() {
        float x = levelManager.getPlayer().getX() - getX();
        if (x > speed) {
            moveX(speed);
            setDirection("Right");
        }
        if(x<-speed) {
            moveX(-speed);
            setDirection("Left");
        }
    }
    public void gettingOlder() {

    }

    public void setAnimation(String animation, int priority, float deltaTime) {
        int frameAmount = 8;
        float frameTime = 0.1f;
        String path = ("Entity/Enemy/Bird/Bird_Flying_"+ getDirection() + ".png");
        loadAnimationFrames(animation,priority, deltaTime, frameAmount, frameTime, path);
    }
}
