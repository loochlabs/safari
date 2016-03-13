/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.demo.demo2;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.entities.esprites.EntitySprite;
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
public class DemoContScreen extends Screen{

    private DemoGoInputManager im;
    private EntitySprite contSprite;
    private FrameCounter endFC = new FrameCounter(15f);
    private FrameManager fm = new FrameManager();
    
    @Override
    public void create() {
        
        System.out.println("@DemoGameOverScreen game over");
     
        im = new DemoGoInputManager();
        
        
        //stop all music
        SoundManager.clear();
        
        //death anim
        contSprite = new EntitySprite("end-cont", false, true, false, false);
        contSprite.sprite.setBounds(
                MainGame.WIDTH/2 - 500f*RATIO, 
                MainGame.HEIGHT/2 , 
                990f*RATIO, 136f*RATIO);
        
        endFC.start(fm);
        
        GameKeyLibrary.clear();
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void render(SpriteBatch sb) {
        fm.update();
        sb.begin();

        contSprite.render(sb);
        
        im.update();
        
        if(endFC.complete){
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
