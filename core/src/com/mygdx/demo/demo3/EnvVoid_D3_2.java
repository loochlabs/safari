/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.demo.demo3;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.demo.demo1.EndWarp_Demo1;
import com.mygdx.demo.demo2.EnvVoid_D2;
import com.mygdx.entities.StaticEntities.trap.En_VoidTrap;
import com.mygdx.entities.esprites.ManSprite;
import com.mygdx.environments.EnvNull.random.Tear_R;
import com.mygdx.environments.EnvSub.EnvSub;
import com.mygdx.environments.EnvVoid.pads.EndPad;
import com.mygdx.environments.EnvVoid.pads.EndPiece;
import com.mygdx.environments.EnvVoid.pads.EndWarp;
import com.mygdx.environments.EnvVoid.GridCell;
import com.mygdx.environments.tears.TearPortal;
import static com.mygdx.game.MainGame.RATIO;

/**
 *
 * @author saynt
 */
public class EnvVoid_D3_2 extends EnvVoid_D3{
    
    public EnvVoid_D3_2(int id){
        super(id, 5000f*RATIO,5000f*RATIO,3, "B");
        
        introDescription = "The Void III";
        
        cyst_count = 6;
    }
    
    
    
    @Override 
    public void spawnPad(){}
    
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
        
        unavailableGridCells.add(5);//center cell;
        
        //spawn end pad
        //NOTE: EndPad needs to be created before tears for end-piece creation
        generateEndPad();
        
        
        //*************************************************************
        //spawn tears in random grid cell (not 5, center)
        //2,4,6,8,1,3,7,9
        
        for(int i = 0; i < spawnCount; i++){
            
            //spawn tear in given GridCell
            TearPortal tear = (TearPortal) spawnEntity(new Tear_R(createSpawnLocation(), this.id, 3));

            map.getTears().add(tear);
        }
        
        
        //add endPieces to Tear envs
        Array<TearPortal> usedTears = new Array<TearPortal>();
        TearPortal tear;
        int pcount = endPieces.size;
        
        do {
            do {
                tear = map.getTears().random();
            } while (!tear.getUserData().toString().contains("_null")
                    || usedTears.contains(tear, false));

            //TODO: chage this to endPieces.pop(). get rid of pcount
            EndPiece piece = endPieces.get(--pcount);
            tear.addReward(piece);
            usedTears.add(tear);

        } while (pcount > 0);
        
        //*********************************************************
     
        
        generateRngSimpleReward();
        generateRngSimpleReward();
        generateGlyphRoom();
    }
    
    
    @Override
    public void generateMisc(){
        super.generateMisc();
        
        this.spawnEntity(new ManSprite(new Vector2(0,0)));
        
        //spawnEntity(new En_VoidTrap(createSpawnLocation()));
        //spawnEntity(new En_VoidTrap(createSpawnLocation()));
    }
    
    
    @Override
    public String toString(){
        return "DEMO2 EnvVoid 2";
    }
    

}
