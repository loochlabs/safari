/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvVoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.environments.tears.TearPortal;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;

/**
 *
 * @author looch
 */
public class VoidMap {
    
    private final float x, y, width, height;
    private final Texture bg;
    
    private final VoidGrid grid;
    private final Array<TearPortal> tears = new Array<TearPortal>();
    private float ratiox, ratioy;
    private final Texture markerRed, markerYellow, startmark, endmark;
    private float markerw, markerh, startw, starth, endmark_x, endmark_y;
    
    private boolean enabled = true;
    
    public float getX() {return x;}
    public float getY() {return y;}
    public float getWidth() {return width;}
    public float getHeight() {return height;}
    public Array<TearPortal> getTears() { return tears; }
    public boolean isEnabled(){ return enabled; }
    
    public void setEnable(boolean b)    { this.enabled = b; }
    
    public void setEndPos(float x, float y) { endmark_x = x; endmark_y = y; }
    
    public VoidMap(float x, float y, float w, float h, VoidGrid grid){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.grid = grid;
        
        bg = MainGame.am.get(ResourceManager.VOID_MAP);
        
        ratiox = this.width / grid.getWidth();
        ratioy = this.height / grid.getHeight();
        
        markerRed = MainGame.am.get(ResourceManager.MAP_MARKER1);
        markerw = 20f*RATIO;
        markerh = 20f*RATIO;
         
        markerYellow = MainGame.am.get(ResourceManager.MAP_MARKER_YELLOW);
      
        
        startmark = MainGame.am.get(ResourceManager.MAP_STARTER);
        startw = 80f*RATIO;
        starth = 40f*RATIO;
        
        endmark = MainGame.am.get(ResourceManager.MAP_END);
        endmark_x = 0f;
        endmark_y = 0f;
        
    }
    
    public void render(SpriteBatch sb){
        if (enabled) {
            sb.draw(bg, x, y, width, height);

            //start mark
            sb.draw(startmark, x + width / 2, y + height / 2, startw, starth);

            //tear marks
            for (TearPortal tear : tears) {
                if (tear.isOpened()) {
                    if (tear.getUserData().toString().contains("bosst_")) {
                        sb.draw(
                                markerYellow,
                                x + tear.getPos().x * ratiox,
                                y + tear.getPos().y * ratioy,
                                markerw, markerh);//todo: map ratio, tear(x,y)f
                    } else {
                        sb.draw(
                                markerRed,
                                x + tear.getPos().x * ratiox,
                                y + tear.getPos().y * ratioy,
                                markerw, markerh);//todo: map ratio, tear(x,y)f
                    }

                }
            }

            sb.draw(endmark, x + endmark_x * ratiox, y + endmark_y * ratioy, startw, starth);

        }
    }
    
    
}
