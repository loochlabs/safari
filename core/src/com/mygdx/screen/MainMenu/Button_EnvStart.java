/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.screen.MainMenu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.screen.GameScreen;
import com.mygdx.screen.ScreenManager;

/**
 *
 * @author looch
 */
public class Button_EnvStart extends MenuButton{

        private final int id;
        private final String name;

    public Button_EnvStart(float x, float y, float width, float height, int id, String name) {
        super(x, y, width, height);

        this.id = id;
        this.name = name;
    }

    @Override
    public void execute() {

        ScreenManager.setScreen(new GameScreen(id));

    }
    
    @Override
    public void render(SpriteBatch sb){
        super.render(sb);
        
        font.draw(sb, name, x, y + font.getCapHeight());
    }


    
}
