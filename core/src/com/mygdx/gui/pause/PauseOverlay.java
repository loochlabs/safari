/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.gui.pause;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.screen.ScreenManager;
import java.util.Stack;

/**
 *
 * @author looch
 */
public class PauseOverlay {
    
    public float x, y, width, height;
    
    public final Stack<MenuOverlay> screens = new Stack<MenuOverlay>();
    
    public PauseOverlay(){
        x = MainGame.WIDTH * 0.1f;
        y = MainGame.HEIGHT * 0.1f;
        width = 650*RATIO;
        height = 650*RATIO;
        
        //screens.push(new MenuOverlay_Main(x,y,width,height));
    }
    
    public void render(SpriteBatch sb){
        //screens.peek().render(sb);
    }
    
    public void moveSelect(){}
    
    public void confirmSelect(){
        screens.peek().confirmSelect();
        
    }
    
    public void back(){
        screens.pop();
        
        if(screens.empty()) exit();
    }
    
    public void exit(){
        ScreenManager.getCurrentScreen().resume();
    }
    
}
