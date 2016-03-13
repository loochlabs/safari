/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.shops;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.pickups.Pickup;
import com.mygdx.entities.pickups.items.StatFrag_Life;

/**
 *
 * @author looch
 */
public class ShopItem_LifeFrag extends ShopItem{

    public ShopItem_LifeFrag(Vector2 pos, Pickup pickup, int cost) {
        super(pos,pickup, cost);
        
        heldItem = new StatFrag_Life(pos);
    }
    
    public ShopItem_LifeFrag(ShopItem pickup){
        super(pickup);
        
        heldItem = new StatFrag_Life(pos);
    }
}
