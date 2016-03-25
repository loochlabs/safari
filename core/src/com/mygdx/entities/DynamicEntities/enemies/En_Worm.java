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
import static com.mygdx.utilities.UtilityVars.BIT_EN;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.PPM;
import java.io.Reader;

/**
 *
 * @author looch
 */
public class En_Worm extends EnemyEntity{

    private FixtureDef attFd1;
    
    public En_Worm(Vector2 pos) {
        super(pos, 20f*RATIO, 20f*RATIO);
        
        moveSprite = new ImageSprite("worm-move", true);
        prepSprite = new ImageSprite("worm-prep", false);
        attackSprite = new ImageSprite("worm-att", true);
        
        
        //combat
        MAX_HP = 25;
        CURRENT_HP = MAX_HP;
        DAMAGE = 15;
        
        prepTime = 1.0f;
        attTime = 0.75f;
        recovTime = 2.5f;
        attackFC = new FrameCounter_Attack(prepTime, attTime, recovTime);
        
        ATTACK_RANGE = 3.5f;
        PLAYER_RANGE = 5.0f;
        
        //ai
        this.maxLinearSpeed = 50f;
        this.maxLinearAcceleration = 500f;
        this.maxAngularSpeed = 3000f;
        this.maxAngularAcceleration = 500f;
        
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
        
        
        //b2d
        attFd1 = new FixtureDef();
        PolygonShape vertBox = new PolygonShape();
        vertBox.setAsBox(width*0.4f/PPM, (20f/PPM)*RATIO, new Vector2(0,0), 0);
        attFd1.shape = vertBox;
        attFd1.isSensor = true;
        attFd1.filter.categoryBits = BIT_EN;
        attFd1.filter.maskBits = BIT_PLAYER;
        bd.linearDamping = 1.0f;
        fd.restitution = 0.05f;
        bd.bullet = true;
        
    }
    
    @Override
    public void init(World world){
        super.init(world);
        
        //att sensors
        body.createFixture(attFd1).setUserData(sensordata);
        
        //ai
        Reader reader = null;
        try {
            //read this file in resource manager
            reader = Gdx.files.internal("ai/enemies/en_goober2.tree").reader();
            BehaviorTreeParser<EnemyEntity> parser = new BehaviorTreeParser<EnemyEntity>(BehaviorTreeParser.DEBUG_NONE);
            bt = parser.parse(reader,this);
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
        prepRotation = attDest.angle() + 90;
        
        super.prepAttack();
        
    }
    
    @Override
    public void attack(){
        body.applyForce(attDest.scl(-1500.0f), body.getPosition(), true);
        super.attack();
    }
    
    
    @Override
    public void updateSprites(){
        
        switch(attackFC.state){
            case PREPPING:
                isprite = prepSprite;
                prepSprite.sprite.setRotation(prepRotation);
                break;
            case ATTACKING:
                isprite = attackSprite;
                attackSprite.sprite.setRotation(prepRotation);
                break;
            default:
                isprite = moveSprite;
                break;
        }
    }
    
    
}
