/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.projectiles;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import static com.mygdx.utilities.UtilityVars.BIT_EN;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;

/**
 *
 * @author looch
 */
public class EnemyProj extends ProjectileEntity{

    public EnemyProj(Vector2 pos, Vector2 dir, float dmg) {
        super(pos, 15f*RATIO, 15f*RATIO, dir.scl(22f*RATIO), 5f, dmg);
        
        fd.filter.categoryBits = BIT_EN;
        fd.filter.maskBits = BIT_PLAYER | BIT_WALL;
        
        texture = MainGame.am.get(ResourceManager.PROJ_EN_1);
    }
    
    @Override
    public void alert(String str){
        if(str.contains("player"))
            GameScreen.player.damage(DMG);
        super.alert(str);
    }
    
}
