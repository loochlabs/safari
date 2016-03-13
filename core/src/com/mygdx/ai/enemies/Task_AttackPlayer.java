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
public class Task_AttackPlayer extends LeafTask<EnemyEntity2>{

    @Override
    public void run(EnemyEntity2 en) {
        if(en.getAttackFC().running){
            System.out.println("@Task_AttackPlayer running ");
            this.running();
        }else if(en.getAttackFC().complete){
            System.out.println("@Task_AttackPlayer success ");
            en.resetAttack();
            this.success();
        }else{
            System.out.println("@Task_AttackPlayer en attakcing ");
            en.prepAttack();
        }
    }

    @Override
    protected Task<EnemyEntity2> copyTo(Task<EnemyEntity2> task) {
        Task_AttackPlayer moveTo = (Task_AttackPlayer)task;
        return moveTo;
    }
}
