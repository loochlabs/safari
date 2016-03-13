/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.StaticEntities.breakable;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.StaticEntities.StaticEntity;
import com.mygdx.entities.esprites.EntitySprite;
import com.mygdx.entities.pickups.Pickup;
import com.mygdx.environments.EnvironmentManager;
import static com.mygdx.utilities.UtilityVars.BIT_ATT;
import static com.mygdx.utilities.UtilityVars.BIT_EN;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class BreakableObject extends StaticEntity{

    protected EntitySprite idleSprite;
    
    protected final Array<Pickup> itemRewardPool = new Array<Pickup>();
  
    
    public BreakableObject(Vector2 pos) {
        super(pos, 40f, 40f);
        
        userdata = "en_" + id;
        bd.position.set(pos.x/PPM, pos.y/PPM);
        cshape.setRadius(width/PPM);
        fd.restitution = 0.8f;
        fd.filter.categoryBits = BIT_EN;
        fd.filter.maskBits = BIT_PLAYER | BIT_WALL | BIT_ATT | BIT_EN;
        fd.shape = cshape;
        
        MAX_HP = 20;
        CURRENT_HP = MAX_HP;
        
    }
    
    
    @Override
    public void death(){
        super.death();
        spawnReward();
        dispose();
    }
    
    public void spawnReward(){
        //spawn reward items
        
        /*
        for(int i = rng.nextInt((int)(itemRewardPool.size*0.5f))+(int)(itemRewardPool.size*0.5f); 
                i < itemRewardPool.size; i++){
            Vector2 iv = new Vector2(
                    body.getPosition().x*PPM + 25*rng.nextFloat()*rngNegSet.random(),
                    body.getPosition().y*PPM + 25*rng.nextFloat()*rngNegSet.random());
            Pickup item = itemRewardPool.random().cpy();
            item.setPosition(iv);
            Pickup p = (Pickup)EnvironmentManager.currentEnv.spawnEntity(item);
            p.spawnForce();
            
        }*/
        
        
        for(Pickup item: itemRewardPool){
            Vector2 iv = new Vector2(
                    body.getPosition().x*PPM + 25*rng.nextFloat()*rngNegSet.random(),
                    body.getPosition().y*PPM + 25*rng.nextFloat()*rngNegSet.random());
            item.setPosition(iv);
            Pickup p = (Pickup)EnvironmentManager.currentEnv.spawnEntity(item);
            p.spawnForce();
        }
    }
    
    public void addReward(Pickup p){
        itemRewardPool.add(p);
    }
    
    
}
