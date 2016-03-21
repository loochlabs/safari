/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.StreamUtils;
import com.mygdx.entities.ImageSprite;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.FrameCounter_Attack;
import com.mygdx.utilities.UtilityVars;
import static com.mygdx.utilities.UtilityVars.BIT_EN;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.PPM;
import java.io.Reader;

/**
 *
 * @author looch
 */
public class En_Worm extends EnemyEntity2{

    private FixtureDef attFd1;
    
    public En_Worm(Vector2 pos) {
        super(pos, 20f, 20f);
        
        moveSprite = new ImageSprite("worm-move", true);
        prepSprite = new ImageSprite("worm-prep", false);
        attackSprite = new ImageSprite("worm-att", true);
        
        this.maxLinearSpeed = 50f;
        this.maxLinearAcceleration = 500f;
        this.maxAngularSpeed = 3000f;
        this.maxAngularAcceleration = 500f;
        
        bd.linearDamping = 1.0f;
        fd.restitution = 0.05f;
        
        MAX_HP = 25;
        CURRENT_HP = MAX_HP;
        DAMAGE = 15;
        
        prepTime = 1.0f;
        attTime = 0.75f;
        recovTime = 2.5f;
        attackFC = new FrameCounter_Attack(prepTime, attTime, recovTime);
        
        ATTACK_RANGE = 3.5f;
        PLAYER_RANGE = 5.0f;
        
        moveToSB = new Arrive<Vector2>(this, GameScreen.player)
                .setTimeToTarget(0.01f)
                .setArrivalTolerance(2f)
                .setDecelerationRadius(10);
        
        wanderSB = new Wander<Vector2>(this)
                .setFaceEnabled(false)
                .setAlignTolerance(0.001f)
                .setDecelerationRadius(5)
                .setTimeToTarget(0.1f)
                .setWanderOffset(90)
                .setWanderOrientation(10)
                .setWanderRadius(40f)
                .setWanderRate(MathUtils.PI2 * 4);
                
        seekWanderSB = new Seek<Vector2>(this, seekWanderTarget);
        
        attFd1 = new FixtureDef();
        PolygonShape vertBox = new PolygonShape();
        vertBox.setAsBox(width*0.4f/PPM, (20f/PPM)*RATIO, new Vector2(0,0), 0);
        attFd1.shape = vertBox;
        attFd1.isSensor = true;
        attFd1.filter.categoryBits = BIT_EN;
        attFd1.filter.maskBits = BIT_PLAYER;
        
    }
    
    @Override
    public void init(World world){
        super.init(world);
        
        //att sensors
        body.createFixture(attFd1).setUserData(attSensorData);
        
        //ai
        Reader reader = null;
        try {
            reader = Gdx.files.internal("ai/enemies/en_goober2.tree").reader();
            BehaviorTreeParser<EnemyEntity2> parser = new BehaviorTreeParser<EnemyEntity2>(BehaviorTreeParser.DEBUG_NONE);
            enemybt = parser.parse(reader,this);
        } finally {
            StreamUtils.closeQuietly(reader);
        }
    }
    
    private Vector2 attDest;
    private float prepRotation = 0;
    
    @Override
    public void prepAttack(){
        attDest = body.getPosition().cpy().sub(
                GameScreen.player.getBody().getPosition().cpy());
        
        prepSprite.reset();
        prepRotation = attDest.angle() + 90;//body.getAngle()*(float)(180/Math.PI);
        
        super.prepAttack();
        
    }
    
    @Override
    public void attack(){
        body.applyForce(attDest.scl(-1500.0f), body.getPosition(), true);
        canAttack = false;
    }
    
    @Override
    public void updateAttack(){
        if (attackFC.state == UtilityVars.AttackState.ATTACKING) {
            if(canAttack){
                attack();
            }
            
            if(canDmgPlayer && !attSensorStack.empty()){
                damagePlayer();
                canDmgPlayer = false;
            }
        }
    }
    
    @Override
    public void updateSprites(){
        if (attackFC.state == UtilityVars.AttackState.PREPPING) {
            isprite = prepSprite;
            prepSprite.sprite.setRotation(prepRotation);
        } else if (attackFC.state == UtilityVars.AttackState.ATTACKING) {
            isprite = attackSprite;
            attackSprite.sprite.setRotation(prepRotation);
        } else {
            isprite = moveSprite;
        }
    }
    
    @Override
    public void alert(String str){
        super.alert(str);
        canDmgPlayer = false;
    }
    
}
