/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.tears;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.screen.GameScreen;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 * 
 * TODO: old class
 */
public class NullWarp extends TearPortal{

    //private Environment warpenv;
    private final int warpid;
    
    public NullWarp(Vector2 pos, int linkid) {
        super(pos,linkid);
        
        warpenv = EnvironmentManager.get(linkid);
        this.warpid = linkid;
        opened = true;
        CURRENT_HP = 5;
    }
    
    @Override
    public void warp(){
        GameScreen.player.warp(body.getPosition());
        EnvironmentManager.currentEnv.end(warpid,endTime);
        opened = true;
        isprite = dmgSprite;
        warpSound.play(false);
    }
    
   
}
