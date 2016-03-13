/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.demo.demo1;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.environments.EnvSub.pads.EndPad;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.screen.ScreenManager;

/**
 *
 * @author looch
 */
public class EndPad_Demo1 extends EndPad{

    public EndPad_Demo1(Vector2 pos, int scount) {
        super(pos, scount, -30);
    }
    
    @Override
    public void actionEvent(){
        //warp to next level
        GameScreen.player.clearActionEvents();
        EnvironmentManager.createLevel(-30); 
        //ScreenManager.gameOverScreen(1);
    }
    
}
