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

import static com.mygdx.game.management.MyGdxGame.*;

public class Player extends Entity {

	private float speed;
	private float jumpVelocity;
	private String skin;
	private Texture healthBar;
	private static final int NUM_HEALTH_BARS = 23;
	private static final int HEALTH_BAR_WIDTH = 24;
	private static final int HEALTH_BAR_HEIGHT = 6;
	private String weaponID;
	private Weapon weapon;
	private int id;
	private int goldAmount;
	private boolean tnt = false;

	public Player(int id, float x, float y, GameMap map, float maxHealth, float attackDamage, float speed, float jumpVelocity, String weaponID, String skin, int goldAmount) {
		super(x, y, EntityType.PLAYER, map, maxHealth, attackDamage);
		this.speed=speed;
		this.jumpVelocity=jumpVelocity;
		this.weaponID=weaponID;
		this.weapon=assignWeapon(weaponID);
		this.skin=skin;
		setAnimation("Stand", 0, 0);
		healthBar=new Texture("Entity/Player/Overlay/PlayerHealthBar.png");
		this.id = id;
		this.goldAmount=goldAmount;
	}

	public void setAnimation(String animation, int priority, float deltaTime) {//legt fest, welche Animation ausgeführt wird
			int frameAmount=0;
			float frameTime=0;
		String data="";
		if(animation=="Walk") {
			frameAmount = 8;
			frameTime = 0.1f;
		}
		else if(animation=="Stand") {
			frameAmount = 1;
			frameTime = 0.2f;
		}
		else if(animation=="Attack") {
			frameAmount = 4;
			data=weaponID;
			frameTime = 0.4f;
		}
		else if(animation=="Jump") {
			frameAmount = 4;
			frameTime = 0.07f;
		}
		String path=  ("Entity/Player/" + skin + "/"+ animation+"/" + skin + animation+data+this.getDirection()+".png");
		loadAnimationFrames(animation, priority, deltaTime, frameAmount, frameTime, path);
	}

	@Override
	public void update(float deltaTime, float gravity) {//Überprüft Aktionen des Spielers in Reaktion auf Drücken von Tasten (keine Zeit, um dies im Input-Manager hinzuzufügen)
		super.update(deltaTime, gravity);
		setAnimation("Stand", 1, deltaTime);
		if (Gdx.input.isKeyPressed(Keys.SPACE) && grounded)
			this.velocityY += jumpVelocity * getWeight();
		else if (Gdx.input.isKeyPressed(Keys.SPACE) && !grounded &&this.velocityY > 0)
			this.velocityY += jumpVelocity * getWeight() * deltaTime;

		if (Gdx.input.isKeyPressed(Keys.A)) {
			this.setDirection("Left");
			moveX(-speed * deltaTime);
			setAnimation("Walk",1, deltaTime);
		}

		if (Gdx.input.isKeyPressed(Keys.D)) {
			this.setDirection("Right");
			moveX(speed * deltaTime);
			setAnimation("Walk",1, deltaTime);
		}
		if(!grounded)
			setAnimation("Jump", 1, deltaTime);
		moveCamY(pos.y);
		updateAnimation(deltaTime);
		if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
			if(!tnt) {
				if(isGrounded()) {
					levelManager.entitiesToAdd.add(new Tnt(getX(),getY(),gameMap));
					tnt = true;
				}
			} else tnt = false;
		}
		moveCamX();
	}

	public void moveCamX() {//bewegt die Kamera in horizontaler Richtung
			if (pos.x > MyGdxGame.getWidth() / 2 && pos.x < MyGdxGame.gameMap.getPixelWidth() - MyGdxGame.getWidth() / 2) {
				cam.position.x = pos.x;
			} else if (pos.x <= MyGdxGame.getWidth() / 2) {
				cam.position.x = MyGdxGame.getWidth() / 2;
			} else {
				cam.position.x = MyGdxGame.gameMap.getPixelWidth() - MyGdxGame.getWidth() / 2;
			}
	}

	public void moveCamY(float y) {//bewegt die Kamera in Y-Richtung auf festgelegten Höhen
		int heightLevel=(int) Math.floor((pos.y+getHeight())/MyGdxGame.getHeight());
		if(heightLevel==0)
			cam.position.y = MyGdxGame.getHeight()/2;
		else if(heightLevel*MyGdxGame.getHeight()+MyGdxGame.getHeight()/2>MyGdxGame.gameMap.getPixelHeight()-MyGdxGame.getHeight()/2+TileType.TILE_SIZE)
			cam.position.y = MyGdxGame.gameMap.getPixelHeight()-MyGdxGame.getHeight()/2;
		else
			cam.position.y = heightLevel*MyGdxGame.getHeight()+MyGdxGame.getHeight()/2-TileType.TILE_SIZE;
		cam.update();
	}

	public void render(SpriteBatch batch) {
		weapon.render(cam, batch);
		// CharacterAnimation rendern
		float frameX = currentFrame * frameWidth;

		// HealthBar rendern
		float healthRatio = Math.abs(health / maxHealth);
		int numBarsToShow = (int) (healthRatio * NUM_HEALTH_BARS);
		int textureY = NUM_HEALTH_BARS - numBarsToShow;

		// Zeichnen der Bilder
		batch.draw(animationTexture, pos.x, pos.y, 0, 0, getWidth(), getHeight(), 1, 1, 0, (int) frameX, 0, frameWidth, frameHeight, false, false);
		batch.draw(healthBar, pos.x + getWidth() / 2f - HEALTH_BAR_WIDTH / 2f, (float) (pos.y + 1.1 * getHeight()), HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT,
				0, HEALTH_BAR_HEIGHT * textureY, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT, false, false);
	}

	public void switchWeapon(String weaponId) {//Tauscht die ausgewählte Waffe aus
		setWeaponID(weaponId);
		this.weapon=assignWeapon(weaponID);
	}

	public void actOnLeftClick(float deltaTime) {//führt Aktion aus, wenn LMB gedrückt wird
		if (this.weapon.getCanAttack()) {
			setAnimation("Attack", 2, deltaTime);
		}
		attack();
	}

	public int getId() {
		return id;
	}
	public void changeGoldAmount(int amount) {this.goldAmount+=amount;}
	public int getGoldAmount() {
		return goldAmount;
	}
	public void attack() {
		this.weapon.attack(this.attackDamage);
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getJumpVelocity() {
		return jumpVelocity;
	}

	public void setJumpVelocity(float jumpVelocity) {
		this.jumpVelocity = jumpVelocity;
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

	public Weapon getWeapon() {
		return weapon;
	}

}
