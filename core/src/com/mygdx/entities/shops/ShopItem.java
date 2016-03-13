/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.shops;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.pickups.Pickup;
import com.mygdx.managers.GameStats;

/**
 *
 * @author looch
 * 
 * Description:  Holds the needed item for purchase and the price 
 * 
 */
public class ShopItem extends Pickup{

    //item being purchased
    protected Pickup heldItem;
    
    //cost for item
    private final Pickup costItem;
    private final int COST;
    
    public Pickup getHeldItem() { return heldItem; } 
    public Pickup getCostItem() { return costItem; }
    public int getCost() { return COST; }
    
    public ShopItem(Vector2 pos, Pickup pickup, int cost) {
        super(pos, 20f, 20f);
        
        solidfd.isSensor = true;
        
        this.costItem = pickup;
        this.COST = cost;
    }
    
    public ShopItem(ShopItem pickup){
        this(
                pickup.getPos(),  
                pickup.getCostItem(), 
                pickup.getCost());
        
        heldItem = pickup.getHeldItem();
    }
    
    @Override
    public void init(World world){
        super.init(world);
        
        texture = heldItem.getTexture();
    }
    
    @Override
    public Pickup cpy(){
        return new ShopItem(this);
    }
    
    @Override
    public void alert(){ 
        
        //dish out heldItem to inventory once picked up
        this.canPickup = false;
        if(GameStats.inventory.hasItemAmmount(costItem, COST)){
            GameStats.inventory.subItem(costItem, COST);
            GameStats.inventory.addItem(heldItem, 1, false);
            this.canPickup = true;
            
            texture = null;
        }
        
        super.alert();
        
        
    }
    
}
