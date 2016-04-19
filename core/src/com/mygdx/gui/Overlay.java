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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.combat.skills.Skill;
import com.mygdx.entities.ImageSprite;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.FrameManager;
import com.mygdx.managers.ResourceManager;
import com.mygdx.utilities.FrameCounter;

/**
 *
 * @author looch
 */
public class Overlay {
    
    public static boolean enable = true;
    
    private float width, height;
    
    
    private final BarHud barHud;
    
    private final SkillHud skillHud;
    public boolean skillHudEnable = false;
    
    private Texture debugGrid;
    
    //alerts on bottom of hud
    private final BitmapFont alertFont;
    private final Array<String> alertTexts = new Array<String>();
    private final Array<Long> alertTextTimes = new Array<Long>();
    private final Array<String> alertTextToRemove = new Array<String>();
    private final Array<Vector2> alertPoses = new Array<Vector2>();
    private final Vector2 alertPos;
    private final long ALERT_TIME = 60;//counted by # of frames, todo: use frameCounter
    
    //title alter for environment
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
    
    public BarHud getBarHud() { return barHud; }
    
    public Overlay(){
        
        this.width = MainGame.WIDTH;
        this.height = MainGame.HEIGHT;
        
        
        //skillHud = new SkillHud(width/2, 0 , 388 * RATIO, 83 * RATIO);
        barHud = new BarHud(0, 0 , 388f * RATIO, 83f * RATIO);
        
        skillHud = new SkillHud();
        
        debugGrid = MainGame.am.get(ResourceManager.OVERLAY_GRID);
        
        alertFont = new BitmapFont(Gdx.files.internal("fonts/nav-impact.fnt"));
        alertFont.setColor(Color.WHITE);
        alertFont.setScale(0.65f);
        //alertPos = new Vector2(MainGame.WIDTH * 0.99f, MainGame.HEIGHT * 0.01f + 50f*RATIO);
        alertPos = new Vector2(MainGame.WIDTH * 0.01f, MainGame.HEIGHT * 0.99f - 50f*RATIO);
        
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
        barHud.update();
        
        if(skillHudEnable){
            skillHud.update();
        }
        
        /*
        if(comboBar != null){
            comboBar.update();
            
            if(comboBar.complete){
                removeComboBar();
            }
        }*/
        
        fm.update();
    }
    
    public void render(SpriteBatch sb){
        if(enable){
            
            barHud.render(sb);
            
            /*
            if(comboBar != null){
                comboBar.render(sb);
            }*/
            
            
            for (int i = 0; i < alertTexts.size; i++) {
                alertFont.draw(sb, alertTexts.get(i), alertPoses.get(i).x, alertPoses.get(i).y + alertFont.getCapHeight());
                alertTextTimes.set(i, alertTextTimes.get(i) - 1);

                if (alertTextTimes.get(i) <= 0) {
                    alertTextToRemove.add(alertTexts.get(i));
                }
            }

            clearAlerts();

            if (MainGame.debugmode) {
                sb.draw(debugGrid, 0, 0, MainGame.WIDTH, MainGame.HEIGHT);
            }
            
            renderTitleAlert(sb);
            
            
            //Skill HUd
            if(skillHudEnable){
                skillHud.render(sb);
            }
            
            
            renderTransion(sb);
        }
    }
        
    public void addAlertText(String string){
        alertTexts.add(string);
        alertTextTimes.add(ALERT_TIME);
        
        //alert pos
        for(Vector2 pos: alertPoses){
            pos.add(0, -alertFont.getCapHeight());
        }
        
        //float len = alertFont.getBounds(string).width;
        //alertPoses.add(alertPos.cpy().sub(len, 0));
        alertPoses.add(alertPos.cpy());
    }
    
    public void clearAlerts(){
        for (String text : alertTextToRemove) {
            alertTexts.removeValue(text, false);
            alertTextTimes.removeIndex(0);
            alertPoses.removeIndex(0);
        }
        alertTextToRemove.clear();
    }
    
    public void addTitleAlert(String str){
        titleText = str;
        titleFC.start(fm);
        titleAlpha = 0.01f;
    }
    
    private void renderTitleAlert(SpriteBatch sb){
        if( !titleText.equals("")){
                
                if(titleFC.complete)    titleText = "";
                
                //bring alpha 0 -> 100 -> 0
                
                titleAlpha = titleAlpha <= 0 ? 0
                        : titleFC.getTimeRemaining() > titleTime*0.9f ? titleAlpha + 0.025f 
                        : titleFC.getTimeRemaining() < titleTime*0.3f && titleFC.getTimeRemaining() >= 0 ? titleAlpha - 0.025f
                        : 1;
                
                titleAlpha = titleAlpha <= 0 ? 0 : titleAlpha;
                
                titleFont.setColor(1, 1, 1, titleAlpha);
                titleFont.draw(sb, titleText, 
                        MainGame.WIDTH/2 - titleFont.getBounds(titleText).width/2, 
                        MainGame.HEIGHT*0.965f);
                
                
        }
    }
    
    //public void resetSkillSlot(int n){
        //barHud.resetSlot(n);
    //}
    
    public void enableSkillHud(int index){
        skillHud.enable();
        skillHud.rotateCursor(index);
        skillHudEnable = true;
    }
    
    public void disableSkillHud(){
        skillHudEnable = false;
    }
    
    
    
    
    public void addDescAlert(Skill skill){
        barHud.addDescAlert(skill);
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
    
    private void renderTransion(SpriteBatch sb){
        if(transitioning){
            if(beginEndTransState){
                //begin trans
                transBeginSprite.render(sb);
            }else{
                //end trans
                transEndSprite.render(sb);
            }
        }
    }
    
}
