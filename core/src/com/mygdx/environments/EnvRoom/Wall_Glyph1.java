/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvRoom;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.ImageSprite;
import com.mygdx.entities.pickups.Item_GlyphOne;
import static com.mygdx.game.MainGame.RATIO;

/**
 *
 * @author looch
 */
public class Wall_Glyph1 extends Wall_Lock{

    
    public Wall_Glyph1(Vector2 pos, float w, float h) {
        super(pos, w, h);
        
        openSprite = new ImageSprite("wall1_open", false);
        openSprite.sprite.setScale(0.85f * RATIO);
        
        isprite = new ImageSprite("wall1_closed",false);
        isprite.sprite.setScale(0.85f * RATIO);
        
        key =  new Item_GlyphOne(new Vector2(0,0), 35f*RATIO , 35f*RATIO, this);
    }
    
    
}
