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
public class GameKeyLibrary {
    
    private static boolean [] keys;
    private static boolean [] pkeys;//previous state of keys
    
    private static final int NUM_KEYS =     13;
    public static final int MOVE_UP =       0;
    public static final int MOVE_DOWN =     1;
    public static final int MOVE_RIGHT =    2;
    public static final int MOVE_LEFT =     3;
    public static final int ATT_ONE =       4;
    public static final int ATT_TWO =       5;
    public static final int ATT_THREE =     6;
    public static final int ATT_FOUR =      7;
    public static final int DASH =          8;
    public static final int SKILL_SELECT =  9;
    public static final int DEV_CMD =       10;
    public static final int MAIN_ESC =      11;
    public static final int MUTE =          12;
    
    static{
        keys = new boolean[NUM_KEYS];
        pkeys = new boolean[NUM_KEYS];
    }
    
    public static void update(){
        for(int i = 0; i < NUM_KEYS; i++){
            pkeys[i] = keys[i];
        }
    }
    
    public static void setKey(int k, boolean b){
        keys[k] = b;
    }
    
    public static boolean isDown(int k){
        return keys[k];
    }
    
    public static boolean isPressed(int k){
        return keys[k] && !pkeys[k];
    }
    
    
    
    public static boolean anyKeyDown(){
        boolean pressed = false;
        for(boolean k : keys){
            if(k) pressed = true;
        }
        
       
        return pressed;
    }
    
    public static void clear(){
        for(int i = 0; i < NUM_KEYS; i++){
            keys[i] = false;
            pkeys[i] = false;
        }
    }
}
