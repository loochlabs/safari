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
public class Task_Wander extends LeafTask<EnemyEntity2>{

    @Override
    public void run(EnemyEntity2 en) {
        en.wander();
    }

    @Override
    protected Task<EnemyEntity2> copyTo(Task<EnemyEntity2> task) {
        Task_Wander moveTo = (Task_Wander)task;
        return moveTo;
    }
}
