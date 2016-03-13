/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.text;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author looch
 */
public class TextDamage extends TextEntity{

    private long duration;//number of frames to render for
    private Vector2 dv;
    private float curAlpha = 1.0f, comboScale_MAX = 1.4f, comboScale_CUR = 0.85f;
    
    private boolean isCombo = false;
    private float cred, cblue, cgreen;
    
    public TextDamage(String string, Vector2 pos, boolean isCombo) {
        super(string, pos);
        
        this.duration = 60;//1 sec
        this.isCombo = isCombo;
        
        float dx;
        
        if(isCombo){
            cred = 1.0f;
            cblue = 0.0f;
            cgreen = 1.0f;
            dx = rng.nextFloat()* -7.0f;
        }else{
            dx = rng.nextFloat()* 5.0f;
        }
        
        pos.x += dx;
        this.dv = new Vector2(0, 0.5f);
    }
    
    public TextDamage(String string, Vector2 pos, boolean isCombo, String color) {
        this(string, pos, isCombo);
        
        
        if(color.equals("red")){
            red = 1.0f;
            green = 0.08f;
            blue = 0.08f;
        }
        
    }
    
    @Override
    public void render(BitmapFont font, SpriteBatch sb){
        
        if(!isCombo){
            font.setColor(red, green, blue, curAlpha);
            font.setScale(scale);
        }else{
            font.setColor(cred, cgreen, cblue, curAlpha);
            if(comboScale_CUR < comboScale_MAX){
                comboScale_CUR *= 1.1f;
            }
            font.setScale(comboScale_CUR);
        }
        super.render(font, sb);
        
        pos.add(dv);
        duration--;
        
        if(duration < 15){
            curAlpha -= 0.07f;
        }
        
        if(duration <= 0){
            flagForDelete = true;
        }
    }
    
}
