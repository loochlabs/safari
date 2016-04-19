/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.managers;

import com.mygdx.environments.EnvironmentManager;
import com.mygdx.managers.StateManager.State;
import com.mygdx.screen.GameScreen;
import com.mygdx.screen.ScreenManager;
import static com.mygdx.utilities.Direction.DOWN;
import static com.mygdx.utilities.Direction.LEFT;
import static com.mygdx.utilities.Direction.RIGHT;
import static com.mygdx.utilities.Direction.UP;

/**
 *
 * @author looch
 */
public class PlayerInputManager {
    
    
    public void update() throws InterruptedException{
        if(EnvironmentManager.currentEnv.getStateManager().getState() == State.PLAYING){
        
            if (GameKeyLibrary.isPressed(GameKeyLibrary.MOVE_UP)) {
                GameScreen.player.move(UP);
            } else if (!GameKeyLibrary.isDown(GameKeyLibrary.MOVE_UP)) {
                GameScreen.player.moveStop(UP);
            }

            if (GameKeyLibrary.isPressed(GameKeyLibrary.MOVE_DOWN)) {
                GameScreen.player.move(DOWN);
            } else if (!GameKeyLibrary.isDown(GameKeyLibrary.MOVE_DOWN)) {
                GameScreen.player.moveStop(DOWN);
            }

            if (GameKeyLibrary.isPressed(GameKeyLibrary.MOVE_RIGHT)) {
                GameScreen.player.move(RIGHT);
            } else if (!GameKeyLibrary.isDown(GameKeyLibrary.MOVE_RIGHT)) {
                GameScreen.player.moveStop(RIGHT);
            }

            if (GameKeyLibrary.isPressed(GameKeyLibrary.MOVE_LEFT)) {
                GameScreen.player.move(LEFT);
            } else if (!GameKeyLibrary.isDown(GameKeyLibrary.MOVE_LEFT)) {
                GameScreen.player.moveStop(LEFT);
            }

            if (GameKeyLibrary.isPressed(GameKeyLibrary.ATT_ONE)) {
                GameScreen.player.attack(0);
            }
            if (GameKeyLibrary.isPressed(GameKeyLibrary.ATT_TWO)) {
                GameScreen.player.attack(1);
            }
            if (GameKeyLibrary.isPressed(GameKeyLibrary.ATT_THREE)) {
                GameScreen.player.attack(2);
            }
            if (GameKeyLibrary.isPressed(GameKeyLibrary.ATT_FOUR)) {
                GameScreen.player.beginAction();
            }
            
            if(GameKeyLibrary.isPressed(GameKeyLibrary.DEV_CMD)){
                GameScreen.player.killSwitch();
            } 
            
            
            if(GameKeyLibrary.isPressed(GameKeyLibrary.DASH)){
                GameScreen.player.attack(4);
            } 
        
        }
        
        if (GameKeyLibrary.isPressed(GameKeyLibrary.MUTE)) {
            SoundManager.mute();
        }

        if (GameKeyLibrary.isPressed(GameKeyLibrary.MAIN_ESC)) {
            ScreenManager.getCurrentScreen().pause();
        }
        
        if (GameKeyLibrary.isPressed(GameKeyLibrary.DASH)
                && GameScreen.sm.getState() == State.PAUSED) {
            
            GameScreen.pauseOverlay.confirmSelect();

        }
        
    }
    
}
