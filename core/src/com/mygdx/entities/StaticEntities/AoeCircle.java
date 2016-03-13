/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.StaticEntities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.esprites.EntitySprite;
import com.mygdx.environments.Environment;
import com.mygdx.environments.EnvironmentManager;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.FrameCounter;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author saynt
 */
public class AoeCircle extends StaticEntity{
    
    protected FrameCounter durationFC = new FrameCounter(10f);
    protected Environment env;
    
    public FrameCounter getDurationFC() { return durationFC; } 
    public Environment getEnvironment() { return env; }
    
    public AoeCircle(Vector2 pos) {
        super(pos, 150f*RATIO, 150f*RATIO);
        
        userdata = "action_" + id;
        bd.position.set(pos.x/PPM, pos.y/PPM);
        cshape.setRadius(width/PPM);
        fd.isSensor = true;
        fd.filter.categoryBits = BIT_WALL;
        fd.filter.maskBits = BIT_PLAYER;
        fd.shape = cshape;
        
        this.flaggedForRenderSort = false;
        
        esprite = new EntitySprite("aoe-heal", true);
        esprite.sprite.setBounds(0, 0, width*2, height*2);
    }
    
    
    @Override
    public void init(World world){
        super.init(world);
        
        durationFC.start(fm);
        env = EnvironmentManager.currentEnv;
    }
    
    @Override
    public void update(){
        super.update();
        
        fm.update();
        
        if(durationFC.complete){
            this.dispose();
        }
    }
    
    
    
    
}
