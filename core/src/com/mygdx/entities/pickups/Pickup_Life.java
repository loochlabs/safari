/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.pickups;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;

/**
 *
 * @author looch
 * 
 */
public class Pickup_Life extends Pickup{

    private final float BASE_W = 15f, BASE_H = 12f;
    private final float SCL_AMNT =  rng.nextFloat();
    private final float LIFE_BASE = 1, LIFE_RANGE = 5, LIFE_AMNT;
    
    public Pickup_Life(Vector2 pos) {
        super(pos, 15f*RATIO, 12f*RATIO);
        
        bd.linearDamping = 5.0f;
        
        texture = MainGame.am.get(ResourceManager.ITEM_LIFE);
        
        width = BASE_W + BASE_W * SCL_AMNT;
        height = BASE_H + BASE_H * SCL_AMNT;
        iw = width*2;
        ih = height*2;
        
        LIFE_AMNT = LIFE_BASE + LIFE_RANGE * SCL_AMNT;
        
        name = "Life Fragment";
        
    }
    
    public Pickup_Life(){
        this(new Vector2(0,0));
    }
    
    
    @Override
    public void death(){
        super.death();
        GameScreen.player.restoreHp(LIFE_AMNT);
        System.out.println("@Pickup_Life amnt " + LIFE_AMNT);
        
    }
    
    @Override
    public Pickup_Life cpy(){
        return new Pickup_Life();
    }
    
}
