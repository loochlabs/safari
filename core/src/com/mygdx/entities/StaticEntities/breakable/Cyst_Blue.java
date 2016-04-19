/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.StaticEntities.breakable;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.ImageSprite;
import com.mygdx.entities.pickups.Pickup_Life;
import com.mygdx.entities.pickups.Pickup_SoulPiece;
import com.mygdx.entities.pickups.Item_DarkMatter;
import com.mygdx.entities.pickups.Pickup_Energy;
import static com.mygdx.game.MainGame.RATIO;

/**
 *
 * @author looch
 */
public class Cyst_Blue extends BreakableObject{

    public Cyst_Blue(Vector2 pos) {
        super(pos);
        
        idleSprite = new ImageSprite("cyst-idle", true);
        idleSprite.sprite.setScale(0.7f * RATIO);
        isprite = idleSprite;
        
        itemRewardPool.add(new Pickup_SoulPiece());
        itemRewardPool.add(new Item_DarkMatter());
        itemRewardPool.add(new Item_DarkMatter());
        itemRewardPool.add(new Item_DarkMatter());
        itemRewardPool.add(new Pickup_Life());
        itemRewardPool.add(new Pickup_Energy());
        //itemRewardPool.add(new Pickup_Life());
    }
    
}
