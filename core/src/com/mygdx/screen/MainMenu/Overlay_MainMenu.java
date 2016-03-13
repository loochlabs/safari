/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.screen.MainMenu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.utilities.Direction;

/**
 *
 * @author looch
 */
public class Overlay_MainMenu {
    
    //Overlay components
    //BUTTON: new game
    //BUTTON: resume
    //BUTTON: options
    
    private Array<MenuButton> buttons = new Array<MenuButton>();
    private Array<MenuButton> currentButtons = new Array<MenuButton>();
    
    private Button_NewGame newGameButton;
    private Button_ResumeGame resumeButton;
    private Button_Options optionsButton;
    private MenuHighlight highlight;
    //private Texture selectBg;
    
    private Vector2 renderPos;
    private int xSelection;
    private int ySelection_MAX, ySelection_CUR;
    private float highlightX, highlightY;
    private final float BUTTON_HEIGHT = 50 * RATIO, BUTTON_WIDTH = 300f * RATIO;
    
    //private Array<Environment> startEnvs;
    
    
    public Overlay_MainMenu(){
        
        renderPos = new Vector2(MainGame.WIDTH - BUTTON_WIDTH, MainGame.HEIGHT - BUTTON_HEIGHT);
        
        newGameButton = new Button_NewGame(
                renderPos.x,
                renderPos.y,
                BUTTON_WIDTH,
                BUTTON_HEIGHT);
        
        buttons.add(newGameButton);
        
        currentButtons = buttons;
        
        ySelection_CUR = currentButtons.size - 1;
        ySelection_CUR = 0;
        xSelection = 0;
        
        
        highlight = new MenuHighlight(
                renderPos.x - 50f*RATIO ,
                renderPos.y,
                50f * RATIO, BUTTON_HEIGHT);
        
        
        //selectBg = MainGame.am.get(ResourceManager.MENU_BG);
        
        
        
    }
    
    public void update(){}
    
    public void render(SpriteBatch sb){
        
        for(MenuButton b : buttons){
            b.render(sb);
        }
        
        
        //highlight rendering
        highlight.setX(renderPos.x - highlight.getWidth() - BUTTON_WIDTH*(xSelection));
        highlight.setY(renderPos.y - BUTTON_HEIGHT * ySelection_CUR);
        highlight.render(sb);
        
    }
    
    public void updateSelection(Direction direction){
    
        switch(direction){
            case UP:
                ySelection_CUR = ySelection_CUR <= 0 ? ySelection_MAX : ySelection_CUR - 1; 
                
                
                break;
                
            case DOWN:
                
                ySelection_CUR = ySelection_CUR >= ySelection_MAX ? 0: ySelection_CUR + 1;
                
                break;
                
            default:
                break;
                   
        }
        
    }
    
    public void confirmSelection(){
        //execute action on button selection
        
        if(currentButtons.size > 0){
            MenuButton but = currentButtons.get(ySelection_CUR);
            but.execute();
            currentButtons = but.getButtons();
            ySelection_MAX = currentButtons.size - 1;
        }
        ySelection_CUR = 0;
        xSelection++;
        
    }
    
    
}
