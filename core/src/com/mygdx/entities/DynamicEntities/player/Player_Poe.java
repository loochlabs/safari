/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.player;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.ImageSprite;
import com.mygdx.entities.esprites.MirrorSprite;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;

/**
 *
 * @author looch
 */
public class Player_Poe extends PlayerEntity{

    public Player_Poe(Vector2 pos) {
        super(pos, 22f*RATIO, 22*RATIO);
        
        playerName = "Poe";
        
        //box2d
        fd.shape = shape;
        
        LIFE_STAT_COUNT = 1;
        ENERGY_STAT_COUNT = 2;
        DAMAGE_STAT_COUNT = 3;
        SPEED_STAT_COUNT = 4;
        
        
        SPECIAL_STAT_COUNT = 2;//todo: remove
        
        
        refreshStats();
        life = CURRENT_LIFE;
        
        //sprites
        spriteScale = width / 60;
        //movement
        idleSprite = new ImageSprite("poe-idle", true);
        idleSprite.sprite.setScale(spriteScale);
        idleSprite.setComplete(true);
        frontSprite = new ImageSprite("poe-front", true);
        frontSprite.sprite.setScale(spriteScale);
        backSprite = new ImageSprite("poe-back", true);
        backSprite.sprite.setScale(spriteScale);
        //leftSprite = new EntitySprite("poe-left-anim", true);
        
        rightSprite = new ImageSprite("poe-side", true);
        rightSprite.sprite.setScale(spriteScale);
        leftSprite = new ImageSprite("poe-side",true,true,true,false);
        leftSprite.sprite.setScale(spriteScale);
        
        playerBuffSprite = new MirrorSprite("poe-buff",false);
        playerBuffSprite.sprite.setScale(0.5f * RATIO);
        
        //transition
        diveSprite = new ImageSprite("poe-dive", true);
        diveSprite.sprite.setScale(0.7f*RATIO);
        
        warpSprite = new ImageSprite("poe-warp", false);
        warpSprite.sprite.setScale(0.7f*RATIO);
        
        //combat
        attackSprite = new MirrorSprite("poe-body-light-att", false);
        attackSprite.sprite.setScale(spriteScale*1.30f, spriteScale*1.2f);
        attackHeavySprite = new ImageSprite("poe-body-heavy-att", false);
        attackHeavySprite.sprite.setScale(spriteScale*1.30f);
        recovSprite = new ImageSprite("poe-recov", false);
        recovSprite.sprite.setScale(spriteScale);
        
        /*
        attackLeftSprite = new MirrorSprite("poe-attack-side", false);
        attackLeftSprite.sprite.setScale(spriteScale*1.30f);
        attackRightSprite = new MirrorSprite("poe-attack-side", false, true, true, false);
        attackRightSprite.sprite.setScale(spriteScale*1.30f);
        attackRightSprite.sprite.setOrigin(-attackRightSprite.sprite.getWidth(), attackRightSprite.sprite.getHeight()/2);
        */
        
        //soul 
        soulTexture = MainGame.am.get(ResourceManager.ITEM_SOUL_POE);
    }
    
    
}
