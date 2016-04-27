/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import static com.mygdx.combat.skills.Skill.SkillAttribute.ENERGY;
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
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author saynt
 */
public class Skill_LightningRod extends LightSkill{

    public Skill_LightningRod(){
        super();
        name = "Lightning Rod";
        attribute = ENERGY;
        damageMod = 1f;
        desc = "Effect: Chance to resore energy";
        skillIcon = MainGame.am.get(ResourceManager.SKILL_CRACKOFLIGHTNING);
        
        comboChain = new SkillType[] { LIGHT, LIGHT, HEAVY };
        descWindow = new DescriptionWindow(name, desc, comboChain);
        
        /*
        impactTemplates.add(new ImageSprite("poe-attack4", false));
        impactTemplates.get(0).sprite.setScale(1.4f*RATIO);
        impactTemplates.add(new ImageSprite("poe-attack3", false));
        impactTemplates.get(1).sprite.setScale(1.4f*RATIO);
        */
        
        
        skillSprite = new ImageSprite("light-att-yellow",false);
        skillSprite.sprite.setScale(0.5f*RATIO);
        
        
        rngNegSet.add(1);
        rngNegSet.add(-1);
        
        comboSound = new SoundObject_Sfx(ResourceManager.SFX_SKILL_LIGHTNING_1);
    }
    
    private final int lightningCount = 5; 
    private final float spawnRange = 200f*RATIO; 
    protected final Random rng = new Random();
    protected final Array<Integer> rngNegSet = new Array<Integer>();
    
    @Override
    public void comboEffect(){
        //create lightning projectiles
        //within range of player
        //random circle
        super.comboEffect();
        
        
        for(int i = 0; i < lightningCount; i++){
            //find location
            //create lighting
            EnvironmentManager.currentEnv.spawnEntity(
                    new LightningEffect(new Vector2(
                            GameScreen.player.getPos().x + spawnRange*rng.nextFloat()*rngNegSet.random(), 
                            GameScreen.player.getPos().y + spawnRange*rng.nextFloat()*rngNegSet.random())),
                    true);
        }
        
        //sound
        comboSound.play(false);
        
    }
    
    private class LightningEffect extends Entity{

        private final FrameCounter durationFC = new FrameCounter(1.0f);
        private final FrameCounter damageTick = new FrameCounter(0.3f);
        private ArrayList<Entity> attTargets = new ArrayList<Entity>();
        
        private final EntitySprite lightningSprite;
        
        public LightningEffect(Vector2 pos) {
            super(pos, 65f*RATIO, 65f*RATIO);
            
            bd.position.set(pos.x / PPM, pos.y / PPM);
            bd.type = BodyDef.BodyType.DynamicBody;
            cshape.setRadius(width / PPM);
            fd.shape = cshape;
            userdata = "bullet_" + id;
            fd.filter.categoryBits = BIT_ATT;
            fd.filter.maskBits = BIT_EN | BIT_TEAR;
            fd.isSensor = true;
            
            flaggedForRenderSort = false;
            
            isprite = new ImageSprite("lightningrod-combo", false);
            isprite.sprite.setBounds(pos.x, pos.y, width*2, height*2);
            
            lightningSprite = new EntitySprite(pos, 140f*RATIO, 400f*RATIO, "lightningbolt", 
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
                
                body.applyForce(new Vector2(0, 5f), body.getPosition(), true);
                
                EnvironmentManager.currentEnv.spawnEntity(new LightningBolt(lightningSprite, pos.cpy()), true);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        @Override
        public void update() {
            if (durationFC.complete) {
                dispose();
            }
            
            if(damageTick.complete){
                for(Entity e : attTargets){
                    damageEnemy(e);
                }
                
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
                            addTarget(e);
                        }
                    }
                }
                if (str[0].equals("end") && str[1].contains(userdata.toString())) {
                    for (Entity e : EnvironmentManager.currentEnv.getEntities()) {
                        if (e.getUserData() != null
                                && e.getUserData().equals(str[2])) {

                            removeTarget(e);
                        }
                    }
                }
                
            } catch (IndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }
        }
        
        private void addTarget(Entity e){
            if(!attTargets.contains(e)){
                attTargets.add(e);
            }
        }
        
        private void removeTarget(Entity e) {
            if (attTargets.contains(e)) {
                attTargets.remove(e);
            }
        }
        
        
        private class LightningBolt extends EntitySprite {

            public LightningBolt(EntitySprite esprite, Vector2 pos) {
                super(esprite, pos);
                
                isprite.sprite.setPosition(pos.x +isprite.sprite.getWidth(), pos.y + isprite.sprite.getHeight() + height);
            }

            @Override
            public void update() {
                super.update();

                if (isprite.isComplete()) {
                    dispose();
                }
            }

  
        }
        
    }
    
    
}
