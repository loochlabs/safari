/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills;

import static com.mygdx.combat.skills.Skill.SkillAttribute.ENERGY;
import com.mygdx.entities.Entity;
import com.mygdx.entities.esprites.EntitySprite;
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
public class Skill_CrackOfThunder extends HeavySkill{

    //private final float FORCE;
    
    @Override
    public float getCost(){
        //cost = all players energy
        
        if(!active){
            COST = GameScreen.player.getEnergy() < 40 ? 40 : GameScreen.player.getEnergy();
        }
        return COST;
    }
    
    public Skill_CrackOfThunder(){
        name = "Crack of Thunder";
        damageMod = 4f;
        attribute = ENERGY;
        
        desc = "Release the Crackle";
        descWindow = new DescriptionWindow(name, desc, type);
        skillIcon = MainGame.am.get(ResourceManager.SKILL_CRACKOFTHUNDER);
        
        
        impactTemplates.add(new EntitySprite("impact1", false));
        impactTemplates.get(0).sprite.setScale(1.4f*RATIO);
        impactTemplates.add(new EntitySprite("impact2", false));
        impactTemplates.get(1).sprite.setScale(1.4f*RATIO);
        
        
        skillSprite = new EntitySprite("heavy-att-yellow",false);
        skillSprite.sprite.setScale(0.5f*RATIO);
        
        impactSound = new SoundObject_Sfx(ResourceManager.SFX_IMPACT_2);
    }
    
    @Override
    public void activate(){}
    @Override
    public void deactivate(){}
    
    @Override
    public void damageEnemy(Entity e, boolean combo, Skill prevSkill) {
        
        float dmg = GameScreen.player.getCurrentDamage() * GameScreen.player.getHeavyMod()
                * (COST / GameScreen.player.getCurrentEnergy()) * damageMod;
        
        if (combo) {
            
            comboEffect(prevSkill);
            
            e.damage(
                    dmg * comboBonus,
                    true);
        } else {
            e.damage(dmg);
        }
    }
    
    @Override 
    public void comboEffect(Skill prevSkill){
        if(prevSkill.getType() != type && prevSkill.getAttribute() == attribute){
            GameScreen.player.addEnergy(COST / 2);
        }
    }
    
}