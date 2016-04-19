/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.pickups;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.managers.GameStats;

/**
 *
 * @author looch
 */
public class Pickup_Item extends Pickup {

    protected boolean flagForHud = false;
    
    public Pickup_Item(Vector2 pos, float w, float h) {
        super(pos, w, h);
        
        
        flagForHud = true;
    }
    
    public Pickup_Item(Pickup pickup){
        super(pickup);
        
        
        flagForHud = true;
    }
    
    @Override
    public void init(World world){
        super.init(world);
        
        body.setLinearDamping(5.0f);
    }
    
    @Override
    public void death(){
        GameStats.inventory.addItem(this, 1, flagForHud);
        super.death();
    }
    
    public void effect(){}
    
    @Override
    public Pickup cpy(){
        return new Pickup_Item(pos, width, height);
    }
    
}
