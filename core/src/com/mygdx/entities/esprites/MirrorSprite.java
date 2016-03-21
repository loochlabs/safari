/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.esprites;

import com.mygdx.entities.ImageSprite;

/**
 *
 * @author saynt
 */
public class MirrorSprite extends ImageSprite{
    
    public MirrorSprite(String key, boolean loop) {
        super(key, loop);
    }
    
    @Override
    public void step(){
        if(reverse){
            reverseStep();
            return;
        }
        
        if(complete || (currentIndex >= totalFrames && !flagForComplete)) return;
        
        currentIndex++;
        
        if(currentIndex > totalFrames){
            if(loop)
                currentIndex = 1;
            else if (flagForComplete) {
                currentIndex = totalFrames;
                reverse = true;
                return;
            }
        }
        
        sprite.setRegion(atlas.findRegion(key, currentIndex));
        
    }
    
    @Override
    public void reverseStep(){
        if(complete || (currentIndex <= 0 && !flagForComplete)) return;
        
        currentIndex--;
        
        if(currentIndex <= 0){
            if(loop)
                currentIndex = totalFrames;
            else if (flagForComplete) {
                currentIndex = 1;
                complete = true;
                //reverse = false;
                return;
            }
        }
        
        sprite.setRegion(atlas.findRegion(key, currentIndex));
    }
    
    @Override
    public void reset() { 
        reverse = false;
        super.reset();
    }
}
