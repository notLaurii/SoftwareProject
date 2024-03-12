package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.entities.enemies.Elf;
import com.mygdx.game.weapons.Weapon;
import com.mygdx.game.world.GameMap;

import java.util.Map;

import static com.mygdx.game.management.MyGdxGame.gameMap;
import static com.mygdx.game.management.MyGdxGame.levelManager;

public class Tnt extends PhysicalAbility {
    private Texture image;
    private boolean placeStatus = false;

    private boolean explosionStatus = false;

    public Tnt(float x, float y, GameMap map) {
        super(x, y, EntityType.TNT, "physicalAbility", map, 0, 100);
        image  = new Texture("Entity/Ability/Tnt/tnt.png");
    }


    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
    }
    @Override
    public void update(float deltaTime, float gravity) {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            image = new Texture("Entity/Ability/Tnt/explosion.png");
            //setWidth(getWidth()*2);
            //setHeight(getHeight()*2);
            explosionStatus = true;
            for (Entity entity : levelManager.entities) {
            if(isEntityInRange(entity,20,20)) {
                if(entity.getHealth() - attackDamage <= 0) {
                    entity.setHealth(0);
                } else entity.setHealth(entity.getHealth() - attackDamage);
           }
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        if(entity instanceof Tnt) levelManager.entitiesToRemove.add(entity);
                    }
                }, 0.3f);
            }
        }
    }

    public boolean isPlaced() {return placeStatus;}

    public boolean isExplosed() {return explosionStatus;}

    public void placing() {
        placeStatus = true;
    }


}
