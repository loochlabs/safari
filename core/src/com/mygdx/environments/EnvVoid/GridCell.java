/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvVoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;

/**
 *
 * @author looch
 */
public class GridCell {
    
    private final int id;
    private final float x, y, width, height;
    private final Texture texture;
    
    public int getId() {return id;}
    public float getX() {return x;}
    public float getY() {return y;}
    public float getWidth() {return width;}
    public float getHeight() {return height;}
    public Texture getTexture() {return texture;}
    
    
    public GridCell(int id, float w, float h, float x, float y){
        this.id = id;
        this.width = w+(8*RATIO);
        this.height = h+(8*RATIO);
        this.x = x;
        this.y = y;
        
        texture = MainGame.am.get(ResourceManager.VOID_BG_PH); 
    }
    
    public GridCell(int id, float w, float h, float x, float y, String type){
        this.id = id;
        this.width = w+(8*RATIO);
        this.height = h+(8*RATIO);
        this.x = x;
        this.y = y;
        
        if(type.equals("A")){
            texture = MainGame.am.get(ResourceManager.VOID_BG_A);
        }
        else if(type.equals("B")){
            texture = MainGame.am.get(ResourceManager.VOID_BG_B);
        }else{
            texture = MainGame.am.get(ResourceManager.VOID_BG_PH); 
        }
    }       
    
    public void render(SpriteBatch sb){
        if(texture != null)
            sb.draw(texture,x,y,width*(1.01f*RATIO),height*(1.01f*RATIO));
    }
    
}
