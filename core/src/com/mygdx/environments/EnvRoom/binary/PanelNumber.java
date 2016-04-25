/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvRoom.binary;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.StaticEntities.StaticEntity;
import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class PanelNumber extends StaticEntity{

    private final CodePanel panel;
    private Texture texture0, texture1;
    
    public void setOn(boolean on) { 
        texture = on ? texture1 : texture0;
    }
    
    public PanelNumber(Vector2 pos, float w, float h, CodePanel panel) {
        super(pos, w, h);
        
        this.panel = panel;
        
        bd.position.set(pos.x/PPM,pos.y/PPM);
        shape.setAsBox(width/PPM, height/PPM);
        fd.shape = shape;
        userdata = "nwall_" + id;
        fd.filter.categoryBits = BIT_WALL;
        fd.filter.maskBits = BIT_PLAYER;
        fd.isSensor = true;
        
        this.flaggedForRenderSort = false;
        
        //texture0 = MainGame.am.get(ResourceManager.ROOM_CODEPANEL0);
        //texture1 = MainGame.am.get(ResourceManager.ROOM_CODEPANEL1);
        
        this.setOn(panel.isOn());
    }
    
}
