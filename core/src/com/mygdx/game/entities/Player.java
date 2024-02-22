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
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.world.GameMap;
import com.mygdx.game.world.TileType;

public class Player extends Entity {

	private static final float FRAME_DURATION = 0.2f;
	private boolean UP = false;
	private float rangeX = 20;
	private float rangeY = 10;
	private float attackCooldown = 0;
	private boolean canAttack = true;
	private String skin="PridePanda";
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
	private Boomerang boomerang;
	
	Texture image;
	
	public Player(float x, float y, GameMap map, float maxHealth, float attackDamage, float speed, float jumpVelocity) {
		super(x, y, EntityType.PLAYER, map, maxHealth, attackDamage, speed, jumpVelocity, "playing");
		loadAnimationFrames(skin, "StandStill");
		//image = new Texture("PlayerAnimations/" + Skin +"/WalkRight/"+ Skin +"WalkRight1.png");
		healthBar=new Texture("PlayerHealthBar.png");
		boomerang = new Boomerang(x, y, map, this);
	}
	
	 private void loadAnimationFrames(String playerSkin, String animation) {
		if(animation=="GoRight") {
	       animationTexture = new Texture ("PlayerAnimations/" + skin + "/WalkRight/" + skin + "WalkRight.png");
		}
		else if(animation=="GoLeft") {
			animationTexture = new Texture ("PlayerAnimations/" + skin + "/WalkLeft/" + skin + "WalkLeft.png");
		}
		else if(animation=="StandStill") {
			animationTexture = new Texture("PlayerAnimations/" + skin + "/" + skin + "Front.png");
		}
		frameCount = 8;
		frameWidth = animationTexture.getWidth() / frameCount;
		frameHeight = animationTexture.getHeight();
	}

	
	/*@Override
	public void create (EntitySnapshot snapshot, EntityType type, GameMap map) {
		super.create(snapshot, type, map);
		image = new Texture("player.png");
		//otherData = snapshot.getFloat("otherData", defaultValue) 
	}*/
	
	@Override
	public void update(float deltaTime, float gravity) {
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)&&attackCooldown==0) {
			attack(this.rangeX, this.rangeY);
			startAttackCooldown();
		}
		if (Gdx.input.isKeyPressed(Keys.SPACE) && grounded)
			this.velocityY += jumpVelocity * getWeight();
		else if (Gdx.input.isKeyPressed(Keys.SPACE) && !grounded &&this.velocityY > 0)
			this.velocityY += jumpVelocity * getWeight() * deltaTime;
		
		super.update(deltaTime, gravity);
		
		if (Gdx.input.isKeyPressed(Keys.A)) {
			moveCamX(-speed * deltaTime);
			moveX(-speed * deltaTime);
			loadAnimationFrames(skin, "GoLeft");
		}
			
		
		if (Gdx.input.isKeyPressed(Keys.D)) {
			moveCamX(speed * deltaTime);
			moveX(speed * deltaTime);
			loadAnimationFrames(skin, "GoRight");
		}
		moveCamY(pos.y);
		updateAnimation(deltaTime);

		boomerang.update(deltaTime, gravity);
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
		if (!map.doesRectCollideWithMap(newX, pos.y, getWidth(), getHeight()))
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
	        currentFrame = (int) (stateTime / FRAME_DURATION) % frameCount;
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

		// Boomerang rendern
		boomerang.render(batch);
    }

	
	public void attack(float attackRangeX, float attackRangeY) {
		ArrayList<Entity> entities = GameMap.entities;
		for(Entity entity : entities) 
			if(entity != this && entity.isInPlayerRange(this)) 
				entity.takeDamage(attackDamage);
		startAttackCooldown();
	}
	
	private void startAttackCooldown() {
        canAttack = false;
        Timer.schedule(new Task() {
            @Override
            public void run() {
            	canAttack = true;
            }
        }, 1f); // 1 Sekunde Cooldown
    }
	public float getRangeX() {
		return rangeX;
	}
	
	public float getRangeY() {
		return rangeY;
	}

	
	/*public EntitySnapshot getSaveSnapshot() {
		EntitySnapshot snapshot = super.getSaveSnapshot();
		//snapshot.putFloat(otherData, defaultValue);
		return snapshot; 
	} */

}
