package com.mygdx.game.interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import static com.mygdx.game.management.MyGdxGame.gameManager;
import static com.mygdx.game.management.MyGdxGame.levelManager;

public class Inventory extends Interface {


    private ItemMenu menu;
    @Override
    public void create()
    {
        this.backgroundImage = new Texture(Gdx.files.internal("Interfaces/inventory.png"));
        this.background = new Image(backgroundImage);
        this.background.setSize((16f/19)*(2f/3)* Gdx.graphics.getHeight(), (2f/3)*Gdx.graphics.getHeight());
        this.background.setPosition((Gdx.graphics.getWidth()-background.getWidth())/2, (Gdx.graphics.getHeight()-background.getHeight())/2);
        super.create();
        addWeaponButton(); //Add weaponButton to the stage to perform rendering and take input.
        addSkinButton();
    }

    public void addWeaponButton() {
        addButton(this.background.getX()+this.background.getWidth()/64*8f, this.background.getY()+this.background.getHeight()/76*42f,this.background.getWidth()*10/64, this.background.getHeight()*10/76, this.background.getWidth()*8/64, this.background.getHeight()*8/76, "Entity/Weapons/"+levelManager.getPlayer().getWeaponID()+".png");
    }

    public void addSkinButton() {
        addButton(this.background.getX()+this.background.getWidth()/64*24, this.background.getY()+this.background.getHeight()/76*34, this.background.getWidth()*20/64, this.background.getHeight()*29/76, this.background.getWidth()*16/64, this.background.getHeight()*24/76,"Entity/Player/"+levelManager.getPlayer().getSkin()+"/Stand/"+levelManager.getPlayer().getSkin()+"StandFront.png");
    }

    @Override
    public void onButtonClicked(int buttonIndex) {
        if (gameManager.getOpenInterfaces().contains(this)) {
            if (buttonIndex == 0) {
                menu = new ItemMenu(gameManager.getAllWeapons(), gameManager.getUnlockedWeapons(), "Weapons");
            } else if (buttonIndex == 1) {
                menu = new ItemMenu(gameManager.getAllSkins(), gameManager.getUnlockedSkins(), "Skins");
            }
        }
    }
    public ItemMenu getMenu() {
        return menu;
    }

    public void setMenu(ItemMenu menu) {
        this.menu = menu;
    }
}