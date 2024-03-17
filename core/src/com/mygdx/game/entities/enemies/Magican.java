package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.entities.EntityType;
import com.mygdx.game.weapons.Weapon;
import com.mygdx.game.world.GameMap;

import java.util.Objects;

import static com.mygdx.game.management.MyGdxGame.cam;
import static com.mygdx.game.management.MyGdxGame.levelManager;

public class Magican extends  Enemy {
    private Texture image;
    private static float jumpVelocity;
    private String weaponID;
    private boolean canJump = true;
    private int walkCounter = 0;

    private int walkLimit = 100;
    private final Weapon weapon;
    private boolean beginningCooldown = false;
    private float speed = 0.2f;
    public Magican(float x, float y, GameMap map, float health, float jumpVelocity) {
        super(x, y, EntityType.MAGICAN, map, health, 0, 2);
        image = new Texture("Entity/Enemy/Magican/magican_right.png");
        this.jumpVelocity = jumpVelocity;
        this.weapon = assignWeapon("pearl");
        this.weaponID=weaponID;
    }

    @Override
    public void update(float deltaTime, float gravity) {
        turnInDirection();
        verfolgen();
        super.update(deltaTime, gravity);
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

    }

    public void verfolgen() {
        float x = levelManager.getPlayer().getX() - getX();
        if(levelManager.getPlayer() != null && levelManager.getPlayer().getHealth() > 0 && getCanWalk()) {
            if (x > 10) {
                moveX(speed);
            } else if (x < -10) {
                moveX(-speed);
            }
            walkCounter++;
            if (!getCanWalk()) {
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        walkCounter = 0;
                    }
                }, 2f);
            }
        }
            float y = levelManager.getPlayer().getY() - getY();
            if(y>0 && isGrounded() && canJump && levelManager.getPlayer().getHealth() > 0){
                this.velocityY += jumpVelocity * getWeight();
                canJump = false;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        canJump = true;
                    }
                }, 10);
            }

    }
    public void turnInDirection() {
        float x = levelManager.getPlayer().getX() - getX();
        if (x > 0) {
            setImage(new Texture("Entity/Enemy/Magican/magican_right.png"));
            setDirection("Right");
        } else if (x < 0) {
            setImage(new Texture("Entity/Enemy/Magican/magican_left.png"));
            setDirection("Left");
        }
    }
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
        super.render(batch);
    }
    public void setImage(Texture image) {
        this.image = image;
    }
    public boolean getCanWalk() {
        if(walkCounter>=walkLimit) {
            return false;
        } else return true;
    }
}
