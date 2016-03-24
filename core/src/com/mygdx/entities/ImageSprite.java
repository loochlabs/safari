/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.mygdx.game.MainGame;

/**
 *
 * @author looch
 */
public class ImageSprite {
    
    public Sprite sprite;
    //public float x, y;
    
    protected TextureAtlas atlas;
    protected AtlasRegion region; 
    protected String key;
    protected boolean loop, complete, flagForComplete = true;
    //flagForComplete - flags the sprite to be removed on completion
    protected int currentIndex;
    protected int totalFrames;
    protected boolean xflip = false, yflip = false;
    protected boolean reverse = false;
    public boolean pause = false;
    
    public String getKey() { return key;}
    public boolean getLoop() { return loop; }
    public int getTotalFrames() { return totalFrames; }
    public int getCurrentFrame() { return currentIndex; }
    public boolean isComplete() { return complete; }
    public boolean getFlagForComplete() { return flagForComplete; }
    public boolean getXFlip() { return xflip; }
    public boolean getYFlip() { return yflip; }
    
    public Texture getFrame(int i) {
        if(i >= 0 && i < totalFrames){
            AtlasRegion reg = atlas.findRegion(key, i);
            
            return reg.getTexture();
        }
        return null;
    }
    
    public void setPause(boolean pause) { this.pause = pause; } 
    public void setComplete(boolean complete) { this.complete = complete; }
    public void setXFlip(boolean flip) { this.xflip = flip; }//todo: old
   
    //default constructor
    public ImageSprite(String key, boolean loop){
        this.loop = loop;
        complete = false;
        currentIndex = 0;
        totalFrames = MainGame.rm.getAtlasLength(key);
        atlas = MainGame.rm.getAtlas(key);
        this.key = key;
        region = atlas.findRegion(this.key, 1);
        
        sprite = new Sprite(region);
    }
    
    public ImageSprite(String key, boolean loop, boolean flagForComplete, boolean xflip, boolean yflip){
        this(key, loop);
        
        this.flagForComplete = flagForComplete;
        
        this.xflip = xflip;
        float w = xflip ? -(this.sprite.getWidth()) : this.sprite.getWidth();
        float h = yflip ? -(this.sprite.getHeight()) : this.sprite.getHeight();
        this.sprite.setSize(w, h);
    }
    
    
    public ImageSprite(String key, boolean loop, boolean flagForComplete, boolean xflip, boolean yflip, float x, float y , float scale){
        this(key,loop,flagForComplete,xflip,yflip);
        
        //this.x = x;
        //this.y = y;
        sprite.setScale(scale);
        //sprite.setPosition(this.x , this.y); 
        sprite.setPosition(x,y);
        
    }
    
    public ImageSprite(String key, boolean loop, boolean flagForComplete, 
            boolean xflip, boolean yflip, float x, float y, float scale,  boolean reverse ){
        this(key,loop,flagForComplete,xflip,yflip,x,y, scale);
        
        this.reverse = reverse;
        if (reverse) {
            currentIndex = MainGame.rm.getAtlasLength(key);
            totalFrames = MainGame.rm.getAtlasLength(key);
            region = atlas.findRegion(this.key, currentIndex);

            sprite = new Sprite(region);
        }
    }
    
    public ImageSprite(String key, boolean loop, boolean flagForComplete, 
            boolean xflip, boolean yflip, float x, float y, float scale, boolean reverse, boolean pause){
        this(key,loop,flagForComplete,xflip,yflip,x,y,scale, reverse);
        
        this.pause = pause;
    }
    
    //copy existing esprite
    public ImageSprite(ImageSprite esprite){
        this(esprite.getKey(), esprite.getLoop(), esprite.getFlagForComplete(), esprite.getXFlip(), esprite.getYFlip());
    }
    
    //copy existing esprite at x,y
    public ImageSprite(ImageSprite esprite, float x, float y, float scale){
        this(esprite.getKey(), esprite.getLoop(), esprite.getFlagForComplete(), esprite.getXFlip(), esprite.getYFlip(), x, y, scale);
    }
    
    //copy existing esprite at x,y and remove on completion
    public ImageSprite(ImageSprite esprite, float x, float y, float scale, boolean flagForComplete){
        this(esprite.getKey(), esprite.getLoop(), flagForComplete, esprite.getXFlip(), esprite.getYFlip(), x, y, scale);
    }
    
    
    public void render(SpriteBatch sb){
        //if(!pause) {
            //this.step();
        //}
        sprite.draw(sb);
    }
    
    public void drawOffset(SpriteBatch sb, float x, float y){
        //float tempx = this.x + x;
        //float tempy = this.y + y;
        float tempx = sprite.getX() + x;
        float tempy = sprite.getY() + y;
        float tx2 = sprite.getX();
        float ty2 = sprite.getY();
        this.sprite.setPosition(tempx, tempy);
        this.sprite.draw(sb);
        this.sprite.setPosition(tx2, ty2);
        
    }
    
    public void step(){
        if(reverse){
            reverseStep();
            return;
        }
        
        if(complete || (currentIndex >= totalFrames && !flagForComplete)) return;
        
        currentIndex++;
        
        if(currentIndex > totalFrames){
            if(loop)
                currentIndex = 1;
            else if(flagForComplete) {
                complete = true;
                return;
            }
        }
        
        sprite.setRegion(atlas.findRegion(key, currentIndex));
        
    }
    
    public void reverseStep(){
        if(complete || (currentIndex <= 0 && !flagForComplete)) return;
        
        currentIndex--;
        
        if(currentIndex <= 0){
            if(loop)
                currentIndex = totalFrames;
            else if(flagForComplete) {
                complete = true;
                return;
            }
        }
        
        sprite.setRegion(atlas.findRegion(key, currentIndex));
    }
    
    public void reset() { 
        currentIndex = reverse ? totalFrames : 0; 
        complete = false;
    }
    
    
    
}
