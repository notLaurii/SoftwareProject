package com.mygdx.game.interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.entities.Player;

import java.util.ArrayList;
import java.util.Objects;

import static com.mygdx.game.management.MyGdxGame.gameManager;
import static com.mygdx.game.management.MyGdxGame.levelManager;

public class ItemMenu extends Interface{


    private String type;
    private ArrayList<String> unlockedItems;
    private ArrayList<String> items;

    public ItemMenu(ArrayList<String> items, ArrayList<String> unlockedItems, String type) {
        this.items=items;
        this.unlockedItems=unlockedItems;
        this.type=type;
        this.create();
    }

    @Override
    public void create() {
        int i=0;
        int y=0;
        this.backgroundImage = new Texture(Gdx.files.internal("Interfaces/darkBackground.png"));
        this.background = new Image(backgroundImage);
        this.background.setSize((3f/4)*(3f/4)* Gdx.graphics.getHeight(), (3f/4)*Gdx.graphics.getHeight());
        this.background.setPosition((Gdx.graphics.getWidth()-background.getWidth())/2, (Gdx.graphics.getHeight()-background.getHeight())/2);
        super.create();
        float backgroundWidth=this.background.getWidth();
        float backgroundHeight=this.background.getHeight();
        if(Objects.equals(type, "Weapons")) {
            for (Object weaponId : unlockedItems) {
                addImageButton(this.background.getX() + backgroundWidth / 42+i*backgroundWidth/42*13, this.background.getY() + backgroundHeight - backgroundHeight * 13 / 56+y*backgroundHeight/56 * 13, backgroundWidth * 12 / 42, backgroundHeight * 12 / 56, backgroundWidth * 12 / 42, backgroundHeight * 12 / 56, "Entity/Weapons/" + weaponId + ".png");
                if(i==2) {
                    i = 0;
                    y++;
                }
                else i++;
            }
            for (Object weaponId : items) {
                if (!unlockedItems.contains(weaponId)) {
                    addImageButton(this.background.getX() + backgroundWidth / 42 + i * backgroundWidth / 42 * 13, this.background.getY() + backgroundHeight - backgroundHeight * 13 / 56 + y * backgroundHeight / 56 * 13, backgroundWidth * 12 / 42, backgroundHeight * 12 / 56, backgroundWidth * 12 / 42, backgroundHeight * 12 / 56, "Entity/Weapons/" + weaponId + "Locked.png");
                    if (i == 2) {
                        i = 0;
                        y++;
                    } else i++;
                }
            }
        }
        else if(Objects.equals(type, "Skins")) {
            for (Object skin : unlockedItems) {
                addImageButton(this.background.getX() + backgroundWidth / 42+i*backgroundWidth/42*13, this.background.getY() + backgroundHeight - backgroundHeight * 13 / 56+y*backgroundHeight/56 * 13, backgroundWidth * 12 / 42, backgroundHeight * 12 / 56, backgroundWidth * 12 / 42, backgroundHeight * 12 / 56, "Entity/Player/"+skin+ "/Stand/" + skin + "StandFront.png");
                if(i==2) {
                    i = 0;
                    y++;
                }
                else i++;
            }
            for (Object skin : items) {
                if(!unlockedItems.contains(skin)) {
                    addImageButton(this.background.getX() + backgroundWidth / 42 + i * backgroundWidth / 42 * 13, this.background.getY() + backgroundHeight - backgroundHeight * 13 / 56 + y * backgroundHeight / 56 * 13, backgroundWidth * 12 / 42, backgroundHeight * 12 / 56, backgroundWidth * 12 / 42, backgroundHeight * 12 / 56, "Entity/Player/" + skin + "/Stand/" + skin + "StandFrontLocked.png");
                    if (i == 2) {
                        i = 0;
                        y++;
                    } else i++;
                }
            }
        }
    }

    @Override
    public void render() {
        super.render();
    }

    public void onButtonClicked(int buttonIndex) {
            Player player = levelManager.getPlayer();
            if (unlockedItems.size() > buttonIndex) {
                if (Objects.equals(type, "Weapons")) {
                    player.switchWeapon(unlockedItems.get(buttonIndex));
                    gameManager.updateInventory();
                    gameManager.getOpenInterfaces().remove(this);
                    removePlayerProjectiles();
                } else if (Objects.equals(type, "Skins")) {
                    player.setSkin(unlockedItems.get(buttonIndex));
                    gameManager.updateInventory();
                    player.update(0, 9.81f);
                    gameManager.getOpenInterfaces().remove(this);
                }
        }
    }
}
