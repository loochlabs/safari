/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.esprites;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.Entity;
import com.mygdx.entities.ImageSprite;

/**
 *
 * @author saynt
 */
public class EntitySprite extends Entity {

    
    public EntitySprite(Vector2 pos, float w, float h, String key, boolean loop, boolean flagForComplete, 
            boolean xflip, boolean yflip, float scale, boolean reverse, boolean pause) {
        super(pos, w, h);
        
        isprite = new ImageSprite(key, loop, flagForComplete, xflip, yflip, pos.x, pos.y, scale, reverse, pause);
    }
    
    public EntitySprite(Vector2 pos, float w, float h, String key, float scale){
        this(pos,w,h,key, false, false, false, false, scale, false, false);
    }
    
    public EntitySprite(EntitySprite esprite, float x, float y, float w, float h){
        this(new Vector2(x,y), w,h, esprite.isprite.getKey(), esprite.isprite.getLoop(), 
                esprite.isprite.getFlagForComplete(), esprite.isprite.getXFlip(),
                esprite.isprite.getYFlip(), 1.0f, false, false);
        
    }

    @Override
    public void init(World world) {
        
    }
    
}
