package com.mygdx.game.entities.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityType;
import com.mygdx.game.entities.Player;
import com.mygdx.game.world.GameMap;


public abstract class Item extends Entity {
    protected Vector2 pos;
    protected float velocityY = 0;

    protected GameMap map;
    protected boolean grounded = false;

    public Item(float x, float y, EntityType type, GameMap map, int i, int i1) {
        super(x,y,type, map, 0,0);
        this.pos = new Vector2(x,y);
        this.type = type;
        this.map = map;
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
    public void update(float deltaTime, float gravity) {
        float newY = pos.y;

        this.velocityY -= gravity * deltaTime * getWeight();
        newY += this.velocityY * deltaTime;

        if (map.doesEntityCollideWithMap(pos.x, newY, getWidth(), getHeight())) {
            if (velocityY < 0) {
                this.pos.y = (float) Math.floor(pos.y);
                grounded = true;
            }
            this.velocityY = 0;
        }	else {
            this.pos.y = newY;
            grounded = false;
        }

    }



    public abstract void render(SpriteBatch batch);

}
