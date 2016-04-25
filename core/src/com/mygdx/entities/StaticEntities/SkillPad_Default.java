/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.StaticEntities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.combat.skills.Skill_LightBasic;
import com.mygdx.managers.GameStats;

/**
 *
 * @author saynt
 */
public class SkillPad_Default extends SkillPad{
    
    public SkillPad_Default(Vector2 pos) {
        super(pos);
    }
    
    @Override
    public void setRandomSkill(){
        if(GameStats.skillPool_default.size != 0){
            SKILL = GameStats.skillPool_default.get(rng.nextInt(GameStats.skillPool_default.size));
            
        }else{
            SKILL = new Skill_LightBasic();
        }
        
    }
}
