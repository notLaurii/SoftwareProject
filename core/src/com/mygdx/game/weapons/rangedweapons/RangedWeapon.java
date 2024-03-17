package com.mygdx.game.weapons.rangedweapons;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.weapons.Weapon;
import com.mygdx.game.world.GameMap;

public abstract class RangedWeapon extends Weapon {
    public RangedWeapon(float x, float y, float weaponDamage, GameMap map, Entity wielder, float maxCooldown, int price) {
        super(x, y, weaponDamage, map, wielder, maxCooldown, price);
    }

    @Override
    public void attack(float playerDamage) {

    }

    public abstract void render(OrthographicCamera cam, SpriteBatch batch);
}
