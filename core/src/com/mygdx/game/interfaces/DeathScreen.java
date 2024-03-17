package com.mygdx.game.interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import static com.mygdx.game.management.MyGdxGame.gameManager;
import static com.mygdx.game.management.MyGdxGame.levelManager;

public class DeathScreen extends Interface{

    public DeathScreen() {
        this.create();
    }

    public void create() {
        float buttonWidth=Gdx.graphics.getHeight()/250f*27;
        float buttonHeight=Gdx.graphics.getHeight()/250f*11;
        this.backgroundImage = new Texture(Gdx.files.internal("Interfaces/darkBackground.png"));
        this.background = new Image(backgroundImage);
        this.background.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        super.create();
        addImageButton((Gdx.graphics.getWidth()-buttonWidth)/2f, Gdx.graphics.getHeight()/2f+buttonHeight, buttonWidth, buttonHeight, buttonWidth, buttonHeight, "Interfaces/Buttons/RetryButton.png");
        addImageButton((Gdx.graphics.getWidth()-buttonWidth)/2f, Gdx.graphics.getHeight()/2f-2*buttonHeight, buttonWidth, buttonHeight, buttonWidth, buttonHeight, "Interfaces/Buttons/LeaveButton.png");
    }

    @Override
    public void onButtonClicked(int buttonIndex) {
        if(buttonIndex==0) {
            levelManager.switchRoom(gameManager.getLevel());
        }
        else if(buttonIndex==1) {
            levelManager.switchRoom(0);
        }
        levelManager.setGameOver(false);
        gameManager.getOpenInterfaces().remove(this);
    }
}
