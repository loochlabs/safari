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
public class Task_SeekPlayer extends LeafTask<EnemyEntity>{

    @Override
    public void run(EnemyEntity en) {
        if(en.inAttackRange()){
            this.success();
        }else if(!en.inRangeOfPlayer()){
            this.fail();
        }else{
            en.seek();
            this.running();
        }
    }

    @Override
    protected Task<EnemyEntity> copyTo(Task<EnemyEntity> task) {
        Task_SeekPlayer moveTo = (Task_SeekPlayer)task;
        return moveTo;
    }
}
