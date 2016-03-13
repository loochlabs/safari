/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvSlum;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.StaticEntities.walls.NullWall;
import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;
import com.mygdx.utilities.Coordinate;
import java.util.Random;

/**
 *
 * @author looch
 */
public class SlumSec_Wall {/*extends SlumSection{

    public SlumSec_Wall(Vector2 pos, float width, float height, EnvSlum env, Coordinate coord) {
        super(pos, width, height, env, coord);
        
        Random rng = new Random();
        float r = rng.nextFloat();
        bg = r < 0.5f ? (Texture)MainGame.am.get(ResourceManager.SLUM_SECTION_WALL) : 
                (Texture)MainGame.am.get(ResourceManager.SLUM_SECTION_WALL_2);
    }
    
    @Override
    public void init(){
        super.init();
        
        env.spawnEntity(new NullWall(new Vector2(pos.x+width/2, pos.y+height/2), width/2, height/2));
    }*/
    
}
