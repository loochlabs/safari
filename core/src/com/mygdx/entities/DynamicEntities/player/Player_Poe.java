/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.player;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.esprites.EntitySprite;
import com.mygdx.entities.esprites.MirrorSprite;
import com.mygdx.environments.EnvironmentManager;
import static com.mygdx.game.MainGame.RATIO;

/**
 *
 * @author looch
 */
public class Player_Poe extends PlayerEntity{

    public Player_Poe(Vector2 pos, float w, float h) {
        super(pos, w, h);
        
        playerName = "Poe";
        
        //box2d
        fd.shape = shape;
        
        LIFE_STAT_COUNT = 1;
        ENERGY_STAT_COUNT = 2;
        DAMAGE_STAT_COUNT = 3;
        SPEED_STAT_COUNT = 2;
        SPECIAL_STAT_COUNT = 2;
        
        //todo: move to init
        updateStats();
        life = CURRENT_LIFE;
        
        //sprites
        spriteScale = width / 60;
        //movement
        idleSprite = new EntitySprite("poe-idle", true);
        idleSprite.sprite.setScale(spriteScale);
        idleSprite.setComplete(true);
        frontSprite = new EntitySprite("poe-front", true);
        frontSprite.sprite.setScale(spriteScale);
        backSprite = new EntitySprite("poe-back", true);
        backSprite.sprite.setScale(spriteScale);
        //leftSprite = new EntitySprite("poe-left-anim", true);
        
        rightSprite = new EntitySprite("poe-side", true);
        rightSprite.sprite.setScale(spriteScale);
        leftSprite = new EntitySprite("poe-side",true,true,true,false);
        leftSprite.sprite.setScale(spriteScale);
        
        //transition
        diveSprite = new EntitySprite("poe-dive", true);
        diveSprite.sprite.setScale(0.7f*RATIO);
        
        warpSprite = new EntitySprite("poe-warp", false);
        warpSprite.sprite.setScale(0.7f*RATIO);
        
        //combat
        attackSprite = new MirrorSprite("poe-body-light-att", false);
        attackSprite.sprite.setScale(spriteScale*1.30f, spriteScale*1.2f);
        attackHeavySprite = new EntitySprite("poe-body-heavy-att", false);
        attackHeavySprite.sprite.setScale(spriteScale*1.30f);
        recovSprite = new EntitySprite("poe-recov", false);
        recovSprite.sprite.setScale(spriteScale);
        
        
        //skills
        //skillSet[0] = new Skill_OneTwo();
        //skillSet[1] = new Skill_Haymaker();
        
    }
    
    @Override
    public void soulUp(){
        this.addStatPoints(0, 0, 0, 1, 0);
        EnvironmentManager.currentEnv.addDamageText("Speed up!", body.getPosition().cpy());
    }
    
}
