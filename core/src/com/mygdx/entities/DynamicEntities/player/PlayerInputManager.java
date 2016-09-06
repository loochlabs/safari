/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.managers.GameKeyLibrary;
import com.mygdx.managers.SoundManager;
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
    
    
    public void update() throws InterruptedException {

        try {

            if (GameKeyLibrary.isPressed(GameKeyLibrary.MOVE_UP)) {
                EnvironmentManager.player.move(UP);
            } else if (!GameKeyLibrary.isDown(GameKeyLibrary.MOVE_UP)) {
                EnvironmentManager.player.moveStop(UP);
            }

            if (GameKeyLibrary.isPressed(GameKeyLibrary.MOVE_DOWN)) {
                EnvironmentManager.player.move(DOWN);
            } else if (!GameKeyLibrary.isDown(GameKeyLibrary.MOVE_DOWN)) {
                EnvironmentManager.player.moveStop(DOWN);
            }

            if (GameKeyLibrary.isPressed(GameKeyLibrary.MOVE_RIGHT)) {
                EnvironmentManager.player.move(RIGHT);
            } else if (!GameKeyLibrary.isDown(GameKeyLibrary.MOVE_RIGHT)) {
                EnvironmentManager.player.moveStop(RIGHT);
            }

            if (GameKeyLibrary.isPressed(GameKeyLibrary.MOVE_LEFT)) {
                EnvironmentManager.player.move(LEFT);
            } else if (!GameKeyLibrary.isDown(GameKeyLibrary.MOVE_LEFT)) {
                EnvironmentManager.player.moveStop(LEFT);
            }

            if (GameKeyLibrary.isPressed(GameKeyLibrary.ATT_ZERO)) {
                //EnvironmentManager.player.attack(0);
            }
            if (GameKeyLibrary.isPressed(GameKeyLibrary.ATT_ONE)) {
                //EnvironmentManager.player.attack(1);
            }
            if (GameKeyLibrary.isPressed(GameKeyLibrary.ATT_TWO)) {
                //EnvironmentManager.player.attack(2);
            }
            if (GameKeyLibrary.isPressed(GameKeyLibrary.ATT_FOUR)) {
                // EnvironmentManager.player.attack(4);
            }
            if (GameKeyLibrary.isPressed(GameKeyLibrary.ACTION)) {
                EnvironmentManager.player.beginAction();
            }

            if (GameKeyLibrary.isPressed(GameKeyLibrary.DEV_CMD)) {
                //EnvironmentManager.player.killSwitch();
            }

            if (GameKeyLibrary.isPressed(GameKeyLibrary.MUTE)) {
                SoundManager.mute();
            }

            if (GameKeyLibrary.isPressed(GameKeyLibrary.MAIN_ESC)) {
                ScreenManager.getCurrentScreen().pause();
            }

            if (GameKeyLibrary.isPressed(GameKeyLibrary.ATT_FOUR)
                    && GameScreen.sm.getState() == State.PAUSED) {

                GameScreen.pauseOverlay.confirmSelect();
            }
            
            if (GameKeyLibrary.isPressed(GameKeyLibrary.MOUSE_LEFT)) {
                EnvironmentManager.player.action_down(1);
            }else if (!GameKeyLibrary.isDown(GameKeyLibrary.MOUSE_LEFT)){
                EnvironmentManager.player.action_up(1);
            }
            
           
        } catch (NullPointerException ex) {
            System.err.println("@PlayerInputManager: player has not been initiallized yet!");
        }

    }

}
