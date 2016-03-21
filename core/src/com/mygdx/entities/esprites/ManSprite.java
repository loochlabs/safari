/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.esprites;

import com.mygdx.entities.ImageSprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.Entity;
import com.mygdx.environments.EnvVoid.EnvVoid;
import com.mygdx.environments.EnvironmentManager;
import static com.mygdx.game.MainGame.RATIO;
import static com.mygdx.utilities.UtilityVars.PPM;
import java.util.Random;

/**
 *
 * @author looch
 */
public class ManSprite extends Entity{

    private EnvVoid env;
    private ImageSprite shadowSprite, crouchSprite, warpSprite;
    private Array<ImageSprite> sprites = new Array<ImageSprite>();
    private Random rng = new Random();
    
    public ManSprite(Vector2 pos) {
        super(pos, 25f, 25f);
        
        bd.position.set(pos.x/PPM, pos.y/PPM);
        fd.isSensor = true;
        cshape.setRadius(width/PPM);
        fd.shape = cshape;
        userdata = "man_"+id;
        
        shadowSprite = new ImageSprite("mitb-shadow", true);
        shadowSprite.sprite.setScale(0.6f*RATIO);
        crouchSprite = new ImageSprite("man-crouch", true);
        crouchSprite.sprite.setScale(0.6f*RATIO);
        
        sprites.add(shadowSprite);
        sprites.add(crouchSprite);
        
        warpSprite = new ImageSprite("man-warp", false);
        warpSprite.sprite.setScale(0.6f);
        
        isprite = sprites.random();
    }

    @Override
    public void init(World world) {
        body = world.createBody(bd);
        body.createFixture(fd).setUserData(userdata);
        body.setUserData(userdata);
        
        env = (EnvVoid)EnvironmentManager.currentEnv;
    }
    
    @Override
    public void update(){
        
        if(inRangeOfPlayer()){
            warpSprite();
        }
        
    }
    
    
    private float PLAYER_RANGE = 5f * RATIO;
    
    private boolean inRangeOfPlayer(){
        float dist = body.getPosition().
                dst(findClosestBody().getPosition());
        return dist < PLAYER_RANGE;
    }
    
    //Gets clostest player body from current EnvVoid playerGhostBodies
    private Body findClosestBody(){
    
        Array<Body> bodies = env.getPlayerBodies();
        Body closestBody = bodies.peek();
        float dv = body.getPosition().dst(closestBody.getPosition());
        
        for(Body pbody: bodies){
            float dc = body.getPosition().dst(pbody.getPosition());
            if(dc < dv){
                closestBody = pbody;
                dv = dc;
            }
        }
        
        return closestBody;
        
    }
    
    
    private void warpSprite(){
        if(!warpSprite.isComplete()){
            isprite = warpSprite;
        }else{
        
            do {
                Vector2 dv = new Vector2(
                        env.getWidth() * rng.nextFloat() / PPM,
                        env.getHeight() * rng.nextFloat() / PPM);
                body.setTransform(dv, 0);
            } while (inRangeOfPlayer());
            
            warpSprite.reset();
            isprite = sprites.random();
        }
    }
}
