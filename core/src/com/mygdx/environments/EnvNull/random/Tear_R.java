/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvNull.random;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.environments.tears.TearPortal;

/**
 *
 * @author saynt
 */
public class Tear_R extends TearPortal{
    
    public Tear_R(float x, float y, int linkid, int difficulty){
        super(new Vector2(x,y),linkid);
        
        warpenv = new EnvNull_R(id,linkid, difficulty);
        EnvironmentManager.add(warpenv);
        
        userdata = userdata.toString() + "_null";
        
    }
    
    
}
