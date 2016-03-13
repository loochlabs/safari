/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.screen.MainMenu;

import com.mygdx.managers.GameInputProcessor;
import com.mygdx.managers.GameKeyLibrary;
import com.mygdx.utilities.Direction;

/**
 *
 * @author looch
 */
public class MenuInputManager {
    
    private Overlay_MainMenu menu;
    
    public MenuInputManager(Overlay_MainMenu menu){
        this.menu = menu;
    }
    
    public void update(){
    
        if (!GameInputProcessor.controller) {
            if (GameKeyLibrary.isPressed(GameKeyLibrary.MOVE_UP)) {
                //move menu selection up
                menu.updateSelection(Direction.UP);
            }

            if (GameKeyLibrary.isPressed(GameKeyLibrary.MOVE_DOWN)) {
                //move menu selection down
                menu.updateSelection(Direction.DOWN);
            }

            if (GameKeyLibrary.isPressed(GameKeyLibrary.DASH)) {
                //menu select
                menu.confirmSelection();
                System.out.println("@MenuInputManager confirm");
            }
        }else{
            if (GameKeyLibrary.isPressed(GameKeyLibrary.MOVE_UP)) {
                //move menu selection up
                menu.updateSelection(Direction.UP);
            }

            if (GameKeyLibrary.isPressed(GameKeyLibrary.MOVE_DOWN)) {
                //move menu selection down
                menu.updateSelection(Direction.DOWN);
            }

            if (GameKeyLibrary.isPressed(GameKeyLibrary.ATT_ONE)) {
                //menu select
                menu.confirmSelection();
                System.out.println("@MenuInputManager confirm");
            }
        }
        
    }
    
}
