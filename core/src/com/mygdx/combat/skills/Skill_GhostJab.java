/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.combat.Buff;
import com.mygdx.combat.PassiveBuff;
import static com.mygdx.combat.skills.Skill.SkillAttribute.SPEED;
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
public class Skill_GhostJab extends LightSkill{
    
    private final float BUFF_MOD;
    private final PassiveBuff pbuff;
    
    public Skill_GhostJab(){
        name = "Ghost Jab";
        attribute = SPEED;
        damageMod = 1.0f;
        BUFF_MOD = 1.025f;
        desc = "The need for speed";
        comboChain = new SkillType[] { LIGHT, LIGHT, LIGHT };
        descWindow = new DescriptionWindow(name, desc, comboChain);
        
        pbuff = new PassiveBuff_GhostJab();
        
        skillIcon = MainGame.am.get(ResourceManager.SKILL_GHOSTJAB);
        
        
        impactTemplates.add(new ImageSprite("poe-attack4", false));
        impactTemplates.get(0).sprite.setScale(1.4f*RATIO);
        impactTemplates.add(new ImageSprite("poe-attack3", false));
        impactTemplates.get(1).sprite.setScale(1.4f*RATIO);
        
        
        impactSound = new SoundObject_Sfx(ResourceManager.SFX_IMPACT_1);
        
    }
    
    
    
    @Override 
    public void activate(){
        super.activate();
        GameScreen.player.addBuff(pbuff);
    }
    @Override
    public void deactivate(){
        super.deactivate();
        GameScreen.player.removeBuff(pbuff);
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
        EnvironmentManager.currentEnv.spawnEntity(new GhostDashSensor(GameScreen.player.getPos().cpy()));
        
        
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
                
                EnvironmentManager.currentEnv.spawnEntity(new GhostSprite(ghostSprite, pos.cpy()));
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
    
    
    
    
    /**************
        BUFFS
    **************/
    
    @Override
    public void addBuff() {
        GameScreen.player.addBuff(new Buff_GhostJab(5, BUFF_MOD));
    }
    
    private class Buff_GhostJab extends Buff {

        private float modifier, buffValue;

        private ParticleEffect buff_effect;
        private ParticleEffectPool effectPool;
        private ParticleEffectPool.PooledEffect effect;

        public Buff_GhostJab(long duration, float mod) {
            super(duration);
            modifier = mod;

            //particle effect
            buff_effect = new ParticleEffect();
            buff_effect.load(Gdx.files.internal("effects/blue-buff.p"), Gdx.files.internal("effects"));
            effectPool = new ParticleEffectPool(buff_effect, 0, 200);
        }

        @Override
        public void applyBuff() {
            buffValue = modifier * GameScreen.player.getCurrentSpeed() - GameScreen.player.getCurrentSpeed();
            GameScreen.player.setCurrentSpeed(GameScreen.player.getCurrentSpeed() + buffValue);

            //impact effect
            effect = effectPool.obtain();
            GameScreen.player.addEffect(effect);
        }

        @Override
        public void removeBuff() {
            GameScreen.player.setCurrentSpeed(GameScreen.player.getCurrentSpeed() - buffValue);

            GameScreen.player.removeEffect(effect);
        }
    }

    
    private class PassiveBuff_GhostJab extends PassiveBuff {

        private final int modifier;

        public PassiveBuff_GhostJab() {
            super();

            modifier = 1;
        }

        @Override
        public void applyBuff() {
            super.applyBuff();

            GameScreen.player.addStatPoints(0, 0, 0, modifier, 0);

        }

        @Override
        public void removeBuff() {
            super.removeBuff();

            GameScreen.player.addStatPoints(0, 0, 0, -modifier, 0);
        }

    }
}
    

