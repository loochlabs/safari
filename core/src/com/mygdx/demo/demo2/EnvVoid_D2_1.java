/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.demo.demo2;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.DynamicEntities.DogEntities.MurphyEntity_Initial;
import com.mygdx.entities.DynamicEntities.DogEntities.StellaEntity;
import com.mygdx.entities.ImageSprite;
import com.mygdx.entities.esprites.EntitySprite;
import com.mygdx.environments.EnvVoid.pads.EndPad;
import com.mygdx.environments.EnvVoid.GridCell;
import com.mygdx.environments.EnvironmentManager;
import static com.mygdx.game.MainGame.RATIO;

/**
 *
 * @author saynt
 */
public class EnvVoid_D2_1 extends EnvVoid_D2{
    
    public EnvVoid_D2_1(int id){
        super(id, 3400f*RATIO,3400f*RATIO,2, "A");
        
        introDescription = "The Void II";
        
        cyst_count = 4;
    }
    
    @Override
    public void generateEndPad(){
        
        
        GridCell g = createGridLocation();
        
        
        //need to create just endPad
        pad =  new EndPad_D2_1(
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
    public void generateTears(){
        super.generateTears();
        
        generateRngSimpleReward();
        generateDMLockRoom();
        
    }
    
    @Override
    public void generateDogs(){
        //stella & murphy
        
        StellaEntity stella = new StellaEntity(new Vector2(800,500));
        spawnEntity(stella);
        
        /*
        MurphyEntity_Initial murphy = 
                new MurphyEntity_Initial(
                        new Vector2(150f*RATIO, 150f*RATIO));
        */
        
        MurphyEntity_Initial murphy = 
                new MurphyEntity_Initial(
                        this.createSpawnLocation());
        spawnEntity(murphy);
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
    }
    
    
    @Override
    public String toString(){
        return "DEMO2 EnvVoid 1";
    }
    
    
    private class EndPad_D2_1 extends EndPad {

        public EndPad_D2_1(Vector2 pos, int scount, int warpid) {
            super(pos, scount, warpid);
        }
        
        @Override
        public void complete(){
            super.complete();
            
            EnvironmentManager.add(new EnvVoid_D2_2(idwarp));
        }

        /*
        @Override
        public void warpToNextEnv() {
            //Warp to EnvVoid_D2_2
            
            EnvironmentManager.currentEnv.setIdWarp(-22);
            EnvironmentManager.add(new EnvVoid_D2_2(-22));
            
            
            //EnvironmentManager.createLevel(-22); //TODO: change id value
        }*/
    }

}
