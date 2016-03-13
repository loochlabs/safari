/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.demo.demo2;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.DynamicEntities.DogEntities.StellaEntity_Initial;
import com.mygdx.entities.esprites.EntitySprite;
import com.mygdx.environments.EnvSub.pads.EndPad;
import com.mygdx.environments.EnvVoid.GridCell;
import com.mygdx.environments.EnvironmentManager;
import static com.mygdx.game.MainGame.RATIO;

/**
 *
 * @author saynt
 */
public class EnvVoid_D2_0 extends EnvVoid_D2{
    

    public EnvVoid_D2_0(int id) {
        super(id, 2000f*RATIO, 2000f*RATIO, 1, "");
        
        introDescription = "The Void I";
        
        cyst_count = 2;
    }
    
    
    
    @Override
    public void generateEndPad(){
        
        GridCell g = createGridLocation();
        
        
        //need to create just endPad
        pad = new EndPad_D2_0(
                        new Vector2(g.getX() + g.getWidth()/4, g.getY() + g.getHeight()/2), 
                        spawnCount, -21 );
        
        map.setEndPos(pad.getPos().x, pad.getPos().y);
        
        endPieces = pad.getPieces();
        spawnCount = endPieces.size;
        
        map.setEnable(false);
        
    }
    
    @Override
    public void generateDogs(){
        //stella & murphy
        
        
        /*
        StellaEntity_Initial stella = 
                new StellaEntity_Initial(
                        new Vector2(150f*RATIO, 150f*RATIO));
        */
        StellaEntity_Initial stella = 
                new StellaEntity_Initial(
                        this.createSpawnLocation());
        spawnEntity(stella);
        
    }
    
    
    @Override 
    public void generateMisc(){
        super.generateMisc();
        

        //bg sprites
        spawnSprite(
                new EntitySprite(
                        "decom", 
                        true, false, false, false, 
                        0, grid.getHeight()*0.9f
                )).sprite.setScale(RATIO);
    }
    
    @Override
    public String toString(){
        return "DEMO2 EnvVoid 0";
    }
    
    private class EndPad_D2_0 extends EndPad {

        public EndPad_D2_0(Vector2 pos, int scount, int idwarp) {
            super(pos, scount, idwarp);
            
        }

        @Override
        public void complete(){
            super.complete();
            
            EnvironmentManager.add(new EnvVoid_D2_1(idwarp));
        }
    }

}
