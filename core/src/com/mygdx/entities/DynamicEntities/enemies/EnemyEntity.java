/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.StreamUtils;
import com.mygdx.entities.DynamicEntities.DynamicEntity;
import com.mygdx.entities.ImageSprite;
import com.mygdx.entities.esprites.EntitySprite;
import com.mygdx.entities.text.TextDamage;
import com.mygdx.environments.EnvironmentManager;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import com.mygdx.managers.StateManager;
import com.mygdx.managers.StateManager.State;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.FrameCounter;
import com.mygdx.utilities.FrameCounter_Attack;
import com.mygdx.utilities.SoundObject_Sfx;
import com.mygdx.utilities.UtilityVars.AttackState;
import static com.mygdx.utilities.UtilityVars.BIT_ATT;
import static com.mygdx.utilities.UtilityVars.BIT_EN;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;
import java.io.Reader;

/**
 *
 * @author looch
 */
public class EnemyEntity extends DynamicEntity{
    
    protected BehaviorTree<EnemyEntity> enemybt;
    protected Texture idleTexture, prepTexture;//todo: not needed
    protected ImageSprite moveSprite, attackSprite, prepSprite;
    
    protected StateManager sm = new StateManager();
    
    //combat
    protected float prepTime, attackTime, recovTime;
    protected Object attSensorData;
    protected boolean canAttack = true, canDmgPlayer = false;
    protected float DAMAGE = 0;
    protected FrameCounter_Attack attackFC;
    
    //damage
    protected ImageSprite damageSprite, bodyDamageSprite,spraySprite;
    protected EntitySprite deathSprite;
    protected FrameCounter dmgFC = new FrameCounter(0.4f);
    protected boolean canInterrupt = true;
    
    //falling death
    protected FrameCounter fallFC = new FrameCounter(0.5f);
    
    protected Vector2 attDest = new Vector2(0,0);
    
    //sound
    protected SoundObject_Sfx deathSound;
    
    public FrameCounter_Attack getAttackFC() { return attackFC; }
    public Object getSensorData() { return attSensorData; }
    public boolean getCanAttack() { return canAttack; }
    
    public EnemyEntity(Vector2 pos, float w, float h){
        super(pos,w,h);
        
        userdata = "en_" + id;
        fd.restitution = 0.8f;
        fd.filter.categoryBits = BIT_EN;
        fd.filter.maskBits = BIT_PLAYER | BIT_WALL | BIT_ATT | BIT_EN;
        
        
        attackFC = new FrameCounter_Attack(0,0,0);
        
        spraySprite = new ImageSprite("spray1", false);
        spraySprite.sprite.setScale(6.0f);
        
        deathSprite = new EntitySprite(pos, width,height, "en-death2", false, false, false, false, 1.25f*RATIO, false, false);
        
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
            damageSprite.step(); //todo: move this to update
            
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
    public void alert(String string){
        if(string.equals("en_damage_begin")){
            canDmgPlayer = true;
        }else if(string.equals("en_damage_end")){
            canDmgPlayer = false;
        }
    }
    
}
