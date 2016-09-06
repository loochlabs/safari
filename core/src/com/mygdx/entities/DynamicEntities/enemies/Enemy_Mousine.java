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
public class Enemy_Mousine extends Enemy_TestMon{
    
    public Enemy_Mousine(Vector2 pos) {
        super(pos);
        
        idleTexture = MainGame.am.get(ResourceManager.ENEMY_MOUSINE);
        deadTexture = MainGame.am.get(ResourceManager.ENEMY_MOUSINE_DEAD);
        texture = idleTexture;
    }
    
}
