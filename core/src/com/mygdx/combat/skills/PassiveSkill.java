/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills;

/**
 *
 * @author saynt
 */
public abstract class PassiveSkill extends Skill{
    
    protected boolean activated = false;
    
    public PassiveSkill(){
        type = SkillType.PASSIVE;
        
    }
    
    @Override 
    public void activate(){
        this.effect();
        activated = true;
    }
    
    @Override 
    public void deactivate(){
        this.removeEffect();
        activated = false;
    }
    
}
