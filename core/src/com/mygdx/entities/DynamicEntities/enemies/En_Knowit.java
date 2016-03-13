/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.esprites.EntitySprite;
import com.mygdx.entities.projectiles.EnemyProj;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.FrameCounter_Attack;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class En_Knowit extends EnemyEntity{

    public En_Knowit(Vector2 pos) {
        super(pos, 40f, 30f);
        
        MAX_HP = 30f;
        CURRENT_HP = MAX_HP;
        DAMAGE = 10f;
        CHARSPEED = 20f; 
        
        prepTime = 0.5f;
        attackTime = 0.3f;
        recovTime = 3f;
        attackFC = new FrameCounter_Attack(prepTime, attackTime, recovTime);
        
        moveSprite = new EntitySprite("knowit-move", true);
        prepSprite = new EntitySprite("knowit-prep", true);
        esprite = moveSprite;
        
        
        PLAYER_IDLE_RANGE = 6.0f;
        PLAYER_ATTACK_RANGE = 4.0f;
        
        canInterrupt = false;
    }
    
    @Override
    public void init(World world){
        super.init(world);
        initBT();
    }
    
    @Override
    public void update(){
        super.update();
        enemybt.step();
    }
    
    @Override
    public void startAttack(){
        if(!attackFC.running && !dmgFC.running){
            Vector2 dv = new Vector2(
                    5f * rng.nextFloat() * rngNegSet.random(), 
                    5f * rng.nextFloat() * rngNegSet.random());
            
            
            attDest = body.getPosition().cpy().sub(
                GameScreen.player.getBody().getPosition().cpy().add(dv));
            
            body.applyForce(attDest.scl(-300.0f), body.getPosition(), true);
        }
        
        super.startAttack();
    }
    
    @Override
    public void attack(){
        Vector2 dir1 = new Vector2(1,1);
        Vector2 dir2 = new Vector2(1,-1);
        Vector2 dir3 = new Vector2(-1,1);
        Vector2 dir4 = new Vector2(-1,-1);
        
        EnvironmentManager.currentEnv.spawnEntity(
                new EnemyProj(
                        new Vector2(body.getPosition().x * PPM + 5f, body.getPosition().y * PPM + 5f),
                        dir1,
                        DAMAGE));
        EnvironmentManager.currentEnv.spawnEntity(
                new EnemyProj(
                        new Vector2(body.getPosition().x * PPM + 5f, body.getPosition().y * PPM - 5f),
                        dir2,
                        DAMAGE));
        EnvironmentManager.currentEnv.spawnEntity(
                new EnemyProj(
                        new Vector2(body.getPosition().x * PPM - 5f , body.getPosition().y * PPM + 5f),
                        dir3,
                        DAMAGE));
        EnvironmentManager.currentEnv.spawnEntity(
                new EnemyProj(
                        new Vector2(body.getPosition().x * PPM - 5f, body.getPosition().y * PPM - 5f ),
                        dir4,
                        DAMAGE));
        
        canAttack = false;
        esprite = moveSprite;
    }
    
}
