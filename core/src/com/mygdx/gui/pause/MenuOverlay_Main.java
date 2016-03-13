/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.gui.pause;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author looch
 */
public class MenuOverlay_Main extends MenuOverlay{
    
    //components
    private PauseOptions options;
    
    public MenuOverlay_Main(float x, float y, float width, float height){
        super(x,y,width,height);
        
        options = new PauseOptions(this, x + width * 0.25f, y + height *0.7f, width * 0.5f, height * 0.08f);
        componentMap.put(0, options);
        
        highlightY = options.getY();
        cursor.setX(options.getX() - cursor.getWidth()*1.2f);
        cursor.setY(highlightY);
        
    }
    
    @Override
    public void render(SpriteBatch sb){
        super.render(sb);
        
        options.render(sb);
        cursor.render(sb);
    }
}
