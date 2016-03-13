/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvSub.pads;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.pickups.Pickup;
import com.mygdx.entities.pickups.Pickup_Item;
import static com.mygdx.game.MainGame.RATIO;

/**
 *
 * @author looch
 */
public class EndPiece extends Pickup_Item{

    public EndPiece(){
        this(new Vector2(0,0));
    }
    
    public EndPiece(Vector2 pos) {
        super(pos, 25f * RATIO, 25f*RATIO);
        
        flagForHud = true;
    }

    @Override
    public Pickup cpy() {
        return new EndPiece(pos);
    }
    
}
