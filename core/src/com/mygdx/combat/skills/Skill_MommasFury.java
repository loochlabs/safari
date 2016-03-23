/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import static com.mygdx.combat.skills.Skill.SkillAttribute.LIFE;
import com.mygdx.entities.Entity;
import com.mygdx.entities.StaticEntities.AoeCircle;
import com.mygdx.entities.ImageSprite;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.gui.descriptions.DescriptionWindow;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.FrameCounter;
import com.mygdx.utilities.SoundObject_Sfx;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author saynt
 */
public class Skill_MommasFury extends HeavySkill {

    private float healChance = 0.5f;
    private final float healAmmount = 2f;
    private AoeCircle_Heal comboHealCircle;

    public Skill_MommasFury() {
        name = "Momma's Fury";
        damageMod = 1.50f;
        attribute = LIFE;

        desc = "Momma wants ta keep ya healthy";
        descWindow = new DescriptionWindow(name, desc, type);
        skillIcon = MainGame.am.get(ResourceManager.SKILL_MOMMASFURY);

        impactTemplates.add(new ImageSprite("impact1", false));
        impactTemplates.get(0).sprite.setScale(1.4f * RATIO);
        impactTemplates.add(new ImageSprite("impact2", false));
        impactTemplates.get(1).sprite.setScale(1.4f * RATIO);

        impactSound = new SoundObject_Sfx(ResourceManager.SFX_IMPACT_2);
        
        skillSprite = new ImageSprite("heavy-att-green",false);
        skillSprite.sprite.setScale(0.5f*RATIO);
    }

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
    public void comboEffect(Skill prevSkill) {

        if (prevSkill.getType() != type && prevSkill.getAttribute() == attribute) {
            //drop healing circle 
            try {
                if (comboHealCircle == null
                        || comboHealCircle.getDurationFC().complete
                        || (comboHealCircle != null && !EnvironmentManager.currentEnv.equals(comboHealCircle.getEnvironment()))) {
                    comboHealCircle = new AoeCircle_Heal(GameScreen.player.getBody().getPosition().cpy().scl(PPM));
                    EnvironmentManager.currentEnv.spawnEntity(comboHealCircle);
                }
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void activate() {
    }

    @Override
    public void deactivate() {
    }

    private class AoeCircle_Heal extends AoeCircle {
        
        private boolean active = false;
        private FrameCounter healTickFC = new FrameCounter(0.5f);
        
        public AoeCircle_Heal(Vector2 pos) {
            super(pos);
            
        }
        
        @Override
        public void init(World world){
            super.init(world);
            
            healTickFC.start(fm);
        }

        @Override
        public void alert(String [] str) {
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
            
            if(active && healTickFC.complete){
                GameScreen.player.restoreHp(healAmmount);
                healTickFC.start(fm);
            }
        }

    }
    
    
}
