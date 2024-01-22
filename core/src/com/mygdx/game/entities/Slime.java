package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.world.GameMap;

public class Slime extends Enemy {
	
	private static final int SPEED = 60;
	private static final int JUMP_VELOCITY = 6;
	private int air = 0;

	private float playerDetectionRangeX=182;
	private float playerDetectionRangeY=64;
	private float attackRangeX=0;
	private float attackRangeY=0;
	
	Texture image;
	int health;
	
	public Slime(float x, float y, GameMap map, float health, float attackDamage) {
		super(x, y, EntityType.SLIME, map, health, attackDamage);
		image = new Texture("slime.png");
	}
	
	/*public void create (EntitySnapshot snapshot, EntityType type, GameMap map) {
		super.create(snapshot, type, map);
		image = new Texture("slime.png");
		//health = snapshot.getInt("health", 50);
	}*/
	
	@Override
	public void update(float deltaTime, float gravity) {
		attackPlayer(GameMap.player);
		if (grounded) {
			if (air==0) {
				int number = randomNumberGenerator (0,2);
				if (number==0) {
					this.velocityY += JUMP_VELOCITY * getWeight();
				}
				else if (number==1) {
					moveX(SPEED * deltaTime);
				}
				else if (number==2) {
					moveX(-SPEED * deltaTime);
				}
				//System.out.println("KANN WOHL NICHT LAUFEN");
			}
			else {
				this.velocityY += JUMP_VELOCITY * getWeight();
				//System.out.println("KANN WOHL NICHT SPRINGEN");
				air = 0;
			}
		}
		else {
			if (randomNumberGenerator(0, 1) == 0 && !grounded && this.velocityY > 0)
				this.velocityY += JUMP_VELOCITY * getWeight() * deltaTime;
			if (!isPlayerInRange(GameMap.player, playerDetectionRangeX, playerDetectionRangeY)) {
				if (air == 0) {
					int randomNumber2 = randomNumberGenerator(1, 2);
					if (randomNumber2 == 1) {
						air = 1;
					} else if (randomNumber2 == 2) {
						air = 2;
					}
				}
				if (air == 1) {
					moveX(SPEED * deltaTime);
				} else if (air == 2) {
					moveX(-SPEED * deltaTime);
				}
			} else if(GameMap.player.pos.x>=this.pos.x)
				air=1;
			else
				air=2;
		}
		
		
		
		super.update(deltaTime, gravity);
	}
	
	public int randomNumberGenerator (int low, int high) {
		double doubleRandomNumber = Math.random()*high;
        int randomNumber = (int)doubleRandomNumber+low;
        return randomNumber;
	}
	
	public void attackPlayer(Player player) {
		if(grounded&&isPlayerInRange(GameMap.player, attackRangeX, attackRangeY ))
			player.takeDamage(attackDamage);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
		super.render(batch);

	}

}
