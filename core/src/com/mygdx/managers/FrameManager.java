/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.managers;

import com.badlogic.gdx.utils.Array;
import com.mygdx.utilities.FrameCounter;

/**
 *
 * @author looch
 */
public class FrameManager {
    
    
    private final Array<FrameCounter> fCounters = new Array<FrameCounter>();
    private final Array<FrameCounter> fCountersToRemove = new Array<FrameCounter>();
    
    public void update(){
        for(FrameCounter counter: fCounters){
            counter.step();
            
            if(counter.complete){
                fCountersToRemove.add(counter);
            }
        }
        
        for(FrameCounter counter: fCountersToRemove){
            fCounters.removeValue(counter, false);
        }
        
        fCountersToRemove.clear();
    }
    
    public void add(FrameCounter counter){
        fCounters.add(counter);
    }
    
    public void remove(FrameCounter counter){
        if(fCounters.contains(counter, false)){
            fCountersToRemove.add(counter);
        }
    }
    
}
