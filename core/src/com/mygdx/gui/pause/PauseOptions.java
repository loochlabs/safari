/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.gui.pause;

import com.mygdx.demo.DemoScreen;
import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.ScreenManager;

/**
 *
 * @author looch
 */
public class PauseOptions extends PauseComponent{

    private MenuOverlay parent;
    
    public PauseOptions(MenuOverlay parent, float x, float y, float width, float height) {
        super(x, y, width, height);
        
        this.parent = parent;
        texture = MainGame.am.get(ResourceManager.PAUSE_OPTIONS);
    }
    
    @Override
    public void execute(){
        System.out.println("@PauseOptions execute");
        
        //ScreenManager.setScreen(new DemoScreen());
        
        /*
        GameScreen.pauseOverlay.screens.push(
                new MenuOverlay_Options(
                        parent.getX(),
                        parent.getY(),
                        parent.getWidth(),
                        parent.getHeight()));
        */
    }

}
