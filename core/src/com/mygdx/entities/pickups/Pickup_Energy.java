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
 * @author saynt
 */
public class Pickup_Energy extends Pickup{

    
    public Pickup_Energy(Vector2 pos) {
        super(pos, 15f*RATIO, 15f*RATIO);
        
        bd.linearDamping = 5.0f;
        
        texture = MainGame.am.get(ResourceManager.ICON_ENERGY);
        
        iw = width*2;
        ih = height*2;
        
        name = "Energy Pod";
        
    }
    
    public Pickup_Energy(){
        this(new Vector2(0,0));
    }
    
    
    @Override
    public void death(){
        super.death();
        GameScreen.player.restoreEnergy(1);
        System.out.println("@Pickup_Energy amnt " + 1);
        
    }
    
    @Override
    public Pickup_Energy cpy(){
        return new Pickup_Energy();
    }
    
}