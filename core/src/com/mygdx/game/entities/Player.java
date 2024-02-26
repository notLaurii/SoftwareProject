package com.mygdx.game.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygdx.game.management.MyGdxGame;
import com.mygdx.game.weapons.Weapon;
import com.mygdx.game.world.GameMap;
import com.mygdx.game.world.TileType;

public class Player extends Entity {

	private static float speed;
	private static float jumpVelocity;
	private static float frameDuration = 0.2f;
	private String skin;
	protected Texture healthBar;
	private static final int NUM_HEALTH_BARS = 23;
	private static final int HEALTH_BAR_WIDTH = 24;
	private static final int HEALTH_BAR_HEIGHT = 6;

	private Texture animationTexture;
	private int frameWidth;
	private int frameHeight;
	private int frameCount;
	private int currentFrame = 0;
	private float stateTime = 0;
	private String weaponID;
	private Weapon weapon;
	private float animationTimer=0;
	private int currentPriority=0;
	private String currentAnimation;
	private int id;


	Texture image;

	public Player(int id, float x, float y, GameMap map, float maxHealth, float health, float attackDamage, float speed, float jumpVelocity, String weaponID, String skin) {
		super(x, y, EntityType.PLAYER, "player", map, maxHealth, attackDamage);
		this.speed=speed;
		this.jumpVelocity=jumpVelocity;
		this.weaponID=weaponID;
		this.weapon=assignWeapon(weaponID);
		this.skin=skin;
		this.health=health;
		loadAnimationFrames("Stand", 0, 0);
		healthBar=new Texture("Entity/Player/Overlay/PlayerHealthBar.png");
		this.id = id;
	}

	private void loadAnimationFrames(String animation, int priority, float deltaTime) {
		String data="";
		if(!animation.equals(currentAnimation)&&(animationTimer>frameCount*frameDuration||priority>=currentPriority)) {
		if(animation=="Walk") {
			frameCount = 8;
			frameDuration = 0.1f;
		}
		else if(animation=="Stand") {
			frameCount = 1;
			frameDuration = 0.2f;
		}
		else if(animation=="Attack") {
			frameCount = 4;
			data=weaponID;
			frameDuration = 0.4f;
		}
		else if(animation=="Jump") {
			frameCount = 4;
			frameDuration = 0.07f;
		}
			animationTimer=0;
			animationTexture = new Texture ("Entity/Player/" + skin + "/"+ animation+"/" + skin + animation+data+this.getDirection()+".png");
			currentPriority=priority;
			currentAnimation = animation;
		}
		frameWidth = animationTexture.getWidth() / frameCount;
		frameHeight = animationTexture.getHeight();
		animationTimer+=deltaTime;
	}

	@Override
	public void update(float deltaTime, float gravity) {
		super.update(deltaTime, gravity);
		loadAnimationFrames("Stand", 1, deltaTime);
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			if(this.weapon.getCanAttack()) {
				loadAnimationFrames("Attack", 2, deltaTime);
			}
			attack();
		}
		if (Gdx.input.isKeyPressed(Keys.SPACE) && grounded)
			this.velocityY += jumpVelocity * getWeight();
		else if (Gdx.input.isKeyPressed(Keys.SPACE) && !grounded &&this.velocityY > 0)
			this.velocityY += jumpVelocity * getWeight() * deltaTime;

		if (Gdx.input.isKeyPressed(Keys.A)) {
			this.setDirection("Left");
			moveCamX(-speed * deltaTime);
			moveX(-speed * deltaTime);
			loadAnimationFrames("Walk",1, deltaTime);
		}

		if (Gdx.input.isKeyPressed(Keys.D)) {
			this.setDirection("Right");
			moveCamX(speed * deltaTime);
			moveX(speed * deltaTime);
			loadAnimationFrames("Walk",1, deltaTime);
		}
		if(!grounded)
			loadAnimationFrames("Jump", 1, deltaTime);
		moveCamY(pos.y);
		updateAnimation(deltaTime);
	}

	public void moveCamX(float amount) {
		if ((Gdx.input.isKeyPressed(Keys.D)||Gdx.input.isKeyPressed(Keys.A))) {
			if (pos.x > MyGdxGame.getWidth() / 2 && pos.x < MyGdxGame.gameMap.getPixelWidth() - MyGdxGame.getWidth() / 2) {
				Vector2 translation = new Vector2(getDeltaX(amount), 0f);
				MyGdxGame.cam.translate(translation);
				MyGdxGame.cam.update();
			} else if (pos.x <= MyGdxGame.getWidth() / 2) {
				MyGdxGame.cam.position.x = MyGdxGame.getWidth() / 2;
			} else {
				MyGdxGame.cam.position.x = MyGdxGame.gameMap.getPixelWidth() - MyGdxGame.getWidth() / 2;
			}
		}
	}

	public float getDeltaX(float amount) {
		if (Gdx.input.isKeyPressed(Keys.D)||Gdx.input.isKeyPressed(Keys.A)) {
			float newX = pos.x + amount;
			if (!map.doesEntityCollideWithMap(newX, pos.y, getWidth(), getHeight()))
				return amount;
		}
		return 0f;
	}

	public void moveCamY(float y) {
		int heightLevel=(int) Math.floor((pos.y+getHeight())/MyGdxGame.getHeight());
		if(heightLevel==0)
			MyGdxGame.cam.position.y = MyGdxGame.getHeight()/2;
		else if(heightLevel*MyGdxGame.getHeight()+MyGdxGame.getHeight()/2>MyGdxGame.gameMap.getPixelHeight()-MyGdxGame.getHeight()/2+TileType.TILE_SIZE)
			MyGdxGame.cam.position.y = MyGdxGame.gameMap.getPixelHeight()-MyGdxGame.getHeight()/2;
		else
			MyGdxGame.cam.position.y = heightLevel*MyGdxGame.getHeight()+MyGdxGame.getHeight()/2-TileType.TILE_SIZE;
		MyGdxGame.cam.update();
	}

	public float getY(float y) {
		if (y>MyGdxGame.gameMap.getPixelHeight()/2)
			return MyGdxGame.gameMap.getPixelHeight()/2;
		return -MyGdxGame.gameMap.getPixelHeight()/2;
	}

	private void updateAnimation(float deltaTime) {
		stateTime += deltaTime;
		currentFrame = (int) (stateTime / frameDuration) % frameCount;
	}

	@Override
	public void render(SpriteBatch batch) {
		// CharacterAnimation rendern
		float frameX = currentFrame * frameWidth;
		float frameY = 0; // Der Y-Wert im Bild bleibt 0, da es sich um eine einzelne Zeile handelt

		// HealthBar rendern
		float healthRatio = health / maxHealth;
		int numBarsToShow = (int) (healthRatio * NUM_HEALTH_BARS);
		int textureY = NUM_HEALTH_BARS - numBarsToShow;

		// Zeichnen der Bilder
		batch.draw(animationTexture, pos.x, pos.y, 0, 0, getWidth(), getHeight(), 1, 1, 0, (int)frameX, (int)frameY, frameWidth, frameHeight, false, false);
		batch.draw(healthBar, pos.x+getWidth()/2-HEALTH_BAR_WIDTH/2, (float) (pos.y + 1.1*getHeight()), HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT,
				0, HEALTH_BAR_HEIGHT * textureY, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT, false, false);
	}

	public int getId() {
		return id;
	}

	public void attack() {
		this.weapon.attack(this.attackDamage);
	}

	public static float getSpeed() {
		return speed;
	}

	public static void setSpeed(float speed) {
		Player.speed = speed;
	}

	public static float getJumpVelocity() {
		return jumpVelocity;
	}

	public static void setJumpVelocity(float jumpVelocity) {
		Player.jumpVelocity = jumpVelocity;
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public String getWeaponID() {
		return weaponID;
	}

	public void setWeaponID(String weaponID) {
		this.weaponID = weaponID;
	}

}