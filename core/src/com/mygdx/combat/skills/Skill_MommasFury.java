/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import static com.mygdx.combat.skills.Skill.SkillAttribute.LIFE;
import static com.mygdx.combat.skills.Skill.SkillType.HEAVY;
import static com.mygdx.combat.skills.Skill.SkillType.LIGHT;
import com.mygdx.entities.Entity;
import com.mygdx.entities.ImageSprite;
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
 * @author saynt
 */
public class Skill_MommasFury extends HeavySkill {

    
    public Skill_MommasFury() {
        super();
        name = "Momma's Fury";
        damageMod = 1.50f;
        attribute = LIFE;
        desc = "Momma wants ta keep ya healthy";
        comboChain = new SkillType[] { HEAVY, HEAVY, LIGHT };

        descWindow = new DescriptionWindow(name, desc, comboChain);
        
        skillIcon = MainGame.am.get(ResourceManager.SKILL_MOMMASFURY);

        /*
        impactTemplates.add(new ImageSprite("impact1", false));
        impactTemplates.get(0).sprite.setScale(1.4f * RATIO);
        impactTemplates.add(new ImageSprite("impact2", false));
        impactTemplates.get(1).sprite.setScale(1.4f * RATIO);
        */
        
        
        skillSprite = new ImageSprite("heavy-att-green",false);
        skillSprite.sprite.setScale(0.5f*RATIO);
        
        comboSound = new SoundObject_Sfx(ResourceManager.SFX_SKILL_MOMMA_1);
    }

    
     /***************************
        COMBO EFFECT
    ***************************/
    
    
    @Override
    public void comboEffect(){
        super.comboEffect();
        
        EnvironmentManager.currentEnv.spawnEntity(new MommasFurySensor(GameScreen.player.getPos().cpy()), true);
        
        
    }
    
    private class MommasFurySensor extends Entity{
         private final FrameCounter durationFC = new FrameCounter(0.75f);
        
        private Vector2 direction;
        private final float speed = 1100f;
        
        public MommasFurySensor(Vector2 pos) {
            super(pos, 100f*RATIO,100f*RATIO);

            bd.position.set(pos.x / PPM, pos.y / PPM);
            bd.type = BodyDef.BodyType.DynamicBody;
            shape.setAsBox(width/PPM, height/PPM);
            fd.shape = shape;
            userdata = "bullet_" + id;
            fd.filter.categoryBits = BIT_ATT;
            fd.filter.maskBits = BIT_EN | BIT_TEAR;
            fd.isSensor = true;
            bd.linearDamping = 2.0f;
            
            isprite = new ImageSprite("mommastouch-combo", true);
            isprite.sprite.setSize(width*2, height*2);
            
            
            
        }

        @Override
        public void init(World world) {
            try {
                body = world.createBody(bd);
                body.createFixture(fd).setUserData(userdata);
                body.setUserData(userdata);

                durationFC.start(fm);

                //get player direction of movement 
                //set rotation
                float degree_ang = GameScreen.player.getCurrentAngle() * (float) (180/Math.PI) + 180;
                
                //degree adjustment to make it easier to work with
                if(degree_ang >= 360)   degree_ang -= 360;
                
                
                if (degree_ang >= 225 && degree_ang < 315) {
                    //rotate north
                    direction = new Vector2(0, 1);
                } else if (degree_ang >= 135 && degree_ang < 225) {
                    //rotate east
                    direction = new Vector2(1,0);
                } else if (degree_ang >= 45 && degree_ang < 135) {
                    //south
                    direction = new Vector2(0,-1);
                } else {
                    //west
                    direction = new Vector2(-1, 0);
                }
                
                direction.scl(speed);
                body.applyForce(direction, body.getPosition(), true);
                
                //sound
                comboSound.play(false);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        private float alpha = 1f;
        
        @Override
        public void update() {

            if (durationFC.complete) {
                dispose();
            }
            
            if(durationFC.CURRENT_FRAME > durationFC.MAX_FRAME*0.75f){
                alpha *= 0.8f;
                isprite.sprite.setAlpha(alpha);
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

                            //damage enemy and restore .25 life
                            GameScreen.player.restoreHp(damageEnemy(e) / 4);
                        }
                    }
                } 
            } catch (IndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
