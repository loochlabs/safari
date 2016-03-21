/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.ImageSprite;
import com.mygdx.entities.projectiles.EnemyProj;
import com.mygdx.environments.EnvironmentManager;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.FrameCounter_Attack;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class En_Peeker extends EnemyEntity{

    public En_Peeker(Vector2 pos) {
        super(pos, 30f, 30f);
        
        
        moveSprite = new ImageSprite("peeker-idle", true);
        moveSprite.sprite.setScale(0.55f*RATIO);
        isprite = moveSprite;
        attackSprite = moveSprite;
        prepSprite = new ImageSprite("peeker-prep", true);
        prepSprite.sprite.setScale(0.55f*RATIO);
     
        
        MAX_HP = 30f;
        CURRENT_HP = MAX_HP;
        DAMAGE = 10f;
        
        prepTime = 0.5f;
        attackTime = 0.3f;
        recovTime = 3f;
        attackFC = new FrameCounter_Attack(prepTime, attackTime, recovTime);
        
        PLAYER_ATTACK_RANGE = 6f; 
        
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
    public void moveTo(Vector2 dv, float speed){}
    
    @Override
    public void startAttack(){
        //launch bullet at player
        
        super.startAttack();
        attDest = null;
    }
    
    @Override
    public void attack(){
        Vector2 dir = GameScreen.player.getBody().getPosition().cpy().sub(body.getPosition()).nor();
        EnvironmentManager.currentEnv.spawnEntity(
                new EnemyProj(
                        new Vector2(body.getPosition().x * PPM, body.getPosition().y * PPM),
                        dir,
                        DAMAGE));
        canAttack = false;
        isprite = moveSprite;
    }
}
