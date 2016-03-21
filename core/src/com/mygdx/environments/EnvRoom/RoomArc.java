/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvRoom;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.StaticEntities.StaticEntity;
import com.mygdx.entities.StaticEntities.NullWall;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class RoomArc extends StaticEntity{

    private NullWall wallWest, wallEast;
    
    public RoomArc(Vector2 pos, float w, float h) {
        super(pos, w, h);
        
        bd.position.set(pos.x/PPM,pos.y/PPM);
        shape.setAsBox(width/PPM, height/PPM);
        fd.shape = shape;
        userdata = "roomArc_" + id;
        fd.isSensor = true;
        
        texture = MainGame.am.get(ResourceManager.ROOM_ARC);
        
        wallWest = new NullWall(new Vector2(pos.x - width*0.78f, pos.y), 15*RATIO, height*0.9f);
        wallEast = new NullWall(new Vector2(pos.x + width*0.78f, pos.y), 15*RATIO, height*0.9f);
    }
    
    @Override
    public void init(World world){
        super.init(world);
        
        EnvironmentManager.currentEnv.spawnEntity(wallWest);
        EnvironmentManager.currentEnv.spawnEntity(wallEast);
    }
    
}
