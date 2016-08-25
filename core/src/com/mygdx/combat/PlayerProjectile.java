/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.Entity;
import com.mygdx.entities.projectiles.ProjectileEntity;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import static com.mygdx.utilities.UtilityVars.BIT_ATT;
import static com.mygdx.utilities.UtilityVars.BIT_EN;
import static com.mygdx.utilities.UtilityVars.BIT_TEAR;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;

/**
 *
 * @author saynt
 */
public class PlayerProjectile extends ProjectileEntity{

    public PlayerProjectile(Vector2 pos, float width, float height, Vector2 dir, float dmg) {
        super(pos, width, height, dir.scl(22f*RATIO), 1f, dmg);
        
        fd.filter.categoryBits = BIT_ATT;
        fd.filter.maskBits = BIT_EN | BIT_TEAR | BIT_WALL;
        
        texture = MainGame.am.get(ResourceManager.DEFAULT_SQUARE);
    }
    
    @Override
    public void alert(String []str) {
        try {
            if (str[2].contains("en_")) {
                for (Entity e : EnvironmentManager.currentEnv.getEntities()) {
                    if (e.getUserData().toString().equals(str[2])) {
                        
                        //impact with enemy
                    }
                }
            }
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
        super.alert(str);
    }
}
