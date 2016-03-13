/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.pickups.statups;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.pickups.Pickup;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import com.mygdx.utilities.SoundObject_Sfx;

/**
 *
 * @author saynt
 */
public class Pickup_Statup extends Pickup{
    
    //body, image width/height
    private final float BASE_W = 35f, BASE_H = 35f;
    
    public Pickup_Statup(Vector2 pos) {
        super(pos, 35f*RATIO,35f*RATIO);    //width, height values are for pickup sensor
        
        bd.linearDamping = 5.0f;
        
        //texture = MainGame.am.get(ResourceManager.ITEM_LIFE);
        
        width = BASE_W;
        height = BASE_H;
        iw = width*2;
        ih = height*2;
        
        
        name = "Stat Orb";
        
        //sound
        pickupSound = new SoundObject_Sfx(ResourceManager.SFX_PICKUP_SKILL);
        
    }
    
    public Pickup_Statup(){
        this(new Vector2(0,0));
    }
    
    
    @Override
    public Pickup_Statup cpy(){
        return new Pickup_Statup();
    }
    
}
