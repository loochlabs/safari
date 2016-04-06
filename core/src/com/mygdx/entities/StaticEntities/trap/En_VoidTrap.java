/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.StaticEntities.trap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.mygdx.entities.DynamicEntities.SteerableEntity;
import com.mygdx.entities.ImageSprite;
import com.mygdx.environments.EnvVoid.EnvVoid;
import com.mygdx.environments.EnvironmentManager;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.StateManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.FrameCounter;
import com.mygdx.utilities.FrameCounter_Attack;
import com.mygdx.utilities.UtilityVars.AttackState;
import static com.mygdx.utilities.UtilityVars.BIT_EN;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author saynt
 */
public class En_VoidTrap extends SteerableEntity{
    
    
    
    private ImageSprite idleSprite, prepSprite, armSprite, armRevSprite, spawnSprite;
    
    //attack
    private FrameCounter_Attack attackFC;
    private final FrameCounter damageFC = new FrameCounter(1f);
    private final float DAMAGE = 1f;
    private boolean canAttack = false;
    private boolean recovAttack = false;
    private final float PLAYER_RANGE = 3.5f;
    private RevoluteJointDef jointDef;
    private RevoluteJoint j; 
    
    //reproduce
    private final FrameCounter reproduceFC;
    private final float REPRODUCE_RANGE = 15f;
    public final int SPAWN_MAX = 10;
    public int spawnCount;      //cap the number of times this can reproduce
    
    public En_VoidTrap(Vector2 pos){
        this(pos, 0);
    }
    
    public En_VoidTrap(Vector2 pos, int spawnCount) {
        super(pos, 50f*RATIO, 50f*RATIO);
        
        
        //b2d
        bd.type = BodyDef.BodyType.StaticBody;
        bd.position.set(pos.x/PPM,pos.y/PPM);
        cshape.setRadius(0.5f * width/PPM);
        userdata = "en_" + id;
        fd.restitution = 0.8f;
        fd.shape = cshape;
        fd.filter.categoryBits = BIT_EN;
        fd.filter.maskBits = BIT_WALL;
        sensordata = "en_att_sensor_" + id;  //change to same as userdata
        
        jointDef = new RevoluteJointDef();
        
        //reproduce
        reproduceFC = new FrameCounter(REPRODUCE_RANGE + REPRODUCE_RANGE*rng.nextFloat());
        
        this.spawnCount = spawnCount;
        
        //attack
        attackFC = new FrameCounter_Attack(1.5f,5,5);
        attackFC.reset();
        
        idleSprite = new ImageSprite("void-trap-idle", true);
        idleSprite.sprite.setBounds(pos.x, pos.y, width, height*1.3f);
        
        prepSprite = new ImageSprite("void-trap-prep", true);
        prepSprite.sprite.setBounds(pos.x, pos.y, width, height*1.45f);
        
        armSprite = new ImageSprite("void-trap-arm", false);
        armRevSprite = new ImageSprite("void-trap-arm", false, true, 
            false, false, 0, 0, 1.0f, true);
        
        spawnSprite = new ImageSprite("void-trap-spawn", false, true, false, false, 0,0,1,false);
        spawnSprite.sprite.setBounds(pos.x, pos.y, width, height*1.45f);
        
        isprite = spawnSprite;
        
        active = false;
    }
    
    @Override
    public void init(World world){
        super.init(world);
        
        reproduceFC.start(fm);
    }
    
    @Override
    public void update(){
        super.update();

        
        if (spawnSprite.isComplete() && !active) {
            active = true;
            isprite = idleSprite;
        }

        if (active) {
            inRangeOfPlayer();

            if (attackFC.state == AttackState.ATTACKING) {
                armSprite.step();
            } else if (attackFC.state == AttackState.RECOVERING) {
                armRevSprite.step();
            }

            if (reproduceFC.complete) {
                this.reproduce();
            }
        }
        
        
    }
    
    @Override
    public void render(SpriteBatch sb){
        
        //render void arm
        if(attackFC.state == AttackState.ATTACKING){
            //render armSprite
            
            armSprite.sprite.draw(sb);
        }
        else if(!armRevSprite.isComplete() && attackFC.state == AttackState.RECOVERING){
            //render armSprite
            
            armRevSprite.sprite.draw(sb);
        }
        
        super.render(sb);
    }
    
    public void inRangeOfPlayer(){
        //if true - prep
        //if false - cancel prep (if prep running)
        if(GameScreen.player.getBody() == null) return;
        
        float dist = body.getPosition().dst(GameScreen.player.getBody().getPosition());
        if(dist < PLAYER_RANGE){
            
            if(attackFC.state == AttackState.ATTACKING){
                if(canAttack){
                    attack();
                }else if(damageFC.complete){
                    damagePlayer();
                }
            }else if(recovAttack && attackFC.state == AttackState.RECOVERING){
                endAttack();
            }else if(!attackFC.running){
                prepAttack();
            }
            
        }else{
            if(attackFC.state == AttackState.PREPPING){
                reset();
            }
        }
    }
    
    public void reset(){
        isprite = idleSprite;
        attackFC.stop();
        attackFC.reset();
        recovAttack = false;
        canAttack = false;
    }
    
    public void prepAttack(){
        //prep attack FC
        
        canAttack = true;
        attackFC.start(fm);
        isprite = prepSprite;
    }
    public void attack(){
        //create joint
        //attack FC
        //damage player
        
        canAttack = false;
        recovAttack = true;
        
        damageFC.start(fm);
        
        jointDef.initialize(body, GameScreen.player.getBody(), body.getPosition());
        j = (RevoluteJoint)EnvironmentManager.currentEnv.getWorld().createJoint(jointDef);
        
        
        isprite = idleSprite;
        
        armSprite.sprite.setOrigin(0, 0);
        armSprite.sprite.setBounds(pos.x, pos.y, 15f*RATIO, pos.dst(GameScreen.player.getPos()));
        Vector2 cd = GameScreen.player.getPos().cpy().sub(pos).nor();
        armSprite.sprite.setRotation(cd.angle() - 90);
        armSprite.reset();
        
        armRevSprite.sprite.setOrigin(0, 0);
        armRevSprite.sprite.setBounds(pos.x, pos.y, 15f*RATIO, pos.dst(GameScreen.player.getPos()));
        armRevSprite.sprite.setRotation(cd.angle() - 90);
        armRevSprite.reset();
    }
    
    public void endAttack() throws NullPointerException {
        //destroy joint
        recovAttack = false;
        if (j != null) {
            EnvironmentManager.currentEnv.getWorld().destroyJoint(j);
        }
    }
    
    public void damagePlayer(){
         damageFC.start(fm);
         GameScreen.player.damage(DAMAGE);
         
         if(GameScreen.player.isDead()){
             reset();
         }
    }
    
    
    //spawn new copy of this on reproduceTimer
    public void reproduce(){
        reproduceFC.reset();
        
        //cap on spawn ammount
        if(spawnCount >= SPAWN_MAX) return;
        
        
        int reproduceCount = rng.nextInt(2) + 1;

        for (int i = 0; i < reproduceCount; i++) {
            
            EnvVoid env = (EnvVoid) EnvironmentManager.currentEnv;
            Vector2 loc = env.createRandomLocation();
            int count = 0;
            
            while (loc.dst(pos) > 200f) {
                loc = env.createRandomLocation();

                count++;

                if (count > 50) {
                    if( i > 0){
                        reproduceFC.start(fm);
                    }
                    break;
                }
                
            }
             if (count > 50) {
                break;
            }

           
            env.spawnEntity(new En_VoidTrap(loc, spawnCount + reproduceCount));
        }
    }
    
}
