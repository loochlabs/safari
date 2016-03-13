/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.gui.pause;

import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;

/**
 *
 * @author looch
 */
public class PauseSound extends PauseComponent{

    public PauseSound(float x, float y, float width, float height) {
        super(x, y, width, height);
        
        texture = MainGame.am.get(ResourceManager.PAUSE_SOUND);
    }
    
}
