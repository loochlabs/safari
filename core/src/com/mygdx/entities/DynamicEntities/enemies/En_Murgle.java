/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.StreamUtils;
import com.mygdx.entities.esprites.EntitySprite;
import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;
import com.mygdx.utilities.FrameCounter_Attack;
import java.io.Reader;

/**
 *
 * @author looch
 */
public class En_Murgle extends EnemyEntity{
    
    public En_Murgle(Vector2 pos, float w, float h){
        super(pos,w,h);
        
        idleTexture = MainGame.am.get(ResourceManager.MURGLE_MAIN);
        prepTexture = MainGame.am.get(ResourceManager.MURGLE_MAIN);
        
        moveSprite = new EntitySprite("murgle-front",true);
        moveSprite.sprite.setScale(0.5f);
        
        texture = idleTexture;
        
        MAX_HP = 5;
        CURRENT_HP = MAX_HP;
        CHARSPEED = 10.0f;
        DAMAGE = 20.0f;
        
        prepTime = 0.5f;
        attackTime = 0.3f;
        recovTime = 3f;
        
        attackFC = new FrameCounter_Attack(prepTime, attackTime, recovTime);
        
        //expYield = 3;
    }
    
    @Override
    public void init(World world){
        super.init(world);
        
        //ai
        Reader reader = null;
        try {
            reader = Gdx.files.internal("ai/enemies/en_murgle.tree").reader();
            BehaviorTreeParser<EnemyEntity> parser = new BehaviorTreeParser<EnemyEntity>(BehaviorTreeParser.DEBUG_NONE);
            enemybt = parser.parse(reader,this);
        } finally {
            StreamUtils.closeQuietly(reader);
        }
    }
    
    @Override
    public void update(){
        super.update();
        
        enemybt.step();
    }
    
    @Override
    public void attack(){
        super.attack();
        body.applyForce(attDest.scl(-800.0f), body.getPosition(), true);
    }
    
    @Override
    public void startAttack(){
        super.startAttack();
        esprite = null;
    }
    
    @Override
    public void moveTo(Vector2 dest, float speed){
        super.moveTo(dest, speed);
        esprite = moveSprite;
    }
}
