/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.projectiles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.Entity;
import com.mygdx.entities.ImageSprite;
import com.mygdx.entities.esprites.EntitySprite;
import com.mygdx.environments.EnvironmentManager;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.utilities.FrameCounter;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class ProjectileEntity extends Entity{

    protected final Vector2 dv;
    protected final float DURATION;
    protected final FrameCounter durFC;
    
    public ProjectileEntity(Vector2 pos, float w, float h, Vector2 dir, float duration, float dmg) {
        super(pos, w, h);
        
        bd.position.set(pos.x/PPM,pos.y/PPM);
        bd.type = BodyType.DynamicBody;
        bd.linearDamping  = 8.0f;
        cshape.setRadius(width/PPM);
        fd.shape = cshape;
        userdata = "bullet_" + id;
        
        dv = dir;
        DURATION = duration;
        durFC = new FrameCounter(DURATION);
        
    }
     
    
    @Override
    public void init(World world){
        try {
            body = world.createBody(bd);
            body.createFixture(fd).setUserData(userdata);
            body.setUserData(userdata);

            durFC.start(fm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void update(){
        updatePos();
        
        if(durFC.complete){
            dispose();
        }
        
        super.update();
    }
    
    @Override
    public void alert(String [] str){
        dispose(); 
    }
    
    
    public void updatePos(){
        body.applyForceToCenter(dv, true);
    }
    
   
}
