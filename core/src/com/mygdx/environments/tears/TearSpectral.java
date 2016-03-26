/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.tears;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.ImageSprite;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.screen.GameScreen;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class TearSpectral extends TearPortal{

    public TearSpectral(Vector2 pos,int linkid) {
        super(pos, linkid);
        
        userdata = "warp_" + id;
        bd.position.set(pos.x/PPM,pos.y/PPM);
        fd = new FixtureDef();
        fd.filter.categoryBits = BIT_WALL;
        fd.filter.maskBits = BIT_PLAYER;
        cshape.setRadius(0.5f * width/PPM);
        fd.shape = cshape;
        
        isprite = new ImageSprite("tear-open2",true);
    }
    
    @Override
    public void init(World world){
        super.init(world);
        warpenv = EnvironmentManager.get(linkid);
    }
    
    /*
    @Override 
    public void alert(){
        warp();
    }*/
    
    @Override
    public void warp(){
        //revive player
        
        GameScreen.player.revive();
        EnvironmentManager.currentEnv.end(linkid, endTime);
        opened = false;
        
    }
    
}
