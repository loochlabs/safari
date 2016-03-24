/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.esprites;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.ImageSprite;
import com.mygdx.environments.EnvVoid.EnvVoid;
import com.mygdx.environments.EnvironmentManager;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.FrameCounter;

/**
 *
 * @author saynt
 */
public class DecomSprite extends EntitySprite{
    
    private final ImageSprite decom1, decom2, decom3;
    private EnvVoid env;
    private float box_x, box_y, box_w, box_h;
    private int decomStage = 0;
    private FrameCounter stageFC = new FrameCounter(20f);
    
    public DecomSprite(Vector2 pos) {
        super(pos, 450f*RATIO, 200f*RATIO, "leave1", true, false, false, false, 1.0f, false, false, false, false);
        
        decom1 = new ImageSprite(isprite);
        decom2 = new ImageSprite("leave2", true, false, false, false);
        decom3 = new ImageSprite("decom3", true, false, false, false);
    }
    
    @Override
    public void init(World world){
        env = (EnvVoid)EnvironmentManager.currentEnv;
        
        box_w = 300f;
        box_h = 300f;
        box_x = env.getWidth()/2 - box_w/2;
        box_y = env.getHeight()/2 - box_h/2;
    }
    
    @Override
    public void update(){
        super.update();
        
        checkPlayerPos();
    }
    
    private void checkPlayerPos(){
        Vector2 pp = GameScreen.player.getPos();
        if(pp.x > box_x 
                && pp.x < box_x + box_w
                && pp.y > box_y
                && pp.y < box_y + box_h){
            updateDecomStage();
        }
    }
    
    private void updateDecomStage(){
        
        if (!stageFC.running) {
            decomStage++;
            stageFC.start(fm);

            switch (decomStage) {
                case 1:
                    isprite = decom1;
                    break;
                case 2:
                    isprite = decom2;
                    break;
                case 3:
                    isprite = decom3;
                    break;
                default:
                    isprite = decom3;
                    break;
            }
            isprite.reset();
        }
        
        
        
    }
}
