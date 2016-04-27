/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills;

import static com.mygdx.combat.skills.Skill.SkillAttribute.SPEED;
import static com.mygdx.combat.skills.Skill.SkillType.LIGHT;
import com.mygdx.game.MainGame;
import com.mygdx.gui.descriptions.DescriptionWindow;
import com.mygdx.managers.ResourceManager;

/**
 *
 * @author saynt
 * 
 * 
 * Default Light skill loaded on character
 * 
 */
public class Skill_LightBasic extends LightSkill{
    
    public Skill_LightBasic(){
        super();
        name = "Basic Light Attack";
        attribute = SPEED;
        damageMod = 1.0f;
        desc = "Basic light attack";
        comboChain = new SkillType[] { LIGHT, LIGHT };
        descWindow = new DescriptionWindow(name, desc, comboChain);
        
        skillIcon = MainGame.am.get(ResourceManager.SKILL_LIGHTBASIC);
        
        /*
        impactTemplates.add(new ImageSprite("poe-attack4", false));
        impactTemplates.get(0).sprite.setScale(1.4f*RATIO);
        impactTemplates.add(new ImageSprite("poe-attack3", false));
        impactTemplates.get(1).sprite.setScale(1.4f*RATIO);
        */
        
        
    }
    
    
}
