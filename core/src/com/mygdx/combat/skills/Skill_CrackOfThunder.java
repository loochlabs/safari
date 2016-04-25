/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import static com.mygdx.combat.skills.Skill.SkillAttribute.ENERGY;
import static com.mygdx.combat.skills.Skill.SkillType.HEAVY;
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

/**
 *
 * @author saynt
 */
public class Skill_CrackOfThunder extends HeavySkill{

    public Skill_CrackOfThunder(){
        name = "Crack of Thunder";
        damageMod = 1.5f;
        attribute = ENERGY;
        comboChain = new SkillType[] { HEAVY, HEAVY, HEAVY };
        desc = "Release the Crackle";
        descWindow = new DescriptionWindow(name, desc, comboChain);
        skillIcon = MainGame.am.get(ResourceManager.SKILL_CRACKOFTHUNDER);
        
        /*
        impactTemplates.add(new ImageSprite("impact1", false));
        impactTemplates.get(0).sprite.setScale(1.4f*RATIO);
        impactTemplates.add(new ImageSprite("impact2", false));
        impactTemplates.get(1).sprite.setScale(1.4f*RATIO);
        */
        
        skillSprite = new ImageSprite("heavy-att-yellow",false);
        skillSprite.sprite.setScale(0.5f*RATIO);
        
        
        //sound
        comboSound = new SoundObject_Sfx(ResourceManager.SFX_SKILL_THUNDER_1);
        
    }
    
    private final float SPAWN_RANGE = 350f*RATIO;
    
    @Override
    public void comboEffect(){
        //create lightning projectiles
        //within range of player
        //random circle
        super.comboEffect();

        //bottom left
        EnvironmentManager.currentEnv.spawnEntity(
                new ThunderEffect(new Vector2(
                    GameScreen.player.getPos().x - SPAWN_RANGE, GameScreen.player.getPos().y - SPAWN_RANGE),
                    new Vector2(1,1)));
        
        //bottom right
        EnvironmentManager.currentEnv.spawnEntity(
                new ThunderEffect(new Vector2(
                    GameScreen.player.getPos().x + SPAWN_RANGE, GameScreen.player.getPos().y - SPAWN_RANGE),
                    new Vector2(-1,1)));

        //upper left
        EnvironmentManager.currentEnv.spawnEntity(
                new ThunderEffect(new Vector2(
                    GameScreen.player.getPos().x - SPAWN_RANGE, GameScreen.player.getPos().y + SPAWN_RANGE),
                    new Vector2(1,-1)));

        //upper right
        EnvironmentManager.currentEnv.spawnEntity(
                new ThunderEffect(new Vector2(
                    GameScreen.player.getPos().x + SPAWN_RANGE, GameScreen.player.getPos().y + SPAWN_RANGE),
                    new Vector2(-1,-1)));
        
        
        //sound
        comboSound.play(false);
    }
    
    private class ThunderEffect extends Entity{

        private final FrameCounter durationFC = new FrameCounter(1.0f);
        private final FrameCounter damageTick = new FrameCounter(0.25f);
        //private ArrayList<Entity> attTargets = new ArrayList<Entity>();
        
        private final Vector2 direction;
        private float speed = 6.5f;
        
        private final EntitySprite lightningSprite;
        
        public ThunderEffect(Vector2 pos, Vector2 direction) {
            super(pos, 100f*RATIO, 100f*RATIO);
            
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
            
            this.direction = direction.scl(speed);
            
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
                //damageTick.start(fm);
                
                EnvironmentManager.currentEnv.spawnEntity(new LightningBolt(lightningSprite, pos.cpy()));
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
                //for(Entity e : attTargets){
                    //damageEnemy(e);
                //}
                
                EnvironmentManager.currentEnv.spawnEntity(new LightningBolt(lightningSprite, pos.cpy()));
                
                damageTick.start(fm);
            }

            //move to pos
            moveToCenter();
            
            super.update();
        }
        
        private void moveToCenter(){
            //move this to center pos
            body.applyForce(direction, body.getPosition(), dead);
        }
        
        @Override
        public void alert(String[] str) {
            try {
                if (str[0].equals("begin") && str[1].contains(userdata.toString())) {
                    for (Entity e : EnvironmentManager.currentEnv.getEntities()) {
                        if (e.getUserData() != null
                                && e.getUserData().equals(str[2])) {
                            damageEnemy(e);
                            //addTarget(e);
                        }
                    }
                }
                
            } catch (IndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }
        }
        
        /*
        private void addTarget(Entity e){
            if(!attTargets.contains(e)){
                attTargets.add(e);
            }
        }
        
        private void removeTarget(Entity e) {
            if (attTargets.contains(e)) {
                attTargets.remove(e);
            }
        }*/
        
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