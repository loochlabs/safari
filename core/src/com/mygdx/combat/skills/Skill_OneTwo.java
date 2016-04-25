/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
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
 * @author looch
 */
public class Skill_OneTwo extends LightSkill{
    
    
    
    public Skill_OneTwo(){
        name = "One Two";
        damageMod = 1.0f;
        desc = "More heavy damage";
        comboChain = new SkillType[] { LIGHT, HEAVY, LIGHT };
        descWindow = new DescriptionWindow(name, desc, comboChain);
        
        skillIcon = MainGame.am.get(ResourceManager.SKILL_ONETWO);
        
        /*
        impactTemplates.add(new ImageSprite("poe-attack4", false));
        impactTemplates.get(0).sprite.setScale(1.4f*RATIO);
        impactTemplates.add(new ImageSprite("poe-attack3", false));
        impactTemplates.get(1).sprite.setScale(1.4f*RATIO);
        */
        
        skillSprite = new ImageSprite("light-att-red",false);
        skillSprite.sprite.setScale(0.5f*RATIO);
        
        
        comboSound = new SoundObject_Sfx(ResourceManager.SFX_SKILL_ONETWO_1);
    }
    
    
    /***************************
        COMBO EFFECT
    ***************************/
    
    
    @Override
    public void comboEffect(){
        super.comboEffect();
        
        EnvironmentManager.currentEnv.spawnEntity(new OneTwoSensor(GameScreen.player.getPos().cpy()));
        
        
    }
    
    
    private class OneTwoSensor extends Entity{

        private final FrameCounter durationFC = new FrameCounter(0.65f);
        
        private Vector2 direction;
        private final float speed = 1500f;
        
        public OneTwoSensor(Vector2 pos) {
            super(pos, 50f*RATIO,200f*RATIO);

            bd.position.set(pos.x / PPM, pos.y / PPM);
            bd.type = BodyDef.BodyType.DynamicBody;
            shape.setAsBox(width/PPM, height/PPM);
            fd.shape = shape;
            userdata = "bullet_" + id;
            fd.filter.categoryBits = BIT_ATT;
            fd.filter.maskBits = BIT_EN | BIT_TEAR;
            fd.isSensor = true;
            
            bd.linearDamping = 9.0f;
            
            isprite = new ImageSprite("onetwo-combo", false);
            isprite.sprite.setSize(height*2, width*2);
            
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
                float ang = GameScreen.player.getCurrentAngle();
                float degree_ang = ang * (float) (180/Math.PI) + 180;
                
                //degree adjustment to make it easier to work with
                if(degree_ang >= 360)   degree_ang -= 360;
                
                
                if (degree_ang >= 225 && degree_ang < 315) {
                    //rotate north
                    body.setTransform(body.getPosition(), 0);
                    direction = new Vector2(0, 1);
                    isprite.sprite.setRotation(90);
                } else if (degree_ang >= 135 && degree_ang < 225) {
                    //rotate east
                    body.setTransform(body.getPosition(), 90 * (float) (Math.PI / 180)); 
                    direction = new Vector2(1,0);
                    isprite.sprite.setRotation(0);
                } else if (degree_ang >= 45 && degree_ang < 135) {
                    //south
                    body.setTransform(body.getPosition(), 180 * (float) (Math.PI / 180)); 
                    direction = new Vector2(0,-1);
                    isprite.sprite.setRotation(270);
                } else {
                    //west
                    body.setTransform(body.getPosition(), 270 * (float) (Math.PI / 180)); 
                    direction = new Vector2(-1, 0);
                    isprite.sprite.setRotation(180);
                }
                
                direction.scl(speed);
                body.applyForce(direction, body.getPosition(), true);

                
                //sound 
                comboSound.play(false);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        @Override
        public void update() {

            if (durationFC.complete) {
                dispose();
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
    
    
    
}
