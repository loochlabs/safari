/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.player;

import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author looch
 */
public class Player_Woogie extends PlayerEntity{

    public Player_Woogie(Vector2 pos, float w, float h) {
        super(pos, w, h);
        
        LIFE_STAT_COUNT = 3;
        ENERGY_STAT_COUNT = 1;
        DAMAGE_STAT_COUNT = 5;
        SPEED_STAT_COUNT = 2;
        SPECIAL_STAT_COUNT = 1;
        
        updateStats();
        life = CURRENT_LIFE;
    }
    
}
