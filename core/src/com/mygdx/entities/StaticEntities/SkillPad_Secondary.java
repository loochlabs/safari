/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.StaticEntities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.combat.skills.Skill_GhostJab;
import com.mygdx.managers.GameStats;

/**
 *
 * @author saynt
 */
public class SkillPad_Secondary extends SkillPad{
    
    public SkillPad_Secondary(Vector2 pos) {
        super(pos);
    }
    
    @Override
    public void setRandomSkill(){
        if(GameStats.skillPool.size != 0){
            SKILL = GameStats.skillPool_Secondary.random();
            
        }else{
            SKILL = new Skill_GhostJab();
        }
        
    }
    
}
