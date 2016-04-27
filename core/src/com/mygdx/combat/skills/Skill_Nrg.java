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
 * @author looch
 */
public class Skill_Nrg extends PassiveSkill {

    private final Buff buff;
    
    public Skill_Nrg(){
        super();
        name = "N.R.G.";
        type = SkillType.PASSIVE;
        skillIcon = MainGame.am.get(ResourceManager.SKILL_NRG);
        buff = new PassiveBuff_Nrg();
        desc = "More energy";
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
    }
    
    public class PassiveBuff_Nrg extends PassiveBuff {

        private final int modifier;

        public PassiveBuff_Nrg() {
            super();

            modifier = 2;
        }

        @Override
        public void applyBuff() {
            super.applyBuff();

            GameScreen.player.addStatPoints(0, modifier, 0, 0, 0);

        }

        @Override
        public void removeBuff() {
            super.removeBuff();

            GameScreen.player.addStatPoints(0, -modifier, 0, 0, 0);
        }

    }

}
