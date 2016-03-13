/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.ai.enemies;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.mygdx.entities.DynamicEntities.enemies.EnemyEntity;

/**
 *
 * @author looch
 */
public class EnPatrolTask extends LeafTask<EnemyEntity>{

    @Override
    public void run(EnemyEntity en) {
        en.patrol();
        /*
        if(!en.inAttackRange())
            en.moveTo(GameScreen.player.getBody().getPosition(),en.getSpeed());
        else{
            success();
        }*/
    }

    @Override
    protected Task<EnemyEntity> copyTo(Task<EnemyEntity> task) {
        EnMoveToTask moveTo = (EnMoveToTask)task;
        return moveTo;
    }
    
}
