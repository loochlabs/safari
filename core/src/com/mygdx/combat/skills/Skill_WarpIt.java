/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills;

import com.badlogic.gdx.math.Vector2;
import static com.mygdx.combat.skills.Skill.SkillType.SPECIAL;
import com.mygdx.entities.ImageSprite;
import com.mygdx.environments.Environment;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.FrameCounter_Combo;
import com.mygdx.utilities.SoundObject_Sfx;

/**
 *
 * @author looch
 */
public class Skill_WarpIt extends Skill{

    private Vector2 pos;
    private boolean posSet = false;
    private Environment env;
    private ImageSprite permSprite;
    
    public Skill_WarpIt(){
        name = "Warp It";
        type = SPECIAL;
        desc = "Warp here, then warp there...";
        //COST = 60f;
        skillIcon = MainGame.am.get(ResourceManager.SKILL_WARPIT);
        
        comboFC = new FrameCounter_Combo(0, 0.3f, 0, 0);
        //attackTime = 0;
        //comboTime = 0.3f;
        //recovTime = 0;
        
        impactSound = new SoundObject_Sfx(ResourceManager.SFX_POWER_1);
        
    }
    /*
    @Override
    public void effect(boolean isCombo, Skill prevSkill, boolean isComboChain) {
         if(!EnvironmentManager.currentEnv.equals(env)){
             pos = null;
             posSet = false;
             if(permSprite != null){
                 permSprite.setComplete(true);
             }
         }
        
        if(!posSet){
            posSet = true;
            pos = GameScreen.player.getBody().getPosition().cpy();
            env = EnvironmentManager.currentEnv;
            
            /*
            permSprite = new EntitySprite("perm-warpit", true, false, false, false);
            permSprite.sprite.setScale(0.7f *RATIO);
            permSprite.x = pos.x*PPM - permSprite.sprite.getWidth()/2;
            permSprite.y = pos.y*PPM - permSprite.sprite.getHeight()/2;
            permSprite.sprite.setPosition(
                    permSprite.x, permSprite.y);
            env.spawnEntity(permSprite);
            
        }else{
            posSet = false;
            GameScreen.player.getBody().setTransform(pos, 0);
            //env.removeEntity(permSprite);
        
        }
        
        
        reset();
        impactSound.play(false);
    }*/

    @Override
    public void activate() {
    }

    @Override
    public void deactivate() {
        pos = null;
        posSet = false;
        env = null;
    }
    
}
