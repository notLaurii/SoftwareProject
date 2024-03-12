package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.world.GameMap;

public class PhysicalAbility extends Entity {

    public PhysicalAbility(float x, float y, EntityType type, String category, GameMap map, float maxHealth, float attackDamage) {
        super(x, y, type, "physicalAbility", map, 0, attackDamage);
    }

    @Override
    public void render(SpriteBatch batch) {

    }
}
