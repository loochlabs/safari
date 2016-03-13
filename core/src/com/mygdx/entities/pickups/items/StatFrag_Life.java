/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.pickups.items;

import com.mygdx.entities.pickups.Pickup_Item;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.pickups.Pickup;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.gui.descriptions.DescriptionWindow;

/**
 *
 * @author looch
 */
public class StatFrag_Life extends Pickup_Item{

    public StatFrag_Life(){
        this(new Vector2(0,0));
    }
    
    public StatFrag_Life(Vector2 pos) {
        super(pos, 20f*RATIO, 20f*RATIO);
        
        //texture = MainGame.am.get(ResourceManager.FRAGMENT_LIFE);
        name = "Life Fragment";
        desc = "Effect: restores life";
        descWindow = new DescriptionWindow(""+name+"",desc, type);
        
        //itemSkill = new Skill_Item_LifeFrag(this);
        
    }
    
    @Override 
    public void effect(){
        //itemSkill.effect();
    }
    
    @Override
    public Pickup cpy(){
        return new StatFrag_Life();
    }
    
}
