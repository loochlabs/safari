/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.demo.demo1;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.environments.EnvVoid.pads.EndWarp;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.managers.GameStats;

/**
 *
 * @author looch
 */
public class EndWarp_Demo2 extends EndWarp {
    
    public EndWarp_Demo2(Vector2 pos) {
        super(pos);
    }

    @Override
    public void createEnvSub() {
        
        endEnvSub = new EnvSub_Demo2(
                ++GameStats.idcount, 
                EnvironmentManager.currentEnv.getId(),
                this);
        EnvironmentManager.add(endEnvSub);
        
    }
    
}
