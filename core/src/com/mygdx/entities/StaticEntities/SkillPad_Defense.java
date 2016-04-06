/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.StaticEntities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.combat.skills.defense.DefenseSkill;

/**
 *
 * @author saynt
 */
public class SkillPad_Defense extends SkillPad{
    
    public SkillPad_Defense(Vector2 pos) {
        super(pos);
        
        SKILL = new DefenseSkill();
    }
    
    @Override
    public void reset(){
        SKILL = new DefenseSkill();
        confirmSkill();
    }
    
}
