/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import com.mygdx.utilities.FrameCounter_Combo;

/**
 *
 * @author saynt
 */
public class ComboBar extends OverlayComponent{

    private Texture baseTexture, comboTexture, cursorTexture; 
    private FrameCounter_Combo comboFC;
    private float combo_x, combo_w, cursor_x;
    
    public boolean complete = false;
    
    public ComboBar(float x, float y, FrameCounter_Combo fc) {
        super(x - 150f*RATIO, y, 300f*RATIO, 5f*RATIO);
        
        comboFC = fc;
        
        baseTexture = MainGame.am.get(ResourceManager.HUD1_HP_BG);
        comboTexture = MainGame.am.get(ResourceManager.HUD1_EXP_FG);
        cursorTexture = MainGame.am.get(ResourceManager.HUD1_HP_BG);
        
        combo_x = this.x + width * comboFC.attTime/comboFC.MAX_FRAME;
        combo_w = (this.width/2) * comboFC.comboTime/comboFC.MAX_FRAME;
        cursor_x = this.x + this.width * comboFC.CURRENT_FRAME/comboFC.MAX_FRAME;
    }

    @Override
    public void update() {
        
        cursor_x = x + width * comboFC.CURRENT_FRAME/comboFC.MAX_FRAME;
        
        complete = comboFC.complete;
        
    }
    
    @Override 
    public void render(SpriteBatch sb){
        sb.draw(baseTexture, x, y, width, height);
        sb.draw(comboTexture, combo_x ,y+5f, combo_w, height*2);
        sb.draw(cursorTexture, cursor_x, y, width/100, height*2.5f);
    }
    
}
