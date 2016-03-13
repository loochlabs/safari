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
import static com.mygdx.combat.skills.Skill.SkillAttribute.DAMAGE;
import com.mygdx.game.MainGame;
import com.mygdx.gui.descriptions.DescriptionWindow;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;

/**
 *
 * @author looch
 */
public class Skill_PowerPlease extends SpecialSkill{

    private Buff buff;
    
    private ParticleEffect activate_effect;
    private ParticleEffectPool  effectPool;
    private ParticleEffectPool.PooledEffect effect;
    
    public Skill_PowerPlease(){
        name = "Power Please!";
        attribute = DAMAGE;
        skillIcon = MainGame.am.get(ResourceManager.SKILL_POWERPLEASE);
        type = SkillType.SPECIAL;
        desc = "More damage";
        descWindow = new DescriptionWindow(name, desc, type);
        
        
        
        activate_effect = new ParticleEffect();
        activate_effect.load(Gdx.files.internal("effects/yellow-power.p"), Gdx.files.internal("effects"));
        effectPool = new ParticleEffectPool(activate_effect, 0, 200);
    }
    
    @Override
    public void activate(){}
    @Override
    public void deactivate(){}
    
    @Override
    public void effect(boolean isCombo, Skill prevSkill) {
        
        if(!GameScreen.player.hasBuff(buff))
            buff = GameScreen.player.addBuff(new Buff_PowerPlease(30));
        else
            buff.refresh();
        
        impactSound.play(false);
        
        reset();
        
        //activate effect
        effect = effectPool.obtain();
        GameScreen.player.addEffect(effect);
        
    }
    
    private class Buff_PowerPlease extends Buff {

        private float modifier, buffValue;

        private ParticleEffect buff_effect;
        private ParticleEffectPool buffPool;
        private ParticleEffectPool.PooledEffect buffEffect;

        public Buff_PowerPlease(long duration) {
            super(duration);
            modifier = 0.25f;
            //modifier *= GameScreen.player.getCurrentSpecial();

            //particle effect
            buff_effect = new ParticleEffect();
            buff_effect.load(Gdx.files.internal("effects/yellow-buff.p"), Gdx.files.internal("effects"));
            buffPool = new ParticleEffectPool(buff_effect, 0, 200);
        }

        @Override
        public void applyBuff() {
            buffValue = modifier * GameScreen.player.getCurrentDamage();
            GameScreen.player.setCurrentDamage(GameScreen.player.getCurrentDamage() + buffValue);

            buffEffect = buffPool.obtain();
            GameScreen.player.addEffect(buffEffect);
        }

        @Override
        public void removeBuff() {
            GameScreen.player.setCurrentDamage(GameScreen.player.getCurrentDamage() - buffValue);

            GameScreen.player.removeEffect(buffEffect);
        }

    }

    
}
