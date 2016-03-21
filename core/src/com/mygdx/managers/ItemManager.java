/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.managers;

import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.pickups.Pickup;
import com.mygdx.entities.pickups.Pickup_Life;
import com.mygdx.entities.pickups.Item_DarkMatter;

/**
 *
 * @author looch
 */
public class ItemManager {
    
    public static Array<Pickup> itemPool = new Array<Pickup>();
    
    public static void load(){
        loadItemPool();
    }
    
    public static void loadItemPool(){
        itemPool.add(new Item_DarkMatter());
        itemPool.add(new Item_DarkMatter());
        itemPool.add(new Item_DarkMatter());
        
        itemPool.add(new Pickup_Life());
        itemPool.add(new Pickup_Life());
        itemPool.add(new Pickup_Life());
        
        
    }
    
    public static Array<Pickup> generateItems(int num){
        Array<Pickup> items = new Array<Pickup>();
        
        for(int i = 0; i < num; i++){
            items.add(itemPool.random().cpy());
        }
        
        return items;
    }
    
    
    
}
