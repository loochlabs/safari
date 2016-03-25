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
import com.mygdx.entities.ImageSprite;
import com.mygdx.entities.projectiles.EnemyProj;
import com.mygdx.environments.EnvironmentManager;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.FrameCounter_Attack;
import java.io.Reader;

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
        
        //combat
        ATTACK_RANGE = 4.5f*RATIO;
        
        prepTime = 0.5f;
        attTime = 0.3f;
        recovTime = 3f;
        attackFC = new FrameCounter_Attack(prepTime, attTime, recovTime);
        
        canInterrupt = false;
    }
    
    @Override
    public void init(World world) {
        super.init(world);
        //ai
        Reader reader = null;
        try {
            //read this file in resource manager
            reader = Gdx.files.internal("ai/enemies/en_goober2.tree").reader();
            BehaviorTreeParser<EnemyEntity> parser = new BehaviorTreeParser<EnemyEntity>(BehaviorTreeParser.DEBUG_NONE);
            bt = parser.parse(reader, this);
        } finally {
            StreamUtils.closeQuietly(reader);
        }
    }

    @Override
    public void attack(){
        Vector2 dir = GameScreen.player.getPos().cpy().sub(pos).nor();
        EnvironmentManager.currentEnv.spawnEntity(
                new EnemyProj(pos.cpy(), dir, DAMAGE));
        
        canAttack = false;
    }
}
