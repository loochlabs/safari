/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.ai.DogTasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.mygdx.entities.DynamicEntities.DogEntities.DogEntity;

/**
 *
 * @author looch
 */
public class Task_MoveToTear extends LeafTask<DogEntity>{

    @Override
    public void run(DogEntity dog) {
        
        if (dog.isNearTear() != null) {
            dog.moveToTear();
        } else {
            success();
        }
    }

    @Override
    protected Task<DogEntity> copyTo(Task<DogEntity> task) {
        Task_MoveToTear moveTo = (Task_MoveToTear)task;
        return moveTo;
    }
    
}
