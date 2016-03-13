/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.StaticEntities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.Entity;

/**
 *
 * @author looch
 */
public class StaticEntity extends Entity{
    
    public StaticEntity(Vector2 pos, float w, float h){
        super(pos,w,h);
        
        bd.type = BodyType.StaticBody;
        userdata = "static_" + id;      
    }

    @Override
    public void init(World world) {
        body = world.createBody(bd);
        body.createFixture(fd).setUserData(userdata);
        body.setUserData(userdata);
    }

    
}
