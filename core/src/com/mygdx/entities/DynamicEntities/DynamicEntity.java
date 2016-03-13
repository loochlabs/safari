/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.Entity;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 * 
 * TODO: remove entire class
 */
public class DynamicEntity extends Entity{
    
    
    
    protected float CHARSPEED;
    
    //todo: move to playerEntity
    protected float RANGE;
    //protected long ATTSPEED;
    
    
    public float getSpeed() {return CHARSPEED;}
    public float getRange() {return RANGE;}
    
    public void setSpeed(float speed) { this.CHARSPEED = speed; }

    public DynamicEntity(Vector2 pos, float w, float h){
        super(pos,w,h);
        
        MAX_HP = 100;
        CURRENT_HP = MAX_HP;
        
        //canGrab = true;
        
        bd.position.set(pos.x/PPM,pos.y/PPM);
        bd.type = BodyType.DynamicBody;
        cshape.setRadius(width/PPM);
        shape.setAsBox(width/PPM, height/PPM);
        fd.shape = cshape;
        userdata = "dyn";
        
    }

    @Override
    public void init(World world) {
        body = world.createBody(bd);
        body.createFixture(fd).setUserData(userdata);
        body.setUserData(userdata);
        body.setLinearDamping(8.0f);
        
    }

    
}
