package com.mygdx.game.entities;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

import com.mygdx.game.world.GameMap;


public enum EntityType {

	PLAYER("player", 16, 24, 40),
	SLIME("slime", 12, 12, 30),
	SHOP("shop", 16, 37, 40),

	ELF("elf",16,14,40),
	BOOMERANG("boomerang", 12, 12, 0),
	DONUTPROJECTILE("donutprojectile", 16, 16, 0),
	TNT("tnt",16,16,20),
	GOLD("gold",  8,8,30);

	private String id;
	//private Class loaderClass;
	private int width, height;
	private float weight;

	private EntityType(String id, int width, int height, float weight) {
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
