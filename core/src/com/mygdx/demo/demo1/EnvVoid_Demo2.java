/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.demo.demo1;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.StaticEntities.breakable.Cyst_Blue;
import com.mygdx.environments.tears.TearPortal;
import com.mygdx.environments.tears.Tear_Room_Glyph1;
import com.mygdx.environments.tears.Tear_Room_Simple;
import com.mygdx.entities.pickups.Pickup;
import com.mygdx.environments.EnvRoom.EnvRoom_Glyph1;
import com.mygdx.environments.EnvSub.EnvSub;
import com.mygdx.environments.EnvVoid.pads.EndWarp;
import com.mygdx.environments.EnvVoid.EnvVoid;
import com.mygdx.environments.EnvVoid.GridCell;
import static com.mygdx.game.MainGame.RATIO;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class EnvVoid_Demo2 extends EnvVoid{

    public EnvVoid_Demo2(int id) {
        super(id, 3000f*RATIO, 3000f*RATIO, 3, "");
        
        startPos = new Vector2(width*0.5f/PPM, height*0.5f/PPM);
        this.setPlayerToStart();
    }
    
    @Override
    public void generateEndPad(){
        GridCell g = createGridLocation();
        
        EndWarp warp = (EndWarp)spawnEntity(
                new EndWarp_Demo2(
                        new Vector2(
                                g.getX() + g.getWidth()/2,
                                g.getY() + g.getHeight()/2)));
        
        map.setEndPos(warp.getPos().x, warp.getPos().y);
        
        EnvSub env = warp.getEnv();
        endPieces = env.getEndPad().getPieces();
        spawnCount = endPieces.size;
    }
    
    @Override
    public void generateMisc(){
        super.generateMisc();
        
        //***************************************
        //      SPAWN CYSTS
        //************************************
        int lcount = 10;

        for (int i = 0; i < lcount; i++) {

            spawnEntity( new Cyst_Blue( new Vector2(createSpawnLocation())));

        }
        //***************************************************
    }
    
    @Override
    public void generateTears(){
        
        super.generateTears();
        
        //**********************
        //  STRANGE GLYPH ROOM
        //**************
        Array<Integer> usedCells = new Array<Integer>();
        int tempId; //grid id to spawn in
        do{
                tempId = rng.nextInt(9) + 1;
        }while (tempId == 5 || usedCells.contains(tempId, true));
        GridCell gc = grid.getCellById(tempId);
        
       Tear_Room_Glyph1 t1 = (Tear_Room_Glyph1)spawnEntity(new Tear_Room_Glyph1(new Vector2(
                    gc.getX() + (float) (rng.nextFloat() * gc.getWidth()*0.9f),
                    gc.getY() + (float) (rng.nextFloat() * gc.getHeight()*0.9f)),
                    this.id));
       
       map.getTears().add(t1);
       
       //assign key from Tear_Room_Glyph1 to random null
        EnvRoom_Glyph1 skillRoom1 = (EnvRoom_Glyph1) t1.getWarpEnv();//this needs to be a key pool (Array<Keys>) to pull from,, not a specific room

        Pickup key = skillRoom1.getKey();
        TearPortal keyTear;
        int scount = 0;

        do {
            keyTear = map.getTears().random();
            scount++;
        } while (!keyTear.getUserData().toString().contains("_null") || scount <= spawnCount * 2);

        keyTear.addReward(key);
       
        //**************************************
        
        //**********************************
        //      SIMPLE ROOM
        //*********************************
        
        do {
            tempId = rng.nextInt(9) + 1;
        } while (tempId == 5 || usedCells.contains(tempId, true));
        gc = grid.getCellById(tempId);

        Tear_Room_Simple t2 = (Tear_Room_Simple) spawnEntity(new Tear_Room_Simple(new Vector2(
                gc.getX() + (float) (rng.nextFloat() * gc.getWidth() * 0.9f),
                gc.getY() + (float) (rng.nextFloat() * gc.getHeight() * 0.9f)),
                this.id));

        map.getTears().add(t2);
       //*********************************
        
        
    }
    
    @Override
    public String toString(){
        return "DEMO 2";
    }
    
}
