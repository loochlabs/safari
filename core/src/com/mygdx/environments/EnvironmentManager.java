/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.demo.demo1.EnvVoid_Demo1;
import com.mygdx.demo.demo1.EnvVoid_Demo2;
import com.mygdx.demo.demo2.EnvVoid_D2_0;
import com.mygdx.demo.demo2.EnvVoid_D2_1;
import com.mygdx.demo.demo2.EnvVoid_D2_2;
import com.mygdx.dev.EnvVoid_Dev0;
import com.mygdx.entities.DynamicEntities.player.Player_Poe;
import com.mygdx.environments.EnvMan.EnvMan_Intro;
import com.mygdx.environments.EnvSub.pads.EndPadManager;
import com.mygdx.environments.EnvVoid.EnvVoid_Showcase;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.GameKeyLibrary;
import com.mygdx.screen.GameScreen;
import static com.mygdx.screen.GameScreen.player;
import java.util.HashMap;

/**
 *
 * @author looch
 */
public class EnvironmentManager {
    
    //public final static Array<Environment> START_ENVS = new Array<Environment>();
    public final static HashMap<Integer,Environment> FULL_ENV_MAP = new HashMap<Integer,Environment>();
    public static Environment currentEnv;
    
    
    /*
    public EnvironmentManager(){
        
        
        START_ENVS.add(new EnvVoid_Dev(-2, 4000,4000,4));
        START_ENVS.add(new EnvVoid_Showcase(-3, 2000, 2000, 4));
        START_ENVS.add(new EnvVoid_Demo1(-10));
        START_ENVS.add(new EnvVoid_D2_0(-20));
        START_ENVS.add(new EnvVoid_Demo1(-11));
        START_ENVS.add(new EnvRoom_Start(-30, -20));
        START_ENVS.add(new EnvMan_Intro(-100, -20));
        
        
        
        for (Environment e : START_ENVS) {
            if (!FULL_ENV_MAP.containsValue(e)) {
                FULL_ENV_MAP.put(e.getId(), e);
            }
        }
    }*/
    
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
    
    
    
    //Description:  Get environment by id.
    public static Environment get(int id){
        Environment env = FULL_ENV_MAP.get(id);
        if(env == null) return null;
        
        return env;
    }
    
    public static void setCurrent(int id){
        
        if(currentEnv != null && id != Integer.MAX_VALUE){
            
            currentEnv.pause();
            currentEnv = FULL_ENV_MAP.get(id);
            currentEnv.begin();
            
            System.out.println("@EnvManager: CurrentEnv set, id:" + currentEnv.getId());
            GameKeyLibrary.clear();
        }
    }
    
    public static void clearEnvs(){
        FULL_ENV_MAP.clear();
        //START_ENVS.clear();
        currentEnv = null;
    }
    
    
    
    //@param: 
    public static void createStart(int n){
        //Environment e = START_ENVS.get(n);
        
        Environment e;
        
        switch(n){
            case 0:
                //dev env
                e = new EnvVoid_Dev0();
                break;
            case 1:
                e = new EnvVoid_D2_0(-20);
                break;
            default:
                e = new EnvVoid_D2_0(-20);
                break;
            
        }
        
        //player, initial setup
        if(GameScreen.player == null)
            player = new Player_Poe(new Vector2(300 * RATIO , 400 * RATIO), 22 * RATIO , 31 * RATIO);
        
        //end pad manager
        EndPadManager.init();
        
        init(e);
        currentEnv.begin();
        
    }
    
    public static void createLevel(int n){
        
        
        //environments
        Environment e;
        
        switch (n){
            case 0:
                e = new EnvVoid_Showcase(-3, 2000, 2000, 4);
                break;
            case 1:
                e = new EnvVoid_Showcase(-3, 4000, 4000, 4);
                break;
            case 2:
                e = new EnvVoid_Demo1(-11);
                break;
            case 3:
                e = new EnvVoid_Demo2(-12);
                break;
            case -21:
                //Demo2 level 1
                e = new EnvVoid_D2_1(-21);
                break;
            case -22:
                //Demo2 level 2
                e = new EnvVoid_D2_2(-22);
                break;
            case -30:
                //Demo2 level 2
                e = new EnvMan_Intro(-31, -32);
                break;
            default:
                //dev, n = -1
                e = new EnvVoid_D2_0(-20);
        }
        
        clearEnvs();
        init(e);
        currentEnv.begin();
    }
    
    
    public static void resize(int w, int h){
        //resize all Environents w/ entities
    }
}
