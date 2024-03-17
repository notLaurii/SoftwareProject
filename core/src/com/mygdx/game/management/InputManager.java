package com.mygdx.game.management;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Interactable;
import com.mygdx.game.interfaces.Interface;
import com.mygdx.game.interfaces.Inventory;

import java.util.ArrayList;

import static com.mygdx.game.management.MyGdxGame.gameManager;
import static com.mygdx.game.management.MyGdxGame.levelManager;

public class InputManager {

    public void update(float deltaTime) {
        if (!levelManager.isGameOver()) {
            ArrayList<Interface> openInterfaces = gameManager.getOpenInterfaces();
            Inventory inventory = gameManager.getInventory();
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                if (!openInterfaces.isEmpty()) {
                    if (inventory == openInterfaces.get(openInterfaces.size() - 1))
                        inventory = null;
                    openInterfaces.remove(openInterfaces.get(openInterfaces.size() - 1));
                }
                if (inventory != null) {
                    gameManager.updateInventory();
                    inventory = gameManager.getInventory();
                }
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
                if (inventory != null) {
                    openInterfaces.remove(inventory.getMenu());
                    openInterfaces.remove(inventory);
                    inventory = null;
                } else if (gameManager.isGameRunning()) {
                    inventory = new Inventory();
                    inventory.create();
                    gameManager.setGameRunning(false);

                }

            } else if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                if (gameManager.getGameRunning()) {
                    boolean interactedWithInteractable = false;
                    for (Entity entity : levelManager.getEntities()) {
                        entity.checkIsTouched();
                        if (entity instanceof Interactable && entity.isEntityInRange(levelManager.getPlayer(), entity.getPlayerDetectionRangeX(), entity.getPlayerDetectionRangeY()) && entity.isTouched()) {
                            ((Interactable) entity).interactWithPlayer();
                            gameManager.setGameRunning(false);
                            interactedWithInteractable = true;
                        }
                    }
                    if (!interactedWithInteractable)
                        levelManager.getPlayer().actOnLeftClick(deltaTime);
                }
            }
            gameManager.setOpenInterfaces(openInterfaces);
            gameManager.setInventory(inventory);
        }
    }
}
