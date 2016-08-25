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
import com.mygdx.entities.ImageSprite;
import com.mygdx.game.MainGame;
import com.mygdx.managers.FrameManager;
import com.mygdx.managers.ResourceManager;
import com.mygdx.utilities.FrameCounter;

/**
 *
 * @author looch
 */
public class Overlay {
    
    public static boolean enable = true;
    private Texture debugGrid;
    
    //title alter for environment name
    private String titleText = "";
    private final BitmapFont titleFont;
    private final FrameManager fm = new FrameManager();
    private final float titleTime = 5f; 
    private float titleAlpha = 0;
    private final FrameCounter titleFC = new FrameCounter(titleTime);
    
    //transition sprite
    //Using old endSpectralSprite from player death
    private final ImageSprite transEndSprite;
    private final ImageSprite transBeginSprite;
    private boolean beginEndTransState = true, transitioning = false;
    
    
    public Overlay(){
        debugGrid = MainGame.am.get(ResourceManager.UI_GRID);
        
        //title alert fonts
        titleFont = new BitmapFont(Gdx.files.internal("fonts/nav-impact.fnt"));
        titleFont.setColor(Color.WHITE);
        titleFont.setScale(1.7f);
        
        //transtion sprite
        transEndSprite = new ImageSprite("endSpectralSprite", false);
        transEndSprite.sprite.setBounds(0, 0, MainGame.WIDTH, MainGame.HEIGHT);
        transEndSprite.reset();
        
        transBeginSprite = new ImageSprite("endSpectralSprite", false, true, false, false, 0,0, 1.0f, true);
        transBeginSprite.sprite.setBounds(0, 0, MainGame.WIDTH, MainGame.HEIGHT);
        transBeginSprite.reset();
        
    }
    
    
    public void update(){
        fm.update();
    }
    
    public void render(SpriteBatch sb){
        if(enable){
            if (MainGame.debugmode) {
                sb.draw(debugGrid, 0, 0, MainGame.WIDTH, MainGame.HEIGHT);
            }
            
            renderTitleAlert(sb);
            renderTransion(sb);
        }
    }
       
    public void addTitleAlert(String str){
        titleText = str;
        titleFC.start(fm);
        titleAlpha = 0.01f;
    }
    
    private void renderTitleAlert(SpriteBatch sb) {
        if (!titleText.equals("")) {

            if (titleFC.complete) {
                titleText = "";
            }

            //bring alpha 0 -> 100 -> 0
            titleAlpha = titleAlpha <= 0 ? 0
                    : titleFC.getTimeRemaining() > titleTime * 0.9f ? titleAlpha + 0.025f
                            : titleFC.getTimeRemaining() < titleTime * 0.3f && titleFC.getTimeRemaining() >= 0 ? titleAlpha - 0.025f
                                    : 1;

            titleAlpha = titleAlpha <= 0 ? 0 : titleAlpha;

            titleFont.setColor(1, 1, 1, titleAlpha);
            titleFont.draw(sb, titleText,
                    MainGame.WIDTH / 2 - titleFont.getBounds(titleText).width / 2,
                    MainGame.HEIGHT * 0.965f);

        }
    }
    
    //handle transSprite during trasition 
    //@param - transState
    //    -true = beginning transition
    //    -false = ending transition
    //
    //@return - is transition complete?
    //    -true = transSprite has completed
    //
    //Called from Environment 
    
    public boolean transition(boolean transState) {
        beginEndTransState = transState;

        if (transState) {
            transitioning = !transBeginSprite.isComplete();
            transBeginSprite.step();
        } else {
            transitioning = !transEndSprite.isComplete();
            transEndSprite.step();
        }

        return !transitioning;
    }
    
    public void endTransition() {
        transitioning = false;
        transBeginSprite.reset();
        transEndSprite.reset();
    }
    
    private void renderTransion(SpriteBatch sb) {
        if (transitioning) {
            if (beginEndTransState) {
                //begin trans
                transBeginSprite.render(sb);
            } else {
                //end trans
                transEndSprite.render(sb);
            }
        }
    }

}
