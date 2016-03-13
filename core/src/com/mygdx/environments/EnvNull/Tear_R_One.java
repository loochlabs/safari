/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvNull;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.environments.EnvNull.EnvNull_R_One;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.environments.tears.TearPortal;

/**
 *
 * @author looch
 */
public class Tear_R_One extends TearPortal{
    
    public Tear_R_One(Vector2 pos, int linkid){
        super(pos,linkid);
        
        warpenv = new EnvNull_R_One(id,linkid);
        EnvironmentManager.add(warpenv);
        
        userdata = userdata.toString() + "_null";
        
    }
}
