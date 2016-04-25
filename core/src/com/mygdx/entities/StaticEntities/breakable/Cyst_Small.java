/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.StaticEntities.breakable;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.ImageSprite;
import com.mygdx.entities.pickups.Item_DarkMatter;
import static com.mygdx.game.MainGame.RATIO;

/**
 *
 * @author saynt
 */
public class Cyst_Small extends BreakableObject{

    public Cyst_Small(Vector2 pos) {
        super(pos, 20f*RATIO, 20f*RATIO);
        
        idleSprite = new ImageSprite("cyst-idle", true);
        idleSprite.sprite.setBounds(pos.x, pos.y, width*2, height*2);
        isprite = idleSprite;
        
        itemRewardPool.add(new Item_DarkMatter());
        
        MAX_HP = 6;
        CURRENT_HP = MAX_HP;
    }
}
