/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.mygdx.combat.Buff;
import com.mygdx.combat.PassiveBuff;
import static com.mygdx.combat.skills.Skill.SkillAttribute.SPEED;
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
public class Skill_GhostJab extends LightSkill{
    
    private final float BUFF_MOD;
    private final PassiveBuff pbuff;
    
    public Skill_GhostJab(){
        name = "Ghost Jab";
        attribute = SPEED;
        damageMod = 1.0f;
        BUFF_MOD = 1.025f;
        desc = "The need for speed";
        descWindow = new DescriptionWindow(name, desc, type);
        
        
        pbuff = new PassiveBuff_GhostJab();
        
        skillIcon = MainGame.am.get(ResourceManager.SKILL_GHOSTJAB);
        
        
        impactTemplates.add(new ImageSprite("poe-attack4", false));
        impactTemplates.get(0).sprite.setScale(1.4f*RATIO);
        impactTemplates.add(new ImageSprite("poe-attack3", false));
        impactTemplates.get(1).sprite.setScale(1.4f*RATIO);
        
        
        impactSound = new SoundObject_Sfx(ResourceManager.SFX_IMPACT_1);
        
    }
    
    @Override
    public void comboEffect(Skill prevSkill){}
    
    @Override 
    public void activate(){
        GameScreen.player.addBuff(pbuff);
    }
    @Override
    public void deactivate(){
        GameScreen.player.removeBuff(pbuff);
    }  
    
    @Override
    public void addBuff() {
        GameScreen.player.addBuff(new Buff_GhostJab(5, BUFF_MOD));
    }
    
    private class Buff_GhostJab extends Buff {

        private float modifier, buffValue;

        private ParticleEffect buff_effect;
        private ParticleEffectPool effectPool;
        private ParticleEffectPool.PooledEffect effect;

        public Buff_GhostJab(long duration, float mod) {
            super(duration);
            modifier = mod;

            //particle effect
            buff_effect = new ParticleEffect();
            buff_effect.load(Gdx.files.internal("effects/blue-buff.p"), Gdx.files.internal("effects"));
            effectPool = new ParticleEffectPool(buff_effect, 0, 200);
        }

        @Override
        public void applyBuff() {
            buffValue = modifier * GameScreen.player.getCurrentSpeed() - GameScreen.player.getCurrentSpeed();
            GameScreen.player.setCurrentSpeed(GameScreen.player.getCurrentSpeed() + buffValue);

            //impact effect
            effect = effectPool.obtain();
            GameScreen.player.addEffect(effect);
        }

        @Override
        public void removeBuff() {
            GameScreen.player.setCurrentSpeed(GameScreen.player.getCurrentSpeed() - buffValue);

            GameScreen.player.removeEffect(effect);
        }
    }

    
    private class PassiveBuff_GhostJab extends PassiveBuff {

        private final int modifier;

        public PassiveBuff_GhostJab() {
            super();

            modifier = 1;
        }

        @Override
        public void applyBuff() {
            super.applyBuff();

            GameScreen.player.addStatPoints(0, 0, 0, modifier, 0);

        }

        @Override
        public void removeBuff() {
            super.removeBuff();

            GameScreen.player.addStatPoints(0, 0, 0, -modifier, 0);
        }

    }
}
    

