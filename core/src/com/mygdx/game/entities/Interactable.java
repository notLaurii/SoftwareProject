package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.world.GameMap;

public class Interactable extends Entity{

    public Interactable(float x, float y, EntityType type, GameMap map, float maxHealth, float attackDamage) {
        super(x, y, type,  map, maxHealth, attackDamage);
    }

    public void interactWithPlayer() {

    }

    @Override
    public void render(SpriteBatch batch) {

    }
}
