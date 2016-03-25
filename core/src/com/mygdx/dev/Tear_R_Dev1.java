/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.dev;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.environments.EnvNull.EnvNull;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.environments.tears.TearPortal;

/**
 *
 * @author saynt
 */
public class Tear_R_Dev1 extends TearPortal{
    
    public Tear_R_Dev1(Vector2 pos, int linkid){
        super(pos,linkid);
        
        warpenv = new EnvNull_R_Dev0(id,linkid);
        EnvironmentManager.add(warpenv);
        
        userdata = userdata.toString() + "_null";
        
    }
    
    private class EnvNull_R_Dev0 extends EnvNull{
     
        public EnvNull_R_Dev0(int id, int linkid){
            super(id,linkid,-1);
        }

        
        @Override
        public void initSections() {
            super.initSections();
            
            for(EnvNull.LayerManager lm : layerManagers){
                for(int i = 1; i < lm.layerSections.size; i++){
                //for(NullSection ns : lm.layerSections){
                    spawnEnemyGroup(lm.layerSections.get(i));
                }
            }

        }
        
    }
}
