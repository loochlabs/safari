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
import com.mygdx.screen.GameScreen;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class PanelSwitch extends StaticEntity{

    private final CodePanel panel;
    private Texture onTexture, offTexture;
    
    public PanelSwitch(Vector2 pos, float w, float h, CodePanel panel) {
        super(pos, w, h);
        
        this.panel = panel;
        
        bd.position.set(pos.x/PPM,pos.y/PPM);
        shape.setAsBox(width/PPM, height/PPM);
        fd.shape = shape;
        userdata = "action_" + id;
        fd.filter.categoryBits = BIT_WALL;
        fd.filter.maskBits = BIT_PLAYER;
        fd.isSensor = true;
        
        this.flaggedForRenderSort = false;
        
        //texture = MainGame.am.get(ResourceManager.ROOM_CODEPANEL_SWITCH);
        
        onTexture = MainGame.am.get(ResourceManager.ROOM_CODESWITCH_1);
        offTexture = MainGame.am.get(ResourceManager.ROOM_CODESWITCH_0);
        
        texture = panel.isOn() ? onTexture : offTexture;
    }
    
    @Override
    public void alert(String str){
        if (str.equals("active")) {
            GameScreen.player.inRangeForAction(this);
        }
        if (str.equals("inactive")) {
            GameScreen.player.outRangeForAction(this);
        }
    }
    
    @Override
    public void actionEvent() {
        //switch panel
        panel.switchState();
        
        texture = panel.isOn() ? onTexture : offTexture;
    }
    
}
