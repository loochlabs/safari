/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.screen.MainMenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MainGame;
import com.mygdx.gui.OverlayComponent;
import com.mygdx.managers.ResourceManager;

/**
 *
 * @author looch
 */
public abstract class MenuButton extends OverlayComponent{
    
    protected final BitmapFont font = new BitmapFont();
    protected Array<MenuButton> button_options = new Array<MenuButton>();
    
    public Array<MenuButton> getButtons() { return button_options; }

    public MenuButton(float x, float y, float width, float height){
        super(x,y,width,height);
        
        texture = MainGame.am.get(ResourceManager.MENU_BG);
        font.setColor(Color.WHITE);
    }
    
    @Override
    public void update() {
    }
    
    public abstract void execute();
    
}
