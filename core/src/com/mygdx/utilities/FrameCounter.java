/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.utilities;

import com.mygdx.game.MainGame;
import com.mygdx.managers.FrameManager;

/**
 *
 * @author looch
 */
public class FrameCounter {
 
    public boolean running = false, complete = false;
    public int CURRENT_FRAME, MAX_FRAME;
    
    public void setTime(float time) { 
        MAX_FRAME = (int)(time * Math.pow(MainGame.STEP,-1));
    }
    
    public float getTimeRemaining(){
        return (MAX_FRAME - CURRENT_FRAME) * MainGame.STEP;
    }
    
    public FrameCounter(float time){
        
        this.setTime(time);
    }
    
    public void start(FrameManager fm){
        fm.add(this);
        reset();
    }
    
    public void stop(FrameManager fm){
        fm.remove(this);
    }
    
    public void step(){
        if(complete) return;
        
        CURRENT_FRAME++;
        running = true;
        
        if(CURRENT_FRAME >= MAX_FRAME){
            complete();
        }
    }
    
    
    public void reset(){
        running = false;
        complete = false;
        
        CURRENT_FRAME = 0;
    }
    
    public void complete(){
        complete = true;
        running = false;
    }
    
}
