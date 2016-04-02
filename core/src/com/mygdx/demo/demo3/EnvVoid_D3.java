/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.demo.demo3;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.StaticEntities.breakable.Cyst_Blue;
import com.mygdx.environments.EnvSub.pads.EndPad;
import com.mygdx.environments.EnvVoid.EnvVoid;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author saynt
 */
public class EnvVoid_D3 extends EnvVoid{
    
    protected EndPad pad;
    
    protected int cyst_count = 4;
    
    public EnvVoid_D3(int id , float w, float h, int spawncount, String type){
        super(id, w, h, spawncount, type);
        
        startPos = new Vector2(width*0.5f/PPM, height*0.5f/PPM);
        this.setPlayerToStart();
    }
    
    @Override
    public void init(){
        super.init();
        
        spawnPad();
    }
    
    public void spawnPad(){
        this.spawnEntity(pad);
        pad.createSections(this);
    }
    
    @Override
    public void generateEndPad(){
        
    }
    
    
    @Override
    public void generateMisc(){
        super.generateMisc();
        
        //***************************************
        //      SPAWN CYSTS
        //************************************

        for (int i = 0; i < cyst_count; i++) {

            spawnEntity( new Cyst_Blue( new Vector2(createSpawnLocation())));

        }
        //***************************************************
    }
}
