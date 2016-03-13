/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.utilities;

import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author looch
 */
public class SteeringUtils {
    
    public static float vectorToAngle(Vector2 vector){
        return (float)Math.atan2((-vector.x), vector.y);
    }
    
    public static Vector2 angleToVector(Vector2 outVector, float angle){
        outVector.x = -(float)Math.sin(angle);
        outVector.y = (float)Math.cos(angle);
        return outVector;
    }
    
}
