/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import static com.mygdx.combat.skills.Skill.SkillAttribute.SPEED;
import static com.mygdx.combat.skills.Skill.SkillType.LIGHT;
import com.mygdx.entities.Entity;
import com.mygdx.entities.esprites.EntitySprite;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.gui.descriptions.DescriptionWindow;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.FrameCounter;
import com.mygdx.utilities.SoundObject_Sfx;
import static com.mygdx.utilities.UtilityVars.BIT_ATT;
import static com.mygdx.utilities.UtilityVars.BIT_EN;
import static com.mygdx.utilities.UtilityVars.BIT_TEAR;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class Skill_GhostJab extends LightSkill{
    
    public Skill_GhostJab(){
        super();
        name = "Ghost Jab";
        attribute = SPEED;
        damageMod = 1.0f;
        desc = "The need for speed";
        comboChain = new SkillType[] { LIGHT, LIGHT, LIGHT };
        descWindow = new DescriptionWindow(name, desc, comboChain);
        
        skillIcon = MainGame.am.get(ResourceManager.SKILL_GHOSTJAB);
      
        comboSound = new SoundObject_Sfx(ResourceManager.SFX_SKILL_GHOST_1);
    }
    
    
    /***************************
        COMBO EFFECT
    ***************************/
    
    private float COMBO_FORCE = 1800f;
    
    @Override
    public void comboEffect(){
        super.comboEffect();
        //dash attack
        System.out.println("@GhostJab Combo effect!");
        
        //apply dash in player move direction
        if (GameScreen.player.isUserMoving()) {
            Vector2 dir = GameScreen.player.getCurrentDirection().cpy();
            dir.scl(COMBO_FORCE);
            GameScreen.player.getBody().applyForce(dir, GameScreen.player.getBody().getPosition(), true);
        }
        
        //spawn GhostDashSensor
        EnvironmentManager.currentEnv.spawnEntity(new GhostDashSensor(GameScreen.player.getPos().cpy()), true);
        
        //sound
        comboSound.play(false);
    }
    
    private class GhostDashSensor extends Entity{

        private final FrameCounter durationFC = new FrameCounter(0.65f);
        private final FrameCounter damageTick = new FrameCounter(0.1f);
        private EntitySprite ghostSprite;
        
        public GhostDashSensor(Vector2 pos) {
            super(pos, 100f*RATIO,100f*RATIO);

            bd.position.set(pos.x / PPM, pos.y / PPM);
            bd.type = BodyDef.BodyType.DynamicBody;
            //bd.linearDamping = 8.0f;
            cshape.setRadius(width / PPM);
            fd.shape = cshape;
            userdata = "bullet_" + id;
            fd.filter.categoryBits = BIT_ATT;
            fd.filter.maskBits = BIT_EN | BIT_TEAR;
            fd.isSensor = true;

            //ghost sprite template
            ghostSprite = new EntitySprite(pos, width*2, height*2, "ghostjab-combo", 
                    false, true, false, false, 1.0f, false, false, false, false);
            
        }

        @Override
        public void init(World world) {
            try {
                body = world.createBody(bd);
                body.createFixture(fd).setUserData(userdata);
                body.setUserData(userdata);

                durationFC.start(fm);
                damageTick.start(fm);
                
                EnvironmentManager.currentEnv.spawnEntity(new GhostSprite(ghostSprite, pos.cpy()), true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        @Override
        public void update() {
            body.setLinearVelocity(GameScreen.player.getBody().getLinearVelocity().cpy().scl(1.15f));

            if (durationFC.complete) {
                dispose();
            }
            
            //create new esprite
            if (damageTick.complete) {
                EnvironmentManager.currentEnv.spawnEntity(new GhostSprite(ghostSprite, pos.cpy()), true);
                damageTick.start(fm);
            }

            super.update();
        }
        
        @Override
        public void alert(String[] str) {
            try {
                if (str[0].equals("begin") && str[1].contains(userdata.toString())) {
                    for (Entity e : EnvironmentManager.currentEnv.getEntities()) {
                        if (e.getUserData() != null
                                && e.getUserData().equals(str[2])) {

                            damageEnemy(e);
                        }
                    }
                }
            } catch (IndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }
        }


    }
    
    
    private class GhostSprite extends EntitySprite{
        
        public GhostSprite(EntitySprite esprite, Vector2 pos) {
            super(esprite, pos);
        }
        
        @Override
        public void update(){
            super.update();
        
            if(isprite.isComplete()){
                dispose();
            }
        }
        
    }
    
    
    
}
    

