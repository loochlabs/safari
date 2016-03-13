/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;

/**
 *
 * @author looch
 */
public class EnTargetDummy extends EnemyEntity{
    
    public EnTargetDummy(Vector2 pos, float w, float h){
        super(pos,w,h);
        
        bd.type = BodyType.StaticBody;
        
        texture = MainGame.am.get(ResourceManager.ENEMY_PH);
        
        MAX_HP = 100000;
        CURRENT_HP = MAX_HP;
        
    }
    
    @Override
    public void update(){
        super.update();
        if(CURRENT_HP < MAX_HP){
            CURRENT_HP += 20;
        }
    }
    
    @Override
    public String toString(){
        return "TargetDummy " + id;
    }
}
