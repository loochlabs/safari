/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.pickups;

import com.badlogic.gdx.math.Vector2;
import static com.mygdx.combat.skills.Skill.SkillType.NONE;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.gui.descriptions.DescriptionWindow;
import com.mygdx.managers.ResourceManager;

/**
 *
 * @author looch
 */
public class Item_DarkMatter extends Pickup_Item{

    public Item_DarkMatter(){
        this(new Vector2(0,0));
    }
    
    public Item_DarkMatter(Vector2 pos) {
        super(pos, 20f*RATIO, 20f*RATIO);
        
        texture = MainGame.am.get(ResourceManager.ITEM_DM1);
        name = "Dark Matter";
    }
    
    @Override
    public Pickup cpy(){
        return new Item_DarkMatter(pos);
    }
}
