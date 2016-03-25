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
            boolean xflip, boolean yflip, float scale, boolean reverse, boolean pause, 
            boolean flagForRenderSort, boolean flagForRenderTop) {
        super(pos, w, h);
        
        isprite = new ImageSprite(key, loop, flagForComplete, xflip, yflip, pos.x, pos.y, scale, reverse, pause);
        
        isprite.sprite.setBounds(pos.x - width/2, pos.y - height/2, width, height);
        
        this.flaggedForRenderSort = flagForRenderSort;
        this.flaggedForRenderTop = flagForRenderTop;
        
    }
    
    public EntitySprite(Vector2 pos, float w, float h, String key, float scale, 
            boolean flagForRenderSort, boolean flagForRenderTop){
        this(pos,w,h,key, false, false, false, false, scale, false, false, flagForRenderSort, flagForRenderTop);
    }
    
    public EntitySprite(EntitySprite esprite, float x, float y, float w, float h, 
            boolean flagForRenderSort, boolean flagForRenderTop){
        this(new Vector2(x,y), w,h, esprite.isprite.getKey(), esprite.isprite.getLoop(), 
                esprite.isprite.getFlagForComplete(), esprite.isprite.getXFlip(),
                esprite.isprite.getYFlip(), 1.0f, false, false,
                flagForRenderSort, flagForRenderTop);
        
    }
    
    @Override
    public void setPosition(Vector2 pos){
        super.setPosition(pos);
        
        if(isprite != null){
            isprite.sprite.setPosition(
                    pos.x - isprite.sprite.getWidth()/2, pos.y - isprite.sprite.getHeight()/2);
        }
    }
    
    @Override
    public void init(World world) {}
    
    @Override
    public void update(){
        super.update();
        
        if(isprite.isComplete()){
            this.dispose();
        }
        
        if(isprite.sprite.getX() != pos.x - width/2){
            isprite.sprite.setX(pos.x - width/2);
        }
        if(isprite.sprite.getY() != pos.y - height/2){
            isprite.sprite.setY(pos.y - height/2);
        }
    }
    
}
