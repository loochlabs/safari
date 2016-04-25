/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills.defense;

import com.mygdx.combat.skills.Skill;
import static com.mygdx.combat.skills.Skill.SkillType.DEFENSE;
import com.mygdx.game.MainGame;
import com.mygdx.gui.descriptions.DescriptionWindow;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.SoundObject_Sfx;

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

    private final float DASHMOD = 3.1f;
    
    public DefenseSkill(){
        name = "Dash";
        desc = "A hastey retreat";
        type = DEFENSE;
        attribute = SkillAttribute.NONE;
        descWindow = new DescriptionWindow(name, desc, comboChain);
        
        skillIcon = MainGame.am.get(ResourceManager.SKILL_DASH);
        
        impactSound = new SoundObject_Sfx(ResourceManager.SFX_DASH);
    }
    
    @Override
    public void effect() {
        float speed = GameScreen.player.getCurrentSpeed() * DASHMOD;
        GameScreen.player.getBody().applyForce(GameScreen.player.getCurrentDirection().cpy().scl(speed), GameScreen.player.getBody().getPosition(), true);

        
        
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

    @Override
    public void activate() {
        
    }

    @Override
    public void deactivate() {
        
    }
    
    
}
