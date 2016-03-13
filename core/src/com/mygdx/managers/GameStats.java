/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.managers;

import com.badlogic.gdx.utils.Array;
import com.mygdx.combat.skills.Skill;
import com.mygdx.combat.skills.Skill_CantTouchThis;
import com.mygdx.combat.skills.Skill_CrackOfThunder;
import com.mygdx.combat.skills.Skill_DashBolt;
import com.mygdx.combat.skills.Skill_GhostJab;
import com.mygdx.combat.skills.Skill_HauntHaste;
import com.mygdx.combat.skills.Skill_Haymaker;
import com.mygdx.combat.skills.Skill_HeavyHanded;
import com.mygdx.combat.skills.Skill_LightningRod;
import com.mygdx.combat.skills.Skill_MommasFury;
import com.mygdx.combat.skills.Skill_MommasTouch;
import com.mygdx.combat.skills.Skill_Nrg;
import com.mygdx.combat.skills.Skill_OneTwo;
import com.mygdx.combat.skills.Skill_PowerPlease;
import com.mygdx.combat.skills.Skill_Prognosis;
import com.mygdx.combat.skills.Skill_TaintedTorture;
import com.mygdx.combat.skills.Skill_WarpIt;

/**
 *
 * @author looch
 */
public class GameStats {
    
    
    public static int idcount = 0;
    
    public static Array<Skill> skillPool_Primary = new Array<Skill>();
    public static Array<Skill> skillPool_Secondary = new Array<Skill>();    //contains all skills, except lights
    public static Array<Skill> skillPool = new Array<Skill>();
    public static Inventory inventory = new Inventory();
    
    public static void init(){
        
        //light
        //skillPool.add(new Skill_OneTwo());
        //skillPool.add(new Skill_MommasTouch());
        //skillPool.add(new Skill_LightningRod());
        
        skillPool_Primary.add(new Skill_GhostJab());
        skillPool_Primary.add(new Skill_OneTwo());
        skillPool_Primary.add(new Skill_MommasTouch());
        skillPool_Primary.add(new Skill_LightningRod());
        
        //heavy
        skillPool_Secondary.add(new Skill_Haymaker());
        skillPool_Secondary.add(new Skill_HauntHaste());
        skillPool_Secondary.add(new Skill_MommasFury());
        skillPool_Secondary.add(new Skill_CrackOfThunder());
        //special
        skillPool_Secondary.add(new Skill_PowerPlease());
        skillPool_Secondary.add(new Skill_WarpIt());
        skillPool_Secondary.add(new Skill_TaintedTorture());
        //passive
        skillPool_Secondary.add(new Skill_DashBolt());
        skillPool_Secondary.add(new Skill_Nrg());
        skillPool_Secondary.add(new Skill_CantTouchThis());
        skillPool_Secondary.add(new Skill_Prognosis());
        skillPool_Secondary.add(new Skill_HeavyHanded());
        
        for(Skill ps: skillPool_Primary){
            skillPool.add(ps);
        }
        
        for(Skill s: skillPool_Secondary){
            skillPool.add(s);
        }
    }
    
    public static void addSkill(Skill skill){
        if(!skillPool.contains(skill, false))
            skillPool.add(skill);
    }
    
    public static void removeSkill(Skill skill){
        if(skillPool.contains(skill, false))
            skillPool.removeValue(skill, false);
    }
    
    public static void dispose(){
        skillPool.clear();
        inventory.dispose();
        idcount = 0;
    }
}
