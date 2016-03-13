/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvSpectral;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.StaticEntities.walls.NullWall;
import com.mygdx.environments.Environment;

/**
 *
 * @author looch
 */
public class SpectralPath_Trap extends SpectralPath{

    private SpectralTrap trap;
    private NullWall nwall;
    
    public SpectralPath_Trap(Vector2 pos, float width, float height, Environment env) {
        super(pos, width, height, env);
        
        trap = new SpectralTrap(new Vector2(pos.x + width/2, pos.y + height/2), width/2);
        nwall = new NullWall(new Vector2(pos.x + width/2, pos.y + height - height*0.25f/2), width/2, height*0.25f/2);
    }
    
    @Override
    public void init(){
        super.init();
        
        env.spawnEntity(trap);
        env.spawnEntity(nwall);
    }
    
}
