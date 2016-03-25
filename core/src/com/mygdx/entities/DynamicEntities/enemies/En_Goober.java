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
import com.mygdx.utilities.UtilityVars.AttackState;
import static com.mygdx.utilities.UtilityVars.BIT_EN;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.PPM;
import java.io.Reader;

/**
 *
 * @author looch
 */
public class En_Goober extends EnemyEntity{
    
    private FixtureDef attFd1, attFd2, attFd3, attFd4;
    
    //sound
    //private SoundObject_Sfx SFX_MOVE;
    
    public En_Goober(Vector2 pos){
        super(pos,30f*RATIO,30f*RATIO);
        fd.restitution = 0.2f;
        
        
        float sscale = 0.38f *RATIO;
        
        moveSprite = new ImageSprite("goober_move",true);
        moveSprite.sprite.setScale(sscale);
        isprite = moveSprite;
        
        prepSprite = new ImageSprite("goober_prep",true);
        prepSprite.sprite.setScale(sscale);
        
        attackSprite = new ImageSprite("goober-attack2",false);
        attackSprite.sprite.setScale(sscale);
        
        bodyDamageSprite = new ImageSprite("goober-dmg", false);
        bodyDamageSprite.sprite.setScale(sscale);
        
        MAX_HP = 30;
        CURRENT_HP = MAX_HP;
        DAMAGE = 15.0f;
        
        prepTime = 1.0f;
        attTime = 0.3f;
        recovTime = 3f;
        
        attackFC = new FrameCounter_Attack(prepTime, attTime, recovTime);
        
        
        //attack sensors
        attFd1 = new FixtureDef();
        PolygonShape vertBox = new PolygonShape();
        vertBox.setAsBox(width*0.4f/PPM, (75f/PPM)*RATIO, new Vector2(0,(75f/PPM)*RATIO), 0);
        attFd1.shape = vertBox;
        attFd1.isSensor = true;
        attFd1.filter.categoryBits = BIT_EN;
        attFd1.filter.maskBits = BIT_PLAYER;
        
        
        attFd2 = new FixtureDef();
        PolygonShape horzBox = new PolygonShape();
        horzBox.setAsBox((75f/PPM)*RATIO, width*0.4f/PPM, new Vector2((75f/PPM)*RATIO,0), 0);
        attFd2.shape = horzBox;
        attFd2.isSensor = true;
        attFd2.filter.categoryBits = BIT_EN;
        attFd2.filter.maskBits = BIT_PLAYER;
        
        attFd3 = new FixtureDef();
        PolygonShape vertBoxSouth = new PolygonShape();
        vertBoxSouth.setAsBox(width*0.4f/PPM, (75f/PPM)*RATIO, new Vector2(0,-(75f/PPM)*RATIO), 0);
        attFd3.shape = vertBoxSouth;
        attFd3.isSensor = true;
        attFd3.filter.categoryBits = BIT_EN;
        attFd3.filter.maskBits = BIT_PLAYER;
        
        attFd4 = new FixtureDef();
        PolygonShape horzBoxWest = new PolygonShape();
        horzBoxWest.setAsBox((75f/PPM)*RATIO, width*0.4f/PPM, new Vector2(-(75f/PPM)*RATIO,0), 0);
        attFd4.shape = horzBoxWest;
        attFd4.isSensor = true;
        attFd4.filter.categoryBits = BIT_EN;
        attFd4.filter.maskBits = BIT_PLAYER;
        
        
        //ai
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
        
        //ai
        this.maxLinearSpeed = 150f;
        this.maxLinearAcceleration = 500f;
        this.maxAngularSpeed = 30f;
        this.maxAngularAcceleration = 5f;
        
    }
    
    @Override
    public void init(World world){
        super.init(world);
        
        body.createFixture(attFd1).setUserData(sensordata);
        body.createFixture(attFd2).setUserData(sensordata);
        body.createFixture(attFd3).setUserData(sensordata);
        body.createFixture(attFd4).setUserData(sensordata);
        
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
    
    @Override
    public void attack() {
        body.applyForce(attDest.scl(-400.0f), body.getPosition(), true);
        super.attack();
    }

    @Override
    public void prepAttack() {
        super.prepAttack();
        attDest = body.getPosition().cpy().sub(
                GameScreen.player.getBody().getPosition().cpy());
    }

}
