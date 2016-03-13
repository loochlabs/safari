/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.text;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import java.util.Random;

/**
 *
 * @author looch
 */
public class TextEntity {
    
    public String string;
    public Vector2 pos;
    public boolean flagForDelete = false;
    protected float red, blue, green, alpha;
    protected float scale = 0.85f; //find cleaner way to get scale from currentEnv font scale
    
    protected Random rng = new Random();
    
    public TextEntity(String string, Vector2 pos){
        this.string = string;
        this.pos = pos;
        
        red = 1.0f;
        blue = 1.0f;
        green = 1.0f;
        alpha = 1.0f;
    }
    
    public void addToPos(Vector2 dv){
        this.pos.add(dv);
    }
    
    public void render(BitmapFont font, SpriteBatch sb){
        font.draw(sb, string, pos.x - font.getBounds(string).width/2, pos.y);
    }
    
    
    
}
