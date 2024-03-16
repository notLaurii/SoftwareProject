package com.mygdx.game.weapons.melees;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.world.GameMap;

public class Fists extends Melee {
    private float x;
    private float y;
    private GameMap map;
    private Entity wielder;
    private float cooldown=1;

    public Fists(GameMap map, Entity wielder) {
        super(wielder.getX(),wielder.getY(), map,0,wielder,10, 5,1, 0);
    }

    @Override
    public void render(OrthographicCamera cam, SpriteBatch batch) {

    }
}
