/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.player;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.ImageSprite;
import com.mygdx.entities.esprites.MirrorSprite;
import static com.mygdx.game.MainGame.RATIO;

/**
 *
 * @author looch
 */
public class Player_Woogie extends PlayerEntity{

    public Player_Woogie(Vector2 pos) {
        super(pos, 22f*RATIO, 31f*RATIO);
        
        playerName = "Woogie";
        
        //box2d
        fd.shape = shape;
        
        LIFE_STAT_COUNT = 2;
        ENERGY_STAT_COUNT = 3;
        DAMAGE_STAT_COUNT = 4;
        SPEED_STAT_COUNT = 1;
        
        
        SPECIAL_STAT_COUNT = 1;
        
        refreshStats();
        life = CURRENT_LIFE;
        
        
        //sprites
        spriteScale = 31f*RATIO / 60;
        //movement
        idleSprite = new ImageSprite("woogie-idle", true);
        idleSprite.sprite.setScale(spriteScale);
        idleSprite.setComplete(true);
        frontSprite = new ImageSprite("woogie-front", true);
        frontSprite.sprite.setScale(spriteScale);
        backSprite = new ImageSprite("woogie-back", true);
        backSprite.sprite.setScale(spriteScale);
        leftSprite = new ImageSprite("woogie-side", true);
        leftSprite.sprite.setScale(spriteScale);
        rightSprite = new ImageSprite("woogie-side",true,true,true,false);
        rightSprite.sprite.setScale(spriteScale);
        recovSprite = new ImageSprite("woogie-recov", false);
        recovSprite.sprite.setScale(spriteScale);
        playerBuffSprite = new MirrorSprite("woogie-buff",false);
        playerBuffSprite.sprite.setScale(0.5f * RATIO);
        diveSprite = new ImageSprite("woogie-dive", true);
        diveSprite.sprite.setScale(0.7f*RATIO);
        warpSprite = new ImageSprite("woogie-warp", false);
        warpSprite.sprite.setScale(0.7f*RATIO);
        attackSprite = new MirrorSprite("woogie-light", false);
        attackSprite.sprite.setScale(spriteScale*1.30f, spriteScale*1.2f);
        attackHeavySprite = new ImageSprite("woogie-heavy", false);
        attackHeavySprite.sprite.setScale(spriteScale*1.30f);
        
    }
}
