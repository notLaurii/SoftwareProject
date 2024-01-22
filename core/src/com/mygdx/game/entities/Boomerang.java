package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.world.GameMap;

public class Boomerang extends Entity {

    private static final int SPEED = 250;
    private boolean thrown = false;
    private boolean visible = false; // Variable für Sichtbarkeit
    private float throwTimer = 0;
    private float maxThrowTime = 2.5f; // Adjust as needed
    private Texture image;
    private Player player;

    public Boomerang(float x, float y, GameMap map, Player player) {
        super(x, y, EntityType.BOOMERANG, map);
        this.player = player;
        image = new Texture("boomerang.png");
    }

    @Override
    public void update(float deltaTime, float gravity) {
        super.update(deltaTime, gravity);

        if (thrown) {
            throwTimer += deltaTime;

            if (throwTimer >= maxThrowTime) {
                resetBoomerang();
            }
        } else if (Gdx.input.isKeyPressed(Keys.F)) {
            resetBoomerang();
            setThrown(true);
            setVisible(true); // Boomerang wird sichtbar, wenn geworfen
        }

        if (thrown) {
            float delta = SPEED * deltaTime;
            moveX(delta);

            // Check for collisions or boundaries here
            // ...

            if (throwTimer > maxThrowTime / 2) {
                resetBoomerang();
            }
        }
    }

    public void setVisible(boolean isVisible) {
        this.visible = isVisible;
    }

    private void resetBoomerang() {
        thrown = false;
        throwTimer = 0;
        pos.set(player.getPos());
        setVisible(false); // Boomerang wird unsichtbar, wenn zurückgesetzt
    }

    @Override
    public void render(SpriteBatch batch) {
        if (visible) {
            batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
        }
    }

    public void setThrown(boolean thrown) {
        this.thrown = thrown;
    }
}
