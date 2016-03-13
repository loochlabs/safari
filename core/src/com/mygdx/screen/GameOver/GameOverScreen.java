/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.screen.GameOver;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;
import com.mygdx.managers.SoundManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.screen.Screen;

/**
 *
 * @author looch
 */
public class GameOverScreen extends Screen{

    private Texture go_text;
    private GoInputManager im;
    //private PlayerInputManager pim;
    
    @Override
    public void create() {
        
        System.out.println("@GameOverScreen game over");
     
        im = new GoInputManager();
        
        go_text = MainGame.am.get(ResourceManager.GO_TEXT);
        
        //stop all music
        SoundManager.clear();
        
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void render(SpriteBatch sb) {
        
        im.update();
        
        sb.begin();
        sb.draw(go_text, MainGame.WIDTH/2, MainGame.HEIGHT/2);
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
