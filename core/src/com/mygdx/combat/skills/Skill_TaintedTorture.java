/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.Entity;
import com.mygdx.entities.StaticEntities.AoeCircle;
import com.mygdx.entities.ImageSprite;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.FrameCounter;
import static com.mygdx.utilities.UtilityVars.BIT_EN;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author saynt
 */
public class Skill_TaintedTorture extends SpecialSkill{

    private AoeCircle_Dot aoeCircle;
    public Skill_TaintedTorture(){
        name = "Tainted Torture";
        attribute = SkillAttribute.SPECIAL;
        desc = "Pain. Lots of Pain";
        skillIcon = MainGame.am.get(ResourceManager.SKILL_TAINTEDTORTURE);
        
        
    }
    
    @Override
    public void effect(boolean isCombo, Skill prevSkill) {
        
        try {
            if (aoeCircle == null
                    || aoeCircle.getDurationFC().complete
                    || (aoeCircle != null && !EnvironmentManager.currentEnv.equals(aoeCircle.getEnvironment()))) {
                aoeCircle = new AoeCircle_Dot(GameScreen.player.getBody().getPosition().cpy().scl(PPM));
                EnvironmentManager.currentEnv.spawnEntity(aoeCircle);
            }else{
                aoeCircle.effect();
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        
        impactSound.play(false);
    }

    @Override
    public void activate() {
    }

    @Override
    public void deactivate() {
    }
    
    private class AoeCircle_Dot extends AoeCircle {
        
        //private FrameCounter damageTickFC = new FrameCounter(0.5f);
        private Array<Entity> targets = new Array<Entity>();
        private Array<FrameCounter> targetsFCs = new Array<FrameCounter>();
        private int dmgAmmount;
        
        public AoeCircle_Dot(Vector2 pos) {
            super(pos);
            
            fd.filter.categoryBits = BIT_WALL;
            fd.filter.maskBits = BIT_EN;
            
            isprite = new ImageSprite("aoe-dmg", true);
            isprite.sprite.setBounds(0, 0, width*2, height*2);
            
            targets = new Array<Entity>();
            targetsFCs = new Array<FrameCounter>();
            
            dmgAmmount = (int)(GameScreen.player.getCurrentDamage() * 0.25f 
                    * GameScreen.player.getCurrentSpecial());
        }
        
        @Override
        public void alert(String str) {
            
            System.out.println("@TaintedTOuch " + str);
            //add enemy to targets
            if(active){
                //add target
                for(Entity e : EnvironmentManager.currentEnv.getEntities()){
                    if(!targets.contains(e, true) 
                            && e.getUserData().toString().equals(str)
                            && str.contains("en_")){
                        targets.add(e);
                        targetsFCs.add(new FrameCounter(0.5f));
                        targetsFCs.peek().start(fm);
                    }
                }
            }else{
                //remove target
                for(Entity e : EnvironmentManager.currentEnv.getEntities()){
                    if (targets.contains(e, true)) {
                        int index = targets.indexOf(e, true);
                        targets.removeIndex(index);
                        targetsFCs.removeIndex(index);
                    }
                }
            }
            
            if (str.equals("active")) {
                active = true;
            }
            if (str.equals("inactive")) {
                active = false;
            }
            
        }
        
        
        @Override
        public void update(){
            super.update();
            
            for(int i = 0; i < targets.size; i++){
                //dmg enemies in circle
                try {
                    if (targetsFCs.get(i).complete 
                            && targets.get(i).getBody() != null) {
                        //damage this enemy
                        targets.get(i).damage(dmgAmmount);
                        targetsFCs.get(i).start(fm);
                    }

                } catch (IndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                }
            }
            
        }
        
        public void effect(){
            for (int i = 0; i < targets.size; i++) {
                //dmg enemies in circle
                try {
                    if (targets.get(i).getBody() != null) {
                        targets.get(i).damage(dmgAmmount * comboBonus, true);
                    }

                } catch (IndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }
    
}