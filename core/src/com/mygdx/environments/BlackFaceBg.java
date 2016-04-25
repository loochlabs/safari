/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;

/**
 *
 * @author saynt
 */
public class BlackFaceBg {
    
    private float x, y, width, height;
    
    private Texture topTexture;
    private Texture bottomTexture;
    
    //scrolling
    private float top_y, bottom_y;
    private float scrollRate = 1f*RATIO;
    
    public BlackFaceBg(){
        x = -MainGame.WIDTH/2;
        y = -MainGame.HEIGHT/2;
        width = MainGame.WIDTH;
        height = MainGame.HEIGHT;
        
        top_y = y;
        bottom_y = top_y - height;
        
        topTexture = MainGame.am.get(ResourceManager.START_BLACK_BG);
        bottomTexture = MainGame.am.get(ResourceManager.START_BLACK_BG);
    }
    
    //update scrolling
    public void update(){
     
        top_y += scrollRate;
        bottom_y += scrollRate;
        
        //Note: potential bug with subtracting height, instead of seeing
        //may cause space between textures over enough iterations
        if(top_y > height/2){
            top_y -= height*2;
        }
        
        if(bottom_y > height/2){
            bottom_y -= height*2;
        }
    }
    
    public void render(SpriteBatch sb){
        sb.draw(topTexture, x, top_y, width, height);
        sb.draw(bottomTexture, x, bottom_y, width, height);
    }
    
}
