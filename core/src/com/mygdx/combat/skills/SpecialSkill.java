/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills;

import static com.mygdx.combat.skills.Skill.SkillType.SPECIAL;
import com.mygdx.managers.ResourceManager;
import com.mygdx.utilities.SoundObject_Sfx;

/**
 *
 * @author saynt
 */
public abstract class SpecialSkill extends Skill{
 
    public SpecialSkill(){
        type = SPECIAL;
        COST = 60.0f;
        
        prepTime = 0;
        attTime = 0.5f;
        recovTime = 0.25f;
        
        impactSound = new SoundObject_Sfx(ResourceManager.SFX_POWER_1);
        
    }
    
    
}