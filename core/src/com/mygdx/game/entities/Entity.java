package com.mygdx.game.entities;


import java.util.ArrayList;
import java.util.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.weapons.Weapon;
import com.mygdx.game.weapons.melees.Fists;
import com.mygdx.game.weapons.rangedweapons.Boomerang;
import com.mygdx.game.world.GameMap;

public abstract class Entity {
	
	protected Vector2 pos;
	protected EntityType type;
	protected float velocityY = 0;
	protected GameMap map;
	protected boolean grounded = false;
	protected float maxHealth;
	protected float health;
	protected float attackDamage;
	private String direction = "Right";
	private String category;
	
	public Entity(float x, float y, EntityType type, String category, GameMap map, float maxHealth, float attackDamage) {
		this.pos = new Vector2(x,y);
		this.type=type;
		this.map=map;
		this.maxHealth=maxHealth;
		this.health=maxHealth;
		this.attackDamage=attackDamage;
		this.category=category;
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
	
	protected void moveX (float amount) {
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
		if ("Fists".equals(weaponID))
			return new Fists(map, this);
		else if ("Boomerang".equals(weaponID)) {
			return new Boomerang(this.getX(), this.getY() + this.getHeight() / 2, map, this);
		}
		return null;
	}
	public String getDirection() {return direction;}
	public void setDirection(String newDirection) {direction=newDirection;}

	public String getCategory() {return category;}
}

