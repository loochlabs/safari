/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.DogEntities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.ImageSprite;
import static com.mygdx.game.MainGame.RATIO;

/**
 *
 * @author looch
 */
public class StellaEntity extends DogEntity{

    public StellaEntity(Vector2 pos){
        super(pos,25f*RATIO, 25f*RATIO);
        
        userdata = "stella";
        
        spriteScale = width / 72;
        
        moveSprite = new ImageSprite("stella-move", true);
        moveSprite.sprite.setScale(spriteScale * RATIO);
        
        alertSprite = new ImageSprite("red-alert", true);
        alertSprite.sprite.setScale(0.65f * RATIO);
        
        
    }
    
    
}
