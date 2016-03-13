/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.gui.descriptions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.gui.OverlayComponent;
import com.mygdx.managers.GameInputProcessor;
import com.mygdx.managers.ResourceManager;

/**
 *
 * @author looch
 */
public class EquipDescription extends OverlayComponent{

    private boolean padEnabled = false;
    private BitmapFont uifont;
    private Texture PAD_A, PAD_B, PAD_X, PAD_Y;
    private String message;
    private float tw, padding;
    
    public EquipDescription(float x, float y, float width, float height) {
        super(x, y, width, height);
        
        texture = MainGame.am.get(ResourceManager.DESC_BG);
        
        
        uifont = new BitmapFont(Gdx.files.internal("fonts/nav-impact.fnt"));
        uifont.setScale(0.4f * RATIO);
        uifont.setColor((78f/255f),(254f/255f),(78f/255f),1.0f);//green
        
        message = " Equip: ";
        tw = 22f * RATIO;
        
        //key bindings
        if(Gdx.input.getInputProcessor() == MainGame.cip)   padEnabled = true;
        
        if(!padEnabled){
            message += 
                    Input.Keys.toString(GameInputProcessor.KEY_ACTION_1) + " / " + 
                    Input.Keys.toString(GameInputProcessor.KEY_ACTION_2) + " / " + 
                    Input.Keys.toString(GameInputProcessor.KEY_ACTION_3) + " / " +
                    Input.Keys.toString(GameInputProcessor.KEY_ACTION_4) + " ";
            
            this.width = uifont.getBounds(message).width;
        }else{
            PAD_A = MainGame.am.get(ResourceManager.GUI_PAD_A);
            PAD_B = MainGame.am.get(ResourceManager.GUI_PAD_B);
            PAD_X = MainGame.am.get(ResourceManager.GUI_PAD_X);
            PAD_Y = MainGame.am.get(ResourceManager.GUI_PAD_Y);
            
            padding = height * 0.15f;
            this.width = uifont.getBounds(message).width + (tw+padding) * 4;
            
        }
        
    }

    
    
    @Override
    public void update() {
    }
    
    @Override
    public void render(SpriteBatch sb){
        sb.draw(texture, x, y, width, height);
        uifont.draw(sb, message, x, y + uifont.getCapHeight()*1.4f);
        
        if(padEnabled){
            sb.draw(PAD_A, x + uifont.getBounds(message).width , y + padding, tw, tw);
            sb.draw(PAD_B, x + uifont.getBounds(message).width + tw + padding, y + padding, tw, tw);
            sb.draw(PAD_X, x + uifont.getBounds(message).width + (tw+padding)*2 , y + padding, tw, tw);
            sb.draw(PAD_Y, x + uifont.getBounds(message).width + (tw+padding)*3 , y + padding, tw, tw);
        }
    }
    
}
