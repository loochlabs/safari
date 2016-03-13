/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.player;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.esprites.EntitySprite;
import com.mygdx.managers.ResourceManager;

/**
 *
 * @author looch
 */
public class Player_Petal extends PlayerEntity{

    public Player_Petal(Vector2 pos, float w, float h) {
        super(pos, w, h);
        
        //box2d
        fd.shape = shape;
        
        LIFE_STAT_COUNT = 2;
        ENERGY_STAT_COUNT = 2;
        DAMAGE_STAT_COUNT = 4;
        SPEED_STAT_COUNT = 1;
        SPECIAL_STAT_COUNT = 3;
        
        updateStats();
        life = CURRENT_LIFE;
        
        //sprites
        spriteScale = width / 44;
        
        //frontTexture = ResourceManager.PETAL_FRONT;
        //texture = frontTexture;
        //iw *= 1.1f;
        //ih *= 1.3f;
        
        //movement
        frontSprite = new EntitySprite("petal-walk-front", true);
        frontSprite.sprite.setScale(spriteScale);
        
        idleSprite = new EntitySprite("petal-idle", true);
        idleSprite.sprite.setScale(spriteScale);
        idleSprite.setComplete(true);
        
        //transition
        diveSprite = new EntitySprite("petal-dive", true);
        diveSprite.sprite.setScale(spriteScale);
        
        
        //********************************
        //poe
        backSprite = new EntitySprite("poe-back-anim", true);
        backSprite.sprite.setScale(spriteScale);
        leftSprite = new EntitySprite("poe-left-anim", true);
        leftSprite.sprite.setScale(spriteScale);
        rightSprite = new EntitySprite("poe-right-anim", true);
        rightSprite.sprite.setScale(spriteScale);
        
        
        
        warpSprite = new EntitySprite("poe-warp", false);
        warpSprite.sprite.setScale(0.7f);
        
        //combat
        attackSprite = new EntitySprite("poe-attack", false);
        attackSprite.sprite.setScale(spriteScale*1.30f, spriteScale*1.2f);
        
    }
    
}
