/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.demo.demo2;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.demo.demo1.EndWarp_Demo1;
import com.mygdx.entities.esprites.ManSprite;
import com.mygdx.environments.EnvSub.EnvSub;
import com.mygdx.environments.EnvVoid.pads.EndPad;
import com.mygdx.environments.EnvSub.EndWarp;
import com.mygdx.environments.EnvVoid.GridCell;
import com.mygdx.environments.EnvironmentManager;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.StateManager;
import com.mygdx.screen.GameOver.GameOverScreen;
import com.mygdx.screen.GameScreen;
import com.mygdx.screen.ScreenManager;

/**
 *
 * @author saynt
 */
public class EnvVoid_D2_2 extends EnvVoid_D2{
    
    public EnvVoid_D2_2(int id){
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
                                g.getY() + g.getHeight()/2), 
                        this.id));
        
        map.setEndPos(warp.getPos().x, warp.getPos().y);
        
        EnvSub env = warp.getEnv();
        
        endPieces = env.getEndPad().getPieces();
        spawnCount = endPieces.size;
    }
    
    @Override
    public void generateTears(){
        super.generateTears();
        
        generateRngSimpleReward();
        generateRngSimpleReward();
        generateGlyphRoom();
    }
    
    @Override
    public void generateMisc(){
        super.generateMisc();;
        
        this.spawnEntity(new ManSprite(new Vector2(0,0)));
    }
    
    
    @Override
    public String toString(){
        return "DEMO2 EnvVoid 2";
    }
    
    private class EndPad_D2_2 extends EndPad {

        public EndPad_D2_2(Vector2 pos, int scount, int idwarp) {
            super(pos, scount, idwarp);
        }
        
/*
        @Override
        public void warpToNextEnv() {
            ///Game Over Demo screen
            ScreenManager.setScreen(new GameOverScreen());
        }*/
    }

}
