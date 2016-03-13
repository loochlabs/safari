/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.npcs;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.entities.DynamicEntities.DynamicEntity;
import static com.mygdx.utilities.UtilityVars.BIT_EN;
import static com.mygdx.utilities.UtilityVars.BIT_PICKUP;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;

/**
 *
 * @author looch
 */
public class NpcEntity extends DynamicEntity{

    public NpcEntity(Vector2 pos, float w, float h) {
        super(pos, w, h);
        
        bd.type = BodyDef.BodyType.KinematicBody;
        fd.filter.categoryBits = BIT_EN;
        fd.filter.maskBits = BIT_WALL | BIT_EN | BIT_PICKUP | BIT_PLAYER;
        
    }
    
    @Override
    public void damage(float d){};
    
}
