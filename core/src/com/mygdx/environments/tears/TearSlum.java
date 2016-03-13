/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.tears;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.environments.EnvSlum.EnvSlum;
import com.mygdx.environments.EnvironmentManager;

/**
 *
 * @author looch
 */
public class TearSlum extends TearPortal{
    
    public TearSlum(Vector2 pos, int linkid, int sectionCount){
        super(pos,linkid);
        
        teardata = "bosst_" + id;
        userdata = teardata;
        
        warpenv = new EnvSlum(id,linkid, sectionCount);
        EnvironmentManager.add(warpenv);
        
    }
    
}
