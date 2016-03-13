/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author looch
 */
public abstract class OverlayComponent {
    
    protected float x, y, width, height;
    protected Texture texture;
    
    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public Texture getTexture() { return texture; } 
    
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setWidth(float width) { this.width = width; }
    public void setHeight(float height) { this.height = height; }
    public void setTexture(Texture texture) { this.texture = texture; }
    
    
    public OverlayComponent(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public abstract void update();
    
    public void render(SpriteBatch sb){
        if(texture != null)
            sb.draw(texture, x,y,width,height);
    }
    
}
