/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.screen.MainMenu;

import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;

/**
 *
 * @author looch
 */
public class Button_Options extends MenuButton{

    public Button_Options(float x, float y, float width, float height){
        super(x,y,width,height);
        
        //set texture
        texture = MainGame.am.get(ResourceManager.MENU_OPTIONS);
    }
    
    @Override
    public void execute() {
        
        //open options screen
        
    }
    
}
