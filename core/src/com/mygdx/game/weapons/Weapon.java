package com.mygdx.game.weapons;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Player;
import com.mygdx.game.world.GameMap;



public abstract class Weapon {

    protected boolean canAttack=true;
    protected float x;
    protected float y;
    protected float weaponDamage;
    protected float attackDamage;
    protected GameMap map;
    protected Entity wielder;
    protected float maxCooldown;
    public Weapon(float x, float y, float weaponDamage, GameMap map, Entity wielder, float maxCooldown) {
        this.x=x;
        this.y=y;
        this.map=map;
        this.wielder=wielder;
        this.weaponDamage=weaponDamage;
        this.attackDamage=weaponDamage+wielder.getAttackDamage();
        this.maxCooldown=maxCooldown;
    }
public abstract void attack(float playerDamage);

    public void startAttackCooldown(float weaponCooldown) {
        canAttack = false;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                canAttack = true;
            }
        }, weaponCooldown);
    }
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

    public abstract void render(OrthographicCamera cam, SpriteBatch batch);
}
