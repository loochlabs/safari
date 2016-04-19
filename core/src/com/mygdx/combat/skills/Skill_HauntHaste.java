/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import static com.mygdx.combat.skills.Skill.SkillAttribute.SPEED;
import static com.mygdx.combat.skills.Skill.SkillType.HEAVY;
import static com.mygdx.combat.skills.Skill.SkillType.LIGHT;
import com.mygdx.entities.Entity;
import com.mygdx.entities.ImageSprite;
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
public class Skill_HauntHaste extends HeavySkill{

    //private final float FORCE;
    private float speedMod;
    private final float SPEED_VALUE = 0.015f;
    
    public Skill_HauntHaste(){
        name = "Haunt Haste";
        attribute = SPEED;
        damageMod = 1.0f;
        FORCE = 500.0f;
        comboChain = new SkillType[] { HEAVY, LIGHT, LIGHT };
        desc = "More speed = More DMG";
        descWindow = new DescriptionWindow(name, desc, comboChain);
        skillIcon = MainGame.am.get(ResourceManager.SKILL_HAUNTHASTE);
        
        impactTemplates.add(new ImageSprite("impact1", false));
        impactTemplates.get(0).sprite.setScale(1.4f*RATIO);
        impactTemplates.add(new ImageSprite("impact2", false));
        impactTemplates.get(1).sprite.setScale(1.4f*RATIO);
        
        impactSound = new SoundObject_Sfx(ResourceManager.SFX_IMPACT_2);
    }
    
    
    @Override
    public void effect(){
        
        //get current player speed
        speedMod = GameScreen.player.getCurrentSpeed() * SPEED_VALUE;
        //convert to dmg
        damageMod = 1 + speedMod;
        
        super.effect();
        
    }
    
    
    /***************************
        COMBO EFFECT
    ***************************/
    
    private float COMBO_FORCE = 1800f;
    
    @Override
    public void comboEffect(){
        super.comboEffect();
        //dash attack
        System.out.println("@HauntHaste Combo effect!");
        
        //apply dash in player move direction
        if (GameScreen.player.isUserMoving()) {
            Vector2 dir = GameScreen.player.getCurrentDirection().cpy();
            dir.scl(COMBO_FORCE);
            GameScreen.player.getBody().applyForce(dir, GameScreen.player.getBody().getPosition(), true);
        }
        
        //spawn GhostDashSensor
        EnvironmentManager.currentEnv.spawnEntity(new HauntTrailSensor(GameScreen.player.getPos().cpy()));
        
        
    }
    
    private class HauntTrailSensor extends Entity{

        private final FrameCounter durationFC = new FrameCounter(0.65f);
        private final FrameCounter damageTick = new FrameCounter(0.1f);
        private EntitySprite ghostSprite;
        
        public HauntTrailSensor(Vector2 pos) {
            super(pos, 100f*RATIO,100f*RATIO);

            bd.position.set(pos.x / PPM, pos.y / PPM);
            bd.type = BodyDef.BodyType.DynamicBody;
            //bd.linearDamping = 8.0f;
            cshape.setRadius(width / PPM);
            fd.shape = cshape;
            userdata = "bullet_" + id;
            fd.filter.categoryBits = BIT_ATT;
            fd.filter.maskBits = BIT_EN;
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
                
                EnvironmentManager.currentEnv.spawnEntity(new GhostSprite(ghostSprite, pos.cpy()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        @Override
        public void update() {
            body.setLinearVelocity(GameScreen.player.getBody().getLinearVelocity().cpy().scl(1.15f));

            if (durationFC.complete) {
                EnvironmentManager.currentEnv.spawnEntity(new HauntEndSensor(pos.cpy()));
                
                dispose();
            }
            
            //create new esprite
            if (damageTick.complete) {
                EnvironmentManager.currentEnv.spawnEntity(new GhostSprite(ghostSprite, pos.cpy()));
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
    
    private class HauntEndSensor extends Entity{

        private final FrameCounter durationFC = new FrameCounter(0.65f);
        private final FrameCounter damageTick = new FrameCounter(0.1f);
        private EntitySprite ghostSprite;
        
        private final FixtureDef northfd = new FixtureDef();
        private final FixtureDef southfd = new FixtureDef();
        private final FixtureDef eastfd = new FixtureDef();
        private final FixtureDef westfd = new FixtureDef();
        
        public HauntEndSensor(Vector2 pos) {
            super(pos, 10f*RATIO,10f*RATIO);

            bd.position.set(pos.x / PPM, pos.y / PPM);
            bd.type = BodyDef.BodyType.DynamicBody;
            cshape.setRadius(width / PPM);
            fd.shape = cshape;
            userdata = "bullet_" + id;
            fd.filter.categoryBits = BIT_ATT;
            fd.filter.maskBits = BIT_EN | BIT_TEAR;
            fd.isSensor = true;
            
            PolygonShape nsh = new PolygonShape();
            nsh.setAsBox(50f*RATIO/PPM, 150f*RATIO/PPM, new Vector2(0, 0.75f*RATIO), 0);
            northfd.shape = nsh;
            northfd.filter.categoryBits = BIT_ATT;
            northfd.filter.maskBits = BIT_EN | BIT_TEAR;
            northfd.isSensor = true;
            
            PolygonShape ssh = new PolygonShape();
            ssh.setAsBox(50f*RATIO/PPM, 150f*RATIO/PPM, new Vector2(0, -0.75f*RATIO), 0);
            southfd.shape = ssh;
            southfd.filter.categoryBits = BIT_ATT;
            southfd.filter.maskBits = BIT_EN | BIT_TEAR;
            southfd.isSensor = true;
            
            PolygonShape wsh = new PolygonShape();
            wsh.setAsBox(150f*RATIO/PPM, 50f*RATIO/PPM, new Vector2(-0.75f*RATIO, 0), 0);
            westfd.shape = wsh;
            westfd.filter.categoryBits = BIT_ATT;
            westfd.filter.maskBits = BIT_EN | BIT_TEAR;
            westfd.isSensor = true;
            
            PolygonShape esh = new PolygonShape();
            esh.setAsBox(150f*RATIO/PPM, 50f*RATIO/PPM, new Vector2(0.75f*RATIO, 0), 0);
            eastfd.shape = esh;
            eastfd.filter.categoryBits = BIT_ATT;
            eastfd.filter.maskBits = BIT_EN | BIT_TEAR;
            eastfd.isSensor = true;
            

            //ghost sprite template
            ghostSprite = new EntitySprite(pos, 300f*RATIO, 300f*RATIO, "haunt-combo", 
                    false, true, false, false, 1.0f, false, false, false, false);
            
        }

        @Override
        public void init(World world) {
            try {
                body = world.createBody(bd);
                body.createFixture(fd).setUserData(userdata);
                body.setUserData(userdata);
                
                body.createFixture(northfd).setUserData(userdata);
                body.createFixture(westfd).setUserData(userdata);
                body.createFixture(eastfd).setUserData(userdata);
                body.createFixture(southfd).setUserData(userdata);
                
                durationFC.start(fm);
                damageTick.start(fm);
                
                EnvironmentManager.currentEnv.spawnEntity(new GhostSprite(ghostSprite, pos.cpy()));
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
