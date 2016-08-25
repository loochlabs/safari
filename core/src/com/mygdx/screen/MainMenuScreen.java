/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.camera.OrthoCamera;
import com.mygdx.managers.GameKeyLibrary;

/**
 *
 * @author looch
 * 
 */
public class MainMenuScreen extends Screen{

    private OrthoCamera camera;
    private MenuInputManager mim;
    
    @Override
    public void create() {
        mim = new MenuInputManager(this);
    }

    public void start(){
        System.out.println("@MainMenuScreen Creating new GameScreen...");
        ScreenManager.setScreen(new GameScreen());
    }
    
    @Override
    public void update(float dt) {
    }

    @Override
    public void render(SpriteBatch sb) {
        
        camera.update();
        mim.update();
        
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
    
    
    private class MenuInputManager {

        private MainMenuScreen screen;

        public MenuInputManager(MainMenuScreen screen) {
            this.screen = screen;
        }

        public void update() {
            if (GameKeyLibrary.anyKeyDown()) {
                screen.start();
            }
        }
    }
}
