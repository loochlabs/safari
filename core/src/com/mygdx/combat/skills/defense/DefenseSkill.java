/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills.defense;

import com.mygdx.combat.skills.Skill;
import static com.mygdx.combat.skills.Skill.SkillType.DEFENSE;
import com.mygdx.screen.GameScreen;

/**
 *
 * @author saynt
 * 
 * 
 * TEMP DASH SKILL
 * todo: break dash into subclass
 * 
 */
public class DefenseSkill extends Skill{

    private final float DASHMOD = 2.6f;
    
    public DefenseSkill(){
        type = DEFENSE;
        COST = 20.0f;
        attribute = SkillAttribute.NONE;
        
    }
    
    public void effect(boolean isCombo, Skill prevSkill, boolean isComboChain){
        //if (dashFC.running) {
            //if (dashFC.state == UtilityVars.AttackState.ATTACKING
                    //&& dashSpeed > body.getLinearVelocity().x) {
                //edit: (2/17/16) 
                //apply persistent force on player, based on current direction
                float speed = GameScreen.player.getCurrentSpeed() * DASHMOD;
                GameScreen.player.getBody().applyForce(GameScreen.player.getCurrentDirection().cpy().scl(speed), GameScreen.player.getBody().getPosition(), true);

            //} else {
                //isprite = recovSprite;
            //}
        //}
        
        
        //RENDER/UPDATE DASH SPRITS
        
        /*
            if(dashFC.running){
                dashSprites.add(new Sprite(isprite.sprite));
                dashAlphas.add(1.0f);
            }else if(dashFC.complete && dashSprites.size > 0){
                boolean clearAlphas = true;
                for (Float alpha : dashAlphas) {
                    if (alpha > 0) {
                        clearAlphas = false;
                    }
                }
            
            
                if(clearAlphas){
                    dashSprites.clear();
                    dashAlphas.clear();
                }
                
            }
        */
    }
    
    public void removeEffect(){
        //if (!dashFC.running && !canDash) {
            //canDash = true;
            //DASHMOD /= DASHMOD;
        //}
    }
    
    @Override
    public void activate() {
        
    }

    @Override
    public void deactivate() {
        
    }
    
}
