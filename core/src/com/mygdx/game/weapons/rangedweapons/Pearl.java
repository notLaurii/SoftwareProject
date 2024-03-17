package com.mygdx.game.weapons.rangedweapons;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.projectiles.PearlProjectile;
import com.mygdx.game.world.GameMap;

import static com.mygdx.game.management.MyGdxGame.levelManager;

public class Pearl extends RangedWeapon {

    public Pearl(float x, float y, GameMap map, Entity wielder) {
        super(x, y, 0, map, wielder, 5,0);
    }

    @Override
    public void render(OrthographicCamera cam, SpriteBatch batch) {

    }



    public void attack(float playerDamage) {
        if(this.getCanAttack() && !levelManager.pearlIsOnMap()&& levelManager.getPlayer().getHealth() > 0) {
            PearlProjectile pearlProjectile = new PearlProjectile(getMap(), getWielder());
            levelManager.entitiesToAdd.add(pearlProjectile);
            this.startAttackCooldown(this.getMaxCooldown());
        }
        
    }
}
