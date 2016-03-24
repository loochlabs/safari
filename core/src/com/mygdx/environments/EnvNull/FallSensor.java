/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvNull;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.StaticEntities.StaticEntity;
import com.mygdx.environments.EnvironmentManager;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class FallSensor extends StaticEntity{
    
    private final NullSection childSection, parentSection;
    //private boolean active = true;
    
    public NullSection getParent() { return parentSection; }
    
    public FallSensor(Vector2 pos, float w, float h, NullSection childSection, NullSection parentSection){
        super(pos,w,h);
       
        this.childSection = childSection;
        this.parentSection = parentSection;
        
        bd.position.set(pos.x/PPM,pos.y/PPM);
        shape.setAsBox(width/PPM, height/PPM);
        fd.shape = shape;
        userdata = "fallsensor_" + id;
        fd.filter.categoryBits = BIT_WALL;
        fd.filter.maskBits = BIT_PLAYER; //todo: add BIT_EN for enemy fall
        fd.isSensor = true;
        
    }
    
    @Override
    public void alert(String []str){
        //init childSection PitSection
        //move player to center of childSection PitSection
        //set parentSection side type and texture to connected
        
        if(str[0].equals("begin") && str[1].equals(userdata.toString())){
            EnvNull ev = (EnvNull)EnvironmentManager.currentEnv;
            ev.fall(childSection, true);
        }
        
    }
    
}
