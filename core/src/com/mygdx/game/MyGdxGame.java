package com.mygdx.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.entities.Player;
import com.mygdx.game.world.GameMap;
import com.mygdx.game.world.TileType;
import com.mygdx.game.world.TiledGameMap;

public class MyGdxGame extends ApplicationAdapter {
	
	public static OrthographicCamera cam;

	public static float Width;
	public static float Height;
	SpriteBatch batch;
	
	public static GameMap gameMap;
	private static String level;

	@Override
	public void create () {
		batch = new SpriteBatch();
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.update();
		Width = Gdx.graphics.getWidth();
		Height = Gdx.graphics.getHeight();
		
		gameMap = new TiledGameMap();
	}

	public void update(float delta) {
		if(gameMap.checkIfLevelCleared()) {
				gameMap.nextLevel();
			Json json =
			String score = json.toJson(score);
			}
		}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		cam.update();
		gameMap.update(Gdx.graphics.getDeltaTime());
		gameMap.render(cam, batch);
	}

	public static float getWidth() {
		return Width;
	}

	public static float getHeight() {
		return Height;
	}


	@Override
	public void dispose () {
		batch.dispose();
	}
}
