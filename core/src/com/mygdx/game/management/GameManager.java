package com.mygdx.game.management;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.mygdx.game.management.MyGdxGame.*;

public class GameManager {
    private int level;
    private int room;

    public GameManager() {
        this.level=gameProgress.getLevel();
    }

    public void update(float deltaTime) {
        if(Math.ceil(levelManager.getPlayer().getX())>=(gameMap.getPixelWidth()-levelManager.getPlayer().getWidth()))
            if(levelManager.isEntitiesCreated()&&levelManager.noEnemiesLeft()) {
                if (level == room) {
                    System.out.println(level + "; " + room);
                    setLevel(level + 1);
                }
                if (level == room+1 || room == 0) {
                    FileHandle folder = Gdx.files.internal("Map/Level" + level);
                    gameSaver.savePlayerData(levelManager.getPlayer());
                    gameSaver.saveGameProgress(levelManager.getPlayer());
                    if (folder.exists())
                        levelManager.switchSetting(gameManager.getLevel());
                    else {
                        goToMainRoom();
                        System.out.println(room + " vs. " + level);
                    }
                }
            }
    }

    public void goToMainRoom() {
        room=0;
        levelManager.switchSetting(room);
    }

    public int getLevel() {
        return level;
    }
    public void setLevel(int amount) {
        level=amount;
    }

    public void render(OrthographicCamera cam, SpriteBatch batch) {
        gameMap.render(cam, batch);
    }

    public int getRoom() {
        return room;
    }
    public void setRoom(int i) {
        room=i;
    }
}
