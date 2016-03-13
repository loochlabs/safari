/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.combat.Buff;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author looch
 */
public class BuffUi extends OverlayComponent{

    private Array<Buff> buffs = new Array<Buff>();
    private final HashMap<Class, Integer> buffMap = new HashMap<Class, Integer>();
    private final HashMap<Class,Texture> textureMap = new HashMap<Class,Texture>();
    //private final Texture countbg;
    private final BitmapFont font;
    
    public BuffUi(float x, float y, float width, float height) {
        super(x, y, width, height);
        
        //countbg = MainGame.am.get(ResourceManager.COUNT_BG);
        
        font = new BitmapFont(Gdx.files.internal("fonts/nav-impact.fnt"));
        font.setScale(0.35f * RATIO);
        font.setColor(Color.WHITE);
    }

    @Override
    public void update() {
        
        //todo: need a one time call to update this ui
        
        if(!buffs.equals(GameScreen.player.getBuffs()))
            buffs = GameScreen.player.getBuffs();
    }
    
    @Override
    public void render(SpriteBatch sb){
        
        
        int count = 0;
        for(Buff buff: buffs){
            
            
            /*
            if(buffMap.isEmpty() || !buffMap.containsKey(buff.getClass())){
                buffMap.put(buff.getClass(), 1);
                textureMap.put(buff.getClass(), buff.getTexture());
            }else{
                count = buffMap.get(buff.getClass());
                buffMap.replace(
                        buff.getClass(), 
                        count+1);
            }*/
            
           
            
        }
        
        Iterator it = buffMap.entrySet().iterator();
        int index = 0;
        while (it.hasNext()) {
            
            /*
            sb.draw(countbg, 
                    x + width*index, 
                    y,
                    20f*RATIO,
                    20f*RATIO);*/
            
            HashMap.Entry pair = (HashMap.Entry)it.next();
            
            //it.remove(); // avoids a ConcurrentModificationException
            
            sb.draw(textureMap.get(pair.getKey()), x + index*width, y, width, height);
            
            if(!pair.getValue().equals(1)) 
                font.draw(sb, "" + pair.getValue() + "", x + index*width , y + font.getCapHeight());
            
            
            index++;
        }
        
        buffMap.clear();
        
    }
    
}
