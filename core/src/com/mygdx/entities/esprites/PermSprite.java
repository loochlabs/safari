/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.esprites;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.environments.EnvironmentManager;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.utilities.FrameCounter;
import java.util.Random;

/**
 *
 * @author looch
 */
public class PermSprite extends EntitySprite{

    private final FrameCounter durationFC;
    private final float DURATION = 60f;
    private final Random rng = new Random();
    
    public PermSprite(String key, Vector2 pos) {
        super(key, false, false, false, false);
        
        durationFC = new FrameCounter(DURATION);
        
        
        sprite.setPosition(pos.x - sprite.getWidth()/2, pos.y - sprite.getHeight()/2);
        sprite.setScale(0.8f*RATIO);
        sprite.rotate(360 * rng.nextFloat());
        
        this.x = pos.x - sprite.getWidth()/2;
        this.y = pos.y - sprite.getHeight()/2;
    }
    
    @Override
    public void update(){
        super.update();
        
        if(durationFC.complete)
            end();
    }
    
    public void start(){
        durationFC.start(EnvironmentManager.currentEnv.getFrameManager());
        EnvironmentManager.currentEnv.spawnSprite(this);
        
    }
    
    public void end(){
        EnvironmentManager.currentEnv.removeSprite(this);
    }
    
    
    
}
