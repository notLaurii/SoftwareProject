package com.mygdx.game.entities;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

import com.mygdx.game.world.GameMap;


public enum EntityType {

	PLAYER("player", "player", 16, 24, 40),
	SLIME("slime", "aggressive", 12, 12, 30),
	BOOMERANG("boomerang", "neutral", 12, 12, 0);

	private String id;
	//private Class loaderClass;
	private int width, height;
	private float weight;

	private EntityType(String id, String category, int width, int height, float weight) {
		this.id = id;
		//this.loaderClass = loaderClass;
		this.width = width;
		this.height = height;
		this.weight = weight;
	}

	public String getId() {
		return id;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public float getWeight() {
		return weight;
	}

}
