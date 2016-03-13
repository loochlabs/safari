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
public class UtilityThread implements Runnable{
    
    public Thread runner;
    public long attTime;
    public boolean running = false;
    public boolean complete = false;
    
    
    public UtilityThread(){}
   
    public void start(long time){
        attTime = time;
        complete = false;
        
        
        if (runner == null){
            runner = new Thread(this);
            runner.start();
        }
    }
    
    @Override
    public void run(){
        
        //runs for duration of anim time
        while(runner == Thread.currentThread()){
            try{
                running = true;
                Thread.sleep(attTime);
                running = false;
                stop();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    
    public void stop(){
        complete = true;
        
        
        if(runner != null){
            runner = null;
        }
    }

    public boolean isComplete() { return complete; }
    
    public boolean isRunning() {return running; }

    public void setRunning(boolean running) {
        this.running = running;
    }
    
     
    
}
