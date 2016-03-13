/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.gui.pause;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.gui.OverlayComponent;
import com.mygdx.managers.ResourceManager;
import com.mygdx.utilities.Direction;
import java.util.HashMap;

/**
 *
 * @author looch
 */
public abstract class MenuOverlay {
    
    public float x, y, width, height;
    protected Texture bg;
    protected float highlightY;
    
    protected int selectY = 0, select_MAX;
    protected HashMap<Integer,PauseComponent> componentMap = new HashMap<Integer,PauseComponent>();
    //components
    protected PauseCursor cursor;
    
    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    
    public MenuOverlay(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bg = MainGame.am.get(ResourceManager.PAUSE_BG);
        
        cursor = new PauseCursor(x,y,30f*RATIO, 30f*RATIO);
        select_MAX = 0;
    }
    
    public void render(SpriteBatch sb){
        if(bg != null)
            sb.draw(bg, x, y, width, height);
    }
    public void moveSelect(Direction d){
        switch (d){
            case UP:
                selectY--;
                selectY = selectY < 0 ? select_MAX : selectY;
                
                break;
            case DOWN:
                selectY++;
                selectY = selectY > select_MAX ? 0 : selectY;
                
                break;
            case LEFT:
                break;
            case RIGHT:
                break;
            default:
                break;
        }
    }
    
    
    public void confirmSelect(){
        if(!componentMap.isEmpty()){
            componentMap.get(selectY).execute();
            
        }
    }
    
}
