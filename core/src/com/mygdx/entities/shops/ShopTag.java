/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.shops;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.StaticEntities.StaticEntity;
import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 * 
 * Description: price tag for Shop item for purchase
 * 
 */
public class ShopTag  extends StaticEntity{

    //the item being purchased
    private final ShopItem ITEM;
    
    //private final Texture bg;
    private final BitmapFont font = new BitmapFont();
    
    public ShopTag(Vector2 pos, float w, float h, ShopItem item) {
        super(pos, w, h);
        
        userdata = "shoptag" + id;
        bd.position.set(pos.x/PPM,pos.y/PPM);
        shape.setAsBox(width/PPM, height/PPM);
        fd.shape = shape;
        fd.isSensor = true;
        
        this.ITEM = item;
        
        font.setColor(Color.WHITE);
        font.setScale(3.0f);
    }
    
    @Override
    public void render(SpriteBatch sb){
        if(ITEM != null){
            //sb.draw(bg, pos.x - width, pos.y, width * 2, height);
            font.draw(sb, "" + ITEM.getCost() + "", pos.x - width * 0.8f, pos.y + height * 0.2f + font.getCapHeight());
            sb.draw(ITEM.getCostItem().getTexture(), pos.x, pos.y, width * 0.9f, width * 0.9f);
        }
    }
    
    
}
