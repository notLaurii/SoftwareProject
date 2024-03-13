package com.mygdx.game.interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.projectiles.Projectile;

import java.util.ArrayList;

import static com.mygdx.game.management.MyGdxGame.gameManager;
import static com.mygdx.game.management.MyGdxGame.levelManager;

public class Interface {

    protected Stage stage;
    protected Texture buttonTexture;
    protected TextureRegion buttonTextureRegion;
    protected TextureRegionDrawable buttonTexRegionDrawable;
    protected ImageButton weaponButton;
    protected ImageButton skinButton;
    protected Texture backgroundImage;
    protected Image background;
    protected ArrayList<ImageButton> buttons = new ArrayList<>();

    public void create()
    {
        gameManager.getOpenInterfaces().add(this);
        this.stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        this.stage.addActor(background);
        Gdx.input.setInputProcessor(stage); //Start taking input from the ui
    }


    public void render()
    {
            stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
            stage.draw(); //Draw the ui
    }

    public void addButton(float x, float y, float width, float height, float textureWidth, float textureHeight, String path) {
        buttonTexture = new Texture(Gdx.files.internal(path));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonTexRegionDrawable = new TextureRegionDrawable(buttonTextureRegion);
        ImageButton button = new ImageButton(buttonTexRegionDrawable); //Set the button up
        button.setPosition(x, y);
        button.getImageCell().getActor().setScale(textureWidth/buttonTexture.getWidth(),textureHeight/buttonTexture.getHeight());
        stage.addActor(button);
        buttons.add(button);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onButtonClicked(buttons.indexOf(button));
            }
        });
    }
    public void onButtonClicked(int buttonIndex) {

    }

    public void removePlayerProjectiles() {
        for(Entity entity : levelManager.getEntities())
            if(entity instanceof Projectile) {
                if(((Projectile) entity).getShooter()== levelManager.getPlayer())
                    levelManager.entitiesToRemove.add(entity);
            }
    }
}
