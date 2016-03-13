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
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.StreamUtils;
import com.mygdx.entities.esprites.EntitySprite;
import com.mygdx.screen.GameScreen;
import java.io.Reader;

/**
 *
 * @author looch
 */
public class En_Goober2 extends EnemyEntity2{

    
    public En_Goober2(Vector2 pos) {
        super(pos, 30f, 30f);
        
        moveSprite = new EntitySprite("goober_move",true);
        moveSprite.sprite.setScale(0.38f);
        esprite = moveSprite;
        
        this.maxLinearSpeed = 50f;
        this.maxLinearAcceleration = 500f;
        this.maxAngularSpeed = 3000f;
        this.maxAngularAcceleration = 500f;
        
        
        
        moveToSB = new Arrive<Vector2>(this, GameScreen.player)
                .setTimeToTarget(0.01f)
                .setArrivalTolerance(2f)
                .setDecelerationRadius(10);
        
        //this.setBehavior(moveToSB);
        
        
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
        
        //this.setBehavior(wanderSB);
        
    }
    
    @Override
    public void init(World world){
        super.init(world);
        
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
    
    
    
    
}
