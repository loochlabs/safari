/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.pickups;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.environments.EnvRoom.Wall_Lock;

/**
 *
 * @author looch
 */
public class Pickup_Key extends Pickup_Item{
    
    protected Wall_Lock lock;
    
    public Wall_Lock getWallLock() { return lock; }
    
    public Pickup_Key(Vector2 pos, float w, float h, Wall_Lock lock){
        super(pos, w, h);
        
        this.lock = lock;
        flagForHud = true;
    }
    
    @Override
    public Pickup cpy(){
        return new Pickup_Key(pos, width, height, lock);
    }
    
}
