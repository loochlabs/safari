/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills;

import com.mygdx.combat.Buff;
import com.mygdx.combat.PassiveBuff;
import com.mygdx.game.MainGame;
import com.mygdx.gui.descriptions.DescriptionWindow;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;

/**
 *
 * @author saynt
 */
public class Skill_Prognosis extends PassiveSkill{
    
    private final Buff buff;
    
    public Skill_Prognosis(){
        super();
        name = "Prognosis";
        type = SkillType.PASSIVE;
        skillIcon = MainGame.am.get(ResourceManager.SKILL_PROGNOSIS);
        buff = new PassiveBuff_Prognosis();
        desc = "Time to recover";
        descWindow = new DescriptionWindow(name, desc, comboChain);
        
    }
    
    @Override
    public void effect() {
        if(activated)   return;
        
        GameScreen.player.addBuff(buff);
        active();
    }
    
    @Override
    public void removeEffect(){
        if(!activated)  return;
        
        GameScreen.player.removeBuff(buff);
        reset();
        GameScreen.player.restoreHp(0);
    }
    
    private class PassiveBuff_Prognosis extends PassiveBuff {

        private final int modifier;

        public PassiveBuff_Prognosis() {
            super();

            modifier = 1;
        }

        @Override
        public void applyBuff() {
            super.applyBuff();

            GameScreen.player.addStatPoints(modifier,0,0,0, 0);

        }

        @Override
        public void removeBuff() {
            super.removeBuff();

            GameScreen.player.addStatPoints(-modifier, 0,0,0,0);
        }
    }

}