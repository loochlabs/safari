/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.entities.DynamicEntities.DynamicEntity;

/**
 *
 * @author looch
 * 
 * Description:
 *      Attack sensor for normal front attack
 */
public class NormAttackSensor extends AttackSensor{
    
    private final CircleShape cshape = new CircleShape();
    
    public NormAttackSensor(DynamicEntity parent){
        super(parent);
        data = "playatt_norm";
        
        cshape.setRadius(parent.getRange());
        cshape.setPosition(new Vector2(0,0));
        this.shape = cshape;
        
        
    }
    
    
}
