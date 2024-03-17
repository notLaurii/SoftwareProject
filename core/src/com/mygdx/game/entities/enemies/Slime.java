package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.entities.EntityType;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.world.GameMap;

import static com.mygdx.game.management.MyGdxGame.gameManager;
import static com.mygdx.game.management.MyGdxGame.levelManager;

public class Slime extends Enemy {

	private static float speed;
	private static float jumpVelocity;
	private int jumpDirection = 0;
	private float playerDetectionRangeX=182;
	private float playerDetectionRangeY=64;
	private float attackRangeX=0;
	private float attackRangeY=0;
	private boolean canAct=true;
	private boolean justLanded=true;
	
	Texture image;
	int health;
	
	public Slime(float x, float y, GameMap map, float health, float attackDamage, float speed, float jumpVelocity, String WeaponID) {
		super(x, y, EntityType.SLIME, map, health, attackDamage,5);
		image = new Texture("Entity/Enemy/Slime/slime.png");
		this.speed=speed;
		this.jumpVelocity=jumpVelocity;
	}

	@Override
	public void update(float deltaTime, float gravity) {//führt dauerhaft alle möglichen Aktionen aus
		if (canAct) {
			tryAttackingPlayer();
			checkNextDirection();
			moveInJumpDirection(deltaTime);
		}
		super.update(deltaTime, gravity);
	}
	
	public int randomNumberGenerator (int low, int high) {
		double doubleRandomNumber = Math.random()*high;
        int randomNumber = (int)doubleRandomNumber+low;
        return randomNumber;
	}

	public void checkNextDirection() {//Checkt, ob der Spieler verfolgt werden soll oder zufällig gesprungen wird
		if (!isEntityInRange(levelManager.getPlayer(), playerDetectionRangeX, playerDetectionRangeY)) {
			if (jumpDirection == 0) {
				int randomNumber2 = randomNumberGenerator(1, 2);
				if (randomNumber2 == 1) {
					jumpDirection = 1;
				} else if (randomNumber2 == 2) {
					jumpDirection = 2;
				}
			}

		} else if (isEntityInRangeX(levelManager.getPlayer(), attackRangeX))
			jumpDirection=0;
		else if (levelManager.getPlayer().getX() >= this.pos.x)
			jumpDirection = 1;
		else
			jumpDirection = 2;
	}

	public void moveInJumpDirection(float deltaTime){//bewegt den Slime in die festgelegte Sprungrichtung
		if (jumpDirection == 1) {
			moveX(speed * deltaTime);
		} else if (jumpDirection == 2) {
			moveX(-speed * deltaTime);
		}
	}
	
	public void tryAttackingPlayer() {//greift Spieler an, wenn möglich
		if (grounded)
			if (justLanded) {
				attackPlayer(levelManager.getPlayer(), attackRangeX, attackRangeY);
				startCooldown(0.3f);
				jumpDirection = 0;
				justLanded = false;
			} else {
				this.velocityY += jumpVelocity * getWeight();
				justLanded = true;
			}
	}

	public void startCooldown(float cooldown) {
		canAct = false;
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				canAct = true;
			}
		}, cooldown);
	}
	
	@Override
	public void render(SpriteBatch batch) { //Stellt den Slime dar
		batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
		super.render(batch);

	}

}
