/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.tears;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.environments.EnvNull.A.EnvNull_A0;
import com.mygdx.environments.EnvNull.A.EnvNull_A1;
import com.mygdx.environments.EnvNull.A.EnvNull_A2;
import com.mygdx.environments.EnvNull.A.EnvNull_A3;
import com.mygdx.environments.EnvNull.A.EnvNull_A4;
import com.mygdx.environments.Environment;
import com.mygdx.environments.EnvironmentManager;

/**
 *
 * @author looch
 */
public class Tear_A extends TearPortal{
    
    private final int ENV_COUNT = 2;
    
    public Tear_A(Vector2 pos, int linkid){
        super(pos,linkid);
        
        warpenv = createWarpEnv();
        
        EnvironmentManager.add(warpenv);
        
        userdata = userdata.toString() + "_null";
        
    }
    
    public Tear_A(Vector2 pos, int linkid, int env){
        super(pos, linkid);
        
        warpenv = createWarpEnv(env);
        EnvironmentManager.add(warpenv);
        
        userdata = userdata.toString() + "_null";
    }
    
    //random EnvNull_A
    private Environment createWarpEnv(){
        return createWarpEnv(rng.nextInt(ENV_COUNT));
    }
    
    //int n EnvNull_A
    private Environment createWarpEnv(int n){
        
        switch(n){
            case 0:
                return new EnvNull_A0(id, linkid);
            case 1:
                return new EnvNull_A1(id, linkid);
            case 2: 
                return new EnvNull_A2(id, linkid);
                
            case 3:
                return new EnvNull_A3(id, linkid);
            case 4:
                return new EnvNull_A4(id, linkid);
            default:
                return new EnvNull_A0(id, linkid);
        }
        
    }
}
