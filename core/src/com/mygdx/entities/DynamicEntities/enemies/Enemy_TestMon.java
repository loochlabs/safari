/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import com.badlogic.gdx.ai.steer.behaviors.Flee;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.StreamUtils;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;
import com.mygdx.utilities.UtilityVars.Rarity;
import static com.mygdx.utilities.UtilityVars.Rarity.COMMON;
import java.io.Reader;

/**
 *
 * @author saynt
 */
public class Enemy_TestMon extends EnemyEntity{
    
    private boolean dead = false;
    protected Texture idleTexture, deadTexture;
    protected Rarity rarity = COMMON;
    
    public Texture getIconTexture() { return idleTexture; }
    public Rarity getRarity() { return rarity; }
    
    public Enemy_TestMon(Vector2 pos) {
        super(pos, 40f, 40f);
        
        idleTexture = MainGame.am.get(ResourceManager.ENEMY_PH);
        deadTexture = MainGame.am.get(ResourceManager.ENEMY_PH_DEAD);
        
        texture = idleTexture;
        
        //AI
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
        fleeSB = new Flee<Vector2>(this, EnvironmentManager.player);
        
        this.maxLinearSpeed = 150f;
        this.maxLinearAcceleration = 500f;
        this.maxAngularSpeed = 30f;
        this.maxAngularAcceleration = 5f;
    }
    
    @Override 
    public void init(World world){
        super.init(world);
        EnemyManager.add(this);
        
        //ai
        Reader reader = null;
        try {
            //read this file in resource manager
            reader = Gdx.files.internal("ai/patrol.tree").reader();
            BehaviorTreeParser<EnemyEntity> parser = new BehaviorTreeParser<EnemyEntity>(BehaviorTreeParser.DEBUG_NONE);
            bt = parser.parse(reader,this);
        } finally {
            StreamUtils.closeQuietly(reader);
        }
    }
    
    
    @Override
    public void dispose(){
        EnemyManager.remove(this);
        super.dispose();
    }
    
    @Override
    public void alert(String [] str){
        try{
            System.out.println("@Enemy_TestMon alert");
            if(str[2].contains("bullet_")){
                death();
            }else if(dead && str[2].contains("player_")){
                dispose();
            }
            
        }catch(IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }
    
    private void death(){
        dead = true;
        texture = deadTexture;
        bt = null;
        behavior = null;
    }
    
}
