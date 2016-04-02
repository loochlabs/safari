/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.demo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.entities.ImageSprite;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.FrameManager;
import com.mygdx.managers.GameKeyLibrary;
import com.mygdx.managers.ResourceManager;
import com.mygdx.managers.SoundManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.screen.Screen;
import com.mygdx.screen.ScreenManager;
import com.mygdx.utilities.FrameCounter;

/**
 *
 * @author saynt
 */
public class DemoGameOverScreen extends Screen{

    private Texture go_text;
    private DemoGoInputManager im;
    private ImageSprite deathSprite1, deathSprite2;
    private FrameCounter deathFC = new FrameCounter(15f);
    private FrameManager fm = new FrameManager();
    
    @Override
    public void create() {
        
        System.out.println("@DemoGameOverScreen game over");
     
        im = new DemoGoInputManager();
        
        go_text = MainGame.am.get(ResourceManager.GO_TEXT);
        
        //stop all music
        SoundManager.clear();
        
        //death anim
        deathSprite1 = new ImageSprite("poeSpectral", false, true, false, false);
        deathSprite1.sprite.setBounds(
                MainGame.WIDTH/2 -160f*RATIO, 
                MainGame.HEIGHT/2 - 285f*RATIO, 
                310f*RATIO, 300f*RATIO);
        
        deathSprite2 = new ImageSprite("poe-death", false, true, false, false);
        deathSprite2.sprite.setBounds(
                MainGame.WIDTH/2 - 300f*RATIO, 
                MainGame.HEIGHT/2 - 250f*RATIO, 
                600f*RATIO, 500f*RATIO);
        
        deathFC.start(fm);
        
        GameKeyLibrary.clear();
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void render(SpriteBatch sb) {
        fm.update();
        sb.begin();
        
        im.update();
        
        //render poe death anim
        /*
        if(!deathSprite1.isComplete()){
            deathSprite1.render(sb);
        }
        else if (!deathSprite2.isComplete() || deathFC.running) {
            deathSprite2.render(sb);
        } else {
            
            sb.draw(go_text, MainGame.WIDTH / 2 - 150f * RATIO, MainGame.HEIGHT / 2);
        }
        */
        
        if(!deathSprite1.isComplete()){
            deathSprite1.render(sb);
        }
        else {
            deathSprite2.render(sb);
        } 
        
        if(deathSprite2.isComplete() && deathFC.complete){
            ScreenManager.setScreen(new DemoScreen());
        }
        
        
        
        sb.end();
        
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void dispose() {
        EnvironmentManager.clearEnvs();
        GameScreen.player = null;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
    
}