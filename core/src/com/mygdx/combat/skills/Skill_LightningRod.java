/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills;

import static com.mygdx.combat.skills.Skill.SkillAttribute.ENERGY;
import com.mygdx.entities.esprites.EntitySprite;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.gui.descriptions.DescriptionWindow;
import com.mygdx.managers.ResourceManager;
import com.mygdx.utilities.SoundObject_Sfx;

/**
 *
 * @author saynt
 */
public class Skill_LightningRod extends LightSkill{

    public Skill_LightningRod(){
        name = "Lightning Rod";
        attribute = ENERGY;
        COST /= 2;
        damageMod = 0.5f;
        desc = "Half the effort. Half the crackle";
        descWindow = new DescriptionWindow(name, desc, type);
        
        skillIcon = MainGame.am.get(ResourceManager.SKILL_CRACKOFLIGHTNING);
        
        
        impactTemplates.add(new EntitySprite("poe-attack4", false));
        impactTemplates.get(0).sprite.setScale(1.4f*RATIO);
        impactTemplates.add(new EntitySprite("poe-attack3", false));
        impactTemplates.get(1).sprite.setScale(1.4f*RATIO);
        
        skillSprite = new EntitySprite("light-att-yellow",false);
        skillSprite.sprite.setScale(0.5f*RATIO);
        
        impactSound = new SoundObject_Sfx(ResourceManager.SFX_IMPACT_1);
        
        
    }
    
    @Override
    public void activate() {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deactivate() {
         //To change body of generated methods, choose Tools | Templates.
    }
    
}
