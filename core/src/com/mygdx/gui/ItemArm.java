/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.esprites.EntitySprite;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import com.mygdx.utilities.FrameCounter;

/**
 *
 * @author looch
 */
public class ItemArm {
    
    public boolean complete = false;
    
    private EntitySprite esprite;
    private Vector2 pos_start, pos_end;
    private FrameCounter durationFC = new FrameCounter(0.65f);
    
    
    public ItemArm(){
        pos_start = new Vector2(MainGame.WIDTH/2, MainGame.HEIGHT/2);
        esprite = new EntitySprite("matter-arm1", false, false, false, false, pos_start.x, pos_start.y);
        
    }
    
    public void render(SpriteBatch sb){
        esprite.render(sb);
        
        //scale sprite
        
        esprite.sprite.setSize(esprite.sprite.getWidth(), esprite.sprite.getHeight()*1.1f);
        esprite.sprite.setPosition(MainGame.WIDTH/2 - esprite.sprite.getWidth()/2, MainGame.HEIGHT/2 - esprite.sprite.getHeight());
        
        
        if(durationFC.complete) end();
    }
    
    public void start(){
        durationFC.start(EnvironmentManager.currentEnv.getFrameManager());
    }
    
    public void end(){
        complete = true;
    }
    
}
