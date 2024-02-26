package com.mygdx.game.weapons.rangedweapons;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.weapons.Weapon;
import com.mygdx.game.world.GameMap;

public abstract class RangedWeapon extends Weapon {
    public RangedWeapon(float x, float y, float weaponDamage, GameMap map, Entity wielder, float maxCooldown) {
        super(x, y, weaponDamage, map, wielder, maxCooldown);
    }

    @Override
    public void attack(float playerDamage) {

    }
}
