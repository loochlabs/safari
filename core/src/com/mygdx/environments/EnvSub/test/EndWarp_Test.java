/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvSub.test;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.environments.EnvSub.EnvSub;
import com.mygdx.environments.EnvSub.EndWarp;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.managers.GameStats;

/**
 *
 * @author looch
 */
public class EndWarp_Test extends EndWarp{

    public EndWarp_Test(Vector2 pos, int linkid) {
        super(pos, linkid);
    }

    @Override
    public void createEnvSub() {
        
        endEnvSub = new EnvSub_Test(
                ++GameStats.idcount, 
                EnvironmentManager.currentEnv.getId(),
                this);
        EnvironmentManager.add(endEnvSub);
        
    }
    
}