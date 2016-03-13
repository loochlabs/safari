/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.demo.demo1;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.StaticEntities.breakable.Cyst_Blue;
import com.mygdx.environments.tears.Tear_Room_Simple;
import com.mygdx.environments.EnvSub.EnvSub;
import com.mygdx.environments.EnvSub.pads.EndWarp;
import com.mygdx.environments.EnvVoid.EnvVoid;
import com.mygdx.environments.EnvVoid.GridCell;
import static com.mygdx.game.MainGame.RATIO;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class EnvVoid_Demo1 extends EnvVoid{

    public EnvVoid_Demo1(int id) {
        super(id, 2000f*RATIO, 2000f*RATIO, 2, "");
        
        startPos = new Vector2(width*0.5f/PPM, height*0.5f/PPM);
        this.setPlayerToStart();
    }
    
    @Override
    public void generateEndPad(){
        GridCell g = createGridLocation();
        
        EndWarp warp = (EndWarp)spawnEntity(
                new EndWarp_Demo1(
                        new Vector2(
                                g.getX() + g.getWidth()/2,
                                g.getY() + g.getHeight()/2)));
        
        map.setEndPos(warp.getPos().x, warp.getPos().y);
        
        EnvSub env = warp.getEnv();
        endPieces = env.getEndPad().getPieces();
        spawnCount = endPieces.size;
    }
    
    @Override
    public void generateTears(){
        super.generateTears();
        
        //**********************************
        //      SIMPLE ROOM
        //*********************************
        
        //Array<Integer> usedCells = new Array<Integer>();
        Tear_Room_Simple t2 = (Tear_Room_Simple) spawnEntity(
                new Tear_Room_Simple(createSpawnLocation(),this.id));

        map.getTears().add(t2);
        
       //*********************************
    }
    
    @Override
    public void generateMisc(){
        super.generateMisc();
        
        //***************************************
        //      SPAWN CYSTS
        //************************************
        int lcount = 4;

        for (int i = 0; i < lcount; i++) {

            spawnEntity( new Cyst_Blue( new Vector2(createSpawnLocation())));

        }
        //***************************************************
    }
    
    @Override
    public String toString(){
        return "DEMO 1";
    }
    
}
