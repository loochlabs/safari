/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills;

import com.mygdx.combat.Buff;
import com.mygdx.combat.PassiveBuff;
import com.mygdx.entities.ImageSprite;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.gui.descriptions.DescriptionWindow;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.SoundObject_Sfx;

/**
 *
 * @author looch
 */
public class Skill_OneTwo extends LightSkill{
    
    private final float BUFF_MOD;
    //private final float FORCE;
    private final PassiveBuff PBUFF;
    
    public Skill_OneTwo(){
        name = "One Two";
        //type = LIGHT;
        //COST = 20.0f;
        damageMod = 1.0f;
        BUFF_MOD = 0.05f;
        desc = "More heavy damage";
        descWindow = new DescriptionWindow(name, desc, type);
        
        //prepTime = 0;
        //attTime = 0.4f;
        //recovTime = 0;
        
        //FORCE = 250.0f;
        
        PBUFF = new PassiveBuff_OneTwo();
        
        
        skillIcon = MainGame.am.get(ResourceManager.SKILL_ONETWO);
        
        
        impactTemplates.add(new ImageSprite("poe-attack4", false));
        impactTemplates.get(0).sprite.setScale(1.4f*RATIO);
        impactTemplates.add(new ImageSprite("poe-attack3", false));
        impactTemplates.get(1).sprite.setScale(1.4f*RATIO);
        
        
        skillSprite = new ImageSprite("light-att-red",false);
        skillSprite.sprite.setScale(0.5f*RATIO);
        
        //sound
        impactSound = new SoundObject_Sfx(ResourceManager.SFX_IMPACT_1);
        
    }
    
    @Override
    public void activate(){
        GameScreen.player.addBuff(PBUFF);
    }
    @Override
    public void deactivate(){
        GameScreen.player.removeBuff(PBUFF);
    }
    
    @Override
    public void addBuff(){
        GameScreen.player.addBuff(new Buff_OneTwo(5, BUFF_MOD));
    }

    private class Buff_OneTwo extends Buff {

        private float modifier, buffValue;

        public Buff_OneTwo(long duration, float mod) {
            super(duration);

            modifier = mod;
        }

        @Override
        public void applyBuff() {
            buffValue = modifier * GameScreen.player.getHeavyMod();
            GameScreen.player.setHeavyMod(GameScreen.player.getHeavyMod() + buffValue);

        }

        @Override
        public void removeBuff() {
            GameScreen.player.setHeavyMod(GameScreen.player.getHeavyMod() - buffValue);

        }

    }
    
    private class PassiveBuff_OneTwo extends PassiveBuff {

        private final float modifier;
        private float buffValue;

        public PassiveBuff_OneTwo() {
            super();

            modifier = 0.2f;
        }

        @Override
        public void applyBuff() {
            super.applyBuff();

            buffValue = modifier * GameScreen.player.getHeavyMod();
            GameScreen.player.setHeavyMod(GameScreen.player.getHeavyMod() + buffValue);

        }

        @Override
        public void removeBuff() {
            super.removeBuff();

            GameScreen.player.setHeavyMod(GameScreen.player.getHeavyMod() - buffValue);
        }

    }

    
}
