/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;
import com.mygdx.utilities.FrameCounter_Attack;
import com.mygdx.utilities.UtilityVars.AttackState;
import java.util.Random;

/**
 *
 * @author looch
 */
public class En_Pollop extends EnemyEntity{

    private final Texture openTexture, closedTexture;
    private boolean vulnerable;
    private final Random rng = new Random();
    private final int[] rngSet = {-1,1};
    
    public boolean getVulnerable() { return vulnerable; }
    
    public En_Pollop(Vector2 pos, float w, float h) {
        super(pos, w, h);
        
        
        closedTexture = MainGame.am.get(ResourceManager.POLLOP_CLOSED);
        openTexture = MainGame.am.get(ResourceManager.POLLOP_OPEN);
        texture = closedTexture;
        
        MAX_HP = 25;
        CURRENT_HP = MAX_HP;
        //CHARSPEED = 200.0f * MainGame.RATIO;
        DAMAGE = 5.0f;
        
        fd.restitution = 0.4f;
        fd.friction = 0.0f;
        
        recovTime = 10;
        attackTime = 4;
        
        attackFC = new FrameCounter_Attack(0,attackTime, recovTime);
        
        vulnerable = false;
    }
    
    @Override
    public void init(World world){
        super.init(world);
        
        attackFC.start(fm);
        
        body.setLinearDamping(0.0f);
        //body.applyForce(new Vector2(CHARSPEED, CHARSPEED), body.getPosition(), true);
        
    }
    
    @Override
    public void update(){
        super.update();
        
        if(attackFC.complete){
            attackFC.start(fm);
        }
        if(attackFC.state == AttackState.ATTACKING
                && vulnerable){
            texture = closedTexture;
            //body.applyForce(new Vector2(CHARSPEED * rngSet[rng.nextInt(2)], CHARSPEED * rngSet[rng.nextInt(2)]), 
                    //body.getPosition(), true);
            vulnerable = false;
        }else if(attackFC.state != AttackState.ATTACKING){
            texture = openTexture;
            vulnerable = true;
        }
    }
    
    @Override
    public void damage(float d){
        if(attackFC.state != AttackState.ATTACKING)
            super.damage(d);
    }
    
    
}
