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
        
        //prepTime = 0;
        //attTime = 0.4f;
        //recovTime = 0.2f;
        
        
        
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
    public void comboEffect(Skill prevSkill){
        
        float projDamage = GameScreen.player.getCurrentDamage() * GameScreen.player.getLightMod();
        float projSpeed = 3f;
        
        if(prevSkill.getType() != type && prevSkill.getAttribute() == attribute){
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
        }
    }
    
    @Override
    public void effect(boolean isCombo, Skill prevSkill) {
        
        //get current player speed
        speedMod = GameScreen.player.getCurrentSpeed() * SPEED_VALUE;
        //convert to dmg
        damageMod = 1 + speedMod;
        
        super.effect(isCombo, prevSkill);
        
    }
    
    /*
    @Override
    public void effect(boolean isCombo){
        
        reset();
        
        boolean playSound = false;
        
        //get current player speed
        speedMod = GameScreen.player.getCurrentSpeed() * SPEED_VALUE;
        //convert to dmg
        damageMod = 1 + speedMod;
        
        
        for(Entity ent: GameScreen.player.getAttTargets()){
            //ent.damage(GameScreen.player.getDamage() * GameScreen.player.getLightMod() * damageMod);
            if(isCombo){
                ent.damage(
                        GameScreen.player.getCurrentDamage() * GameScreen.player.getHeavyMod() * damageMod * comboBonus,
                        true);
            }else{
                ent.damage(GameScreen.player.getCurrentDamage() * GameScreen.player.getHeavyMod() * damageMod);
            }
            
            Vector2 dv = ent.getBody().getPosition().sub(GameScreen.player.getBody().getPosition()).cpy().nor();
            ent.getBody().applyForce(dv.scl(FORCE), ent.getBody().getPosition(), true);
            
            
            EntitySprite isprite = new EntitySprite(impactTemplates.get(rng.nextInt(impactTemplates.size)));      
            
            //flip sprite
            if(GameScreen.player.getBody().getPosition().x < ent.getBody().getPosition().x){
                isprite.setXFlip(true);
            }
                    
            GameScreen.player.addImpactSprite(ent.getBody().getPosition().x*PPM - isprite.sprite.getWidth()/2, 
                    ent.getBody().getPosition().y*PPM - isprite.sprite.getHeight()/2,
                    isprite);
            
            playSound = true;
            
        }
        
        if(playSound) impactSound.play(false);
        
        //permanance sprite
        PermSprite p = new PermSprite("perm2",
                new Vector2(
                        GameScreen.player.getBody().getPosition().x * PPM,
                        GameScreen.player.getBody().getPosition().y * PPM));
        p.start();
    }
    */
}
