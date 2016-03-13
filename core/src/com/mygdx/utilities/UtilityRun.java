/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.utilities;

/**
 *
 * @author looch
 */
@Deprecated
public class UtilityRun implements Runnable{

    public long TIME;
    public boolean running = false;
    public boolean complete = false;
    
    public boolean isRunning() { return running; }
    public boolean isComplete() { return complete; }
    
    public void setTime(long time) { this.TIME = time; }
    
    public UtilityRun(long time){
        this.TIME = time;
    }
    
    @Override
    public void run() {
        try {
            running = true;
            Thread.sleep(TIME);
            running = false;
            complete = true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void reset(){
        complete = false;
    }
    
}
