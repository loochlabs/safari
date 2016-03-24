/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.enemies;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.DynamicEntities.SteerableEntity;
import com.mygdx.entities.ImageSprite;
import com.mygdx.entities.esprites.EntitySprite;
import com.mygdx.environments.EnvironmentManager;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.FrameCounter_Attack;
import com.mygdx.utilities.SoundObject_Sfx;
import com.mygdx.utilities.UtilityVars.AttackState;
import static com.mygdx.utilities.UtilityVars.BIT_ATT;
import static com.mygdx.utilities.UtilityVars.BIT_EN;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;
import java.util.Stack;

/**
 *
 * @author looch
 */
public class EnemyEntity2 extends SteerableEntity{

    protected ImageSprite 
            moveSprite, prepSprite, attackSprite;
    
    protected EntitySprite deathSprite;
    
    protected FrameCounter_Attack attackFC;
    protected float DAMAGE = 0;
    
    //ai
    protected BehaviorTree<EnemyEntity2> enemybt;
    protected Arrive<Vector2> moveToSB;
    protected Wander<Vector2> wanderSB;
    protected Seek<Vector2> seekWanderSB;
    
    //protected Vector2 wanderPos; 
    protected SeekWanderTarget seekWanderTarget;
    
    
    //attack fields
    protected float prepTime, attTime, recovTime;
    protected boolean canAttack = true, canDmgPlayer = false;
    protected Stack<String> attSensorStack = new Stack<String>();
    protected Object attSensorData;
    
    //sound
    protected SoundObject_Sfx deathSound;
    
    public FrameCounter_Attack getAttackFC() { return attackFC; } 
    public Seek<Vector2> getSeekWanderSb() { return seekWanderSB; }
    public Object getSensorData() { return attSensorData; }
    
    public EnemyEntity2(Vector2 pos, float w, float h) {
        super(pos, w, h);
        
        this.maxLinearSpeed = 500f;
        this.maxLinearAcceleration = 5000f;
        this.maxAngularSpeed = 30f;
        this.maxAngularAcceleration = 5f;
        
        userdata = "en_" + id;
        fd.restitution = 0.8f;
        fd.filter.categoryBits = BIT_EN;
        fd.filter.maskBits = BIT_PLAYER | BIT_WALL | BIT_ATT | BIT_EN;
        
        attackFC = new FrameCounter_Attack(0,0,0);
        
        attSensorData = "en_att_sensor_" + id;
        
        deathSprite = new EntitySprite(pos, width, height, "en-death2", 
                false, false, false, false, 1.25f*RATIO, false, false,
                true, true);
        
        deathSound = new SoundObject_Sfx(ResourceManager.SFX_DEATH_1);
        
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
        
        fm.update();
        
        if(enemybt != null){        
            enemybt.step();
        }
        
        updateAttack();
        
        updateSprites();
        
        
    }
    
    @Override
    public void death(){
        super.death();
        
        deathSound.play(false);
        
        EnvironmentManager.currentEnv.addKillCount();
        System.out.println("@EnemyEntity en death");
        
        deathSprite.setPosition(new Vector2(
                pos.x - deathSprite.getWidth()/2, 
                pos.y - deathSprite.getHeight()/2));
        EnvironmentManager.currentEnv.spawnEntity(deathSprite);
        
        EnvironmentManager.currentEnv.removeEntity(seekWanderTarget);
        
        dispose();
    }
    
    @Override 
    public void dispose(){
        EnvironmentManager.currentEnv.removeEntity(this);
        
        if(GameScreen.player.getAttTargets().contains(this)){
            GameScreen.player.getAttTargets().remove(this);
        }
    }
    
    
    //set behavior to wander steering behavior
    public void wander(){
        behavior = wanderSB;
    }
    
    public void seekWander(){
        behavior = seekWanderSB;
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
    
    protected float PLAYER_RANGE = 4f;
    
    public boolean inRangeOfPlayer(){
        float dist = this.getBody().getPosition().
                dst(GameScreen.player.getBody().getPosition());
        
        return dist < PLAYER_RANGE;
    }
    
    protected float ATTACK_RANGE = 2.0f;
    
    public boolean inAttackRange(){
        float dist = body.getPosition().dst(GameScreen.player.getBody().getPosition());
        return dist < ATTACK_RANGE;
    }
    
    public void updateAttack(){
        if (attackFC.state == AttackState.ATTACKING) {
            if(canAttack){
                attack();
            }
            
            if(canDmgPlayer && !attSensorStack.empty()){
                damagePlayer();
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
        
        /*
        //new edit: 2/10/16
        if (attackFC.state == AttackState.PREPPING) {
            esprite = prepSprite;
        } else if (attackFC.state == AttackState.ATTACKING) {
            esprite = attackSprite;
            attackSprite.sprite.setRotation(body.getAngle()*(float)(180/Math.PI));
        } else {
            esprite = moveSprite;
        }*/
    }
    
    public void prepAttack(){
        if (!attackFC.running) {
            behavior = null;
            attackFC.start(fm);

            canAttack = true;
            canDmgPlayer = true;
            
            System.out.println("@En2 prepAttack");
        }
        
    }
    
    public void attack(){
        canAttack = false;
    }
    
    public void resetAttack(){
        attackFC.reset();
    }
    
    public void damagePlayer(){
        GameScreen.player.damage(DAMAGE);
    }
    
    @Override
    public void alert(String []string){
        try {
            if (string[0].equals("begin")) {
                attSensorStack.push(string[2]);
            } else if (string[0].equals("end")) {
                attSensorStack.pop();
            }
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }
    
    private class SeekWanderTarget extends SteerableEntity{

        public SeekWanderTarget(Vector2 pos) {
            super(pos, 10f, 10f);
            
            userdata = "tar_" + id;
            fd.filter.categoryBits = BIT_EN;
            fd.filter.maskBits = BIT_WALL;
        }
        
        public void setWanderPos(Vector2 pos){
            body.setTransform(pos, 0);
        }
        
    }
}
