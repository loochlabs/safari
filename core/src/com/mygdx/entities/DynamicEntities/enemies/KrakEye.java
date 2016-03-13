/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;
import com.mygdx.utilities.UtilityThread;

/**
 *
 * @author looch
 */

/*
public class KrakEye extends EnemyEntity{

    private Texture eyeOpenTexture, eyeClosedTexture;
    private boolean eyeOpen;
    private UtilityThread eyeThread = new UtilityThread();
    private long openTime = 10000;
    
    
    public KrakEye(Vector2 pos, float w, float h) {
        super(pos, w, h);
        
        bd.type = BodyDef.BodyType.StaticBody;
        
        MAX_HP = 200;
        CURRENT_HP = MAX_HP;
        expYield = 100;
        
        eyeOpenTexture = MainGame.am.get(ResourceManager.KRAK_EYE_OPEN);
        eyeClosedTexture = MainGame.am.get(ResourceManager.KRAK_EYE_CLOSED);
        
        texture = eyeClosedTexture;
    }
    
    @Override
    public void update(){
        super.update();
        
        if(eyeOpen && eyeThread.isComplete()){
            openEye();
        }
    }
    
    @Override
    public void damage(float damage){
        if(eyeOpen){
            super.damage(damage);
        }
    }
    
    @Override
    public void death(){
        //call super for now
        super.death();
        EnvironmentManager.currentEnv.removeEntity(this);
    }
    
    @Override 
    public void alert(){
        openEye();
    }
    
    public void openEye(){
        if(!eyeOpen){
            eyeOpen = true;
            texture = eyeOpenTexture;
            eyeThread.start(openTime);
        }else{
            eyeOpen = false;
            texture = eyeClosedTexture;
        }
    }
    
}
*/