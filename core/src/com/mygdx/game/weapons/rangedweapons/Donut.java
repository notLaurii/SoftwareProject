package com.mygdx.game.weapons.rangedweapons;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.entities.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.projectiles.DonutProjectile;
import com.mygdx.game.world.GameMap;

import static com.mygdx.game.management.MyGdxGame.levelManager;

public class Donut extends RangedWeapon{
    private Texture image;
    public Donut(float x, float y, GameMap map, Entity wielder) {
        super(x, y, 25, map, wielder, 1.5f, 20);
        this.image=new Texture("Entity/Weapons/donut.png");
    }


    public void attack(float playerDamage) {
        if(this.getCanAttack()) {
            DonutProjectile donutProjectile = new DonutProjectile(getMap(), getWielder(), getAttackDamage());
            levelManager.entitiesToAdd.add(donutProjectile);
            this.startAttackCooldown(this.getMaxCooldown());
        }
    }

    @Override
    public void render(OrthographicCamera cam, SpriteBatch batch) {
        //batch.draw(image, wielder.getX()-10, wielder.getY()+ 12, 0, 0, 13, 14, 0.75f, 0.75f, 0, 0, 0, image.getWidth(), image.getHeight(), false, false);
    }
}
