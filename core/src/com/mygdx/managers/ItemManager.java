/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.managers;

import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.pickups.Pickup;
import com.mygdx.entities.pickups.Pickup_Life;
import com.mygdx.entities.pickups.items.Item_DarkMatter;
import com.mygdx.entities.shops.ShopItem;

/**
 *
 * @author looch
 */
public class ItemManager {
    
    public static Array<Pickup> itemPool = new Array<Pickup>();
    public static Array<ShopItem> shopPool = new Array<ShopItem>();
    
    public static void load(){
        loadItemPool();
        loadShopPool();
    }
    
    public static void loadItemPool(){
        itemPool.add(new Item_DarkMatter());
        itemPool.add(new Item_DarkMatter());
        itemPool.add(new Item_DarkMatter());
        //itemPool.add(new Item_DarkMatter());
        //itemPool.add(new Item_DarkMatter());
        
        itemPool.add(new Pickup_Life());
        itemPool.add(new Pickup_Life());
        itemPool.add(new Pickup_Life());
        
        //itemPool.add(new Item_GreenMatter());
        //itemPool.add(new Item_RedMatter());
        //itemPool.add(new Item_WhiteMatter());
        //itemPool.add(new Item_YellowMatter());
        
    }
    
    public static Array<Pickup> generateItems(int num){
        Array<Pickup> items = new Array<Pickup>();
        
        for(int i = 0; i < num; i++){
            items.add(itemPool.random().cpy());
        }
        
        return items;
    }
    
    public static void loadShopPool(){
        
        /*
        shopPool.add(
                new ShopItem_LifeOrb(
                        new Vector2(0,0),
                        new StatFrag_Life(new Vector2(0,0)),
                        2));
        
        shopPool.add(
                new ShopItem_EnergyOrb(
                        new Vector2(0,0),
                        new StatFrag_Energy(new Vector2(0,0)),
                        2));
        
        shopPool.add(
                new ShopItem_DamageOrb(
                        new Vector2(0,0), 
                        new StatFrag_Damage(new Vector2(0,0)),
                        2));
        
        shopPool.add(
                new ShopItem_SpeedOrb(
                        new Vector2(0,0),
                        new StatFrag_Speed(new Vector2(0,0)),
                        2));
        
        
        shopPool.add(
            new ShopItem_LifeFrag(
                        new Vector2(0,0),
                        new Item_DarkMatter(new Vector2(0,0)),
                        5));
        
        shopPool.add(
            new ShopItem_EnergyFrag(
                        new Vector2(0,0),
                        new Item_DarkMatter(new Vector2(0,0)),
                        5));
        shopPool.add(
            new ShopItem_DamageFrag(
                        new Vector2(0,0), 
                        new Item_DarkMatter(new Vector2(0,0)),
                        5));
        
        shopPool.add(
            new ShopItem_SpeedFrag(
                        new Vector2(0,0),
                        new Item_DarkMatter(new Vector2(0,0)),
                        5));
        
                */
                
    }
    
    
}
