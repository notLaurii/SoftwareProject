package com.mygdx.game.entities.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.EntityType;
import com.mygdx.game.entities.Player;
import com.mygdx.game.world.GameMap;

public class Gold extends Item {
    Texture image;

    public Gold(float x, float y, GameMap map) {
        super(x,y,EntityType.GOLD, map, 0,0);
        this.pos = new Vector2(x,y);
        this.type = type;
        this.map = map;
        image = new Texture("Entity/Item/Gold/Gold.png");
    }


    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y);
    }

    @Override
    public void update(float deltaTime, float gravity) {
        super.update(deltaTime, gravity);
    }

}





