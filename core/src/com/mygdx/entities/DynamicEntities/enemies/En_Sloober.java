/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.ImageSprite;
import static com.mygdx.game.MainGame.RATIO;

/**
 *
 * @author looch
 */
public class En_Sloober extends En_Goober{

    public En_Sloober(Vector2 pos) {
        super(pos);
        
        float sscale = 0.38f*RATIO;
        
        moveSprite = new ImageSprite("sloober-move", true);
        moveSprite.sprite.setScale(sscale);
        prepSprite = new ImageSprite("sloober-prep", true);
        prepSprite.sprite.setScale(sscale);
        attackSprite = new ImageSprite("sloober-att", true);
        attackSprite.sprite.setScale(sscale);
        bodyDamageSprite = new ImageSprite("sloober-dmg", true);
        bodyDamageSprite.sprite.setScale(sscale);
        
        isprite = moveSprite;
        
    }
    
    @Override
    public void init(World world){
        super.init(world);
        body.setTransform(body.getPosition(), (float)(45f/180f * Math.PI));
    }
    
}
