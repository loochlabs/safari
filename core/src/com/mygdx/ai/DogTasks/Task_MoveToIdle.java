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
 * 
 * AI for Stella.
 * Move to Player
 * 
 */
//todo: move this class to DogTasks folder
public class Task_MoveToIdle extends LeafTask<DogEntity>{

    @Override
    public void run(DogEntity dog) {
        if(!dog.inIdleRange()){
            dog.moveToPlayer();
            running();
        }else{
            success();
        }
    }

    @Override
    protected Task<DogEntity> copyTo(Task<DogEntity> task) {
        Task_MoveToIdle moveTo = (Task_MoveToIdle)task;
        return moveTo;
    }
    
}
