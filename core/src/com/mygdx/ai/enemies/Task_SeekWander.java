/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.ai.enemies;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.mygdx.entities.DynamicEntities.enemies.EnemyEntity2;

/**
 *
 * @author looch
 */
public class Task_SeekWander extends LeafTask<EnemyEntity2>{

    @Override
    public void run(EnemyEntity2 en) {
        if(en.getSeekWanderSb().getTarget() == null){
            en.setNewWanderPos();
        }
        
        if(en.inWanderRange()){
            this.success();
        }else{
            en.seekWander();
            this.running();
        }
        
    }

    @Override
    protected Task<EnemyEntity2> copyTo(Task<EnemyEntity2> task) {
        Task_SeekWander moveTo = (Task_SeekWander)task;
        return moveTo;
    }
}
