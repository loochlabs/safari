/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills;

import static com.mygdx.combat.skills.Skill.SkillAttribute.LIFE;
import static com.mygdx.combat.skills.Skill.SkillType.HEAVY;
import static com.mygdx.combat.skills.Skill.SkillType.LIGHT;
import com.mygdx.entities.Entity;
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
 */
public class Skill_MommasTouch extends LightSkill{
    
    private final float healAmmount = 1;
    private final float healChance = 0.05f;
    
    public Skill_MommasTouch(){
        name = "Momma's Touch";
        attribute = LIFE;
        damageMod = 1.0f;
        desc = "Little bit of love";
        comboChain = new SkillType[] { LIGHT, LIGHT, HEAVY };
        descWindow = new DescriptionWindow(name, desc, comboChain);
        
        skillIcon = MainGame.am.get(ResourceManager.SKILL_MOMMASTOUCH);
        
        
        impactTemplates.add(new ImageSprite("poe-attack4", false));
        impactTemplates.get(0).sprite.setScale(1.4f*RATIO);
        impactTemplates.add(new ImageSprite("poe-attack3", false));
        impactTemplates.get(1).sprite.setScale(1.4f*RATIO);
        
        
        impactSound = new SoundObject_Sfx(ResourceManager.SFX_IMPACT_1);
        
        skillSprite = new ImageSprite("light-attack-green",false);
        skillSprite.sprite.setScale(0.35f*RATIO);
        
    }
    /*
    @Override
    public void damageEnemy(Entity e, boolean combo, Skill prevSkill) {
        super.damageEnemy(e, combo, prevSkill);
        
        chanceToHeal();
    }
    
    private void chanceToHeal() {
        //on 5% chance, heal on hit
        if (rng.nextFloat() < healChance) {
            GameScreen.player.restoreHp(healAmmount);
        }
    }
    
    @Override
    public void comboEffect(Skill prevSkill){}
*/
    
}
