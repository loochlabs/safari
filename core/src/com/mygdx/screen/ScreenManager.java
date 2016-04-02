/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.screen;

import com.mygdx.demo.DemoGameOverScreen;
import com.mygdx.screen.GameOver.GameOverScreen;

/**
 *
 * @author looch
 */
public class ScreenManager {
    
    private static Screen currentScreen;
    
    public static void setScreen(Screen screen){
        if(currentScreen != null)
            currentScreen.dispose();
        currentScreen = screen;
        currentScreen.create();
    }
    
    public static Screen getCurrentScreen(){
        return currentScreen;
    }
    
    public static void gameOverScreen(int n){
        switch(n){
            case 0:
                setScreen(new GameOverScreen());
                break;
            case 1:
                setScreen(new DemoGameOverScreen());
                break;
            default:  
                gameOverScreen(0);
                break;
        }
    }
    
}
