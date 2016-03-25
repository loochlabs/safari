/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.combat.PlayerProjectile;
import static com.mygdx.combat.skills.Skill.SkillAttribute.SPEED;
import com.mygdx.entities.ImageSprite;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.gui.descriptions.DescriptionWindow;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.SoundObject_Sfx;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class Skill_HauntHaste extends HeavySkill{

    //private final float FORCE;
    private float speedMod;
    private final float SPEED_VALUE = 0.015f;
    
    public Skill_HauntHaste(){
        name = "Haunt Haste";
        attribute = SPEED;
        damageMod = 1.0f;
        FORCE = 500.0f;
        
        desc = "More speed = More DMG";
        descWindow = new DescriptionWindow(name, desc, type);
        skillIcon = MainGame.am.get(ResourceManager.SKILL_HAUNTHASTE);
        
        impactTemplates.add(new ImageSprite("impact1", false));
        impactTemplates.get(0).sprite.setScale(1.4f*RATIO);
        impactTemplates.add(new ImageSprite("impact2", false));
        impactTemplates.get(1).sprite.setScale(1.4f*RATIO);
        
        impactSound = new SoundObject_Sfx(ResourceManager.SFX_IMPACT_2);
    }
    
    @Override
    public void activate(){}
    @Override
    public void deactivate(){}
    
    @Override 
    public void comboChainEffect(Skill prevSkill){
        
        float projDamage = GameScreen.player.getCurrentDamage() * GameScreen.player.getLightMod();
        float projSpeed = 3f;
        
        //if(prevSkill.getType() != type && prevSkill.getAttribute() == attribute){
            EnvironmentManager.currentEnv.spawnEntity(
                new PlayerProjectile(
                        new Vector2(
                                GameScreen.player.getBody().getPosition().x * PPM, 
                                GameScreen.player.getBody().getPosition().y * PPM),
                        30f*RATIO, 30f*RATIO,
                        new Vector2(0,1).scl(projSpeed),
                        projDamage));
            EnvironmentManager.currentEnv.spawnEntity(
                new PlayerProjectile(
                        new Vector2(
                                GameScreen.player.getBody().getPosition().x * PPM, 
                                GameScreen.player.getBody().getPosition().y * PPM),
                        30f*RATIO, 30f*RATIO,
                        new Vector2(1,0).scl(projSpeed),
                        projDamage));
            EnvironmentManager.currentEnv.spawnEntity(
                new PlayerProjectile(
                        new Vector2(
                                GameScreen.player.getBody().getPosition().x * PPM, 
                                GameScreen.player.getBody().getPosition().y * PPM),
                        30f*RATIO, 30f*RATIO,
                        new Vector2(-1,0).scl(projSpeed),
                        projDamage));
            EnvironmentManager.currentEnv.spawnEntity(
                new PlayerProjectile(
                        new Vector2(
                                GameScreen.player.getBody().getPosition().x * PPM, 
                                GameScreen.player.getBody().getPosition().y * PPM),
                        30f*RATIO, 30f*RATIO,
                        new Vector2(0,-1).scl(projSpeed),
                        projDamage));
        //}
    }
    
    @Override
    public void removeComboChainEffect(){
        comboChain = false;
    }
    
    @Override
    public void effect(boolean isCombo, Skill prevSkill, boolean isComboChain) {
        
        //get current player speed
        speedMod = GameScreen.player.getCurrentSpeed() * SPEED_VALUE;
        //convert to dmg
        damageMod = 1 + speedMod;
        
        super.effect(isCombo, prevSkill, isComboChain);
        
    }
    
}
