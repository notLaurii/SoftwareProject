package com.mygdx.game.interfaces;

import java.util.ArrayList;
import java.util.List;

public class ItemMenu {


    private List unlockedItems;
    private List items;

    public ItemMenu(List items, List unlockedItems) {
        this.items=items;
        this.unlockedItems=unlockedItems;
    }
}
