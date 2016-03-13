/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.utilities;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.entities.pickups.Pickup;

/**
 *
 * @author looch
 */
public class ItemContainer{
        
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
