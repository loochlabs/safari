/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills;

import static com.mygdx.combat.skills.Skill.SkillType.HEAVY;
import static com.mygdx.combat.skills.Skill.SkillType.LIGHT;
import com.mygdx.entities.ImageSprite;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.gui.descriptions.DescriptionWindow;
import com.mygdx.managers.ResourceManager;
import com.mygdx.utilities.SoundObject_Sfx;

/**
 *
 * @author saynt
 */
public class HeavyBasic extends HeavySkill{

    
    public HeavyBasic(){
        name = "Basic Heavy";
        damageMod = 1.55f;
        FORCE = 1000.0f;
        comboChain = new SkillType[] { HEAVY, HEAVY };
        desc = "A basic heavy attack";
        skillIcon = MainGame.am.get(ResourceManager.SKILL_BLANK);
        descWindow = new DescriptionWindow(name, desc, comboChain);
        
        impactTemplates.add(new ImageSprite("impact1", false));
        impactTemplates.get(0).sprite.setScale(1.4f*RATIO);
        impactTemplates.add(new ImageSprite("impact2", false));
        impactTemplates.get(1).sprite.setScale(1.4f*RATIO);
        
        
        skillSprite = new ImageSprite("heavy-att-red",false);
        skillSprite.sprite.setScale(0.5f*RATIO);
        
        impactSound = new SoundObject_Sfx(ResourceManager.SFX_IMPACT_2);
    }
    
    
    
    
    
}
