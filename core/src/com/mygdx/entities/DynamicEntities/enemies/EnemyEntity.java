/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.enemies;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.Flee;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.DynamicEntities.SteerableEntity;
import com.mygdx.entities.ImageSprite;
import com.mygdx.entities.esprites.EntitySprite;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.managers.StateManager;
import com.mygdx.utilities.FrameCounter;
import com.mygdx.utilities.FrameCounter_Attack;
import com.mygdx.utilities.SoundObject_Sfx;
import com.mygdx.utilities.UtilityVars.AttackState;
import static com.mygdx.utilities.UtilityVars.BIT_ATT;
import static com.mygdx.utilities.UtilityVars.BIT_EN;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import java.util.Stack;

/**
 *
 * @author looch
 */
public class EnemyEntity extends SteerableEntity{
    
    protected ImageSprite moveSprite, attackSprite, prepSprite;
    protected ImageSprite damageSprite, bodyDamageSprite; //,spraySprite;
    protected EntitySprite deathSprite;
    
    protected StateManager sm = new StateManager();
    
    //ai
    protected BehaviorTree<EnemyEntity> bt;
    protected Arrive<Vector2> moveToSB;
    protected Wander<Vector2> wanderSB;
    protected Seek<Vector2> seekWanderSB;
    protected SeekWanderTarget seekWanderTarget;
    protected Flee<Vector2> fleeSB;
    
    //combat
    protected float prepTime, attTime, recovTime;
    protected boolean canAttack = true, canDmgPlayer = false;
    protected float DAMAGE = 0;
    protected float ATTACK_RANGE = 2.0f;
    protected float PLAYER_RANGE = 4f;
    protected FrameCounter_Attack attackFC;
    protected Stack<String> attSensorStack = new Stack<String>();
    protected FrameCounter dmgFC = new FrameCounter(0.4f);
    protected boolean canInterrupt = true;
    protected Vector2 attDest = new Vector2(0,0);
    
    //sound
    protected SoundObject_Sfx deathSound;
    
    public FrameCounter_Attack getAttackFC() { return attackFC; }
    public boolean getCanAttack() { return canAttack; }
    
    public Seek<Vector2> getSeekWanderSb() { return seekWanderSB; }
    
    
    public EnemyEntity(Vector2 pos, float w, float h) {
        super(pos, w, h);
        
        //b2d
        userdata = "en_" + id;
        fd.restitution = 0.8f;
        fd.filter.categoryBits = BIT_EN;
        fd.filter.maskBits = BIT_PLAYER | BIT_WALL | BIT_ATT | BIT_EN;
        sensordata = "en_att_sensor_" + id;  //change to same as userdata
        
        //ai
        this.maxLinearSpeed = 200f;
        this.maxLinearAcceleration = 1000f;
        this.maxAngularSpeed = 30f;
        this.maxAngularAcceleration = 5f;
        
        //combat
        attackFC = new FrameCounter_Attack(0,0,0);
        
    }
    
    @Override
    public void init(World world){
        super.init(world);
        
        seekWanderTarget = new SeekWanderTarget(new Vector2(pos.x, pos.y));
        EnvironmentManager.currentEnv.spawnEntity(seekWanderTarget);
        
        seekWanderSB = new Seek<Vector2>(this, seekWanderTarget);
    }
    
    @Override
    public void update(){
        super.update();
        
        if (active) {
            if (bt != null) {
                bt.step();
            }

            updateAttack();

            updateSprites();
        }
        
    }
    
    @Override
    public void dispose(){
        EnvironmentManager.currentEnv.removeEntity(seekWanderTarget);
        super.dispose();
    }
    
    
    //set behavior to wander steering behavior
    public void wander(){
        behavior = wanderSB;
    }
    
    public void seekWander(){
        behavior = seekWanderSB;
    }
    
    public void flee(){
        behavior = fleeSB;
    }
    
    public void setNewWanderPos(){
        seekWanderTarget.setWanderPos(body.getPosition());
        seekWanderSB.setTarget(seekWanderTarget);
    }
    
    public void removeWanderPos(){
        seekWanderSB.setTarget(null);
    }
    
    private final float WANDER_RANGE = 1.5f;
    public boolean inWanderRange(){
        return seekWanderTarget.getBody().getPosition().dst(body.getPosition()) < WANDER_RANGE;
    }
    
    //set behavior to arrive steering behavior
    public void seek(){
        behavior = moveToSB;
    }
    
    
    public float distance(){
        return pos.dst(EnvironmentManager.player.getPos());
    }
    
    //TODO: use distance() in this method
    public boolean inRangeOfPlayer(){
        if(EnvironmentManager.player.getBody() == null) return false;
        
        float dist = this.getBody().getPosition().
                dst(EnvironmentManager.player.getBody().getPosition());
        
        return dist < PLAYER_RANGE;
    }
    
    
    
    public boolean inAttackRange(){
        if(EnvironmentManager.player.getBody() == null) return false;
        
        float dist = body.getPosition().dst(EnvironmentManager.player.getBody().getPosition());
        return dist < ATTACK_RANGE;
    }
    
    public void updateAttack(){
        if (attackFC.state == AttackState.ATTACKING) {
            if(canAttack){
                attack();
            }
            
            if(canDmgPlayer && !attSensorStack.empty()){
                //damagePlayer();
                canDmgPlayer = false;
            }
        }
        
    }
    
    public void updateSprites(){
        switch(attackFC.state){
            case PREPPING:
                isprite = prepSprite;
                break;
            case ATTACKING:
                isprite = attackSprite;
                attackSprite.sprite.setRotation(body.getAngle()*(float)(180/Math.PI));  
                break;
            default:
                isprite = moveSprite;
                break;
        }   
        
    }
    
    public void prepAttack(){
        if (!attackFC.running && !dmgFC.running) {
            behavior = null;
            attackFC.start(fm);

            canAttack = true;
            canDmgPlayer = true;
        }
        
    }
    
    public void attack(){
        canAttack = false;
    }
    
    public void resetAttack(){
        attackFC.reset();
    }
    
    
    @Override
    public void alert(String[] string) {
        if (active) {
            try {
                if (string[0].equals("begin") && string[1].contains(sensordata.toString())) {
                    attSensorStack.push(string[2]);
                } else if (string[0].equals("end") && string[1].contains(sensordata.toString())) {
                    attSensorStack.pop();
                }
            } catch (IndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private class SeekWanderTarget extends SteerableEntity{

        public SeekWanderTarget(Vector2 pos) {
            super(pos, 5f, 5f);
            
            userdata = "tar_" + id;
            fd.filter.categoryBits = BIT_EN;
            fd.filter.maskBits = BIT_WALL;
        }
        
        public void setWanderPos(Vector2 pos){
            body.setTransform(pos, 0);
        }
        
    }
    
    
}
