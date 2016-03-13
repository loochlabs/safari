/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.managers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MainGame;
import com.mygdx.utilities.XBox360Pad;

/**
 *
 * @author looch
 */
public class InputProcessor_Gamepad extends InputAdapter implements ControllerListener{

    //****************
    //  GAMEPAD
    //****************
    
    private final float SENSITIVITY = 0.4f;
    private boolean moveRight = false, moveLeft = false, moveUp = false, moveDown = false;
    
    public InputProcessor_Gamepad(){
        GameInputProcessor.controller = true;
        
        GameInputProcessor.KEY_ACTION_1 = XBox360Pad.BUTTON_A;
        GameInputProcessor.KEY_ACTION_2 = XBox360Pad.BUTTON_B;
        GameInputProcessor.KEY_ACTION_3 = XBox360Pad.BUTTON_X;
        GameInputProcessor.KEY_ACTION_4 = XBox360Pad.BUTTON_Y;
        GameInputProcessor.MAIN_ESC = XBox360Pad.BUTTON_START;
    }
    
    public void update(){
        if(MainGame.hasControllers){
            // Update movement based on movement of left stick
            // Give a "deadzone" of 0.2 - -0.2, meaning the first 20% in either direction will be ignored.
            // This keeps controls from being too twitchy
            // Move by up to 10 pixels per frame if full left or right.
            // Once again I flipped the sign on the Y axis because I prefer inverted Y axis controls.
            
            //move right
            if(Controllers.getControllers().first().getAxis(XBox360Pad.AXIS_LEFT_X) > SENSITIVITY
                    && !moveRight){
                GameKeyLibrary.setKey(GameKeyLibrary.MOVE_RIGHT, true);
                moveRight = true;
            }else if(Controllers.getControllers().first().getAxis(XBox360Pad.AXIS_LEFT_X) <= SENSITIVITY
                    && moveRight){
                GameKeyLibrary.setKey(GameKeyLibrary.MOVE_RIGHT, false);
                moveRight = false;
            }
            //move left
            if(Controllers.getControllers().first().getAxis(XBox360Pad.AXIS_LEFT_X) < -SENSITIVITY
                    && !moveLeft){
                GameKeyLibrary.setKey(GameKeyLibrary.MOVE_LEFT, true);
                moveLeft = true;
            }else if(Controllers.getControllers().first().getAxis(XBox360Pad.AXIS_LEFT_X) >= -SENSITIVITY
                    && moveLeft){
                GameKeyLibrary.setKey(GameKeyLibrary.MOVE_LEFT, false);
                moveLeft = false;
            }
                
            //down
            if(Controllers.getControllers().first().getAxis(XBox360Pad.AXIS_LEFT_Y) > SENSITIVITY
                    && !moveDown){
                GameKeyLibrary.setKey(GameKeyLibrary.MOVE_DOWN, true);
                moveDown = true;
            }else if(Controllers.getControllers().first().getAxis(XBox360Pad.AXIS_LEFT_Y) <= SENSITIVITY
                    && moveDown){
                GameKeyLibrary.setKey(GameKeyLibrary.MOVE_DOWN, false);
                moveDown = false;
            }
         
            //up
            if(Controllers.getControllers().first().getAxis(XBox360Pad.AXIS_LEFT_Y) < -SENSITIVITY
                    && !moveUp){
                GameKeyLibrary.setKey(GameKeyLibrary.MOVE_UP, true);
                moveUp = true;
            }else if(Controllers.getControllers().first().getAxis(XBox360Pad.AXIS_LEFT_Y) >= -SENSITIVITY
                    && moveUp){
                GameKeyLibrary.setKey(GameKeyLibrary.MOVE_UP, false);
                moveUp = false;
            }

            // Poll if user hits start button, if they do, reset position of sprite
            /*
            if(Controllers.getControllers().first().getButton(XBox360Pad.BUTTON_START))
                System.out.println("@ControllerInputProc start");
            */
        }
    }
    

    // connected and disconnect dont actually appear to work for XBox 360 controllers.
    @Override
    public void connected(Controller controller) {
        MainGame.hasControllers = true;
    }

    @Override
    public void disconnected(Controller controller) {
        MainGame.hasControllers = false;
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        
        if(buttonCode == XBox360Pad.BUTTON_A){
            GameKeyLibrary.setKey(GameKeyLibrary.ATT_ONE, true);
        }
        if(buttonCode == XBox360Pad.BUTTON_B){
            GameKeyLibrary.setKey(GameKeyLibrary.ATT_TWO, true);
        }
        if(buttonCode == XBox360Pad.BUTTON_X){
            GameKeyLibrary.setKey(GameKeyLibrary.ATT_THREE, true);
        }
        if(buttonCode == XBox360Pad.BUTTON_Y){
            GameKeyLibrary.setKey(GameKeyLibrary.ATT_FOUR, true);
        }

        if(buttonCode == XBox360Pad.BUTTON_LB){
            GameKeyLibrary.setKey(GameKeyLibrary.DASH, true);
        }
        if(buttonCode == XBox360Pad.BUTTON_RB){
            GameKeyLibrary.setKey(GameKeyLibrary.SKILL_SELECT, true);
        }
        
        if(buttonCode == XBox360Pad.BUTTON_START){
            GameKeyLibrary.setKey(GameKeyLibrary.MAIN_ESC, true);
            System.out.println("@ControllerInputProc start PRESS ");
        }
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        
        if(buttonCode == XBox360Pad.BUTTON_A){
            GameKeyLibrary.setKey(GameKeyLibrary.ATT_ONE, false);
        }
        if(buttonCode == XBox360Pad.BUTTON_B){
            GameKeyLibrary.setKey(GameKeyLibrary.ATT_TWO, false);
        }
        if(buttonCode == XBox360Pad.BUTTON_X){
            GameKeyLibrary.setKey(GameKeyLibrary.ATT_THREE, false);
        }
        if(buttonCode == XBox360Pad.BUTTON_Y){
            GameKeyLibrary.setKey(GameKeyLibrary.ATT_FOUR, false);
        }

        if(buttonCode == XBox360Pad.BUTTON_LB){
            GameKeyLibrary.setKey(GameKeyLibrary.DASH, false);
        }
        if(buttonCode == XBox360Pad.BUTTON_RB){
            GameKeyLibrary.setKey(GameKeyLibrary.SKILL_SELECT, false);
        }
        
        if(buttonCode == XBox360Pad.BUTTON_START){
            GameKeyLibrary.setKey(GameKeyLibrary.MAIN_ESC, false);
            System.out.println("@ControllerInputProc start RELEASE ");
        }
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        // This is your analog stick
        // Value will be from -1 to 1 depending how far left/right, up/down the stick is
        // For the Y translation, I use a negative because I like inverted analog stick
        // Like all normal people do! ;)

        /*
        // Left Stick
        if(axisCode == XBox360Pad.AXIS_LEFT_X)
            System.out.println("@MainGame PRESS: axis_left_x");
        if(axisCode == XBox360Pad.AXIS_LEFT_Y)
            System.out.println("@MainGame PRESS: axis_left_y");

        // Right stick
        if(axisCode == XBox360Pad.AXIS_RIGHT_X)
            System.out.println("@MainGame PRESS: axis_right_x");
                */
        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        // This is the dpad
        if(value == XBox360Pad.BUTTON_DPAD_LEFT)
            System.out.println("@MainGame PRESS: dpad_left");
        if(value == XBox360Pad.BUTTON_DPAD_RIGHT)
            System.out.println("@MainGame PRESS: dpad_right");
        if(value == XBox360Pad.BUTTON_DPAD_UP)
            System.out.println("@MainGame PRESS: dpad_up");
        if(value == XBox360Pad.BUTTON_DPAD_DOWN)
            System.out.println("@MainGame PRESS: dpad_down");
        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }
    

    
    
}
