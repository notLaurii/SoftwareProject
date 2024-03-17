package com.mygdx.game.weapons.rangedweapons;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.projectiles.EggProjectile;
import com.mygdx.game.world.GameMap;

import static com.mygdx.game.management.MyGdxGame.levelManager;

public class Egg extends RangedWeapon{
    public Egg(float x, float y, GameMap map, Entity wielder) {
        super(x, y, 0, map, wielder, 5,0);
    }
    public void attack(float playerDamage) {
        if(this.getCanAttack() && levelManager.getPlayer().getHealth() > 0) {
            EggProjectile eggProjectile = new EggProjectile(getMap(), getWielder());
            levelManager.entitiesToAdd.add(eggProjectile);
            this.startAttackCooldown(this.getMaxCooldown());
        }
    }


    @Override
    public void render(OrthographicCamera cam, SpriteBatch batch) {

    }
}
