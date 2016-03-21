/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.ImageSprite;
import com.mygdx.entities.pickups.Pickup;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.GameStats;
import com.mygdx.managers.ResourceManager;
import com.mygdx.utilities.ItemContainer;

/**
 *
 * @author looch
 */
public class InventoryUi extends OverlayComponent{

    private ImageSprite dmIdleSprite;
    private Texture dmTexture;
    private int dm_count;
    private BitmapFont font;
    
    private Array<Pickup> pickups = new Array<Pickup>();
    private Array<Pickup> pickupsToRemove = new Array<Pickup>();
    
    public InventoryUi(float x, float y, float width, float height) {
        super(x, y, width, height);
        
        
        dmIdleSprite = new ImageSprite("hud-dm-idle", true);
        dmIdleSprite.sprite.setBounds(x, y, width, height);
        dmTexture = MainGame.am.get(ResourceManager.ITEM_DM1);
        font = new BitmapFont(Gdx.files.internal("fonts/nav-impact.fnt"));
        font.setScale(0.7f * RATIO);
    }

    @Override
    public void update() {
        
        dm_count = 
                GameStats.inventory.getItemByName("Dark Matter") == null ? 0 : 
                GameStats.inventory.getItemByName("Dark Matter").count;
        
        for(ItemContainer item : GameStats.inventory.getItems()){
            if(item.flagForHud && !pickups.contains(item.pickup, false))
                pickups.add(item.pickup);
        }
        
        for(Pickup p : pickups){
            if(!GameStats.inventory.hasItem(p))
                pickupsToRemove.add(p);
        }
        
        for(Pickup pi : pickupsToRemove){
            pickups.removeValue(pi, false);
        }
        
        pickupsToRemove.clear();
    }
    
    @Override
    public void render(SpriteBatch sb){
        if (dm_count > 0) {
            dmIdleSprite.render(sb);
            font.draw(sb, "" + dm_count + "", x + width / 2 - font.getBounds("" + dm_count + "").width / 2, y + height * 0.25f + font.getCapHeight());
        }
        for(int i = 0; i < pickups.size; i++){
            sb.draw(pickups.get(i).getTexture(), x + width + 50f*RATIO*i, y, 50f*RATIO, 50f*RATIO);
        }
    }
    
}
