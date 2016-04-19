/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.GameStats;
import com.mygdx.utilities.ItemContainer;

/**
 *
 * @author looch
 */
public class InventoryUi extends OverlayComponent{

    private BitmapFont font;
    
    private Array<ItemContainer> pickups = new Array<ItemContainer>();
    private Array<ItemContainer> pickupsToRemove = new Array<ItemContainer>();
    
    public InventoryUi(float x, float y) {
        super(x + 10f*RATIO, y + 10f*RATIO, 25f*RATIO, 25f*RATIO);
        
        font = new BitmapFont(Gdx.files.internal("fonts/nav-impact.fnt"));
        font.setScale(0.55f * RATIO);
    }

    @Override
    public void update() {
        
        for(ItemContainer item : GameStats.inventory.getItems()){
            if(item.flagForHud && !pickups.contains(item, false))
                pickups.add(item);
        }
        
        for(ItemContainer p : pickups){
            if(!GameStats.inventory.hasItem(p.pickup))
                pickupsToRemove.add(p);
        }
        
        for(ItemContainer pi : pickupsToRemove){
            pickups.removeValue(pi, false);
        }
        
        pickupsToRemove.clear();
    }
    
    @Override
    public void render(SpriteBatch sb){
        
        //render pickup textre and count(use BitmapFont font)
        for(int i = 0; i < pickups.size; i++){
            sb.draw(pickups.get(i).pickup.getTexture(), x, y + i*35f*RATIO , 35f*RATIO, 35f*RATIO);
            font.draw(sb, " x " + pickups.get(i).count , 
                    x + 35f*RATIO + 10f*RATIO, 
                    y + i*35f*RATIO + 10f*RATIO + font.getCapHeight());
        }
    }
    
}
