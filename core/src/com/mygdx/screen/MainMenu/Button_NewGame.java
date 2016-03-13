/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.screen.MainMenu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.environments.Environment;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;

/**
 *
 * @author looch
 */
public class Button_NewGame extends MenuButton{

    private Array<Environment> envs = new Array<Environment>();
    
    public Button_NewGame(float x, float y, float width, float height){
        super(x,y,width,height);
        
        //set texture
        texture = MainGame.am.get(ResourceManager.MENU_NEW_GAME);
    }
    

    @Override
    public void execute() {
        //begin new game
        //create new GameScreen()
        //set ScreenManager currentScreen
        
        /*
        envs = EnvironmentManager.START_ENVS;
        
        for(int i = 0; i < envs.size; i++){
            button_options.add(new Button_EnvStart(x - width, y - height*i, width, height, i, envs.get(i).toString()));
        }*/
    }
    
    @Override
    public void render(SpriteBatch sb){
        super.render(sb);
        
        for(MenuButton b : button_options){
            b.render(sb);
        }
    }
    
}
