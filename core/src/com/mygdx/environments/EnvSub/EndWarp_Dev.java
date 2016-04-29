/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvSub;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.environments.EnvVoid.pads.EndPad;
import com.mygdx.environments.EnvironmentManager;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author saynt
 */
public class EndWarp_Dev extends EndWarp{

    public EndWarp_Dev(Vector2 pos, int linkid) {
        super(pos, linkid);
    }

    @Override
    public void createEnvSub() {
        
        endEnvSub = new EnvSub_Dev(
                --EnvironmentManager.ID, 
                linkid, 
                this);
        EnvironmentManager.add(endEnvSub);
        
    }
    
    

    private class EnvSub_Dev extends EnvSub {

        public EnvSub_Dev(int id, int linkid, EndWarp endWarp) {
            super(id, linkid, endWarp);
        }

        @Override
        public void createPad() {
            pad = new EndPad(new Vector2(bd.position.x*PPM, bd.position.y*PPM), 4, -30);
        }

    }
}
