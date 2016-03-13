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
 */
public class MoveToTearTask extends LeafTask<DogEntity>{

    @Override
    public void run(DogEntity dog) {
        
        if(dog.isNearTear() != null )
            //if(!stella.atTear())
                dog.moveTo(dog.isNearTear().getBody().getPosition(),dog.getSpeed());
        else{
            success();
        }//&& !stella.atTear(stella.isNearTear()
    }

    @Override
    protected Task<DogEntity> copyTo(Task<DogEntity> task) {
        MoveToTearTask moveTo = (MoveToTearTask)task;
        return moveTo;
    }
    
}
