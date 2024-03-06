package com.mygdx.game.interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.weapons.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mygdx.game.management.MyGdxGame.*;

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
        this.backgroundImage = new Texture(Gdx.files.internal("Interfaces/selectMenu.png"));
        this.background = new Image(backgroundImage);
        this.background.setSize((3f/4)*(3f/4)* Gdx.graphics.getHeight(), (3f/4)*Gdx.graphics.getHeight());
        this.background.setPosition((Gdx.graphics.getWidth()-background.getWidth())/2, (Gdx.graphics.getHeight()-background.getHeight())/2);
        super.create();
        float backgroundWidth=this.background.getWidth();
        float backgroundHeight=this.background.getHeight();
        if(Objects.equals(type, "Weapons")) {
            System.out.println(items);
            for (Object weaponId : items) {
                addButton(this.background.getX() + backgroundWidth / 42+i*backgroundWidth/42*13, this.background.getY() + backgroundHeight - backgroundHeight * 13 / 56+y*backgroundHeight/56 * 13, backgroundWidth * 12 / 42, backgroundHeight * 12 / 56, backgroundWidth * 12 / 42, backgroundHeight * 12 / 56, "Entity/Weapons/" + weaponId + ".png");
                if(i==2) {
                    i = 0;
                    y++;
                }
                else i++;
            }
        }
        else if(Objects.equals(type, "Skins")) {
            System.out.println(items);
            for (Object skin : items) {
                addButton(this.background.getX() + backgroundWidth / 42+i*backgroundWidth/42*13, this.background.getY() + backgroundHeight - backgroundHeight * 13 / 56+y*backgroundHeight/56 * 13, backgroundWidth * 12 / 42, backgroundHeight * 12 / 56, backgroundWidth * 12 / 42, backgroundHeight * 12 / 56, "Entity/Player/"+skin+ "/Stand/" + skin + "StandFront.png");
                if(i==2) {
                    i = 0;
                    y++;
                }
                else i++;
            }
        }
    }

    @Override
    public void render() {
        super.render();
    }

    public void onButtonClicked(int buttonIndex) {
        if(Objects.equals(type, "Weapons")) {
            levelManager.getPlayer().switchWeapon(items.get(buttonIndex));
            System.out.println(buttonIndex + " " + items.get(buttonIndex));
            gameManager.updateInventory();
        }
        else if(Objects.equals(type, "Skins")) {
            levelManager.getPlayer().setSkin(items.get(buttonIndex));
            System.out.println(buttonIndex + " " + items.get(buttonIndex));
            gameManager.updateInventory();
        }
    }
}
