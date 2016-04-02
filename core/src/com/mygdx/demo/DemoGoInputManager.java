/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.demo;

import com.mygdx.managers.GameInputProcessor;
import com.mygdx.managers.GameKeyLibrary;
import com.mygdx.screen.ScreenManager;

/**
 *
 * @author saynt
 */
public class DemoGoInputManager {
    
    
    public void update(){
    
        if (!GameInputProcessor.controller) {
            if (GameKeyLibrary.anyKeyDown()) {
                //clear key library
                GameKeyLibrary.clear();
                ScreenManager.setScreen(new DemoScreen());
                //ScreenManager.setScreen(new DemoLoadScreen());
            }
        }else{
            if (GameKeyLibrary.anyKeyDown()) {
                GameKeyLibrary.clear();
                ScreenManager.setScreen(new DemoScreen());
                //ScreenManager.setScreen(new DemoLoadScreen());
            }
        }
        
    }
    
}
