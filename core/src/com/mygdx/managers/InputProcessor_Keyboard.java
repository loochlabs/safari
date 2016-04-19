/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.managers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

/**
 *
 * @author looch
 */
public class InputProcessor_Keyboard extends InputAdapter{
    
    private final int KEY_UP =          Keys.W;
    private final int KEY_DOWN =        Keys.S;
    private final int KEY_RIGHT =       Keys.D;
    private final int KEY_LEFT =        Keys.A;
    private final int KEY_ATT_ZERO =     Keys.U;
    private final int KEY_ATT_ONE =     Keys.I;
    private final int KEY_ATT_TWO =     Keys.O;
    private final int KEY_ATT_FOUR =     Keys.SPACE;
    private final int KEY_ATT_ACTION =    Keys.E;
    private final int KEY_SKILL_SELECT =    Keys.Q;
    private final int DEV_CMD =         Keys.DEL;
    private final int MAIN_ESC =        Keys.ESCAPE;
    private final int MUTE =            Keys.M;
    
    
    public InputProcessor_Keyboard(){
        GameInputProcessor.controller = false;
        
        GameInputProcessor.KEY_UP = KEY_UP;
        GameInputProcessor.KEY_DOWN = KEY_DOWN;
        GameInputProcessor.KEY_RIGHT = KEY_RIGHT;
        GameInputProcessor.KEY_LEFT = KEY_LEFT;
        GameInputProcessor.KEY_ACTION_0 = KEY_ATT_ZERO;
        GameInputProcessor.KEY_ACTION_1 = KEY_ATT_ONE;
        GameInputProcessor.KEY_ACTION_2 = KEY_ATT_TWO;
        GameInputProcessor.KEY_ACTION_4 = KEY_ATT_FOUR;
        GameInputProcessor.KEY_ACTION_ACTION = KEY_ATT_ACTION;
        GameInputProcessor.KEY_SKILL_SELECT = KEY_SKILL_SELECT;
        GameInputProcessor.DEV_CMD = DEV_CMD;
        GameInputProcessor.MAIN_ESC = MAIN_ESC;
        GameInputProcessor.MUTE = MUTE;
    }
    
    //TODO: soft code Key values for account
    @Override
    public boolean keyDown(int k){
        if(k == KEY_UP){
            GameKeyLibrary.setKey(GameKeyLibrary.MOVE_UP, true);
        }
        if(k == KEY_LEFT){
            GameKeyLibrary.setKey(GameKeyLibrary.MOVE_LEFT, true);
        }
        if(k == KEY_DOWN){
            GameKeyLibrary.setKey(GameKeyLibrary.MOVE_DOWN, true);
        }
        if(k == KEY_RIGHT){
            GameKeyLibrary.setKey(GameKeyLibrary.MOVE_RIGHT, true);
        }
        if(k == KEY_ATT_ZERO){
            GameKeyLibrary.setKey(GameKeyLibrary.ATT_ZERO, true);
        }
        if(k == KEY_ATT_ONE){
            GameKeyLibrary.setKey(GameKeyLibrary.ATT_ONE, true);
        }
        if(k == KEY_ATT_TWO){
            GameKeyLibrary.setKey(GameKeyLibrary.ATT_TWO, true);
        }
        if(k == KEY_ATT_FOUR){
            GameKeyLibrary.setKey(GameKeyLibrary.ATT_FOUR, true);
        }
        if(k == KEY_ATT_ACTION){
            GameKeyLibrary.setKey(GameKeyLibrary.ACTION, true);
        }
        if(k == KEY_SKILL_SELECT){
            GameKeyLibrary.setKey(GameKeyLibrary.SKILL_SELECT, true);
        }
        if(k == DEV_CMD){
            GameKeyLibrary.setKey(GameKeyLibrary.DEV_CMD, true);
        }
        if(k == MAIN_ESC){
            GameKeyLibrary.setKey(GameKeyLibrary.MAIN_ESC, true);
        }
        if(k == MUTE){
            GameKeyLibrary.setKey(GameKeyLibrary.MUTE, true);
        }
        return true;
    }
    
    @Override
    public boolean keyUp(int k){
        if(k == KEY_UP){
            GameKeyLibrary.setKey(GameKeyLibrary.MOVE_UP, false);
        }
        if(k == KEY_LEFT){
            GameKeyLibrary.setKey(GameKeyLibrary.MOVE_LEFT, false);
        }
        if(k == KEY_DOWN){
            GameKeyLibrary.setKey(GameKeyLibrary.MOVE_DOWN, false);
        }
        if(k == KEY_RIGHT){
            GameKeyLibrary.setKey(GameKeyLibrary.MOVE_RIGHT, false);
        }
        if(k == KEY_ATT_ZERO){
            GameKeyLibrary.setKey(GameKeyLibrary.ATT_ZERO, false);
        }
        if(k == KEY_ATT_ONE){
            GameKeyLibrary.setKey(GameKeyLibrary.ATT_ONE, false);
        }
        if(k == KEY_ATT_TWO){
            GameKeyLibrary.setKey(GameKeyLibrary.ATT_TWO, false);
        }
        if(k == KEY_ATT_FOUR){
            GameKeyLibrary.setKey(GameKeyLibrary.ATT_FOUR, false);
        }
        if(k == KEY_ATT_ACTION){
            GameKeyLibrary.setKey(GameKeyLibrary.ACTION, false);
        }
        if(k == KEY_SKILL_SELECT){
            GameKeyLibrary.setKey(GameKeyLibrary.SKILL_SELECT, false);
        }
        if(k == DEV_CMD){
            GameKeyLibrary.setKey(GameKeyLibrary.DEV_CMD, false);
        }
        if(k == MAIN_ESC){
            GameKeyLibrary.setKey(GameKeyLibrary.MAIN_ESC, false);
        }
        if(k == MUTE){
            GameKeyLibrary.setKey(GameKeyLibrary.MUTE, false);
        }
        return true;
    }
    
}
