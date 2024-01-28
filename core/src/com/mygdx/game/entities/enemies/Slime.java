package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.EntityType;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.world.GameMap;

public class Slime extends Enemy {

	private static float speed;
	private static float jumpVelocity;
	private int air = 0;
	private float playerDetectionRangeX=182;
	private float playerDetectionRangeY=64;
	private float attackRangeX=0;
	private float attackRangeY=0;
	
	Texture image;
	int health;
	
	public Slime(float x, float y, GameMap map, float health, float attackDamage, float speed, float jumpVelocity, String WeaponID) {
		super(x, y, EntityType.SLIME, map, health, attackDamage);
		image = new Texture("Entity/Enemy/Slime/slime.png");
		this.speed=speed;
		this.jumpVelocity=jumpVelocity;
	}

	@Override
	public void update(float deltaTime, float gravity) {
		attackPlayer(GameMap.player);
		if (grounded) {
			if (air==0) {
				int number = randomNumberGenerator (0,2);
				if (number==0) {
					this.velocityY += jumpVelocity * getWeight();
				}
				else if (number==1) {
					moveX(speed * deltaTime);
				}
				else if (number==2) {
					moveX(-speed * deltaTime);
				}
				//System.out.println("KANN WOHL NICHT LAUFEN");
			}
			else {
				this.velocityY += jumpVelocity * getWeight();
				//System.out.println("KANN WOHL NICHT SPRINGEN");
				air = 0;
			}
		}
		else {
			if (randomNumberGenerator(0, 1) == 0 && !grounded && this.velocityY > 0)
				this.velocityY += jumpVelocity * getWeight() * deltaTime;
			if (!isEntityInRange(GameMap.player, playerDetectionRangeX, playerDetectionRangeY)) {
				if (air == 0) {
					int randomNumber2 = randomNumberGenerator(1, 2);
					if (randomNumber2 == 1) {
						air = 1;
					} else if (randomNumber2 == 2) {
						air = 2;
					}
				}
				if (air == 1) {
					moveX(speed * deltaTime);
				} else if (air == 2) {
					moveX(-speed * deltaTime);
				}
			} else if(GameMap.player.getX()>=this.pos.x)
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
		if(grounded&&isEntityInRange(GameMap.player, attackRangeX, attackRangeY ))
			player.takeDamage(attackDamage);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
		super.render(batch);

	}

}
