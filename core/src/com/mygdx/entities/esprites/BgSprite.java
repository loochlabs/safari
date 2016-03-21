/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.esprites;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.ImageSprite;
import static com.mygdx.game.MainGame.RATIO;
import java.util.Random;

/**
 *
 * @author looch
 */
public class BgSprite extends EntitySprite{

    private float SPEED = 1.0f;
    private final Random rng = new Random();
    
    public float getSpeed() { return SPEED; }   
    
    public BgSprite(float x, float y, float scale) {
        super(new Vector2(x,y),325f*RATIO,325f*RATIO,"bg-piece8", true, false, false, false, scale, false, false);
        
        SPEED *= rng.nextFloat() + 1; 
        
    }
    
    @Override
    public void update(){
        super.update();
        isprite.sprite.setX(isprite.sprite.getX() + SPEED);
        pos.x = isprite.sprite.getX();
    }
    
}
