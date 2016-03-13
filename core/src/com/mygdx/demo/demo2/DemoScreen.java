/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.demo.demo2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.Screen;

/**
 *
 * @author saynt
 */
public class DemoScreen extends Screen{

    private DemoScreenInputManager dsim;
    private Texture logo;
    private BitmapFont font; 
    
    //public static boolean flagForNewGame = false;
    
    @Override
    public void create() {
        
        //input mananger
        dsim = new DemoScreenInputManager();
        
        EnvironmentManager em = new EnvironmentManager();
        
        //logo
        logo = MainGame.am.get(ResourceManager.MENU_LOGO);
        
        font = new BitmapFont(Gdx.files.internal("fonts/nav-impact.fnt"));
        font.setColor(Color.WHITE);
    }

    private final String ANYKEY_TEXT = "Press any key to start";
    private final String TEXT_TWITTER = "@NullAndVoidGame";
    private final String TEXT_CALOOCH = "@TheCalooch";        
    
    @Override
    public void render(SpriteBatch sb) {
        
        dsim.update();
        //GameKeyLibrary.update();
        
        
        sb.begin();
        
        sb.draw(logo, MainGame.WIDTH * 0.16f * RATIO, MainGame.HEIGHT - 300f*RATIO, 900f*RATIO, 275f*RATIO);
        
        font.draw(sb, ANYKEY_TEXT, MainGame.WIDTH/2 - font.getBounds(ANYKEY_TEXT).width/2, MainGame.HEIGHT/2);
        
        font.draw(sb, TEXT_TWITTER, 0, font.getCapHeight()*2);
        font.draw(sb, TEXT_CALOOCH, 0, font.getCapHeight());
        
        sb.end();
        
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void dispose() {
        System.out.println("@DemoScreen dispose");
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void update(float dt) {
        
    }
}
