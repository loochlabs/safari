/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.screen.MainMenu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.camera.OrthoCamera;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.Screen;

/**
 *
 * @author looch
 * 
 * 
 * TODO: 
 *  -Adjust UI for Brick City Demo
 *  -Create single option for demo start
 * 
 */
public class MainMenuScreen extends Screen{

    private OrthoCamera camera;
    private Overlay_MainMenu mainMenu;
    private MenuInputManager mim;
    
    private Texture logo;
    
    //public static boolean flagForNewGame = false;
    
    @Override
    public void create() {
        
        //main camera
        camera = new OrthoCamera();
        
        //create new Overlay_MainMenu
        mainMenu = new Overlay_MainMenu();
        
        //input mananger
        //Gdx.input.setInputProcessor(new InputProcessor_Keyboard());
        mim = new MenuInputManager(mainMenu);
        
        //todo: declartion needed here?
        EnvironmentManager em = new EnvironmentManager();
        
        //logo
        logo = MainGame.am.get(ResourceManager.MENU_LOGO);
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void render(SpriteBatch sb) {
        
        mainMenu.update();
        camera.update();
        mim.update();
        //GameKeyLibrary.update();
        
        
        sb.begin();
        mainMenu.render(sb);
        
        sb.draw(logo, 0, MainGame.HEIGHT - 154f*RATIO, 515f*RATIO, 154f*RATIO);
        sb.end();
        
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void dispose() {
        System.out.println("@MainMenuScreen dispose");
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
    
}
