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
 * @author saynt
 */
public class TextHealing extends TextEntity{

    private long duration;//number of frames to render for
    private Vector2 dv;
    private float curAlpha = 1.0f, comboScale_MAX = 1.4f, comboScale_CUR = 0.85f;
    
    private float cred, cblue, cgreen;
    
    public TextHealing(String string, Vector2 pos) {
        super(string, pos);
        
        this.duration = 60;//1 sec
        
        float dx = rng.nextFloat()* 5.0f;
        
        red = 0.0f;
        blue = 0.0f;
        green = 1.0f;
        alpha = 1.0f;
        
        pos.x += dx;
        this.dv = new Vector2(0, 0.5f);
    }
    
    public TextHealing(String string, Vector2 pos, String color) {
        this(string, pos);
        
        
        if(color.equals("red")){
            red = 1.0f;
            green = 0.0f;
            blue = 0.0f;
        }
        
    }
    
    @Override
    public void render(BitmapFont font, SpriteBatch sb){
        
        
        font.setColor(red, green, blue, curAlpha);
        font.setScale(scale);
        
        super.render(font, sb);
        
        pos.sub(dv);
        duration--;
        
        if(duration < 15){
            curAlpha -= 0.07f;
        }
        
        if(duration <= 0){
            flagForDelete = true;
        }
    }
    
}

