package com.mygdx.game.weapons;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Player;
import com.mygdx.game.world.GameMap;

import static com.mygdx.game.management.MyGdxGame.gameManager;


public abstract class Weapon {

    protected boolean canAttack=true;
    protected float x;
    protected float y;
    protected float weaponDamage;
    protected float attackDamage;
    protected GameMap map;
    protected Entity wielder;
    protected float maxCooldown;
    private float cooldownTimer;
    private boolean cooldownStopped;
    private Timer.Task cooldownTask;
    protected int price;

    public Weapon(float x, float y, float weaponDamage, GameMap map, Entity wielder, float maxCooldown, int price) {
        this.x=x;
        this.y=y;
        this.map=map;
        this.wielder=wielder;
        this.weaponDamage=weaponDamage;
        this.attackDamage=weaponDamage+wielder.getAttackDamage();
        this.maxCooldown=maxCooldown;
        this.price=price;
    }
    public abstract void attack(float playerDamage);

    public void startAttackCooldown(float weaponCooldown) {
        canAttack = false;
        this.cooldownTimer=maxCooldown;
        cooldownTask = new Timer.Task() {
            @Override
            public void run() {
                canAttack = true;
            }
        };
        Timer.schedule(cooldownTask, weaponCooldown);
    }

    public void update(float deltaTime) {
        if(!canAttack)
            if(gameManager.isGameRunning()) {
                cooldownTimer-=deltaTime;
                if(cooldownStopped) {
                    cooldownStopped=false;
                    Timer.schedule(cooldownTask, cooldownTimer);
                }
            }
            else {
                cooldownTask.cancel();
                cooldownStopped=true;
            }
    }
    public abstract void render(OrthographicCamera cam, SpriteBatch batch);

    public float getWeaponDamage() {
        return weaponDamage;
    }
    public float getAttackDamage() {
        return attackDamage;
    }
    public GameMap getMap() {
        return map;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public Entity getWielder() {
        return wielder;
    }
    public float getMaxCooldown() {
        return maxCooldown;
    }
    public boolean getCanAttack() {return  canAttack;}
    public int getPrice() {return price;}
}
