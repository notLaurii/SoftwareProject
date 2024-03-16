package com.mygdx.game.entities.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityType;
import com.mygdx.game.entities.Player;
import com.mygdx.game.world.GameMap;

import static com.mygdx.game.management.MyGdxGame.levelManager;


public abstract class Item extends Entity {

    public Item(float x, float y, EntityType type, GameMap map) {
        super(x,y,type, map, 0,0);
    }

    public float DistanceToPlayer(Player player) {
        float x;
        float y;
        float z;

        x = Math.abs(player.getX()-getX());
        y = Math.abs(player.getY()-getY());

        z=x+y;

        return z;
    }


    public abstract void render(SpriteBatch batch);

}
