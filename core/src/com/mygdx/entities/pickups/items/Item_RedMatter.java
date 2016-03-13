/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.pickups.items;

import com.mygdx.entities.pickups.Pickup_Item;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.pickups.Pickup;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;

/**
 *
 * @author looch
 */
public class Item_RedMatter extends Pickup_Item{

    public Item_RedMatter(){
        this(new Vector2(0,0));
    }
    
    public Item_RedMatter(Vector2 pos) {
        super(pos, 20f*RATIO, 20f*RATIO);
        
        texture = MainGame.am.get(ResourceManager.ITEM_MATTER_RED);
        
        name = "Red Matter";
    }

    @Override
    public Pickup cpy() {
        return new Item_RedMatter(pos);
    }
    
}
