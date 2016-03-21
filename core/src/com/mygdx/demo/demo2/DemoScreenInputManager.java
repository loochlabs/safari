/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.demo.demo2;

import com.mygdx.managers.GameInputProcessor;
import com.mygdx.managers.GameKeyLibrary;
import com.mygdx.screen.GameScreen;
import com.mygdx.screen.ScreenManager;

/**
 *
 * @author saynt
 */
public class DemoScreenInputManager {
    
    
    public DemoScreenInputManager(){
    }
    
    public void update() {

        if (GameKeyLibrary.anyKeyDown()) {
            //start game
            System.out.println("@DemoScreenInputManager any key pressed");
            GameKeyLibrary.clear();
            ScreenManager.setScreen(new GameScreen(1));
        }

    }
    
}
