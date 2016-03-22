/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills;

import com.mygdx.combat.Buff;
import com.mygdx.game.MainGame;
import com.mygdx.gui.descriptions.DescriptionWindow;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;

/**
 *
 * @author looch
 */
public class Skill_MuchHaste extends Skill{
    
    private Buff buff;
    
    public Skill_MuchHaste(){
        name = "Much Haste";
        COST = 60.0f;
        skillIcon = MainGame.am.get(ResourceManager.SKILL_MUCHHASTE);
        type = SkillType.SPECIAL;
        desc = "More speed";
        descWindow = new DescriptionWindow(name, desc, type);
        
        attackTime = 0;
        comboTime = 0.3f;
        recovTime = 0;
        
    }
    
    @Override
    public void activate(){}
    @Override
    public void deactivate(){}
    
    @Override
    public void effect() {
        
        if(!GameScreen.player.hasBuff(buff))
            buff = GameScreen.player.addBuff(new Buff_MuchHaste(30));
        else
            buff.refresh();
        
        reset();
        
    }
    
    private class Buff_MuchHaste extends Buff {

        private float modifier, buffValue;

        public Buff_MuchHaste(long duration) {
            super(duration);
            modifier = 0.1f;
            modifier *= GameScreen.player.getCurrentSpecial();
        }

        @Override
        public void applyBuff() {
            buffValue = modifier * GameScreen.player.getCurrentSpeed();
            GameScreen.player.setCurrentSpeed(GameScreen.player.getCurrentSpeed() + buffValue);

        }

        @Override
        public void removeBuff() {
            GameScreen.player.setCurrentSpeed(GameScreen.player.getCurrentSpeed() - buffValue);

        }

    }

}
