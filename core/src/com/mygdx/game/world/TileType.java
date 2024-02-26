package com.mygdx.game.world;

import java.util.HashMap;

public enum TileType {
	
	GROUND_TOP_LEFT(1, true),
	GROUND_TOP_MID(2, true),
	GROUND_TOP_RIGHT(3, true),
	MUSHROOM_TOP_LEFT(4, false),
	MUSHROOM_TOP_MID(5, false),
	MUSHROOM_TOP_RIGHT(6, false),
	TREE_TOP_LEFT(7, false),
	TREE_TOP_MIDDLE_LEFT(8, false),
	TREE_TOP_MIDDLE_RIGHT(9, false),
	TREE_TOP_RIGHT(10, false),
	BIG_ROCK_TOP_LEFT(11, false),
	BIG_ROCK_TOP_MID(12, false),
	BIG_ROCK_TOP_RIGHT(13, false),
	BIG_BOX_TOP_LEFT(14, true),
	BIG_BOX_TOP_RIGHT(15, true),
	GROUND_MIDDLE_LEFT(16, true),
	GROUND_MIDDLE_MID(17, true),
	GROUND_MIDDLE_RIGHT(18, true),
	MUSHROOM_MIDDLE_TOP_LEFT(19, false),
	MUSHROOM_MIDDLE_TOP_MID(20, false),
	MUSHROOM_MIDDLE_TOP_RIGHT(21, false),
	TREE_MIDDLE_TOP_LEFT(22, false),
	TREE_MIDDLE_TOP_MID_LEFT(23, false),
	TREE_MIDDLE_TOP_MID_RIGHT(24, false),
	TREE_MIDDLE_TOP_RIGHT(25, false),
	BIG_ROCK_BOTTOM_LEFT(26, false),
	BIG_ROCK_BOTTOM_MID(27, false),
	BIG_ROCK_BOTTOM_RIGHT(28, false),
	BIG_BOX_BOTTOM_LEFT(29, true),
	BIG_BOX_BOTTOM_RIGHT(30, true),
	GROUND_BOTTOM_LEFT(31, true),
	GROUND_BOTTOM_MID(32, true),
	GROUND_BOTTOM_RIGHT(33, true),
	AIR(34, false),
	MUSHROOM_MIDDLE_BOTTOM(35, false),
	SMALL_BOX(36, true),
	TREE_MIDDLE_BOTTOM_LEFT(37, false),
	TREE_MIDDLE_BOTTOM_MID_LEFT(38, false),
	TREE_MIDDLE_BOTTOM_MID_RIGHT(39, false),
	TREE_MIDDLE_BOTTOM_RIGHT(40, false),
	BIG_BUSH_TOP_LEFT(41, false),
	BIG_BUSH_TOP_MID(42, false),
	BIG_BUSH_TOP_RIGHT(43, false),
	SMALL_BUSH_LEFT(44, false),
	SMALL_BUSH_RIGHT(45, false),
	FLOWERS_HANGING(46, false),
	VINE_PART(47, false),
	GRASS_HANGING(48, false),
	MUSHROOM_BOTTOM_LEFT(49, false),
	MUSHROOM_BOTTOM_RIGHT(50, false),
	SMALL_ROCK(51, false),
	SMALL_MUSHROOMS(52, false),
	TREE_BOTTOM_LEFT(53, false),
	TREE_BOTTOM_RIGHT(54, false),
	FLOWERS_GROUNDED(55, false),
	BIG_BUSH_BOTTOM_LEFT(56, false),
	BIG_BUSH_BOTTOM_MID(57, false),
	BIG_BUSH_BOTTOM_RIGHT(58, false),
	VINE_END(59, false),
	BACKGROUND(60, false);

	public static final int TILE_SIZE = 16;
	
	private int id;
	private boolean collidable;
	private float damage;
	
	private TileType (int id, boolean collidable) {
		this(id, collidable, 0);
	}
	
	private TileType (int id, boolean collidable, float damage) {
		this.id=id;
		this.collidable=collidable;
		this.damage=damage;
	}

	public int getId() {
		return id;
	}

	public boolean isCollidable() {
		return collidable;
	}


	public float getDamage() {
		return damage;
	}
	
	private static HashMap<Integer, TileType> tileMap;
	
	static {
		tileMap = new HashMap<Integer, TileType>();
		for (TileType tileType : TileType.values()) {
			tileMap.put(tileType.getId(), tileType);
		}
	}
	
	public static TileType getTileTypeById (int id) {
		return tileMap.get(id);
	}
}
