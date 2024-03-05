package com.mygdx.game.management;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityData;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.enemies.Slime;
import com.mygdx.game.entities.projectiles.BoomerangProjectile;
import com.mygdx.game.world.GameMap;
import com.mygdx.game.world.TiledGameMap;

import java.util.ArrayList;
//Erstellt das Spiel
public class MyGdxGame extends ApplicationAdapter {

	public static OrthographicCamera cam;

	public static float Width;
	public static float Height;
	public static SpriteBatch batch;
	public static GameMap gameMap;
	public static GameManager gameManager;
	public static LevelManager levelManager;
	public static GameProgress gameProgress;
	public static GameSaver gameSaver;

	@Override
	public void create() {
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.update();
		Width = Gdx.graphics.getWidth();
		Height = Gdx.graphics.getHeight();
		gameProgress = new GameProgress();
		try {
			loadGameFromJson("gameProgress.json", false);
		} catch (Exception e){
			loadGameFromJson("defaultGameProgress.json", false);
		}
		gameMap = new TiledGameMap();
		gameSaver = new GameSaver(gameProgress.getLevel(), gameManager);
		levelManager = new LevelManager();
		levelManager.create();
	}

	public void update(float delta) {
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		cam.update();
		gameMap.update(Gdx.graphics.getDeltaTime());
		gameManager.update(Gdx.graphics.getDeltaTime());
		levelManager.update(Gdx.graphics.getDeltaTime());
		gameSaver.update(Gdx.graphics.getDeltaTime());
		gameManager.render(cam, batch);
	}

	public static float getWidth() {
		return Width;
	}

	public static float getHeight() {
		return Height;
	}


	@Override
	public void dispose() {
		batch.dispose();
	}

	protected void loadGameFromJson(String jsonFilePath, boolean internal) {
		Json json = new Json();
		ArrayList<GameProgress> gameDataList = json.fromJson(ArrayList.class, GameProgress.class, Gdx.files.local(jsonFilePath));
		if (internal)
			gameDataList = json.fromJson(ArrayList.class, GameProgress.class, Gdx.files.internal(jsonFilePath));
		for (GameProgress gameData : gameDataList) {
			createGameFromData(gameData);
		}
	}
	private void createGameFromData(GameProgress gameData) {
		gameManager = new GameManager(gameData.getPlayerId(), gameData.getLevel());
	}
}
