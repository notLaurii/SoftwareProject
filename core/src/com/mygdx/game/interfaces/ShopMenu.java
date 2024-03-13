package com.mygdx.game.interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.ShopOwner;

import java.util.ArrayList;
import java.util.Objects;

import static com.mygdx.game.management.MyGdxGame.gameManager;
import static com.mygdx.game.management.MyGdxGame.levelManager;

public class ShopMenu extends Interface{


    private String type;
    private ArrayList<String> unlockedItems;
    private ArrayList<String> purchasableItems;
    private ShopOwner owner;

    public ShopMenu(ArrayList<String> purchasableItems, ArrayList<String> unlockedItems, String type, ShopOwner owner) {
        this.purchasableItems=purchasableItems;
        this.unlockedItems=unlockedItems;
        this.type=type;
        this.owner=owner;
        this.create();
    }

    @Override
    public void create() {
        removePlayerProjectiles();
        int i=0;
        int y=0;
        this.backgroundImage = new Texture(Gdx.files.internal("Interfaces/shopMenu"+type+".png"));
        this.background = new Image(backgroundImage);
        this.background.setSize((3f/4)*(3f/4)* Gdx.graphics.getHeight(), (3f/4)*Gdx.graphics.getHeight());
        this.background.setPosition((Gdx.graphics.getWidth()-background.getWidth())/2, (Gdx.graphics.getHeight()-background.getHeight())/2);
        super.create();
        float backgroundWidth=this.background.getWidth();
        float backgroundHeight=this.background.getHeight();
        addButton(this.background.getX() + backgroundWidth / 64*2, this.background.getY() + backgroundHeight - backgroundHeight * 17 / 76, backgroundWidth * 8 / 64, backgroundHeight * 7 / 76, backgroundWidth * 8 / 64, backgroundHeight * 7 / 76, "Interfaces/Buttons/ArrowBack.png");
        addButton(this.background.getX() + backgroundWidth / 64*54, this.background.getY() + backgroundHeight - backgroundHeight * 17 / 76, backgroundWidth * 8 / 64, backgroundHeight * 7 / 76, backgroundWidth * 8 / 64, backgroundHeight * 7 / 76, "Interfaces/Buttons/ArrowForward.png");
        if(Objects.equals(type, "Weapons")) {
            for (Object weaponId : purchasableItems) {
                if (!unlockedItems.contains(weaponId)) {
                    addButton(this.background.getX() + backgroundWidth / 64*3 + i * backgroundWidth / 64 * 13, this.background.getY() + backgroundHeight - backgroundHeight * 33 / 76 + y * backgroundHeight / 76 * 13, backgroundWidth * 12 / 64, backgroundHeight * 12 / 76, backgroundWidth * 12 / 64, backgroundHeight * 12 / 76, "Entity/Weapons/" + weaponId + ".png");
                    if (i == 3) {
                        i = 0;
                        y++;
                    } else i++;
                }
            }
        }
        else if(Objects.equals(type, "Skins")) {
            for (Object skin : purchasableItems) {
                if (!unlockedItems.contains(skin)) {
                    addButton(this.background.getX() + backgroundWidth / 64*5 + i * backgroundWidth / 64 * 19, this.background.getY() + backgroundHeight - backgroundHeight * 45 / 76 + y * backgroundHeight / 76 * 25, backgroundWidth * 16 / 64, backgroundHeight * 24 / 76, backgroundWidth * 16 / 64, backgroundHeight * 24 / 76, "Entity/Player/" + skin + "/Stand/" + skin + "StandFront.png");
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
        if (buttonIndex == 0) {
            if (Objects.equals(type, "Weapons")) {
                owner.setMenu(new ShopMenu(gameManager.getPurchasableSkins(), gameManager.getUnlockedSkins(), "Skins", owner));
            } else if (Objects.equals(type, "Skins")) {
                owner.setMenu(new ShopMenu(gameManager.getPurchasableWeapons(), gameManager.getUnlockedWeapons(), "Weapons", owner));
            }
        } else if (buttonIndex == 1) {
            if (Objects.equals(type, "Weapons")) {
                owner.setMenu(new ShopMenu(gameManager.getPurchasableSkins(), gameManager.getUnlockedSkins(), "Skins", owner));
            } else if (Objects.equals(type, "Skins")) {
                owner.setMenu(new ShopMenu(gameManager.getPurchasableWeapons(), gameManager.getUnlockedWeapons(), "Weapons", owner));
            }
        } //0 und 1 nur gleich, da es keine kaufbaren fähigkeiten gibt
        else {
            Player player = levelManager.getPlayer();
            //if(Kaufbedingung) {
            //+ Geld ausgeben
            unlockedItems.add(purchasableItems.get(buttonIndex - 2));
            purchasableItems.remove(purchasableItems.get(buttonIndex - 2));
            if (Objects.equals(type, "Weapons")) {
                gameManager.setUnlockedWeapons(unlockedItems);
            } else if (Objects.equals(type, "Skins")) {
                gameManager.setUnlockedSkins(unlockedItems);
            }
            owner.setMenu(new ShopMenu(purchasableItems, unlockedItems, type, owner));
         }
        }
    }

