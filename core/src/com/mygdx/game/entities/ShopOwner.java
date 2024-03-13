package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.interfaces.ShopMenu;
import com.mygdx.game.world.GameMap;

import static com.mygdx.game.management.MyGdxGame.*;

public class ShopOwner extends Interactable {


    private ShopMenu shopMenu;
    private boolean menuOpen;
    Texture image;

    public ShopOwner(float x, float y, GameMap map) {
        super(x, y, EntityType.SHOP, map, 0, 0);
        this.image = new Texture("Entity/Player/PridePanda/Stand/PridePandaStandRight.png");
        this.playerDetectionRangeX=182;
        this.playerDetectionRangeY=64;
    }

    @Override
    public void update(float deltaTime, float gravity) {
        if(isEntityInRange(levelManager.getPlayer(), playerDetectionRangeX, playerDetectionRangeY)) {
            this.image= new Texture("Entity/NPC/ShopOwner/ShopOwnerInRange.png");
        }
        else this.image = new Texture("Entity/NPC/ShopOwner/ShopOwner.png");
        super.update(deltaTime, gravity);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
    }

    @Override
    public void interactWithPlayer() {
        this.shopMenu=new ShopMenu(gameManager.getPurchasableWeapons(), gameManager.getUnlockedWeapons(), "Weapons", this);
    }

    public ShopMenu getShopMenu() {
        return shopMenu;
    }

    public void setMenu(ShopMenu menu) {
        if(this.shopMenu!=null)
            gameManager.getOpenInterfaces().remove(shopMenu);
        this.shopMenu=menu;}
}
