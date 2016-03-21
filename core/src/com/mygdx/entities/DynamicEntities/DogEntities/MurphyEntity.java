/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.DogEntities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.Entity;
import com.mygdx.entities.ImageSprite;
import com.mygdx.environments.tears.TearPortal;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import java.util.ArrayList;

/**
 *
 * @author looch
 */
public class MurphyEntity extends DogEntity{
    
    
    public MurphyEntity(Vector2 pos){
        super(pos,25f*RATIO, 25f*RATIO);
        
        userdata = "murphy";
        
        dogTexture = MainGame.am.get(ResourceManager.MURPHY_IDLE);
        alertTexture = MainGame.am.get(ResourceManager.MURPHY_ALERT);
        
        spriteScale = width / 72;
        
        moveSprite = new ImageSprite("murphy-move", true);
        moveSprite.sprite.setScale(spriteScale * RATIO);
        
        alertSprite = new ImageSprite("murphy-alert", true);
        alertSprite.sprite.setScale(0.65f* RATIO);
        
        idleSprite = new ImageSprite("murphy-idle", true);
        idleSprite.sprite.setScale(0.29f * RATIO);
        
        isprite = idleSprite;
    }
    
    @Override
    public TearPortal isNearTear(){
        
        
        Array<TearPortal> tears = new Array<TearPortal>();
        //Array<Entity> entities = EnvironmentManager.currentEnv.getEntities();
        ArrayList<Entity> entities = EnvironmentManager.currentEnv.getEntities();
        for(Entity e: entities){
            if(e.getUserData().toString().contains("bosst_")){
                try{
                    tears.add((TearPortal)e);
                }catch(Exception ex){
                    System.out.println("@DogEntity: Current tear is not of type <TearPortal>");
                }
            }
        }
        
        for(TearPortal tp: tears){
            //if(TEARRANGE > GameScreen.player.getBody().getPosition().dst(tp.getBody().getPosition())){
            if(TEARRANGE > findClosestBody().getPosition().dst(tp.getBody().getPosition())){
                isprite = alertSprite;
                isprite.sprite.rotate(-0.25f);
                //texture = alertTexture;
                //iw = alertWidth;
                //ih = alertHeight;
                return tp;
            }    
        }
                
        
        return null;
    }
    
    
}
