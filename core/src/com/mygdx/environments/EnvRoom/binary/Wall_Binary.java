/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvRoom.binary;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.StaticEntities.NullWall;
import com.mygdx.entities.ImageSprite;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;

/**
 *
 * @author looch
 */
public class Wall_Binary extends NullWall{

    private boolean locked = true;
    private final int LOCK_CODE; 
    private final int lockRange = 254;
    
    private ImageSprite closedSprite, openSprite;
    
    //hint sprite
    private CodeHintSprite codeHintSprite;
    
    public int getLockCode() { return LOCK_CODE; }
    public boolean isLocked() { return locked; }
    public CodeHintSprite getHintSprite() { return codeHintSprite; }
    
    public Wall_Binary(Vector2 pos, float w, float h) {
        super(pos, w, h);
        
        //texture = MainGame.am.get(ResourceManager.ROOM_BIN_WALL);
        
        closedSprite = new ImageSprite("binWall-closed", false);
        closedSprite.sprite.setBounds(pos.x, pos.y, width*2, height*2);
        
        openSprite = new ImageSprite("binWall-open", false);
        openSprite.sprite.setBounds(pos.x, pos.y, width*2, height*2);
        
        isprite = closedSprite;
        
        LOCK_CODE = rng.nextInt(lockRange + 1);
        System.out.println("@Wall_Binary LOCK_CODE : " + LOCK_CODE );
        
        //codeHintSprite = new CodeHintSprite(LOCK_CODE, 0, 0);
        
        //create hint sprite in void
    }
    
    @Override
    public void update(){
        super.update();
        
        if(!locked){
            isprite = openSprite;
            
            if(openSprite.isComplete()){
                destroy();
            }
        }
    }
    
    
    //called when key is used within range of lock
    public void openDoor(){
        
        if(locked){//temp code, needs animation
            locked = false;
            //destroy();
        }
        
    }
    
    public void destroy(){
        EnvironmentManager.currentEnv.removeEntity(this);
    }
    
}
