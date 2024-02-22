package com.mygdx.game.entities;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.world.GameMap;

public abstract class Entity {
	
	protected Vector2 pos;
	protected EntityType type;
	protected float velocityY = 0;
	protected GameMap map;
	protected boolean grounded = false;
	protected float maxHealth;
	protected float health; //Setze 0, wenn nicht angreifbar
	protected float attackDamage; //Setze 0, wenn nicht angreifend
	protected float speed;
	protected float jumpVelocity;
	protected String aggression;
	
	public Entity(float x, float y, EntityType type, GameMap map, float maxHealth, float attackDamage, float speed, float jumpVelocity, String aggression) {
		this.pos = new Vector2(x,y);
		this.type=type;
		this.map=map;
		this.maxHealth=maxHealth;
		this.health=maxHealth;
		this.attackDamage=attackDamage;
		this.speed=speed;
		this.jumpVelocity=jumpVelocity;
		this.aggression=aggression;
	}
	
	/*public void create (EntitySnapshot snapshot, EntityType type, GameMap map) {
		this.pos = new Vector2(snapshot.getX(),snapshot.getY());
		this.type = type;
		this.map = map;
	}*/
	public void loadHealthBar() {
		
	}
	public void update (float deltaTime, float gravity) {
		float newY = pos.y;
		
		this.velocityY -= gravity * deltaTime * getWeight();
		newY += this.velocityY * deltaTime;
		
		if (map.doesRectCollideWithMap(pos.x, newY, getWidth(), getHeight())) {
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
	
	protected void moveX (float amount) {
		float newX = pos.x + amount;
		if (!map.doesRectCollideWithMap(newX, pos.y, getWidth(), getHeight()))
			this.pos.x = newX;
	}
	
	/*public EntitySnapshot getSaveSnapshot() {
		
		return new EntitySnapshot(type.getId(), pos.x, pos.y);
	}*/

	public Vector2 getPos() {
		return pos;
	}
	public String getAggression() {
		return aggression;
	}

	public float getMaxHealth() {
		return maxHealth;
	}

	public float getHealth() {
		return health;
	}

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

	public float getSpeed() {
		return type.getWeight();
	}

	public float getJumpVelocity() {
		return this.jumpVelocity;
	}
	public boolean isPlayerInRange(Player player, float rangeX, float rangeY) {
		if(0<=player.pos.x-this.pos.x&&player.pos.x-this.pos.x<=player.getWidth()||0<=this.pos.x-player.pos.x&&this.pos.x-player.pos.x<=this.getWidth())
			if(0<=player.pos.y-this.pos.y&&player.pos.y-this.pos.y<=player.getHeight()||0<=this.pos.y-player.pos.y&&this.pos.y-player.pos.y<=this.getHeight())
		return true;
	if(Math.abs(player.pos.x+player.getWidth()-this.pos.x)<=rangeX||Math.abs(player.pos.x-(this.pos.x+this.getWidth()))<=rangeX) 
			if((Math.abs(player.pos.y+player.getHeight()-this.pos.y)<=rangeY)||Math.abs(player.pos.y-(this.pos.y+this.getHeight()))<=rangeY)
    return true;
	return false;
	}
	public boolean isInPlayerRange(Player player) {
		if(0<=player.pos.x-this.pos.x&&player.pos.x-this.pos.x<=player.getWidth()||0<=this.pos.x-player.pos.x&&this.pos.x-player.pos.x<=this.getWidth())
			if(0<=player.pos.y-this.pos.y&&player.pos.y-this.pos.y<=player.getHeight()||0<=this.pos.y-player.pos.y&&this.pos.y-player.pos.y<=this.getHeight())
		return true;
	if(Math.abs(player.pos.x+player.getWidth()-this.pos.x)<=player.getRangeX()||Math.abs(player.pos.x-(this.pos.x+this.getWidth()))<=player.getRangeX()) 
			if((Math.abs(player.pos.y+player.getHeight()-this.pos.y)<=player.getRangeY())||Math.abs(player.pos.y-(this.pos.y+this.getHeight()))<=player.getRangeY())
    return true;
	return false;
	}

	public void takeDamage(float attackDamage) {
		if (this.health != 0)
		this.health-=attackDamage;
		System.out.println(this.health);
	}
}
