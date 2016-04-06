/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvVoid;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.StaticEntities.breakable.Cyst_Blue;
import com.mygdx.environments.tears.TearPortal;
import com.mygdx.environments.tears.Tear_Room_Glyph1;
import com.mygdx.entities.pickups.Pickup;
import com.mygdx.environments.EnvRoom.EnvRoom_Glyph1;
import com.mygdx.environments.EnvSub.EnvSub;
import com.mygdx.environments.EnvVoid.pads.A.EndWarp_A;
import com.mygdx.environments.EnvVoid.pads.EndWarp;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class EnvVoid_Showcase extends EnvVoid{

    public EnvVoid_Showcase(int id, float w, float h, int spawncount) {
        super(id, w, h, spawncount, "");
        
        startPos = new Vector2(width*0.5f/PPM, height*0.5f/PPM);
        this.setPlayerToStart();
        
    
    }
    
    @Override
    public void generateEndPad(){
        int tempId;

        do {
            tempId = rng.nextInt(9) + 1;
        } while (unavailableGridCells.contains(tempId, true));
        
        GridCell g = grid.getCellById(tempId);
        
        EndWarp warp = (EndWarp)spawnEntity(
                new EndWarp_A(
                        new Vector2(
                                g.getX() + g.getWidth()/2,
                                g.getY() + g.getHeight()/2)));
        
        unavailableGridCells.add(tempId);
        map.setEndPos(warp.getPos().x, warp.getPos().y);
        
        EnvSub env = warp.getEnv();
        endPieces = env.getEndPad().getPieces();
        spawnCount = endPieces.size;
    }
    
    
    @Override
    public void generateTears(){
        
        super.generateTears();
        
        //**********************
        //  STRANGE GLYPH ROOM
        //**************
        
        //TODO: simplify massively
        //check EnvVoid_D2_2
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
        
        
    }
    
    @Override
    public void generateMisc(){
        super.generateMisc();
        
        //random skill at start
        //this.spawnEntity(new SkillPad(new Vector2(startPos.x*PPM + 150*RATIO, startPos.y*PPM - 200f*RATIO), "Ghost Jab"));
        
        int lcount = 3;
        Array<Integer> usedCells = new Array<Integer>();
        int tempId; //grid id to spawn in
        
        for(int i = 0; i < lcount; i++){
        
            do {
                tempId = rng.nextInt(9) + 1;
            } while (tempId == 5 || usedCells.contains(tempId, true));
            GridCell gc = grid.getCellById(tempId);
            
            spawnEntity(
                    new Cyst_Blue(
                            new Vector2(
                                    gc.getX() + gc.getWidth() * rng.nextFloat(),
                                    gc.getY() + gc.getHeight() * rng.nextFloat())));
            
        }
        
    }
    
    
    @Override
    public String toString(){
        return "showcase";
    }
    
}
