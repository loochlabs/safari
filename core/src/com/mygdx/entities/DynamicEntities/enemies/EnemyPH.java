/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;

/**
 *
 * @author looch
 */
public class EnemyPH extends EnemyEntity{
    
    public EnemyPH(Vector2 pos, float w, float h){
        super(pos,w,h);
        
        texture = MainGame.am.get(ResourceManager.ENEMY_PH);
        
        MAX_HP = 100;
        CURRENT_HP = MAX_HP;
    }
    
}
