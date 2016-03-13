/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.ai;

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
public class MoveToTask extends LeafTask<DogEntity>{

    @Override
    public void run(DogEntity dog) {
        if(!dog.inRange(dog.getIdleRange()))
            //dog.moveTo(GameScreen.player.getBody().getPosition(),dog.getSpeed());
            dog.moveTo(dog.findClosestBody().getPosition(),dog.getSpeed());
        else{
            success();
        }
    }

    @Override
    protected Task<DogEntity> copyTo(Task<DogEntity> task) {
        MoveToTask moveTo = (MoveToTask)task;
        return moveTo;
    }
    
}
