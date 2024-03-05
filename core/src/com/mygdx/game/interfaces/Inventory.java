package com.mygdx.game.interfaces;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.mygdx.game.management.MyGdxGame.gameProgress;
import static com.mygdx.game.management.MyGdxGame.levelManager;

public class Inventory extends Game {

    private Stage inventoryStage;
    private Texture myTexture;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton weaponButton;

    @Override
    public void create()
    {
        myTexture = new Texture(Gdx.files.internal("Entity/Projectile/boomerang.png"));
        myTextureRegion = new TextureRegion(myTexture);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        weaponButton = new ImageButton(myTexRegionDrawable); //Set the button up
        weaponButton.setPosition(levelManager.getPlayer().getX(), levelManager.getPlayer().getY());
        weaponButton.setSize(10,10);
        inventoryStage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        inventoryStage.addActor(weaponButton); //Add the button to the stage to perform rendering and take input.
        Gdx.input.setInputProcessor(inventoryStage); //Start taking input from the ui
    }

    @Override
    public void render()
    {
        //Gdx.gl.glClearColor(0.5f,0.2f,0.3f,0.5f);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        inventoryStage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        inventoryStage.draw(); //Draw the ui
    }

    public void update(float deltaTime) {
        if(weaponButton.isPressed()) {
            new ItemMenu(gameProgress.getWeaponsUnlocked(), gameProgress.getWeaponsUnlocked());
        }
    }
}