/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.demo.demo1;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.environments.EnvSub.pads.EndPad;
import com.mygdx.screen.GameOver.GameOverScreen;
import com.mygdx.screen.GameScreen;
import com.mygdx.screen.ScreenManager;

/**
 *
 * @author looch
 */
public class EndPad_Demo2 extends EndPad{
    
    public EndPad_Demo2(Vector2 pos, int scount) {
        super(pos, scount, -30);
    }
    
    @Override
    public void actionEvent(){
        //end of demo, game over screen
        GameScreen.player.clearActionEvents();
        
        ScreenManager.setScreen(new GameOverScreen());
    }
    
}
