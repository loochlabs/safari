/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills;

/**
 *
 * @author looch
 */
public class Skill_DashBolt extends Skill{
    //TEMP FOR DASH DEFENSE SKILL EDIT
    @Override
    public void activate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deactivate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*
    public Skill_DashBolt(){
        name = "Dash Bolt";
        type = SkillType.PASSIVE;
        skillIcon = MainGame.am.get(ResourceManager.SKILL_DASHBOLT);
        dashSkill = true;
        damageMod = 1.0f;
        desc = "Dash is now a light attack";
        descWindow = new DescriptionWindow(name, desc, type);
        
        impactTemplates.add(new ImageSprite("poe-attack4", false));
        impactTemplates.get(0).sprite.setScale(1.4f*RATIO);
        impactTemplates.add(new ImageSprite("poe-attack3", false));
        impactTemplates.get(1).sprite.setScale(1.4f*RATIO);
        
        //desc = new Desc_DashBolt();
    }
    
    @Override 
    public void activate(){}
    @Override
    public void deactivate(){}
    
    @Override
    public void effect() {
        
        for(Entity ent: GameScreen.player.getAttTargets()){
            //ent.damage(GameScreen.player.getDamage() * GameScreen.player.getLightMod() * damageMod);
            ent.damage(GameScreen.player.getCurrentDamage() * GameScreen.player.getLightMod() * damageMod);
                    
            ImageSprite isprite = new ImageSprite(impactTemplates.get(rng.nextInt(impactTemplates.size)));      
            
            //flip sprite
            if(GameScreen.player.getBody().getPosition().x < ent.getBody().getPosition().x){
                isprite.setXFlip(true);
            }
                    
            GameScreen.player.addImpactSprite(ent.getBody().getPosition().x*PPM - isprite.sprite.getWidth()/2, 
                    ent.getBody().getPosition().y*PPM - isprite.sprite.getHeight()/2,
                    isprite);
            
            
        }
        
        
        //shake screen
        //if(player.getAttTargets().size() > 0)
            //EnvironmentManager.currentEnv.shake();
         //screen shake
        if(!GameScreen.player.getAttTargets().isEmpty())
            GameScreen.camera.shake(3, 0.35f);
        
        reset();
    }
    
    
    @Override
    public void effect(boolean isCombo, Skill prevSkill, boolean isComboChain){
        
        for(Entity ent: GameScreen.player.getAttTargets()){
            //ent.damage(GameScreen.player.getDamage() * GameScreen.player.getLightMod() * damageMod);
            if(isCombo){
                ent.damage(
                        GameScreen.player.getCurrentDamage() * GameScreen.player.getLightMod() * damageMod,
                        true);
            }else{
                ent.damage(GameScreen.player.getCurrentDamage() * GameScreen.player.getLightMod() * damageMod);
            }
            
            ImageSprite isprite = new ImageSprite(impactTemplates.get(rng.nextInt(impactTemplates.size)));      
            
            //flip sprite
            if(GameScreen.player.getBody().getPosition().x < ent.getBody().getPosition().x){
                isprite.setXFlip(true);
            }
                    
            GameScreen.player.addImpactSprite(ent.getBody().getPosition().x*PPM - isprite.sprite.getWidth()/2, 
                    ent.getBody().getPosition().y*PPM - isprite.sprite.getHeight()/2,
                    isprite);
            
            
        }
        
        
        //shake screen
         //screen shake
        if(!GameScreen.player.getAttTargets().isEmpty())
            GameScreen.camera.shake(3, 0.35f);
        
        
        reset();
    }
    */
}
