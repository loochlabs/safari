/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import static com.mygdx.combat.skills.Skill.SkillAttribute.SPEED;
import static com.mygdx.combat.skills.Skill.SkillType.LIGHT;
import static com.mygdx.combat.skills.Skill.SkillType.SPECIAL;
import com.mygdx.entities.Entity;
import com.mygdx.entities.ImageSprite;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.gui.descriptions.DescriptionWindow;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.UtilityVars;
import static com.mygdx.utilities.UtilityVars.BIT_ATT;
import static com.mygdx.utilities.UtilityVars.BIT_EN;
import static com.mygdx.utilities.UtilityVars.BIT_MISC;
import static com.mygdx.utilities.UtilityVars.BIT_TEAR;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author saynt
 *
 */
public class Skill_HasteLaysWaste extends SpecialSkill{
    
    private final int STAT_BUFF = 1;
        
    public Skill_HasteLaysWaste(){
        name = "Haste Lays Waste";
        attribute = SPEED;
        damageMod = 1.0f;
        desc = "The need for speed";
        comboChain = new SkillType[] { SPECIAL, LIGHT, LIGHT };
        descWindow = new DescriptionWindow(name, desc, comboChain);
        
        skillIcon = MainGame.am.get(ResourceManager.SKILL_MUCHHASTE);
    }
        
    
    /******************
        EFFECT
    *******************/
        
    @Override
    public void effect(){
        //creat 3 balls that circle player
        //add Speed Stat for each ball
        //remove stat on ball destroy
        EnvironmentManager.currentEnv.spawnEntity(new Entity_HasteManager(GameScreen.player.getPos(), 0));
        EnvironmentManager.currentEnv.spawnEntity(new Entity_HasteManager(GameScreen.player.getPos(), 120f * (float)(Math.PI/180)));
        EnvironmentManager.currentEnv.spawnEntity(new Entity_HasteManager(GameScreen.player.getPos(), 240f * (float)(Math.PI/180)));
        
        reset();
    }
    
    @Override
    public float damageEnemy(Entity e){
        e.damage(GameScreen.player.getCurrentDamage() * GameScreen.player.getLightMod() * damageMod);
        return GameScreen.player.getCurrentDamage() * GameScreen.player.getLightMod() * damageMod;
    }
    
    private class Entity_HasteManager extends Entity {

        FixtureDef ballfd1 = new FixtureDef();
        private RevoluteJointDef jointDef;
        private RevoluteJoint j;
        
        private ImageSprite bulletSprite;

        public Entity_HasteManager(Vector2 pos, float angle_rad) {
            super(pos, 25f * RATIO, 25f * RATIO);

            userdata = "bullet_" + id;
            bd.position.set(pos.x / PPM, pos.y / PPM);
            bd.type = BodyDef.BodyType.DynamicBody;
            bd.linearDamping = 0.0f;
            cshape.setRadius(width / PPM);
            fd.shape = cshape;
            fd.filter.categoryBits = BIT_ATT;
            fd.filter.maskBits = BIT_MISC;
            fd.isSensor = true;
            fd.density = 0.1f;
            bd.angle = angle_rad;
            
            //create 3 circling balls
            CircleShape bs1 = new CircleShape();
            bs1.setRadius(width/PPM);
            bs1.setPosition(new Vector2(0, 1.4f*RATIO));
            ballfd1.shape = bs1;
            ballfd1.isSensor = true;
            ballfd1.filter.categoryBits = BIT_ATT;
            ballfd1.filter.maskBits = BIT_EN | BIT_TEAR;
            
            jointDef = new RevoluteJointDef();
            
            bulletSprite = new ImageSprite("haste-ball", true);
            bulletSprite.sprite.setBounds(pos.x, pos.y, width*2, 160f*RATIO);
            //bulletSprite.sprite.setCenter(0, 140f*RATIO);
            bulletSprite.sprite.setOrigin(width, 0);
            bulletSprite.sprite.setRotation(UtilityVars.radiansToDegrees(angle_rad));
            
            isprite = bulletSprite;
        }

        @Override
        public void init(World world) {
            try {
                body = world.createBody(bd);
                body.createFixture(fd).setUserData(userdata);
                body.setUserData(userdata);
                body.createFixture(ballfd1).setUserData(userdata);
                body.resetMassData();   //needed for setting density
                
                body.applyTorque(5.5f/PPM, true);
                
                //adjust stats
                GameScreen.player.addStatPoints(0, 0, 0, STAT_BUFF, 0);
                
                
                //create joint between player and main body
                jointDef.initialize(body, GameScreen.player.getBody(), body.getPosition());
                j = (RevoluteJoint)EnvironmentManager.currentEnv.getWorld().createJoint(jointDef);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        @Override
        public void update(){
            super.update();
            
            isprite.sprite.setRotation(UtilityVars.radiansToDegrees(body.getAngle()) );
            
            if(GameScreen.player.getBody() == null){
                death();
            }
        }
        
        @Override
        public void render(SpriteBatch sb) {
            if (isprite != null) {
                //todo: needed? xflip, yflip covered in ImageSprite
                if (isprite.getXFlip()) {
                    isprite.sprite.setPosition((pos.x + isprite.sprite.getWidth() / 2),
                            (pos.y ));
                } else {
                    isprite.sprite.setPosition((pos.x - isprite.sprite.getWidth() / 2),
                            (pos.y ));
                }
                isprite.render(sb);
            } else if (texture != null) {
                sb.draw(texture, pos.x - width, pos.y , iw, ih);
            }

            renderEffects(sb);
        }
        
        @Override
        public void damage(float d){}
        
        @Override
        public void alert(String[] str) {
            try {
                if (str[0].equals("begin") && str[1].contains(userdata.toString())) {
                    for (Entity e : EnvironmentManager.currentEnv.getEntities()) {
                        if (e.getUserData() != null
                                && e.getUserData().equals(str[2])) {

                            damageEnemy(e);
                            death();
                        }
                    }
                }
            } catch (IndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void death() {

            //adjust stats
            GameScreen.player.addStatPoints(0, 0, 0, -STAT_BUFF, 0);

            
            dispose();
        }

    }
    
}
