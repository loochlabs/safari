/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.screen.MainMenu;

import com.mygdx.game.MainGame;
import com.mygdx.gui.OverlayComponent;
import com.mygdx.managers.ResourceManager;

/**
 *
 * @author looch
 */
public class MenuHighlight extends OverlayComponent{

    public MenuHighlight(float x, float y, float width, float height){
        super(x,y,width,height);
        
        texture = MainGame.am.get(ResourceManager.MENU_HIGHLIGHT);
        
    }
    
    @Override
    public void update() {
    }
    
}
