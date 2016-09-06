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
 * @author saynt
 */
public class Enemy_Poe extends Enemy_TestMon{
    
    public Enemy_Poe(Vector2 pos) {
        super(pos);
        
        idleTexture = MainGame.am.get(ResourceManager.ENEMY_POE_IDLE);
        deadTexture = MainGame.am.get(ResourceManager.ENEMY_POE_DEAD);
        texture = idleTexture;
    }
    
}
