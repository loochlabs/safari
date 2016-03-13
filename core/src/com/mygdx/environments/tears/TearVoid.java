/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.tears;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.environments.EnvironmentManager;

/**
 *
 * @author looch
 * 
 * 
 * todo: old class
 */
public class TearVoid extends TearPortal{
    
    public TearVoid(Vector2 pos, int linkid){
        super(pos,linkid);
        
    }
    
    @Override
    public void init(World world){
        super.init(world);
        warpenv = EnvironmentManager.get(id);
    }
    
}
