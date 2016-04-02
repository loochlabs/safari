/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.managers;


/**
 *
 * @author looch
 */
public class StateManager {
    
    public enum State { BEGIN, PLAYING, PAUSED, END, FALLING };
    
    private State state;
    private boolean paused = false;
    public boolean respawn = false;
    
    public State getState() { return state; }
    public boolean isPaused() { return paused; }

    public void setPaused(boolean paused) { this.paused = paused; }
    
    
    public StateManager(){
        state = State.PLAYING;
    }
    
    //todo: change parameter to State, not integer
    public void setState(int index){
        switch(index){
            case(0):
                state = State.BEGIN;
                break;
            case(1):
                state = State.PLAYING;
                break;
            case(2):
                state = State.PAUSED;
                break;
            case(3):
                state = State.END;
                break;
            case(4):
                state = State.FALLING;
                break;
            default:
                state = null;
                break;
        }
    }
    
    
}
