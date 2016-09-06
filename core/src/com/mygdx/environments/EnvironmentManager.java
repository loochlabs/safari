/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.DynamicEntities.player.PlayerEntity;
import com.mygdx.entities.DynamicEntities.player.Player_Test;
import com.mygdx.environments.EnvRoom.EnvRoom;
import com.mygdx.managers.GameKeyLibrary;
import java.util.HashMap;

/**
 *
 * @author looch
 */
public class EnvironmentManager {
    
    public final static HashMap<Integer,Environment> FULL_ENV_MAP = new HashMap<Integer,Environment>();
    public static Environment currentEnv;
    
    public static int ID = -100;
    public static int START_ID = -99;
    public static PlayerEntity player;
    
    public static void init(int index){
        switch(index){
            default:
                currentEnv = new EnvRoom(0,1);
        }
        
        FULL_ENV_MAP.put(currentEnv.getId(), currentEnv);
        
        //player
        //NOTE: player declaration needs to proceed Env initialization for b2d purposes
        player = new Player_Test(new Vector2(0,0), 10,10);
        
        currentEnv.begin();
    }
    
    
    public static void add(Environment e){
        if(!FULL_ENV_MAP.containsValue(e))
            FULL_ENV_MAP.put(e.getId(), e);
        
        if(currentEnv == null){
            currentEnv = e;
        }
    }
    
    
    public static Environment get(int id){
        Environment env = FULL_ENV_MAP.get(id);
        if(env == null) return null;
        
        return env;
    }
    
    public static void setCurrent(int id){
        
        if(currentEnv != null && id != Integer.MAX_VALUE){
            
            //currentEnv.pause();
            currentEnv = FULL_ENV_MAP.get(id);
            currentEnv.begin();
            
            System.out.println("@EnvManager: CurrentEnv set, id:" + currentEnv.getId());
            GameKeyLibrary.clear();
        }
    }
    
    public static void clearEnvs(){
        FULL_ENV_MAP.clear();
        currentEnv = null;
    }
    
    public static void respawn(){}
    
    public static void resize(int w, int h){
        //resize all Environents w/ entities
    }
}
