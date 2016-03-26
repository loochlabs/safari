/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvStart.charstart;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.DynamicEntities.player.PlayerEntity;
import com.mygdx.entities.DynamicEntities.player.Player_Lumen;
import com.mygdx.entities.ImageSprite;
import com.mygdx.environments.Environment;
import static com.mygdx.game.MainGame.RATIO;

/**
 *
 * @author saynt
 */
public class CharacterStart_Lumen extends CharacterStart{
    
    public CharacterStart_Lumen(Vector2 pos, int select) {
        super(pos, select);
        
        startSprite = new ImageSprite("lumen-start", true);
        startSprite.sprite.setScale(0.5f*RATIO);
        
    }
    

    @Override
    public PlayerEntity createPlayer() {
        return new Player_Lumen(pos.cpy());
    }
}
