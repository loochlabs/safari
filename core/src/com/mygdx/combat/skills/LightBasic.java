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
import static com.mygdx.combat.skills.Skill.SkillType.LIGHT;
import com.mygdx.entities.ImageSprite;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.gui.descriptions.DescriptionWindow;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.SoundObject_Sfx;

/**
 *
 * @author saynt
 * 
 * 
 * Default Light skill loaded on character
 * 
 */
public class LightBasic extends LightSkill{
    
    public LightBasic(){
        name = "Basic Light Attack";
        attribute = SPEED;
        damageMod = 1.0f;
        desc = "Basic light attack";
        comboChain = new SkillType[] { LIGHT, LIGHT };
        descWindow = new DescriptionWindow(name, desc, comboChain);
        
        skillIcon = MainGame.am.get(ResourceManager.SKILL_BLANK);
        
        
        impactTemplates.add(new ImageSprite("poe-attack4", false));
        impactTemplates.get(0).sprite.setScale(1.4f*RATIO);
        impactTemplates.add(new ImageSprite("poe-attack3", false));
        impactTemplates.get(1).sprite.setScale(1.4f*RATIO);
        
        
        impactSound = new SoundObject_Sfx(ResourceManager.SFX_IMPACT_1);
        
    }
    
    
}
