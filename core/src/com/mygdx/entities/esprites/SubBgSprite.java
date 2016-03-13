/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.esprites;

import com.badlogic.gdx.math.Vector2;
import java.util.Random;

/**
 *
 * @author looch
 */
public class SubBgSprite extends EntitySprite{

    private Vector2 dv, cv, pos;
    private final float SPEED = 1.0f, RAD;
    private final Random rng = new Random();
    
    
    //  pos - starting position
    //@param -
    //  cv - center, destination vector
    //  rad - radius of env circle
    public SubBgSprite(Vector2 pos, Vector2 cv, float rad) {
        super("bg-sub-piece1", false, false, false, false, pos.x, pos.y);
        
        this.pos = pos;
        this.cv = cv;
        this.RAD = rad;
        
        Vector2 v = this.cv.cpy().sub(pos).nor();
        this.dv = v;
        this.dv.scl(SPEED);
        
        sprite.rotate(30*rng.nextFloat());
        
        
    }
    
    @Override
    public void update(){
        x += dv.x;
        y += dv.y;
        sprite.setPosition(x, y);
        
        scale();
        
        if(dist() < 100f){
            resetPos();
        }
    }
    
    private void scale(){
        sprite.setScale(dist() / RAD);
    }
    
    private float dist(){
        return cv.dst(x,y);
    }
    
    private void resetPos(){
        Vector2 v = dv.cpy().scl(1/SPEED).scl(-RAD * 1.1f);
        x += v.x;
        y += v.y;
        sprite.setPosition(x, y);
    }
    
}
