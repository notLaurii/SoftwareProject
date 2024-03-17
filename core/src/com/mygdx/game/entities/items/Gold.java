package com.mygdx.game.entities.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.EntityType;
import com.mygdx.game.entities.Player;
import com.mygdx.game.world.GameMap;

public class Gold extends Item {
    Texture image;
    int value;

    public Gold(float x, float y, GameMap map, int value) {
        super(x, y, EntityType.GOLD, map);
        this.image = new Texture("Entity/Item/Gold/Gold.png");
        this.value=value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y);
    }
}





