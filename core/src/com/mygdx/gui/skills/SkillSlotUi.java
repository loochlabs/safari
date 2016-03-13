/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.gui.skills;

import com.mygdx.game.MainGame;
import com.mygdx.gui.OverlayComponent;
import com.mygdx.managers.ResourceManager;

/**
 *
 * @author looch
 */
public class SkillSlotUi extends OverlayComponent{

    public SkillSlotUi(float x, float y, float width, float height) {
        super(x, y, width, height);
        
        //texture = MainGame.am.get(ResourceManager.SKILL_NONE);
    }

    @Override
    public void update() {
    }
    
}
