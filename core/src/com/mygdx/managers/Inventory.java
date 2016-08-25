/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.pickups.Pickup;

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
    
 
    private class ItemContainer{
        
        public Object data;
        public Texture texture;
        public int count;
        public Pickup pickup;
        public boolean newAlert = true;
        public boolean flagForHud = false;
        
        public ItemContainer(Pickup pickup, boolean flagForHud){
            this.pickup = pickup;
            this.data = pickup.getName();
            this.texture = pickup.getTexture();
            this.flagForHud = flagForHud;
        }
        
        public void addAmmount(int ammount){
            count = count + ammount > 999 ? 999 : count + ammount;
        }
        
        public void removeAmmount(int ammount){
            count = count - ammount < 0 ? 0 : count - ammount;
        }
        
        public boolean empty(){
            return count <= 0;
        }
    }
    
}
