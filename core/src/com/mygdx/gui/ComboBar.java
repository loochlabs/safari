/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.utilities.FrameCounter_Combo;

/**
 *
 * @author saynt
 */
public class ComboBar extends OverlayComponent{

    private Texture baseTexture, comboTexture, cursorTexture; 
    private FrameCounter_Combo comboFC;
    private float combo_x, combo_w, cursor_x;
    
    public ComboBar(int x, int y, FrameCounter_Combo fc) {
        super(x, y, 200f*RATIO, 20f*RATIO);
        
        comboFC = fc;
        
        combo_x = x + width * comboFC.attTime/comboFC.MAX_FRAME;
        combo_w = width * comboFC.comboTime/comboFC.MAX_FRAME;
        cursor_x = x + width * comboFC.CURRENT_FRAME/comboFC.MAX_FRAME;
    }

    @Override
    public void update() {
        
        cursor_x = x + width * comboFC.CURRENT_FRAME/comboFC.MAX_FRAME;
        
    }
    
    @Override 
    public void render(SpriteBatch sb){
        sb.draw(baseTexture, x, y, width, height);
        sb.draw(comboTexture, combo_x, y, combo_w, height*2);
        sb.draw(cursorTexture, cursor_x, y, width/100, height*2.5f);
    }
    
}
