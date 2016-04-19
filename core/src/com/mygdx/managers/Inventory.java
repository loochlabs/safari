/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.managers;

import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.pickups.Pickup;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.ItemContainer;

/**
 *
 * @author looch
 */
public class Inventory {
    
    private Array<ItemContainer> items = new Array<ItemContainer>();
    private Array<ItemContainer> itemsToRemove = new Array<ItemContainer>();

    public Array<ItemContainer> getItems() { return items; }
    
    public void addItem(Pickup pickup, int ammount, boolean flagForHud){
        boolean duplicate = false;
        for(ItemContainer item: items){
            if(item.data != null && item.data.equals(pickup.getName()))
                duplicate = true;
        }
        
        if(!duplicate){
            items.add(new ItemContainer(pickup, flagForHud));
            items.peek().addAmmount(ammount);
        }else{
            for(ItemContainer item: items){
                if(item.data != null && item.data.equals(pickup.getName()))
                    item.addAmmount(ammount);
            }
        }
        
        GameScreen.overlay.getBarHud().addItem(pickup);
        
    }
    
    public void subItem(Pickup pickup, int ammount){
        for(ItemContainer item: items){
            if(item.data != null && item.data.equals(pickup.getName())){
                item.removeAmmount(ammount);
            }
            if(item.count <= 0){
                itemsToRemove.add(item);
            }
        }
        
        for(ItemContainer item: itemsToRemove){
            items.removeValue(item, false);
        }
        if(itemsToRemove.size > 0){
            itemsToRemove.clear();
        }
        
        for(int i = 0; i < ammount; i++){
            GameScreen.overlay.getBarHud().removeItem(pickup);
        }
    }
    
    public int subAll(Pickup pickup){
        int count = 0;
        for(ItemContainer item: items){
            if(item.data != null && item.data.equals(pickup.getName())){
                count = item.count;
                itemsToRemove.add(item);
            }
        }
        
        for(ItemContainer item: itemsToRemove){
            items.removeValue(item, false);
        }
        if(itemsToRemove.size > 0){
            itemsToRemove.clear();
        }
        
        for(int i = 0; i < count; i++){
            GameScreen.overlay.getBarHud().removeItem(pickup);
        }
        
        return count;
    }
    
    public boolean hasItem(Pickup pickup){
        for(ItemContainer container: items){
            if(container.pickup.equals(pickup)){
                return true;
            }
        }
        return false;
    }
    
    public boolean hasItemByName(String name){
        for(ItemContainer container: items){
            if(container.pickup.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
    
    public boolean hasItemAmmount(Pickup pickup, int cost){
        for (ItemContainer item : items) {
            if (item.data.equals(pickup.getName()) && item.count >= cost) {
                return true;
            }
        }
        
        return false;
    }
    
    public ItemContainer getItemByName(String str){
        
        for(ItemContainer i : items){
            if(i.data.toString().equals(str)){
                return i;
            }
        }
        
        return null;
    }
    
    public void dispose(){
        items.clear();
        itemsToRemove.clear();
    }
    
    
}
