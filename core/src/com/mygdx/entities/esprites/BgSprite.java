/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.esprites;

import java.util.Random;

/**
 *
 * @author looch
 */
public class BgSprite extends EntitySprite{

    private float SPEED = 1.0f;
    private final Random rng = new Random();
    
    public float getSpeed() { return SPEED; }   
    
    public BgSprite(String key, boolean loop, float x, float y, float scale) {
        super(key, loop,true, false, false,x,y);
        
        SPEED *= rng.nextFloat() + 1; 
        sprite.setScale(scale);
        
    }
    
    @Override
    public void update(){
        sprite.setX(sprite.getX() + SPEED);
        x = sprite.getX();
    }
    
}
