package com.mygdx.game.entities;


import java.util.ArrayList;
import java.util.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.weapons.Weapon;
import com.mygdx.game.weapons.melees.Fists;
import com.mygdx.game.weapons.rangedweapons.Boomerang;
import com.mygdx.game.weapons.rangedweapons.Donut;
import com.mygdx.game.world.GameMap;

import static com.mygdx.game.management.MyGdxGame.cam;

public abstract class Entity {

	protected Vector2 pos;
	protected EntityType type;
	protected float velocityY;
	protected GameMap map;
	protected boolean grounded = false;
	protected float maxHealth;
	protected float health;
	protected float attackDamage;
	protected String direction = "Right";
	protected float playerDetectionRangeX=0;
	protected float playerDetectionRangeY=0;
	protected float animationTimer;
	protected int currentPriority;
	protected String currentAnimation;
	protected int frameWidth;
	protected int frameHeight;
	private int frameCount;
	protected int currentFrame;
	protected Texture animationTexture;
	private float stateTime = 0;
	private static float frameDuration = 0.2f;
	protected boolean isTouched;

	public Entity(float x, float y, EntityType type, GameMap map, float maxHealth, float attackDamage) {
		this.pos = new Vector2(x,y);
		this.type=type;
		this.map=map;
		this.maxHealth=maxHealth;
		this.health=maxHealth;
		this.attackDamage=attackDamage;
	}
	public void loadHealthBar() {

	}
	public void update (float deltaTime, float gravity) {
		float newY = pos.y;

		this.velocityY -= gravity * deltaTime * getWeight();
		newY += this.velocityY * deltaTime;

		if (map.doesEntityCollideWithMap(pos.x, newY, getWidth(), getHeight())) {
			if (velocityY < 0) {
				this.pos.y = (float) Math.floor(pos.y);
				grounded = true;
			}
			this.velocityY = 0;
		}	else {
			this.pos.y = newY;
			grounded = false;
		}
	}

	public abstract void render (SpriteBatch batch);

	public void moveX (float amount) {
		float newX = pos.x + amount;
		if (!map.doesEntityCollideWithMap(newX, pos.y, getWidth(), getHeight()))
			this.pos.x = newX;
	}

	public Vector2 getPos() {
		return pos;
	}

	public float getMaxHealth() {
		return maxHealth;
	}
	public float getHealth() {
		return health;
	}
	public float getAttackDamage() {
		return attackDamage;
	}
	public void setMaxHealth(float amount) {this.maxHealth=amount;}
	public void setHealth(float amount) {this.health=amount;}
	public void setAttackDamage(float amount) {this.attackDamage=amount;}
	public float getX() {
		return pos.x;
	}

	public float getY() {
		return pos.y;
	}

	public EntityType getType() {
		return type;
	}

	public boolean isGrounded() {
		return grounded;
	}

	public int getWidth() {
		return type.getWidth();
	}

	public int getHeight() {
		return type.getHeight();
	}

	public float getWeight() {
		return type.getWeight();
	}
	public boolean isEntityInRange(Entity entity, float rangeX, float rangeY) {
		if(0<=entity.pos.x-this.pos.x&&entity.pos.x-this.pos.x<=entity.getWidth()||0<=this.pos.x-entity.pos.x&&this.pos.x-entity.pos.x<=this.getWidth())
			if(0<=entity.pos.y-this.pos.y&&entity.pos.y-this.pos.y<=entity.getHeight()||0<=this.pos.y-entity.pos.y&&this.pos.y-entity.pos.y<=this.getHeight())
				return true;
		if(Math.abs(entity.pos.x+entity.getWidth()-this.pos.x)<=rangeX||Math.abs(entity.pos.x-(this.pos.x+this.getWidth()))<=rangeX)
			if((Math.abs(entity.pos.y+entity.getHeight()-this.pos.y)<=rangeY)||Math.abs(entity.pos.y-(this.pos.y+this.getHeight()))<=rangeY)
				return true;
		return false;
	}
	public boolean isEntityInRangeX(Entity entity, float rangeX) {
		if(0<=entity.pos.x-this.pos.x&&entity.pos.x-this.pos.x<=entity.getWidth()||0<=this.pos.x-entity.pos.x&&this.pos.x-entity.pos.x<=this.getWidth())
			return true;
		if(Math.abs(entity.pos.x+entity.getWidth()-this.pos.x)<=rangeX||Math.abs(entity.pos.x-(this.pos.x+this.getWidth()))<=rangeX)
			return true;
		return false;
	}
	public boolean isEntityInRangeY(Entity entity, float rangeY) {
		if(0<=entity.pos.y-this.pos.y&&entity.pos.y-this.pos.y<=entity.getHeight()||0<=this.pos.y-entity.pos.y&&this.pos.y-entity.pos.y<=this.getHeight())
			return true;
		if((Math.abs(entity.pos.y+entity.getHeight()-this.pos.y)<=rangeY)||Math.abs(entity.pos.y-(this.pos.y+this.getHeight()))<=rangeY)
			return true;
		return false;
	}

	public void takeDamage(float attackDamage) {
		if (this.health != 0)
			this.health-=attackDamage;
	}
	public void attackPlayer(Player player, float attackRangeX, float attackRangeY) {
		if(isEntityInRange(player, attackRangeX, attackRangeY ))
			player.takeDamage(attackDamage);
	}
	public Weapon assignWeapon(String weaponID) {
		if ("fists".equals(weaponID))
			return new Fists(map, this);
		else if ("boomerang".equals(weaponID)) {
			return new Boomerang(this.getX(), this.getY() + this.getHeight() / 2, map, this);
		}
		else if ("donut".equals(weaponID)) {
			return new Donut(this.getX(), this.getY() + this.getHeight() / 2, map, this);
		}
		return null;
	}
	public String getDirection() {return direction;}
	public void setDirection(String newDirection) {direction=newDirection;}
	public void loadAnimationFrames(String animation, int priority, float deltaTime, int frameAmount, float frameDuration, String path) {
		if (!animation.equals(currentAnimation) && (animationTimer > frameCount * frameDuration || priority >= currentPriority)) {
			this.animationTimer = 0;
			this.animationTexture = new Texture(path);
			this.currentPriority = priority;
			this.currentAnimation = animation;
			this.frameCount=frameAmount;
		}
		this.frameWidth = animationTexture.getWidth() / frameCount;
		this.frameHeight = animationTexture.getHeight();
		this.animationTimer += deltaTime;
	}
	public void updateAnimation(float deltaTime) {
		this.stateTime += deltaTime;
		this.currentFrame = (int) (stateTime / frameDuration) % frameCount;
	}

	public boolean isTouched() {
		return isTouched;
	}

	public void checkIsTouched() {
		Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		cam.unproject(mousePos);
		if (this.pos.x <= mousePos.x && mousePos.x <= this.pos.x + getWidth() &&
				this.pos.y <= mousePos.y && mousePos.y <= this.pos.y + getHeight()) {
			isTouched = true;
		}
		else isTouched=false;
	}

	public float getPlayerDetectionRangeX() { return playerDetectionRangeX;
	}

	public float getPlayerDetectionRangeY() {return playerDetectionRangeY;
	}
}

