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
import com.mygdx.entities.DynamicEntities.enemies.EnemyManager;
import com.mygdx.entities.DynamicEntities.enemies.Enemy_TestMon;
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
    private boolean beginEndTransState = true, transitioning = false;
    
    private final TrackingUI trackingUI = new TrackingUI();
    
    public Overlay(){
        debugGrid = MainGame.am.get(ResourceManager.UI_GRID);
        
        //title alert fonts
        titleFont = new BitmapFont(Gdx.files.internal("fonts/nav-impact.fnt"));
        titleFont.setColor(Color.WHITE);
        titleFont.setScale(1.7f);  
        
    }
    
    
    public void update(){
        fm.update();
        trackingUI.update();
    }
    
    public void render(SpriteBatch sb){
        if(enable){
            if (MainGame.debugmode) {
                sb.draw(debugGrid, 0, 0, MainGame.WIDTH, MainGame.HEIGHT);
            }
            
            renderTitleAlert(sb);
            renderTransion(sb);
            trackingUI.render(sb);
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
            transitioning = false;
        } else {
            transitioning = false;
        }

        return !transitioning;
    }
    
    public void endTransition() {
        transitioning = false;
    }
    
    private void renderTransion(SpriteBatch sb) {
        if (transitioning) {
            if (beginEndTransState) {
                //begin trans
                //transBeginSprite.render(sb);
            } else {
                //end trans
                //transEndSprite.render(sb);
            }
        }
    }
    
    
    //Tracking UI component for viewing distance between player and existing enemies
    
    private class TrackingUI{
        
        private final float DISTANCE_1 = 1000f;
        private final float DISTANCE_2 = 2000f;
        private final float DISTANCE_3 = 3000f;
        private final float DISTANCE_4 = 4000f;
        private final float DISTANCE_5 = 5000f;
        private int distanceMarker = 0;
        
        public TrackingUI(){}
        
        public void update(){
        }
        
        public void render(SpriteBatch sb){
            int count = 1;
            for(Enemy_TestMon e : EnemyManager.enemies){
                distanceMarker = e.distance() > DISTANCE_5 ? 5 : 
                        e.distance() <= DISTANCE_5 && e.distance() > DISTANCE_4 ? 4 :
                        e.distance() <= DISTANCE_4 && e.distance() > DISTANCE_3 ? 3 : 
                        e.distance() <= DISTANCE_3 && e.distance() > DISTANCE_2 ? 2 :
                        e.distance() <= DISTANCE_2 && e.distance() > DISTANCE_1 ? 1 :
                        0;
                if(distanceMarker >= 3) continue;
                
                sb.draw(e.getIconTexture(), 0,10 + titleFont.getCapHeight()*(count-1), 40,40);
                titleFont.draw(sb, "" + distanceMarker + "" ,
                    50, titleFont.getCapHeight()*count++);
            }
        }
        
    }

}
