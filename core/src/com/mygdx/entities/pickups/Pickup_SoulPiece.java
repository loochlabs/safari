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
public class Pickup_SoulPiece extends Pickup{

     private final float BASE_W = 25f, BASE_H = 25f;
    //private final float SCL_AMNT =  rng.nextFloat();
    //private final float LIFE_BASE = 2, LIFE_RANGE = 8, LIFE_AMNT;
    private final float SOUL_AMNT = 10f;
    
    public Pickup_SoulPiece(Vector2 pos) {
        super(pos, 25f*RATIO, 25f*RATIO);
        
        bd.linearDamping = 5.0f;
        
        name = "Soul Piece";
        
        String pname = GameScreen.player.getPlayerName();
        if(pname.equals("Poe")){
            //poe soul texture;
            texture = MainGame.am.get(ResourceManager.ITEM_SOUL_POE);
            name = "Innocence";
        }else{
            texture = MainGame.am.get(ResourceManager.ICON_ENERGY);
        }
        
        width = BASE_W;
        height = BASE_H;
        iw = width*2;
        ih = height*2;
        
        
        
        
    }
    
    public Pickup_SoulPiece(){
        this(new Vector2(0,0));
    }
    
    
    @Override
    public void death(){
        super.death();
        GameScreen.player.addSoulPiece(SOUL_AMNT);
        System.out.println("@Pickup_SoulPiece amnt " + SOUL_AMNT);
        
    }
    
    @Override
    public Pickup_SoulPiece cpy(){
        return new Pickup_SoulPiece();
    }
    
}