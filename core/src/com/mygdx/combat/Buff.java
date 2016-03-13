/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.FrameCounter;

/**
 *
 * @author looch
 */
public abstract class Buff {
    
    protected long duration;
    protected FrameCounter durationFC;
    protected boolean complete = false;
    //protected Texture texture;
    
    //public Texture getTexture() { return texture; }
    public boolean isComplete() { return complete; }
    
    public Buff(long duration){
        //this.texture = texture;
        this.duration = duration;
        
        durationFC = new FrameCounter(duration);
    }
    
    public abstract void applyBuff();
    public abstract void removeBuff();
    
    public void update(){
        if(!complete && durationFC.complete){
            complete = true;
        }
    }
    
    public void start(){
        durationFC.start(GameScreen.player.getFrameManager());
    }
    
    public void refresh(){
        durationFC.start(GameScreen.player.getFrameManager());
    }
    
}
