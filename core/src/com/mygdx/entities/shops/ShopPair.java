/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.shops;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.StaticEntities.StaticEntity;
import com.mygdx.environments.EnvironmentManager;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class ShopPair extends StaticEntity{

    private final ShopItem item;
    private final ShopTag tag;
    
    public ShopPair(Vector2 pos, float w, float h, ShopItem item) {
        super(pos, w, h);
        
        userdata = "ShopItem_" + id;
        bd.position.set(pos.x/PPM,pos.y/PPM);
        shape.setAsBox(width/PPM, height/PPM);
        fd.shape = shape;
        fd.isSensor = true;
        
        this.item = item;
        this.item.setPosition(new Vector2(pos.x, pos.y - height));
        tag = new ShopTag(pos, w, h, item);
    }
    
    @Override
    public void init(World world){
        super.init(world);
        
        EnvironmentManager.currentEnv.spawnEntity(item);
        EnvironmentManager.currentEnv.spawnEntity(tag);
    }

    
}
