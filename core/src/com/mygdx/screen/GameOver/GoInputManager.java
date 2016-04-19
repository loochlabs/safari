/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.screen.GameOver;

import com.mygdx.managers.GameInputProcessor;
import com.mygdx.managers.GameKeyLibrary;
import com.mygdx.screen.MainMenu.MainMenuScreen;
import com.mygdx.screen.ScreenManager;

/**
 *
 * @author looch
 */
public class GoInputManager {
    
    
    public void update(){
    
        if (!GameInputProcessor.controller) {
            if (GameKeyLibrary.isPressed(GameKeyLibrary.ATT_FOUR)) {
                ScreenManager.setScreen(new MainMenuScreen());
            }
        }else{
            if (GameKeyLibrary.isPressed(GameKeyLibrary.ATT_ZERO)
                    || GameKeyLibrary.isPressed(GameKeyLibrary.MAIN_ESC)) {
                ScreenManager.setScreen(new MainMenuScreen());
            }
        }
        
    }
    
}
