/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments;

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
    
    public static void init(Environment e){
        currentEnv = e;
        
        FULL_ENV_MAP.put(currentEnv.getId(), currentEnv);
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
    
    
    public static void createStart(int n){}
    
    public static void respawn(){}
    
    public static void resize(int w, int h){
        //resize all Environents w/ entities
    }
}
