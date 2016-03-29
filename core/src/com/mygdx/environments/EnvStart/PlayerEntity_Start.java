/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvStart;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.DynamicEntities.player.PlayerEntity;
import com.mygdx.entities.ImageSprite;
import static com.mygdx.game.MainGame.RATIO;

/**
 *
 * @author saynt
 */
public class PlayerEntity_Start extends PlayerEntity{
    
    private ImageSprite moveSprite;
    
    public PlayerEntity_Start() {
        super(new Vector2(0,0), 20f*RATIO, 20f*RATIO);
        
        moveSprite = new ImageSprite("start-player", true);
        moveSprite.sprite.setBounds(pos.x, pos.y, width*2, height*2);
        isprite = moveSprite;
        
        
        CURRENT_SPEED = 15f;
    }
    
    @Override
    public void render(SpriteBatch sb){
        if(isprite != null){
            isprite.render(sb);
        }
        
        renderActionKey(sb);
    }
    
    @Override
    public void moveUpdate(){
        super.moveUpdate();
        
        isprite = moveSprite;
    }
    
    @Override
    public void attack(int index){}
    
    @Override
    public void death(){}
    
}
