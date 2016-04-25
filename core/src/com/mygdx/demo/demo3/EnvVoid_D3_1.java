/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.demo.demo3;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.demo.demo2.EnvVoid_D2;
import com.mygdx.demo.demo2.EnvVoid_D2_2;
import com.mygdx.entities.DynamicEntities.DogEntities.MurphyEntity_Initial;
import com.mygdx.entities.DynamicEntities.DogEntities.StellaEntity;
import com.mygdx.entities.StaticEntities.trap.En_VoidTrap;
import com.mygdx.entities.esprites.EntitySprite;
import com.mygdx.environments.EnvNull.random.Tear_R;
import com.mygdx.environments.EnvVoid.pads.EndPad;
import com.mygdx.environments.EnvVoid.pads.EndPiece;
import com.mygdx.environments.EnvVoid.GridCell;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.environments.tears.TearPortal;
import static com.mygdx.game.MainGame.RATIO;

/**
 *
 * @author saynt
 */
public class EnvVoid_D3_1 extends EnvVoid_D3{
    
    public EnvVoid_D3_1(int id){
        super(id, 3400f*RATIO,3400f*RATIO,2, "A");
        
        introDescription = "The Void II";
        
        cyst_count = 4;
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
            TearPortal tear = (TearPortal) spawnEntity(new Tear_R(createSpawnLocation(), this.id, 1));

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
        generateDMLockRoom();
    }
    
    @Override
    public void generateEndPad(){
        
        
        GridCell g = createGridLocation();
        
        
        //need to create just endPad
        pad =  new EndPad_D3_1(
                        new Vector2(g.getX() + g.getWidth()/2, g.getY() + g.getHeight()/2), 
                        spawnCount, -22 );
        
        map.setEndPos(pad.getPos().x, pad.getPos().y);
        
        endPieces = pad.getPieces();
        spawnCount = endPieces.size;
        
        
        //Needs to be here.  This room needs to be generated before 
        //  endPieces are distributed to other tears
        generateEndPieceRoom(endPieces.pop());

    }
    
    
    @Override
    public void generateDogs(){
        //stella & murphy
        
        spawnEntity(new StellaEntity(new Vector2(800,500)));
        
        spawnEntity(new MurphyEntity_Initial(this.createSpawnLocation()));
    }
    
    @Override 
    public void generateMisc(){
        super.generateMisc();
        
        //bg sprites
        spawnEntity(new EntitySprite(new Vector2(0, grid.getHeight()*0.9f),
                        185f,165f,
                        "decom2", 
                        true, false, false, false, 
                        1.0f * RATIO,
                        false, false,
                        false, false));
        
        
        //spawnEntity(new En_VoidTrap(createSpawnLocation()));
    }
    
    
    @Override
    public String toString(){
        return "DEMO2 EnvVoid 1";
    }
    
    
    private class EndPad_D3_1 extends EndPad {

        public EndPad_D3_1(Vector2 pos, int scount, int warpid) {
            super(pos, scount, warpid);
        }
        
        @Override
        public void complete(){
            super.complete();
            
            EnvironmentManager.add(new EnvVoid_D3_2(idwarp));
        }

    }

}
