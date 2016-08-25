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
import com.mygdx.managers.StateManager;
import com.mygdx.screen.GameScreen;
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
        
        //sprites
        deathSprite = new EntitySprite(pos, width*4, height*4, "en-death2", 
                false, true, false, false, 1f*RATIO, false, false,
                true, true);
        
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
    
    
    
    public boolean inRangeOfPlayer(){
        if(GameScreen.player.getBody() == null) return false;
        
        float dist = this.getBody().getPosition().
                dst(GameScreen.player.getBody().getPosition());
        
        return dist < PLAYER_RANGE;
    }
    
    
    
    public boolean inAttackRange(){
        if(GameScreen.player.getBody() == null) return false;
        
        float dist = body.getPosition().dst(GameScreen.player.getBody().getPosition());
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
    
    
    
    
    
    
    /*
    
    
    public EnemyEntity(Vector2 pos, float w, float h){
        super(pos,w,h);
        
        userdata = "en_" + id;
        fd.restitution = 0.8f;
        fd.filter.categoryBits = BIT_EN;
        fd.filter.maskBits = BIT_PLAYER | BIT_WALL | BIT_ATT | BIT_EN;
        
        
        attackFC = new FrameCounter_Attack(0,0,0);
        
        spraySprite = new ImageSprite("spray1", false);
        spraySprite.sprite.setScale(6.0f);
        
        deathSprite = new EntitySprite(pos, width,height, "en-death2", 
                false, true, false, false, 1.25f*RATIO, false, false,
                true, false);
        
        sm.setState(1);
        
        attSensorData = "en_att_sensor_" + id;
        
        deathSound = new SoundObject_Sfx(ResourceManager.SFX_DEATH_1);
    }
    
    @Override
    public void update(){
        super.update();
        
        if(sm.getState() == State.PLAYING){
            
            fm.update();
            
            if(attackFC.running 
                && canAttack
                && attackFC.state == AttackState.ATTACKING){
                
                attack();
            }
        }else if(sm.getState() == State.FALLING){
            
            if(fallFC.running){
                if(isprite != null)
                    isprite.sprite.setScale(isprite.sprite.getScaleX()*0.60f);
            }else if(fallFC.complete){
                death();
            }
            
            
        }
        
        
    }
    
    @Override
    public void render(SpriteBatch sb){
        if(damageSprite != null){
            damageSprite.sprite.draw(sb);
            damageSprite.step();
            
            if(damageSprite.isComplete()){
                damageSprite = null;
            }
        }
        
        super.render(sb);
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
        
        dispose();
    }
    
    public void initBT(){
        //ai
        Reader reader = null;
        try {
            reader = Gdx.files.internal("ai/grunt.tree").reader();
            BehaviorTreeParser<EnemyEntity> parser = new BehaviorTreeParser<EnemyEntity>(BehaviorTreeParser.DEBUG_NONE);
            enemybt = parser.parse(reader,this);
        } finally {
            StreamUtils.closeQuietly(reader);
        }
    }
    
    @Override 
    public void dispose(){
        EnvironmentManager.currentEnv.removeEntity(this);
        
        if(GameScreen.player.getAttTargets().contains(this)){
            GameScreen.player.getAttTargets().remove(this);
        }
    }
    
    @Override
    public void fall(){
        super.fall();
        
        sm.setState(4);
        fallFC.start(fm);
    }
    
    public void patrol(){}
    
    
    //prepping
    public void startAttack(){
        
        if(!attackFC.running && !dmgFC.running){
            
            attackFC.start(fm);
            canAttack = true;
        
            attDest = body.getPosition().cpy().sub(
                GameScreen.player.getBody().getPosition().cpy());
            
            isprite = prepSprite;
        }
    }
    
    public void attack(){
        canAttack = false;
        
        if(canDmgPlayer){
            GameScreen.player.damage(DAMAGE);
            //canDmgPlayer = false;
        }
    }
    
    
    //@param: movement to destination, (catch up)
    public void moveTo(Vector2 dest, float speed){
        
        Vector2 dirv = this.getBody().getPosition().sub(dest);
        dirv = dirv.nor();
        this.getBody().applyForce(dirv.scl(-speed), dest, true);
        
    }
    
    protected float PLAYER_IDLE_RANGE = 5.5f;
    
    public boolean inIdleRange(){
        float dist = this.getBody().getPosition().
                dst(GameScreen.player.getBody().getPosition());
        return dist < PLAYER_IDLE_RANGE;
    }
    
    protected float PLAYER_ATTACK_RANGE = 2.0f;
    
    public boolean inAttackRange(){
        float dist = this.getBody().getPosition().
                dst(GameScreen.player.getBody().getPosition());
        return dist < PLAYER_ATTACK_RANGE;
    }
    
    @Override
    public void alert(String [] string){
        try {
            if (string[0].equals("begin")) {
                canDmgPlayer = true;
            } else if (string[0].equals("end")) {
                canDmgPlayer = false;
            }
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }
    

*/
}
