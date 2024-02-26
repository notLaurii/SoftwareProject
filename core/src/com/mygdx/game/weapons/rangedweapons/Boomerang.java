package com.mygdx.game.weapons.rangedweapons;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.projectiles.BoomerangProjectile;
import com.mygdx.game.world.GameMap;

import java.util.ArrayList;

import static com.mygdx.game.management.MyGdxGame.levelManager;


public class Boomerang extends RangedWeapon{

    public Boomerang(float x, float y, GameMap map, Entity wielder) {
        super(x, y, 15, map, wielder, 1);
    }
    public void attack(float playerDamage) {
        if(this.getCanAttack()) {
            BoomerangProjectile boomerangProjectile = new BoomerangProjectile(getMap(), getWielder(), getAttackDamage());
            levelManager.entitiesToAdd.add(boomerangProjectile);
            this.startAttackCooldown(this.getMaxCooldown());
        }
    }
}
