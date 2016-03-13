/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.StaticEntities.walls;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.StaticEntities.StaticEntity;
import static com.mygdx.utilities.UtilityVars.BIT_ATT;
import static com.mygdx.utilities.UtilityVars.BIT_EN;
import static com.mygdx.utilities.UtilityVars.BIT_PICKUP;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 * 
 * Class Description:
 *      Bounding wall for Null Environment
 */
public class NullWall extends StaticEntity{
    
    public NullWall(Vector2 pos, float w, float h){
        super(pos,w,h);
        
        bd.position.set(pos.x/PPM,pos.y/PPM);
        shape.setAsBox(width/PPM, height/PPM);
        fd.shape = shape;
        userdata = "nwall_" + id;
        fd.filter.categoryBits = BIT_WALL;
        fd.filter.maskBits = BIT_PLAYER | BIT_EN | BIT_PICKUP | BIT_ATT;
        
        
        
    }
    
    @Override
    public void damage(float dmg){}
    
}
