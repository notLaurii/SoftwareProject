package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityType;
import com.mygdx.game.world.GameMap;

public class Enemy extends Entity {
	
	protected Texture healthBar;
	private static final int NUM_HEALTH_BARS = 23;
    private static final int HEALTH_BAR_WIDTH = 24;
    private static final int HEALTH_BAR_HEIGHT = 6;
	private int goldDrop;
	public Enemy(float x, float y, EntityType type, GameMap map, float health, float attackDamage, int goldDrop) {
		super(x, y, type, "enemy", map, health, attackDamage);
		healthBar=new Texture("Entity/Enemy/Overlay/EnemyHealthBar.png");
		this.goldDrop = goldDrop;
	}

	@Override
	public void render(SpriteBatch batch) {
        // HealthBar rendern
	    float healthRatio = health / maxHealth;
        int numBarsToShow = (int) (healthRatio * NUM_HEALTH_BARS);
        int textureY = NUM_HEALTH_BARS - numBarsToShow;

        // Zeichnen der Bilder
        batch.draw(healthBar, pos.x+getWidth()/2-HEALTH_BAR_WIDTH/2, (float) (pos.y + 1.1*getHeight()), HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT,
                   0, HEALTH_BAR_HEIGHT * textureY, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT, false, false);
    }
	public int getGoldDrop() { return goldDrop;}

}
