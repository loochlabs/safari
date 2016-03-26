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
public class Player_Lumen extends PlayerEntity{

    public Player_Lumen(Vector2 pos) {
        super(pos, 31f*RATIO,31f*RATIO);
        
        //used for soul piece
        playerName = "Lumen";
        
        
        
        LIFE_STAT_COUNT = 3;
        ENERGY_STAT_COUNT = 7;
        DAMAGE_STAT_COUNT = 6;
        SPEED_STAT_COUNT = 3;
        SPECIAL_STAT_COUNT = 4;
        
        refreshStats();
        life = CURRENT_LIFE;
        
        
        //sprites
        spriteScale = 19f*RATIO / 60;
        //movement
        idleSprite = new ImageSprite("lumen-idle", true);
        idleSprite.sprite.setScale(spriteScale);
        idleSprite.setComplete(true);
        frontSprite = new ImageSprite("lumen-front", true);
        frontSprite.sprite.setScale(spriteScale);
        backSprite = new ImageSprite("lumen-back", true);
        backSprite.sprite.setScale(spriteScale);
        rightSprite = new ImageSprite("lumen-side", true);
        rightSprite.sprite.setScale(spriteScale*0.95f);
        leftSprite = new ImageSprite("lumen-side",true,true,true,false);
        leftSprite.sprite.setScale(spriteScale*0.95f);
        recovSprite = new ImageSprite("lumen-recov", false);
        recovSprite.sprite.setScale(spriteScale);
        playerBuffSprite = new MirrorSprite("lumen-buff",false);
        playerBuffSprite.sprite.setScale(0.5f * RATIO);
        diveSprite = new ImageSprite("lumen-dive", true);
        diveSprite.sprite.setScale(0.7f*RATIO);
        warpSprite = new ImageSprite("lumen-warp", false);
        warpSprite.sprite.setScale(0.7f*RATIO);
        attackSprite = new MirrorSprite("lumen-light", false);
        attackSprite.sprite.setScale(spriteScale*1.30f, spriteScale*1.2f);
        attackHeavySprite = new MirrorSprite("lumen-heavy", false);
        attackHeavySprite.sprite.setScale(spriteScale*1.30f);
        
    }
    
    @Override
    public void soulUp(){
        super.soulUp();
        this.addStatPoints(0, 1, 0, 0, 0);
    }
    
}
